package com.minsait.innovators.alexa.service;

import static com.minsait.innovators.alexa.commons.CommonsInterface.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.Ticket;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FreshdeskService {
	private static final String API_BASE_ENDPOINT = "https://onesaitplatform.freshdesk.com/helpdesk/tickets/filter/new_and_my_open?format=json&wf_order=created_at";
	private final CommonsInterface commons = new CommonsInterface();
	private final OkHttpClient client = commons.getClient();
	private final static String FRESHDESK_USER = "FRESHDESK_USER";
	private final static String FRESHDESK_SECRET = "FRESHDESK_SECRET";
	private final String user = System.getenv(FRESHDESK_USER);
	private final String secret = System.getenv(FRESHDESK_SECRET);

	private static FreshdeskService freshdeskService;

	public static synchronized FreshdeskService getInstance() {
		if (freshdeskService == null) {
			freshdeskService = new FreshdeskService();
		}
		return freshdeskService;
	}

	public List<Ticket> getNewTickets() {
		try {

			final Request request = new Request.Builder().url(API_BASE_ENDPOINT).header("Authorization",
					"Basic " + Base64.getEncoder().encodeToString((user + ":" + secret).getBytes())).build();
			final Response response = client.newCall(request).execute();
			return mapper.readValue(response.body().string(), new TypeReference<List<Ticket>>() {
			});
		} catch (final IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public static String formatRequester(String requester) {
		try {
			return requester.split(",")[1] + " " + requester.split(",")[0];
		} catch (final Exception e) {
			return requester;
		}
	}
}
