package com.minsait.innovators.alexa;

import java.util.Arrays;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.minsait.innovators.alexa.handlers.CancelandStopIntentHandler;
import com.minsait.innovators.alexa.handlers.FallbackIntentHandler;
import com.minsait.innovators.alexa.handlers.HelpIntentHandler;
import com.minsait.innovators.alexa.handlers.LaunchRequestHandler;
import com.minsait.innovators.alexa.handlers.MeetingsIntentHandler;
import com.minsait.innovators.alexa.handlers.SessionEndedRequestHandler;
import com.minsait.innovators.alexa.interceptors.CurrentUserInterceptor;

public class InnovatorsStreamHandler extends SkillStreamHandler {

	private static Skill getSkill() {
		return Skills.standard()
				.addRequestHandlers(Arrays.asList(new FallbackIntentHandler(), new CancelandStopIntentHandler(),
						new HelpIntentHandler(), new LaunchRequestHandler(), new SessionEndedRequestHandler(),
						new MeetingsIntentHandler()))
				.addRequestInterceptor(new CurrentUserInterceptor()).build();

	}

	public InnovatorsStreamHandler() {
		super(getSkill());
	}

}
