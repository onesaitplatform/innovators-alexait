package com.minsait.innovators.alexa.interceptors;

import static com.minsait.innovators.alexa.commons.CommonsInterface.USER_ATT;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import com.minsait.innovators.alexa.model.AlexaDevice;
import com.minsait.innovators.alexa.service.DeviceManagementService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrentUserInterceptor implements RequestInterceptor {

	@Override
	public void process(HandlerInput input) {
		if (input.getAttributesManager().getSessionAttributes().get(USER_ATT) == null) {
			String deviceId = input.getRequestEnvelope().getContext().getSystem().getDevice().getDeviceId();
			// TO-DO remove mock deviceid
			if (deviceId == null) {
				log.warn("No device id provided, fallback to demo");
				deviceId = "DEVICE_DEMO";
			}
			final AlexaDevice device = DeviceManagementService.getInstance().getDevice(deviceId);
			if (device != null) {
				input.getAttributesManager().getSessionAttributes().put(USER_ATT, device);
			}
		}

	}

}
