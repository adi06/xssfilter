package asu.cs541.ss.xssfilter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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

		private HttpServletRequest request;
		private Map<String, String[]> modifiedRequestParamMap;  
		
		public XSSHttpRequestWrapper(HttpServletRequest request) {
			super(request);
			this.request= request;
			this.modifiedRequestParamMap = new HashMap<String, String[]>();
		}
		
		    @Override
		    public String getParameter(String name) {
		 	String[] values = this.request.getParameterValues(name);
		 	String sanitizedParameter = null ;
			 for(int index=0; index < values.length; index++){
				 for(RequestParamValidator paramValidator : XSSDefenseRuleFactory.getRules()) {
					 sanitizedParameter = paramValidator.validate(values[index]);
					 values[index] = sanitizedParameter;
				 }		
			 }
			 modifiedRequestParamMap.put(name, values);
			 return sanitizedParameter;
		    }
		    
		    @Override
		    public Map<String, String[]> getParameterMap(){
		    	return this.modifiedRequestParamMap;
		    }
		    
		    @Override
		    public String[] getParameterValues(String name) {
		    	return this.modifiedRequestParamMap.get(name);
		    }
		    
		    @Override
		    public Enumeration<String> getParameterNames() {
		    	return Collections.enumeration(this.modifiedRequestParamMap.keySet());
		    }
		    
		    
	}
}
