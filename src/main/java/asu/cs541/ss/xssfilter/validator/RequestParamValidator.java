package asu.cs541.ss.xssfilter.validator;

import asu.cs541.ss.xssfilter.exception.InvalidRequestException;

public interface RequestParamValidator {
	
	public void validate(String param) throws InvalidRequestException;

}
