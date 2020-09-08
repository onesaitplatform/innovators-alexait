package com.minsait.innovators.alexa.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.innovators.alexa.model.AlexaDevice;

import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class CommonsInterface {

	public static final String API_KEY = "71511dc82a5c4e10bbd6a1988f7f1dc6";
	public static final String X_OP_API_KEY = "X-OP-APIKey";
	public static final String USER_ATT = "USER_ID";
	public static final String EMAIL_SLOT = "userEmail";
	public static final String DATE_SLOT = "dateIn";
	public static final String NO_MEETINGS = "No tienes ninguna reunión.";
	public static final String NO_USER = "Perdona, no se quien eres, concede permisos a la aplicación para poder seguir utilizándola.";
	public static final String CARD_TITLE = "Innovators";
	public static final String PCR_PRIVACY = "Por motivos de privacidad, no me es posible revelar quién ha dado positivo a la prueba de la PCR";
	public static final String PCR_TRUE = "Alguno de los miembros de una de tus reuniones ha dado positivo a la PCR recientemente, te recomiendo que te hagas una PCR para descartar un posible contagio";
	public static final String PCR_FALSE = "No tienes de qué preocuparte, ningún miembro de tus pasadas reuniones ha dado positivo a la PCR";
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

}
