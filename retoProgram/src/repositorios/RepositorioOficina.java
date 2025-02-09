package repositorios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import modelo.Oficina;

public class RepositorioOficina {

    // Método para obtener todas las oficinas desde la base de datos
    public static ArrayList<Oficina> obtenerOficinas() {
        ArrayList<Oficina> oficinas = new ArrayList<>();
        String query = "SELECT * FROM Oficina";

        try (PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idOficina = rs.getInt("idOficina");
                String nombre = rs.getString("nombre");
                String localizacion = rs.getString("localizacion");
                int telefono = rs.getInt("telefono");

                oficinas.add(new Oficina(idOficina, nombre, localizacion, telefono));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oficinas;
    }

    // Metodo para insertar una nueva oficina
    public static void insertarOficina(Oficina oficina) {
        String query = "INSERT INTO Oficina (nombre, localizacion, telefono) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query)) {
            stmt.setString(1, oficina.getNombre());
            stmt.setString(2, oficina.getLocalizacion());
            stmt.setInt(3, oficina.getTelefono());

            int result = stmt.executeUpdate();
            if (result > 0) {
                System.out.println("Oficina insertada correctamente.");
            } else {
                System.out.println("No se pudo insertar la oficina.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar una oficina por su ID
    public static void eliminarOficina(int idOficina) {
        String query = "DELETE FROM Oficina WHERE idOficina = ?";
        try (PreparedStatement stmt = ConectorBD.conexion.prepareStatement(query)) {
            stmt.setInt(1, idOficina);

            int result = stmt.executeUpdate();
            if (result > 0) {
                System.out.println("Oficina eliminada correctamente.");
            } else {
                System.out.println("No se encontró la oficina con ID " + idOficina);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Clase interna para gestionar operaciones desde consola
    public static class GestionOficina {

        // Método para insertar oficina desde consola
        public static void insertarOficinaDesdeConsola() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("--- Introducir nueva oficina ---");

            System.out.print("Ingrese el nombre de la oficina: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese la localización de la oficina: ");
            String localizacion = scanner.nextLine();

            System.out.print("Ingrese el teléfono de la oficina: ");
            int telefono = scanner.nextInt();

            Oficina nuevaOficina = new Oficina(0, nombre, localizacion, telefono);
            insertarOficina(nuevaOficina);
            System.out.println("¡Oficina agregada correctamente!");
        }

        // Método para eliminar oficina desde consola
        public static void eliminarOficinaDesdeConsola() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("--- Eliminar oficina ---");

            // Mostrar las oficinas actuales para que el usuario conozca los IDs
            ArrayList<Oficina> oficinas = obtenerOficinas();
            if (oficinas.isEmpty()) {
                System.out.println("No hay oficinas registradas.");
                return;
            } else {
                System.out.println("Oficinas registradas:");
                for (Oficina of : oficinas) {
                    System.out.println(of);
                }
            }

            System.out.print("Ingrese el ID de la oficina a eliminar: ");
            int idOficina = scanner.nextInt();
            eliminarOficina(idOficina);
        }
    }
}
