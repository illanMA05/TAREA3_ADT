package com.luisdbb.tarea3AD2024base.modelo;

import java.util.Objects;

public class Contratar {

	private Long id;
	private Long conjContraId;
	private Long servicioId;
	
	//constructores
	public Contratar(Long id, Long conjContraId, Long servicioId) {
		super();
		this.id = id;
		this.conjContraId = conjContraId;
		this.servicioId = servicioId;
	}
	
	public Contratar () {}

	
	//getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getConjContraId() {
		return conjContraId;
	}

	public void setConjContraId(Long conjContraId) {
		this.conjContraId = conjContraId;
	}

	public Long getServicioId() {
		return servicioId;
	}

	public void setServicioId(Long servicioId) {
		this.servicioId = servicioId;
	}

	//metodos
	
	@Override
	public int hashCode() {
		return Objects.hash(conjContraId, id, servicioId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contratar other = (Contratar) obj;
		return Objects.equals(conjContraId, other.conjContraId) && Objects.equals(id, other.id)
				&& Objects.equals(servicioId, other.servicioId);
	}

	@Override
	public String toString() {
		return "Contratar [id=" + id + ", conjContraId=" + conjContraId + ", servicioId=" + servicioId + "]";
	}
	
	
	
}
