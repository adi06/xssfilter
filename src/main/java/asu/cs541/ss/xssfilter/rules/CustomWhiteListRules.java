package asu.cs541.ss.xssfilter.rules;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

import asu.cs541.ss.xssfilter.exception.InvalidRequestException;
import asu.cs541.ss.xssfilter.model.WhiteList;
import asu.cs541.ss.xssfilter.validator.RequestParamValidator;

public  class CustomWhiteListRules implements RequestParamValidator {
	private  WhiteList whiteList;
	private  ObjectMapper mapper = new ObjectMapper();
	
	public CustomWhiteListRules() {}
	public CustomWhiteListRules(String filterConfig) {
	}

	public String validate(String param) throws InvalidRequestException {
		try {
			//TODO make an input stream 
			whiteList = mapper.readValue(new File("filterConfig"), WhiteList.class);	
			System.out.println(whiteList.getRule());
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return param;
	}
	
	public static void main(String[] args) {
		new CustomWhiteListRules().validate("hi");
	}
	

}
