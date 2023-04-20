package com.practica.genericas;

public class PosicionPersona {
	private Coordenada coordenada;
	private String documento;
	private FechaHora fechaPosicion;

	public Coordenada getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public FechaHora getFechaPosicion() {
		return fechaPosicion;
	}

	public void setFechaPosicion(FechaHora fechaPosicion) {
		this.fechaPosicion = fechaPosicion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("%s;", getDocumento()));
		FechaHora fecha = getFechaPosicion();
		builder.append(String.format("%02d/%02d/%04d;%02d:%02d;",
				fecha.getFecha().getDia(),
				fecha.getFecha().getMes(),
				fecha.getFecha().getAnio(),
				fecha.getHora().getHora(),
				fecha.getHora().getMinuto()));
		builder.append(String.format("%.4f;%.4f\n", getCoordenada().getLatitud(),
				getCoordenada().getLongitud()));
		return builder.toString();
	}

}
