package com.minsait.innovators.alexa.commons;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.innovators.alexa.model.AlexaDevice;
import com.minsait.innovators.alexa.model.CompanyNewsWrapper;

import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class CommonsInterface {

	public static final String API_KEY = "71511dc82a5c4e10bbd6a1988f7f1dc6";
	public static final String X_OP_API_KEY = "X-OP-APIKey";
	public static final String USER_ATT = "USER_ID";
	public static final String NEWS_ATT = "NEWS";
	public static final String EMAIL_SLOT = "userEmail";
	public static final String DATE_SLOT = "dateIn";
	public static final String VAR_IN = "varIn";
	public static final String CREATE_TASK_NEXT_SLOT = "nextTaskSlot";
	public static final String TASK_TITLE_SLOT = "taskTitle";
	public static final String TASK_CHANGE_STATE_SLOT = "taskState";
	public static final String TASK_CHANGE_ASSIGNED_SLOT = "taskAssigned";
	public static final String NO_MEETINGS = "No tienes ninguna reunión.";
	public static final String NO_TASK_FOUND = "No he encontrado la tarea que me has indicado. Prueba otra vez.";
	public static final String NO_TASK_STATE_CHANGE_SLOTS = "Para cambiar una tarea de estado, indica qué tarea es mediante su título o fecha programada, y el estado al que deseas cambiarla.";
	public static final String NO_USER = "Perdona, no se quien eres, concede permisos a la aplicación para poder seguir utilizándola.";
	public static final String NO_TASKS = "No tienes ninguna tarea asignada.";
	public static final String CARD_TITLE = "Innovators";
	public static final String PCR_PRIVACY = "Por motivos de privacidad, no me es posible revelar quién ha dado positivo a la prueba de la PCR";
	public static final String PCR_TRUE = "Alguno de los miembros de una de tus reuniones ha dado positivo a la PCR recientemente, te recomiendo que te hagas una PCR para descartar un posible contagio";
	public static final String PCR_FALSE = "No tienes de qué preocuparte, ningún miembro de tus pasadas reuniones ha dado positivo a la PCR";
	public static final String NO_NEWS = "No hay nuevas noticias.";
	public static final ObjectMapper mapper = new ObjectMapper();

	@Getter
	public OkHttpClient client = clientHttp();

	private OkHttpClient clientHttp() {
		final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		httpClient.addInterceptor(chain -> {
			final Request request = chain.request();
			Request newRequest;

			newRequest = request.newBuilder().addHeader(X_OP_API_KEY, API_KEY).addHeader("Accept", "application/json")
					.build();
			return chain.proceed(newRequest);
		});
		return httpClient.build();
	}

	public static AlexaDevice getCurrentUser(Object attr) {
		if (attr instanceof AlexaDevice) {
			return (AlexaDevice) attr;
		} else {
			return mapper.convertValue(attr, AlexaDevice.class);
		}
	}

	public static Optional<List<CompanyNewsWrapper>> getCachedNews(HandlerInput input) {
		if (input.getAttributesManager().getSessionAttributes().get(NEWS_ATT) != null) {
			try {
				return Optional
						.of(mapper.convertValue(input.getAttributesManager().getSessionAttributes().get(NEWS_ATT),
								new TypeReference<List<CompanyNewsWrapper>>() {
								}));
			} catch (final Exception e) {
				return Optional.empty();
			}
		} else {
			return Optional.empty();
		}
	}

	public static String escapeSSMLCharacters(String input) {
		return input.replace(" & ", " y ").replace("&", " y ");
	}

	public static String getEncodedUser(String user) {
		String userEncoded;
		try {
			userEncoded = URLEncoder.encode(user, StandardCharsets.UTF_8.name());
			userEncoded = userEncoded.replace("+", "%20");
			return userEncoded;
		} catch (final UnsupportedEncodingException e) {
			return user;
		}

	}

}
