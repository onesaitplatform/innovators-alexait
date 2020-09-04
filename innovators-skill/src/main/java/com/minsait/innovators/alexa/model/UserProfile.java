package com.minsait.innovators.alexa.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "user_id", "email", "name", "postal_code" })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

	@JsonProperty("user_id")
	public String userId;
	@JsonProperty("email")
	public String email;
	@JsonProperty("name")
	public String name;
	@JsonProperty("postal_code")
	public String postalCode;

}
