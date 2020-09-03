package com.minsait.innovators.alexa.handlers;

import static com.minsait.innovators.alexa.commons.CommonsInterface.USER_ATT;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.minsait.innovators.alexa.model.AlexaDevice;

public class LaunchRequestHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(Predicates.requestType(LaunchRequest.class));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		String speechText;
		if (input.getAttributesManager().getRequestAttributes().get(USER_ATT) != null) {
			final AlexaDevice device = (AlexaDevice) input.getAttributesManager().getRequestAttributes().get(USER_ATT);
			speechText = "Bienvenido a la skill de innovators " + device.getFullName()
					+ " , desarrollada para la demo de innovators, puedes empezar pidiendo ayuda.";
		} else {
			speechText = "Bienvenido a la skill de innovators. Configure su correo corporativo para empezar.";
		}
		return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("InnovatorsDemo", speechText)
				.withReprompt(speechText).build();
	}

}
