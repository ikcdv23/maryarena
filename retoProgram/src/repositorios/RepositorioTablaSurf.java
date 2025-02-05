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
				+ "FROM Articulo a " + "JOIN TablaSurf ts ON a.idArticulo = ts.idArticulo " + "WHERE a.idOficina = ?";

		try (PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query)) {
			stmt.setInt(1, idOficina);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int idArticulo = rs.getInt("idArticulo");
				double precioHoras = rs.getDouble("precio_horas");
				int idOficina1 = rs.getInt("idOficina");
				String tipo = rs.getString("tipoTablaSurf");
				int tamaño = rs.getInt("tamaño");
				TablaSurf tablaSurf = new TablaSurf(idArticulo, precioHoras, idOficina1, tipo, tamaño);
				TablaSurf.add(tablaSurf); // Agregar la tabla a la lista
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		}

		return TablaSurf;
	}
}
