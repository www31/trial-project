package com.lps.ldtracker.model;

import lombok.Data;

@Data
public class ValidationParamCollection <A, B, C>{
	 private final A first;
	    private final B second;
	    private final C third;

	    public ValidationParamCollection(A first,B second, C third) {
	        this.first = first;
	        this.second = second;
	        this.third = third;
	    }
}
