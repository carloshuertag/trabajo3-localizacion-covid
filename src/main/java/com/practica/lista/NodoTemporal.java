package com.practica.lista;

import java.util.LinkedList;
import java.util.List;

import com.practica.genericas.FechaHora;
import com.practica.genericas.PosicionPersona;

/**
 * Nodo para guardar un instante de tiempo. Además guardamos una lista con las
 * coordeandas
 * y las personas (solo número) que en ese instante están en una coordeanda en
 * concreto
 *
 */
public class NodoTemporal implements Comparable<NodoTemporal> {
	private List<NodoPosicion> listaCoordenadas;
	private FechaHora fecha;

	public static NodoTemporal fromPosicionPersona(PosicionPersona pp) {
		NodoTemporal nt = new NodoTemporal();
		nt.setFecha(pp.getFechaPosicion());
		nt.getListaCoordenadas().add(NodoPosicion.fromPosicionPersona(pp));
		return nt;
	}

	public NodoTemporal() {
		super();
		listaCoordenadas = new LinkedList<>();
	}

	public List<NodoPosicion> getListaCoordenadas() {
		return listaCoordenadas;
	}

	public void setListaCoordenadas(List<NodoPosicion> listaCoordenadas) {
		this.listaCoordenadas = listaCoordenadas;
	}

	public FechaHora getFecha() {
		return fecha;
	}

	public void setFecha(FechaHora fecha) {
		this.fecha = fecha;
	}

	public void combine(NodoTemporal other) {
		if (!this.fecha.equals(other.getFecha()))
			return;
		int index;
		for (NodoPosicion np : other.getListaCoordenadas()) {
			if ((index = listaCoordenadas.indexOf(np)) == -1)
				listaCoordenadas.add(new NodoPosicion(np));
			else
				listaCoordenadas.get(index).combine(np);
		}
	}

	public boolean isBetween(FechaHora start, FechaHora end) {
		return fecha.compareTo(start) >= 0 && fecha.compareTo(end) <= 0;
	}

	public int getNumPersonas() {
		return listaCoordenadas.stream().mapToInt(NodoPosicion::getNumPersonas).sum();
	}

	@Override
	public int compareTo(NodoTemporal other) {
		return this.fecha.compareTo(other.getFecha());
	}

	@Override
	public int hashCode() {
		return fecha.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof NodoTemporal))
			return false;
		NodoTemporal other = (NodoTemporal) obj;
		return this.fecha.equals(other.getFecha());
	}

	@Override
	public String toString() {
		return fecha.toString();
	}

}
