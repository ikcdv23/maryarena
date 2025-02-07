package repositorios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import modelo.TablaSurf;

public class RepositorioTablaSurf {
	public static ArrayList<TablaSurf> obtenerTablaSurfPorOficina(int idOficina) {
		ArrayList<TablaSurf> tablas = new ArrayList<>();
		String query = "SELECT a.idArticulo, a.precio_horas, a.idOficina, ts.tipo, ts.tamaño " + "FROM Articulo a "
				+ "JOIN TablaSurf ts ON a.idArticulo = ts.idArticulo " + "WHERE a.idOficina = ?";

		try (PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query)) {
			stmt.setInt(1, idOficina);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int idArticulo = rs.getInt("idArticulo");
				double precioHoras = rs.getDouble("precio_horas");
				idOficina = rs.getInt("idOficina");
				String tipo = rs.getString("tipo");
				int tamano = rs.getInt("tamaño");
				tablas.add(new TablaSurf(idArticulo, precioHoras, idOficina, tipo, tamano));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tablas;
	}

	public static void insertarTablaSurf(TablaSurf tabla) {
		String query1 = "INSERT INTO articulo (precio_hora, idOficina) VALUES (?, ?)";
		String query2 = "INSERT INTO tablasurf (tipo, tamaño) VALUES (?, ?)";
		String queryCheck = "SELECT COUNT(*) FROM articulo WHERE idArticulo = ?";

		try (PreparedStatement checkStmt = ConectorBD.conexion.prepareStatement(queryCheck)) {
			checkStmt.setInt(1, tabla.getIdArticulo());
			ResultSet resultSet = checkStmt.executeQuery();
			resultSet.next();
			if (resultSet.getInt(1) > 0) {
				System.out
						.println("El Artículo con ID \"" + tabla.getIdArticulo() + "\" ya existe en la base de datos.");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int counter = 0;

		try (PreparedStatement preparedStatement = ConectorBD.conexion.prepareStatement(query1)) {
			preparedStatement.setDouble(1, tabla.getPrecio_horas());
			preparedStatement.setInt(2, tabla.getIdOficina());
			preparedStatement.executeUpdate();
			counter++;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try (PreparedStatement preparedStatement = ConectorBD.conexion.prepareStatement(query2)) {
			preparedStatement.setString(1, tabla.getTipo());
			preparedStatement.setDouble(2, tabla.getTamaño());
			preparedStatement.executeUpdate();
			counter++;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (counter == 2) {
			System.out.println("El Artículo ha sido creado correctamente");
		} else {
			System.out.println("Error al almacenar el artículo");
		}
	}

	public class gestionTablaSurf {

		public static void insertarTablaSurfDesdeConsola() {
			Scanner scanner = new Scanner(System.in);

			System.out.println("--- Introducir nueva tabla de surf ---");
			System.out.print("Ingrese el precio por hora: ");
			double precioHoras = scanner.nextDouble();

			System.out.print("Ingrese el ID de la oficina: ");
			int idOficina = scanner.nextInt();
			scanner.nextLine();

			System.out.print("Ingrese el tipo de tabla: ");
			String tipo = scanner.nextLine();

			System.out.print("Ingrese el tamaño de la tabla: ");
			int tamano = scanner.nextInt();

			TablaSurf nuevaTabla = new TablaSurf(0, precioHoras, idOficina, tipo, tamano);
			insertarTablaSurf(nuevaTabla);
			scanner.close();
			System.out.println("¡Tabla de surf agregada correctamente!");
		}
	}
}