package proyectoversus.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectoversus.modelo.Premio;
/**
 *
 * @author Alex
 */
public class PremioDAO {
    public void insertarPremio(Premio premio) { // Metodo para CREATE de premios nuevos
        String sql = "INSERT INTO premio (descripcion, posicion, id_torneo) VALUES(?,?,?)";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, premio.getDescripcion());
            
            // Manejo de nulos: si la posición es 0, la dejamos como NULL
            if (premio.getPosicion() == 0) {
                stmt.setNull(2, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(2, premio.getPosicion());
            }
            
            // Manejo de nulos para la llave foránea del torneo
            if (premio.getId_torneo() == 0) {
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, premio.getId_torneo());
            }
            
            stmt.execute();
            System.out.println("PREMIO AGREGADO A LA BASE DE DATOS SIN PROBLEMAS OuO b");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Premio> obtenerPremios() { // Metodo para LEER los datos de los premios
        List<Premio> Lista = new ArrayList<>();
        String sql = "SELECT * FROM premio";
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resSet = stmt.executeQuery(sql)) {
            
            while (resSet.next()) {
                Premio p = new Premio(
                    resSet.getInt("id_premio"),
                    resSet.getString("descripcion"),
                    resSet.getInt("posicion"),
                    resSet.getInt("id_torneo")
                );
                Lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Lista;
    }

    public void actualizarPremio(Premio premio) { // Metodo para ACTUALIZAR los datos de un premio
        String sql = "UPDATE premio SET descripcion=?, posicion=?, id_torneo=? WHERE id_premio=?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, premio.getDescripcion());
            
            // Manejo de nulos para actualizar posición
            if (premio.getPosicion() == 0) {
                stmt.setNull(2, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(2, premio.getPosicion());
            }
            
            // Manejo de nulos para actualizar torneo
            if (premio.getId_torneo() == 0) {
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, premio.getId_torneo());
            }
            
            stmt.setInt(4, premio.getId_premio());
            
            stmt.executeUpdate();
            System.out.println("Premio editado sin problemas d(OuO)");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPremio(int id) { // Metodo para BORRAR los datos del premio
        String sql = "DELETE FROM premio WHERE id_premio=?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Premio borrado exitosamente o7");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
