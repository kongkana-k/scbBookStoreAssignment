package com.scb.assignment.bookStoreRestApi.utility;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtility {
	public static Date fromString(String dateString) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate birthDate = LocalDate.parse(dateString, dtf);
		return Date.valueOf(birthDate);
	}

	public static String toString(Date date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return date.toLocalDate().format(dtf);
	}
}
