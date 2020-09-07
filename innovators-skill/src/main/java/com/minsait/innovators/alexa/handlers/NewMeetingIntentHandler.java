package com.minsait.innovators.alexa.handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

public class NewMeetingIntentHandler implements RequestHandler {
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(Predicates.intentName("NewMeetingIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
