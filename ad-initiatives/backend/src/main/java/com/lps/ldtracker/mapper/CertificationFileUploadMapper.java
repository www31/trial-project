package com.lps.ldtracker.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CertificationFileUploadMapper {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}																								
}
