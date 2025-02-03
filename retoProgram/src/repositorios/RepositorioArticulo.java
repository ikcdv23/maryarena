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
	    String query = "SELECT a.idArticulo, a.precio_horas, a.disponibilidad, a.idOficina, "
	        + "ts.tipo AS tipoTablaSurf, ts.tamaño, " 
	        + "np.grosor, np.color, np.talla " 
	        + "FROM Articulo a "
	        + "LEFT JOIN TablaSurf ts ON a.idArticulo = ts.idArticulo "
	        + "LEFT JOIN Neopreno np ON a.idArticulo = np.idArticulo "
	        + "JOIN oficina o ON a.idOficina = o.idOficina " 
	        + "WHERE a.disponibilidad = 1 AND a.idOficina = ?"; // Corrección aquí

	    ArrayList<Articulo> articulosDisponibles = new ArrayList<>();

	    try (Connection conexion = ConectorBD.conexion;
	         PreparedStatement stmt = conexion.prepareStatement(query)) {
	        stmt.setInt(1, idoficina);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                // ... (resto del código)
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return articulosDisponibles;
	}

}