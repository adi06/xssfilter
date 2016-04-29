package asu.cs541.ss.xssfilter;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
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
	private static final ConcurrentHashMap<ServletRequest, ServletResponse> reqResMap = new ConcurrentHashMap<ServletRequest, ServletResponse>();
	
	public XSSFilter(ServletRequest request, ServletResponse response, String filterConfig)throws InvalidParameterException{
		this(request, response);
		this.filterConfig = filterConfig;
		
		//Add custom white list to rule factory
		//TODO as this is a one time rule shoud make it global
		addRulesToFactory();
	}
	public XSSHttpResponseWrapper XSSFilterOnResponse(ServletRequest request,XSSHttpResponseWrapper response){
		//reqResMap.get(response);
		HttpServletRequest inputRequest = (HttpServletRequest) request;
		String reqMethod = inputRequest.getMethod();
//		PrintWriter writer;
//		try {
//			writer = response.getWriter();
//			writer.write("<html><body>GET/POST response</body></html>");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//HttpServletResponse outputResponse = (HttpServletResponse)response;
		System.out.println("in XSSFilterOnResponse");
		int resStatus = response.getStatus();
		System.out.println("res:"+resStatus);
		Collection<String> headerNames = response.getHeaderNames();
		for (String headerName : headerNames) {
			Collection<String> values = response.getHeaders(headerName);
			System.out.println("header :"+ headerName + "  value:"+ values);
		}
		//outputResponse.
		return null;
	}

	public XSSFilter(ServletRequest request, ServletResponse response) {
		this.requestWrapper = new XSSHttpRequestWrapper((HttpServletRequest)request);
		this.responseWrapper = new XSSHttpResponseWrapper((HttpServletResponse)response);
		reqResMap.put(request, response);
	}
	
	private void addRulesToFactory() {
		if(filterConfig != null && !filterConfig.isEmpty())
			XSSDefenseRuleFactory.addRule(new BlackListRule(new CustomWhiteListRules(filterConfig)));
		else {
			XSSDefenseRuleFactory.addRule(new BlackListRule());
			//XSSDefenseRuleFactory.addRule(new HtmlEscapeRule());
		}
	}
	public XSSHttpRequestWrapper getRequestWrapper() {
		return requestWrapper;
	}
	
	public XSSHttpResponseWrapper getResponseWrapper() {
		System.out.println("inside response wrapper");
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
	
	public  class XSSHttpResponseWrapper extends HttpServletResponseWrapper {
		
		private HttpServletResponse response;
		 private CharArrayWriter output;
		 public String toString() {
		        return output.toString();
		    }
		public XSSHttpResponseWrapper(HttpServletResponse response) {
			super(response);
			this.response = response;
			output = new CharArrayWriter();
			System.out.println("in response wrapper");
		}
		
		public PrintWriter getWriter() {
			
			System.out.println("in getwriter");
	        return new PrintWriter(output);
	    }
		
		
	}
}
