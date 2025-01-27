package modelo;

public class Oficina {
	private int idOficina;
	private String nombre;
	private String localizacion;
	private int telefono;
	
	public Oficina(int idOficina, String nombre, String localizacion, int telefono) {
		super();
		this.idOficina = idOficina;
		this.nombre = nombre;
		this.localizacion = localizacion;
		this.telefono = telefono;
	}
	
	public Oficina() {
		super();
	}

	public int getIdOficina() {
		return idOficina;
	}
	public void setIdOficina(int idOficina) {
		this.idOficina = idOficina;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getLocalizacion() {
		return localizacion;
	}
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	
	
}
