package proyectoversus.modelo;
import java.sql.Timestamp;
/**
 *
 * @author Alex
 */
public class Inscripcion {
    private int id_inscripcion;
    private int id_jugador;
    private int id_equipo;
    private int id_torneo;
    private Timestamp fecha_inscripcion; // Usamos Timestamp para manejar la fecha y hora exacta

    // Constructor vacío
    public Inscripcion() {
    }

    // Constructor con todos los parámetros (Para el ResultSet del DAO)
    public Inscripcion(int id, int id_jugador, int id_equipo, int id_torneo, Timestamp fecha_inscripcion) {
        this.id_inscripcion = id;
        this.id_jugador = id_jugador;
        this.id_equipo = id_equipo;
        this.id_torneo = id_torneo;
        this.fecha_inscripcion = fecha_inscripcion;
    }

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================

    public int getId_inscripcion() { return id_inscripcion; }
    public void setId_inscripcion(int id_inscripcion) { this.id_inscripcion = id_inscripcion; }

    public int getId_jugador() { return id_jugador; }
    public void setId_jugador(int id_jugador) { this.id_jugador = id_jugador; }

    public int getId_equipo() { return id_equipo; }
    public void setId_equipo(int id_equipo) { this.id_equipo = id_equipo; }

    public int getId_torneo() { return id_torneo; }
    public void setId_torneo(int id_torneo) { this.id_torneo = id_torneo; }

    public Timestamp getFecha_inscripcion() { return fecha_inscripcion; }
    public void setFecha_inscripcion(Timestamp fecha_inscripcion) { this.fecha_inscripcion = fecha_inscripcion; }

    // Sobrescritura de toString()
    @Override
    public String toString() {
        return "Inscripcion{" +
                "id=" + id_inscripcion +
                ", id_jugador=" + id_jugador +
                ", id_equipo=" + id_equipo +
                ", id_torneo=" + id_torneo +
                ", fecha_inscripcion=" + fecha_inscripcion +
                '}';
    }

}
