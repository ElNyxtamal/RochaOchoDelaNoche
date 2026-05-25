package proyectoversus.dao;
import proyectoversus.dao.EncuentroDAO;
import proyectoversus.dao.JugadorDAO;
import proyectoversus.dao.TorneoDAO;
import proyectoversus.logica.GestorInscripcion;
import proyectoversus.logica.GestorTorneo;
import proyectoversus.modelo.Juego;
import proyectoversus.modelo.Torneo;
/**
 *
 * @author Alex
 */
public class MainTemp {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SIMULACIÓN DE TORNEO VERSUS ===");
        // 1. Instanciamos TODAS nuestras herramientas
        JuegoDAO juegoDAO = new JuegoDAO();
        TorneoDAO torneoDAO = new TorneoDAO();
        //JugadorDAO jugadorDAO = new JugadorDAO();
        GestorInscripcion aduana = new GestorInscripcion();
        GestorTorneo organizador = new GestorTorneo();

        System.out.println("\n--- FASE 0: PREPARANDO EL TERRENO (Juego y Torneo) ---");
        // OJO: Si corres el script por segunda vez, comenta esta Fase 0 para no crear torneos duplicados
        
        // Creamos el Juego (Asumiendo constructor: nombre, requiere_equipo)
        Juego ggStrive = new Juego("Guilty Gear Strive", false);
        juegoDAO.insertarJuego(ggStrive);
        System.out.println("Juego registrado en la BD.");

        // Creamos el Torneo (Asumiendo constructor: nombre, estado, id_juego)
        // Le pasamos ID Juego = 1, asumiendo que GG Strive fue el primer juego creado en tu BD limpia.
        Torneo torneoGG = new Torneo("Torneo Relámpago GG", "ABIERTO", 1);
        torneoDAO.insertarTorneo(torneoGG);
        System.out.println("Torneo ABIERTO y listo para recibir inscripciones.");

        // Como es una BD limpia, asumimos que este primer torneo recibió el ID 1
        int idTorneoPrueba = 1;
        System.out.println("\n--- FASE 1: Cambiando COMPETIDORES EN LA BD ---");
        // OJO: Igual que arriba, si ya los creaste en la BD, comenta este bloque.
        //Jugador j1 = new Jugador("El_Nyxtamal", "Alex", 0);
        //Jugador j2 = new Jugador("PoshoDK", "Juan", 0);
        //Jugador j3 = new Jugador("Min_KO", "Domingo", 0);
        //Jugador j4 = new Jugador("EverestDeidad", "Everest", 0);
        //Jugador j5 = new Jugador("Voltz", "Alancito", 0);
        //Jugador j6 = new Jugador("Puco_lol", "Pamela", 0);
        //j1.setId(2);
        //j2.setId(3);
        //j3.setId(4);
        //j4.setId(5);
        //j5.setId(6);
        //j6.setId(7);
        //jugadorDAO.actualizarJugador(j1);
        //jugadorDAO.actualizarJugador(j2);
        //jugadorDAO.actualizarJugador(j3);
        //jugadorDAO.actualizarJugador(j4);
        //jugadorDAO.actualizarJugador(j5);
        //jugadorDAO.actualizarJugador(j6);
        //System.out.println("Jugadores creados.");
        System.out.println("\n--- FASE 2: INSCRIPCIONES (La Aduana) ---");
        // Inscribimos a los 6 jugadores (asumiendo que sus IDs quedaron del 1 al 6)
        aduana.inscCheck(idTorneoPrueba, 2, false);
        aduana.inscCheck(idTorneoPrueba, 3, false);
        aduana.inscCheck(idTorneoPrueba, 4, false);
        aduana.inscCheck(idTorneoPrueba, 5, false);
        aduana.inscCheck(idTorneoPrueba, 6, false);
        aduana.inscCheck(idTorneoPrueba, 7, false);
        
        System.out.println("\nIntentando hacer trampa con un clon...");
        aduana.yaInscrito(idTorneoPrueba, 2, false);

        System.out.println("\n--- FASE 3: GENERACION DEL BRACKET ---");
        boolean exitoBracket = organizador.inicioTorneo(idTorneoPrueba);

        if (exitoBracket) {
            System.out.println("\n¡EL TORNEO HA SIDO GENERADO EXITOSAMENTE!");
            System.out.println("Ve a phpMyAdmin y revisa TODAS las tablas para ver la magia.");
        } else {
            System.out.println("\nAlgo fallo al generar el torneo.");
        }
    }
}
