package repositorios;

import java.sql.Connection;

import java.sql.DriverManager;

public class ConectorBD {
    
public static Connection conexion;
    
    public static void conectar(){

        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver cargado");        
            try{
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/MaryArena","root","1DAW3_BBDD");
          
            System.out.println("Conexion establecida");
          
        }catch(Exception e){
            System.out.println("Error en la conexion");
        }
        }catch(Exception e){
            System.out.println("Error en el driver");
        }
    }
}