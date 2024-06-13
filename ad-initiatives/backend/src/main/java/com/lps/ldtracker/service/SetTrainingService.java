package com.lps.ldtracker.service;

import java.util.List;

import com.lps.ldtracker.dto.TrainingDto;
import com.lps.ldtracker.model.Result;

public interface SetTrainingService {
	Result assignTrainings(List<String> memberIds, List<TrainingDto> trainings);
}
