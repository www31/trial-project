package com.java.user_service.util;

public class Validations {

	public static boolean isTextEmpty(String text) {
		return null == text || "".equalsIgnoreCase(text.trim());
	}
	
	public static boolean isTextNotEmpty(String text) {
		return !isTextEmpty(text);
	}
}
