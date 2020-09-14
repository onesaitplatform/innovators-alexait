package com.minsait.innovators.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;
import static com.minsait.innovators.alexa.commons.CommonsInterface.CARD_TITLE;
import static com.minsait.innovators.alexa.commons.CommonsInterface.LAST_INTENT_ATT;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

public class CancelandStopIntentHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AMAZON.StopIntent")
				.or(intentName("AMAZON.CancelIntent").or(intentName("AMAZON.NoIntent"))));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		String output = "Si necesitas cualquier cosa aquí estaré!";
		if (((IntentRequest) input.getRequestEnvelope().getRequest()).getIntent().getName().equals("AMAZON.NoIntent")) {
			input.getAttributesManager().getSessionAttributes().remove(LAST_INTENT_ATT);
			output = "Vale, avísame si necesitas algo más!";
		}
		return input.getResponseBuilder().withSpeech(output).withSimpleCard(CARD_TITLE, output)
				.withShouldEndSession(false).build();
	}

}
