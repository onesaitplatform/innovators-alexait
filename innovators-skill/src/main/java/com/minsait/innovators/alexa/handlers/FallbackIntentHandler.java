package com.minsait.innovators.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.minsait.innovators.alexa.commons.CommonsInterface;

public class FallbackIntentHandler implements RequestHandler {
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AMAZON.FallbackIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		final String speechText = "Lo siento, no te he entendido, ¡prueba pidiendo ayuda!";
		return input.getResponseBuilder().withSpeech(speechText).withSimpleCard(CommonsInterface.CARD_TITLE, speechText)
				.withReprompt(speechText).withShouldEndSession(false).build();
	}
}
