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
		NodoTemporal currentNode = lista;
		NodoTemporal prev = null;
		boolean end = false;
		boolean exists = false;
		while (currentNode != null && !end) {
			if (currentNode.getFecha().compareTo(p.getFechaPosicion()) == 0) {
				exists = true;
				end = true;
				insertarNodoPosicion(currentNode, p);
			} else if (currentNode.getFecha().compareTo(p.getFechaPosicion()) < 0) {
				prev = currentNode;
				currentNode = currentNode.getSiguiente();
			} else if (currentNode.getFecha().compareTo(p.getFechaPosicion()) > 0) {
				end = true;
			}
		}
		if (!exists) {
			insertarNuevoNodo(prev, currentNode, p);
		}
	}

	/**
	 * Insertamos en la lista de coordenadas
	 */
	private void insertarNodoPosicion(NodoTemporal node, PosicionPersona p) {
		NodoPosicion current = node.getListaCoordenadas();
		NodoPosicion prev = null;
		boolean exists = false;
		while (current != null && !exists) {
			if (current.getCoordenada().equals(p.getCoordenada())) {
				exists = true;
				current.setNumPersonas(current.getNumPersonas() + 1);
			} else {
				prev = current;
				current = current.getSiguiente();
			}
		}
		if (!exists) {
			NodoPosicion npNuevo = new NodoPosicion(p.getCoordenada(), 1, null);
			if (node.getListaCoordenadas() == null)
				node.setListaCoordenadas(npNuevo);
			else if (prev != null)
				prev.setSiguiente(npNuevo);
		}
	}

	/*
	 * Metemos un nodo nuevo en la lista
	 */
	private void insertarNuevoNodo(NodoTemporal prev, NodoTemporal next, PosicionPersona p) {
		NodoTemporal newNode = new NodoTemporal();
		newNode.setFecha(p.getFechaPosicion());
		insertarNodoPosicion(newNode, p);
		if (prev != null) {
			newNode.setSiguiente(next);
			prev.setSiguiente(newNode);
		} else {
			newNode.setSiguiente(lista);
			lista = newNode;
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
		while (aux != null)
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
			} else
				aux = aux.getSiguiente();
		return cont;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		int cont = 0;
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
