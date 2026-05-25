package proyectoversus.modelo;

/**
 *
 * @author Alex
 */
public class Juego {
    private int id_juego;
    private String nombre;
    private boolean requiere_equipo; // Usamos boolean para el tinyint(1)

    // Constructor vacío
    public Juego() {
    }

    // Constructor con todos los parámetros (Para el ResultSet del DAO)
    public Juego(int id, String nombre, boolean requiere_equipo) {
        this.id_juego = id;
        this.nombre = nombre;
        this.requiere_equipo = requiere_equipo;
    }

    public Juego(String nombre, boolean requiere_equipo) {
        this.nombre = nombre;
        this.requiere_equipo = requiere_equipo;
    }
    
    

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================

    public int getId_juego() {
        return id_juego;
    }

    public void setId_juego(int id_juego) {
        this.id_juego = id_juego;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Para los booleanos, el "get" suele llamarse "is" por convención
    public boolean isRequiere_equipo() {
        return requiere_equipo;
    }

    public void setRequiere_equipo(boolean requiere_equipo) {
        this.requiere_equipo = requiere_equipo;
    }

    // Sobrescritura de toString() d(OuO)
    @Override
    public String toString() {
        return "Juego{" +
                "id=" + id_juego +
                ", nombre='" + nombre + '\'' +
                ", requiere_equipo=" + requiere_equipo +
                '}';
    }

}
