package com.practica.ems.covid;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.practica.excecption.EmsDuplicatePersonException;
import com.practica.excecption.EmsPersonNotFoundException;
import com.practica.genericas.Persona;

public class Poblacion {
	LinkedList<Persona> lista;

	public Poblacion() {
		super();
		this.lista = new LinkedList<>();
	}

	public List<Persona> getLista() {
		return lista;
	}

	public void setLista(LinkedList<Persona> lista) {
		this.lista = lista;
	}

	public void addPersona(Persona persona) throws EmsDuplicatePersonException {
		try {
			findPersona(persona.getDocumento());
			throw new EmsDuplicatePersonException();
		} catch (EmsPersonNotFoundException e) {
			lista.add(persona);
		}
	}

	public void delPersona(String documento) throws EmsPersonNotFoundException {
		int pos = -1;
		/**
		 * Busca la persona por documento, en caso de encontrarla
		 * devuelve la posición dentro de la lista, sino está lanza
		 * una excepción
		 */
		try {
			pos = findPersona(documento);
		} catch (EmsPersonNotFoundException e) {
			throw new EmsPersonNotFoundException();
		}
		lista.remove(pos);
	}

	public int findPersona(String documento) throws EmsPersonNotFoundException {
		int cont = 0;
		Iterator<Persona> it = lista.iterator();
		while (it.hasNext()) {
			Persona persona = it.next();
			cont++;
			if (persona.getDocumento().equals(documento)) {
				return cont;
			}
		}
		throw new EmsPersonNotFoundException();
	}

	public void printPoblacion() {
		System.out.println(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		lista.forEach(builder::append);
		return builder.toString();
	}

}
