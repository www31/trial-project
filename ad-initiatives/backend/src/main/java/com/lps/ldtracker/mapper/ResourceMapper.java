package com.lps.ldtracker.mapper;

import com.lps.ldtracker.dto.ResourceDto;
import com.lps.ldtracker.entity.Resource;

public class ResourceMapper {
	public static Resource resourceMapper(ResourceDto resourceDto, String generatedPassword, String newMemberId) {
		return new Resource(
					newMemberId,
					resourceDto.getFirstname(),
					resourceDto.getLastname(),
					resourceDto.getMiddlename(),
					resourceDto.getSuffix(),
					resourceDto.getGender(),
					resourceDto.getEmpId(),
					resourceDto.getEmailAddress(),
					resourceDto.getRegion(),
					resourceDto.getIsEnabled(),
					resourceDto.getCareerStep(),
					resourceDto.getTeam(),
					"",
					generatedPassword,
					resourceDto.getRole(),
					resourceDto.getSkills()
				);
	}
	
	public static ResourceDto resourceMapperDto(Resource resource) {
		return new ResourceDto(
					resource.getMemberId(),
					resource.getFirstname(),
					resource.getLastname(),
					resource.getMiddlename(),
					resource.getSuffix(),
					resource.getGender(),
					resource.getEmpId(),
					resource.getEmailAddress(),
					resource.getRegion(),
					resource.isEnabled(),
					resource.getCareerStep(),
					resource.getTeam(),
					resource.getStatus(),
					resource.getPassword(),
					resource.getRole(),
					resource.getSkills()
				);
	}
}
