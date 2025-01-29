package utilidades;

import java.util.Scanner;

import modelo.Usuario;

public class GestionDatos {

	private static Scanner sc = new Scanner(System.in);

	public static Usuario pedirDatosUsuario() {

		sc.nextLine(); // validador para que el DN
		int counterDNI = 0;
		String dni;
		do {
			System.out.println("Introduce el DNI de Usuario");
			dni = sc.nextLine();
			if (dni.length() < 9) {
				System.out.println("El DNI no puede tener menos de 9 caracteres ");
			} else {
				counterDNI++;
			}
		} while (counterDNI != 1);

		System.out.println("Introduce el nombre de Usuario");
		String nombre = sc.nextLine();

		System.out.println("Introduce el apellido de Usuario");
		String apellido = sc.nextLine();

		System.out.println("Introduce la contraseña de Usuario");
		String contraseña = sc.nextLine();
		int counterRol = 0;
		String rol;
		do {
			System.out.println("Introduce el rol del Usuario (Cliente / Administrador)"); /*de momento es string en vez
																						problemas con el
																						enum*/
			rol = sc.nextLine();
			rol = rol.toUpperCase();
			if (rol != "CLIENTE" && rol != "ADMINISTRADOR") {
				System.out.println("Este rol no esta disponible.");
			} else {
				counterRol++;
			}
		} while (counterRol != 1);

		Usuario usuario = new Usuario(dni, nombre, apellido, contraseña, rol);

		return usuario;

	}

	public static int guardarOpcion() {
		int eleccion = sc.nextInt();
		return eleccion;
	}

}