package com.minsait.innovators.alexa.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.minsait.innovators.alexa.model.AlexaDevice;
import com.minsait.innovators.alexa.model.CompanyNewsWrapper;
import com.minsait.innovators.alexa.model.IndraUsers;
import com.minsait.innovators.alexa.model.Meetings;
import com.minsait.innovators.alexa.model.TeamTasksWrapper;

public class MeetingsServiceTest {

	private static final String DEVICE_ID = "DEVICE";

	@Test
	public void onFetchMeetings_thenAllMeetingsAreFetched() {
		final List<Meetings> meetings = MeetingsService.getInstance()
				.fetchMeetings("Francisco Javier Gómez-Cornejo Gil");
		assertTrue(!meetings.isEmpty());
	}

	@Test
	public void onRegisterNewDeviceWhenNotExists_thenDeviceIsCreated() {
		AlexaDevice device = DeviceManagementService.getInstance().getDevice(DEVICE_ID);
		if (device == null) {
			DeviceManagementService.getInstance().register(DEVICE_ID, "fjgcornejo", "Javier Gómez-Cornejo Gil",
					"fjgcornejo@minsait.com");
		}
		device = DeviceManagementService.getInstance().getDevice(DEVICE_ID);
		assertNotNull(device);
	}

	@Test
	public void onFetchIndraUser_thenIndraUserIsRetrieved() {
		final IndraUsers user = IndraUsersService.getInstance().getUser("fjgcornejo@minsait.com");
		assertNotNull(user);
	}

	@Test
	public void onProvidedUserFullName_thenNextMeetingIsRetrieved() {
		final String name = "Francisco Javier Gómez-Cornejo Gil";
		final Meetings meeting = MeetingsService.getInstance().fetchNextMeeting(name);
		assertNotNull(meeting);
	}

	@Test
	public void fetchNews() throws JsonProcessingException {
		final List<CompanyNewsWrapper> news = NewsService.getInstance().getLatestNews();
		assertTrue(!news.isEmpty());
	}

	@Test
	public void onProvidedDateAndTitle_thenTaskIsFetched() {
		final String date = "2020-09-30";
		final String title = "entorno de producción";
		final String assigned = "Francisco Javier Gómez-Cornejo Gil";

		TeamTasksWrapper task = null;
		task = TasksService.getInstance().fetchTaskByDate(date, assigned);
		assertNotNull(task);
		task = null;
		task = TasksService.getInstance().fetchTaskByTitle(title, assigned);
		assertNotNull(task);
		task = null;
		task = TasksService.getInstance().fetchTaskByDateOrTitle(date, title, assigned);
		assertNotNull(task);
		TasksService.getInstance().changeState(task.getId(), "en curso");

	}
}
