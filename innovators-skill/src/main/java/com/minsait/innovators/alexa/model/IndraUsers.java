package com.minsait.innovators.alexa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Cod Empleado", "Empleado", "Rol", "Direccin", "Pais Sociedad", "Mail", "Negocio" })
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndraUsers {

	@JsonProperty("Cod Empleado")
	public Float codEmpleado;
	@JsonProperty("Empleado")
	public String empleado;
	@JsonProperty("Rol")
	public String rol;
	@JsonProperty("Dirección")
	public Float direccin;
	@JsonProperty("Pais Sociedad")
	public String paisSociedad;
	@JsonProperty("Mail")
	public String mail;
	@JsonProperty("Negocio")
	public String negocio;

}