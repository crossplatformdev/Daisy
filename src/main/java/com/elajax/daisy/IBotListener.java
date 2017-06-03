package com.elajax.daisy;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

@Component("ibotlistener")
public interface IBotListener {
	public void onUpdateCallback(GetUpdates request, GetUpdatesResponse response);
	public void onFailureCallback(GetUpdates request, IOException e);
}
