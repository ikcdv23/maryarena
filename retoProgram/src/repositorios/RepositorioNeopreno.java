package repositorios;

import modelo.Neopreno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioNeopreno {

    public static List<Neopreno> obtenerNeoprenoPorOficina(int idOficina) {
        List<Neopreno> neoprenos = new ArrayList<>();
        String sql = "SELECT a.idArticulo, a.precio_horas, n.grosor, n.color, n.talla " +
                     "FROM Articulo a " +
                     "JOIN Neopreno n ON a.idArticulo = n.idArticulo " +
                     "WHERE a.idOficina = ?";

        try {
            // ConectorBD.conectar(); 
            PreparedStatement stmt = ConectorBD.conexion.prepareStatement(sql);
            stmt.setInt(1, idOficina);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Neopreno neopreno = new Neopreno(
                	rs.getInt("idArticulo"),
                	rs.getDouble("precio_horas"),
                    idOficina,
                    rs.getString("grosor"),
                    rs.getString("color"),
                    rs.getString("talla")
                );
                neoprenos.add(neopreno);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return neoprenos;
    }

    public static boolean reservarNeopreno(int idArticulo, String dni, String fecha, String horaInicio, String horaFin) {
        String sql = "INSERT INTO Reserva (dni, idArticulo, fecha, hora_inicio, hora_fin) VALUES (?, ?, ?, ?, ?)";

        try {
            ConectorBD.conectar();
            PreparedStatement stmt = ConectorBD.conexion.prepareStatement(sql);
            stmt.setString(1, dni);
            stmt.setInt(2, idArticulo);
            stmt.setString(3, fecha);
            stmt.setString(4, horaInicio);
            stmt.setString(5, horaFin);

            int rows = stmt.executeUpdate();
            stmt.close();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
		}
	}
}