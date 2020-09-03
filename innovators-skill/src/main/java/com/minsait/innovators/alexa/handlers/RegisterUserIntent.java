package com.minsait.innovators.alexa.handlers;

import static com.minsait.innovators.alexa.commons.CommonsInterface.EMAIL_SLOT;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;
import com.minsait.innovators.alexa.model.IndraUsers;
import com.minsait.innovators.alexa.service.DeviceManagementService;
import com.minsait.innovators.alexa.service.IndraUsersService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegisterUserIntent implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(Predicates.intentName("RegisterUserIntent"));

	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		String speechText = "No he entendido tu correo, prueba otra vez";
		final IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
		final Map<String, Slot> slots = intentRequest.getIntent().getSlots();
		if (!slots.isEmpty()) {
			final Slot email = slots.get(EMAIL_SLOT);
			if (email != null) {
				log.info("El correo es {}", email.getValue());
				final IndraUsers user = IndraUsersService.getInstance().getUser(email.getValue());
				if (user == null) {
					speechText = "Lo siento, ese email no se encuentra registrado dentro de la organización, prueba con otro correo";
				} else {
					// TO-DO remove mock deviceid
					String deviceId = input.getRequestEnvelope().getContext().getSystem().getDevice().getDeviceId();
					if (deviceId == null) {
						deviceId = "DEVICE_DEMO";
					}
					DeviceManagementService.getInstance().register(deviceId, String.valueOf(user.getCodEmpleado()),
							user.getEmpleado(), email.getValue());
					speechText = user.getEmpleado() + ", su correo se ha registrado satisfactoriamente";
				}

			}
		}
		return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("InnovatorsDemo", speechText)
				.withReprompt(speechText).build();
	}

}
