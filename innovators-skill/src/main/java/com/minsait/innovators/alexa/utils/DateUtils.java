package com.minsait.innovators.alexa.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import lombok.Getter;

public class DateUtils {

	@Getter
	private static final Map<Integer, String> months;
	static {
		final Map<Integer, String> map = new HashMap<>();
		map.put(1, "Enero");
		map.put(2, "Febrero");
		map.put(3, "Marzo");
		map.put(4, "Abril");
		map.put(5, "Mayo");
		map.put(6, "Junio");
		map.put(7, "Julio");
		map.put(8, "Ågosto");
		map.put(9, "Septiembre");
		map.put(10, "Octubre");
		map.put(11, "Noviembre");
		map.put(12, "Diciembre");
		months = Collections.unmodifiableMap(map);
	}

	public static String parseISODateToSpeech(DateTime date) {
		return "El " + date.getDayOfMonth() + " de " + DateUtils.getMonths().get(date.getMonthOfYear()) + " a las "
				+ date.getHourOfDay() + " horas y " + date.getMinuteOfHour() + " minutos. ";
	}

}
