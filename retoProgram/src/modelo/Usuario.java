package modelo;

public class Usuario {

	private String dni;
	private String nombre;
	private String apellido;
	private String contraseña;
	private rolUsuario rol;
	
	
	public Usuario() {
		super();
	}
	
	public Usuario(String dni, String nombre, String apellido, String contraseña, rolUsuario cliente) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.contraseña = contraseña;
<<<<<<< HEAD
		this.rol = cliente;
=======
		this.rol = rol;
>>>>>>> branch 'master' of https://github.com/ikcdv23/maryarena.git
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public rolUsuario getCliente() {
		return rol;
	}
	public void setCliente(rolUsuario cliente) {
<<<<<<< HEAD
		this.rol = cliente;
=======
		this.rol = rol;
>>>>>>> branch 'master' of https://github.com/ikcdv23/maryarena.git
	}

	
}
