package proyectoversus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnEntrar;
    private JButton btnEspectador; 
    private JButton btnInscribirse; 

    public Login() {
        // 1. Configuración de la ventana
        setTitle("Acceso al Torneo");
        setSize(480, 200); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        
        // El layout principal se queda igual
        setLayout(new GridLayout(3, 1, 10, 10)); 

        // 2. Crear los componentes
        JPanel panelUsuario = new JPanel();
        panelUsuario.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField(15);
        panelUsuario.add(txtUsuario);

        JPanel panelPassword = new JPanel();
        panelPassword.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField(15);
        panelPassword.add(txtPassword);

        // --- ZONA DE BOTONES (CORREGIDA) ---
        // Volvemos al diseño fluido (FlowLayout) para que los botones tengan su tamaño normal
        // y se queden centrados sin engordar cuando haces la ventana gigante.
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        
        btnEntrar = new JButton("Entrar (Admin)");
        btnEspectador = new JButton("Ver Bracket");
        btnInscribirse = new JButton("Inscribirse"); 
        
        panelBoton.add(btnEntrar);
        panelBoton.add(btnEspectador);
        panelBoton.add(btnInscribirse); 

        // 3. Agregar los paneles a la ventana
        add(panelUsuario);
        add(panelPassword);
        add(panelBoton);

        // 4. Lógica del botón Entrar (ADMIN)
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                if (validarAcceso(usuario, password)) {
                    JOptionPane.showMessageDialog(null, "¡Bienvenido, " + usuario + "!");
                    dispose(); // Cierra la ventana de login
                    
                    Dashboard dashboard = new Dashboard(); 
                    dashboard.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 5. Lógica del botón Espectador (PÚBLICO)
        btnEspectador.addActionListener(e -> {
            JFrame vistaJugador = new JFrame("Bracket del Torneo (Modo Lectura)");
            vistaJugador.setSize(850, 650); 
            vistaJugador.setLocationRelativeTo(null);
            
            // Mata la ejecución si el usuario le da a la 'X' roja
            vistaJugador.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            vistaJugador.setLayout(new BorderLayout()); 
            
            // El botón de Regresar
            JPanel panelNavegacion = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton btnRegresar = new JButton("⬅ Regresar al Menú");
            btnRegresar.addActionListener(ev -> {
                vistaJugador.dispose(); // Cierra el bracket
                new Login().setVisible(true); // Vuelve a abrir el menú principal
            });
            panelNavegacion.add(btnRegresar);
            
            // Instanciamos el bracket
            PanelBracket panelPublico = new PanelBracket(false); // FALSE = Clics desactivados
            panelPublico.setBackground(new Color(240, 240, 245)); 
            
            // Agregamos todo a la ventana
            vistaJugador.add(panelNavegacion, BorderLayout.NORTH); 
            vistaJugador.add(panelPublico, BorderLayout.CENTER); 
            
            vistaJugador.setVisible(true);
            dispose(); // Cierra el login 
        });

        // 6. Lógica del botón Inscribirse (PÚBLICO) con Cadenero
        btnInscribirse.addActionListener(e -> {
            int inscritos = contarJugadoresRegistrados();
            
            if (inscritos >= 8) {
                JOptionPane.showMessageDialog(this, "¡Lo sentimos! El torneo ya alcanzó su límite de 8 jugadores.", "Cupo Lleno", JOptionPane.WARNING_MESSAGE);
            } else {
                RegistroJugador ventanaRegistro = new RegistroJugador(this);
                ventanaRegistro.setVisible(true);
            }
        });
    }

    // Método que conecta con XAMPP y verifica los datos del Admin
    private boolean validarAcceso(String user, String pass) {
        boolean accesoConcedido = false;
        String query = "SELECT * FROM USUARIO WHERE username = ? AND password = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, user);
            ps.setString(2, pass);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                accesoConcedido = true;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }

        return accesoConcedido;
    }

    // Método que cuenta cuántos jugadores hay en XAMPP para el cadenero
    private int contarJugadoresRegistrados() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM JUGADOR";
        
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                total = rs.getInt("total");
            }
            
        } catch (SQLException ex) {
            System.out.println("Error contando jugadores: " + ex.getMessage());
        }
        
        return total;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login ventanaLogin = new Login();
            ventanaLogin.setVisible(true);
        });
    }
}