package utilidades;

import java.util.Scanner;

import modelo.Usuario;

public class GestionDatos {

	private static Scanner sc = new Scanner(System.in);

    public static Usuario pedirDatosUsuario() {

        sc.nextLine(); 
        System.out.println("Introduce el DNI de Usuario");
        String dni = sc.nextLine();
        
        System.out.println("Introduce el nombre de Usuario");
        String nombre = sc.nextLine();

        System.out.println("Introduce el apellido de Usuario");
        String apellido = sc.nextLine();

        System.out.println("Introduce la contraseña de Usuario");
        String contraseña = sc.nextLine();
        
        System.out.println("Introduce el rol del Usuario (Cliente / Administrador)"); //de momento es string en vez de enum, porque tenemos problemas con el enum
        String rol = sc.nextLine();
        
        Usuario usuario = new Usuario(dni, nombre, apellido, contraseña, rol); 

        return usuario;
    }


    public static int guardarOpcion() {
        int eleccion = sc.nextInt();
        return eleccion;
    }

}