package com.minsait.innovators.alexa.handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

public class MeetingsIntentHandler implements RequestHandler {
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(Predicates.intentName("MeetingsIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		final String speechText = "Reuniones:";
		// TO-DO request news
		return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("InnovatorsDemo", speechText).build();
	}

}
