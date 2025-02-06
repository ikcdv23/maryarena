package modelo;

public class Neopreno {
    private double grosor;
    private String color;
    private String talla;

    public Neopreno(int idArticulo, double precioHoras, int idOficina1, String grosor2, String color2, String talla2) {
		super();
	}
    
    
    
	public Neopreno() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Neopreno(int id, double grosor, String color, String talla, double precio_horas, boolean disponible) {
		super();

		this.grosor = grosor;
		this.color = color;
		this.talla = talla;
	}


	public double getGrosor() {
		return grosor;
	}

	public void setGrosor(double grosor) {
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
		return "Neopreno [grosor=" + grosor + ", color=" + color + ", talla=" + talla + ", "
				+ super.toString() + "]";
	}   
    
    
}
