package view;

import java.util.Scanner;

import modelo.Usuario;
import registros.registrar;
import repositorios.RepositorioUsuario;
import utilidades.GestionDatos;

public class Menu {

    // Simulamos los datos de registro
    private String dniRegistrado;
    private String contrasenaRegistrada;

    // Constructor
    public Menu() {
        this.dniRegistrado = "";
        this.contrasenaRegistrada = "";
    }

    public void mostrarMenu() {
    

        // Mostrar menú de inicio
        System.out.println("Bienvenido al sistema de reservas");
        System.out.println("1. Registrarse");
        System.out.println("2. Registrarse");
        System.out.println("3. Salir");

        int opcion = GestionDatos.

        switch (opcion) {
            case 1:
                registrarUsuario();
                break;
            case 2:
                System.out.println("¡Hasta luego!");
                return;
            case 2:
                System.out.println("¡Hasta luego!");
                return;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
                mostrarMenu();
        }
    }
    public void registrarUsuario() {
    	Usuario usuario=GestionDatos.pedirDatosUsuario();
    	RepositorioUsuario.insertarUsuario(usuario);
    }

    private void registrarUsuario(Scanner sc) {
        // Pedir DNI y contraseña
        System.out.println("Ingrese su DNI:");
        dniRegistrado = sc.nextLine();
        System.out.println("Ingrese su contraseña:");
        contrasenaRegistrada = sc.nextLine();

        System.out.println("¡Registro exitoso!");

        // Mostrar el siguiente menú
        mostrarMenuOpciones(sc);
    }

    private void mostrarMenuOpciones(Scanner sc) {
        boolean autenticado = false;

        // Verificar autenticación con DNI y contraseña
        while (!autenticado) {
            System.out.println("Ingrese su DNI:");
            String dni = sc.nextLine();
            System.out.println("Ingrese su contraseña:");
            String contrasena = sc.nextLine();

            if (dni.equals(dniRegistrado) && contrasena.equals(contrasenaRegistrada)) {
                autenticado = true;
            } else {
                System.out.println("DNI o contraseña incorrectos. Intente nuevamente.");
            }
        }

        // Mostrar el menú después de la autenticación
        while (true) {
            System.out.println("\nMenu de opciones:");
            System.out.println("1. Reservar equipo");
            System.out.println("2. Comprobar reserva");
            System.out.println("3. Eliminar reserva");
            System.out.println("4. Salir");

            int opcion = sc.nextInt();
            sc.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1:
                    System.out.println("Usted ha elegido reservar un equipo.");
                    break;
                case 2:
                    System.out.println("Usted ha elegido comprobar su reserva.");
                    break;
                case 3:
                    System.out.println("Usted ha elegido eliminar su reserva.");
                    break;
                case 4:
                    System.out.println("¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.mostrarMenu();
    }
}

