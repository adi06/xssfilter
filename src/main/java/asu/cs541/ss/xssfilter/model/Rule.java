package asu.cs541.ss.xssfilter.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            The name
	 */
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return The whitelist
	 */
	@JsonProperty("whitelist")
	public List<Whitelist> getWhitelist() {
		return whitelist;
	}

	/**
	 * 
	 * @param whitelist
	 *            The whitelist
	 */
	@JsonProperty("whitelist")
	public void setWhitelist(List<Whitelist> whitelist) {
		this.whitelist = whitelist;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}