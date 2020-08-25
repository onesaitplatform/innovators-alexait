package com.minsait.innovators.alexa.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.minsait.innovators.alexa.model.Meetings;

public class MeetingsServiceTest {

	@Test
	public void onFetchMeetings_thenAllMeetingsAreFetched() {
		final List<Meetings> meetings = MeetingsService.getInstance().fetchMeetings();
		assertTrue(!meetings.isEmpty());
	}

}
