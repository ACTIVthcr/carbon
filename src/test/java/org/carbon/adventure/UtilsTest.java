package org.carbon.adventure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.carbon.adventure.exception.OrientationException;
import org.carbon.adventure.tile.Adventurer;
import org.carbon.adventure.tile.Mountain;
import org.carbon.adventure.tile.Position;
import org.carbon.adventure.tile.Treasure;

public abstract class UtilsTest {

	private static Logger logger = LogManager.getLogger(UtilsTest.class);

	static MapDescription parseOutputFile(File file) {
		MapDescription mapDescription = new MapDescription();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				processLine(line, mapDescription);
			}
		} catch (IOException e) {
			logger.error(e);
		}
		return mapDescription;
	}

	private static void processLine(String line, MapDescription mapDescription) {
		String type = line.substring(0, 1);
		switch (type) {
		case "C":
			addSize(line, mapDescription);
			break;
		case "M":
			addMoutain(line, mapDescription);
			break;
		case "T":
			addTreasure(line, mapDescription);
			break;
		case "A":
			addAdventurer(line, mapDescription);
			break;
		default:
			break;
		}
	}

	private static void addAdventurer(String line, MapDescription mapDescription) {
		String[] properties = trimLine(line);
		Position position = new Position(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
		try {
			mapDescription.getAdventurers()
					.add(new Adventurer(properties[1], position, properties[4], Integer.parseInt(properties[5])));
		} catch (OrientationException e) {
			logger.error(e);
			return;
		}
	}

	private static void addTreasure(String line, MapDescription mapDescription) {
		String[] properties = trimLine(line);
		Position position = new Position(Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
		mapDescription.getTreasures().add(new Treasure(position, Integer.parseInt(properties[3])));
	}

	private static void addMoutain(String line, MapDescription mapDescription) {
		String[] properties = trimLine(line);
		Position position = new Position(Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
		mapDescription.getMoutains().add(new Mountain(position));
	}

	private static void addSize(String line, MapDescription mapDescription) {
		String[] properties = trimLine(line);
		Position position = new Position(Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
		mapDescription.setSize(position);
	}

	private static String[] trimLine(String line) {
		String[] properties = line.split(" - ");
		for (int i = 0; i < properties.length; i++) {
			properties[i] = properties[i].trim();
		}
		return properties;
	}

}
