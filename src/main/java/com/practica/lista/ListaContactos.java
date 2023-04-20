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
	 * coordenada
	 * en un instante
	 */
	public void insertarNodoTemporal(PosicionPersona p) {
		NodoTemporal[] search = searchPosition(p);
		boolean found = search[1].getFecha().compareTo(p.getFechaPosicion()) == 0;
		if (found)
			insertIntoCoordinates(search[1], p);
		else {
			NodoTemporal nuevo = addNewNode(p);
			if (search[0] != null) {
				nuevo.setSiguiente(search[1]);
				search[0].setSiguiente(nuevo);
			} else {
				nuevo.setSiguiente(lista);
				lista = nuevo;
			}
			this.size++;
		}
	}

	/**
	 * Busco la posición adecuada donde meter el nodo de la lista, excepto
	 * que esté en la lista. Entonces solo añadimos una coordenada.
	 */
	private NodoTemporal[] searchPosition(PosicionPersona p) {
		NodoTemporal aux = lista;
		NodoTemporal ant = null;
		boolean salir = false;
		while (aux != null && !salir) {
			if (aux.getFecha().compareTo(p.getFechaPosicion()) == 0) {
				salir = true;
			} else if (aux.getFecha().compareTo(p.getFechaPosicion()) < 0) {
				ant = aux;
				aux = aux.getSiguiente();
			} else {
				salir = true;
			}
		}
		return new NodoTemporal[] { ant, aux };
	}

	/**
	 * Insertamos en la lista de coordenadas
	 */
	private void insertIntoCoordinates(NodoTemporal node, PosicionPersona p) {
		NodoPosicion npActual = node.getListaCoordenadas();
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
			if (node.getListaCoordenadas() == null)
				node.setListaCoordenadas(npNuevo);
			else
				npAnt.setSiguiente(npNuevo);
		}
	}

	/**
	 * No hemos encontrado ninguna posición temporal, así que
	 * metemos un nodo nuevo en la lista
	 */
	private NodoTemporal addNewNode(PosicionPersona p) {
		NodoTemporal nuevo = new NodoTemporal();
		nuevo.setFecha(p.getFechaPosicion());
		NodoPosicion npActual = nuevo.getListaCoordenadas();
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
			if (nuevo.getListaCoordenadas() == null)
				nuevo.setListaCoordenadas(npNuevo);
			else
				npAnt.setSiguiente(npNuevo);
		}
		return nuevo;
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
		String cadena = aux.getFecha().getFecha().toString();
		cadena += ";" + aux.getFecha().getHora().toString();
		return cadena;
	}

	/**
	 * Métodos para comprobar que insertamos de manera correcta en las listas de
	 * coordenadas, no tienen una utilidad en sí misma, más allá de comprobar que
	 * nuestra lista funciona de manera correcta.
	 */
	public int numPersonasEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		if (this.size == 0)
			return 0;
		NodoTemporal aux = lista;
		int cont = 0;
		cont = 0;
		while (aux != null) {
			if (aux.getFecha().compareTo(inicio) >= 0 && aux.getFecha().compareTo(fin) <= 0) {
				NodoPosicion nodo = aux.getListaCoordenadas();
				while (nodo != null) {
					cont = cont + nodo.getNumPersonas();
					nodo = nodo.getSiguiente();
				}
				aux = aux.getSiguiente();
			} else {
				aux = aux.getSiguiente();
			}
		}
		return cont;
	}

	public int numNodosCoordenadaEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		if (this.size == 0)
			return 0;
		NodoTemporal aux = lista;
		int cont = 0;
		cont = 0;
		while (aux != null) {
			if (aux.getFecha().compareTo(inicio) >= 0 && aux.getFecha().compareTo(fin) <= 0) {
				NodoPosicion nodo = aux.getListaCoordenadas();
				while (nodo != null) {
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
		String cadena = "";
		int cont;
		cont = 0;
		NodoTemporal aux = lista;
		for (cont = 1; cont < size; cont++) {
			cadena += aux.getFecha().getFecha().toString();
			cadena += ";" + aux.getFecha().getHora().toString() + " ";
			aux = aux.getSiguiente();
		}
		cadena += aux.getFecha().getFecha().toString();
		cadena += ";" + aux.getFecha().getHora().toString();
		return cadena;
	}

}
