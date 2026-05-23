/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoversus.dao;

import java.util.List;
import proyectoversus.modelo.Jugador;

/**
 *
 * @author Alex
 */
public class MainTemp {
    public static void main(String[] args) {
        // Instanciamos el DAO
        JugadorDAO dao = new JugadorDAO();

        System.out.println("--- PRUEBA 1: CREATE ---");
        // Nota: Asumo que tu constructor es (gamertag, nombre, id_equipo) sin el ID porque es Autoincremental
        // Le pasamos 0 al equipo simulando que entra como jugador individual
        //Jugador nuevoJugador = new Jugador("PoshoDK", "Juan Carlos", 0); 
        //dao.insertarJugador(nuevoJugador);

        System.out.println("\n--- PRUEBA 3: UPDATE ---");
        //Asumiendo que el jugador que acabamos de crear se le asignó el ID 1 en la base de datos
        //Jugador jugadorEditado = new Jugador("PoshoPilin", "Edit Invencible", 0);
        //jugadorEditado.setId(1); // Importante setear el ID para el WHERE
        //dao.actualizarJugador(jugadorEditado);
        
        System.out.println("\n--- PRUEBA 2: READ ---");
        //List<Jugador> lista = dao.obtenerJugador();
        //for (Jugador j : lista) {
            //System.out.println("ID: " + j.getId() + " | Gamertag: " + j.getGamertag() + " | Nombre: " + j.getNombre());
        //}

        System.out.println("\n--- PRUEBA 4: DELETE ---");
        // Descomenta la siguiente línea para probar si lo borra correctamente
        //dao.eliminarJugador(1); 
        //System.out.println("Pruebas finalizadas.");
    }
    
}
