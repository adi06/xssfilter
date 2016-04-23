package asu.cs541.ss.xssfilter.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "allow" })
public class Whitelist {

	@JsonProperty("name")
	private String name;
	@JsonProperty("allow")
	private List<String> allow = new ArrayList<String>();

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("allow")
	public List<String> getAllow() {
		return allow;
	}

	@JsonProperty("allow")
	public void setAllow(List<String> allow) {
		this.allow = allow;
	}

}