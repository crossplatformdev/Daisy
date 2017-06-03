package com.elajax.daisy;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.script.ScriptException;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.elajax.daisy.modules.AdminModule;
import com.elajax.daisy.modules.DiceRollModule;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineQueryResult;
import com.pengrad.telegrambot.model.request.InlineQueryResultGame;
import com.pengrad.telegrambot.model.request.InputMessageContent;
import com.pengrad.telegrambot.model.request.InputTextMessageContent;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.AnswerInlineQuery;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendGame;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

@PropertySource("classpath:Daisy.properties")
@Component("diceybot")
public class DiceyBot extends Botable implements IBotListener {

	org.slf4j.Logger logger = LoggerFactory.getLogger(DiceyBot.class);

	@Autowired
	private AdminModule adminModule;

	public DiceyBot(@Value("${token}") String tkn) {
		super(tkn);
		super.setiBotListener(this);
	}

	public void onUpdateCallback(GetUpdates request, GetUpdatesResponse response) {
		List<Update> updates = response.updates();
		if (updates != null) {
			for (Update update : updates) {
				System.err.println("Update: " + update);
				updateId.set(update.updateId() + 1);
				if (update.message() != null) {
					runModules(update, request);
				}
				if (update.callbackQuery() != null) {
					getTelegramBot().execute(new AnswerCallbackQuery(update.callbackQuery().id()).url("http://54.149.116.230"));
				}
			}
		}
	}

	private void runModules(Update update, GetUpdates request) {
		String text = update.message().text();
		text = text.replaceFirst("/", "");
		logger.info(text);
		if (text.startsWith("admin")) {
			text = text.replaceFirst("admin", "");
			if (text.startsWith(":")) {
				text = text.replaceFirst(":", "");
				send(adminModule.authentify(update.message().from(), text), update);
			}
		} else if (update.callbackQuery() != null) {
			InlineQueryResult<InlineQueryResultGame> results = new InlineQueryResultGame(update.callbackQuery().id(),
					update.callbackQuery().gameShortName());
			InputMessageContent inputMessageContent = new InputTextMessageContent("www.example.com");
			getTelegramBot().execute(
					new AnswerInlineQuery(update.inlineQuery().id(), results.inputMessageContent(inputMessageContent)));
		} else if (text.startsWith("mb")) {
			sendGame(update);
		} else if (text.matches("^[0-9]{9}\\(\\d+\\,\\d+\\)$")) {
			try {
				String coords = text.replaceFirst("[0-9]{9}", "");
				Integer mapId = Integer.parseInt(text.replaceFirst("\\(\\d+\\,\\d+\\)", ""));
				String file = adminModule.putInMap(mapId, coords, updateId);
				if (file != null) {
					sendFile(file, update);
					send(file.replace(".png", ""), update);
				}
			} catch (ScriptException | SQLException | InterruptedException | IOException e) {
				send(e.getMessage(), update);
				e.printStackTrace();
			}
		} else if (text.startsWith("map")) {
			try {
				text = text.replaceFirst("map", "");
				String file = adminModule.sendMap(update.updateId());
				send(file.replace(".png", ""), update);
				sendFile(file, update);
			} catch (InterruptedException | ScriptException | SQLException | IOException | ImageReadException
					| ImageWriteException e) {
				send(e.getMessage(), update);
				e.printStackTrace();
			}
		} else if (text.startsWith("dc")) {
			text = text.replaceFirst("dc", "");
			send(adminModule.think(text), update);
		} else if (text.startsWith("/")) {
			try {
				text = text.replaceFirst("/", "");
				send(adminModule.evaluate(text), update);
			} catch (ScriptException | SQLException | InterruptedException e) {
				send(e.getMessage(), update);
				e.printStackTrace();
			}
		} else if (update.message().text().startsWith("/") && (text.matches(DiceRollModule.ROLLPATTERN))) {
			try {
				String name = update.message().from().firstName();
				if (name == null){
					name = update.message().from().username();
				}
				send(adminModule.rollDice(text, name), update);
			} catch (InterruptedException | ScriptException | SQLException e) {
				send(e.getMessage(), update);
				e.printStackTrace();			
			}
		}
	}

	private void sendGame(Update u) {
		getTelegramBot().execute(new SendGame(u.message().chat().id(), "MapsBeta"));

	}

	public String saveFile(byte[] bytes) throws IOException {
		// convert byte array back to BufferedImage
		InputStream in = new ByteArrayInputStream(bytes);
		BufferedImage bImageFromConvert = ImageIO.read(in);
		ImageIO.write(bImageFromConvert, "jpg", new File("./test.jpg"));
		return "./test.jpg";
	}

	private void sendFile(String file, Update update) {
		getTelegramBot().execute(new SendPhoto(update.message().chat().id(), new File("./" + file)));
	}

	public static void send(String text, Update update) {
		getTelegramBot().execute(new SendMessage(update.message().chat().id(), text));
	}

	public void onFailureCallback(GetUpdates request, IOException e) {
		System.err.println("EXCEPTION: " + e.getMessage());
		e.printStackTrace();
	}
}
