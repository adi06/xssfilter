package asu.cs541.ss.xssfilter.rules;

import java.util.LinkedList;

import asu.cs541.ss.xssfilter.validator.RequestParamValidator;


public class XSSDefenseRuleFactory {
	
	private static final LinkedList<RequestParamValidator> rules = new LinkedList<RequestParamValidator>();
	
	public static LinkedList<RequestParamValidator> getRules(){
		return rules;
	}
	
	public static void addRule(RequestParamValidator rule) {
		if(!rules.contains(rule))
			rules.addFirst(rule);
	}

}
