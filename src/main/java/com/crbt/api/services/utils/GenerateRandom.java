package com.crbt.api.services.utils;

import java.util.Random;

public class GenerateRandom {

	public static int getOtp() {
		return getRandomNumberInRange(100000, 200000);
	}
	
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
