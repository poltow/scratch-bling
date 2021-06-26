package com.itscout.domain.model;

import java.util.Arrays;
import java.util.List;

public class Size {

	private static List<String> sizes = Arrays.asList(new String[] { "S", "M", "L", "XL" });

	public static boolean isValid(String value) {
		return sizes.contains(value);
	}

	public static String values() {
		return sizes.toString();
	}
	
}
