package com.minsait.innovators.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;
import static com.minsait.innovators.alexa.commons.CommonsInterface.DATE_SLOT;
import static com.minsait.innovators.alexa.commons.CommonsInterface.NO_USER;
import static com.minsait.innovators.alexa.commons.CommonsInterface.USER_ATT;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.Meetings;
import com.minsait.innovators.alexa.service.MeetingsService;

public class CancelMeetingIntentHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("CancelMeetingIntent").or(intentName("CancelMeetingDateIntent")));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		if (input.getAttributesManager().getSessionAttributes().get(USER_ATT) == null) {
			return input.getResponseBuilder().withShouldEndSession(true).withSpeech(NO_USER)
					.withSimpleCard(CommonsInterface.CARD_TITLE, NO_USER).build();
		}
		String speech = "";
		final IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
		final Map<String, Slot> slots = intentRequest.getIntent().getSlots();
		if (slots != null && !slots.isEmpty()) {
			final Slot date = slots.get(DATE_SLOT);
			final String d = date.getValue();
			final String currentUser = CommonsInterface
					.getCurrentUser(input.getAttributesManager().getSessionAttributes().get(USER_ATT)).getFullName();
			final List<Meetings> meetings = MeetingsService.getInstance().fetchMeetings(currentUser);
			final Meetings match = meetings.stream().filter(m -> m.getDate().split("T")[0].equals(d)).findFirst()
					.orElse(null);
			if (match != null) {
				final boolean canceled = MeetingsService.getInstance().cancelMeeting(match.getId());
				if (canceled) {
					speech = "Su reunión del " + d + " ha sido cancelada.";
					return input.getResponseBuilder().withSpeech(speech).withShouldEndSession(true)
							.withSimpleCard(CommonsInterface.CARD_TITLE, speech).build();
				}
			} else {
				speech = "No tiene ninguna reunión el " + d;
			}
		} else {
			speech = "¿Qué reunión quieres cancelar?";
		}
		return input.getResponseBuilder().withSpeech(speech).withShouldEndSession(false)
				.withSimpleCard(CommonsInterface.CARD_TITLE, speech).build();
	}

}
