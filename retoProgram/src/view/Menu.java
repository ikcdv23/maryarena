package view;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import modelo.DatosReserva;
import modelo.Neopreno;
import modelo.Oficina;
import modelo.Reserva;
import modelo.TablaSurf;
import repositorios.RepositorioNeopreno;
import repositorios.RepositorioOficina;
import repositorios.RepositorioReserva;
import repositorios.RepositorioTablaSurf;
import repositorios.RepositorioUsuario;

public class Menu {

    private static int idOficina;
    

    
    // Método para solicitar y validar fecha y horas de reserva
    private static DatosReserva solicitarDatosReserva() {
        Scanner scanner = new Scanner(System.in);
        DatosReserva datos = new DatosReserva();

        // Validación de fecha
        while (datos.getFecha() == null) {
            try {
                System.out.print("Ingrese la fecha de la reserva (YYYY-MM-DD): ");
                String fechaSc = scanner.next();
                LocalDate fechaReserva = LocalDate.parse(fechaSc);
                if (fechaReserva.isBefore(LocalDate.now())) {
                    System.out.println("Error: La fecha no puede ser anterior a la fecha actual.");
                } else {
                    datos.setFecha(Date.valueOf(fechaReserva));
                    break;
                }
            } catch (Exception e) {
                System.out.println("Error: Formato de fecha inválido. Use YYYY-MM-DD.");
            }
        }

        // Validación de hora de inicio
        while (datos.getHoraInicio() == null) {
            try {
                System.out.print("Ingrese la hora de inicio (HH:MM): ");
                String horaInicioSc = scanner.next();
                LocalTime horaInicioReserva = LocalTime.parse(horaInicioSc);
                // Si la fecha es hoy, la hora de inicio no puede ser anterior a la actual
                if (LocalDate.now().isEqual(datos.getFecha().toLocalDate())) {
                    if (horaInicioReserva.isBefore(LocalTime.now())) {
                        System.out.println("Error: La hora de inicio no puede ser anterior a la hora actual.");
                        continue;
                    }
                }
                datos.setHoraInicio(Time.valueOf(horaInicioReserva));
                break;
            } catch (Exception e) {
                System.out.println("Error: Formato de hora inválido. Use HH:MM (24 horas).");
            }
        }

        // Validación de hora de fin
        while (datos.getHoraFin() == null) {
            try {
                System.out.print("Ingrese la hora de fin (HH:MM): ");
                String horaFinSc = scanner.next();
                LocalTime horaFinReserva = LocalTime.parse(horaFinSc);
                if (horaFinReserva.isBefore(datos.getHoraInicio().toLocalTime())) {
                    System.out.println("Error: La hora de fin debe ser posterior a la de inicio.");
                } else {
                    datos.setHoraFin(Time.valueOf(horaFinReserva));
                    break;
                }
            } catch (Exception e) {
                System.out.println("Error: Formato de hora inválido. Use HH:MM (24 horas).");
            }
        }
        return datos;
    }
    
    public static void mostrarMenuPrincipal() {
        int opcion = 0;
        Scanner scanner = new Scanner(System.in);

        do {
            // Mostrar menú principal
            System.out.println("-----Bienvenido a Mar&Arena-----");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");

            try {
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("Registrarse:");
                        RepositorioUsuario.registrarUsuario(); // Registro
                        break;
                    case 2:
                        System.out.println("Iniciar sesión:");
                        RepositorioUsuario.iniciarSesion(); // Inicio de sesión
                        break;
                    case 3:
                        System.out.println("Programa finalizado");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe introducir un número.");
                scanner.nextLine(); // Limpiar el buffer
            }

        } while (opcion != 3);
    }

    public static void mostrarMenuOficinas() {
        Scanner sc = new Scanner(System.in);
        List<Oficina> oficinas = RepositorioOficina.obtenerOficinas();

        int opcion;

        // Mostrar oficinas disponibles
        System.out.println("Oficinas disponibles:");
        for (int i = 0; i < oficinas.size(); i++) {
            Oficina oficina = oficinas.get(i);
            System.out.println((i + 1) + ". " + oficina.getNombre());
        }
        System.out.println((oficinas.size() + 1) + ". Cerrar sesión");
        System.out.println((oficinas.size() + 2) + ". Finalizar");

        opcion = sc.nextInt();

        if (opcion >= 1 && opcion <= oficinas.size()) {
            Oficina oficinaSeleccionada = oficinas.get(opcion - 1);
            idOficina = oficinaSeleccionada.getIdOficina();
            System.out.println("Has seleccionado la oficina: " + oficinaSeleccionada.getNombre());
            // Proceder a solicitar datos de reserva y luego mostrar los artículos disponibles
            mostrarArticulos();
        } else if (opcion == oficinas.size() + 1) {
            mostrarMenuPrincipal();
        } else if (opcion == oficinas.size() + 2) {
            System.out.println("Programa finalizado.");
            System.exit(0);
        } else {
            System.out.println("Opción incorrecta. Inténtalo de nuevo.");
        }
    }

    // Primero se solicitan los datos de reserva y luego se pregunta qué artículo se desea reservar.
    public static void mostrarArticulos() {
        // Solicitar datos de reserva (fecha, hora inicio y hora fin)
        DatosReserva datosReserva = solicitarDatosReserva();
        
        int opcion;
        System.out.println("-----¿Qué artículo desea reservar?-----");
        System.out.println("1. Tablas de surf");
        System.out.println("2. Neoprenos");
        opcion = RepositorioUsuario.guardarOpcion();
        switch (opcion) {
            case 1:
                mostrarTablasPorOficina(idOficina, datosReserva);
                break;
            case 2:
                mostrarNeoprenosPorOficina(idOficina, datosReserva);
                break;
            default:
                System.out.println("Opción inválida.");
                break;
        }
    }

