package controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateUtil handles date and time conversions from and to string format while
 * maintaining the correct format. It also has methods for showing the current
 * date and the weekday of a specific date.
 * 
 * @author Arttuhal
 * @since 28/04/2019
 */

public class DateUtil {
	private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	private static final String[] WEEKDAYS = new String[] { LanguageUtil.translate("Monday"),
			LanguageUtil.translate("Tuesday"), LanguageUtil.translate("Wednesday"), LanguageUtil.translate("Thursday"),
			LanguageUtil.translate("Friday"), LanguageUtil.translate("Saturday"), LanguageUtil.translate("Sunday") };

	/**
	 * This method converts a given date to a string in the correct format. Doesn't
	 * include time.
	 * 
	 * @param date The date to convert.
	 * @return dateString The converted date in "yyyy/MM/dd" string format.
	 */
	public static String dateToString(LocalDate date) {
		String dateString = date.format(DATE_FORMATTER);
		return dateString;
	}

	/**
	 * This method converts a given date to a string in the correct format. Includes
	 * time.
	 * 
	 * @param date The date and time to convert.
	 * @return dateString The converted date in "yyyy/MM/dd HH:mm:ss" string format.
	 */
	public static String dateToString(LocalDateTime date) {
		String dateString = date.format(DATETIME_FORMATTER);
		return dateString;
	}

	/**
	 * This method converts a string containing date information into a LocalDate
	 * object. Doesn't include time.
	 * 
	 * @param dateString The string to convert.
	 */
	public static LocalDate stringToDate(String dateString) {
		return LocalDate.parse(dateString, DATE_FORMATTER);
	}

	/**
	 * This method converts a string containing date information into a LocalDate
	 * object. Includes time.
	 * 
	 * @param dateString The string to convert.
	 */
	public static LocalDateTime stringToDateTime(String dateString) {
		return LocalDateTime.parse(dateString, DATETIME_FORMATTER);
	}

	/**
	 * This method returns the current date in a string, formatted to the correct
	 * time format. Doesn't include time.
	 */
	public static String currentDate() {
		return dateToString(LocalDate.now());
	}

	/**
	 * This method returns the current date in a string, formatted to the correct
	 * time format. Includes time.
	 */
	public static String currentDateTime() {
		return dateToString(LocalDateTime.now());
	}

	/**
	 * This method returns the weekday of a given specific date in a string format.
	 * 
	 * @param date The date from which the weekday is taken.
	 * @return weekDay The weekday of the given date.
	 */
	public static String weekdayUtil(LocalDate date) {
		String weekDay = WEEKDAYS[date.getDayOfWeek().getValue() - 1];
		return weekDay;
	}

	/**
	 * This method returns the weekday of a given specific date in a string format.
	 * 
	 * @param date The date from which the weekday is taken.
	 * @return weekDay The weekday of the given date.
	 */
	public static String weekdayUtil(LocalDateTime date) {
		String weekDay = WEEKDAYS[date.getDayOfWeek().getValue() - 1];
		return weekDay;
	}

	public static String[] getWeekdays() {
		return WEEKDAYS;
	}

}
