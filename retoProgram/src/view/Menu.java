package view;

import modelo.Usuario;
import repositorios.RepositorioUsuario;

public class Menu {

	public void mostrarMenu() {

		int opcion;
		int menuClienteUsuario = 0;
		do {
			// Mostrar menu principal
			System.out.println("Bienvenido al sistema de reservas");
			System.out.println("1. Registrarse");
			System.out.println("2. Iniciar sesión");
			System.out.println("4. Salir");

			opcion = RepositorioUsuario.guardarOpcion();

			switch (opcion) {
			case 1:
				System.out.println("Registrarse:");
				 // Llamamos al registro
				Usuario usuario = RepositorioUsuario.registrarUsuario();
				break;
			case 2:
				System.out.println("Iniciar sesión:");
				RepositorioUsuario.iniciarSesion(); // Llamar al inicio de sesion
				
				menuClienteUsuario = 1;
				break;
			case 3:
				System.out.println("Programa finalizado");
				break;
			case 4:
				System.out.println("Programa finalizado");
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
				break;
			}
		} while (opcion != 4 || menuClienteUsuario == 1); // El ciclo se repite hasta que el usuario elige "Salir"
	}

	public static void menuReservas() {
		// Mostrar menu principal
		System.out.println("Bienvenido al sistema de reservas");
		System.out.println("1. Mostrar mi perfil");
		System.out.println("2. Crear reserva");
		System.out.println("1. Comprobar reserva");
		System.out.println("2. Ver productos");
		System.out.println("3. Eliminar cuenta");
		System.out.println("4. Cerrar sesión");
	}

}
