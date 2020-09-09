package com.minsait.innovators.alexa.service;

import static com.minsait.innovators.alexa.commons.CommonsInterface.mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.CompanyNewsWrapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsService {

	private static final String API_BASE_ENDPOINT = "https://lab.onesaitplatform.com/api-manager/server/api/v1/minsait-news";
	private final CommonsInterface commons = new CommonsInterface();
	private final OkHttpClient client = commons.getClient();

	private static final int LIMIT_DEFAULT = 5;

	private static NewsService newsService;

	public static synchronized NewsService getInstance() {
		if (newsService == null) {
			newsService = new NewsService();
		}
		return newsService;
	}

	public List<CompanyNewsWrapper> getLatestNews() {
		try {

			final Request request = new Request.Builder()
					.url(API_BASE_ENDPOINT + "/filter/" + getDateForQuery() + "/" + LIMIT_DEFAULT).get().build();
			final Response response = client.newCall(request).execute();
			return mapper.readValue(response.body().string(), new TypeReference<List<CompanyNewsWrapper>>() {
			});
		} catch (final IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public String getDateForQuery() {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -2);
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(calendar.getTime());
	}

}
