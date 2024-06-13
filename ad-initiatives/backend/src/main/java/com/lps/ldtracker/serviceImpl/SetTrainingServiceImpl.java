package com.lps.ldtracker.serviceImpl;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.constants.LdTrackerConstants;
import com.lps.ldtracker.dto.TrainingDto;
import com.lps.ldtracker.entity.SetTraining;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.repository.SetTrainingRepository;
import com.lps.ldtracker.service.SetTrainingService;

import jakarta.transaction.Transactional;

@Service
public class SetTrainingServiceImpl implements SetTrainingService{
		private static final Logger logger =   LoggerFactory.getLogger(SetTrainingServiceImpl.class);
	 	@Autowired
	    private SetTrainingRepository trainingProgressRepository;

	    @Transactional
	    public Result assignTrainings(List<String> memberIds, List<TrainingDto> trainings) {
	    	Result result = new Result();
	    	List<SetTraining> assignedTrainings = new ArrayList<>();
	        try {
	        	for (String memberId : memberIds) {
		            for (TrainingDto training : trainings) {
		            	SetTraining progress = new SetTraining();
		                progress.setProgressId(generateProgressId());
		                progress.setRequired(true);
		                progress.setDueDate(training.getDate());
		                progress.setTrainingId(training.getValue());
		                progress.setMemberId(memberId);
		                progress.setActive(true);
		                trainingProgressRepository.save(progress);
		                assignedTrainings.add(progress);
		            }
		        }
	        	
	        	result.setData(assignedTrainings);
	        	result.setMessage(LdTrackerConstants.SUCCESS);
				result.setStatus(LdTrackerConstants.SUCCESS);
	        }catch (Exception e) {
				e.printStackTrace();
				logger.error("ERROR set training: " + e.getMessage());
				throw e;
			}
			return result;
	    }

	    private String generateProgressId() {
	    	SetTraining lastProgress = trainingProgressRepository.findTopByOrderByProgressIdDesc();
	        String lastId = lastProgress != null ? lastProgress.getProgressId() : null;
	        int nextIdNumber = lastId != null ? Integer.parseInt(lastId.replaceAll("\\D", "")) + 1 : 1;
	        return String.format("TP%010d", nextIdNumber);
	    }
}
