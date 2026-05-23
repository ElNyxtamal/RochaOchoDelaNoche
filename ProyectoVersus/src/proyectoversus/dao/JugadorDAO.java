package proyectoversus.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectoversus.modelo.Jugador;
public class JugadorDAO {
    public void insertarJugador(Jugador jugador){ //Metodo para CREATE de jugadores nuevos
        String sql = "INSERT INTO jugador (gamertag, nombre, id_equipo) VALUES(?,?,?)";
        try(Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, jugador.getGamertag());
            stmt.setString(2, jugador.getNombre());
            if (jugador.getId_equipo() == 0) { 
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, jugador.getId_equipo());
            }
            stmt.execute();
            System.out.println("SE HA AGREGADO A LA BASE DE DATOS SIN PROBLEMAS OuO b");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public List<Jugador> obtenerJugador(){ //Metodo para LEER los datos del jugador
        List <Jugador> Lista = new ArrayList<>();
        String sql = "SELECT * FROM jugador";
        try(Connection conn = ConexionDB.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet resSet = stmt.executeQuery(sql)){
            while(resSet.next()){
                Jugador j = new Jugador(resSet.getInt("id_jugador"),
                resSet.getString("gamertag"),
                resSet.getString("nombre"),
                resSet.getInt("id_equipo")
                );
                Lista.add(j);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return Lista;
    }
    public void actualizarJugador(Jugador jugador){ //Metodo para ACTUALIZAR los datos de un jugador
        String sql = "UPDATE jugador SET gamertag=?, nombre=?, id_equipo=? WHERE id_jugador=?";
        try(Connection conn = ConexionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, jugador.getGamertag());
            stmt.setString(2, jugador.getNombre());
            if (jugador.getId_equipo() == 0) { 
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, jugador.getId_equipo());
            }
            stmt.setInt(4, jugador.getId());
            stmt.executeUpdate();
            System.out.println("Alumno editado sin problemas d(OuO)");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void eliminarJugador(int id){ //Metodo para BORRAR los datos del jugador
        String sql = "DELETE FROM jugador WHERE id_jugador=?";
        try(Connection conn = ConexionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Borrado existoso o7");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
