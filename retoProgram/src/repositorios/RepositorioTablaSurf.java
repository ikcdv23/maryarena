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
        String query = "SELECT a.idArticulo, a.precio_horas, a.idOficina, ts.tipo, ts.tamaño " +
                       "FROM Articulo a JOIN TablaSurf ts ON a.idArticulo = ts.idArticulo " +
                       "WHERE a.idOficina = ?";

        try (PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query)) {
            stmt.setInt(1, idOficina);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idArticulo = rs.getInt("idArticulo");
                double precioHoras = rs.getDouble("precio_horas");
                int oficina = rs.getInt("idOficina");
                String tipo = rs.getString("tipo");
                int tamano = rs.getInt("tamaño");
                tablas.add(new TablaSurf(idArticulo, precioHoras, oficina, tipo, tamano));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tablas;
    }

    public static void insertarTablaSurf(TablaSurf tabla) {
        String query1 = "INSERT INTO articulo (precio_horas, idOficina) VALUES (?, ?)";
        String query2 = "INSERT INTO tablasurf (tipo, tamaño) VALUES (?, ?)";
        String queryCheck = "SELECT COUNT(*) FROM articulo WHERE idArticulo = ?";

        try (PreparedStatement checkStmt = ConectorBD.conexion.prepareStatement(queryCheck)) {
            checkStmt.setInt(1, tabla.getIdArticulo());
            ResultSet resultSet = checkStmt.executeQuery();
            resultSet.next();
            if (resultSet.getInt(1) > 0) {
                System.out.println("El Artículo con ID \"" + tabla.getIdArticulo() + "\" ya existe en la base de datos.");
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

    // verificar si existe un registro en la tabla surf
    private static boolean existeTablaSurf(int idArticulo) {
        String query = "SELECT COUNT(*) FROM tablasurf WHERE idArticulo = ?";
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

    // Método para eliminar un artículo de tabla surf
    public static void eliminarTablaSurf(int idArticulo) {
        if (!existeTablaSurf(idArticulo)) {
            System.out.println("No se encontró ningún artículo de TablaSurf con el ID " + idArticulo);
            return;
        }
        String queryEliminarTabla = "DELETE FROM tablasurf WHERE idArticulo = ?";
        String queryEliminarArticulo = "DELETE FROM articulo WHERE idArticulo = ?";
        int counter = 0;

        try (PreparedStatement stmtTabla = ConectorBD.conexion.prepareStatement(queryEliminarTabla)) {
            stmtTabla.setInt(1, idArticulo);
            int result = stmtTabla.executeUpdate();
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
            System.out.println("El artículo de TablaSurf con ID " + idArticulo + " ha sido eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar completamente el artículo de TablaSurf. Verifique que el ID exista y que no existan dependencias.");
        }
    }

    // Clase gestionar inserciones desde consola
    public static class gestionTablaSurf {
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
            System.out.println("¡Tabla de surf agregada correctamente!");
        }
    }
}
