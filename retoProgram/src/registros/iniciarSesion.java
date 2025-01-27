package registros;

import java.util.Scanner;

public class iniciarSesion {
	public void iniciarSesion() {
		boolean autenticado = false;

		// Verificar autenticación con DNI y contraseña
		while (!autenticado) {
			System.out.println("Ingrese su DNI:");
			String dni = sc.nextLine();
			System.out.println("Ingrese su contraseña:");
			String contrasena = sc.nextLine();

			if (/*comprobrar si los datos son correctos en los repositorios de inicio de sesión*/) {
				autenticado = true;
			} else {
				System.out.println("DNI o contraseña incorrectos. Intente nuevamente.");
			}
		}

	}
}