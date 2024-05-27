package com.main.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class JupiterTestCsv {
	@ParameterizedTest
	@CsvSource({ "foo, 1", "bar, 2", "baz, 3" })
	void testCsvSource(String arg1, int arg2) {
		assertTrue(arg1.contains("a"));
		assertTrue(arg2 > 1);
	}
}
