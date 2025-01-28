package registros;

import java.util.Scanner;
import repositorios.RepositorioUsuario;

public class iniciarSesion {

    public static void iniciarSesion() {
        Scanner sc = new Scanner(System.in);
        
        // Pedir DNI y contraseña
        System.out.println("Ingrese su DNI:");
        String dni = sc.nextLine();
        System.out.println("Ingrese su contraseña:");
        String contrasena = sc.nextLine();

        
        try {
            boolean autenticado = RepositorioUsuario.comprobarUsuario(dni, contrasena);
            if (autenticado) {
                System.out.println("Sesión iniciada.");
                // Aqui llamaremos al segundo menu después
            } else {
                System.out.println("DNI o contraseña incorrectos. Intentalo de nuevo.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
