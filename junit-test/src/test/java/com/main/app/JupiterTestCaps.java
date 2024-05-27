/**
 * 
 */
package com.main.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 
 */
class JupiterTestCaps {

	@ParameterizedTest
	@MethodSource("parametersSource")
	void testAllCaps(String parameter) {
		for (char c : parameter.toCharArray()) {
			System.out.println(c);
			assertTrue(Character.isUpperCase(c));
		}
	}

	static Stream<String> parametersSource() {
		return Stream.of("JUNIT", "JAVA", "JUnit5");
	}

}
