package proyectoversus.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectoversus.modelo.Estadisticas;
/**
 *
 * @author Alex
 */
public class EstadisticasDAO {
    public void insertarStats(Estadisticas estadistica){ // Metodo para CREATE de estadísticas nuevas
        String sql = "INSERT INTO estadisticas (victorias, derrotas, id_jugador, id_equipo, id_juego) VALUES(?,?,?,?,?)";
        
        try(Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setInt(1, estadistica.getVictorias());
            stmt.setInt(2, estadistica.getDerrotas());
            
            // Manejo de nulos para las llaves foráneas
            if (estadistica.getId_jugador() == 0) { 
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, estadistica.getId_jugador());
            }
            
            if (estadistica.getId_equipo() == 0) { 
                stmt.setNull(4, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(4, estadistica.getId_equipo());
            }
            
            if (estadistica.getId_juego() == 0) { 
                stmt.setNull(5, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(5, estadistica.getId_juego());
            }
            
            stmt.execute();
            System.out.println("ESTADÍSTICA AGREGADA A LA BASE DE DATOS SIN PROBLEMAS OuO b");
            
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Estadisticas> obtenerStats(){ // Metodo para LEER los datos de las estadísticas
        List<Estadisticas> Lista = new ArrayList<>();
        String sql = "SELECT * FROM estadisticas";
        
        try(Connection conn = ConexionDB.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet resSet = stmt.executeQuery(sql)){
            
            while(resSet.next()){
                Estadisticas est = new Estadisticas(
                    resSet.getInt("id_estadisticas"),
                    resSet.getInt("victorias"),
                    resSet.getInt("derrotas"),
                    resSet.getInt("id_jugador"),
                    resSet.getInt("id_equipo"),
                    resSet.getInt("id_juego")
                );
                Lista.add(est);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        
        return Lista;
    }

    public void actualizarStats(Estadisticas estadistica){ // Metodo para ACTUALIZAR los datos
        String sql = "UPDATE estadisticas SET victorias=?, derrotas=?, id_jugador=?, id_equipo=?, id_juego=? WHERE id_estadisticas=?";
        
        try(Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setInt(1, estadistica.getVictorias());
            stmt.setInt(2, estadistica.getDerrotas());
            
            // Manejo de nulos para las llaves foráneas
            if (estadistica.getId_jugador() == 0) { 
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, estadistica.getId_jugador());
            }
            
            if (estadistica.getId_equipo() == 0) { 
                stmt.setNull(4, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(4, estadistica.getId_equipo());
            }
            
            if (estadistica.getId_juego() == 0) { 
                stmt.setNull(5, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(5, estadistica.getId_juego());
            }
            
            stmt.setInt(6, estadistica.getId_estadisticas());
            stmt.executeUpdate();
            
            System.out.println("Estadística editada sin problemas d(OuO)");
            
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void eliminarStats(int id){ // Metodo para BORRAR los datos
        String sql = "DELETE FROM estadisticas WHERE id_estadisticas=?";
        
        try(Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
            System.out.println("Borrado exitoso o7");
            
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

}
