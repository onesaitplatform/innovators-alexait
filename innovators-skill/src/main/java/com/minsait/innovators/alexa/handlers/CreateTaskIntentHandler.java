package com.minsait.innovators.alexa.handlers;

import static com.minsait.innovators.alexa.commons.CommonsInterface.CARD_TITLE;
import static com.minsait.innovators.alexa.commons.CommonsInterface.CREATE_TASK_NEXT_SLOT;
import static com.minsait.innovators.alexa.commons.CommonsInterface.DATE_SLOT;
import static com.minsait.innovators.alexa.commons.CommonsInterface.NO_USER;
import static com.minsait.innovators.alexa.commons.CommonsInterface.TASK_CHANGE_ASSIGNED_SLOT;
import static com.minsait.innovators.alexa.commons.CommonsInterface.TASK_TITLE_SLOT;
import static com.minsait.innovators.alexa.commons.CommonsInterface.USER_ATT;
import static com.minsait.innovators.alexa.commons.CommonsInterface.VAR_IN;
import static com.minsait.innovators.alexa.utils.IntentPredicates.hasTaskAttributes;

import java.util.Arrays;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.TeamTasks;
import com.minsait.innovators.alexa.model.TeamTasks.State;
import com.minsait.innovators.alexa.model.TeamTasksWrapper;
import com.minsait.innovators.alexa.service.DeviceManagementService;
import com.minsait.innovators.alexa.service.TasksService;
import com.minsait.innovators.alexa.utils.DateUtils;

public class CreateTaskIntentHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(hasTaskAttributes().or(Predicates.intentName("CreateTaskIntent")));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		if (input.getAttributesManager().getSessionAttributes().get(USER_ATT) == null) {
			return input.getResponseBuilder().withShouldEndSession(true).withSpeech(NO_USER)
					.withSimpleCard(CommonsInterface.CARD_TITLE, NO_USER).build();
		}
		String output;
		final IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
		if (intentRequest.getIntent().getSlots() == null || intentRequest.getIntent().getSlots().isEmpty()) {
			input.getAttributesManager().getSessionAttributes().put(CREATE_TASK_NEXT_SLOT, TASK_TITLE_SLOT);
			output = "Cuál es el título de la tarea";
			return input.getResponseBuilder().withSpeech(output).withSimpleCard(CARD_TITLE, output).withReprompt(output)
					.withShouldEndSession(false).build();
		} else {
			final String nextSlot = (String) input.getAttributesManager().getSessionAttributes()
					.get(CREATE_TASK_NEXT_SLOT);
			if (nextSlot.equals(TASK_TITLE_SLOT)) {
				input.getAttributesManager().getSessionAttributes().put(CREATE_TASK_NEXT_SLOT, DATE_SLOT);
				input.getAttributesManager().getSessionAttributes().put(TASK_TITLE_SLOT,
						intentRequest.getIntent().getSlots().get(VAR_IN).getValue());
				output = "Para que día está programada";
			} else if (nextSlot.equals(DATE_SLOT)) {
				input.getAttributesManager().getSessionAttributes().put(CREATE_TASK_NEXT_SLOT,
						TASK_CHANGE_ASSIGNED_SLOT);
				input.getAttributesManager().getSessionAttributes().put(DATE_SLOT,
						intentRequest.getIntent().getSlots().get(DATE_SLOT).getValue());
				output = "A quién se la asigno";
			} else if (nextSlot.equals(TASK_CHANGE_ASSIGNED_SLOT)) {
				input.getAttributesManager().getSessionAttributes().remove(CREATE_TASK_NEXT_SLOT);
				final String title = (String) input.getAttributesManager().getSessionAttributes().get(TASK_TITLE_SLOT);
				final String date = (String) input.getAttributesManager().getSessionAttributes().get(DATE_SLOT);
				final String assigned = intentRequest.getIntent().getSlots().get(VAR_IN).getValue();
				final String assignedMatch = DeviceManagementService.getInstance().getMostSimilarUsername(assigned);

				final TeamTasksWrapper task = TeamTasksWrapper.builder().teamTasks(TeamTasks.builder().title(title)
						.assigned(Arrays.asList(assignedMatch)).description(title)
						.creationDate(DateUtils.getNowFormated()).expirationDate(date).state(State.EN_ESPERA)
						.creator(CommonsInterface
								.getCurrentUser(input.getAttributesManager().getSessionAttributes().get(USER_ATT))
								.getFullName())
						.build()).build();

				TasksService.getInstance().createTask(task);
				final StringBuilder builder = new StringBuilder();
				builder.append("La tarea ");
				builder.append(title);
				builder.append(" ha sido creada y asinada a ");
				builder.append(assignedMatch);
				builder.append(". Además ha sido programada para el día " + date);
				output = builder.toString();

			} else {
				output = "Lo siento, no he podido crear la tarea, inténtalo otra vez";
				input.getAttributesManager().getSessionAttributes().remove(CREATE_TASK_NEXT_SLOT);

			}
			return input.getResponseBuilder().withSpeech(output).withSimpleCard(CARD_TITLE, output).withReprompt(output)
					.withShouldEndSession(false).build();

		}

	}

}
