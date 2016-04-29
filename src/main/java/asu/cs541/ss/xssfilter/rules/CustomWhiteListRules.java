package asu.cs541.ss.xssfilter.rules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;

import asu.cs541.ss.xssfilter.exception.InvalidParameterException;
import asu.cs541.ss.xssfilter.model.Rule;
import asu.cs541.ss.xssfilter.model.Tag;
import asu.cs541.ss.xssfilter.model.WhiteList;
import asu.cs541.ss.xssfilter.validator.RequestParamValidator;

public class CustomWhiteListRules {
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

	public WhiteList getWhiteList() {
		return whiteList;
	}
	public String validate(String param) throws InvalidParameterException {
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		for(Rule rule : whiteList.getRule()){
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
			if(doc.getElementsByTag(tagName) != null) {
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
					doc.getElementsByTag(tagName).removeAttr(str);
				}
				System.out.println("oooo "+doc.getElementsByTag(tagName).get(0));
			}
		}
		param = doc.getElementsByTag(tagName).get(0).toString();
		return param;
	}
	//TODO remove this
	public static void main(String[] args) {
		String filterConfig = "{\"rule\":[{\"name\" : \"Escape\",\"allowed\" : true, \"tags\":[{\"name\" : \"script\",\"allowed\": [\"src\"]}]}]}";
		new CustomWhiteListRules(filterConfig).validate("<script src=link href=\"url\"/>");
	}

}
