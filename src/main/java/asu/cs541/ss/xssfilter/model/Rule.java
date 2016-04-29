package asu.cs541.ss.xssfilter.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "allowed", "tags" })
public class Rule {

	@JsonProperty("name")
	private String name;
	@JsonProperty("allowed")
	private Boolean allowed;
	@JsonProperty("onFiles")
	private Boolean onFiles;
	@JsonProperty("tags")
	private List<Tag> tags = new ArrayList<Tag>();

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("allowed")
	public Boolean getAllowed() {
		return allowed;
	}

	@JsonProperty("allowed")
	public void setAllowed(Boolean allowed) {
		this.allowed = allowed;
	}

	@JsonProperty("tags")
	public List<Tag> getTags() {
		return tags;
	}

	@JsonProperty("tags")
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	public Boolean getOnFiles() {
		return onFiles;
	}

	public void setOnFiles(Boolean onFiles) {
		this.onFiles = onFiles;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name: ").append(name)
		  .append(",tags: ").append(tags);
		return sb.toString();
	}
}