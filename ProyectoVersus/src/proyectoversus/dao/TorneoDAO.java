package proyectoversus.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectoversus.modelo.Torneo;
/**
 *
 * @author Alex
 */
public class TorneoDAO {
    public void insertarTorneo(Torneo torneo) { // Metodo para CREATE de torneos nuevos
        String sql = "INSERT INTO torneo (nombre, estado, id_juego) VALUES(?,?,?)";
        
        try (Connection conn = ConexionDB.getConnection(); // O ConexionDB.getConnection()
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, torneo.getNombre());
            
            // Si el estado viene nulo o vacío, le asignamos el default de la base de datos
            if (torneo.getEstado() == null || torneo.getEstado().trim().isEmpty()) {
                stmt.setString(2, "ABIERTO");
            } else {
                stmt.setString(2, torneo.getEstado());
            }
            
            // Manejo de nulos para la llave foránea de juego
            if (torneo.getId_juego() == 0) {
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, torneo.getId_juego());
            }
            
            stmt.execute();
            System.out.println("TORNEO AGREGADO A LA BASE DE DATOS SIN PROBLEMAS OuO b");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Torneo> obtenerTorneos() { // Metodo para LEER los datos de los torneos
        List<Torneo> Lista = new ArrayList<>();
        String sql = "SELECT * FROM torneo";
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resSet = stmt.executeQuery(sql)) {
            
            while (resSet.next()) {
                Torneo t = new Torneo(
                    resSet.getInt("id_torneo"),
                    resSet.getString("nombre"),
                    resSet.getString("estado"),
                    resSet.getInt("id_juego")
                );
                Lista.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Lista;
    }

    public void actualizarTorneo(Torneo torneo) { // Metodo para ACTUALIZAR los datos de un torneo
        String sql = "UPDATE torneo SET nombre=?, estado=?, id_juego=? WHERE id_torneo=?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, torneo.getNombre());
            stmt.setString(2, torneo.getEstado());
            
            // Manejo de nulos
            if (torneo.getId_juego() == 0) {
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, torneo.getId_juego());
            }
            
            stmt.setInt(4, torneo.getId());
            
            stmt.executeUpdate();
            System.out.println("Torneo editado sin problemas d(OuO)");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarTorneo(int id) { // Metodo para BORRAR los datos del torneo
        String sql = "DELETE FROM torneo WHERE id_torneo=?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
            System.out.println("Torneo borrado exitosamente o7");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void cambiarEstado(int idTorneo, String nuevoEstado) {
        // Consultar SQL para actualizar solo la columna estado
        String sql = "UPDATE torneo SET estado=? WHERE id_torneo=?";
        
        // Uso de try para asegurar el cierre automático de recursos
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Inyectamos los parámetros en orden de los signos de interrogación '?'
            stmt.setString(1, nuevoEstado); // Primer '?' -> El nuevo estado (ABIERTO, EN_PROGRESO, FINALIZADO)
            stmt.setInt(2, idTorneo);       // Segundo '?' -> El ID del torneo a modificar
            
            // Ejecutamos la actualización en la BD
            stmt.executeUpdate();
            System.out.println("¡Estado del torneo #" + idTorneo + " cambiado a " + nuevoEstado + " con éxito! d(OuO b)");
            
        } catch (SQLException e) {
            System.err.println("Error al cambiar el estado del torneo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
