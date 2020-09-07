package com.minsait.innovators.alexa.handlers;

import static com.minsait.innovators.alexa.commons.CommonsInterface.NO_MEETINGS;
import static com.minsait.innovators.alexa.commons.CommonsInterface.NO_USER;
import static com.minsait.innovators.alexa.commons.CommonsInterface.USER_ATT;

import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.Meetings;
import com.minsait.innovators.alexa.service.MeetingsService;
import com.minsait.innovators.alexa.utils.DateUtils;

public class MeetingsIntentHandler implements RequestHandler {
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(Predicates.intentName("MeetingsIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		if (input.getAttributesManager().getSessionAttributes().get(USER_ATT) == null) {
			return input.getResponseBuilder().withShouldEndSession(true).withSpeech(NO_USER)
					.withSimpleCard(CommonsInterface.CARD_TITLE, NO_USER).build();
		}
		final List<Meetings> meetings = MeetingsService.getInstance().fetchMeetings(CommonsInterface
				.getCurrentUser(input.getAttributesManager().getSessionAttributes().get(USER_ATT)).getFullName());
		if (meetings.isEmpty()) {
			return input.getResponseBuilder().withShouldEndSession(false).withSpeech(NO_MEETINGS)
					.withSimpleCard(CommonsInterface.CARD_TITLE, NO_MEETINGS).build();
		}
		final StringBuilder builder = new StringBuilder();
		builder.append("Sus próximas reuniones son: ");
		meetings.forEach(m -> {
			final DateTime date = new DateTime(m.getDate());
			builder.append(DateUtils.parseISODateToSpeech(date));
			builder.append("Será en " + DateUtils.getMeetingLocation(m) + ".");
		});
		return input.getResponseBuilder().withSpeech(builder.toString())
				.withSimpleCard(CommonsInterface.CARD_TITLE, builder.toString()).withShouldEndSession(false).build();
	}

}