    // Método modificado para recibir los datos de reserva y usarlos al confirmar la reserva.
    public static void mostrarTablasPorOficina(int idOficina, DatosReserva datosReserva) {
        // Si es posible, aquí se puede llamar a un método del repositorio que filtre según la fecha/horario.
        List<TablaSurf> tablaSurfDisponibles = RepositorioTablaSurf.obtenerTablaSurfPorOficina(idOficina);
        if (tablaSurfDisponibles.isEmpty()) {
            System.out.println("No hay tablas de surf disponibles en esta oficina para el horario seleccionado.");
        } else {
            System.out.println("Tablas de surf disponibles en esta oficina:");
            for (int i = 0; i < tablaSurfDisponibles.size(); i++) {
                TablaSurf tabla = tablaSurfDisponibles.get(i);
                System.out.println((i + 1) + ". Tabla Surf Tipo: " + tabla.getTipo() + ", Tamaño: " + tabla.getTamaño()
                        + ", Precio: " + tabla.getPrecio_horas() + "€/hora");
            }

            // Solicitar la selección de una tabla de surf
            Scanner sc = new Scanner(System.in);
            System.out.println("Seleccione la tabla para reservar: ");
            int seleccion = sc.nextInt();
            TablaSurf tablaSeleccionada = tablaSurfDisponibles.get(seleccion - 1);

            // Obtener el DNI del usuario logueado
            String dni = RepositorioUsuario.obtenerDniUsuarioLogueado();
            // Realizar la reserva usando los datos ya solicitados
            realizarReserva(tablaSeleccionada.getIdArticulo(), dni, datosReserva.getFecha(), datosReserva.getHoraInicio(),
                    datosReserva.getHoraFin());
        }
    }

    public static void mostrarNeoprenosPorOficina(int idOficina, DatosReserva datosReserva) {
        ArrayList<Neopreno> neoprenosDisponibles = RepositorioNeopreno.obtenerNeoprenoPorOficina(idOficina);
        if (neoprenosDisponibles.isEmpty()) {
            System.out.println("No hay neoprenos disponibles en esta oficina para el horario seleccionado.");
        } else {
            System.out.println("Neoprenos disponibles en esta oficina:");
            for (int i = 0; i < neoprenosDisponibles.size(); i++) {
                Neopreno neopreno = neoprenosDisponibles.get(i);
                System.out.println((i + 1) + ". Neopreno Grosor: " + neopreno.getGrosor() + ", Color: "
                        + neopreno.getColor() + ", Talla: " + neopreno.getTalla() + ", Precio: "
                        + neopreno.getPrecio_horas() + "€/hora");
            }

            // Solicitar la selección de un neopreno
            Scanner sc = new Scanner(System.in);
            System.out.println("Seleccione el neopreno para reservar: ");
            int seleccion = sc.nextInt();
            Neopreno neoprenoSeleccionado = neoprenosDisponibles.get(seleccion - 1);

            // Obtener el DNI del usuario logueado
            String dni = RepositorioUsuario.obtenerDniUsuarioLogueado();
            // Realizar la reserva usando los datos ya solicitados
            realizarReserva(neoprenoSeleccionado.getIdArticulo(), dni, datosReserva.getFecha(), datosReserva.getHoraInicio(),
                    datosReserva.getHoraFin());
        }
    }

    // Método modificado para recibir la fecha y horas de reserva ya definidas.
    public static Reserva realizarReserva(int idArticulo, String dni, Date fecha, Time horaInicio, Time horaFin) {
        // Validación simple del DNI
        if (!dni.matches("\\d{8}[A-Za-z]")) {
            throw new IllegalArgumentException("Error: DNI inválido. Debe tener 8 números seguidos de una letra.");
        }

        // Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setDni(dni);
        reserva.setIdArticulo(idArticulo);
        reserva.setFecha(fecha);
        reserva.setHora_inicio(horaInicio);
        reserva.setHora_fin(horaFin);

        // Guardar la reserva en la base de datos
        boolean guardada = RepositorioReserva.guardarReserva(reserva);

        if (guardada) {
            System.out.println("Reserva guardada con éxito.");
            System.out.println("");
            System.out.println("¿Qué desea hacer ahora?");
            System.out.println("1. Realizar otra reserva");
            System.out.println("2. Cerrar sesión");
            System.out.println("3. Finalizar programa");

            Scanner scanner = new Scanner(System.in);
            int opcion = -1;
            while (opcion < 1 || opcion > 3) {
                try {
                    System.out.print("Seleccione una opción: ");
                    opcion = Integer.parseInt(scanner.next());
                    switch (opcion) {
                        case 1:
                            mostrarArticulos();
                            break;
                        case 2:
                            System.out.println("Sesión cerrada.");
                            mostrarMenuPrincipal();
                            break;
                        case 3:
                            System.out.println("Programa finalizado.");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Opción no válida. Intente nuevamente.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número válido.");
                }
            }
        } else {
            System.out.println("Hubo un problema al guardar la reserva.");
        }
        return reserva;
    }

    // Método auxiliar para validar el formato de la hora (se mantiene si se requiere en algún otro lugar)
    private static void validarFormatoHora(String hora) {
        if (!hora.matches("^([01]\\d|2[0-3]):([0-5]\\d)$")) {
            throw new IllegalArgumentException("Formato de hora inválido. Use HH:MM (24 horas).");
        }
    }
}
