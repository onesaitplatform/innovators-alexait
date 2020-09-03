package com.minsait.innovators.alexa.commons;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class CommonsInterface {

	public static final String API_KEY = "71511dc82a5c4e10bbd6a1988f7f1dc6";
	public static final String X_OP_API_KEY = "X-OP-APIKey";
	public static final String USER_ATT = "USER_ID";
	public static final String EMAIL_SLOT = "userEmail";

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

}
