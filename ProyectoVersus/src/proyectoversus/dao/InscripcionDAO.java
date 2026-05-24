package proyectoversus.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectoversus.modelo.Inscripcion;
/**
 *
 * @author Alex
 */
public class InscripcionDAO {
    public void insertarInscripcion(Inscripcion inscripcion) { // Metodo para CREATE de inscripciones nuevas
        // Omitimos fecha_inscripcion en el INSERT para que MySQL ponga el current_timestamp() solito
        String sql = "INSERT INTO inscripcion (id_jugador, id_equipo, id_torneo) VALUES(?,?,?)";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Manejo de nulos para las llaves foráneas
            if (inscripcion.getId_jugador() == 0) stmt.setNull(1, java.sql.Types.INTEGER);
            else stmt.setInt(1, inscripcion.getId_jugador());
            
            if (inscripcion.getId_equipo() == 0) stmt.setNull(2, java.sql.Types.INTEGER);
            else stmt.setInt(2, inscripcion.getId_equipo());
            
            if (inscripcion.getId_torneo() == 0) stmt.setNull(3, java.sql.Types.INTEGER);
            else stmt.setInt(3, inscripcion.getId_torneo());
            
            stmt.execute();
            System.out.println("INSCRIPCIÓN AGREGADA A LA BASE DE DATOS SIN PROBLEMAS OuO b");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Inscripcion> obtenerInscripciones() { // Metodo para LEER los datos
        List<Inscripcion> Lista = new ArrayList<>();
        String sql = "SELECT * FROM inscripcion";
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resSet = stmt.executeQuery(sql)) {
            
            while (resSet.next()) {
                Inscripcion ins = new Inscripcion(
                    resSet.getInt("id_inscripcion"),
                    resSet.getInt("id_jugador"),
                    resSet.getInt("id_equipo"),
                    resSet.getInt("id_torneo"),
                    resSet.getTimestamp("fecha_inscripcion") // Aquí leemos la fecha que generó MySQL
                );
                Lista.add(ins);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Lista;
    }

    public void actualizarInscripcion(Inscripcion inscripcion) { // Metodo para ACTUALIZAR los datos
        // Omitimos la fecha aquí también, usualmente no quieres alterar la fecha de registro original
        String sql = "UPDATE inscripcion SET id_jugador=?, id_equipo=?, id_torneo=? WHERE id_inscripcion=?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Manejo de nulos
            if (inscripcion.getId_jugador() == 0) stmt.setNull(1, java.sql.Types.INTEGER);
            else stmt.setInt(1, inscripcion.getId_jugador());
            
            if (inscripcion.getId_equipo() == 0) stmt.setNull(2, java.sql.Types.INTEGER);
            else stmt.setInt(2, inscripcion.getId_equipo());
            
            if (inscripcion.getId_torneo() == 0) stmt.setNull(3, java.sql.Types.INTEGER);
            else stmt.setInt(3, inscripcion.getId_torneo());
            
            stmt.setInt(4, inscripcion.getId_inscripcion()); // El id para el WHERE
            
            stmt.executeUpdate();
            System.out.println("Inscripción editada sin problemas d(OuO)");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarInscripcion(int id) { // Metodo para BORRAR los datos
        String sql = "DELETE FROM inscripcion WHERE id_inscripcion=?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
            System.out.println("Inscripción borrada exitosamente o7");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
