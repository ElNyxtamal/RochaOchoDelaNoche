package proyectoversus.modelo;

/**
 *
 * @author Alex
 */
public class Jugador { //Clase que consigue los datos de los jugadores
    private int id; //el id unico del jugador
    private String gamertag; //el gamertag, tambien es unico
    private String nombre; //el nombre propio del jugador
    private int id_equipo; //una FK que solo se usa si el jugador pertenece a un equipo
    //Con ID
    public Jugador(int id_jugador, String gamertag, String nombre, int id_equipo) { //Constructor
        this.id = id_jugador;
        this.gamertag = gamertag;
        this.nombre = nombre;
        this.id_equipo = id_equipo;
    }
    //Sin ID
    public Jugador(String gamertag, String nombre, int id_equipo) { //Constructor
        this.gamertag = gamertag;
        this.nombre = nombre;
        this.id_equipo = id_equipo;
    }
    
    //Getters y setters de ID
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    //Getters y setters de Gamertag
    public String getGamertag() {
        return gamertag;
    }

    public void setGamertag(String gamertag) {
        this.gamertag = gamertag;
    }
    //Getters y setters de Nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    //Getters y setters de Id Equipo
    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }
}
