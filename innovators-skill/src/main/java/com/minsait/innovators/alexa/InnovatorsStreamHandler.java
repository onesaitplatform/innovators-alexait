package com.minsait.innovators.alexa;

import java.util.Arrays;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.minsait.innovators.alexa.handlers.CancelMeetingIntentHandler;
import com.minsait.innovators.alexa.handlers.CancelandStopIntentHandler;
import com.minsait.innovators.alexa.handlers.ChangeTaskStateIntentHandler;
import com.minsait.innovators.alexa.handlers.CreateTaskIntentHandler;
import com.minsait.innovators.alexa.handlers.FallbackIntentHandler;
import com.minsait.innovators.alexa.handlers.FreshdeskIntentHandler;
import com.minsait.innovators.alexa.handlers.HelpIntentHandler;
import com.minsait.innovators.alexa.handlers.LaunchRequestHandler;
import com.minsait.innovators.alexa.handlers.MeetingsIntentHandler;
import com.minsait.innovators.alexa.handlers.NewsIntentHandler;
import com.minsait.innovators.alexa.handlers.NextMeetingIntentHandler;
import com.minsait.innovators.alexa.handlers.PCRIntentHandler;
import com.minsait.innovators.alexa.handlers.SessionEndedRequestHandler;
import com.minsait.innovators.alexa.handlers.TaskIntentHandler;
import com.minsait.innovators.alexa.interceptors.CurrentUserInterceptor;

public class InnovatorsStreamHandler extends SkillStreamHandler {

	private static Skill getSkill() {
		return Skills.standard()
				.addRequestHandlers(Arrays.asList(new FallbackIntentHandler(), new CancelandStopIntentHandler(),
						new HelpIntentHandler(), new LaunchRequestHandler(), new SessionEndedRequestHandler(),
						new MeetingsIntentHandler(), new NextMeetingIntentHandler(), new CancelMeetingIntentHandler(),
						new PCRIntentHandler(), new NewsIntentHandler(), new TaskIntentHandler(),
						new ChangeTaskStateIntentHandler(), new FreshdeskIntentHandler(),
						new CreateTaskIntentHandler()))
				.addRequestInterceptor(new CurrentUserInterceptor()).build();

	}

	public InnovatorsStreamHandler() {
		super(getSkill());
	}

}
