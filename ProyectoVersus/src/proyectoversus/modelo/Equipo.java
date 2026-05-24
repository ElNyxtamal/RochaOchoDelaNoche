package proyectoversus.modelo;

/**
 *
 * @author Alex
 */
public class Equipo {
    private int id;
    private String nombre_equipo;

    // Constructor vacío
    public Equipo() {
    }

    // Constructor con todos los parámetros (Para el ResultSet del DAO)
    public Equipo(int id, String nombre_equipo) {
        this.id = id;
        this.nombre_equipo = nombre_equipo;
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

    public String getNombre_equipo() {
        return nombre_equipo;
    }

    public void setNombre_equipo(String nombre_equipo) {
        this.nombre_equipo = nombre_equipo;
    }

    // Sobrescritura de toString() d(OuO)
    @Override
    public String toString() {
        return "Equipo{" +
                "id=" + id +
                ", nombre_equipo='" + nombre_equipo + '\'' +
                '}';
    }

}
