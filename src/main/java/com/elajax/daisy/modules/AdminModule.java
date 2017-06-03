package com.elajax.daisy.modules;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.script.ScriptException;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.model.User;

@PropertySource("classpath:Daisy.properties")
@Component("adminmodule")
public class AdminModule {
	@Autowired
	private JavaScriptModule javaScriptModule;
	@Autowired
	private DiceRollModule diceRollModule;
	@Autowired
	private CleverbotModule cleverbotModule;
	@Autowired
	private MapsModule mapsModule;

	private String tkn;

	public AdminModule(@Value("${token}") String token) {
		this.tkn = token;
	}

	public static boolean checkIfTokenIsValid(String text, String tkn) {
		return text.contains(tkn.substring(0, 5)) ? true : false;
	}

	public String authentify(User u, String text) {
		if (checkIfTokenIsValid(text, this.tkn)) {
			try {
				javaScriptModule.reload();
				return "JS Reloaded by username:" + u.username() + "::" + u.firstName() + " " + u.lastName() + "...";
			} catch (SQLException e) {
				e.printStackTrace();
				return e.getMessage();
			} catch (ScriptException e) {
				e.printStackTrace();
				return e.getMessage();
			}
		} else {
			return "Enter password.";
		}
	}

	public String evaluate(String text) throws ScriptException, SQLException, InterruptedException {
		return javaScriptModule.evaluate(text);
	}

	public String think(String text) {
		return cleverbotModule.think(text);
	}

	public String sendMap(Integer integer) throws InterruptedException, ScriptException, SQLException, IOException, ImageReadException, ImageWriteException {
		return mapsModule.sendMap(integer, javaScriptModule);
	}
	
	public String putInMap(Integer mapId, String coords, AtomicInteger updateId) throws IOException, ScriptException, SQLException, InterruptedException {
		return mapsModule.putInMap(mapId, coords, javaScriptModule, mapId);
	}
	
	public String rollDice(String text , String name) throws InterruptedException, ScriptException, SQLException {
		return diceRollModule.rollDice(text, name, javaScriptModule);
	}

	public CleverbotModule getCleverbotModule() {
		return cleverbotModule;
	}

	public void setCleverbotModule(CleverbotModule cleverbotModule) {
		this.cleverbotModule = cleverbotModule;
	}

	public JavaScriptModule getJavaScriptModule() {
		return javaScriptModule;
	}

	public void setJavaScriptModule(JavaScriptModule javaScriptModule) {
		this.javaScriptModule = javaScriptModule;
	}

	public DiceRollModule getDiceRollModule() {
		return diceRollModule;
	}

	public void setDiceRollModule(DiceRollModule diceRollModule) {
		this.diceRollModule = diceRollModule;
	}

	public MapsModule getMapsModule() {
		return mapsModule;
	}

	public void setMapsModule(MapsModule mapsModule) {
		this.mapsModule = mapsModule;
	}
}
