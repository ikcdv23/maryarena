package repositorios;

import java.sql.*;

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
				}else System.out.println("Opcion no permitida");
				System.out.println("");
				System.out.println("");
			} while (opcion != 1);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
