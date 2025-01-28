package registros;

import java.util.Scanner;
import repositorios.RepositorioUsuario;

public class iniciarSesion {

    public static void iniciarSesion() {
        Scanner sc = new Scanner(System.in);
        
        // Pedir al usuario que ingrese su DNI y contraseña
        System.out.println("Ingrese su DNI:");
        String dni = sc.nextLine();
        System.out.println("Ingrese su contraseña:");
        String contrasena = sc.nextLine();

        // Llamar al método comprobarUsuario de RepositorioUsuario
        try {
            boolean autenticado = RepositorioUsuario.comprobarUsuario(dni, contrasena);
            if (autenticado) {
                System.out.println("Sesión iniciada con éxito.");
                // Aquí puedes añadir el código para mostrar el menú de opciones después de autenticarse
            } else {
                System.out.println("DNI o contraseña incorrectos. Intente nuevamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
