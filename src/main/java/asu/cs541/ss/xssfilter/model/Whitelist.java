package asu.cs541.ss.xssfilter.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "rule" })
public class WhiteList {

	@JsonProperty("rule")
	private List<Rule> rule = new ArrayList<Rule>();

	@JsonProperty("rule")
	public List<Rule> getRule() {
		return rule;
	}

	@JsonProperty("rule")
	public void setRule(List<Rule> rule) {
		this.rule = rule;
	}
}