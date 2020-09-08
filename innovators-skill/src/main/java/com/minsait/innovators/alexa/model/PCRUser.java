package com.minsait.innovators.alexa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "user", "user" })
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PCRUser {

	@JsonProperty("user")
	private String user;
	@JsonProperty("pcrs")
	private boolean pcr;
}
