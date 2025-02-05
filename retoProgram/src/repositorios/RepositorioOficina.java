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
    public static ArrayList<Oficina> obtenerOficinas() {
        List<Oficina> oficinas = new ArrayList<>();
        String query = "SELECT * FROM Oficina";
        
        // Usamos la conexion
        try (PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Añadimos todos los atributos de cada tupla a las variables.
                int idOficina = rs.getInt("idOficina");
                String nombre = rs.getString("nombre");
                String localizacion = rs.getString("localizacion");
                int telefono = rs.getInt("telefono");

                // Creamos un objeto oficina, y lo añadimos al arrrayList
                oficinas.add(new Oficina(idOficina, nombre, localizacion, telefono));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (ArrayList<Oficina>) oficinas;
    }
}