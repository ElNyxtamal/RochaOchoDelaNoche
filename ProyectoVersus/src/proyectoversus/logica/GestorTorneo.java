package proyectoversus.logica;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import proyectoversus.modelo.Encuentro;
import proyectoversus.dao.EncuentroDAO;
import proyectoversus.dao.InscripcionDAO;
import proyectoversus.modelo.Inscripcion;
import proyectoversus.dao.TorneoDAO;

/**
 *
 * @author Alex
 */
public class GestorTorneo { //Esta es la clase mas importante del proyecto porque maneja varias cosas con ayuda de otros metodos
    private InscripcionDAO inscripcionDAO;
    private EncuentroDAO encuentroDAO;
    private TorneoDAO torneoDAO;
   
    public GestorTorneo() {
        this.inscripcionDAO = new InscripcionDAO();
        this.encuentroDAO = new EncuentroDAO();
        this.torneoDAO = new TorneoDAO();
    }
    
    public boolean inicioTorneo(int idTorneo){
        List<Inscripcion> inscritos = inscripcionDAO.torneoBuscar(idTorneo);
        int p = inscritos.size();
        if(p < 2){
            System.out.println("ERROR: Es peligroso ir solo! Se necesitan al menos 2 jugadores para hacer el torneo");
            return false;
        }
        //1. Este es el sisntema de los Brackets (usando algo estadistico que encontre en internet que se llama Byes xd)
        // Se calcula cuantos jugadores hay registrados, si no estan en una potencia de 2 se calcula la proxima potencia mas cercana
        // Los "sobrantes" pasan a la siguiente ronda directamente y los que entran en la potencia se enfrentan
        int n = 1;
        int totalRondas = 0;
        while(n < p){
            n *= 2;
            totalRondas++;
        }
        int byes = n - p;
        // 2. Revolver a los jugadores reales primero para la aleatoriedad
        Collections.shuffle(inscritos);
        List<Integer> idRSiguiente = new ArrayList<>();
        // 3. Rellenar con "Fantasmas" (Byes) hasta alcanzar la potencia perfecta (n)
        while (inscritos.size() < n) {
            Inscripcion fantasma = new Inscripcion();
            fantasma.setId_inscripcion(0); // El 0 se convertirá en NULL en tu DAO
            inscritos.add(fantasma);
        }
        for(int rondaActual = totalRondas; rondaActual >= 1; rondaActual--){
            List<Integer> idRActual = new ArrayList<>();
            int juegosRonda = (int)Math.pow(2, totalRondas-rondaActual);
            for(int i = 0; i < juegosRonda; i++){
                Encuentro versus = new Encuentro();
                versus.setId_torneo(idTorneo);
                versus.setRonda(rondaActual);
                if(rondaActual < totalRondas){
                    int indexSig = i/2;
                    versus.setId_encuentro_sig(idRSiguiente.get(indexSig));
                }
                //aca se define quien pelea con quien
                // --- ASIGNACIÓN DE JUGADORES ---
                if (rondaActual == 1) {
                    // Solo asignamos en la Ronda 1. Metemos a todos (reales y fantasmas).
                    versus.setId_inscripcion1(inscritos.remove(0).getId_inscripcion());
                    versus.setId_inscripcion2(inscritos.remove(0).getId_inscripcion());
                }
                //Insertar en la base de Datos
                int idCreado = encuentroDAO.insertarEncuentro(versus);
                idRActual.add(idCreado);
            }
            idRSiguiente = idRActual;
        }
        torneoDAO.cambiarEstado(idTorneo, "EN_PROGRESO");
        System.out.println("Torneo Generado sin problemas (UvU)b");
        return true;
    }
}
