package modelo;

import java.sql.Date;
import java.sql.Time;

public class DatosReserva {
	// Clase auxiliar para almacenar los datos de la reserva
	Date fecha;
	Time horaInicio;
	Time horaFin;
	
	
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Time getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Time horaInicio) {
		this.horaInicio = horaInicio;
	}
	public Time getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(Time horaFin) {
		this.horaFin = horaFin;
	}
	public DatosReserva() {
		super();
	}
	
	
}
