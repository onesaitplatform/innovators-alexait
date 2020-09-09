package com.minsait.innovators.alexa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "text", "description", "date", "covid_related" })
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyNews {

	@JsonProperty("text")
	private String text;
	@JsonProperty("description")
	private String description;
	@JsonProperty("date")
	private String date;
	@JsonProperty("covid_related")
	private Boolean covidRelated;

}