package com.lps.ldtracker.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface CertificationUploadService {
	public void init();
	public void save(MultipartFile file);
	Resource getFileByName(String fileName);
	public void deleteAll();
	Stream<Path> loadAllFiles();
}
