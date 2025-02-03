package view;

import java.util.Scanner;

import repositorios.ConectorBD;

public class Main {

	public static void main(String[] args) {
		
		ConectorBD.conectar();
		Scanner sc = new Scanner(System.in);

		// Crear un objeto llamado menu para llamar sus funcniones
	    Menu.mostrarMenu(sc);
	    repositorios.RepositorioArticulo.obtenerArticulo(1);
		
	}

}
