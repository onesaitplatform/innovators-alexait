package com.minsait.innovators.alexa.utils;

import static com.minsait.innovators.alexa.commons.CommonsInterface.CREATE_TASK_NEXT_SLOT;

import java.util.function.Predicate;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;

public class IntentPredicates {

	public static Predicate<HandlerInput> hasTaskAttributes() {
		return i -> i.getRequestEnvelope().getRequest() instanceof IntentRequest
				&& i.getAttributesManager().getSessionAttributes().get(CREATE_TASK_NEXT_SLOT) != null;
	}
}
