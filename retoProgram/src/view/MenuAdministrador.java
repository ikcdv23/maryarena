package view;

import java.util.InputMismatchException;
import java.util.Scanner;

import repositorios.RepositorioNeopreno;
import repositorios.RepositorioNeopreno.GestionNeopreno;
import repositorios.RepositorioReserva;
import repositorios.RepositorioTablaSurf.gestionTablaSurf;
import repositorios.RepositorioUsuario;

public class MenuAdministrador {

	public static void MostrarMenuAdmin() {
		int opcion = 0;
		Scanner scanner = new Scanner(System.in);

		System.out.println("-----Has iniciado sesión como administrador-----");
		System.out.println("1. Ver todas las reservas");
		System.out.println("2. Añadir Articulos");
		System.out.println("3. Eliminar Articulos");
		System.out.println("4. Añadir Oficinas");
		System.out.println("5. Eliminar Oficinas");
		System.out.println("6. Cerrar sesión");
		System.out.println("7. Cerrar sesión y finalizar el programa");

		try {

			System.out.print("Seleccione una opción: ");
			opcion = scanner.nextInt();

			switch (opcion) {
			case 1:
				RepositorioReserva.mostrarReservas();
				// Llama a la funcion que muestra todas las reservas
				break;
			case 2:
			    System.out.println("---¿Qué artículo desea añadir a la BBDD?---");
			    System.out.println("1. Tablas de surf");
			    System.out.println("2. Neoprenos");
			    opcion = scanner.nextInt();

			    switch (opcion) {
			        case 1:
			        	gestionTablaSurf.insertarTablaSurfDesdeConsola();
			            // Llamar la función para insertar tablas
			            break;
			        case 2:
			            GestionNeopreno.insertarNeoprenoDesdeConsola();
			            break;
			    }
			    break;
			case 3:
				System.out.println("Programa finalizado");
				System.exit(0);
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
				break;
			}
		} catch (InputMismatchException e) {
			System.out.println("Error: Debe introducir un número.");
			scanner.nextLine(); // Limpiar el buffer del scanner
		}
	}
}