package com.minsait.innovators.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

public class HelpIntentHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AMAZON.HelpIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		final String speechText = "Puedes preguntarme por nuevas noticias de la empresa, información sobre el covid, próximas reuniones e incluso mandar mensajes";
		final String repromptText = "Puedes empezar diciendo, cuales son las últimas noticias o cual es mi próxima reunión";
		return input.getResponseBuilder().withSimpleCard("InnovatorsDemo", speechText).withSpeech(speechText)
				.withReprompt(repromptText).withShouldEndSession(false).build();
	}

}
