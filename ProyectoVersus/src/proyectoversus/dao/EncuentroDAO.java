package proyectoversus.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectoversus.modelo.Encuentro;
/**
 *
 * @author Alex
 */
public class EncuentroDAO {
    public int insertarEncuentro(Encuentro encuentro) { // CREATE
        String sql = "INSERT INTO encuentro (ronda, id_torneo, id_inscripcion1, id_inscripcion2, id_ganador, id_encuentro_sig) VALUES(?,?,?,?,?,?)";
        int idNuevo = 0;
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, encuentro.getRonda());
            
            // Manejo de nulos para las llaves foráneas
            if (encuentro.getId_torneo() == 0) stmt.setNull(2, java.sql.Types.INTEGER);
            else stmt.setInt(2, encuentro.getId_torneo());
            
            if (encuentro.getId_inscripcion1() == 0) stmt.setNull(3, java.sql.Types.INTEGER);
            else stmt.setInt(3, encuentro.getId_inscripcion1());
            
            if (encuentro.getId_inscripcion2() == 0) stmt.setNull(4, java.sql.Types.INTEGER);
            else stmt.setInt(4, encuentro.getId_inscripcion2());
            
            if (encuentro.getId_ganador() == 0) stmt.setNull(5, java.sql.Types.INTEGER);
            else stmt.setInt(5, encuentro.getId_ganador());

            if (encuentro.getId_encuentro_sig() == 0) stmt.setNull(6, java.sql.Types.INTEGER);
            else stmt.setInt(6, encuentro.getId_encuentro_sig());
            
            stmt.execute();
            try (ResultSet resSet = stmt.getGeneratedKeys()) {
                if (resSet.next()) {
                    idNuevo = resSet.getInt(1); // Saca el ID de la primera columna del resultado
                }
            }
            System.out.println("ENCUENTRO AGREGADO A LA BASE DE DATOS SIN PROBLEMAS OuO b");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idNuevo;
    }

    public List<Encuentro> obtenerEncuentros() { // READ
        List<Encuentro> Lista = new ArrayList<>();
        String sql = "SELECT * FROM encuentro";
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resSet = stmt.executeQuery(sql)) {
            
            while (resSet.next()) {
                Encuentro enc = new Encuentro(
                    resSet.getInt("id_encuentro"),
                    resSet.getInt("ronda"),
                    resSet.getInt("id_torneo"),
                    resSet.getInt("id_inscripcion1"),
                    resSet.getInt("id_inscripcion2"),
                    resSet.getInt("id_ganador"),
                    resSet.getInt("id_encuentro_sig")
                );
                Lista.add(enc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Lista;
    }

    public void actualizarEncuentro(Encuentro encuentro) { // UPDATE
        String sql = "UPDATE encuentro SET ronda=?, id_torneo=?, id_inscripcion1=?, id_inscripcion2=?, id_ganador=?, id_encuentro_sig=? WHERE id_encuentro=?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, encuentro.getRonda());
            
            // Manejo de nulos
            if (encuentro.getId_torneo() == 0) stmt.setNull(2, java.sql.Types.INTEGER);
            else stmt.setInt(2, encuentro.getId_torneo());
            
            if (encuentro.getId_inscripcion1() == 0) stmt.setNull(3, java.sql.Types.INTEGER);
            else stmt.setInt(3, encuentro.getId_inscripcion1());
            
            if (encuentro.getId_inscripcion2() == 0) stmt.setNull(4, java.sql.Types.INTEGER);
            else stmt.setInt(4, encuentro.getId_inscripcion2());
            
            if (encuentro.getId_ganador() == 0) stmt.setNull(5, java.sql.Types.INTEGER);
            else stmt.setInt(5, encuentro.getId_ganador());

            if (encuentro.getId_encuentro_sig() == 0) stmt.setNull(6, java.sql.Types.INTEGER);
            else stmt.setInt(6, encuentro.getId_encuentro_sig());
            
            stmt.setInt(7, encuentro.getId()); // El WHERE id_encuentro=?
            stmt.executeUpdate();
            
            System.out.println("Encuentro editado sin problemas d(OuO)");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void eliminarEncuentro(int id) { // DELETE
        String sql = "DELETE FROM encuentro WHERE id_encuentro=?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
            System.out.println("Encuentro borrado exitosamente o7");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
