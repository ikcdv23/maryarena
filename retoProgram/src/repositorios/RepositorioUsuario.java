package repositorios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Usuario;

public class RepositorioUsuario {
	public static void insertarUsuario(Usuario usuario) {

	    String query = "INSERT INTO Usuario (dni, nombre, apellido, contraseña, rol) VALUES (?, ?, ?, ?, ?)";
	    String queryCheck = "SELECT COUNT(*) FROM Usuario WHERE DNI = ?";

	    try (PreparedStatement checkStmt = ConectorBD.conexion.prepareStatement(queryCheck)) {
	        checkStmt.setString(1, usuario.getDni());
	        ResultSet resultSet = checkStmt.executeQuery();
	        resultSet.next();
	        int count = resultSet.getInt(1);

	        if (count > 0) {
	            System.out.println("El usuario con DNI \"" + usuario.getDni() + "\" ya existe en la base de datos.");
	            return;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    try (PreparedStatement preparedStatement = ConectorBD.conexion.prepareStatement(query)) {
	        preparedStatement.setString(1, usuario.getDni());
	        preparedStatement.setString(2, usuario.getNombre());
	        preparedStatement.setString(3, usuario.getApellido());
	        preparedStatement.setString(4, usuario.getContraseña());
	        preparedStatement.setString(5, usuario.getRol());
	        preparedStatement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	}



	public static boolean comprobarUsuario(String dni, String contraseña) throws SQLException {
        String queryCheck = "SELECT COUNT(*) FROM Usuario WHERE dni = ? AND contraseña = ?";

        try (PreparedStatement checkStmt = ConectorBD.conexion.prepareStatement(queryCheck)) {
            checkStmt.setString(1, dni);  // DNI como String
            checkStmt.setString(2, contraseña);  // Contraseña como String

            ResultSet resultSet = checkStmt.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            return true;  // Si el usuario existe, devuelve true
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Lanza la excepción si ocurre un error en la consulta
        }
    }

}
