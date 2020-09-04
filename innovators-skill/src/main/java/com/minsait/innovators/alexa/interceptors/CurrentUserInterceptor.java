package com.minsait.innovators.alexa.interceptors;

import static com.minsait.innovators.alexa.commons.CommonsInterface.USER_ATT;
import static com.minsait.innovators.alexa.commons.CommonsInterface.mapper;

import java.io.IOException;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.AlexaDevice;
import com.minsait.innovators.alexa.model.UserProfile;
import com.minsait.innovators.alexa.service.DeviceManagementService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CurrentUserInterceptor implements RequestInterceptor {

	private final CommonsInterface commons = new CommonsInterface();
	private final OkHttpClient client = commons.getClient();
	private static final String PROFILE_ENDPOINT = "https://api.amazon.com/user/profile";

	@Override
	public void process(HandlerInput input) {
		if (input.getAttributesManager().getSessionAttributes().get(USER_ATT) == null) {
			final String deviceId = input.getRequestEnvelope().getContext().getSystem().getDevice().getDeviceId();

			final AlexaDevice device = DeviceManagementService.getInstance().getDevice(deviceId);
			if (device != null) {
				input.getAttributesManager().getSessionAttributes().put(USER_ATT, device);
			} else {
				final String accessToken = input.getRequestEnvelope().getContext().getSystem().getUser()
						.getAccessToken();
				if (accessToken != null) {
					final UserProfile profile = getProfile(accessToken);
					if (profile != null) {
						final AlexaDevice d = DeviceManagementService.getInstance().register(deviceId,
								profile.getUserId(), profile.getName(), profile.getEmail());
						if (d != null) {
							input.getAttributesManager().getSessionAttributes().put(USER_ATT, d);
						}
					}
				}
			}
		}

	}

	private UserProfile getProfile(String accessToken) {
		final Request request = new Request.Builder().url(PROFILE_ENDPOINT).get()
				.header("Authorization", "bearer " + accessToken).build();
		Response response;
		try {
			response = client.newCall(request).execute();
			return mapper.readValue(response.body().string(), UserProfile.class);
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}
