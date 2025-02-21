package com.luisdbb.tarea3AD2024base.modelo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EnvioACasa  extends Servicio{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double peso;
	private int[] volumen = new int[3];
	private boolean urgente = false;
	
	//constructores
	

	public EnvioACasa(Long id, String nombre, double precio, List<Long> paradas, List<Long> contratar, Long id2,
			double peso, int[] volumen, boolean urgente) {
		super(id, nombre, precio, paradas, contratar);
		id = id2;
		this.peso = peso;
		this.volumen = volumen;
		this.urgente = urgente;
	}

	public EnvioACasa(Long id, String nombre, double precio, List<Long> paradas, List<Long> contratar) {
		super(id, nombre, precio, paradas, contratar);
	}

	
	//getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}


	public int[] getVolumen() {
		return volumen;
	}

	public void setVolumen(int[] volumen) {
		this.volumen = volumen;
	}

	public boolean isUrgente() {
		return urgente;
	}

	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}
	
	//metodos

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(volumen);
		result = prime * result + Objects.hash(id, peso, urgente);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnvioACasa other = (EnvioACasa) obj;
		return Objects.equals(id, other.id) && Double.doubleToLongBits(peso) == Double.doubleToLongBits(other.peso)
				&& urgente == other.urgente && Arrays.equals(volumen, other.volumen);
	}

	@Override
	public String toString() {
		return "EnvioACasa [id=" + id + ", peso=" + peso + ", volumen=" + Arrays.toString(volumen) + ", urgente="
				+ urgente + "]";
	}
	
	
	
	
	
}
