package asu.cs541.ss.xssfilter.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "whitelist" })
public class Rule {

	@JsonProperty("name")
	private String name;
	@JsonProperty("whitelist")
	private List<Whitelist> whitelist = new ArrayList<Whitelist>();

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("whitelist")
	public List<Whitelist> getWhitelist() {
		return whitelist;
	}

	@JsonProperty("whitelist")
	public void setWhitelist(List<Whitelist> whitelist) {
		this.whitelist = whitelist;
	}
}