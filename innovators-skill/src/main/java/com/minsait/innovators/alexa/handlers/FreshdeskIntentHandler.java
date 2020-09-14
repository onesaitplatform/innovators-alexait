package com.minsait.innovators.alexa.handlers;

import static com.minsait.innovators.alexa.commons.CommonsInterface.LAST_INTENT_ATT;
import static com.minsait.innovators.alexa.commons.CommonsInterface.LAST_INTENT_TICKETS;
import static com.minsait.innovators.alexa.commons.CommonsInterface.NO_TICKETS;
import static com.minsait.innovators.alexa.commons.CommonsInterface.NO_USER;
import static com.minsait.innovators.alexa.commons.CommonsInterface.USER_ATT;

import java.util.List;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.Ticket;
import com.minsait.innovators.alexa.service.FreshdeskService;
import com.minsait.innovators.alexa.utils.IntentPredicates;

public class FreshdeskIntentHandler implements RequestHandler {
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(
				Predicates.intentName("TicketIntent").or(IntentPredicates.isYesForTickets("AMAZON.YesIntent")));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		if (input.getAttributesManager().getSessionAttributes().get(USER_ATT) == null) {
			return input.getResponseBuilder().withShouldEndSession(true).withSpeech(NO_USER)
					.withSimpleCard(CommonsInterface.CARD_TITLE, NO_USER).build();
		}
		final List<Ticket> tickets = FreshdeskService.getInstance().getNewTickets();
		if (tickets.isEmpty()) {
			return input.getResponseBuilder().withShouldEndSession(false).withSpeech(NO_TICKETS)
					.withSimpleCard(CommonsInterface.CARD_TITLE, NO_TICKETS).build();
		}
		final StringBuilder builder = new StringBuilder();
		if (!((IntentRequest) input.getRequestEnvelope().getRequest()).getIntent().getName()
				.equals("AMAZON.YesIntent")) {
			builder.append("Hay un total de ");
			builder.append(tickets.size());
			builder.append(" tickets nuevos en el buzón de <lang xml:lang=\"en-GB\"> one site platform.</lang>");
			builder.append(" ¿Quieres ver el detalle de los tickets?");
			input.getAttributesManager().getSessionAttributes().put(LAST_INTENT_ATT, LAST_INTENT_TICKETS);
		} else {

			builder.append("Estos son los nuevos tickets: ");
			tickets.forEach(t -> {
				builder.append("<break time=\"1s\"/> ");
				builder.append(" Ticket de ");
				builder.append(FreshdeskService.formatRequester(t.getRequester()));
				builder.append(". ");
				builder.append("<break time=\"1s\"/>");
				builder.append(t.getSubject());
			});
			input.getAttributesManager().getSessionAttributes().remove(LAST_INTENT_ATT);
		}
		return input.getResponseBuilder().withShouldEndSession(false).withSpeech(builder.toString())
				.withSimpleCard(CommonsInterface.CARD_TITLE, builder.toString()).build();
	}

}
