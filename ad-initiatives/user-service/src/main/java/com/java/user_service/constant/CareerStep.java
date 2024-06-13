package com.java.user_service.constant;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum CareerStep {

	I03(1, "BB1", "I03"), I04(2, "BB1", "I04"), I05(3, "BB1", "I05"), I06(4, "BB2", "I06"), I07(5, "BB2", "I07");

	private int id;
	private String broadband;
	private String careerStep;

	private CareerStep(int id, String broadband, String careerStep) {
		this.id = id;
		this.broadband = broadband;
		this.careerStep = careerStep;
	}

	public static CareerStep getById(int i) {
		return Arrays.asList(CareerStep.values()).stream().filter(cs -> cs.getId() == i).findFirst().orElse(null);
	}

	public static CareerStep getByCareerStep(String careerStep) {
		return Arrays.asList(CareerStep.values()).stream().filter(cs -> cs.getCareerStep().equalsIgnoreCase(careerStep))
				.findFirst().orElse(null);
	}
}
