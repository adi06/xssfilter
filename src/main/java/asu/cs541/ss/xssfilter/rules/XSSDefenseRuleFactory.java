package asu.cs541.ss.xssfilter.rules;

import java.util.ArrayList;
import java.util.List;

import asu.cs541.ss.xssfilter.validator.RequestParamValidator;


public class XSSDefenseRuleFactory {
	
	private static final List<RequestParamValidator> rules = new ArrayList<RequestParamValidator>();
	
	//TODO add the other rules here..
	static {
		rules.add(new HtmlEscapeRule());
	}
	
	public static List<RequestParamValidator> getRules(){
		return rules;
	}
}
