package com.practica.ems.covid;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.practica.excecption.EmsDuplicateLocationException;
import com.practica.excecption.EmsLocalizationNotFoundException;
import com.practica.genericas.FechaHora;
import com.practica.genericas.PosicionPersona;

public class Localizacion {
	LinkedList<PosicionPersona> lista;

	public Localizacion() {
		super();
		this.lista = new LinkedList<>();
	};

	public List<PosicionPersona> getLista() {
		return lista;
	}

	public void setLista(LinkedList<PosicionPersona> lista) {
		this.lista = lista;
	}

	public void addLocalizacion(PosicionPersona p) throws EmsDuplicateLocationException {
		try {
			findLocalizacion(p.getDocumento(), p.getFechaPosicion().getFecha().toString(),
					p.getFechaPosicion().getHora().toString());
			throw new EmsDuplicateLocationException();
		} catch (EmsLocalizationNotFoundException e) {
			lista.add(p);
		}
	}

	public int findLocalizacion(String documento, String fecha, String hora) throws EmsLocalizationNotFoundException {
		int cont = 0;
		Iterator<PosicionPersona> it = lista.iterator();
		while (it.hasNext()) {
			cont++;
			PosicionPersona pp = it.next();
			FechaHora fechaHora = this.parsearFecha(fecha, hora);
			if (pp.getDocumento().equals(documento) &&
					pp.getFechaPosicion().equals(fechaHora)) {
				return cont;
			}
		}
		throw new EmsLocalizationNotFoundException();
	}

	public void delLocalizacion(String documento, String fecha, String hora) throws EmsLocalizationNotFoundException {
		int pos = -1;
		/**
		 * Busca la localización, sino existe lanza una excepción
		 */
		try {
			pos = findLocalizacion(documento, fecha, hora);
		} catch (EmsLocalizationNotFoundException e) {
			throw new EmsLocalizationNotFoundException();
		}
		this.lista.remove(pos);

	}

	void printLocalizacion() {
		System.out.println(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		lista.forEach(builder::append);
		return builder.toString();
	}

	@SuppressWarnings("unused")
	private FechaHora parsearFecha(String fecha) {
		int dia, mes, anio;
		String[] valores = fecha.split("\\/");
		dia = Integer.parseInt(valores[0]);
		mes = Integer.parseInt(valores[1]);
		anio = Integer.parseInt(valores[2]);
		return new FechaHora(dia, mes, anio, 0, 0);
	}

	private FechaHora parsearFecha(String fecha, String hora) {
		int dia, mes, anio;
		String[] valores = fecha.split("\\/");
		dia = Integer.parseInt(valores[0]);
		mes = Integer.parseInt(valores[1]);
		anio = Integer.parseInt(valores[2]);
		int minuto, segundo;
		valores = hora.split("\\:");
		minuto = Integer.parseInt(valores[0]);
		segundo = Integer.parseInt(valores[1]);
		return new FechaHora(dia, mes, anio, minuto, segundo);
	}

}
