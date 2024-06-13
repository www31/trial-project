package com.lps.ldtracker.service;

import java.util.List;

import com.lps.ldtracker.model.LdTrackerError;
import com.lps.ldtracker.model.Result;

public interface ResultService {
	public Result setResult (String status, String message, List<LdTrackerError> errors, Object data);
}
