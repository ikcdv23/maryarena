package view;

import java.util.List;
import java.util.Scanner;

import modelo.Oficina;
import repositorios.RepositorioOficina;
import repositorios.RepositorioUsuario;

public class Menu {

	public static void mostrarMenu() {

		int opcion;
		do {
			// Mostrar menu principal
			System.out.println("Bienvenido al sistema de reservas");
			System.out.println("1. Registrarse");
			System.out.println("2. Iniciar sesión");
			System.out.println("3. Salir");

			opcion = RepositorioUsuario.guardarOpcion();

			switch (opcion) {
			case 1:
				System.out.println("Registrarse:");
				RepositorioUsuario.registrarUsuario(); // Llamamor al registro
				break;
			case 2:
				System.out.println("Iniciar sesión:");
				RepositorioUsuario.iniciarSesion(); // Llamar al inicio de sesion
				break;
			case 3:
				System.out.println("Programa finalizado");
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
				break;
			}
		} while (opcion != 3); // El ciclo se repite hasta que el usuario elige "Salir"
	}

	public static void mostrarMenu2() {
		Scanner sc = new Scanner(System.in);
		List<Oficina> oficinas = RepositorioOficina.obtenerOficinas(); // Obtener las oficinas de la base de datos

		int opcion;
		do {
			System.out.println("Oficinas disponibles: ");

			// Mostrar oficina
			for (int i = 0; i < oficinas.size(); i++) {
				Oficina oficina = oficinas.get(i);
				System.out.println((i + 1) + ". " + oficina.getNombre());
			}

			// Opción para finalizar
			System.out.println((oficinas.size() + 1) + ". Finalizar");
			System.out.println((oficinas.size() + 2) + ". Volver al menú anterior");

			// Leer la opción del usuario
			opcion = RepositorioUsuario.guardarOpcion();

			// Lógica para manejar la opción seleccionada
			if (opcion >= 1 && opcion <= oficinas.size()) {
				Oficina oficinaSeleccionada = oficinas.get(opcion - 1);
				System.out.println(oficinaSeleccionada.getNombre());
				// Aqui pondremos el siguiente menu
			} else if (opcion == oficinas.size() + 1) {
				System.out.println("Programa finalizado");
			} else if (opcion == oficinas.size() + 2) {
				mostrarMenu();
			} else {
				System.out.println("Opción no válida. Inténtalo de nuevo.");
			}

		} while (opcion != oficinas.size() + 1); // Termina cuando se selecciona la opción "Finalizar"
	}

	public static void menuReserva() {
		System.out.println("¡Bienbenido a Mar & Arena!");
		System.out.println("1. Ver lista de articulos");
		System.out.println("2. Crear reserva");
		System.out.println("3. Eliminar reserva");
		System.out.println("4. Página anterior");
		System.out.println("5. Cerrar sesion");

	}

}