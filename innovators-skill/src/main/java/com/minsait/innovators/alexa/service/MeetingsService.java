package com.minsait.innovators.alexa.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.innovators.alexa.model.Meetings;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MeetingsService {

	private static final String API_BASE_ENDPOINT = "https://lab.onesaitplatform.com/api-manager/server/api/v1/meetings";
	private static final String API_KEY = "71511dc82a5c4e10bbd6a1988f7f1dc6";
	private static final String X_OP_API_KEY = "X-OP-APIKey";
	private final OkHttpClient client = new OkHttpClient();
	private final ObjectMapper mapper = new ObjectMapper();

	static MeetingsService meetingsService;

	public List<Meetings> fetchMeetings() {
		final Request request = new Request.Builder().url(API_BASE_ENDPOINT).get().header(X_OP_API_KEY, API_KEY)
				.build();
		try {
			final Response response = client.newCall(request).execute();
			return mapper.readValue(response.body().string(), new TypeReference<List<Meetings>>() {
			});

		} catch (final IOException e) {
			return new ArrayList<>();
		}

	}

	public static synchronized MeetingsService getInstance() {
		if (meetingsService == null) {
			meetingsService = new MeetingsService();
		}
		return meetingsService;
	}
}
