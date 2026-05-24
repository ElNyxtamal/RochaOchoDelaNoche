package proyectoversus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // Configuración de los datos de la BD en XAMPP
    private static final String DATABASE = "torneo_db";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE + "?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";      // Usuario por defecto de XAMPP
    private static final String PASSWORD = "";      // Contraseña por defecto (vacía)

    public static Connection getConexion() {
        Connection conexion = null;
        try {
            // 1. Registrar el Driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Establecer la conexión
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa a la base de datos '" + DATABASE + "'!");
            
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el Driver de MySQL. Asegúrate de agregar el archivo JAR.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error: No se pudo conectar a la base de datos. Verifica que XAMPP esté activo.");
            e.printStackTrace();
        }
        return conexion;
    }

    // Método de prueba rápido
    public static void main(String[] args) {
        // Ejecuta esta clase directamente para probar el puente
        getConexion();
    }
}