package com.practica.ems.covid;

import com.practica.excecption.EmsDuplicateLocationException;
import com.practica.excecption.EmsDuplicatePersonException;
import com.practica.excecption.EmsInvalidNumberOfDataException;
import com.practica.excecption.EmsInvalidTypeException;
import com.practica.genericas.FechaHora;

public class Principal {

	public static void main(String[] args) throws EmsInvalidTypeException,
			EmsInvalidNumberOfDataException {
		String test_data = generateTestData();
		ContactosCovid contactosCovid = new ContactosCovid();
		contactosCovid.loadDataFile("datos2.txt", false);
		System.out.println(contactosCovid.getLocalizacion().toString());
		System.out.println(contactosCovid.getPoblacion().toString());
		System.out.println(contactosCovid.getListaContactos().tamanioLista());
		System.out.println(contactosCovid.getListaContactos().getPrimerNodo());
		System.out.println(contactosCovid.getListaContactos());
		FechaHora ini = new FechaHora(25, 5, 2021, 16, 30);
		FechaHora fin = new FechaHora(25, 5, 2021, 16, 30);

	}

	private static String generateTestData() {
		StringBuilder test_data = new StringBuilder();
		test_data.append("PERSONA;87654321K;Jessica;Diaz;jessica.diaz@ems.com;");
		test_data.append("La calle de jessica, 33;28033;25/01/1980\n");
		test_data.append("PERSONA;98765432J;Angel;Panizo;angel.panizo@ems.com;");
		test_data.append("La calle de Angel, 46;28871;12/01/1995\n");
		test_data.append("LOCALIZACION;87654321K;25/10/2021;23:41;41.3870;2.1698\n");
		test_data.append("LOCALIZACION;87654321K;25/10/2021;23:45;41.3870;2.1695\n");
		test_data.append("LOCALIZACION;98765432J;25/10/2021;23:55;41.3871;2.1697\n");
		test_data.append("LOCALIZACION;87654321K;25/10/2021;23:55;41.3871;2.1697\n");
		return test_data.toString();
	}
}
