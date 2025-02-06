package kims.semi1.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

	/**
	 * (yyyyMMdd, yyyy-MM-dd) 형식들의 문자열을 받아 yyyy-mm-dd 형식의 LocalDate 객체로 반환. 문자열의 형식이
	 * 틀리면 null 을 반환.
	 * 
	 * @return {@code null} if the dateString is not date format. (yyyyMMdd or
	 *         yyyy-MM-dd); {@code LocalDate (yyyy-mm-dd)} if the dateString can be
	 *         converted.
	 */
	public static LocalDate convertStringToLocalDate(String dateString) {
		DateTimeFormatter[] formatters = new DateTimeFormatter[] { DateTimeFormatter.ofPattern("yyyyMMdd"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd") };

		for (DateTimeFormatter formatter : formatters) {
			try {
				return LocalDate.parse(dateString, formatter);
			} catch (DateTimeParseException ignored) {
				// 무시하고 다음 형식을 시도
			}
		}

		// 모든 형식이 실패한 경우 null 반환
		return null;
	}
}
