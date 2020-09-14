package com.minsait.innovators.alexa.utils;

import static com.minsait.innovators.alexa.commons.CommonsInterface.CREATE_TASK_NEXT_SLOT;
import static com.minsait.innovators.alexa.commons.CommonsInterface.LAST_INTENT_ATT;
import static com.minsait.innovators.alexa.commons.CommonsInterface.LAST_INTENT_NEWS;
import static com.minsait.innovators.alexa.commons.CommonsInterface.LAST_INTENT_TICKETS;

import java.util.function.Predicate;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;

public class IntentPredicates {

	public static Predicate<HandlerInput> hasTaskAttributes() {
		return i -> i.getRequestEnvelope().getRequest() instanceof IntentRequest
				&& i.getAttributesManager().getSessionAttributes().get(CREATE_TASK_NEXT_SLOT) != null;
	}

	public static Predicate<HandlerInput> isYesForNews(String intentName) {
		return i -> i.getRequestEnvelope().getRequest() instanceof IntentRequest
				&& intentName.equals(((IntentRequest) i.getRequestEnvelope().getRequest()).getIntent().getName())
				&& i.getAttributesManager().getSessionAttributes().get(LAST_INTENT_ATT) != null
				&& LAST_INTENT_NEWS.equals(i.getAttributesManager().getSessionAttributes().get(LAST_INTENT_ATT));
	}

	public static Predicate<HandlerInput> isYesForTickets(String intentName) {
		return i -> i.getRequestEnvelope().getRequest() instanceof IntentRequest
				&& intentName.equals(((IntentRequest) i.getRequestEnvelope().getRequest()).getIntent().getName())
				&& i.getAttributesManager().getSessionAttributes().get(LAST_INTENT_ATT) != null
				&& LAST_INTENT_TICKETS.equals(i.getAttributesManager().getSessionAttributes().get(LAST_INTENT_ATT));
	}
}
