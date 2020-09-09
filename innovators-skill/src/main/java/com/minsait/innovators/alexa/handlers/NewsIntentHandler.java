package com.minsait.innovators.alexa.handlers;

import static com.minsait.innovators.alexa.commons.CommonsInterface.CARD_TITLE;
import static com.minsait.innovators.alexa.commons.CommonsInterface.NEWS_ATT;
import static com.minsait.innovators.alexa.commons.CommonsInterface.NO_NEWS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.minsait.innovators.alexa.commons.CommonsInterface;
import com.minsait.innovators.alexa.model.CompanyNewsWrapper;
import com.minsait.innovators.alexa.service.NewsService;

public class NewsIntentHandler implements RequestHandler {
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(Predicates.intentName("NewsIntent").or(Predicates.intentName("AMAZON.YesIntent")));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		List<CompanyNewsWrapper> news = new ArrayList<>();
		if (((IntentRequest) input.getRequestEnvelope().getRequest()).getIntent().getName()
				.equals("AMAZON.YesIntent")) {
			final Optional<List<CompanyNewsWrapper>> opt = CommonsInterface.getCachedNews(input);
			if (opt.isPresent()) {
				news = CommonsInterface.getCachedNews(input).orElse(new ArrayList<>());
			}
		} else {
			news = NewsService.getInstance().getLatestNews();
		}
		if (news.isEmpty()) {
			return input.getResponseBuilder().withSpeech(NO_NEWS).withSimpleCard(CARD_TITLE, NO_NEWS)
					.withShouldEndSession(false).build();
		}

		final CompanyNewsWrapper firstNew = news.iterator().next();
		final StringBuilder builder = new StringBuilder();
		builder.append("Noticia del ");
		builder.append(firstNew.getCompanyNews().getDate());
		builder.append(". ");
		builder.append(firstNew.getCompanyNews().getDescription());
		builder.append(". <break time=\"2s\"/>  ");
		builder.append(firstNew.getCompanyNews().getText());
		builder.append(". <break time=\"2s\"/>  ¿Quieres más noticias?");
		news.remove(firstNew);
		if (news.isEmpty()) {

			input.getAttributesManager().getSessionAttributes().remove(NEWS_ATT);

		} else {
			input.getAttributesManager().getSessionAttributes().put(NEWS_ATT, news);
		}

		return input.getResponseBuilder().withSpeech(CommonsInterface.escapeSSMLCharacters(builder.toString()))
				.withShouldEndSession(false).withSimpleCard(CARD_TITLE, builder.toString()).build();
	}
}
