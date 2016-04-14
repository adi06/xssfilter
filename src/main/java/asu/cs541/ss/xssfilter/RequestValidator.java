package asu.cs541.ss.xssfilter;

import java.util.Map;

import javax.servlet.ServletRequest;

import asu.cs541.ss.xssfilter.rules.XSSDefenseRuleFactory;
import asu.cs541.ss.xssfilter.validator.RequestParamValidator;


public class RequestValidator {

	public static ServletRequest validate(ServletRequest request) {
		
		if(request != null) {
			for(Map.Entry<String, String[]> reqParamMap : request.getParameterMap().entrySet()) {
				for(String param : reqParamMap.getValue()){
					for(RequestParamValidator paramValidator : XSSDefenseRuleFactory.getRules()) {
						paramValidator.validate(param);
					}
				}
			}	
		}
		
		return request;
	}

}
