package com.minsait.innovators.alexa.service;

import static com.minsait.innovators.alexa.commons.CommonsInterface.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazon.ask.model.Slot;
import com.fasterxml.jackson.core.type.TypeReference;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.TeamTasks;
import com.minsait.innovators.alexa.model.TeamTasksWrapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TasksService {
	private static final String API_BASE_ENDPOINT = "https://lab.onesaitplatform.com/api-manager/server/api/v1/team-tasks";
	private final CommonsInterface commons = new CommonsInterface();
	private final OkHttpClient client = commons.getClient();

	private static TasksService tasksService;

	public static synchronized TasksService getInstance() {
		if (tasksService == null) {
			tasksService = new TasksService();
		}
		return tasksService;
	}

	public List<TeamTasksWrapper> fetchAssginedTasks(String user) {
		try {
			final String userEncoded = CommonsInterface.getEncodedUser(user);
			final Request request = new Request.Builder().url(API_BASE_ENDPOINT + "/assigned/" + userEncoded).get()
					.build();
			final Response response = client.newCall(request).execute();
			return mapper.readValue(response.body().string(), new TypeReference<List<TeamTasksWrapper>>() {
			});
		} catch (final IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public TeamTasksWrapper fetchTaskByDate(String date, String assigned) {
		try {
			final String userEncoded = CommonsInterface.getEncodedUser(assigned);
			final Request request = new Request.Builder().url(
					API_BASE_ENDPOINT + "/date-assigned/" + CommonsInterface.getEncodedUser(date) + "/" + userEncoded)
					.get().build();
			final Response response = client.newCall(request).execute();
			return mapper.readValue(response.body().string(), TeamTasksWrapper.class);
		} catch (final IOException e) {
			return null;
		}
	}

	public TeamTasksWrapper fetchTaskByDateOrTitle(String date, String title, String assigned) {
		try {
			final String userEncoded = CommonsInterface.getEncodedUser(assigned);
			final Request request = new Request.Builder()
					.url(API_BASE_ENDPOINT + "/date-assigned-title/" + CommonsInterface.getEncodedUser(date) + "/"
							+ CommonsInterface.getEncodedUser(title) + "/" + userEncoded)
					.get().build();
			final Response response = client.newCall(request).execute();
			return mapper.readValue(response.body().string(), TeamTasksWrapper.class);
		} catch (final IOException e) {
			return null;
		}
	}

	public TeamTasksWrapper fetchTaskByTitle(String title, String assigned) {
		try {
			final String userEncoded = CommonsInterface.getEncodedUser(assigned);
			final Request request = new Request.Builder().url(
					API_BASE_ENDPOINT + "/title-assigned/" + CommonsInterface.getEncodedUser(title) + "/" + userEncoded)
					.get().build();
			final Response response = client.newCall(request).execute();
			return mapper.readValue(response.body().string(), TeamTasksWrapper.class);
		} catch (final IOException e) {
			return null;
		}
	}

	public void changeState(String id, String state) {
		try {

			final String stateName = TeamTasks.State.fromString(state).name();
			final Request request = new Request.Builder()
					.url(API_BASE_ENDPOINT + "/state-change/" + stateName + "/" + id).get().build();
			client.newCall(request).execute();

		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public TeamTasksWrapper fetchTask(Slot date, Slot title, String assigned) {

		if (date.getValue() != null && title.getValue() == null) {
			return fetchTaskByDate(date.getValue(), assigned);
		} else if (title.getValue() != null && date.getValue() == null) {
			return fetchTaskByTitle(title.getValue(), assigned);
		} else if (title.getValue() != null && date.getValue() != null) {
			return fetchTaskByDateOrTitle(date.getValue(), title.getValue(), assigned);
		} else {
			return null;
		}
	}
}
