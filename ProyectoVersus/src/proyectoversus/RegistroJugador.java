package proyectoversus;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegistroJugador extends JDialog {

    private JTextField txtGamertag;
    private JTextField txtNombre;
    private JComboBox<Equipo> comboEquipos; // ¡El nuevo menú desplegable!
    private JButton btnGuardar;

    // Una mini-clase para guardar el ID y el Nombre juntos como si fueran un solo bloque
    class Equipo {
        int id;
        String nombre;
        
        public Equipo(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }
        
        // Esto es VITAL: le dice a Java qué texto mostrar exactamente en la pantalla
        @Override
        public String toString() {
            return nombre;
        }
    }

    public RegistroJugador(JFrame parent) {
        super(parent, "Registrar Nuevo Jugador", true);
        setSize(300, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4, 1, 10, 10));

        // 1. Panel de Gamertag
        JPanel panelGamertag = new JPanel();
        panelGamertag.add(new JLabel("Gamertag:"));
        txtGamertag = new JTextField(15);
        panelGamertag.add(txtGamertag);

        // 2. Panel de Nombre
        JPanel panelNombre = new JPanel();
        panelNombre.add(new JLabel("Nombre Real:"));
        txtNombre = new JTextField(15);
        panelNombre.add(txtNombre);

        // 3. Panel de Equipo con Menú Desplegable
        JPanel panelEquipo = new JPanel();
        panelEquipo.add(new JLabel("Equipo Universitario:"));
        comboEquipos = new JComboBox<>();
        cargarEquiposEnMenu(); // Llenamos la lista desde la base de datos
        panelEquipo.add(comboEquipos);

        // 4. Botón de Guardar
        JPanel panelBoton = new JPanel();
        btnGuardar = new JButton("Guardar Jugador");
        panelBoton.add(btnGuardar);

        add(panelGamertag);
        add(panelNombre);
        add(panelEquipo);
        add(panelBoton);

        btnGuardar.addActionListener(e -> guardarJugador());
    }

    // Método para jalar las universidades de XAMPP y meterlas al JComboBox
    private void cargarEquiposEnMenu() {
        // Asegúrate de que tu tabla en XAMPP se llame "equipo" o pon el nombre correcto aquí
        String sql = "SELECT id_equipo, nombre_equipo FROM equipo"; 
        
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
             
             while (rs.next()) {
                 int id = rs.getInt("id_equipo");
                 String nombre = rs.getString("nombre_equipo");
                 // Metemos el objeto completo a la lista
                 comboEquipos.addItem(new Equipo(id, nombre)); 
             }
             
        } catch (SQLException e) {
             System.out.println("Error al cargar universidades: " + e.getMessage());
        }
    }

    // La lógica de la 'C' del CRUD
    private void guardarJugador() {
        String gamertag = txtGamertag.getText();
        String nombre = txtNombre.getText();

        if (gamertag.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor llena todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Sacamos el objeto completo del menú desplegable y le extraemos su ID numérico
        Equipo equipoSeleccionado = (Equipo) comboEquipos.getSelectedItem();
        int idEquipo = equipoSeleccionado.id;

        // Inserción en la base de datos (¡Con su llave foránea!)
        String sql = "INSERT INTO JUGADOR (gamertag, nombre, id_equipo) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, gamertag);
            ps.setString(2, nombre);
            ps.setInt(3, idEquipo);
            
            ps.executeUpdate(); 

            JOptionPane.showMessageDialog(this, "¡Jugador registrado con éxito!");
            dispose(); 

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error DB", JOptionPane.ERROR_MESSAGE);
        }
    }
}