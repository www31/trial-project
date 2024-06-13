package com.lps.ldtracker.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetTrainingDto {
	private List<String> memberIds;
    private List<TrainingDto> trainings;
}
