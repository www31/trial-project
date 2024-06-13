package com.lps.ldtracker.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lps.ldtracker.model.LdTrackerError;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.service.ResultService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService{
	public Result setResult (String status, String message, List<LdTrackerError> errors, Object data) {
		Result result = new Result();
		
		result.setStatus(status);
		result.setMessage(message);
		result.setErrors(errors);
		result.setData(data);
		
		return result;
	}
}
