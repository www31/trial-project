package com.lps.ldtracker.service;

import java.util.List;

import com.lps.ldtracker.dto.ManageTrainingDto;
import com.lps.ldtracker.dto.TrainingLinksDto;
import com.lps.ldtracker.entity.Training_Dtl;
import com.lps.ldtracker.model.MemberInfo;
import com.lps.ldtracker.model.SkillSet;

public interface ManageTrainingService {

	List<ManageTrainingDto> getTrainingList();
	public void deleteTrainingById(Integer Id);
	ManageTrainingDto saveTraining(ManageTrainingDto training);	
	public List<TrainingLinksDto> saveTrainingLinks(List<TrainingLinksDto> list);
	String deleteTraining(String trainingId);
	List<TrainingLinksDto> getTrainingLinks(String trainingId);
	List<ManageTrainingDto> getTrainingsbyUser();
	List<ManageTrainingDto> searchTrainingByCriteria(String trainingName, String skillName, String startDate, String endDate);
	SkillSet registerUserTraining(SkillSet userTraining);	


}
