package com.minsait.innovators.alexa.service;

import static com.minsait.innovators.alexa.commons.CommonsInterface.mapper;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.AlexaDevice;

import lombok.extern.slf4j.Slf4j;
import net.ricecode.similarity.DiceCoefficientStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public class DeviceManagementService {

	private static final String API_BASE_ENDPOINT = "https://lab.onesaitplatform.com/api-manager/server/api/v1/alexa-device";
	private final CommonsInterface commons = new CommonsInterface();
	private final OkHttpClient client = commons.getClient();
	private static DeviceManagementService deviceManagementService;

	public static synchronized DeviceManagementService getInstance() {
		if (deviceManagementService == null) {
			deviceManagementService = new DeviceManagementService();
		}
		return deviceManagementService;
	}

	public AlexaDevice getDevice(String deviceId) {

		try {
			final Request request = new Request.Builder()
					.url(API_BASE_ENDPOINT + "/device/" + URLEncoder.encode(deviceId, StandardCharsets.UTF_8.name()))
					.get().build();
			final Response response = client.newCall(request).execute();
			final List<AlexaDevice> devices = mapper.readValue(response.body().string(),
					new TypeReference<List<AlexaDevice>>() {
					});
			if (devices.isEmpty()) {
				return null;
			} else {
				return devices.get(0);
			}

		} catch (final IOException e) {
			return null;
		}
	}

	public List<AlexaDevice> getDevices() {

		try {
			final Request request = new Request.Builder().url(API_BASE_ENDPOINT).get().build();
			final Response response = client.newCall(request).execute();
			return mapper.readValue(response.body().string(), new TypeReference<List<AlexaDevice>>() {
			});

		} catch (final IOException e) {
			return new ArrayList<>();
		}
	}

	public String getMostSimilarUsername(String input) {
		final List<AlexaDevice> users = getDevices();
		return users.stream().reduce((a, b) -> {
			final double scorea = scoreUsername(input, a.getFullName());
			final double scoreb = scoreUsername(input, b.getFullName());
			if (scorea > scoreb) {
				return a;
			} else {
				return b;
			}
		}).map(AlexaDevice::getFullName).orElse(input);
	}

	public double scoreUsername(String source, String target) {
		final SimilarityStrategy strategy = new DiceCoefficientStrategy();
		final StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
		return service.score(source, target);
	}

	public AlexaDevice register(String deviceId, String username, String fullName, String email) {
		final AlexaDevice d = getDevice(deviceId);
		if (d == null) {
			final AlexaDevice device = AlexaDevice.builder().deviceId(deviceId).userId(username).email(email)
					.fullName(fullName).build();
			try {
				final RequestBody body = RequestBody.create(MediaType.parse("application/json"),
						mapper.writeValueAsString(device));
				final Request request = new Request.Builder().url(API_BASE_ENDPOINT).post(body).build();
				client.newCall(request).execute();
				return device;
			} catch (final IOException e) {
				log.error("Could not register device");
				return null;
			}
		} else {
			return d;
		}

	}

}
