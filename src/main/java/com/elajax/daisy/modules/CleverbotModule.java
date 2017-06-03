package com.elajax.daisy.modules;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
@PropertySource("classpath:Daisy.properties")
@Component("cleverbotmodule")
public class CleverbotModule {

	private ChatterBotFactory factory;

	private ChatterBot bot1;

	private ChatterBotSession bot1session;

	public CleverbotModule(	@Value("${cleverbot.apikey}") String apikey) {
		try {
			factory = new ChatterBotFactory();
			bot1 = factory.create(ChatterBotType.CLEVERBOT, apikey);
			bot1session = bot1.createSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String think(String text) {
		try {
			text = bot1session.think(text);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return text;
	}
}
