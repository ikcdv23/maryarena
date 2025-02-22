package repositorios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import modelo.Usuario;
import view.Menu;
import view.MenuAdministrador;

public class RepositorioUsuario {

//Método para registrar usuario
	public static void registrarUsuario() {
		Usuario usuario = pedirDatosUsuario();
		insertarUsuario(usuario);
	}

//Método para pedir datos al al usuario
	private static Usuario pedirDatosUsuario() {
		Scanner sc = new Scanner(System.in);
		String dni;
		// Validación del DNI: 8 números seguidos de 1 letra
		do {
			System.out.println("Introduce el DNI de Usuario (8 números seguidos de 1 letra):");
			dni = sc.nextLine();
			if (!dni.matches("^[0-9]{8}[A-Za-z]$")) {
				System.out.println("Error: El DNI debe tener 8 números seguidos de 1 letra.");
			}
		} while (!dni.matches("^[0-9]{8}[A-Za-z]$"));

		System.out.println("Introduce el nombre de Usuario:");
		String nombre = sc.nextLine();

		System.out.println("Introduce el apellido de Usuario:");
		String apellido = sc.nextLine();

		String contraseña;
		// Validación de la contraseña: 8 caracteres, al menos una letra y al menos 1
		// número
		do {
			System.out.println(
					"Introduce la contraseña de Usuario (Un mínimo de 8 caracteres, al menos una letra y al menos 1 número):");
			contraseña = sc.nextLine();
			if (!contraseña.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
				System.out.println(
						"Error: La contraseña debe tener un mínimo de 8 caracteres, al menos una letra y al menos 1 número.");
			}
		} while (!contraseña.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"));

		String rol;
		// Validación del rol: solo se acepta "administrador" o "cliente" (en
		// minúsculas)
		do {
			System.out.println("Introduce el rol del Usuario (Administrador / Cliente):");
			rol = sc.nextLine().toLowerCase();
			if (!rol.equals("administrador") && !rol.equals("cliente")) {
				System.out.println("Rol no válido. Debe ser 'Administrador' o 'Cliente'.");
			}
		} while (!rol.equals("administrador") && !rol.equals("cliente"));

		return new Usuario(dni, nombre, apellido, contraseña, rol);
	}

//Método para insertar el usuario en la base de datos.
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

//Método para iniciar sesion
	private static String dniUsuarioLogueado;

	public static void iniciarSesion() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese su DNI:");
		String dni = sc.nextLine();
		System.out.println("Ingrese su contraseña:");
		String contraseña = sc.nextLine();

		try {
			boolean autenticado = comprobarUsuario(dni, contraseña); // Llama al método para comprobar si el DNI y
																		// contraseña coinciden en la base de datos.

			if (autenticado) {
				// Si el usuario se autentica, guardamos el DNI en la variable estática
				dniUsuarioLogueado = dni;
				System.out.println("Sesión iniciada.");

				// llamar al comprobar rol
				String rol = comprobarRol(dni);

				if (rol.equalsIgnoreCase("cliente")) {
					Menu.mostrarMenuOficinas();
				} else if (rol.equalsIgnoreCase("administrador")) {
					MenuAdministrador.MostrarMenuAdmin();
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

	// Método para obtener el DNI del usuario logueado
	public static String obtenerDniUsuarioLogueado() {
		return dniUsuarioLogueado;
	}

//Método para comprobar Usuario por DNI y contraseña
	public static boolean comprobarUsuario(String dni, String contraseña) throws SQLException {
		String queryCheck = "SELECT COUNT(*) FROM Usuario WHERE dni = ? AND contraseña = ?";

		// Nos conectamos
		try (PreparedStatement checkStmt = ConectorBD.conexion.prepareStatement(queryCheck)) {
			checkStmt.setString(1, dni);
			checkStmt.setString(2, contraseña);

			ResultSet resultSet = checkStmt.executeQuery();
			resultSet.next();

			// Si el resultado es mayor que 0, el usuario existe
			return resultSet.getInt(1) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e; // Lanza la excepción en caso de error
		}
	}

	//
	public static String comprobarRol(String dni) throws SQLException {
		String query = "SELECT rol FROM Usuario WHERE dni = ?";

		try (PreparedStatement checkStmt = ConectorBD.conexion.prepareStatement(query)) {
			checkStmt.setString(1, dni);

			ResultSet rs = checkStmt.executeQuery();
			rs.next();

			return rs.getString("rol");
		} catch (SQLException e) {
			e.printStackTrace();
			throw e; // Lanza la excepción en caso de error
		}
	}

//Método para guardar opción.
	public static int guardarOpcion() {
		Scanner sc = new Scanner(System.in);
		return sc.nextInt();
	}
}