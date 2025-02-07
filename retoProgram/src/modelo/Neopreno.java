package modelo;

public class Neopreno extends Articulo {
	private String grosor;
	private String color;
	private String talla;

	public Neopreno(int idArticulo, double precio_horas, int idOficina, String grosor, String color, String talla) {
		super(idArticulo, precio_horas, idOficina);
		this.grosor = grosor;
		this.color = color;
		this.talla = talla;
	}

	public Neopreno() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Neopreno(int idArticulo, double precio_horas, int idOficina) {
		super(idArticulo, precio_horas, idOficina);
		// TODO Auto-generated constructor stub
	}

	public String getGrosor() {
		return grosor;
	}

	public void setGrosor(String grosor) {
		this.grosor = grosor;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	@Override
	public String toString() {
		return "Neopreno [grosor=" + grosor + ", color=" + color + ", talla=" + talla + ", " + super.toString() + "]";
	}

}
