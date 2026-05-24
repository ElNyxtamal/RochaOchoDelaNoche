package proyectoversus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dashboard extends JFrame {

    public Dashboard() {
        // 1. Configuración de la ventana principal
        setTitle("Sistema de Torneos - Smash Ultimate");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 2. Crear el panel de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // 3. Crear las pestañas
        JPanel panelAttendees = crearPanelAttendees();
        tabbedPane.addTab("Attendees (Jugadores)", panelAttendees);

        // Instanciamos nuestro nuevo panel visual
        PanelBracket panelBracket = new PanelBracket(true);
        tabbedPane.addTab("Bracket Principal", panelBracket);

        // 4. Agregar las pestañas a la ventana
        add(tabbedPane);
    }

    // Método para armar la pestaña de Attendees (La 'R' del CRUD)
    private JPanel crearPanelAttendees() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Configurar las columnas de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Gamertag");
        modelo.addColumn("Nombre Real");
        modelo.addColumn("Equipo");
        
        JTable tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla); 
        
        // Llenar la tabla con los datos de SQL
        cargarDatosJugadores(modelo);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // --- ZONA DE BOTONES INFERIORES ---
        JPanel panelBotones = new JPanel(); 
        
        JButton btnNuevo = new JButton("Registrar Nuevo Jugador");
        btnNuevo.addActionListener(e -> {
            RegistroJugador ventanaRegistro = new RegistroJugador(this);
            ventanaRegistro.setVisible(true);
            cargarDatosJugadores(modelo);
        });
        
        JButton btnRefrescar = new JButton("Actualizar Lista");
        btnRefrescar.addActionListener(e -> cargarDatosJugadores(modelo));
        
        // ¡NUEVO BOTÓN DE BORRAR!
        JButton btnBorrar = new JButton("Borrar Seleccionado");
        // Nota: asumo que tu JTable se llama simplemente 'tabla' en este contexto
        btnBorrar.addActionListener(e -> borrarJugador(tabla, modelo));
        
        // 4. ¡NUEVO BOTÓN DE EDITAR! (Pégalo aquí)
        JButton btnEditar = new JButton("Editar Seleccionado");
        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(panel, "Selecciona un jugador de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Extraemos los datos de la fila seleccionada
            int id = (int) modelo.getValueAt(fila, 0);
            String tag = (String) modelo.getValueAt(fila, 1);
            String nom = (String) modelo.getValueAt(fila, 2);
            String eq = (String) modelo.getValueAt(fila, 3);

            // Buscamos la ventana principal para montar el diálogo encima
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
            EditarJugador ventanaEditar = new EditarJugador(parentFrame, id, tag, nom, eq);
            ventanaEditar.setVisible(true);
            
            // Cuando se cierre la ventana de edición, actualizamos la tabla
            cargarDatosJugadores(modelo);
        });
        
        // Los agregamos todos al panel horizontal en el orden que quieras que aparezcan
        panelBotones.add(btnNuevo);
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnBorrar); 
        panelBotones.add(btnEditar); // <--- ¡Metemos el de editar al panel!
        
        // Metemos el contenedor de botones al sur del panel principal
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }

    // Método que hace el SELECT a la base de datos
    private void cargarDatosJugadores(DefaultTableModel modelo) {
    modelo.setRowCount(0); 
    
    // Usamos LEFT JOIN para no discriminar a los que no tienen equipo
    String sql = "SELECT j.id_jugador, j.gamertag, j.nombre, e.nombre_equipo " +
                 "FROM JUGADOR j " +
                 "LEFT JOIN equipo e ON j.id_equipo = e.id_equipo";
                 
    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
         
         while (rs.next()) {
             Object[] fila = new Object[4]; 
             
             fila[0] = rs.getInt("id_jugador");
             fila[1] = rs.getString("gamertag");
             fila[2] = rs.getString("nombre");
             
             // Si el equipo es NULL, ponemos un texto vacío o "Sin Equipo"
             String equipo = rs.getString("nombre_equipo");
             fila[3] = (equipo != null) ? equipo : "N/A"; 
             
             modelo.addRow(fila);
         }
    } catch (SQLException e) {
         System.out.println("Error al cargar la tabla con JOIN: " + e.getMessage());
    }
}
    
    // Método main para pruebas individuales
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Dashboard ventana = new Dashboard();
            ventana.setVisible(true);
        });
    }
    
    private void borrarJugador(JTable tabla, DefaultTableModel modelo) {
        // Obtenemos qué fila seleccionó el usuario
        int filaSeleccionada = tabla.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Primero selecciona un jugador de la tabla para borrarlo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Rescatamos el ID y el Gamertag de esa fila
        int idJugador = (int) modelo.getValueAt(filaSeleccionada, 0);
        String gamertag = (String) modelo.getValueAt(filaSeleccionada, 1);

        // Ventana de confirmación para no borrar a lo loco
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de que quieres eliminar del torneo a " + gamertag + "?\nEsta acción no se puede deshacer.", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM JUGADOR WHERE id_jugador = ?";
            
            try (Connection con = Conexion.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                 
                 ps.setInt(1, idJugador);
                 ps.executeUpdate(); // ¡Boom! Adiós jugador
                 
                 JOptionPane.showMessageDialog(this, "Jugador eliminado con éxito.");
                 
                 // Recargamos la tabla para que desaparezca visualmente
                 cargarDatosJugadores(modelo); 
                 
            } catch (SQLException ex) {
                 JOptionPane.showMessageDialog(this, "Error al borrar: " + ex.getMessage(), "Error DB", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}