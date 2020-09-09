package com.minsait.innovators.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;
import static com.minsait.innovators.alexa.commons.CommonsInterface.DATE_SLOT;
import static com.minsait.innovators.alexa.commons.CommonsInterface.NO_TASK_FOUND;
import static com.minsait.innovators.alexa.commons.CommonsInterface.NO_TASK_STATE_CHANGE_SLOTS;
import static com.minsait.innovators.alexa.commons.CommonsInterface.NO_USER;
import static com.minsait.innovators.alexa.commons.CommonsInterface.TASK_CHANGE_STATE_SLOT;
import static com.minsait.innovators.alexa.commons.CommonsInterface.TASK_TITLE_SLOT;
import static com.minsait.innovators.alexa.commons.CommonsInterface.USER_ATT;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.TeamTasksWrapper;
import com.minsait.innovators.alexa.service.TasksService;

public class ChangeTaskStateIntentHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("ChangeTaskStateIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		if (input.getAttributesManager().getSessionAttributes().get(USER_ATT) == null) {
			return input.getResponseBuilder().withShouldEndSession(true).withSpeech(NO_USER)
					.withSimpleCard(CommonsInterface.CARD_TITLE, NO_USER).build();
		}
		final IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
		final Map<String, Slot> slots = intentRequest.getIntent().getSlots();
		if (slots.isEmpty() || slots.get(TASK_CHANGE_STATE_SLOT) == null
				|| slots.get(TASK_TITLE_SLOT) == null && slots.get(DATE_SLOT) == null) {
			return input.getResponseBuilder().withShouldEndSession(false).withSpeech(NO_TASK_STATE_CHANGE_SLOTS)
					.withReprompt(NO_TASK_STATE_CHANGE_SLOTS)
					.withSimpleCard(CommonsInterface.CARD_TITLE, NO_TASK_STATE_CHANGE_SLOTS).build();
		}
		final TeamTasksWrapper task = TasksService.getInstance().fetchTask(slots.get(DATE_SLOT),
				slots.get(TASK_TITLE_SLOT),
				CommonsInterface.getCurrentUser(input.getAttributesManager().getSessionAttributes().get(USER_ATT))
						.getFullName());
		if (task != null) {
			TasksService.getInstance().changeState(task.getId(), slots.get(TASK_CHANGE_STATE_SLOT).getValue());
			final StringBuilder builder = new StringBuilder();
			builder.append("Se ha actualizado la tarea ");
			builder.append(task.getTeamTasks().getTitle());
			builder.append(". Su estado actual es " + slots.get(TASK_CHANGE_STATE_SLOT).getValue());
			return input.getResponseBuilder().withShouldEndSession(false).withSpeech(builder.toString())
					.withSimpleCard(CommonsInterface.CARD_TITLE, builder.toString()).build();

		}
		return input.getResponseBuilder().withShouldEndSession(false).withSpeech(NO_TASK_FOUND)
				.withSimpleCard(CommonsInterface.CARD_TITLE, NO_TASK_FOUND).build();
	}

}
