package view;

import java.util.List;
import java.util.Scanner;
import modelo.Neopreno;
import modelo.Oficina;
import modelo.TablaSurf;
import repositorios.RepositorioNeopreno;
import repositorios.RepositorioOficina;
import repositorios.RepositorioTablaSurf;
import repositorios.RepositorioUsuario;

public class Menu {

	private static int idOficina;

	public static void mostrarMenu() {
		int opcion;
		do {
// Mostrar menu principal
			System.out.println("Bienvenido al sistema de reservas");
			System.out.println("1. Registrarse");
			System.out.println("2. Iniciar sesión");
			System.out.println("3. Salir");

			opcion = RepositorioUsuario.guardarOpcion();
			if (opcion > 3 || opcion < 1) {
				System.out.println("Opcion no disponible");
				return;
			}
			switch (opcion) {
			case 1:
				System.out.println("Registrarse:");
				RepositorioUsuario.registrarUsuario(); // Llamamos al registro
				break;
			case 2:
				System.out.println("Iniciar sesión:");
				RepositorioUsuario.iniciarSesion(); // Llamamos al inicio de sesion
				break;
			case 3:
				System.out.println("Programa finalizado");
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
				break;
			}
		} while (opcion != 3); // Bucle hasta que pulsemos el botón de salir.
	}

	public static void mostrarMenuOficinas() {
		Scanner sc = new Scanner(System.in);
		List<Oficina> oficinas = RepositorioOficina.obtenerOficinas();

		int opcion;

		// Mostrar oficinas disponibles
		System.out.println("Oficinas disponibles:");
		for (int i = 0; i < oficinas.size(); i++) {
			Oficina oficina = oficinas.get(i);
			System.out.println((i + 1) + ". " + oficina.getNombre());
		}
		System.out.println((oficinas.size() + 1) + ". Cerrar sesión");
		System.out.println((oficinas.size() + 2) + ". Finalizar");

		opcion = sc.nextInt();

		if (opcion >= 1 && opcion <= oficinas.size()) {
			Oficina oficinaSeleccionada = oficinas.get(opcion - 1);
			idOficina = oficinaSeleccionada.getIdOficina();
			System.out.println("Has seleccionado la oficina: " + oficinaSeleccionada.getNombre());
			mostrarArticulos();
		} else if (opcion == oficinas.size() + 1) {
			mostrarMenu();
		} else if (opcion == oficinas.size() + 2) {
			System.out.println("Programa finalizado.");
			System.exit(0);
		} else {
			System.out.println("Opción incorrecta. Inténtalo de nuevo.");
		}
	}

	public static void mostrarArticulos() {
		int opcion;
		System.out.println("¿Qué articulo desea reservar?");
		System.out.println("1. Tablas de surf");
		System.out.println("2. Neoprenos");
		opcion = RepositorioUsuario.guardarOpcion();
		switch (opcion) {
		case 1:
			realizarReservaTabla(idOficina);
			break;
		case 2:
			realizarReservaNeopreno(idOficina);
			break;

		}
	}

	
	
