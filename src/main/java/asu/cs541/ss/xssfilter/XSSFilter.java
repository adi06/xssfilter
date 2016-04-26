package asu.cs541.ss.xssfilter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import asu.cs541.ss.xssfilter.exception.InvalidParameterException;
import asu.cs541.ss.xssfilter.rules.BlackListRule;
import asu.cs541.ss.xssfilter.rules.CustomWhiteListRules;
import asu.cs541.ss.xssfilter.rules.XSSDefenseRuleFactory;
import asu.cs541.ss.xssfilter.validator.RequestParamValidator;


public final class XSSFilter {	
	
	private XSSHttpRequestWrapper requestWrapper;	
	private XSSHttpResponseWrapper responseWrapper;
	private String filterConfig;
	private static final ConcurrentHashMap<XSSHttpResponseWrapper, XSSHttpRequestWrapper> reqResMap = new ConcurrentHashMap<XSSHttpResponseWrapper, XSSHttpRequestWrapper>();
	
	public XSSFilter(ServletRequest request, ServletResponse response, String filterConfig)throws InvalidParameterException{
		this(request, response);
		this.filterConfig = filterConfig;
		
		//Add custom white list to rule factory
		//TODO as this is a one time rule shoud make it global
		addRulesToFactory();
	}
	

	public XSSFilter(ServletRequest request, ServletResponse response) {
		this.requestWrapper = new XSSHttpRequestWrapper((HttpServletRequest)request);
		this.responseWrapper = new XSSHttpResponseWrapper((HttpServletResponse)response);
		reqResMap.put(responseWrapper, requestWrapper);
	}
	
	private void addRulesToFactory() {
		if(filterConfig != null && !filterConfig.isEmpty())
			XSSDefenseRuleFactory.addRule(new CustomWhiteListRules(filterConfig));
		else {
			XSSDefenseRuleFactory.addRule(new BlackListRule());
			//XSSDefenseRuleFactory.addRule(new HtmlEscapeRule());
		}
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
	
	private class XSSHttpRequestWrapper extends HttpServletRequestWrapper {

		private HttpServletRequest request;
		private Map<String, String[]> modifiedRequestParamMap;
		
		public XSSHttpRequestWrapper(HttpServletRequest request) {
			super(request);
			this.request= request;
			//TODO clone existing request map 
			this.modifiedRequestParamMap = new HashMap<String, String[]>();
		}
		
		    @Override
		    public String getParameter(String name) {
		 	String[] values = request.getParameterValues(name);
		 	String sanitizedParameter = null ;
		 	if(filterConfig != null){
		 		
		 	}
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
