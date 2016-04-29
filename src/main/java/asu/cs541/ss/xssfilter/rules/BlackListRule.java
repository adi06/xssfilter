package asu.cs541.ss.xssfilter.rules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;

import asu.cs541.ss.xssfilter.exception.InvalidParameterException;
import asu.cs541.ss.xssfilter.model.Rule;
import asu.cs541.ss.xssfilter.model.Tag;
import asu.cs541.ss.xssfilter.model.WhiteList;
import asu.cs541.ss.xssfilter.util.EscapeUtils;
import asu.cs541.ss.xssfilter.validator.RequestParamValidator;
/*
 * Credits https://gist.github.com/madoke/2347047 
 */
public class BlackListRule implements RequestParamValidator{
	private CustomWhiteListRules customWhiteListRules;
	private Pattern[] paramPattern = {
			
			Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
			Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
			Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("javascript", Pattern.CASE_INSENSITIVE),
			Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("[img]+")
	};
	
	public BlackListRule() {
		
	}
	public BlackListRule(CustomWhiteListRules customWhiteListRules) {
		this.customWhiteListRules = customWhiteListRules;
	}
	
	public String validate(String param) throws InvalidParameterException {

		if(param == null || param.isEmpty()) {
			return param;
		}
		
		if(customWhiteListRules == null) {
			for(Pattern pattern : paramPattern){		
				if(pattern.matcher(param).matches()){
					//throw new InvalidParameterException("Invalid paramter: "+param, 400);
					return EscapeUtils.escapeHtml(param);
				}
			}
			return param;
		}
		
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		for(Rule rule : customWhiteListRules.getWhiteList().getRule()){
			for(Tag tag: rule.getTags()){
				map.put(tag.getName(), tag.getAllowed());
			}
		}
		org.jsoup.nodes.Document doc = Jsoup.parseBodyFragment(param);
		Element body = doc.body();
		String tagName = null;
		System.out.println("body:"+body.toString());
		for(Map.Entry<String, List<String>> entrySet : map.entrySet()) {
			 tagName = entrySet.getKey();
			if(doc.getElementsByTag(tagName) != null && !doc.getElementsByTag(tagName).isEmpty()) {
				List<Attribute> inputAttributes = doc.getElementsByTag(tagName).get(0).attributes().asList();//TODO loop it
				List<String> inputAttributeKeys = new ArrayList<String>();
				for(Attribute attr : inputAttributes){
					inputAttributeKeys.add(attr.getKey());
				}
				System.out.println("aaaa:" + inputAttributeKeys.toString());
				
				List<String> ruleAttributes = entrySet.getValue();
				System.out.println("bbbb:" + ruleAttributes.toString());
				inputAttributeKeys.removeAll(ruleAttributes);
				System.out.println("cccc:" + inputAttributeKeys.toString()); //case sensitive
				
				for(String str: inputAttributeKeys){
					//String value = EscapeUtils.escapeHtml(doc.getElementsByAttribute(str).toString());
					doc.getElementsByTag(tagName).removeAttr(str);
					
				}
				System.out.println("oooo "+doc.getElementsByTag(tagName).get(0));
				param = doc.getElementsByTag(tagName).get(0).toString();
			}
			else {
				return EscapeUtils.escapeHtml(param);
			}
		}
		return param;		
	}
}
