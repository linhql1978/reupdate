package convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Convert {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");

	public static Date stringToDate(String str) {
		str = getSample(str);
		if (str == null)
			return null;
		else {
			Date date = null;
			try {
				// convert sample -> 'yyyyMMdd' -> string -> 'yyyy-MM-dd'
				date = sdf.parse(sdf.format(sdf1.parse(str)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}
	}

	public static String dateToString(Date date) {

		return sdf.format(date);
	}

	private static String getSample(String str) {
		// Get all digit in string, if have any exception characters -> return null
		String sample = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ' ' || str.charAt(i) == '-' || str.charAt(i) == '/') {
				continue;
			} else if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
				sample += str.charAt(i);
			} else {
				return null;
			}
		}
		// if digits not eligible -> return null
		if (sample.length() != 6 && sample.length() != 8) {
			return null;
		} else if (sample.length() == 6) { // convert sample to 8 digit
			sample = "19" + sample;
		}
		return checkSample(sample);
	}

	private static String checkSample(String sample) {
		// check sample either eligible convert to date or not
		if (sample == null)
			return null;
		else {
			int year = Integer.parseInt(sample.substring(0, 4));
			int month = Integer.parseInt(sample.substring(4, 6));
			int day = Integer.parseInt(sample.substring(6, 8));
			if (month > 12 || day > 31 || year < 1 || month < 1 || day < 1)
				return null;
			if (!checkLapYear(year) && month == 2 && day > 28)
				return null;
			if (checkLapYear(year) && month == 2 && day > 29)
				return null;
			if (day > 30 && (month == 4 || month == 6 || month == 9 || month == 11))
				return null;

		}
		return sample;
	}

	private static boolean checkLapYear(int year) {
		if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
			return true;
		return false;
	}

}
