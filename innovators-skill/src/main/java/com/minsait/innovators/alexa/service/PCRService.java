package com.minsait.innovators.alexa.service;

import static com.minsait.innovators.alexa.commons.CommonsInterface.mapper;

import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.PCRUser;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PCRService {
	private static final String API_BASE_ENDPOINT = "https://lab.onesaitplatform.com/api-manager/server/api/v1/pcr/";
	private final CommonsInterface commons = new CommonsInterface();
	private final OkHttpClient client = commons.getClient();

	private static PCRService pCRService;

	public static synchronized PCRService getInstance() {
		if (pCRService == null) {
			pCRService = new PCRService();
		}
		return pCRService;
	}

	public boolean isUserAtRisk(String username) {
		PCRUser user = PCRUser.builder().user(username).build();
		try {
			final RequestBody body = RequestBody.create(MediaType.parse("application/json"),
					mapper.writeValueAsString(user));
			final Request request = new Request.Builder().url(API_BASE_ENDPOINT).post(body).build();
			final Response response = client.newCall(request).execute();
			user = mapper.readValue(response.body().string(), PCRUser.class);
			return user.isPcr();
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
