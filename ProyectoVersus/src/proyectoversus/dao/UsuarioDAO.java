package proyectoversus.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectoversus.modelo.Usuario;
/**
 *
 * @author Alex
 */
public class UsuarioDAO {
    public void insertarUsuario(Usuario usuario) { // Metodo para CREATE de usuarios nuevos
        String sql = "INSERT INTO usuario (username, password, rol, id_jugador) VALUES(?,?,?,?)";
        
        try (Connection conn = ConexionDB.getConnection(); // O ConexionDB.getConnection()
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword()); // Asumiendo que ya viene hasheada desde tu lógica de negocio
            
            // Si el rol viene nulo o vacío, le asignamos el default de la base de datos
            if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()) {
                stmt.setString(3, "JUGADOR");
            } else {
                stmt.setString(3, usuario.getRol());
            }
            
            // Manejo de nulos para la llave foránea (Si es un Admin, puede no tener id_jugador)
            if (usuario.getId_jugador() == 0) {
                stmt.setNull(4, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(4, usuario.getId_jugador());
            }
            
            stmt.execute();
            System.out.println("USUARIO AGREGADO A LA BASE DE DATOS SIN PROBLEMAS OuO b");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> obtenerUsuarios() { // Metodo para LEER los datos de los usuarios
        List<Usuario> Lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resSet = stmt.executeQuery(sql)) {
            
            while (resSet.next()) {
                Usuario u = new Usuario(
                    resSet.getInt("id_usuario"),
                    resSet.getString("username"),
                    resSet.getString("password"),
                    resSet.getString("rol"),
                    resSet.getInt("id_jugador")
                );
                Lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Lista;
    }

    public void actualizarUsuario(Usuario usuario) { // Metodo para ACTUALIZAR los datos de un usuario
        String sql = "UPDATE usuario SET username=?, password=?, rol=?, id_jugador=? WHERE id_usuario=?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getRol());
            
            // Manejo de nulos
            if (usuario.getId_jugador() == 0) {
                stmt.setNull(4, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(4, usuario.getId_jugador());
            }
            
            stmt.setInt(5, usuario.getId());
            
            stmt.executeUpdate();
            System.out.println("Usuario editado sin problemas d(OuO)");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarUsuario(int id) { // Metodo para BORRAR los datos del usuario
        String sql = "DELETE FROM usuario WHERE id_usuario=?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
            System.out.println("Usuario borrado exitosamente o7");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
