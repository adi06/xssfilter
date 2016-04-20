package asu.cs541.ss.xssfilter.rules;


import org.apache.commons.lang3.StringEscapeUtils;

import asu.cs541.ss.xssfilter.exception.InvalidRequestException;
import asu.cs541.ss.xssfilter.validator.RequestParamValidator;

public class HtmlEscapeRule implements RequestParamValidator {
	
	public HtmlEscapeRule() {
		
	}

	public String validate(String param) throws InvalidRequestException {
		return StringEscapeUtils.escapeHtml4(param);
		
	}
	

}
