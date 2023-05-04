package com.practica.lista;

import java.util.LinkedList;
import java.util.List;

import com.practica.genericas.FechaHora;
import com.practica.genericas.PosicionPersona;

public class ListaContactos {
	private List<NodoTemporal> lista;

	public ListaContactos() {
		super();
		lista = new LinkedList<>();
	}

	private void insertAt(NodoTemporal nt) throws IllegalArgumentException {
		if (lista.contains(nt))
			throw new IllegalArgumentException("Date is already in the list");
		int index = 0;
		for (NodoTemporal actual : lista) {
			if (nt.compareTo(actual) < 0)
				break;
			index++;
		}
		if (index == lista.size())
			lista.add(nt);
		else
			lista.add(index, nt);
	}

	/**
	 * Insertamos en la lista de nodos temporales, y a la vez inserto en la lista de
	 * nodos de coordenadas.
	 * En la lista de coordenadas metemos el documento de la persona que está en esa
	 * coordenada en un instante
	 */
	public void insertarNodoTemporal(PosicionPersona p) {
		NodoTemporal newNodoTemporal = NodoTemporal.fromPosicionPersona(p);
		if (lista.isEmpty()) {
			lista.add(newNodoTemporal);
			return;
		}
		int index = lista.indexOf(newNodoTemporal);
		if (index == -1)
			insertAt(newNodoTemporal);
		else
			lista.get(index).combine(newNodoTemporal);
	}

	public int tamanioLista() {
		return lista.size();
	}

	public String getPrimerNodo() {
		return lista.get(0).getFecha().toString();
	}

	public int numPersonasEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		return lista.stream().filter(nt -> nt.isBetween(inicio, fin)).mapToInt(NodoTemporal::getNumPersonas).sum();
	}

	public int numNodosCoordenadaEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		return lista.stream().filter(nt -> nt.isBetween(inicio, fin)).mapToInt(nt -> nt.getListaCoordenadas().size())
				.sum();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		lista.forEach(nt -> {
			builder.append(nt.getFecha());
			builder.append(" ");
		});
		return builder.toString().trim();
	}

}
