package proyectoversus.logica;

/**
 *
 * @author Alex
 */
public class SesionActual {
    // Variables estáticas
    public static int idUsuarioLogueado;
    public static int idJugadorLogueado; 
    public static String nombreUsuario;
    public static String rolUsuario;
    /**
     * Método útil para cuando el usuario presione Cerrar Sesión.
     */
    public static void limpiarSesion() {
        idUsuarioLogueado = 0;
        idJugadorLogueado = 0;
        nombreUsuario = null;
        rolUsuario = null;
    }
}
