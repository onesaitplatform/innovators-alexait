package com.minsait.innovators.alexa.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.minsait.innovators.alexa.model.AlexaDevice;
import com.minsait.innovators.alexa.model.IndraUsers;
import com.minsait.innovators.alexa.model.Meetings;

public class MeetingsServiceTest {

	private static final String DEVICE_ID = "DEVICE";

	@Test
	public void onFetchMeetings_thenAllMeetingsAreFetched() {
		final List<Meetings> meetings = MeetingsService.getInstance().fetchMeetings();
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

}
