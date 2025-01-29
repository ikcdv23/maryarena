package repositorios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import modelo.Usuario;
import view.Menu;

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

			System.out.println("El usuario ha sido creado correctamente.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void iniciarSesion() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese su DNI:");
		String dni = sc.nextLine();
		System.out.println("Ingrese su contraseña:");
		String contraseña = sc.nextLine();

		try {
			boolean autenticado = comprobarUsuario(dni, contraseña);
			if (autenticado) {
				System.out.println("Sesión iniciada.");
				Usuario usuario = new Usuario();
				usuario.setDni(dni);
				usuario.setContraseña(contraseña);
				String rol = obtenerRolUsuario(dni);
				usuario.setRol(rol);
				if(rol.equals("ADMINISTRADOR")) {
					Menu.menuReservas();
				}
			} else {
				System.out.println("DNI o contraseña incorrectos. Inténtalo de nuevo.");
			}
		} catch (SQLException e) {
			System.out.println("Error en la base de datos. Por favor, inténtalo más tarde.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error inesperado.");
			e.printStackTrace();
		}
	}

	public static Usuario registrarUsuario() {
		Usuario usuario = pedirDatosUsuario();
		insertarUsuario(usuario);
		return usuario;
	}

	private static Usuario pedirDatosUsuario() {
		Scanner sc = new Scanner(System.in);
		String dni;
		do {
			System.out.println("Introduce el DNI de Usuario (9 caracteres):");
			dni = sc.nextLine();
			if (dni.length() != 9) {
				System.out.println("El DNI debe tener exactamente 9 caracteres.");
			}
		} while (dni.length() != 9);

		System.out.println("Introduce el nombre de Usuario:");
		String nombre = sc.nextLine();

		System.out.println("Introduce el apellido de Usuario:");
		String apellido = sc.nextLine();

		System.out.println("Introduce la contraseña de Usuario:");
		String contrase\u00f1a = sc.nextLine();

		String rol;
		do {
			System.out.println("Introduce el rol del Usuario (Administrador / Cliente):");
			rol = sc.nextLine().toLowerCase();
			if (!rol.equals("administrador") && !rol.equals("cliente")) {
				System.out.println("Rol no apellido. Debe ser 'Administrador' o 'Cliente'.");
			}
		} while (!rol.equals("administrador") && !rol.equals("cliente"));

		return new Usuario(dni, nombre, apellido, contraseña, rol);
	}

	public static int guardarOpcion() {
		Scanner sc = new Scanner(System.in);
		return sc.nextInt();
	}

	public static boolean comprobarUsuario(String dni, String contraseña) throws SQLException {
		String queryCheck = "SELECT COUNT(*) FROM Usuario WHERE dni = ? AND contraseña = ?";
		try (PreparedStatement checkStmt = ConectorBD.conexion.prepareStatement(queryCheck)) {
			checkStmt.setString(1, dni);
			checkStmt.setString(2, contraseña);
			ResultSet resultSet = checkStmt.executeQuery();
			resultSet.next();
			return resultSet.getInt(1) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static String obtenerRolUsuario(String dni) throws SQLException {
		String query = "SELECT rol FROM Usuario WHERE dni = ?";
		try (PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query)) {
			stmt.setString(1, dni);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("rol");
			}
		}
		return null;
	}
}
