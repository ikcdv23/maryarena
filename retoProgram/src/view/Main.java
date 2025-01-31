package view;

import repositorios.ConectorBD;

public class Main {

	public static void main(String[] args) {
		
		//Llamamos a la funcion conectar que esta en ConectorBD
		ConectorBD.conectar();
		
		// Crear un objeto llamado menu para llamar sus funcniones
	    Menu.mostrarMenu();
	    
		
	}

}
