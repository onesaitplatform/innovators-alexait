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
@JsonPropertyOrder({ "deviceId", "userId", "email", "fullName" })
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlexaDevice {

	@JsonProperty("deviceId")
	public String deviceId;
	@JsonProperty("userId")
	public String userId;
	@JsonProperty("email")
	public String email;
	@JsonProperty("fullName")
	public String fullName;

}
