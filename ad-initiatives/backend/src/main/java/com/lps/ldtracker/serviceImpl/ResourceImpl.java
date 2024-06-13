package com.lps.ldtracker.serviceImpl;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.constants.LdTrackerConstants;
import com.lps.ldtracker.dto.ResourceDto;
import com.lps.ldtracker.entity.Resource;
import com.lps.ldtracker.mapper.ResourceMapper;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.repository.ResourceRepository;
import com.lps.ldtracker.service.ResourceService;

@Service
public class ResourceImpl implements ResourceService{
	private static final Logger logger =   LoggerFactory.getLogger(ResourceImpl.class);
	
	 private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	 private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	 private static final String DIGITS = "0123456789";
	 private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+<>?";
	 private static final String PASSWORD_CHARS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARS;
	 private static final SecureRandom RANDOM = new SecureRandom();
	
	private ResourceRepository resourceRepository;
	private final PasswordEncoder passwordEncoder;
	
	public ResourceImpl(ResourceRepository resourceRepository, PasswordEncoder passwordEncoder) {
		this.resourceRepository = resourceRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	public static String generatePassword() {
        return IntStream.range(0, 8)
                        .map(i -> PASSWORD_CHARS.charAt(RANDOM.nextInt(PASSWORD_CHARS.length())))
                        .mapToObj(c -> String.valueOf((char) c))
                        .collect(Collectors.joining());
    }
	
	@Override
	public Optional<Resource> findByEmailAddress(String email) { 
		return resourceRepository.findByEmailAddress(email);
	}
	
	@Override
	public Optional<Resource> findById(Long id) { 
		return resourceRepository.findById(id);
	}
	
	@Override
	public Result addResource(ResourceDto resourceDto) {
		try {
			Result result = new Result();
			Optional <Resource>_resource = this.findByEmailAddress(resourceDto.getEmailAddress());
			Optional <Resource> lastResource = resourceRepository.findTopByOrderByMemberIdDesc();
			
			if (_resource.isPresent()) {
				result.setMessage(LdTrackerConstants.USER_ALREADY_EXISTS);
				result.setStatus(LdTrackerConstants.ERROR);
				return result;
			}
			
			long newMemberIdNumericPart = 1; // Default value if no previous record exists
	        if (lastResource.isPresent()) {
	            String lastMemberId = lastResource.get().getMemberId();
	            String numericPart = lastMemberId.substring(3); // Assuming the prefix length is fixed
	            newMemberIdNumericPart = Long.parseLong(numericPart) + 1;
	        }
	        
	        String newMemberId = "LPS" + String.format("%013d", newMemberIdNumericPart);
			
			String generatedPassword = passwordEncoder.encode(ResourceImpl.generatePassword());
			Resource resource = ResourceMapper.resourceMapper(resourceDto, generatedPassword, newMemberId);
			Resource saveResource = resourceRepository.save(resource);
			
			result.setData(ResourceMapper.resourceMapperDto(saveResource));
			result.setMessage(LdTrackerConstants.SUCCESS);
			result.setStatus(LdTrackerConstants.SUCCESS);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR add resource: " + e.getMessage());
			throw e;
		}
	}


	@Override
	public Result editResource(String id, ResourceDto resourceDto) {
		Result result = new Result();
        Optional<Resource> resourceOptional = resourceRepository.findByMemberId(id);

        if (resourceOptional.isPresent()) {
            Resource resource = resourceOptional.get();

            // Update the resource fields with the DTO values
            resource.setLastname(resourceDto.getLastname());
            resource.setFirstname(resourceDto.getFirstname());
            resource.setMiddlename(resourceDto.getMiddlename());
            resource.setSuffix(resourceDto.getSuffix());
            resource.setGender(resourceDto.getGender());
            resource.setEmpId(resourceDto.getEmpId());
            resource.setEmailAddress(resourceDto.getEmailAddress());
            resource.setCareerStep(resourceDto.getCareerStep());
            resource.setRegion(resourceDto.getRegion());
            resource.setIsEnabled(resourceDto.getIsEnabled());
            resource.setTeam(resourceDto.getTeam());
            resource.setRole(resourceDto.getRole());
//            resource.setStatus(resourceDto.getStatus());
            resource.setSkills(resourceDto.getSkills());
            
            Resource updatedResource = resourceRepository.save(resource);

            result.setData(ResourceMapper.resourceMapperDto(updatedResource));
            result.setMessage("Resource updated successfully.");
            result.setStatus("SUCCESS");
        } else {
            result.setMessage("Resource not found.");
            result.setStatus("ERROR");
        }

        return result;
	}

	@Override
	public Result viewResource(String id) {
		try {
			Result result = new Result();
			Optional <Resource> resource = resourceRepository.findByMemberId(id); 
			if (!resource.isPresent()) {
				result.setMessage(LdTrackerConstants.USER_NOT_EXISTS);
				result.setStatus(LdTrackerConstants.ERROR);
				return result;
			}
			result.setMessage(LdTrackerConstants.SUCCESS);
			result.setStatus(LdTrackerConstants.SUCCESS);
			result.setData(resource);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR view resource: " + e.getMessage());
			throw e;
		}
		
	}
	
	public List<ResourceDto> getAllResources() {
      
        return resourceRepository.findAll().stream()
            .map(ResourceMapper::resourceMapperDto)
            .collect(Collectors.toList());
    }
	
	
}
