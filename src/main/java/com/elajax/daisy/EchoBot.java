package com.elajax.daisy;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

@PropertySource("classpath:Daisy.properties")
@Component("echobot")
public class EchoBot extends Botable implements IBotListener {
	@Value("${token}")
	private String token;

	public EchoBot(@Value("${token}") String token) {
		super(token);
		this.token = token;
	}

	public void onUpdateCallback(GetUpdates request, GetUpdatesResponse response) {
		for (Update update : response.updates()) {
			System.err.println("Update: " + update);
			if (update.message() != null) {
				getTelegramBot().execute(new SendMessage(update.message().chat().id(), String.format("You said %s",
						update.message().text() == null ? "nothing" : update.message().text())));
			}
			updateId.set(update.updateId() + 1);
		}
	}

	public void onFailureCallback(GetUpdates request, IOException e) {
		e.printStackTrace();
	}
}
