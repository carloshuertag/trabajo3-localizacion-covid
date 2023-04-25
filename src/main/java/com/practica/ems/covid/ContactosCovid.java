package com.practica.ems.covid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.practica.excecption.EmsDuplicateLocationException;
import com.practica.excecption.EmsDuplicatePersonException;
import com.practica.excecption.EmsInvalidNumberOfDataException;
import com.practica.excecption.EmsInvalidTypeException;
import com.practica.excecption.EmsLocalizationNotFoundException;
import com.practica.excecption.EmsPersonNotFoundException;
import com.practica.genericas.Constantes;
import com.practica.genericas.Coordenada;
import com.practica.genericas.FechaHora;
import com.practica.genericas.Persona;
import com.practica.genericas.PosicionPersona;
import com.practica.lista.ListaContactos;

public class ContactosCovid {
	private Poblacion poblacion;
	private Localizacion localizacion;
	private ListaContactos listaContactos;

	private enum DATA_TAG {
		PERSONA("PERSONA"),
		LOCALIZACION("LOCALIZACION");

		private final String value;

		private DATA_TAG(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public ContactosCovid() {
		this.poblacion = new Poblacion();
		this.localizacion = new Localizacion();
		this.listaContactos = new ListaContactos();
	}

	public Poblacion getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(Poblacion poblacion) {
		this.poblacion = poblacion;
	}

	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
	}

	public ListaContactos getListaContactos() {
		return listaContactos;
	}

	public void setListaContactos(ListaContactos listaContactos) {
		this.listaContactos = listaContactos;
	}

	public void loadData(String data, boolean reset) throws EmsInvalidTypeException, EmsInvalidNumberOfDataException,
			EmsDuplicatePersonException, EmsDuplicateLocationException {
		if (reset)
			resetContactos();
		loadLinesData(data);
	}

	private void resetContactos() {
		setPoblacion(new Poblacion());
		setLocalizacion(new Localizacion());
		setListaContactos(new ListaContactos());
	}

	@SuppressWarnings("resource")
	public void loadDataFile(String fichero, boolean reset) {
		FileReader fr = null;
		BufferedReader br = null;
		String data = null;
		try {
			fr = new FileReader(new File(fichero));
			br = new BufferedReader(fr);
			if (reset)
				resetContactos();
			while ((data = br.readLine()) != null)
				loadLinesData(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	private void loadLinesData(String lineData) throws EmsInvalidTypeException, EmsInvalidNumberOfDataException,
			EmsDuplicatePersonException, EmsDuplicateLocationException {
		String[] entries = dividirEntrada(lineData.trim());
		for (String entry : entries) {
			loadLineData(entry);
		}
	}

	private void loadLineData(String line) throws EmsInvalidTypeException, EmsInvalidNumberOfDataException,
			EmsDuplicatePersonException, EmsDuplicateLocationException {
		String[] data = this.dividirLineaData(line);
		if (data[0].equals(DATA_TAG.PERSONA.getValue())) {
			if (data.length != Constantes.MAX_DATOS_PERSONA.getValue()) {
				throw new EmsInvalidNumberOfDataException(
						"El número de datos para PERSONA es menor de 8");
			}
			getPoblacion().addPersona(this.crearPersona(data));
			return;
		}
		if (data[0].equals(DATA_TAG.LOCALIZACION.getValue())) {
			if (data.length != Constantes.MAX_DATOS_LOCALIZACION.getValue()) {
				throw new EmsInvalidNumberOfDataException(
						"El número de datos para LOCALIZACION es menor de 6");
			}
			PosicionPersona pp = this.crearPosicionPersona(data);
			getLocalizacion().addLocalizacion(pp);
			getListaContactos().insertarNodoTemporal(pp);
			return;
		}
		throw new EmsInvalidTypeException();
	}

	public int findPersona(String documento) throws EmsPersonNotFoundException {
		int pos;
		try {
			pos = getPoblacion().findPersona(documento);
			return pos;
		} catch (EmsPersonNotFoundException e) {
			throw new EmsPersonNotFoundException();
		}
	}

	public int findLocalizacion(String documento, String fecha, String hora) throws EmsLocalizationNotFoundException {
		int pos;
		try {
			pos = localizacion.findLocalizacion(documento, fecha, hora);
			return pos;
		} catch (EmsLocalizationNotFoundException e) {
			throw new EmsLocalizationNotFoundException();
		}
	}

	public List<PosicionPersona> localizacionPersona(String documento) throws EmsPersonNotFoundException {
		int cont = 0;
		List<PosicionPersona> lista = new ArrayList<>();
		Iterator<PosicionPersona> it = getLocalizacion().getLista().iterator();
		while (it.hasNext()) {
			PosicionPersona pp = it.next();
			if (documento.equals(pp.getDocumento())) {
				cont++;
				lista.add(pp);
			}
		}
		if (cont == 0)
			throw new EmsPersonNotFoundException();
		else
			return lista;
	}

	public boolean delPersona(String documento) throws EmsPersonNotFoundException {
		int cont = 0;
		int pos = -1;
		Iterator<Persona> it = this.poblacion.getLista().iterator();
		while (it.hasNext()) {
			Persona persona = it.next();
			if (documento.equals(persona.getDocumento()))
				pos = cont;
			cont++;
		}
		if (pos == -1)
			throw new EmsPersonNotFoundException();
		this.poblacion.getLista().remove(pos);
		return false;
	}

	private String[] dividirEntrada(String input) {
		return input.split("\\n");
	}

	private String[] dividirLineaData(String data) {
		return data.split("\\;");
	}

	private Persona crearPersona(String[] data) {
		Persona persona = new Persona();
		persona.setDocumento(data[1]);
		persona.setNombre(data[2]);
		persona.setApellidos(data[3]);
		persona.setEmail(data[4]);
		persona.setDireccion(data[5]);
		persona.setCp(data[6]);
		persona.setFechaNacimiento(FechaHora.parseDateTime(data[7]));
		return persona;
	}

	private PosicionPersona crearPosicionPersona(String[] data) {
		PosicionPersona posicionPersona = new PosicionPersona();
		float latitud;
		float longitud;
		posicionPersona.setDocumento(data[1]);
		String fecha = data[2];
		String hora = data[3];
		posicionPersona.setFechaPosicion(FechaHora.parseDateTime(fecha, hora));
		latitud = Float.parseFloat(data[4]);
		longitud = Float.parseFloat(data[5]);
		posicionPersona.setCoordenada(new Coordenada(latitud, longitud));
		return posicionPersona;
	}

}
