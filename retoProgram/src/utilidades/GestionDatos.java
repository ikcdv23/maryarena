package utilidades;

import java.util.Scanner;

import modelo.Usuario;
import modelo.rolUsuario;
import view.Menu;

public class GestionDatos {

	private static Scanner sc = new Scanner(System.in);

	public static Usuario pedirDatosUsuario() {

		sc.nextLine(); // Limpiar el buffer
		System.out.println("Introduce el DNI de Usuario");
		String dni = sc.nextLine();

		System.out.println("Introduce el nombre de Usuario");
		String nombre = sc.nextLine();

		System.out.println("Introduce el apellido de Usuario");
		String apellido = sc.nextLine();

		System.out.println("Introduce la contraseña de Usuario");
		String contraseña = sc.nextLine();
		
		System.out.println("Introduce el rol del Usuario");
		String rol = sc.nextLine();
		
		Usuario usuario = new Usuario();

		return usuario;
	}

	public static int inicio() {
		int eleccion = sc.nextInt();
		return eleccion;
	}

}
