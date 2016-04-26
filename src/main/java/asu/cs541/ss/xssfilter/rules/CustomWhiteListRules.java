package asu.cs541.ss.xssfilter.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import asu.cs541.ss.xssfilter.exception.InvalidParameterException;
import asu.cs541.ss.xssfilter.model.Rule;
import asu.cs541.ss.xssfilter.model.Tag;
import asu.cs541.ss.xssfilter.model.WhiteList;
import asu.cs541.ss.xssfilter.validator.RequestParamValidator;

public class CustomWhiteListRules implements RequestParamValidator {
	private WhiteList whiteList;
	private ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = Logger.getLogger("CustomWhiteListRules");

	public CustomWhiteListRules(String filterConfig) {
		try {
			if(filterConfig != null && !filterConfig.isEmpty())
				whiteList = mapper.readValue(filterConfig, WhiteList.class);
		} catch (Exception e) {
			// TODO replace with sl4j logger
			logger.log(Level.SEVERE, e.toString());
		}
	}

	public String validate(String param) throws InvalidParameterException {
		if(whiteList.getRule() != null && !whiteList.getRule().isEmpty()) {
			for(Rule rule : whiteList.getRule()){
				List<String> regexMatch = new ArrayList<String>();
				StringBuffer regex = new StringBuffer();
				for(Tag tag : rule.getTags()) {
					String name = tag.getName();
					for(String tags : tag.getAllowed()){
						//sample input <SCRIPT SRC=link/>
						regex.append("[^")
						.append("<"+name)
						.append(tags+"=>")
						.append("]+");
						
						Pattern pattern = Pattern.compile(regex.toString());
						Matcher match = pattern.matcher(param);
						while(match.find()){
							regexMatch.add(match.group());
						}
						for(String token : regexMatch) {
							if(token !=null && !token.isEmpty()){
								
							}
						}
						System.out.println(regexMatch);
					}
				}
			}
		}
			System.out.println(whiteList.getRule());
		//TODO apply rules to the param
		return param;
	}
	//TODO remove this
	public static void main(String[] args) {
		String filterConfig = "{\"rule\":[{\"name\" : \"Escape\",\"allowed\" : true, \"tags\":[{\"name\" : \"SCRIPT\",\"allowed\": [\"SRC\"]}]}]}";
		new CustomWhiteListRules(filterConfig).validate("<SCRIPT SRC=link>");
	}

}
