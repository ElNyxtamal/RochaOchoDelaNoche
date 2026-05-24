package proyectoversus.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectoversus.modelo.Equipo;
/**
 *
 * @author Alex
 */
public class EquipoDAO {
    public void insertarEquipo(Equipo equipo) { // Metodo para CREATE de equipos nuevos
        String sql = "INSERT INTO equipo (nombre_equipo) VALUES(?)";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, equipo.getNombre_equipo());
            
            stmt.execute();
            System.out.println("EQUIPO AGREGADO A LA BASE DE DATOS SIN PROBLEMAS OuO b");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Equipo> obtenerEquipos() { // Metodo para LEER los datos de los equipos
        List<Equipo> Lista = new ArrayList<>();
        String sql = "SELECT * FROM equipo";
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resSet = stmt.executeQuery(sql)) {
            
            while (resSet.next()) {
                Equipo eq = new Equipo(
                    resSet.getInt("id_equipo"),
                    resSet.getString("nombre_equipo")
                );
                Lista.add(eq);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Lista;
    }

    public void actualizarEquipo(Equipo equipo) { // Metodo para ACTUALIZAR los datos de un equipo
        String sql = "UPDATE equipo SET nombre_equipo=? WHERE id_equipo=?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, equipo.getNombre_equipo());
            stmt.setInt(2, equipo.getId());
            
            stmt.executeUpdate();
            System.out.println("Equipo editado sin problemas d(OuO)");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarEquipo(int id) { // Metodo para BORRAR los datos del equipo
        String sql = "DELETE FROM equipo WHERE id_equipo=?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
            System.out.println("Equipo borrado exitosamente o7");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
