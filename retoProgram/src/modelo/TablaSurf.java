package modelo;

public class TablaSurf extends Articulo {
    private String tipo;   
    private int tamaño;    

    
    public TablaSurf(int idArticulo, double precioHoras, int idOficina, String tipo, int tamaño) {
        super(idArticulo, precioHoras, idOficina);  
        this.tipo = tipo;
        this.tamaño = tamaño;
    }

    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

	@Override
	public String toString() {
		return "TablaSurf [tipo=" + tipo + ", tamaño=" + tamaño + ", " + super.toString() + "]";
	}
}


  
