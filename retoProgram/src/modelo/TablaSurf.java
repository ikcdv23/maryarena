package modelo;

public class TablaSurf extends Articulo {
	private String tipo;
	private double tamaño;

	public TablaSurf(int idArticulo, double precio_horas, int idOficina, String tipo, double tamaño) {
		super(idArticulo, precio_horas, idOficina);
		this.tipo = tipo;
		this.tamaño = tamaño;
	}

	public TablaSurf(int idArticulo, double precio_horas, int idOficina) {
		super(idArticulo, precio_horas, idOficina);
	}

	public TablaSurf() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getTamaño() {
		return tamaño;
	}

	public void setTamaño(double tamaño) {
		this.tamaño = tamaño;
	}

}
