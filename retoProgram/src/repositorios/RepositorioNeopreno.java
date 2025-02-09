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

    public static void insertarNeopreno(Neopreno neopreno) {
        String query1 = "INSERT INTO articulo (precio_horas, idOficina) VALUES (?, ?)";
        String query2 = "INSERT INTO neopreno (grosor, color, talla) VALUES (?, ?, ?)";
        String queryCheck = "SELECT COUNT(*) FROM articulo WHERE idArticulo = ?";

        try (PreparedStatement checkStmt = ConectorBD.conexion.prepareStatement(queryCheck)) {
            checkStmt.setInt(1, neopreno.getIdArticulo());
            ResultSet resultSet = checkStmt.executeQuery();
            resultSet.next();
            if (resultSet.getInt(1) > 0) {
                System.out.println("El Artículo con ID \"" + neopreno.getIdArticulo() + "\" ya existe en la base de datos.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int counter = 0;

        try (PreparedStatement preparedStatement = ConectorBD.conexion.prepareStatement(query1)) {
            preparedStatement.setDouble(1, neopreno.getPrecio_horas());
            preparedStatement.setInt(2, neopreno.getIdOficina());
            preparedStatement.executeUpdate();
            counter++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement preparedStatement = ConectorBD.conexion.prepareStatement(query2)) {
            preparedStatement.setString(1, neopreno.getGrosor());
            preparedStatement.setString(2, neopreno.getColor());
            preparedStatement.setString(3, neopreno.getTalla());
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

    // Método auxiliar: verifica si existe un registro en la tabla 'neopreno'
    private static boolean existeNeopreno(int idArticulo) {
        String query = "SELECT COUNT(*) FROM neopreno WHERE idArticulo = ?";
        try (PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query)) {
            stmt.setInt(1, idArticulo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para eliminar un Neopreno
    public static void eliminarNeopreno(int idArticulo) {
        if (!existeNeopreno(idArticulo)) {
            System.out.println("No se encontró ningún artículo de Neopreno con el ID " + idArticulo);
            return;
        }
        String queryEliminarNeopreno = "DELETE FROM neopreno WHERE idArticulo = ?";
        String queryEliminarArticulo = "DELETE FROM articulo WHERE idArticulo = ?";
        int counter = 0;

        try (PreparedStatement stmtNeopreno = ConectorBD.conexion.prepareStatement(queryEliminarNeopreno)) {
            stmtNeopreno.setInt(1, idArticulo);
            int result = stmtNeopreno.executeUpdate();
            if (result > 0) {
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement stmtArticulo = ConectorBD.conexion.prepareStatement(queryEliminarArticulo)) {
            stmtArticulo.setInt(1, idArticulo);
            int result = stmtArticulo.executeUpdate();
            if (result > 0) {
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (counter == 2) {
            System.out.println("El artículo de Neopreno con ID " + idArticulo + " ha sido eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar completamente el artículo de Neopreno. Verifique que el ID exista y que no existan dependencias.");
        }
    }

    // Clase interna para gestionar inserciones desde consola
    public static class GestionNeopreno {
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

            Neopreno nuevoNeopreno = new Neopreno(0, precioHoras, idOficina, grosor, color, talla);
            insertarNeopreno(nuevoNeopreno);

            System.out.println("¡Neopreno agregado correctamente!");
        }
    }
}