package com.practica.genericas;

public class Coordenada {
	private float latitud;
	private float longitud;

	public static Coordenada parseCoordenada(String latitudStr, String longitudStr) {
		float latitud;
		float longitud;
		latitud = Float.parseFloat(latitudStr);
		longitud = Float.parseFloat(longitudStr);
		return new Coordenada(latitud, longitud);
	}

	public Coordenada() {
	}

	public Coordenada(Coordenada c) {
		this.latitud = c.latitud;
		this.longitud = c.longitud;
	}

	public Coordenada(float latitud, float longitud) {
		super();
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public float getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(latitud);
		result = prime * result + Float.floatToIntBits(longitud);
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
		Coordenada other = (Coordenada) obj;
		if (Float.floatToIntBits(latitud) != Float.floatToIntBits(other.latitud))
			return false;
		return Float.floatToIntBits(longitud) == Float.floatToIntBits(other.longitud);
	}

	@Override
	public String toString() {
		return String.format("%.4f;%.4f%n", latitud, longitud);
	}

}
