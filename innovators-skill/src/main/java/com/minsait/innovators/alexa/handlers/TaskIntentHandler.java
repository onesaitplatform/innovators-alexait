package com.minsait.innovators.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;
import static com.minsait.innovators.alexa.commons.CommonsInterface.NO_TASKS;
import static com.minsait.innovators.alexa.commons.CommonsInterface.NO_USER;
import static com.minsait.innovators.alexa.commons.CommonsInterface.USER_ATT;

import java.util.List;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.TeamTasksWrapper;
import com.minsait.innovators.alexa.service.TasksService;

public class TaskIntentHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AssignedTasksIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		if (input.getAttributesManager().getSessionAttributes().get(USER_ATT) == null) {
			return input.getResponseBuilder().withShouldEndSession(true).withSpeech(NO_USER)
					.withSimpleCard(CommonsInterface.CARD_TITLE, NO_USER).build();
		}
		final List<TeamTasksWrapper> tasks = TasksService.getInstance().fetchAssginedTasks(CommonsInterface
				.getCurrentUser(input.getAttributesManager().getSessionAttributes().get(USER_ATT)).getFullName());
		if (tasks.isEmpty()) {
			return input.getResponseBuilder().withShouldEndSession(false).withSpeech(NO_TASKS)
					.withSimpleCard(CommonsInterface.CARD_TITLE, NO_TASKS).build();
		}
		final StringBuilder builder = new StringBuilder();
		builder.append("Tus tareas asignadas son las siguientes: <break time=\"2s\"/> ");
		tasks.forEach(t -> {
			builder.append(t.getTeamTasks().getTitle());
			builder.append(". Programada para el " + t.getTeamTasks().getExpirationDate());
			builder.append(". Estado actual: " + t.getTeamTasks().getState().getSpeechState() + ".");
			builder.append(" <break time=\"2s\"/> ");
		});
		return input.getResponseBuilder().withShouldEndSession(false).withSpeech(builder.toString())
				.withSimpleCard(CommonsInterface.CARD_TITLE, builder.toString()).build();
	}

}
