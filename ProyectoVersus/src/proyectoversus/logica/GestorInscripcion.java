package proyectoversus.logica;
import java.util.List;
import proyectoversus.dao.InscripcionDAO;
import proyectoversus.modelo.Inscripcion;
/**
 *
 * @author Alex
 */
public class GestorInscripcion {
    private InscripcionDAO inscrip;

    public GestorInscripcion() {
        this.inscrip = new InscripcionDAO();
    }
    
    public boolean inscCheck(int id_torneo, int id_participante, boolean esEquipo){
        //Checar duplicados
        if(yaInscrito(id_torneo, id_participante, esEquipo)){
            System.out.println("ERROR: El participante ya se encuentra inscrito en el torneo :(");
            return false;
        }
        //Si no hay dupes, se genera la nueva inscripcion sin problema
        Inscripcion nuevaInscripcion = new Inscripcion();
        nuevaInscripcion.setId_torneo(id_torneo);
        if (esEquipo) {
            nuevaInscripcion.setId_equipo(id_participante);
            nuevaInscripcion.setId_jugador(0); // 0 para que el DAO lo vuelva NULL
        } else {
            nuevaInscripcion.setId_jugador(id_participante);
            nuevaInscripcion.setId_equipo(0); // 0 para que el DAO lo vuelva NULL
        }
        
        // Lo aventamos a la base de datos
        inscrip.insertarInscripcion(nuevaInscripcion);
        System.out.println("ÉXITO: Participante inscrito correctamente.");
        return true;
    }
    
    public boolean yaInscrito(int id_torneo, int id_participante, boolean esEquipo){
        List<Inscripcion> listaInsc = inscrip.obtenerInscripciones();
        for(Inscripcion i : listaInsc){
            // Primero verificamos que estemos viendo el torneo correcto
            if (i.getId_torneo() == id_torneo) {
                // Luego verificamos si es el mismo equipo o el mismo jugador
                if (esEquipo && i.getId_equipo() == id_participante) {
                    return true; // Aca se captura, NO se puede inscribir 2 veces mi hermano.
                } else if (!esEquipo && i.getId_jugador() == id_participante) {
                    return true; // Aca se captura, NO se puede inscribir 2 veces mi hermano.
                }
            }
        }
        return false;
    }
}
