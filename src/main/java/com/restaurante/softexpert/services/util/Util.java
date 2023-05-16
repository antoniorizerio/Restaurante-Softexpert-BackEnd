package com.restaurante.softexpert.services.util;

import java.text.NumberFormat;
import java.util.Locale;

public class Util {
	
	public static Double getDoubleFormatado(Double valor, int qtdCasas) {
		if(valor != null) {
			Locale locale = new Locale("pt", "BR");
		    NumberFormat format = NumberFormat.getInstance(locale);
		    format.setMaximumFractionDigits(qtdCasas);
		    return Double.parseDouble(format.format(valor).replace(",", "."));
		} else {
			return 0.0;
		}
	}
}
