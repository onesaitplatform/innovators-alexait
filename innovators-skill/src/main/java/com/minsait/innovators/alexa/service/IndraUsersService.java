package com.minsait.innovators.alexa.service;

import static com.minsait.innovators.alexa.commons.CommonsInterface.mapper;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.IndraUsers;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IndraUsersService {
	private static final String API_BASE_ENDPOINT = "https://lab.onesaitplatform.com/api-manager/server/api/v1/indra-users";
	private final CommonsInterface commons = new CommonsInterface();
	private final OkHttpClient client = commons.getClient();

	private static IndraUsersService indraUsersService;

	public static synchronized IndraUsersService getInstance() {
		if (indraUsersService == null) {
			indraUsersService = new IndraUsersService();
		}
		return indraUsersService;
	}

	public IndraUsers getUser(String mail) {
		try {
			final Request request = new Request.Builder()
					.url(API_BASE_ENDPOINT + "/mail/" + URLEncoder.encode(mail, StandardCharsets.UTF_8.name())).get()
					.build();
			final Response response = client.newCall(request).execute();
			final List<IndraUsers> users = mapper.readValue(response.body().string(),
					new TypeReference<List<IndraUsers>>() {
					});
			if (users.isEmpty()) {
				return null;
			} else {
				return users.get(0);
			}

		} catch (final IOException e) {
			return null;
		}
	}

}
