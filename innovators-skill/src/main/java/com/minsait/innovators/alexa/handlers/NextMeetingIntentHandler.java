package com.minsait.innovators.alexa.handlers;

import static com.minsait.innovators.alexa.commons.CommonsInterface.NO_MEETINGS;
import static com.minsait.innovators.alexa.commons.CommonsInterface.NO_USER;
import static com.minsait.innovators.alexa.commons.CommonsInterface.USER_ATT;

import java.util.Optional;

import org.joda.time.DateTime;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.AlexaDevice;
import com.minsait.innovators.alexa.model.Meetings;
import com.minsait.innovators.alexa.service.MeetingsService;
import com.minsait.innovators.alexa.utils.DateUtils;;

public class NextMeetingIntentHandler implements RequestHandler {
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(Predicates.intentName("NextMeetingIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		if (input.getAttributesManager().getSessionAttributes().get(USER_ATT) != null) {
			final AlexaDevice user = CommonsInterface
					.getCurrentUser(input.getAttributesManager().getSessionAttributes().get(USER_ATT));

			final Meetings meeting = MeetingsService.getInstance().fetchNextMeeting(user.getFullName());
			if (meeting == null) {
				return input.getResponseBuilder().withSpeech(NO_MEETINGS).withShouldEndSession(false)
						.withSimpleCard(CommonsInterface.CARD_TITLE, NO_MEETINGS).build();
			}
			final StringBuilder builder = new StringBuilder();
			builder.append("Su próxima reunión es: ");
			final DateTime date = new DateTime(meeting.getDate());
			builder.append(DateUtils.parseISODateToSpeech(date));
			builder.append("Será en " + DateUtils.getMeetingLocation(meeting) + ".");

			return input.getResponseBuilder().withSpeech(builder.toString())
					.withSimpleCard(CommonsInterface.CARD_TITLE, builder.toString()).withShouldEndSession(false)
					.build();
		}
		return input.getResponseBuilder().withSpeech(NO_USER).withShouldEndSession(true)
				.withSimpleCard(CommonsInterface.CARD_TITLE, NO_USER).build();

	}

}