	public static void realizarReservaTabla(int idOficina) {
		List<TablaSurf> tablaSurfDisponibles = RepositorioTablaSurf.obtenerTablaSurfPorOficina(idOficina);

		if (tablaSurfDisponibles.isEmpty()) {
			System.out.println("No hay tablas de surf disponibles en esta oficina.");
			return;
		}

		// Mostrar tablas disponibles
		System.out.println("Tablas de surf disponibles:");
		for (int i = 0; i < tablaSurfDisponibles.size(); i++) {
			TablaSurf tabla = tablaSurfDisponibles.get(i);
			System.out.println((i + 1) + ". Tabla Surf Tipo: " + tabla.getTipo() + ", Tamaño: " + tabla.getTamaño()
					+ ", Precio: " + tabla.getPrecio_horas() + "€/hora");
		}

		// Solicitar selección de tabla
		System.out.print("Seleccione el número de la tabla que desea reservar: ");
		int opcionTabla = RepositorioUsuario.guardarOpcion();

		if (opcionTabla < 1 || opcionTabla > tablaSurfDisponibles.size()) {
			System.out.println("Opción no válida.");
			return;
		}

		TablaSurf tablaSeleccionada = tablaSurfDisponibles.get(opcionTabla - 1);

		// Solicitar horas de reserva
		System.out.print("Ingrese la cantidad de horas que desea reservar: ");
		int horas = RepositorioUsuario.guardarOpcion();

		// Intentar reservar la tabla
		boolean reservaExitosa = RepositorioTablaSurf.reservarTabla(tablaSeleccionada.getId(), horas);

		if (reservaExitosa) {
			System.out.println("Reserva exitosa! Has reservado la tabla " + tablaSeleccionada.getTipo() + " por "
					+ horas + " horas.");
		} else {
			System.out.println("No se pudo realizar la reserva. La tabla no está disponible.");
		}
	}

	
	public static void mostrarNeoprenosPorOficina(int idOficina) {
	    List<Neopreno> neoprenosDisponibles = RepositorioNeopreno.obtenerNeoprenoPorOficina(idOficina);

	    if (neoprenosDisponibles.isEmpty()) {
	        System.out.println("No hay neoprenos disponibles en esta oficina.");
	    } else {
	        System.out.println("Neoprenos disponibles en esta oficina:");
	        for (int i = 0; i < neoprenosDisponibles.size(); i++) {
	            Neopreno neopreno = neoprenosDisponibles.get(i);
	            System.out.println((i + 1) + ". Neopreno Grosor: " + neopreno.getGrosor() + ", Color: "
	                    + neopreno.getColor() + ", Talla: " + neopreno.getTalla() + ", Precio: "
	                    + neopreno.getPrecio_horas() + "€/hora");
	        }

	        // Llamar a la función de reserva
	        realizarReservaNeopreno(idOficina);
	    }
	}
	
	// relaizar el mismo procedimiento que con relalizar reserva neopreno
	public static void realizarReservaNeopreno(int idOficina) {
		List<Neopreno> neoprenosDisponibles = RepositorioNeopreno.obtenerNeoprenoPorOficina(idOficina);

		if (neoprenosDisponibles.isEmpty()) {
			System.out.println("No hay neoprenos disponibles en esta oficina.");
			return;
		}

		// Mostrar neoprenos disponibles
		System.out.println("Neoprenos disponibles:");
		for (int i = 0; i < neoprenosDisponibles.size(); i++) {
			Neopreno neopreno = neoprenosDisponibles.get(i);
			System.out
					.println((i + 1) + ". Neopreno Grosor: " + neopreno.getGrosor() + ", Color: " + neopreno.getColor()
							+ ", Talla: " + neopreno.getTalla() + ", Precio: " + neopreno.getPrecio_horas() + "€/hora");
		}

		// Solicitar selección de neopreno
		System.out.print("Seleccione el número del neopreno que desea reservar: ");
		int opcionNeopreno = RepositorioUsuario.guardarOpcion();

		if (opcionNeopreno < 1 || opcionNeopreno > neoprenosDisponibles.size()) {
			System.out.println("Opción no válida.");
			return;
		}

		Neopreno neoprenoSeleccionado = neoprenosDisponibles.get(opcionNeopreno - 1);

		// Solicitar horas de reserva
		System.out.print("Ingrese la cantidad de horas que desea reservar: ");
		int horas = RepositorioUsuario.guardarOpcion();

		// Intentar reservar el neopreno
		boolean reservaExitosa = RepositorioNeopreno.reservarNeopreno(neoprenoSeleccionado.getId(), horas);

		if (reservaExitosa) {
			System.out.println("Reserva exitosa! Has reservado el neopreno " + neoprenoSeleccionado.getColor()
					+ " talla " + neoprenoSeleccionado.getTalla() + " por " + horas + " horas.");
		} else {
			System.out.println("No se pudo realizar la reserva. El neopreno no está disponible.");
		}
	}

}