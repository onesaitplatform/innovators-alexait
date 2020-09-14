package com.minsait.innovators.alexa.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import com.minsait.innovators.alexa.model.Meetings;

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
		return " El " + date.getDayOfMonth() + " de " + DateUtils.getMonths().get(date.getMonthOfYear()) + " a las "
				+ date.getHourOfDay() + ":" + date.getMinuteOfHour() + " . ";
	}

	public static String getMeetingLocation(Meetings meeting) {
		if (meeting.getBuilding() == null || "".equals(meeting.getBuilding())) {
			return meeting.getRoom();
		} else {
			return meeting.getBuilding() + ", sala " + meeting.getRoom();
		}
	}

	public static String getNowFormated() {
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		final Date date = new Date();
		return dateFormat.format(date);
	}

}
