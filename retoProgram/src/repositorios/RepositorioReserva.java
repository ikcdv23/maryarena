package repositorios;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import modelo.DatosReserva;
import modelo.Neopreno;
import modelo.Reserva;
import view.MenuAdministrador;

public class RepositorioReserva {


	// Método para guardar una reserva en la base de datos
	public static boolean guardarReserva(Reserva reserva) {
		boolean guardada = false;

		try {
			// Consultar el precio por hora del artículo seleccionado
			double precioPorHora = obtenerPrecioArticulo(reserva.getIdArticulo());
			if (precioPorHora == -1) {
				System.out.println("El artículo seleccionado no existe.");
				return false;
			}

			// Calcular el precio total de la reserva
			double precioTotal = precioPorHora * calcularDuracionHoras(reserva.getHora_inicio(), reserva.getHora_fin());

			// Preparar la sentencia SQL para insertar la reserva
			String query = "INSERT INTO Reserva (dni, idArticulo, fecha, hora_inicio, hora_fin) VALUES (?, ?, ?, ?, ?)";

			// Ejecutar la inserción en la base de datos
			try (PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query)) {
				stmt.setString(1, reserva.getDni());
				stmt.setInt(2, reserva.getIdArticulo());
				stmt.setDate(3, reserva.getFecha());
				stmt.setTime(4, reserva.getHora_inicio());
				stmt.setTime(5, reserva.getHora_fin());
				int count = stmt.executeUpdate();

				if (count > 0) {
					guardada = true;
					System.out.println("Reserva realizada con éxito. Precio total: " + precioTotal + "€");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error al guardar la reserva.");
		}

		return guardada;
	}

	// Método para obtener el precio por hora de un artículo
	private static double obtenerPrecioArticulo(int idArticulo) {
		double precio = -1; // Retornamos -1 si no encontramos el artículo

		try {
			// Consultamos el precio por hora del artículo en la base de datos
			String query = "SELECT precio_horas FROM Articulo WHERE idArticulo = ?";
			try (PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query)) {
				stmt.setInt(1, idArticulo);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					precio = rs.getDouble("precio_horas");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return precio;
	}

	// Método para solicitar y validar fecha y horas de reserva
	public static DatosReserva solicitarDatosReserva() {
		Scanner scanner = new Scanner(System.in);
		DatosReserva datos = new DatosReserva();

		// Validación de fecha
		while (datos.fecha == null) {
			try {
				System.out.print("Ingrese la fecha de la reserva (YYYY-MM-DD): ");
				String fechaSc = scanner.next();
				LocalDate fechaReserva = LocalDate.parse(fechaSc);
				if (fechaReserva.isBefore(LocalDate.now())) {
					System.out.println("Error: La fecha no puede ser anterior a la fecha actual.");
				} else {
					datos.fecha = Date.valueOf(fechaReserva);
					break;
				}
			} catch (Exception e) {
				System.out.println("Error: Formato de fecha inválido. Use YYYY-MM-DD.");
			}
		}

		// Validación de hora de inicio
		while (datos.getHoraInicio() == null) {
			try {
				System.out.print("Ingrese la hora de inicio (HH:MM): ");
				String horaInicioSc = scanner.next();
				LocalTime horaInicioReserva = LocalTime.parse(horaInicioSc);
				// Si la fecha es hoy, la hora de inicio no puede ser anterior a la actual
				if (LocalDate.now().isEqual(datos.fecha.toLocalDate())) {
					if (horaInicioReserva.isBefore(LocalTime.now())) {
						System.out.println("Error: La hora de inicio no puede ser anterior a la hora actual.");
						continue;
					}
				}
				datos.setHoraInicio(Time.valueOf(horaInicioReserva));
				break;
			} catch (Exception e) {
				System.out.println("Error: Formato de hora inválido. Use HH:MM (24 horas).");
			}
		}

		// Validación de hora de fin
		while (datos.getHoraFin() == null) {
			
			try {
				System.out.print("Ingrese la hora de fin (HH:MM): ");
				String horaFinSc = scanner.next();
				LocalTime horaFinReserva = LocalTime.parse(horaFinSc);
				if (horaFinReserva.isBefore(datos.getHoraInicio().toLocalTime())) {
					System.out.println("Error: La hora de fin debe ser posterior a la de inicio.");
				} else {
					datos.setHoraFin(Time.valueOf(horaFinReserva));
					break;
				}
			} catch (Exception e) {
				System.out.println("Error: Formato de hora inválido. Use HH:MM (24 horas).");
				System.out.println();
			}
		}
		return datos;
	}

	//
	public static ArrayList<Reserva> comprobarDisponibilidad(DatosReserva datosReserva) throws SQLException{
		Time horaInicio = datosReserva.getHoraInicio();
		Date fecha = datosReserva.getFecha();
		
		String query = "SELECT * FROM maryarena.reservas WHERE fecha != ? AND hora_inicio != ?";
		 try (PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query)){
			 stmt.setDate(1, fecha);
			 stmt.setTime(2, horaInicio);
			 ResultSet rs = stmt.executeQuery();
			 
			 while(rs.next()) {
				 int idReserva = rs.getInt("idReserva");
				String dni = rs.getString("dni");
				int idArticulo = rs.getInt("idArticulo");
				fecha = rs.getDate("fecha");
				horaInicio = rs.getTime("hora_inicio");
				Time horaFin = rs.getTime("hora_fin");
			 }
		 }
		return null;
		
	}
	
	
	
	// Método para calcular la duración de la reserva en horas
	private static int calcularDuracionHoras(Time horaInicio, Time horaFin) {
		long duracionMilisegundos = horaFin.getTime() - horaInicio.getTime();
		return (int) (duracionMilisegundos / (1000 * 60 * 60)); // Convertir milisegundos a horas
	}

	public static void mostrarReservas() {
		String query = "SELECT * FROM maryarena.reserva;";

		try {
			PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				System.out.println("+-------------+----------------------+");
				System.out.printf("| %-11s | %-20s |\n", "ID_Reserva", rs.getInt("idReserva"));
				System.out.println("+-------------+----------------------+");
				System.out.printf("| %-11s | %-20s |\n", "DNI", rs.getString("dni"));
				System.out.println("+-------------+----------------------+");
				System.out.printf("| %-11s | %-20s |\n", "ID_Articulo", rs.getInt("idArticulo"));
				System.out.println("+-------------+----------------------+");
				System.out.printf("| %-11s | %-20s |\n", "Fecha", rs.getDate("fecha"));
				System.out.println("+-------------+----------------------+");
				System.out.printf("| %-11s | %-20s |\n", "Hora_Inicio", rs.getTime("hora_inicio"));
				System.out.println("+-------------+----------------------+");
				System.out.printf("| %-11s | %-20s |\n", "Hora_Fin", rs.getTime("hora_fin"));
				System.out.println("+-------------+----------------------+");

			}

			int opcion = 0;
			do {
				System.out.println("Pulse 1 para volver al menú");
				opcion = RepositorioUsuario.guardarOpcion();
				if (opcion == 1) {
					MenuAdministrador.MostrarMenuAdmin();
				} else
					System.out.println("Opcion no permitida");
				System.out.println("");
				System.out.println("");
			} while (opcion != 1);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
