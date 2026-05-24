package proyectoversus;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditarJugador extends JDialog {

    private JTextField txtGamertag;
    private JTextField txtNombre;
    private JComboBox<Equipo> comboEquipos;
    private int idJugadorActual;

    class Equipo {
        int id;
        String nombre;
        public Equipo(int id, String nombre) { this.id = id; this.nombre = nombre; }
        @Override public String toString() { return nombre; }
    }

    // El constructor ahora recibe los datos de la tabla para pre-llenar las cajas
    public EditarJugador(JFrame parent, int idJugador, String gamertag, String nombre, String equipoActual) {
        super(parent, "Editar Jugador", true);
        this.idJugadorActual = idJugador;
        setSize(300, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4, 1, 10, 10));

        JPanel panelGamertag = new JPanel();
        panelGamertag.add(new JLabel("Gamertag:"));
        txtGamertag = new JTextField(gamertag, 15);
        panelGamertag.add(txtGamertag);

        JPanel panelNombre = new JPanel();
        panelNombre.add(new JLabel("Nombre Real:"));
        txtNombre = new JTextField(nombre != null ? nombre : "", 15);
        panelNombre.add(txtNombre);

        JPanel panelEquipo = new JPanel();
        panelEquipo.add(new JLabel("Equipo:"));
        comboEquipos = new JComboBox<>();
        cargarEquipos(equipoActual); // Carga la lista y selecciona el que ya tenía
        panelEquipo.add(comboEquipos);

        JPanel panelBoton = new JPanel();
        JButton btnActualizar = new JButton("Actualizar Jugador");
        btnActualizar.addActionListener(e -> actualizarEnBD());
        panelBoton.add(btnActualizar);

        add(panelGamertag);
        add(panelNombre);
        add(panelEquipo);
        add(panelBoton);
    }

    private void cargarEquipos(String equipoActual) {
        String sql = "SELECT id_equipo, nombre_equipo FROM equipo";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
             
             while (rs.next()) {
                 Equipo eq = new Equipo(rs.getInt("id_equipo"), rs.getString("nombre_equipo"));
                 comboEquipos.addItem(eq);
                 // Si el nombre coincide con el que traía de la tabla, lo deja seleccionado
                 if (eq.nombre.equals(equipoActual)) {
                     comboEquipos.setSelectedItem(eq);
                 }
             }
        } catch (SQLException e) { 
             System.out.println("Error al cargar equipos: " + e.getMessage()); 
        }
    }

    private void actualizarEnBD() {
        if (txtGamertag.getText().isEmpty() || txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No dejes campos vacíos", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Equipo eqSel = (Equipo) comboEquipos.getSelectedItem();
        // Consulta UPDATE mágica
        String sql = "UPDATE JUGADOR SET gamertag = ?, nombre = ?, id_equipo = ? WHERE id_jugador = ?";
        
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, txtGamertag.getText());
            ps.setString(2, txtNombre.getText());
            ps.setInt(3, eqSel.id);
            ps.setInt(4, idJugadorActual); // Usamos el ID para saber a quién actualizar
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "¡Jugador actualizado correctamente!");
            dispose();
            
        } catch (SQLException ex) { 
            JOptionPane.showMessageDialog(this, "Error DB: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
        }
    }
}
