package view;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import modelo.Neopreno;
import modelo.Oficina;
import modelo.Reserva;
import modelo.TablaSurf;
import repositorios.RepositorioNeopreno;
import repositorios.RepositorioOficina;
import repositorios.RepositorioReserva;
import repositorios.RepositorioTablaSurf;
import repositorios.RepositorioUsuario;

public class Menu {

	private static int idOficina;

	public static void mostrarMenuPrincipal() {
		int opcion = 0;
		Scanner scanner = new Scanner(System.in);

		do {
			// Mostrar menu principal
			System.out.println("-----Bienvenido al sistema de reservas-----");
			System.out.println("1. Registrarse");
			System.out.println("2. Iniciar sesión");
			System.out.println("3. Salir");

			try {

				System.out.print("Seleccione una opción: ");
				opcion = scanner.nextInt();

				switch (opcion) {
				case 1:
					System.out.println("Registrarse:");
					RepositorioUsuario.registrarUsuario(); // Llamamos al registro
					break;
				case 2:
					System.out.println("Iniciar sesión:");
					RepositorioUsuario.iniciarSesion(); // Llamamos al inicio de sesión
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

		} while (opcion != 3); // Bucle hasta que elija salir.

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
			mostrarMenuPrincipal();
		} else if (opcion == oficinas.size() + 2) {
			System.out.println("Programa finalizado.");
			System.exit(0);
		} else {
			System.out.println("Opción incorrecta. Inténtalo de nuevo.");
		}
	}

	public static void mostrarArticulos() {
		int opcion;
		System.out.println("-----¿Qué articulo desea reservar?-----");
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

			// Solicitar al usuario elegir una tabla de surf
			Scanner sc = new Scanner(System.in);
			System.out.println("Seleccione la tabla para reservar: ");
			int seleccion = sc.nextInt();
			TablaSurf tablaSeleccionada = tablaSurfDisponibles.get(seleccion - 1);

			// Realizar la reserva
			String dni = RepositorioUsuario.obtenerDniUsuarioLogueado(); // Obtener el DNI del usuario logueado
			realizarReserva(tablaSeleccionada.getIdArticulo(), dni);
		}
	}

