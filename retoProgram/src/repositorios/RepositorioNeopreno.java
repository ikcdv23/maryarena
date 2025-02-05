package repositorios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Neopreno;
import modelo.TablaSurf;

public class RepositorioNeopreno {
	public static List<Neopreno> obtenerNeoprenoPorOficina(int idOficina) {
		List<Neopreno> Neopreno = new ArrayList<>();
		String query = "SELECT a.idArticulo, a.precio_horas, a.idOficina, " + "np.grosor, np.color, np.talla "
				+ "FROM Articulo a " + "JOIN Neopreno np ON a.idArticulo = np.idArticulo " 
				+ "WHERE a.idOficina = ?";

		try (PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query)) {
			stmt.setInt(1, idOficina);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int idArticulo = rs.getInt("idArticulo");
				double precioHoras = rs.getDouble("precio_horas");
				int idOficina1 = rs.getInt("idOficina");
				String grosor = rs.getString("grosor");
				String color = rs.getString("color");
				String talla = rs.getString("talla");
				Neopreno.add(new Neopreno(idArticulo, precioHoras, idOficina1, grosor, color, talla));
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return Neopreno;
	}
}
