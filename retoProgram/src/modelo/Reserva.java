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
    private int horas;
    private double precio;

    public Reserva(int idArticulo, Date fecha, Time hora_inicio, Time hora_fin, int horas) {
        this.idArticulo = idArticulo;
        this.fecha = fecha;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.horas = horas;
          // Calcular el precio con la duración de la reserva
    }



	public Reserva() {
		// TODO Auto-generated constructor stub
	}



	// Métodos getter y setter
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

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public double getPrecio() {
        return precio;
    }



    @Override
    public String toString() {
        return "Reserva [idReserva=" + idReserva + ", dni=" + dni + ", idArticulo=" + idArticulo + ", fecha=" + fecha
                + ", hora_inicio=" + hora_inicio + ", hora_fin=" + hora_fin + ", precio=" + precio + "]";
    }
}
