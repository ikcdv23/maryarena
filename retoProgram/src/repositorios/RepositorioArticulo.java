package repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.Articulo;
import modelo.Neopreno;
import modelo.TablaSurf;

public class RepositorioArticulo {

	public static void obtenerArticulo() {
		String query = "SELECT a.idArticulo, a.precio_horas, a.disponibilidad, a.idOficina, "
				+ "ts.tipo AS tipoTablaSurf, ts.tamaño, " + "np.grosor, np.color, np.talla " + "FROM Articulo a "
				+ "LEFT JOIN TablaSurf ts ON a.idArticulo = ts.idArticulo "
				+ "LEFT JOIN Neopreno np ON a.idArticulo = np.idArticulo " + "WHERE a.disponibilidad = true";

		List<Articulo> articulosDisponibles = new ArrayList<>();

		try (Connection conexion = ConectorBD.conexion;
				PreparedStatement stmt = conexion.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				int idArticulo = rs.getInt("idArticulo");
				double precioHoras = rs.getDouble("precio_horas");
				boolean disponibilidad = rs.getBoolean("disponibilidad");
				int idOficina = rs.getInt("idOficina");

				if (rs.getString("tipoTablaSurf") != null) {
					// Si es un artículo de tipo TablaSurf
					String tipo = rs.getString("tipoTablaSurf");
					int tamaño = rs.getInt("tamaño");
					articulosDisponibles
							.add(new TablaSurf(idArticulo, precioHoras, disponibilidad, idOficina, tipo, tamaño));
				} else if (rs.getString("grosor") != null) {
					// Si es un artículo de tipo Neopreno
					String grosor = rs.getString("grosor");
					String color = rs.getString("color");
					String talla = rs.getString("talla");
					articulosDisponibles.add(
							new Neopreno(idArticulo, precioHoras, disponibilidad, idOficina, grosor, color, talla));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Mostrar los artículos disponibles
		Scanner sc = new Scanner(System.in);
		int opcion;
		do {
			System.out.println("Artículos disponibles para reservar:");
			for (int i = 0; i < articulosDisponibles.size(); i++) {
				Articulo articulo = articulosDisponibles.get(i);
				if (articulo instanceof TablaSurf) {
					TablaSurf tabla = (TablaSurf) articulo;
					System.out.println((i + 1) + ". Tabla Surf - " + tabla.getTipo() + ", Tamaño: " + tabla.getTamaño()
							+ ", Precio: " + tabla.getPrecio_horas() + "€/hora");
				} else if (articulo instanceof Neopreno) {
					Neopreno neopreno = (Neopreno) articulo;
					System.out.println((i + 1) + ". Neopreno - Grosor: " + neopreno.getGrosor() + ", Talla: "
							+ neopreno.getTalla() + ", Precio: " + neopreno.getPrecio_horas() + "€/hora");
				}
			}
			System.out.println((articulosDisponibles.size() + 1) + ". Finalizar");

			opcion = sc.nextInt();
			if (opcion >= 1 && opcion <= articulosDisponibles.size()) {
				Articulo articuloSeleccionado = articulosDisponibles.get(opcion - 1);
				System.out.println("Has seleccionado el artículo: " + articuloSeleccionado.getIdArticulo());
				// Aquí puedes agregar la lógica para reservar este artículo (por ejemplo,
				// seleccionar la fecha y duración)
			} else if (opcion == articulosDisponibles.size() + 1) {
				System.out.println("Finalizando...");
			} else {
				System.out.println("Opción no válida.");
			}

		} while (opcion != articulosDisponibles.size() + 1);
	}
}