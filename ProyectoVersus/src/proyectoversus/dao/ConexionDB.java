package proyectoversus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/torneo_db";
    private static final String USER = "root";//USUARIO.
    private static final String PASSWORD = "";//CONTRASEÑA.
    
    public static Connection getConnection(){
        Connection conn = null;
        try{
            // 1. Establecer conexion
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("LA CONEXION FUE TODO UN EXITO");
        }
        catch(SQLException e){
            //2. Revisar si hay errores al conectar
            System.out.println("ERROR"+e.getMessage());
        }
        return conn;
    }
}
