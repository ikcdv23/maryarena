package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.Articulo;
import modelo.Neopreno;
import modelo.Oficina;
import modelo.TablaSurf;
import repositorios.RepositorioArticulo;
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

	// Mostrar los articulos disponibles
	public static void mostrarArticulosPorOficina(int idOficina) {
		List<Articulo> articulosDisponibles = RepositorioArticulo.obtenerArticulosPorOficina(idOficina);
		if (articulosDisponibles.isEmpty()) {
			System.out.println("No hay artículos disponibles en esta oficina.");
		} else {
			System.out.println("Artículos disponibles en esta oficina:");
			for (int i = 0; i < articulosDisponibles.size(); i++) {
				Articulo articulo = articulosDisponibles.get(i);
				if (articulo instanceof TablaSurf) {
					TablaSurf tabla = (TablaSurf) articulo;
					System.out.println((i + 1) + ". Tabla Surf Tipo: " + tabla.getTipo() + ", Tamaño: "
							+ tabla.getTamaño() + ", Precio: " + tabla.getPrecio_horas() + "€/hora");
				} else if (articulo instanceof Neopreno) {
					Neopreno neopreno = (Neopreno) articulo;
					System.out.println((i + 1) + ". Neopreno Grosor: " + neopreno.getGrosor() + ", Color: "
							+ neopreno.getColor() + ", Talla: " + neopreno.getTalla() + ", Precio: "
							+ neopreno.getPrecio_horas() + "€/hora");
				}
			}
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
			mostrarTablasPorOficina(idOficina);
			break;
		case 2:
			mostrarNeoprenosPorOficina(idOficina);
			break;

		}
	}

	public static void mostrarTablasPorOficina(int idOficina) {
		List<TablaSurf> tablaSurfDisponibles = RepositorioTablaSurf.obtenerTablaSurfPorOficina(idOficina);
		if (tablaSurfDisponibles.isEmpty()) {
			System.out.println("No hay tablas de surf disponibles en esta oficina.");
		} else {
			System.out.println("Tablas de surf disponibles en esta oficina:");
			for (int i = 0; i < tablaSurfDisponibles.size(); i++) {
				TablaSurf tabla = tablaSurfDisponibles.get(i);
				System.out.println((i + 1) + ". Tabla Surf Tipo: " + tabla.getTipo() + ", Tamaño: " + tabla.getTamaño()
						+ ", Precio: " + tabla.getPrecio_horas() + "€/hora");
			}
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
		}
	}
}