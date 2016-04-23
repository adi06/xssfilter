package asu.cs541.ss.xssfilter.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "allowed" })
public class Tag {

	@JsonProperty("name")
	private String name;
	@JsonProperty("allowed")
	private List<String> allowed = new ArrayList<String>();

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("allowed")
	public List<String> getAllowed() {
		return allowed;
	}

	@JsonProperty("allowed")
	public void setAllowed(List<String> allowed) {
		this.allowed = allowed;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name: ").append(name)
		  .append(",allowed: ").append(allowed);
		return sb.toString();
	}

}