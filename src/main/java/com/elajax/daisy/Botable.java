package com.elajax.daisy;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Value;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

public class Botable {
	private static TelegramBot telegramBot;
	private IBotListener iBotListener;
	private int LONG_POOLING_TIMEOUT_IN_SECONDS = 7;
	protected AtomicInteger updateId = new AtomicInteger(0);

	public Botable(@Value("${token}") String token) {
		Botable.telegramBot = TelegramBotAdapter.build(token);		
	}

	public void run() throws InterruptedException {

		while (true) {
			System.out.println("Checking for updates...");

			telegramBot.execute(new GetUpdates().limit(100).offset(updateId.get()).timeout(LONG_POOLING_TIMEOUT_IN_SECONDS),
					new Callback<GetUpdates, GetUpdatesResponse>() {

						public void onResponse(GetUpdates request, GetUpdatesResponse response) {
							iBotListener.onUpdateCallback(request, response);
						}

						public void onFailure(GetUpdates request, IOException e) {
							iBotListener.onFailureCallback(request, e);
						}
					});

			Thread.sleep(LONG_POOLING_TIMEOUT_IN_SECONDS * 1000);
		}
	}

	public static TelegramBot getTelegramBot() {
		return telegramBot;
	}

	public static void setTelegramBot(TelegramBot bot) {
		telegramBot = bot;
	}

	public IBotListener getiBotListener() {
		return iBotListener;
	}

	public void setiBotListener(IBotListener iBotListener) {
		this.iBotListener = iBotListener;
	}
}
