package proyectoversus.modelo;

/**
 *
 * @author Alex
 */
public class Estadisticas {
    private int id_estadisticas;
    private int victorias;
    private int derrotas;
    private int id_jugador;
    private int id_equipo;
    private int id_juego;

    public Estadisticas() {
    }
    
    

    // Constructor de parametros externos sin ID
    public Estadisticas(int victorias, int derrotas, int id_jugador, int id_equipo, int id_juego) {
        this.victorias = victorias;
        this.derrotas = derrotas;
        this.id_jugador = id_jugador;
        this.id_equipo = id_equipo;
        this.id_juego = id_juego;
    }

    // Constructor con todos los parámetros (El que usas en el ResultSet de tu DAO) Con ID
    public Estadisticas(int id_estadisticas, int victorias, int derrotas, int id_jugador, int id_equipo, int id_juego) {
        this.id_estadisticas = id_estadisticas;
        this.victorias = victorias;
        this.derrotas = derrotas;
        this.id_jugador = id_jugador;
        this.id_equipo = id_equipo;
        this.id_juego = id_juego;
    }

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================

    public int getId_estadisticas() {
        return id_estadisticas;
    }

    public void setId_estadisticas(int id_estadisticas) {
        this.id_estadisticas = id_estadisticas;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public int getId_jugador() {
        return id_jugador;
    }

    public void setId_jugador(int id_jugador) {
        this.id_jugador = id_jugador;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public int getId_juego() {
        return id_juego;
    }

    public void setId_juego(int id_juego) {
        this.id_juego = id_juego;
    }

    // Sobrescritura de toString() para depurar en consola
    @Override
    public String toString() {
        return "Estadisticas{" +
                "id=" + id_estadisticas +
                ", victorias=" + victorias +
                ", derrotas=" + derrotas +
                ", id_jugador=" + id_jugador +
                ", id_equipo=" + id_equipo +
                ", id_juego=" + id_juego +
                '}';
    }

}
