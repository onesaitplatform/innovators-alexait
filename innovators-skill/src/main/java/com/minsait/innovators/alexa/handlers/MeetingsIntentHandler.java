package com.minsait.innovators.alexa.handlers;

import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
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
		final List<Meetings> meetings = MeetingsService.getInstance().fetchMeetings();
		final StringBuilder builder = new StringBuilder();
		builder.append("Sus próximas reuniones son: ");
		meetings.forEach(m -> {
			final DateTime date = new DateTime(m.getDate());
			builder.append(DateUtils.parseISODateToSpeech(date));
			builder.append("Será en " + m.getBuilding() + ".");
		});
		return input.getResponseBuilder().withSpeech(builder.toString())
				.withSimpleCard("InnovatorsDemo", builder.toString()).build();
	}

}
