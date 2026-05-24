package proyectoversus.modelo;

/**
 *
 * @author Alex
 */
public class Premio {
    private int id_premio;
    private String descripcion;
    private int posicion;
    private int id_torneo;

    // Constructor vacío
    public Premio() {
    }

    // Constructor con todos los parámetros (Para el ResultSet del DAO)
    public Premio(int id, String descripcion, int posicion, int id_torneo) {
        this.id_premio = id;
        this.descripcion = descripcion;
        this.posicion = posicion;
        this.id_torneo = id_torneo;
    }
    
    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================

    public int getId_premio() {
        return id_premio;
    }

    public void setId_premio(int id_premio) {
        this.id_premio = id_premio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getId_torneo() {
        return id_torneo;
    }

    public void setId_torneo(int id_torneo) {
        this.id_torneo = id_torneo;
    }

    // Sobrescritura de toString() d(OuO)
    @Override
    public String toString() {
        return "Premio{" +
                "id=" + id_premio +
                ", descripcion='" + descripcion + '\'' +
                ", posicion=" + posicion +
                ", id_torneo=" + id_torneo +
                '}';
    }

}
