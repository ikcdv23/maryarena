package view;

import registros.iniciarSesion;
import registros.registrar;
import repositorios.RepositorioUsuario;
import utilidades.GestionDatos;

public class Menu {

    public void mostrarMenu() {
    
    	 int opcion;
         do {
             // Mostrar menu principal
             System.out.println("Bienvenido al sistema de reservas");
             System.out.println("1. Registrarse");
             System.out.println("2. Iniciar sesión");
             System.out.println("3. Salir");

             opcion = GestionDatos.guardarOpcion(); // Guardar opción del usuario

             switch (opcion) {
                 case 1:
                     System.out.println("Registrarse:");
                     RepositorioUsuario.registrarUsuario(); // Llamamor al registro
                     break;
                 case 2:
                     System.out.println("Iniciar sesión:");
                     RepositorioUsuario.iniciarSesion(); // Llamar al inicio de sesion
                     break;
                 case 3:
                     System.out.println("Programa finalizado"); 
                     break;
                 default:
                     System.out.println("Opción no válida. Intente de nuevo.");
                     break;
             }
         } while (opcion != 3); // El ciclo se repite hasta que el usuario elige "Salir"
     }
}





