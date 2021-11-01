package pt.uc.dei.paj.util;


import java.util.Set;
import java.util.regex.Pattern;

import pt.uc.dei.paj.dto.UserDto;

public class Validator {
	public static boolean isValidUser(UserDto candidate) {
		return (candidate != null && isValidEmail(candidate.getEmail())
				&& isValidPassword(candidate.getPassword()));
	}
	
	public static boolean isValidEmail(String email) {
		if (email == null) {
			return false;
		} else if (email.matches(Constants.EMAIL_REGEX)) {
			return true;
		} else {
			throw new IllegalArgumentException("Email not valid");
		}
	}

	public static boolean isValidPassword(String candidate) {
		return (candidate != null && Pattern.matches(Constants.PASSWORD_REGEX, candidate));

	}
	
	public static boolean isValidLogin(String email, String password) {
		if (isValidEmail(email) && isValidPassword(password)) {
			return true;
		}
		return false;
	}

	public static boolean isNullOrEmpty(String str) {
		if (str != null && !str.trim().isEmpty()) {
			return false;
		}
		return true;
	}

	public static boolean verifyProvidedPassword(String providedPassword, String securePassword, String salt) {
		boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword, securePassword, salt);

		if (passwordMatch) {
			return true;
		} else {
			return false;
		}
	}

	
}
