package repositorios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import modelo.Neopreno;
import modelo.TablaSurf;

public class RepositorioNeopreno {
	public static ArrayList<Neopreno> obtenerNeoprenoPorOficina(int idOficina) {
		ArrayList<Neopreno> Neopreno = new ArrayList<>();
		String query = "SELECT a.idArticulo, a.precio_horas, a.idOficina, " + "np.grosor, np.color, np.talla "
				+ "FROM Articulo a " + "JOIN Neopreno np ON a.idArticulo = np.idArticulo " + "WHERE a.idOficina = ?";

		try (PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query)) {
			stmt.setInt(1, idOficina);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int idArticulo = rs.getInt("idArticulo");
				double precioHoras = rs.getDouble("precio_horas");
				idOficina = rs.getInt("idOficina");
				String grosor = rs.getString("grosor");
				String color = rs.getString("color");
				String talla = rs.getString("talla");
				Neopreno.add(new Neopreno(idArticulo, precioHoras, idOficina, grosor, color, talla));
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return Neopreno;
	}

//metodo para insertar los neoprenos en la base de datos, es igual que lo usado en el repositorio de usuarios para insertarlos en la BBDD
	public static void insertarNeopreno(Neopreno neopreno) {
		// query = consulta
		String query1 = "INSERT INTO articulo (precio_hora, idOficina ) VALUES (?, ?)";
		String query2 = "INSERT INTO neopreno (grosor, color, talla) VALUES (?, ?, ?)";
		String queryCheck = "SELECT COUNT(*) FROM articulo WHERE idArticulo = ?";

		try (PreparedStatement checkStmt = ConectorBD.conexion.prepareStatement(queryCheck)) {
			checkStmt.setInt(1, neopreno.getIdArticulo());
			ResultSet resultSet = checkStmt.executeQuery();
			resultSet.next();
			int count = resultSet.getInt(1);

			if (count > 0) {
				System.out.println(
						"El Articulo con ID \"" + neopreno.getIdArticulo() + "\" ya existe en la base de datos.");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int counter = 0;

		try (PreparedStatement preparedStatement = ConectorBD.conexion.prepareStatement(query1)) {
			preparedStatement.setString(2, neopreno.getColor());
			preparedStatement.setString(1, neopreno.getGrosor());
			preparedStatement.setString(1, neopreno.getTalla());
			preparedStatement.executeUpdate();

			counter++;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (PreparedStatement preparedStatement = ConectorBD.conexion.prepareStatement(query2)) {
			preparedStatement.setDouble(1, neopreno.getPrecio_horas());
			preparedStatement.setDouble(1, neopreno.getIdOficina());
			preparedStatement.executeUpdate();

			counter++;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (counter == 2) {
			System.out.println("El Articulo ha sido creado correctamente");
		} else {
			System.out.println("Error alacenando el articulo");
		}

	}

	public class GestionNeopreno {

		public static void insertarNeoprenoDesdeConsola() {
			Scanner scanner = new Scanner(System.in);

			System.out.println("--- Introducir nuevo neopreno ---");

			System.out.print("Ingrese el precio por hora: ");
			double precioHoras = scanner.nextDouble();

			System.out.print("Ingrese el ID de la oficina: ");
			int idOficina = scanner.nextInt();
			scanner.nextLine(); // Consumir la nueva línea

			System.out.print("Ingrese el grosor del neopreno: ");
			String grosor = scanner.nextLine();

			System.out.print("Ingrese el color del neopreno: ");
			String color = scanner.nextLine();

			System.out.print("Ingrese la talla del neopreno: ");
			String talla = scanner.nextLine();

			// Crear el objeto Neopreno con los datos ingresados
			Neopreno nuevoNeopreno = new Neopreno(0, precioHoras, idOficina, grosor, color, talla);

			// Insertar en la base de datos
			RepositorioNeopreno.insertarNeopreno(nuevoNeopreno);

			System.out.println("¡Neopreno agregado correctamente!");
		}
	}
}
