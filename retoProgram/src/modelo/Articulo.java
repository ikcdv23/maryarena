package modelo;

public class Articulo {

	private int idArticulo;
	private double precio_horas;
	private int idOficina;
	
	public Articulo(int idArticulo, double precio_horas, int idOficina) {
		super();
		this.idArticulo = idArticulo;
		this.precio_horas = precio_horas;
		this.idOficina = idOficina;
	}

	public Articulo() {
		super();
	}

	public int getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(int idArticulo) {
		this.idArticulo = idArticulo;
	}

	public double getPrecio_horas() {
		return precio_horas;
	}

	public void setPrecio_horas(double precio_horas) {
		this.precio_horas = precio_horas;
	}

	public int getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(int idOficina) {
		this.idOficina = idOficina;
	}
	
	
	
}
