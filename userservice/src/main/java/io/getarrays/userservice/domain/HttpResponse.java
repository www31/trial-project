package io.getarrays.userservice.domain;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author Junior RT
 * @version 1.0
 * @license Get Arrays, LLC (https://getarrays.io)
 * @since 6/24/2023
 */
@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpResponse {
	protected String timeStamp;
	protected int statusCode;
	protected HttpStatus status;
	protected String message;
	protected String developerMessage;
	protected String path;
	protected String requestMethod;
	protected Map<?,?> data;
}
