package utilidades;

import java.util.Scanner;

import modelo.Usuario;

public class GestionDatos {
	
	private static Scanner sc =new Scanner(System.in);
	
	public static void pedirDato(String dato) {
		dato = sc.nextLine();
		return.dato;
	}
	
	public static Usuario pedirDatosUsuario() {
		
		System.out.println("Introduce el DNI de Usuario");
		String dni=sc.nextLine();
	
		System.out.println("Introduce el nombre de Usuario");
		String nombre=sc.nextLine();
	
		System.out.println("Introduce el apellido de Usuario");
		String apellido=sc.nextLine();
		
		System.out.println("Introduce la contraseña de Usuario");
		String contraseña=sc.nextLine();
		
		System.out.println("Introduce el rol del Usuario (Cliente / Administrador)");
		String rol=sc.nextLine();
		
		Usuario usuario=new Usuario();
		return usuario;
		

	}
}
