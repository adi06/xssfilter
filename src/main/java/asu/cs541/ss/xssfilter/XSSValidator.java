package asu.cs541.ss.xssfilter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import asu.cs541.ss.xssfilter.rules.CustomWhiteListRules;
import asu.cs541.ss.xssfilter.rules.XSSDefenseRuleFactory;
import asu.cs541.ss.xssfilter.validator.RequestParamValidator;


public final class XSSValidator {	
	
	private XSSHttpRequestWrapper requestWrapper;	
	private XSSHttpResponseWrapper responseWrapper;
	private String filterConfig;
	private static CustomWhiteListRules rule;
	
	public XSSValidator(ServletRequest request, ServletResponse response) {
		this.requestWrapper = new XSSHttpRequestWrapper((HttpServletRequest)request);
		this.responseWrapper = new XSSHttpResponseWrapper((HttpServletResponse)response);
	}
	
	public XSSHttpRequestWrapper getRequestWrapper() {
		return requestWrapper;
	}
	public XSSHttpResponseWrapper getResponseWrapper() {
		return responseWrapper;
	}
	
	public String getFilterConfig() {
		return filterConfig;
	}
	public void setFilterConfig(String filterConfig) {
		this.filterConfig = filterConfig;
	}
	//TODO change this ...
	public CustomWhiteListRules getRule() {
		if(rule == null && filterConfig != null) {
			rule = new CustomWhiteListRules(filterConfig);
		}
		return rule;
	}
	
	
	private class XSSHttpRequestWrapper extends HttpServletRequestWrapper {

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
			 for(int index=0; index < values.length; index++){ // TODO : do only for rules mentioned in filterConfig
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
	private  class XSSHttpResponseWrapper extends HttpServletResponseWrapper {
		
		private HttpServletResponse response;

		public XSSHttpResponseWrapper(HttpServletResponse response) {
			super(response);
			this.response = response;
		}
		
		
	}
}
