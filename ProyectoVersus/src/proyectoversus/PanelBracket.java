package proyectoversus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PanelBracket extends JPanel {

    // Variable para saber si tiene permisos de moverle al bracket
    private boolean esAdministrador;

    // Arreglos para cada ronda
    private JLabel[] labelsCuartos = new JLabel[8]; 
    private JLabel[] labelsSemis = new JLabel[4];
    private JLabel[] labelsFinal = new JLabel[2];

    // ¡NUEVO!: El constructor ahora recibe la bandera de permisos
    public PanelBracket(boolean isAdmin) {
        this.esAdministrador = isAdmin;

        // 1. Cambiamos el panel principal a BorderLayout para soportar la barra inferior
        setLayout(new java.awt.BorderLayout()); 
        
        // 2. Creamos un sub-panel central para mantener tus 3 columnas del bracket alineadas
        JPanel panelColumnas = new JPanel(new GridLayout(1, 3, 20, 0));
        panelColumnas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Columna 1: Cuartos (Ronda 1) ---
        JPanel ronda1 = new JPanel(new GridLayout(4, 1, 0, 20));
        for (int i = 0; i < 4; i++) {
            ronda1.add(crearCaja(labelsCuartos, i * 2, (i * 2) + 1, "TBD", "TBD", 1));
        }

        // --- Columna 2: Semis (Ronda 2) ---
        JPanel ronda2 = new JPanel(new GridLayout(2, 1, 0, 80)); 
        for (int i = 0; i < 2; i++) {
            ronda2.add(crearCaja(labelsSemis, i * 2, (i * 2) + 1, "Ganador Q" + (i+1), "Ganador Q" + (i+2), 2));
        }

        // --- Columna 3: Final (Ronda 3) ---
        JPanel ronda3 = new JPanel(new GridBagLayout()); 
        ronda3.add(crearCaja(labelsFinal, 0, 1, "Finalista 1", "Finalista 2", 3));

        // Agregamos las rondas al sub-panel de columnas
        panelColumnas.add(ronda1);
        panelColumnas.add(ronda2);
        panelColumnas.add(ronda3);

        // Colocamos el esqueleto del bracket en el centro
        add(panelColumnas, java.awt.BorderLayout.CENTER);

        // --- BOTÓN DE REINICIO (¡Solo aparece si es administrador!) ---
        if (esAdministrador) {
            JPanel panelInferior = new JPanel();
            JButton btnReiniciar = new JButton("Reiniciar Torneo");
            btnReiniciar.setBackground(new java.awt.Color(255, 102, 102)); // Tono rojito
            btnReiniciar.setForeground(java.awt.Color.WHITE);
            btnReiniciar.addActionListener(e -> reiniciarTorneo());
            panelInferior.add(btnReiniciar);
            
            // Colocamos la barra del botón en la parte inferior
            add(panelInferior, java.awt.BorderLayout.SOUTH);
        }

        // --- CARGA DE DATOS DESDE XAMPP ---
        cargarJugadoresAlBracket(); // Carga los 8 jugadores iniciales
        cargarProgresoBracket();    // Carga las posiciones guardadas si el torneo ya inició
    }

    // Método universal para crear las cajas e inyectarles la función del doble clic
    private JPanel crearCaja(JLabel[] arregloRonda, int index1, int index2, String txt1, String txt2, int numRonda) {
        JPanel match = new JPanel(new GridLayout(2, 1));
        match.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        match.setBackground(Color.WHITE);

        arregloRonda[index1] = new JLabel(" " + txt1);
        arregloRonda[index2] = new JLabel(" " + txt2);
        arregloRonda[index1] = new JLabel("  " + txt1);
        arregloRonda[index2] = new JLabel("  " + txt2);
        
        // --- ¡EL TOQUE ESTÉTICO (NUEVO)! ---
        // Le ponemos fuente Arial, Negritas (BOLD), tamaño 14
        Font fuentePro = new Font("Arial", Font.BOLD, 14); 
        arregloRonda[index1].setFont(fuentePro);
        arregloRonda[index2].setFont(fuentePro);
        
        arregloRonda[index1].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        // ¡AQUÍ ESTÁ EL TRUCO!: Solo activa los clics si el usuario es administrador
        if (esAdministrador) {
            agregarSistemaDeConfirmacion(match, arregloRonda[index1], arregloRonda[index2], index1, numRonda);
        }

        match.add(arregloRonda[index1]);
        match.add(arregloRonda[index2]);

        JPanel contenedor = new JPanel(new GridBagLayout());
        match.setPreferredSize(new Dimension(140, 50));
        contenedor.add(match);

        return contenedor;
    }

    // Nuevo Sistema de Confirmación
    private void agregarSistemaDeConfirmacion(JPanel matchPanel, JLabel lbl1, JLabel lbl2, int indexBase, int rondaActual) {
        MouseAdapter evento = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Mantenemos el doble clic para activar la ventana
                    String p1 = lbl1.getText().trim();
                    String p2 = lbl2.getText().trim();

                    // 1. Evitar que intenten reportar una pelea que aún no tiene a los dos jugadores listos
                    if (p1.equals("TBD") || p1.startsWith("Ganador") || p1.startsWith("Finalista") ||
                        p2.equals("TBD") || p2.startsWith("Ganador") || p2.startsWith("Finalista")) {
                        JOptionPane.showMessageDialog(matchPanel, "Aún no están definidos ambos retadores para este encuentro.", "Paciencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 2. La magia del OptionDialog (Nuestros botones personalizados)
                    Object[] opciones = {p1, p2, "Cancelar"};
                    int seleccion = JOptionPane.showOptionDialog(matchPanel,
                            "Reportar victoria:\n¿Quién ganó el encuentro entre " + p1 + " y " + p2 + "?",
                            "Confirmar Resultado",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, // Sin icono custom
                            opciones,
                            opciones);

                    // 3. Procesar la respuesta y mandar el ganador a la base de datos
                    if (seleccion == 0) {
                        avanzarJugador(p1, indexBase, rondaActual);
                    } else if (seleccion == 1) {
                        avanzarJugador(p2, indexBase, rondaActual); 
                    }
                    // Si seleccionaron "Cancelar" (índice 2) o cerraron la ventana, no hace nada.
                }
            }
        };

        // Le pegamos el oído a las etiquetas y a toda la caja
        lbl1.addMouseListener(evento);
        lbl2.addMouseListener(evento);
        matchPanel.addMouseListener(evento);
    }

    // Lógica matemática y de Base de Datos para avanzar al ganador
    private void avanzarJugador(String nombreTag, int indexActual, int rondaActual) {
        // Evitamos que avancen los textos vacíos o genéricos
        String gamertagLimpiado = nombreTag.trim();
        if (gamertagLimpiado.equals("TBD") || gamertagLimpiado.startsWith("Ganador") || gamertagLimpiado.startsWith("Finalista")) {
            return;
        }

        // 1. --- ACTUALIZACIÓN VISUAL ---
        int indiceDestino = indexActual / 2;

        if (rondaActual == 1) {
            labelsSemis[indiceDestino].setText(" " + gamertagLimpiado);
        } else if (rondaActual == 2) {
            labelsFinal[indiceDestino].setText(" " + gamertagLimpiado);
        } else if (rondaActual == 3) {
            JOptionPane.showMessageDialog(this, "¡EL CAMPEÓN DEL TORNEO ES " + gamertagLimpiado + "!", "¡Tenemos Ganador!", JOptionPane.INFORMATION_MESSAGE);
        }

        // 2. --- ACTUALIZACIÓN EN BASE DE DATOS (VERSIÓN BLINDADA) ---
        int idGanador = -1;
        
        // Paso A: Buscar el ID real del jugador
        String sqlBusqueda = "SELECT id_jugador FROM jugador WHERE gamertag = ?";
        try (java.sql.Connection con = Conexion.getConexion();
             java.sql.PreparedStatement psBusqueda = con.prepareStatement(sqlBusqueda)) {
             
             psBusqueda.setString(1, gamertagLimpiado);
             java.sql.ResultSet rs = psBusqueda.executeQuery();
             if (rs.next()) {
                 idGanador = rs.getInt("id_jugador");
             }
        } catch (java.sql.SQLException ex) {
             System.out.println("Error buscando ID de " + gamertagLimpiado + ": " + ex.getMessage());
        }

        // Paso B: Si encontramos el ID, lo metemos al primer encuentro vacío de su ronda
        if (idGanador != -1) {
            String sqlUpdate = "UPDATE encuentro SET id_ganador = ? WHERE ronda = ? AND id_ganador IS NULL LIMIT 1";
            try (java.sql.Connection con = Conexion.getConexion();
                 java.sql.PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
                 
                 psUpdate.setInt(1, idGanador);
                 psUpdate.setInt(2, rondaActual);
                 
                 int filasAfectadas = psUpdate.executeUpdate();
                 if (filasAfectadas > 0) {
                     System.out.println("¡Éxito! " + gamertagLimpiado + " guardado en la DB para la ronda " + rondaActual);
                 } else {
                     System.out.println("Aviso: No se encontraron espacios vacíos para " + gamertagLimpiado + " en la ronda " + rondaActual);
                 }
                 
            } catch (java.sql.SQLException ex) {
                 System.out.println("Error actualizando la tabla encuentro: " + ex.getMessage());
            }
        }
    }

    // El SELECT a la base de datos para los competidores iniciales
    private void cargarJugadoresAlBracket() {
        String sql = "SELECT gamertag FROM JUGADOR LIMIT 8";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
             int i = 0;
             while (rs.next() && i < 8) {
                 labelsCuartos[i].setText(" " + rs.getString("gamertag"));
                 i++;
             }
        } catch (SQLException e) {
             System.out.println("Error al cargar jugadores: " + e.getMessage());
        }
    }
    
    private void reiniciarTorneo() {
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de que quieres reiniciar el torneo?\nEsto borrará a los ganadores actuales de la base de datos.", 
            "Confirmar Reinicio", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            // 1. Limpiamos la base de datos
            String sql = "UPDATE encuentro SET id_ganador = NULL";
            try (java.sql.Connection con = Conexion.getConexion();
                 java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
                 ps.executeUpdate();
            } catch (java.sql.SQLException ex) {
                 System.out.println("Error al reiniciar DB: " + ex.getMessage());
            }

            // 2. Limpiamos los textos de la pantalla (Reset visual)
            for (int i = 0; i < 4; i++) {
                labelsSemis[i].setText(" Ganador Q" + (i + 1));
            }
            for (int i = 0; i < 2; i++) {
                labelsFinal[i].setText(" Finalista " + (i + 1));
            }
            
            JOptionPane.showMessageDialog(this, "Bracket reiniciado exitosamente. ¡Listos para otro torneo!");
        }
    }
    
    private void cargarProgresoBracket() {
        // Hacemos un JOIN para traer la ronda y el texto del gamertag de los que ya ganaron
        String sql = "SELECT e.ronda, j.gamertag " +
                     "FROM encuentro e " +
                     "INNER JOIN jugador j ON e.id_ganador = j.id_jugador " +
                     "WHERE e.id_ganador IS NOT NULL " +
                     "ORDER BY e.ronda ASC, e.id_encuentro ASC";

        try (java.sql.Connection con = Conexion.getConexion();
             java.sql.PreparedStatement ps = con.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {

             int contSemis = 0;
             int contFinal = 0;

             while (rs.next()) {
                 int ronda = rs.getInt("ronda");
                 String gamertag = rs.getString("gamertag");

                 // Si ganaron la ronda 1, van a las etiquetas de Semis
                 if (ronda == 1 && contSemis < 4) {
                     labelsSemis[contSemis].setText(" " + gamertag);
                     contSemis++;
                 } 
                 // Si ganaron la ronda 2, van a las etiquetas de la Final
                 else if (ronda == 2 && contFinal < 2) {
                     labelsFinal[contFinal].setText(" " + gamertag);
                     contFinal++;
                 }
             }
        } catch (java.sql.SQLException ex) {
             System.out.println("Error al cargar progreso del bracket: " + ex.getMessage());
        }
    }
    
}