package com.minsait.innovators.alexa.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "creator", "assigned", "creation_date", "expiration_date", "state", "title", "description" })
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TeamTasks {

	public enum State {
		EN_CURSO("en curso"), COMPLETADA("completada"), EN_ESPERA("en espera");
		@Getter
		private String speechState;

		State(String state) {
			speechState = state;
		}

		public static State fromString(String state) {
			for (final State s : State.values()) {
				if (s.speechState.equalsIgnoreCase(state)) {
					return s;
				}
			}
			return null;
		}
	}

	@JsonProperty("creator")
	public String creator;
	@JsonProperty("assigned")
	public List<String> assigned;
	@JsonProperty("creation_date")
	public String creationDate;
	@JsonProperty("expiration_date")
	public String expirationDate;
	@JsonProperty("state")
	public State state;
	@JsonProperty("title")
	public String title;
	@JsonProperty("description")
	public String description;

}