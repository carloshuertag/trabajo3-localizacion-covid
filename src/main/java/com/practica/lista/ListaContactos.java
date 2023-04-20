package com.practica.lista;

import com.practica.genericas.FechaHora;
import com.practica.genericas.PosicionPersona;

public class ListaContactos {
	private NodoTemporal lista;
	private int size;

	/**
	 * Insertamos en la lista de nodos temporales, y a la vez inserto en la lista de
	 * nodos de coordenadas.
	 * En la lista de coordenadas metemos el documento de la persona que está en esa
	 * coordenada en un instante
	 */
	public void insertarNodoTemporal(PosicionPersona p) {
		NodoTemporal aux = lista, ant = null;
		boolean salir = false, encontrado = false;
		while (aux != null && !salir) {
			if (aux.getFecha().compareTo(p.getFechaPosicion()) == 0) {
				encontrado = true;
				salir = true;
				insertarNodoPosicion(aux, p);
			} else if (aux.getFecha().compareTo(p.getFechaPosicion()) < 0) {
				ant = aux;
				aux = aux.getSiguiente();
			} else if (aux.getFecha().compareTo(p.getFechaPosicion()) > 0) {
				salir = true;
			}
		}
		if (!encontrado) {
			insertarNuevoNodo(ant, aux, p);
		}
	}

	/**
	 * Insertamos en la lista de coordenadas
	 */
	private void insertarNodoPosicion(NodoTemporal nodo, PosicionPersona p) {
		NodoPosicion npActual = nodo.getListaCoordenadas();
		NodoPosicion npAnt = null;
		boolean npEncontrado = false;
		while (npActual != null && !npEncontrado) {
			if (npActual.getCoordenada().equals(p.getCoordenada())) {
				npEncontrado = true;
				npActual.setNumPersonas(npActual.getNumPersonas() + 1);
			} else {
				npAnt = npActual;
				npActual = npActual.getSiguiente();
			}
		}
		if (!npEncontrado) {
			NodoPosicion npNuevo = new NodoPosicion(p.getCoordenada(), 1, null);
			if (nodo.getListaCoordenadas() == null)
				nodo.setListaCoordenadas(npNuevo);
			else
				npAnt.setSiguiente(npNuevo);
		}
	}

	/*
	 * Metemos un nodo nuevo en la lista
	 */
	private void insertarNuevoNodo(NodoTemporal prev, NodoTemporal next, PosicionPersona p) {
		NodoTemporal nuevo = new NodoTemporal();
		nuevo.setFecha(p.getFechaPosicion());
		insertarNodoPosicion(nuevo, p);
		if (prev != null) {
			nuevo.setSiguiente(next);
			prev.setSiguiente(nuevo);
		} else {
			nuevo.setSiguiente(lista);
			lista = nuevo;
		}
		this.size++;
	}

	public int personasEnCoordenadas() {
		NodoPosicion aux = this.lista.getListaCoordenadas();
		if (aux == null)
			return 0;
		else {
			int cont;
			for (cont = 0; aux != null;) {
				cont += aux.getNumPersonas();
				aux = aux.getSiguiente();
			}
			return cont;
		}
	}

	public int tamanioLista() {
		return this.size;
	}

	public String getPrimerNodo() {
		NodoTemporal aux = lista;
		StringBuilder builder = new StringBuilder();
		builder.append(aux.getFecha().getFecha().toString());
		builder.append(";");
		builder.append(aux.getFecha().getHora().toString());
		return builder.toString();
	}

	public int numPersonasEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		return numEntreDosInstantes(true, inicio, fin);
	}

	public int numNodosCoordenadaEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		return numEntreDosInstantes(false, inicio, fin);
	}

	private int numEntreDosInstantes(boolean personas, FechaHora inicio, FechaHora fin) {
		if (this.size == 0)
			return 0;
		NodoTemporal aux = lista;
		int cont = 0;
		cont = 0;
		while (aux != null) {
			if (aux.getFecha().compareTo(inicio) >= 0 && aux.getFecha().compareTo(fin) <= 0) {
				NodoPosicion nodo = aux.getListaCoordenadas();
				while (nodo != null) {
					if (personas)
						cont = cont + nodo.getNumPersonas();
					else
						cont = cont + 1;
					nodo = nodo.getSiguiente();
				}
				aux = aux.getSiguiente();
			} else {
				aux = aux.getSiguiente();
			}
		}
		return cont;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		int cont;
		cont = 0;
		NodoTemporal aux = lista;
		for (cont = 1; cont < size; cont++) {
			builder.append(aux.getFecha().getFecha().toString());
			builder.append(";");
			builder.append(aux.getFecha().getHora().toString());
			builder.append(" ");
			aux = aux.getSiguiente();
		}
		builder.append(aux.getFecha().getFecha().toString());
		builder.append(";");
		builder.append(aux.getFecha().getHora().toString());
		return builder.toString();
	}

}
