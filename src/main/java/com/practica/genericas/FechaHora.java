package com.practica.genericas;

import java.time.LocalDateTime;
import java.util.Objects;

public class FechaHora implements Comparable<FechaHora> {
	public class Fecha {
		private int dia;
		private int mes;
		private int anio;

		public Fecha(int dia, int mes, int anio) {
			super();
			this.dia = dia;
			this.mes = mes;
			this.anio = anio;
		}

		public int getDia() {
			return dia;
		}

		public void setDia(int dia) {
			this.dia = dia;
		}

		public int getMes() {
			return mes;
		}

		public void setMes(int mes) {
			this.mes = mes;
		}

		public int getAnio() {
			return anio;
		}

		public void setAnio(int anio) {
			this.anio = anio;
		}

		@Override
		public String toString() {
			return String.format("%2d/%02d/%4d", dia, mes, anio);
		}

		@Override
		public int hashCode() {
			return Objects.hash(dia, mes, anio);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof Fecha))
				return false;
			Fecha date = (Fecha) obj;
			return getDia() == date.getDia() &&
					getMes() == date.getMes() &&
					getAnio() == date.getAnio();
		}

	}

	public class Hora {
		private int hr;
		private int minuto;

		public Hora(int hr, int minuto) {
			super();
			this.hr = hr;
			this.minuto = minuto;
		}

		public int getHora() {
			return hr;
		}

		public void setHora(int hr) {
			this.hr = hr;
		}

		public int getMinuto() {
			return minuto;
		}

		public void setMinuto(int minuto) {
			this.minuto = minuto;
		}

		@Override
		public String toString() {
			return String.format("%02d:%02d", hr, minuto);
		}

		@Override
		public int hashCode() {
			return Objects.hash(hr, minuto);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof Hora))
				return false;
			Hora time = (Hora) obj;
			return getHora() == time.getHora() &&
					getMinuto() == time.getMinuto();
		}

	}

	private Fecha fecha;
	private Hora hora;

	public FechaHora(Fecha fecha, Hora hora) {
		super();
		this.fecha = fecha;
		this.hora = hora;
	}

	public FechaHora(int dia, int mes, int anio, int hora, int minuto) {
		this.fecha = new Fecha(dia, mes, anio);
		this.hora = new Hora(hora, minuto);
	}

	public Fecha getFecha() {
		return fecha;
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

	public Hora getHora() {
		return hora;
	}

	public void setHora(Hora hora) {
		this.hora = hora;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FechaHora fechaHora = (FechaHora) obj;
		return getFecha().equals(fechaHora.getFecha()) && getHora().equals(fechaHora.getHora());
	}

	@Override
	public int compareTo(FechaHora other) {
		LocalDateTime thisDateTime = LocalDateTime.of(getFecha().getAnio(), getFecha().getMes(),
				getFecha().getDia(), getHora().getHora(), getHora().getMinuto());
		LocalDateTime otherDateTime = LocalDateTime.of(other.getFecha().getAnio(), other.getFecha().getMes(),
				other.getFecha().getDia(),
				other.getHora().getHora(), other.getHora().getMinuto());
		return thisDateTime.compareTo(otherDateTime);
	}

	public static FechaHora parseDateTime(String fecha) {
		String[] valores = fecha.split("\\/");
		int dia = Integer.parseInt(valores[0]);
		int mes = Integer.parseInt(valores[1]);
		int anio = Integer.parseInt(valores[2]);
		return new FechaHora(dia, mes, anio, 0, 0);
	}

	public static FechaHora parseDateTime(String fecha, String hora) {
		String[] valores = fecha.split("\\/");
		int dia = Integer.parseInt(valores[0]);
		int mes = Integer.parseInt(valores[1]);
		int anio = Integer.parseInt(valores[2]);
		valores = hora.split("\\:");
		int minuto = Integer.parseInt(valores[0]);
		int segundo = Integer.parseInt(valores[1]);
		return new FechaHora(dia, mes, anio, minuto, segundo);
	}

}
