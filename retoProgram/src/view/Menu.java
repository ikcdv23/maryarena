package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.Articulo;
import modelo.Neopreno;
import modelo.Oficina;
import modelo.TablaSurf;
import repositorios.RepositorioArticulo;
import repositorios.RepositorioOficina;
import repositorios.RepositorioUsuario;

public class Menu {

public static void mostrarMenu() {

int opcion;
do {
// Mostrar menu principal
System.out.println("Bienvenido al sistema de reservas");
System.out.println("1. Registrarse");
System.out.println("2. Iniciar sesión");
System.out.println("3. Salir");

opcion = RepositorioUsuario.guardarOpcion();

switch (opcion) {
case 1:
System.out.println("Registrarse:");
RepositorioUsuario.registrarUsuario(); // Llamamos al registro
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
} while (opcion != 3); // Bucle hasta que pulsemos el botón de salir.
}


public static void mostrarMenuOficinas() {
Scanner sc = new Scanner(System.in);
List<Oficina> oficinas = RepositorioOficina.obtenerOficinas(); // Obtener las oficinas de la base de datos y guardarlas en el arrayList.

int opcion;
do {
System.out.println("Oficinas disponibles: ");
// Mostrar oficina
for (int i = 0; i < oficinas.size(); i++) {
Oficina oficina = oficinas.get(i);
System.out.println((i + 1) + ". " + oficina.getNombre());
}
// Opción para finalizar
System.out.println((oficinas.size() + 1) + ". Volver al menú anterior");
System.out.println((oficinas.size() + 2) + ". Finalizar");

// Leer la opción del usuario
opcion = RepositorioUsuario.guardarOpcion();

// Lógica para manejar la opción seleccionada
if (opcion >= 1 && opcion <= oficinas.size()) {
Oficina oficinaSeleccionada = oficinas.get(opcion - 1);
System.out.println(oficinaSeleccionada.getNombre());
mostrarMenuArticulo();
} else if (opcion == oficinas.size() + 1) {
mostrarMenu();
} else if (opcion == oficinas.size() + 2) {
System.out.println("Programa finalizado");
} else {
System.out.println("Opción incorrecta. Inténtalo de nuevo.");
}

} while (opcion != oficinas.size() + 1); // Opción para finalizar
}
    public static void mostrarMenuArticulo() {
    RepositorioArticulo.obtenerArticulo();
    menuArticulo();
    }
   
    //Mostrar los articulos disponibles
    public static void menuArticulo() {
        List<Articulo> articulosDisponibles = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("Artículos disponibles:");
            for (int i = 0; i < articulosDisponibles.size(); i++) {
                Articulo articulo = articulosDisponibles.get(i);
                if (articulo instanceof TablaSurf) {
                    TablaSurf tabla = (TablaSurf) articulo;
                    System.out.println((i + 1) + ". Tabla Surf - idArticulo: " +tabla.getIdArticulo()+ " Disponibilidad: "+ tabla.isDisponibilidad()+ "idOficina: "+ tabla.getIdOficina()+ " Tipo: " + tabla.getTipo() + "Tamaño: " + tabla.getTamaño() + ", Precio: " + tabla.getPrecio_horas() + "€/hora");
                } else if (articulo instanceof Neopreno) {
                    Neopreno neopreno = (Neopreno) articulo;
                    System.out.println((i + 1) + ". Neopreno - idArticulo: "+neopreno.getIdArticulo()+  " Disponibilidad: "+ neopreno.isDisponibilidad()+ "idOficina: "+ neopreno.getIdOficina()+"Grosor: " + neopreno.getGrosor() + ", Talla: " + neopreno.getTalla() + ", Precio: " + neopreno.getPrecio_horas() + "€/hora");
                }
            }
            System.out.println((articulosDisponibles.size() + 1) + ". Finalizar");

            opcion = sc.nextInt();
            if (opcion >= 1 && opcion <= articulosDisponibles.size()) {
                Articulo articuloSeleccionado = articulosDisponibles.get(opcion - 1);
                System.out.println("Has seleccionado el artículo: " + articuloSeleccionado.getIdArticulo());
            } else if (opcion == articulosDisponibles.size() + 1) {
                System.out.println("Programa finalizado. ");
                System.exit(0);
            } else {
                System.out.println("Opción no válida.");
            }

        } while (opcion != articulosDisponibles.size() + 1);
   
}
}
