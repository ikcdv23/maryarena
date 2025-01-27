package registros;

import modelo.Usuario;
import repositorios.RepositorioUsuario;
import utilidades.GestionDatos;

public class registrar {
    
	public void registrarUsuario() {
		Usuario usuario = GestionDatos.pedirDatosUsuario();
		RepositorioUsuario.insertarUsuario(usuario);
	}

}
