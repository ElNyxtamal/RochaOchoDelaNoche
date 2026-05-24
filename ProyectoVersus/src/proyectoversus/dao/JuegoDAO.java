package proyectoversus.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectoversus.modelo.Juego;
/**
 *
 * @author Alex
 */
public class JuegoDAO {
    public void insertarJuego(Juego juego) { // Metodo para CREATE de juegos nuevos
        String sql = "INSERT INTO juego (nombre, requiere_equipo) VALUES(?, ?)";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, juego.getNombre());
            stmt.setBoolean(2, juego.isRequiere_equipo()); // JDBC mapea el boolean a tinyint(1) automáticamente
            
            stmt.execute();
            System.out.println("JUEGO AGREGADO A LA BASE DE DATOS SIN PROBLEMAS OuO b");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Juego> obtenerJuegos() { // Metodo para LEER los datos de los juegos
        List<Juego> Lista = new ArrayList<>();
        String sql = "SELECT * FROM juego";
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resSet = stmt.executeQuery(sql)) {
            
            while (resSet.next()) {
                Juego j = new Juego(
                    resSet.getInt("id_juego"),
                    resSet.getString("nombre"),
                    resSet.getBoolean("requiere_equipo")
                );
                Lista.add(j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Lista;
    }

    public void actualizarJuego(Juego juego) { // Metodo para ACTUALIZAR los datos de un juego
        String sql = "UPDATE juego SET nombre=?, requiere_equipo=? WHERE id_juego=?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, juego.getNombre());
            stmt.setBoolean(2, juego.isRequiere_equipo());
            stmt.setInt(3, juego.getId_juego());
            
            stmt.executeUpdate();
            System.out.println("Juego editado sin problemas d(OuO)");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarJuego(int id) { // Metodo para BORRAR los datos del juego
        String sql = "DELETE FROM juego WHERE id_juego=?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
            System.out.println("Juego borrado exitosamente o7");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
