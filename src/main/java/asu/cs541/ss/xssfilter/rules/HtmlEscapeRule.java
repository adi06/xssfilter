package asu.cs541.ss.xssfilter.rules;

import asu.cs541.ss.xssfilter.exception.InvalidRequestException;
import asu.cs541.ss.xssfilter.util.EscapeUtils;
import asu.cs541.ss.xssfilter.validator.RequestParamValidator;

public class HtmlEscapeRule implements RequestParamValidator {
	
	public HtmlEscapeRule() {
		
	}

	public String validate(String param) throws InvalidRequestException {
		
		return EscapeUtils.escapeHtml(param);
		
	}
	

}
