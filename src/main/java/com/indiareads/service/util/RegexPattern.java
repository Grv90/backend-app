package com.indiareads.service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPattern {
	public static final String ONLY_NUMBERS = "^[0-9]+$";
	public static final String MOBILE_NUMBERS = "^[7-9][0-9]+$";
	public static final String INDIAN_MOBILE = "^[789]\\d{9}$";

	public static boolean validateEmailAddress(String emailStr) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = 
				Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		return matcher.find();
	}

}




