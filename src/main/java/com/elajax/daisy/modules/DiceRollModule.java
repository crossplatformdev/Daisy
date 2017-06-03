package com.elajax.daisy.modules;

import java.sql.SQLException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptException;

import org.springframework.stereotype.Component;

@Component("dicerollmodule")
public class DiceRollModule {

	public static final String ROLLPATTERN = "([\\d]+)?([d|r|b])([\\d]+)?(([+|\\-|*|\\/|>=|<=|<|>|==][\\d]+)*)?(\\s.+)?";
	private int[] DADO_BLANCO = new int[6];
	private int[] DADO_ROJO = new int[6];

	public DiceRollModule() {
		for (int i = 0; i < 3; i++) {
			DADO_BLANCO[i] = 0;
		}
		DADO_BLANCO[4] = 1;
		DADO_BLANCO[5] = 2;

		for (int i = 0; i < 2; i++) {
			DADO_ROJO[i] = 0;
		}
		DADO_ROJO[3] = 1;
		DADO_ROJO[4] = 2;
		DADO_ROJO[5] = 3;
	}

	public String rollDice(String text, String name, JavaScriptModule javaScriptModule)
			throws InterruptedException, ScriptException, SQLException {
		String result = "";
		String bonus = "";
		text = text.toLowerCase();
		text = text.replaceAll("([\\s])*", "");
		Pattern p = Pattern.compile(ROLLPATTERN);
		Matcher m = p.matcher(text);
		String message = "";
		while (m.find()) {
			Integer ammount;
			if (m.group(1) != null) {
				ammount = Integer.valueOf(m.group(1));
			} else {
				ammount = 1;
			}
			String dice = m.group(2);

			Integer faces;
			if (m.group(3) != null) {
				faces = Integer.valueOf(m.group(3));
			} else {
				faces = 0;
			}
			
			bonus = m.group(4) != null ? javaScriptModule.evaluate(m.group(4)) : "";

			for (int i = 0; i < ammount; i++) {
				result += "+"+roll(dice, faces);
			}
			
			message  = m.group(6) != null ? " ( " + m.group(6) + " )" : "";
		}
		result = result.replaceFirst("\\+", "");
		String resAux = javaScriptModule.evaluate(result);
		String bonusAux = javaScriptModule.evaluate(bonus.replaceAll("\\s", ""));
		bonusAux = bonusAux != "null" ? "+" + bonusAux : "";
		String total = javaScriptModule.evaluate(resAux + bonusAux);
		;
		return name+" Rolled: "+ result + " = " + resAux + bonusAux + " = " + total + message;
	}

	private String roll(String dice, Integer faces) {
		String ret = "";

		if (dice.equalsIgnoreCase("d")) {
			ret = rollD(faces);
		}
		if (dice.equalsIgnoreCase("b")) {
			ret = rollB();
		}
		if (dice.equalsIgnoreCase("r")) {
			ret = rollR();
		}

		return ret;
	}

	private String rollR() {
		String ret = String.valueOf(DADO_ROJO[Integer.valueOf(rollD(6)) - 1]);
		return ret;
	}

	private String rollB() {
		String ret = String.valueOf(DADO_BLANCO[Integer.valueOf(rollD(6)) - 1]);
		return ret;
	}

	private String rollD(Integer faces) {
		Random rn = new Random();
		String text = String.valueOf(Math.round((rn.nextInt(faces) + 1)));
		return text;
	}
}
