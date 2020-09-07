package com.minsait.innovators.alexa.service;

import static com.minsait.innovators.alexa.commons.CommonsInterface.mapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

	public List<Meetings> fetchMeetings(String user) {

		try {
			final String userEncoded = getEncodedUser(user);
			final Request request = new Request.Builder().url(API_BASE_ENDPOINT + "/user/" + userEncoded).get().build();
			final Response response = client.newCall(request).execute();
			return mapper.readValue(response.body().string(), new TypeReference<List<Meetings>>() {
			});
		} catch (final IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}

	}

	public Meetings fetchNextMeeting(String user) {

		try {
			final String userEncoded = getEncodedUser(user);
			final Request request = new Request.Builder().url(API_BASE_ENDPOINT + "/next/" + userEncoded).get().build();
			final Response response = client.newCall(request).execute();
			return mapper.readValue(response.body().string(), Meetings.class);

		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean cancelMeeting(String id) {

		try {

			final Request request = new Request.Builder().url(API_BASE_ENDPOINT + "/cancel/" + id).get().build();
			final Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				return true;
			}

		} catch (final IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static synchronized MeetingsService getInstance() {
		if (meetingsService == null) {
			meetingsService = new MeetingsService();
		}
		return meetingsService;
	}

	private String getEncodedUser(String user) {
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
