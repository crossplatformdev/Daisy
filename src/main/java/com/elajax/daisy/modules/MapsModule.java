package com.elajax.daisy.modules;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.script.ScriptException;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("mapsmodule")
public class MapsModule {
	org.slf4j.Logger logger = LoggerFactory.getLogger(MapsModule.class);

	private Map<Integer, String> maps = new HashMap<Integer, String>();
	private final static String CIRCLE = "" + "               ##################" + "\n"
			+ "          #######              ######" + "\n" + "       #####                       #####" + "\n"
			+ "     ####                             ####" + "\n" + "   ####         ###############         ####"
			+ "\n" + "  ###         #####         #####         ###" + "\n"
			+ " ###        ###                 ###        ###" + "\n"
			+ "###        ##                     ##        ###" + "\n"
			+ "##        ##        #######        ##        ##" + "\n"
			+ "#         #        ###   ###        #         #" + "\n"
			+ "#         #        ##     ##        #         #" + "\n"
			+ "#         #        ##     ##        #         #" + "\n"
			+ "#         ##        #######        ##         #" + "\n"
			+ "##         ##                     ##         ##" + "\n"
			+ "###         ###                 ###         ###" + "\n"
			+ " ###          #####         #####          ###" + "\n" + "  ###           ###############           ###"
			+ "\n" + "   ####                                 ####" + "\n"
			+ "     ####                             ####" + "\n" + "       ######                     ######" + "\n"
			+ "          #######             #######" + "\n" + "               #################";

	public String sendMap(Integer integer, JavaScriptModule javaScriptModule) throws InterruptedException,
			ScriptException, SQLException, IOException, ImageReadException, ImageWriteException {
		String colorString = javaScriptModule.evaluate("_" + integer + "=gen();");
		;
		if (colorString == null) {
			sendMap(integer + 1, javaScriptModule);
		}
		BufferedImage bi = generateBitmap(colorString, "0,0", integer);
		maps.put(integer, colorString);
		String filename = integer + ".png";
		File outputfile = new File(filename);
		ImageIO.write(bi, "PNG", outputfile);

		return filename;
	}

	public String putInMap(Integer mapId, String ints, JavaScriptModule js, Integer integer)
			throws IOException, ScriptException, SQLException, InterruptedException {
		String map = maps.get(mapId);
		BufferedImage bi = generateBitmap(map, ints, integer);
		String filename = mapId + ".png";
		File outputfile = new File(filename);
		ImageIO.write(bi, "PNG", outputfile);

		return filename;
	}

	private BufferedImage generateBitmap(String map, String coords, Integer id) {
		if (map == null || map == "") {
			return null;
		}
		logger.debug(map);
		coords = coords.replace("(", "").replace(")", "");
		String[] c = coords.split(",");
		Integer a = Integer.valueOf(String.valueOf(c[0]));
		Integer b = Integer.valueOf(String.valueOf(c[1]));
		String[] lines = map.split("\n");
		BufferedImage bi = new BufferedImage(lines.length, lines[0].length(), BufferedImage.TYPE_INT_RGB);
		int x_index = 0;
		int y_index = 0;
		String[][] arr = new String[lines.length + 1][lines[0].length() + 1];
		for (int i = 0; i < lines.length; i++) {
			for (int j = 0; j < lines[i].toCharArray().length; j++) {
				int rgb = 0;
				String ch = String.valueOf(lines[i].toCharArray()[j]);
				arr[i][j] = ch;
				Integer color = Integer.valueOf((int) ch.toCharArray()[0]);
				Integer random = Double.valueOf(color * Math.random()).intValue();
				if (i > a && i < a + CIRCLE.split("\n").length && j > b && j < b + CIRCLE.split("\n")[x_index].length() 
						&& !String.valueOf(CIRCLE.split("\n")[x_index].toCharArray()[y_index]).equals(" ")
						|| ch == "#") {
					Integer red = Double.valueOf(255).intValue();
					Integer green = Double.valueOf(127).intValue();
					Integer blue = Double.valueOf(64).intValue();
					arr[i][j] = "#";
					rgb = new Color(red, green, blue).getRGB();
					if (x_index < CIRCLE.split("\n").length - 1) {
						x_index += 1;
					}
				} else if (ch.equals("▓")) {
					rgb = new Color(64, 64, 64).getRGB();
				} else if (ch.equals("▒")) {
					rgb = new Color(192, 192, 192).getRGB();
				} else if (ch.equals("░")) {
					rgb = new Color(255, 255, 255).getRGB();
				} else if (Pattern.matches("[r|R]", ch)) {
					rgb = new Color(color, random, random).getRGB();
				} else if (Pattern.matches("[g|G]", ch)) {
					rgb = new Color(random, color, random).getRGB();
				} else if (Pattern.matches("[b|B]", ch)) {
					rgb = new Color(random, random, color).getRGB();
				} else {
					rgb = new Color(0, 0, 0).getRGB();
				}

				bi.setRGB(i, j, rgb);
			}
			y_index+=1;
		}
		map = "";
		for (String[] str : arr) {
			map += str + "\n";
		}

		maps.put(id, map);
		return bi;
	}
}
