package asu.cs541.ss.xssfilter.rules;

import java.util.regex.Pattern;

import asu.cs541.ss.xssfilter.exception.InvalidParameterException;
import asu.cs541.ss.xssfilter.validator.RequestParamValidator;
/*
 * Credits https://gist.github.com/madoke/2347047 
 */
public class BlackListRule implements RequestParamValidator{
	private Pattern[] paramPattern = {
			
			Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
			Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
			Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
			Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)		
	};
	public String validate(String param) throws InvalidParameterException {
		
		for(Pattern pattern : paramPattern){
			if(pattern.matcher(param).matches()){
				throw new InvalidParameterException("Invalid paramter: "+param, 400);
			}
		}
		
		return param;
	}

}
