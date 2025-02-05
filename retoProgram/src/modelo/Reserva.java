
package modelo;

import java.sql.Date;
import java.sql.Time;

public class Reserva {
	private int idReserva;
	private String dni;
	private int idArticulo;
	private Date fecha;
	private Time hora_inicio;
	private Time hora_fin;
	
	public Reserva(int idReserva, String dni, int idArticulo, Date fecha, Time hora_inicio, Time hora_fin) {
		super();
		this.idReserva = idReserva;
		this.dni = dni;
		this.idArticulo = idArticulo;
		this.fecha = fecha;
		this.hora_inicio = hora_inicio;
		this.hora_fin = hora_fin;
	}

	public Reserva() {
		super();
	}

	public int getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public int getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(int idArticulo) {
		this.idArticulo = idArticulo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Time getHora_inicio() {
		return hora_inicio;
	}

	public void setHora_inicio(Time hora_inicio) {
		this.hora_inicio = hora_inicio;
	}

	public Time getHora_fin() {
		return hora_fin;
	}

	public void setHora_fin(Time hora_fin) {
		this.hora_fin = hora_fin;
	}

	@Override
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", dni=" + dni + ", idArticulo=" + idArticulo + ", fecha=" + fecha
				+ ", hora_inicio=" + hora_inicio + ", hora_fin=" + hora_fin + "]";
	}
	
	
	
	
	
	
}
