package repositorios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Articulo;
import modelo.TablaSurf;

public class RepositorioTablaSurf {
	public static List<TablaSurf> obtenerTablaSurfPorOficina(int idOficina) {
		List<TablaSurf> TablaSurf = new ArrayList<>();
		String query = "SELECT a.idArticulo, a.precio_horas, a.idOficina, " + "ts.tipo AS tipoTablaSurf, ts.tamaño, "
				+ "np.grosor, np.color, np.talla " + "FROM Articulo a "
				+ "LEFT JOIN TablaSurf ts ON a.idArticulo = ts.idArticulo "
				+ "LEFT JOIN Neopreno np ON a.idArticulo = np.idArticulo " + "WHERE a.idOficina = ?";

		try (PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query)) {
			stmt.setInt(1, idOficina);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int idArticulo = rs.getInt("idArticulo");
				double precioHoras = rs.getDouble("precio_horas");
				int idOficina1 = rs.getInt("idOficina");

				if (rs.getString("tipoTablaSurf") != null) {
					// Si es un artículo de tipo TablaSurf
					String tipo = rs.getString("tipoTablaSurf");
					int tamaño = rs.getInt("tamaño");
					TablaSurf tablaSurf = new TablaSurf(idArticulo, precioHoras, idOficina1, tipo, tamaño);
					TablaSurf.add(tablaSurf); // Agregar la tabla a la lista
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return TablaSurf;
	}
}
