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

	public static ArrayList<Articulo> obtenerArticulo(int idoficina) {

		String query = "SELECT a.idArticulo, a.precio_horas, a.disponibilidad, a.idOficina, " + "ts.tipo AS tipoTablaSurf, ts.tamaño, "
				+ "np.grosor, np.color, np.talla " + "FROM Articulo a "
				+ "LEFT JOIN TablaSurf ts ON a.idArticulo = ts.idArticulo "
				+ "LEFT JOIN Neopreno np ON a.idArticulo = np.idArticulo "
				+ "JOIN oficina o ON a.idOficina = o.idOficina " + "WHERE a.disponibilidad = 1 AND a.idOficina = ?"; // Corrección
																														// aquí

		ArrayList<Articulo> articulosDisponibles = new ArrayList<>();

		try (Connection conexion = ConectorBD.conexion; PreparedStatement stmt = conexion.prepareStatement(query)) {
			stmt.setInt(1, idoficina);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {

					// Obtener datos comunes a todos los artículos
					int idArticulo = rs.getInt("idArticulo");
					double precioHoras = rs.getDouble("precio_horas");
					boolean disponibilidad = rs.getBoolean("disponibilidad");
					int idOficina = rs.getInt("idOficina");

					// Verificar si es un artículo de tipo TablaSurf
					if (rs.getString("tipoTablaSurf") != null) {
						String tipo = rs.getString("tipoTablaSurf");
						int tamaño = rs.getInt("tamaño");
						TablaSurf tablaSurf = new TablaSurf(idArticulo, precioHoras, disponibilidad, idOficina, tipo,
								tamaño);
						articulosDisponibles.add(tablaSurf);

						// Imprimir detalles del artículo TablaSurf
						System.out.println("TablaSurf: " + "ID_Articulo: " + idArticulo + ", Precio_hora: "
								+ precioHoras + ", Disponibilidad: " + disponibilidad + ", ID_Oficina: " + idOficina
								+ ", Tipo: " + tipo + ", Tamaño: " + tamaño);
					}
					// Verificar si es un artículo de tipo Neopreno
					else if (rs.getString("grosor") != null) {
						String grosor = rs.getString("grosor");
						String color = rs.getString("color");
						String talla = rs.getString("talla");
						Neopreno neopreno = new Neopreno(idArticulo, precioHoras, disponibilidad, idOficina, grosor,
								color, talla);
						articulosDisponibles.add(neopreno);

						// Imprimir detalles del artículo Neopreno
						System.out.println("Neopreno: " + "ID_Articulo: " + idArticulo + ", Precio_hora: " + precioHoras
								+ ", Disponibilidad: " + disponibilidad + ", ID_Oficina: " + idOficina + ", Grosor: "
								+ grosor + ", Color: " + color + ", Talla: " + talla);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articulosDisponibles;
	}

}