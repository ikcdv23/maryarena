package repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Oficina;

public class RepositorioOficina {
	 // Método para obtener todas las oficinas desde la base de datos
    public static List<Oficina> obtenerOficinas() {
    	List<Oficina> oficinas = new ArrayList<>();
        String query = "SELECT * FROM Oficina";

        try (Connection conexion = ConectorBD.conexion;
             PreparedStatement stmt = conexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {//Añadir los valores de cada atributo en MySQL a las variables de eclipse.
                int idOficina = rs.getInt("idOficina");
                String nombre = rs.getString("nombre");
                String localizacion = rs.getString("localizacion");
                int telefono = rs.getInt("telefono");

                // Crear objeto Oficina y añadirlo al array.
                oficinas.add(new Oficina(idOficina, nombre, localizacion, telefono));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return oficinas;
    }
}
