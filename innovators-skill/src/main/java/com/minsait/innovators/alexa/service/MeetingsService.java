package com.minsait.innovators.alexa.service;

import static com.minsait.innovators.alexa.commons.CommonsInterface.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.Meetings;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MeetingsService {

	private static final String API_BASE_ENDPOINT = "https://lab.onesaitplatform.com/api-manager/server/api/v1/meetings";
	private final CommonsInterface commons = new CommonsInterface();
	private final OkHttpClient client = commons.getClient();

	static MeetingsService meetingsService;

	public List<Meetings> fetchMeetings() {
		final Request request = new Request.Builder().url(API_BASE_ENDPOINT).get().build();
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
