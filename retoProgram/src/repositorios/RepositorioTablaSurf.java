package repositorios;

import modelo.TablaSurf;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTablaSurf {

	public static List<TablaSurf> obtenerTablaSurfPorOficina(int idOficina) {
        List<TablaSurf> tablas = new ArrayList<>();
        String sql = "SELECT a.idArticulo, a.precio_horas, t.tipo, t.tamaño " +
                     "FROM Articulo a " +
                     "JOIN TablaSurf t ON a.idArticulo = t.idArticulo " +
                     "WHERE a.idOficina = ?";

        try {
            // ConectorBD.conectar(); // Usa tu conector
            PreparedStatement stmt = ConectorBD.conexion.prepareStatement(sql);
            stmt.setInt(1, idOficina);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TablaSurf tabla = new TablaSurf(
                    rs.getInt("idArticulo"),
                    rs.getDouble("precio_horas"),
                    idOficina,
                    rs.getString("tipo"),
                    rs.getInt("tamaño")
                );
                tablas.add(tabla);
            }

            // Cerrar recursos
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return tablas;
    }

	public static boolean reservarTabla(int idArticulo, String dni, String fecha, String horaInicio, String horaFin) {
		// Verificar si el artículo ya está reservado en ese horario
	    String sqlCheck = "SELECT COUNT(*) FROM Reserva " +
	                      "WHERE idArticulo = ? AND fecha = ? " +
	                      "AND ((hora_inicio <= ? AND hora_fin >= ?) OR (hora_inicio <= ? AND hora_fin >= ?))";

	    try {
	        ConectorBD.conectar();
	        PreparedStatement stmtCheck = ConectorBD.conexion.prepareStatement(sqlCheck);
	        stmtCheck.setInt(1, idArticulo);
	        stmtCheck.setString(2, fecha);
	        stmtCheck.setString(3, horaFin);
	        stmtCheck.setString(4, horaInicio);
	        stmtCheck.setString(5, horaInicio);
	        stmtCheck.setString(6, horaFin);

	        ResultSet rs = stmtCheck.executeQuery();	
	        rs.next();
	        int count = rs.getInt(1);

	        if (count > 0) {
	            System.out.println("El artículo ya está reservado en ese horario.");
	            return false;
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