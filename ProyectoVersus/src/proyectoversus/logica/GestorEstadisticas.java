package proyectoversus.logica;
import java.util.List;
import proyectoversus.dao.EstadisticasDAO;
import proyectoversus.modelo.Estadisticas;
/**
 *
 * @author Alex
 */
public class GestorEstadisticas {
    private EstadisticasDAO stats;

    public GestorEstadisticas(EstadisticasDAO stats) {
        this.stats = stats;
    }
    
    private void statChange(int idParticipante, int idJuego, boolean esEquipo, boolean esVictoria) {
        // 1. Traemos TODA la lista de estadísticas
        List<Estadisticas> listaStats = stats.obtenerStats();
        Estadisticas statActual = null;
        // 2. Buscamos manualmente en la lista si este jugador/equipo ya tiene registro en este juego
        for (Estadisticas stat : listaStats) {
            if (stat.getId_juego() == idJuego) {
                // Verificamos si es equipo o jugador para comparar el ID correcto
                if (esEquipo && stat.getId_equipo() == idParticipante) {
                    statActual = stat;
                    break; // Lo encontramos, detenemos la búsqueda
                } else if (!esEquipo && stat.getId_jugador() == idParticipante) {
                    statActual = stat;
                    break; // Lo encontramos, detenemos la búsqueda
                }
            }
        }
        // 3. Tomamos la decisión: ¿Insertar o Actualizar?
        if (statActual == null) {
            // NO EXISTE: Es la primera vez que juega este juego. Creamos el registro.
            Estadisticas nuevaStat = new Estadisticas();
            nuevaStat.setId_juego(idJuego);
            
            if (esEquipo) {
                nuevaStat.setId_equipo(idParticipante);
                nuevaStat.setId_jugador(0); // Se manda 0 para que el DAO lo convierta a NULL
            } else {
                nuevaStat.setId_jugador(idParticipante);
                nuevaStat.setId_equipo(0); // Se manda 0 para que el DAO lo convierta a NULL
            }

            // Asignamos los puntos iniciales
            if (esVictoria) {
                nuevaStat.setVictorias(1);
                nuevaStat.setDerrotas(0);
            } else {
                nuevaStat.setVictorias(0);
                nuevaStat.setDerrotas(1);
            }

            // Usamos el método de inserción
            stats.insertarStats(nuevaStat);
        } else {
            // SÍ EXISTE: Solo le sumamos 1 al contador correspondiente
            if (esVictoria) {
                statActual.setVictorias(statActual.getVictorias() + 1);
            } else {
                statActual.setDerrotas(statActual.getDerrotas() + 1);
            }
            // Usamos el método de actualización
            stats.actualizarStats(statActual);
        }
    }
    //Analisis de quien gana y quien pierde port-encuentro, posterior a eso actualiza las estadisticas de los jugadores
    public void checkResults(int idGanador, int idPerdedor, int idJuego, boolean esEquipo) {
        
        // 1. Procesar al GANADOR
        statChange(idGanador, idJuego, esEquipo, true);

        // 2. Procesar al PERDEDOR
        statChange(idPerdedor, idJuego, esEquipo, false);
        
        System.out.println("Estadísticas actualizadas con éxito en el sistema.");
    }
}
