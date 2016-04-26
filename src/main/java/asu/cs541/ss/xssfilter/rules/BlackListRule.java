package asu.cs541.ss.xssfilter.rules;

import java.util.regex.Pattern;

import asu.cs541.ss.xssfilter.exception.InvalidRequestException;
import asu.cs541.ss.xssfilter.validator.RequestParamValidator;
/*
 * Credits https://gist.github.com/madoke/2347047 
 */
public class BlackListRule implements RequestParamValidator{

	public String validate(String param) throws InvalidRequestException {
		
		// Avoid anything between script tags
		Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
		// TODO Auto-generated method stub
		param = scriptPattern.matcher(param).replaceAll("");
		
		// Avoid anything in a src='...' type of expression
		scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		param = scriptPattern.matcher(param).replaceAll("");
		
		scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		param = scriptPattern.matcher(param).replaceAll("");
		
		// Remove any lonesome </script> tag
		scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
		param = scriptPattern.matcher(param).replaceAll("");
		
		// Remove any lonesome <script ...> tag
		scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		param = scriptPattern.matcher(param).replaceAll("");
		
		// Avoid eval(...) expressions
		scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		param = scriptPattern.matcher(param).replaceAll("");
		
		// Avoid expression(...) expressions
		scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		param = scriptPattern.matcher(param).replaceAll("");
		
		// Avoid javascript:... expressions
		scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
		param = scriptPattern.matcher(param).replaceAll("");
		
		// Avoid onload= expressions
		scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		param = scriptPattern.matcher(param).replaceAll("");
		
		return param;
	}

}
