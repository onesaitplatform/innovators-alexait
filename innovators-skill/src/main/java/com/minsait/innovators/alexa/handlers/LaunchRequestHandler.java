package com.minsait.innovators.alexa.handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

public class LaunchRequestHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(Predicates.requestType(LaunchRequest.class));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		final String speechText = "Bienvenido a la skill custom de Alexa, desarrollada para la demo de innovators, puedes empezar pidiendo ayuda.";
		return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("InnovatorsDemo", speechText)
				.withReprompt(speechText).build();
	}

}