	public static void mostrarNeoprenosPorOficina(int idOficina) {
		ArrayList<Neopreno> neoprenosDisponibles = RepositorioNeopreno.obtenerNeoprenoPorOficina(idOficina);
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

			// Solicitar al usuario elegir un neopreno
			Scanner sc = new Scanner(System.in);
			System.out.println("Seleccione el neopreno para reservar: ");
			int seleccion = sc.nextInt();
			Neopreno neoprenoSeleccionado = neoprenosDisponibles.get(seleccion - 1);

			// Realizar la reserva
			String dni = RepositorioUsuario.obtenerDniUsuarioLogueado(); // Obtener el DNI del usuario logueado
			realizarReserva(neoprenoSeleccionado.getIdArticulo(), dni);
		}
	}

	public static Reserva realizarReserva(int idArticulo, String dni) {
		Scanner scanner = new Scanner(System.in);

		// Validación del DNI (suponiendo que debe tener 8 dígitos y una letra)
		if (!dni.matches("\\d{8}[A-Za-z]")) {
			throw new IllegalArgumentException("Error: DNI inválido. Debe tener 8 números seguidos de una letra.");
		}

		// Obtener DNI del usuario logueado (ejemplo)
		String dniUsuario = RepositorioUsuario.obtenerDniUsuarioLogueado();

		List<TablaSurf> tablas = RepositorioTablaSurf.obtenerTablaSurfPorOficina(idOficina);

		Date fecha = null;
		Time horaInicio = null;
		Time horaFin = null;

		// Lectura de fecha con validación
		while (fecha == null) {
			try {
				System.out.print("Ingrese la fecha de la reserva (YYYY-MM-DD): ");
				String fechaSc = scanner.next();
				LocalDate diaActual = LocalDate.now();// Fehca actual
				LocalDate fechaAlquiler = LocalDate.parse(fechaSc);// Fecha intrdoucida por el usuario
				// Si la fecha alquiler es posterior a la actual
				if (diaActual.isBefore(fechaAlquiler) || diaActual.isEqual(fechaAlquiler)) {
					fecha = Date.valueOf(fechaSc);

					// Lectura de hora de inicio con validación
					while (horaInicio == null) {
						try {
							System.out.print("Ingrese la hora de inicio (HH:MM): ");
							String horaInicioSc = scanner.next();
							LocalDateTime horaActual = LocalDateTime.now();
							// Definir el formato esperado
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

							// Convertir la entrada a LocalTime
							LocalTime horaAlquiler = LocalTime.parse(horaInicioSc, formatter);

							// Obtener la fecha actual y combinarla con la hora ingresada
							LocalDateTime fechaHoraAlquiler = LocalDateTime.now().withHour(horaAlquiler.getHour())
									.withMinute(horaAlquiler.getMinute()).withSecond(0).withNano(0);

							if ( horaActual.isEqual(fechaHoraAlquiler) &&  diaActual.isEqual(fechaAlquiler)   ) {
								validarFormatoHora(horaInicioSc);
								horaInicio = Time.valueOf(horaInicioSc + ":00");
							} else {
								System.out.println("La hora no puede ser anterior a la hora actual");
							}

						} catch (IllegalArgumentException e) {
							System.out.println("Error: Formato de hora inválido. Use HH:MM (24 horas).");
							scanner.nextLine();
						}
					}

				} else {
					System.out.println("La fecha no puede ser anterior a la fecha actual");
				}

			} catch (IllegalArgumentException e) {
				System.out.println("Error: Formato de fecha inválido. Use YYYY-MM-DD.");
				scanner.nextLine(); // Limpiar buffer
			}
		}

		// Lectura de hora de fin con validación y comprobación de coherencia
		while (horaFin == null) {
			try {
				System.out.print("Ingrese la hora de fin (HH:MM): ");
				String horaFinSc = scanner.next();
				validarFormatoHora(horaFinSc);
				horaFin = Time.valueOf(horaFinSc + ":00");

				if (horaFin.before(horaInicio)) {
					System.out.println("Error: La hora de fin debe ser posterior a la de inicio.");
					horaFin = null; // Reiniciar para volver a solicitar entrada
				}
			} catch (IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				scanner.nextLine();
			}
		}

		// Crear la reserva
		Reserva reserva = new Reserva();
		reserva.setDni(dni);
		reserva.setIdArticulo(idArticulo);
		reserva.setFecha(fecha);
		reserva.setHora_inicio(horaInicio);
		reserva.setHora_fin(horaFin);

		// Guardar la reserva en la base de datos
		boolean guardada = RepositorioReserva.guardarReserva(reserva);

		if (guardada) {
			System.out.println("Reserva guardada con éxito.");
			System.out.println("");
			System.out.println("¿Qué desea hacer ahora?");
			System.out.println("1. Realizar otra reserva ");
			System.out.println("2. Cerrar sesión ");
			System.out.println("3. Finalizar programa ");

			int opcion = -1;
			while (opcion < 1 || opcion > 3) {
				try {
					System.out.print("Seleccione una opción: ");
					opcion = Integer.parseInt(scanner.next());

					switch (opcion) {
					case 1:
						mostrarArticulos();
						break;
					case 2:
						System.out.println("Sesión cerrada.");
						mostrarMenuPrincipal();
						break;
					case 3:
						System.out.println("Programa finalizado.");
						System.exit(0);
						break;
					default:
						System.out.println("Opción no válida. Intente nuevamente.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Error: Ingrese un número válido.");
				}
			}
		} else {
			System.out.println("Hubo un problema al guardar la reserva.");
		}

		return reserva;
	}

	// Método auxiliar para validar el formato de la hora (HH:MM)
	private static void validarFormatoHora(String hora) {
		if (!hora.matches("^([01]\\d|2[0-3]):([0-5]\\d)$")) {
			throw new IllegalArgumentException("Formato de hora inválido. Use HH:MM (24 horas).");
		}
	}

}