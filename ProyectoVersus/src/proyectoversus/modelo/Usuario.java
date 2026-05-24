package proyectoversus.modelo;

/**
 *
 * @author Alex
 */
public class Usuario {
    private int id;
    private String username;
    private String password;
    private String rol;
    private int id_jugador;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con todos los parámetros (Para el ResultSet del DAO)
    public Usuario(int id, String username, String password, String rol, int id_jugador) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.id_jugador = id_jugador;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getId_jugador() {
        return id_jugador;
    }

    public void setId_jugador(int id_jugador) {
        this.id_jugador = id_jugador;
    }

    // Sobrescritura de toString() d(OuO)
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='[PROTEGIDA]'" + // Es buena práctica no imprimir contraseñas en consola
                ", rol='" + rol + '\'' +
                ", id_jugador=" + id_jugador +
                '}';
    }

}
