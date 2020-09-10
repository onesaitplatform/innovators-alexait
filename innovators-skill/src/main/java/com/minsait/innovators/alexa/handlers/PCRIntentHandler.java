package com.minsait.innovators.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;
import static com.minsait.innovators.alexa.commons.CommonsInterface.NO_USER;
import static com.minsait.innovators.alexa.commons.CommonsInterface.PCR_FALSE;
import static com.minsait.innovators.alexa.commons.CommonsInterface.PCR_PRIVACY;
import static com.minsait.innovators.alexa.commons.CommonsInterface.PCR_TRUE;
import static com.minsait.innovators.alexa.commons.CommonsInterface.USER_ATT;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.AlexaDevice;
import com.minsait.innovators.alexa.service.PCRService;

public class PCRIntentHandler implements RequestHandler {
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AmIAtRiskIntent").or(intentName("WhoIsAtRisk")));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		if (input.getAttributesManager().getSessionAttributes().get(USER_ATT) != null) {
			if (((IntentRequest) input.getRequestEnvelope().getRequest()).getIntent().getName().equals("WhoIsAtRisk")) {
				return input.getResponseBuilder().withSpeech(PCR_PRIVACY).withShouldEndSession(false)
						.withSimpleCard(CommonsInterface.CARD_TITLE, PCR_PRIVACY).build();
			}
			final AlexaDevice user = CommonsInterface
					.getCurrentUser(input.getAttributesManager().getSessionAttributes().get(USER_ATT));
			final boolean isAtRisk = PCRService.getInstance().isUserAtRisk(user.getFullName());
			return input.getResponseBuilder().withSpeech(isAtRisk ? PCR_TRUE : PCR_FALSE).withShouldEndSession(false)
					.withSimpleCard(CommonsInterface.CARD_TITLE, isAtRisk ? PCR_TRUE : PCR_FALSE).build();

		}
		return input.getResponseBuilder().withSpeech(NO_USER).withShouldEndSession(true)
				.withSimpleCard(CommonsInterface.CARD_TITLE, NO_USER).build();
	}

}
