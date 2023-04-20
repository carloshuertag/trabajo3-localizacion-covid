package com.practica.genericas;

public enum Constantes {

	MAX_DATOS_PERSONA(8),
	MAX_DATOS_LOCALIZACION(6);

	private final String key;
	private final int value;

	Constantes(int value) {
		this.key = null;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public int getValue() {
		return value;
	}
}
