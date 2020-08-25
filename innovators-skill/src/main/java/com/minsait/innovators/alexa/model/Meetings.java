package com.minsait.innovators.alexa.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "date", "members", "canceled", "building", "room" })
public class Meetings {

	@JsonProperty("date")
	public String date;
	@JsonProperty("members")
	public List<String> members = null;
	@JsonProperty("canceled")
	public Boolean canceled;
	@JsonProperty("building")
	public String building;
	@JsonProperty("room")
	public String room;

}
