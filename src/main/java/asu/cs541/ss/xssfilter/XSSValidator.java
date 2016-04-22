package asu.cs541.ss.xssfilter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import asu.cs541.ss.xssfilter.model.Rule;
import asu.cs541.ss.xssfilter.rules.XSSDefenseRuleFactory;
import asu.cs541.ss.xssfilter.validator.RequestParamValidator;


public class XSSValidator {	
	
	private XSSHttpRequestWrapper requestWrapper;
	
	private XSSHttpResponseWrapper responseWrapper;
	private String filterConfig;
	
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
		System.out.println("inside setFilterCofig with input data as:"+ filterConfig);
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Rule> allRules = mapper.readValue(
		    		filterConfig,
		            mapper.getTypeFactory().constructCollectionType(
		                    List.class, Rule.class));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
