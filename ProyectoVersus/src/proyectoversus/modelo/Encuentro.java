package proyectoversus.modelo;

/**
 *
 * @author Alex
 */
public class Encuentro {
    private int id;
    private int ronda;
    private int id_torneo;
    private int id_inscripcion1;
    private int id_inscripcion2;
    private int id_ganador;
    private int id_encuentro_sig; // El campo para avanzar en el bracket

    // Constructor vacío
    public Encuentro() {
    }

    // Constructor con todos los parámetros (Para el ResultSet del DAO)
    public Encuentro(int id, int ronda, int id_torneo, int id_inscripcion1, int id_inscripcion2, int id_ganador, int id_encuentro_sig) {
        this.id = id;
        this.ronda = ronda;
        this.id_torneo = id_torneo;
        this.id_inscripcion1 = id_inscripcion1;
        this.id_inscripcion2 = id_inscripcion2;
        this.id_ganador = id_ganador;
        this.id_encuentro_sig = id_encuentro_sig;
    }

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRonda() { return ronda; }
    public void setRonda(int ronda) { this.ronda = ronda; }

    public int getId_torneo() { return id_torneo; }
    public void setId_torneo(int id_torneo) { this.id_torneo = id_torneo; }

    public int getId_inscripcion1() { return id_inscripcion1; }
    public void setId_inscripcion1(int id_inscripcion1) { this.id_inscripcion1 = id_inscripcion1; }

    public int getId_inscripcion2() { return id_inscripcion2; }
    public void setId_inscripcion2(int id_inscripcion2) { this.id_inscripcion2 = id_inscripcion2; }

    public int getId_ganador() { return id_ganador; }
    public void setId_ganador(int id_ganador) { this.id_ganador = id_ganador; }

    public int getId_encuentro_sig() { return id_encuentro_sig; }
    public void setId_encuentro_sig(int id_encuentro_sig) { this.id_encuentro_sig = id_encuentro_sig; }


    // Sobrescritura de toString()
    @Override
    public String toString() {
        return "Encuentro{" +
                "id=" + id +
                ", ronda=" + ronda +
                ", id_torneo=" + id_torneo +
                ", id_inscripcion1=" + id_inscripcion1 +
                ", id_inscripcion2=" + id_inscripcion2 +
                ", id_ganador=" + id_ganador +
                ", id_encuentro_sig=" + id_encuentro_sig +
                '}';
    }

}
