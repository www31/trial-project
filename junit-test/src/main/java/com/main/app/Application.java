package com.main.app;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.security.SecureRandom;

public class Application {

	// Online Java Compiler
	// Use this editor to write, compile and run your Java code online

	private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	private static final String DIGITS = "0123456789";
	private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+<>?";
	private static final String PASSWORD_CHARS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARS;
	private static final SecureRandom RANDOM = new SecureRandom();

	public static void main(String[] args) {
		System.out.println(generatePassword());

	}

	public static String generatePassword() {
		return IntStream.range(0, 8).map(i -> PASSWORD_CHARS.charAt(RANDOM.nextInt(PASSWORD_CHARS.length())))
				.peek(System.out::println).mapToObj(c -> String.valueOf((char) c)).peek(System.out::println)
				.collect(Collectors.joining());
	}
}
