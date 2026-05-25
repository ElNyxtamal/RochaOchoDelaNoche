package proyectoversus.modelo;

/**
 *
 * @author Alex
 */
public class Torneo {
    private int id;
    private String nombre;
    private String estado;
    private int id_juego;

    // Constructor vacío
    public Torneo() {
    }

    // Constructor con todos los parámetros (Para el ResultSet del DAO)
    public Torneo(int id, String nombre, String estado, int id_juego) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.id_juego = id_juego;
    }

    public Torneo(String nombre, String estado, int id_juego) {
        this.nombre = nombre;
        this.estado = estado;
        this.id_juego = id_juego;
    }

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_juego() {
        return id_juego;
    }

    public void setId_juego(int id_juego) {
        this.id_juego = id_juego;
    }

    // Sobrescritura de toString() d(OuO)
    @Override
    public String toString() {
        return "Torneo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", id_juego=" + id_juego +
                '}';
    }

}
