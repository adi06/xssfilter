package asu.cs541.ss.xssfilter.rules;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import asu.cs541.ss.xssfilter.exception.InvalidRequestException;
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

	public String validate(String param) throws InvalidRequestException {
		if(whiteList.getRule() != null && !whiteList.getRule().isEmpty())
			System.out.println(whiteList.getRule());
		//TODO apply rules to the param
		return param;
	}

}
