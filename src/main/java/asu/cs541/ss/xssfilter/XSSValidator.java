package asu.cs541.ss.xssfilter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import asu.cs541.ss.xssfilter.rules.XSSDefenseRuleFactory;
import asu.cs541.ss.xssfilter.validator.RequestParamValidator;


public class XSSValidator {	
	
	public static ServletRequest doValidate(ServletRequest request) {
		
		return new XSSHttpRequestWrapper((HttpServletRequest)request);
	}

	private static class XSSHttpRequestWrapper extends HttpServletRequestWrapper {
		HttpServletRequest request;
		
		public XSSHttpRequestWrapper(HttpServletRequest request) {
			super(request);
			this.request= request;
		}
		
		 @Override
		    public String getParameter(String parameter) {
			 String paramValue = request.getParameterValues(parameter)[0];
			 String sanitizedParameter = paramValue;
			 for(RequestParamValidator paramValidator : XSSDefenseRuleFactory.getRules()) {
					sanitizedParameter = paramValidator.validate(sanitizedParameter);
				}
			 System.out.println("sanitizedParameter "+sanitizedParameter);
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
