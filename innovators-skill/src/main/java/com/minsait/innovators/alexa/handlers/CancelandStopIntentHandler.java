package com.minsait.innovators.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;
import static com.minsait.innovators.alexa.commons.CommonsInterface.CARD_TITLE;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

public class CancelandStopIntentHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AMAZON.StopIntent")
				.or(intentName("AMAZON.CancelIntent").or(intentName("AMAZON.NoIntent"))));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		return input.getResponseBuilder().withSpeech("Si necesitas cualquier cosa aquí estaré!")
				.withSimpleCard(CARD_TITLE, "Si necesitas cualquier cosa aquí estaré!").withShouldEndSession(true)
				.build();
	}

}
