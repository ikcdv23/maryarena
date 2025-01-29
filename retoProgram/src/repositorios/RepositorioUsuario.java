package repositorios;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Scanner;

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

	public static void iniciarSesion() {

		Scanner sc = new Scanner(System.in);

		// Pedir DNI y contraseña

		System.out.println("Ingrese su DNI:");

		String dni = sc.nextLine();

		System.out.println("Ingrese su contraseña:");

		String contraseña = sc.nextLine();

		try {

			boolean autenticado = comprobarUsuario(dni, contraseña);

			if (autenticado) {

				System.out.println("Sesión iniciada.");

				// Aquí llamamos al segundo menú después

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

	public static void registrarUsuario() {

		Usuario usuario = GestionDatos.pedirDatosUsuario();

		RepositorioUsuario.insertarUsuario(usuario);

	}

	public static class GestionDatos {

		private static Scanner sc = new Scanner(System.in);

		public static Usuario pedirDatosUsuario() {

			 // validador para que el DN
			int counterDNI = 0;
			String dni;
			do {
				System.out.println("Introduce el DNI de Usuario");
				dni = sc.nextLine();
				if (dni.length() < 9) {
					System.out.println("El DNI no puede tener menos de 9 caracteres ");
				} else {
					counterDNI++;
				}
			} while (counterDNI != 1);

			System.out.println("Introduce el nombre de Usuario");
			String nombre = sc.nextLine();

			System.out.println("Introduce el apellido de Usuario");
			String apellido = sc.nextLine();

			System.out.println("Introduce la contraseña de Usuario");
			String contraseña = sc.nextLine();
			int counterRol = 0;
			String rol;
			do {
				System.out
						.println("Introduce el rol del Usuario (Cliente / Administrador)"); /*
																							 * de momento es string en
																							 * vez problemas con el enum
																							 */
				rol = sc.nextLine();
				rol = rol.toUpperCase();
				if (rol != "CLIENTE" || rol != "ADMINISTRADOR") {
					System.out.println("Este rol no esta disponible.");
				} else {
					counterRol++;
				}
			} while (counterRol != 1);

			Usuario usuario = new Usuario(dni, nombre, apellido, contraseña, rol);

			return usuario;

		}

		public static int guardarOpcion() {

			int eleccion = sc.nextInt();

			return eleccion;

		}

	}

	// Método público estático para comprobar el usuario

	public static boolean comprobarUsuario(String dni, String contraseña) throws SQLException {

		String queryCheck = "SELECT COUNT(*) FROM Usuario WHERE dni = ? AND contraseña = ?";

		try (PreparedStatement checkStmt = ConectorBD.conexion.prepareStatement(queryCheck)) {

			checkStmt.setString(1, dni);

			checkStmt.setString(2, contraseña);

			ResultSet resultSet = checkStmt.executeQuery();

			resultSet.next();

			int count = resultSet.getInt(1);

			return count > 0; //

		} catch (SQLException e) {
			e.printStackTrace();
			throw e; // Lanza la excepción si ocurre un error en la consulta

		}

	}
}