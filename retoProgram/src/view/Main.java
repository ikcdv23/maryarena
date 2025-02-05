package view;

import repositorios.ConectorBD;

public class Main {

	public static void main(String[] args) {

		ConectorBD.conectar();

		// Crear un objeto llamado menu para llamar sus funcniones
		Menu.mostrarMenu();

	}

}
