package ec.edu.ups.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibm.icu.text.SimpleDateFormat;

@Entity
@Table(name = "Reservas")
public class Reserva implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "rev_id_seq", sequenceName = "rev_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rev_id_seq")
	@Column(name = "rev_id", updatable = false, nullable = false, unique = true)
	private int id;
	
	@Column(name = "rev_fecha", length = 20, nullable = false)
	
	private LocalDate fecha;
	
	@Column(name = "rev_hora", length = 20, nullable = false)
	private LocalTime hora;
	
	@Column(name = "rev_nasistente", nullable = false)
	private int nasistentes;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "rev_cliente_id")
	private Cliente cliente;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "rev_restaurante_id")
	private Restaurante restaurante;
	
	
	public Reserva() {
		// TODO Auto-generated constructor stub
	}
	
	public Reserva(int id, LocalDate fecha, LocalTime hora,  Cliente cliente,int asistentes ,Restaurante restaurante) {
		this.id = id;
		this.fecha = fecha;
		this.hora =  hora;
		this.cliente = cliente;
		this.nasistentes = asistentes;
		this.restaurante = restaurante;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public int getNasistentes() {
		return nasistentes;
	}

	public void setNasistentes(int nasistentes) {
		this.nasistentes = nasistentes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + id;
		result = prime * result + nasistentes;
		result = prime * result + ((restaurante == null) ? 0 : restaurante.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (id != other.id)
			return false;
		if (nasistentes != other.nasistentes)
			return false;
		if (restaurante == null) {
			if (other.restaurante != null)
				return false;
		} else if (!restaurante.equals(other.restaurante))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", fecha=" + fecha + ", hora=" + hora + ", nasistentes=" + nasistentes
				+ ", cliente=" + cliente + ", restaurante=" + restaurante + "]";
	}
}