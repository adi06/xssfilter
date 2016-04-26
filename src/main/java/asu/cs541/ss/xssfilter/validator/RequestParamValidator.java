package asu.cs541.ss.xssfilter.validator;

import asu.cs541.ss.xssfilter.exception.InvalidParameterException;

public interface RequestParamValidator {
	
	public String validate(String param) throws InvalidParameterException;

}
