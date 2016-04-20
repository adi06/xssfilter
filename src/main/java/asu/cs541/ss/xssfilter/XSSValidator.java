package asu.cs541.ss.xssfilter;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import asu.cs541.ss.xssfilter.rules.XSSDefenseRuleFactory;
import asu.cs541.ss.xssfilter.validator.RequestParamValidator;


public class XSSValidator {	
	
	public static ServletRequest doValidate(ServletRequest request) {
		
		/*if(request != null) {
			for(Map.Entry<String, String[]> reqParamMap : request.getParameterMap().entrySet()) {
				for(String param : reqParamMap.getValue()){
					for(RequestParamValidator paramValidator : XSSDefenseRuleFactory.getRules()) {
						paramValidator.validate(param);
					}
				}
			}	
		}*/
		
		return new XSSHttpRequestWrapper((HttpServletRequest)request);
	}

	private static class XSSHttpRequestWrapper extends HttpServletRequestWrapper {
		
		public XSSHttpRequestWrapper(HttpServletRequest request) {
			super(request);
		}
		
		 @Override
		    public String getParameter(String parameter) {	
			 String sanitizedParameter = parameter;
			 for(RequestParamValidator paramValidator : XSSDefenseRuleFactory.getRules()) {
					sanitizedParameter = paramValidator.validate(sanitizedParameter);
				}
		        return sanitizedParameter;
		    }
		 	
		 	//TODO
		    @Override
		    public String getHeader(String name) {
		        return null;
		    }
		    //TODO implementations
		    
	}
}
