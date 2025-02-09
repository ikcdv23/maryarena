package view;

import java.util.InputMismatchException;
import java.util.Scanner;
import repositorios.RepositorioNeopreno;
import repositorios.RepositorioTablaSurf;
import repositorios.RepositorioOficina;
import repositorios.RepositorioReserva;

public class MenuAdministrador {

    public static void MostrarMenuAdmin() {
        int opcion = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("-----Has iniciado sesión como administrador-----");
        System.out.println("1. Ver todas las reservas");
        System.out.println("2. Añadir Artículos");
        System.out.println("3. Eliminar Artículos");
        System.out.println("4. Añadir Oficinas");
        System.out.println("5. Eliminar Oficinas");
        System.out.println("6. Cerrar sesión");
        System.out.println("7. Cerrar sesión y finalizar el programa");

        try {
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    RepositorioReserva.mostrarReservas();
                    break;
                case 2:
                    System.out.println("---¿Qué artículo desea añadir a la BBDD?---");
                    System.out.println("1. Tablas de surf");
                    System.out.println("2. Neoprenos");
                    System.out.println("3. Salir");
                    int subOpcionAgregar = scanner.nextInt();

                    switch (subOpcionAgregar) {
                        case 1:
                            repositorios.RepositorioTablaSurf.gestionTablaSurf.insertarTablaSurfDesdeConsola();
                            break;
                        case 2:
                            repositorios.RepositorioNeopreno.GestionNeopreno.insertarNeoprenoDesdeConsola();
                            break;
                        default:
                            System.out.println("Opción no válida. Intente de nuevo.");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("---¿Qué artículo desea eliminar?---");
                    System.out.println("1. Tablas de surf");
                    System.out.println("2. Neoprenos");
                    System.out.println("3. Salir");
                    int subOpcionEliminar = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer

                    switch (subOpcionEliminar) {
                        case 1:
                            System.out.print("Ingrese el ID del artículo de TablaSurf a eliminar: ");
                            int idTabla = scanner.nextInt();
                            RepositorioTablaSurf.eliminarTablaSurf(idTabla);
                            break;
                        case 2:
                            System.out.print("Ingrese el ID del artículo de Neopreno a eliminar: ");
                            int idNeopreno = scanner.nextInt();
                            RepositorioNeopreno.eliminarNeopreno(idNeopreno);
                            break;
                        case 3:
                            System.out.println("Saliendo al menú anterior...");
                            break;
                        default:
                            System.out.println("Opción no válida.");
                            break;
                    }
                    break;
                case 4:
                    repositorios.RepositorioOficina.GestionOficina.insertarOficinaDesdeConsola();
                    break;
                case 5:
                    repositorios.RepositorioOficina.GestionOficina.eliminarOficinaDesdeConsola();
                    break;
                case 6:
                    System.out.println("Cerrando sesión...");
                    Menu.mostrarMenuPrincipal();
                    break;
                case 7:
                    System.out.println("Cerrando sesión y finalizando el programa.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe introducir un número.");
            scanner.nextLine(); // Limpiar el buffer del scanner
        }
    }
}
