package org.carbon.adventure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.carbon.adventure.exception.OrientationException;
import org.carbon.adventure.exception.OutOfMapException;
import org.carbon.adventure.tile.Adventurer;
import org.carbon.adventure.tile.Mountain;
import org.carbon.adventure.tile.Orientation;
import org.carbon.adventure.tile.Position;
import org.carbon.adventure.tile.Treasure;

public class MapDescription {

	private static final String CURRENT_ELEMENT_IS_OUT_OF_THE_MAP = "Current element is out of the map";

	private static Logger logger = LogManager.getLogger(MapDescription.class);

	private List<Adventurer> adventurers;
	private List<Mountain> mountains;
	private List<Treasure> treasures;
	private Position size;

	private static final String DELIMITER = " - ";

	public MapDescription() {
		this.adventurers = new ArrayList<>();
		this.mountains = new ArrayList<>();
		this.treasures = new ArrayList<>();
	}

	public Position getSize() {
		return size;
	}

	public void setSize(Position size) {
		this.size = size;
	}

	public List<Adventurer> getAdventurers() {
		return adventurers;
	}

	public void setAdventurers(List<Adventurer> adventurers) {
		this.adventurers = adventurers;
	}

	public List<Mountain> getMoutains() {
		return mountains;
	}

	public void setMoutains(List<Mountain> moutains) {
		this.mountains = moutains;
	}

	public List<Treasure> getTreasures() {
		return treasures;
	}

	public void setTreasures(List<Treasure> treasures) {
		this.treasures = treasures;
	}

	@Override
	public String toString() {
		StringBuilder value = new StringBuilder("MapDescription size: ").append(this.getSize()).append("\n");
		for (int y = 0; y < this.getSize().getyAxis(); y++) {
			for (int x = 0; x < this.getSize().getxAxis(); x++) {
				Position currentPosition = new Position(x, y);
				boolean isFullfill = false;
				determineValueForMap(value, currentPosition, isFullfill);
			}
			value.append("\n");
		}
		return value.toString();
	}

	/**
	 * process the map description with a file write a file to output directory :
	 * src/main/resoucres/
	 * 
	 * @param fileInput
	 */
	public void process(File fileInput, File fileOutput) {
		processFile(fileInput);
		logger.info(this.toString());
		execute();
		logger.info(this.toString());
		writeFile(fileOutput);
	}

	private void determineValueForMap(StringBuilder value, Position currentPosition, boolean isFullfill) {
		String spaceCharacter = "\t\t";
		for (Mountain moutain : mountains) {
			if (moutain.getPosition().equals(currentPosition)) {
				value.append(spaceCharacter).append("M");
				isFullfill = true;
				break;
			}
		}
		for (Adventurer adventurer : adventurers) {
			if (adventurer.getPosition().equals(currentPosition) && !isFullfill) {
				value.append(spaceCharacter).append("A(").append(adventurer.getName().substring(0, 4)).append(")");
				isFullfill = true;
				break;
			}
		}
		for (Treasure treasure : treasures) {
			if (treasure.getPosition().equals(currentPosition) && !isFullfill) {
				value.append(spaceCharacter).append("T(").append(treasure.getTreasureNumber()).append(")");
				isFullfill = true;
				break;
			}
		}
		if (!isFullfill) {
			value.append(spaceCharacter).append(".");
		}
	}

	private void processFile(File file) {
		try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
			String line = null;
			while ((line = fileReader.readLine()) != null) {
				processLine(line);
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}

	private void processLine(String line) {
		String type = line.substring(0, 1);
		switch (type) {
		case "C":
			addSize(line);
			break;
		case "M":
			try {
				addMoutain(line);
			} catch (OutOfMapException e) {
				logger.error(e);
			}
			break;
		case "T":
			try {
				addTreasure(line);
			} catch (OutOfMapException e) {
				logger.error(e);
			}
			break;
		case "A":
			try {
				addAdventurer(line);
			} catch (OutOfMapException e) {
				logger.error(e);
			}
			break;
		default:
			break;
		}
	}

	private void addAdventurer(String line) throws OutOfMapException {
		String[] properties = trimLine(line);
		Position position = new Position(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
		if (position.isOut(size)) {
			throw new OutOfMapException(CURRENT_ELEMENT_IS_OUT_OF_THE_MAP);
		}
		try {
			this.getAdventurers().add(new Adventurer(properties[1], position, properties[4], properties[5]));
		} catch (OrientationException e) {
			logger.error(e);
			return;
		}
	}

	private void addTreasure(String line) throws OutOfMapException {
		String[] properties = trimLine(line);
		Position position = new Position(Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
		if (position.isOut(size)) {
			throw new OutOfMapException(CURRENT_ELEMENT_IS_OUT_OF_THE_MAP);
		}
		this.getTreasures().add(new Treasure(position, Integer.parseInt(properties[3])));
	}

	private void addMoutain(String line) throws OutOfMapException {
		String[] properties = trimLine(line);
		Position position = new Position(Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
		if (position.isOut(size)) {
			throw new OutOfMapException(CURRENT_ELEMENT_IS_OUT_OF_THE_MAP);
		}
		this.getMoutains().add(new Mountain(position));
	}

	private void addSize(String line) {
		String[] properties = trimLine(line);
		Position position = new Position(Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
		this.setSize(position);
	}

	private String[] trimLine(String line) {
		String[] properties = line.split(DELIMITER);
		for (int i = 0; i < properties.length; i++) {
			properties[i] = properties[i].trim();
		}
		return properties;
	}

	private void writeFile(File file) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			if (this.getSize() != null) {
				StringBuilder strBuilder = new StringBuilder("C");
				strBuilder.append(DELIMITER).append(this.getSize().getxAxis()).append(DELIMITER)
						.append(this.getSize().getyAxis());
				writer.write(strBuilder.append("\n").toString());
			}
			if (!mountains.isEmpty()) {
				for (Mountain mountain : mountains) {
					StringBuilder strBuilder = new StringBuilder("M");
					strBuilder.append(DELIMITER).append(mountain.getPosition().getxAxis()).append(DELIMITER)
							.append(mountain.getPosition().getyAxis());
					writer.write(strBuilder.append("\n").toString());
				}
			}
			if (!treasures.isEmpty()) {
				writer.write("# {T comme Trésor} - {Axe horizontal} - {Axe vertical} - {Nb. de trésors restants}\n");
				for (Treasure treasure : treasures) {
					StringBuilder strBuilder = new StringBuilder("T");
					strBuilder.append(DELIMITER).append(treasure.getPosition().getxAxis()).append(DELIMITER)
							.append(treasure.getPosition().getyAxis()).append(DELIMITER)
							.append(treasure.getTreasureNumber());
					writer.write(strBuilder.append("\n").toString());
				}
			}
			if (!treasures.isEmpty()) {
				writer.write("# {A comme Aventurier} - {Nom de l’aventurier} - {Axe horizontal} - {Axe vertical}"
						+ "- {Orientation} - {Nb. trésors ramassés}\n");
				for (Adventurer adventurer : adventurers) {
					StringBuilder strBuilder = new StringBuilder("A");
					strBuilder.append(DELIMITER).append(adventurer.getName()).append(DELIMITER)
							.append(adventurer.getPosition().getxAxis()).append(DELIMITER)
							.append(adventurer.getPosition().getyAxis()).append(DELIMITER)
							.append((adventurer.getOrientation().getName())).append(DELIMITER)
							.append(adventurer.getTreasureFound());
					writer.write(strBuilder.append("\n").toString());
				}
			}
		} catch (IOException e) {
			logger.error(e);
		}

	}

	private void execute() {
		List<char[]> sequences = new ArrayList<>();
		for (Adventurer adventurer : this.getAdventurers()) {
			sequences.add(adventurer.getSequence());
		}
		int maxSequence = 0;
		for (char[] cs : sequences) {
			if (cs.length > maxSequence) {
				maxSequence = cs.length;
			}
		}
		for (int i = 0; i < maxSequence; i++) {
			for (Adventurer adventurer : this.getAdventurers()) {
				char move;
				try {
					move = adventurer.getSequence()[i];
					processMove(move, adventurer);
				} catch (ArrayIndexOutOfBoundsException e) {
					logger.debug("this adventurer : " + adventurer.toString() + " has finished his sequence");
				} catch (OutOfMapException e) {
					logger.error(e);
				}
			}
		}
	}

	private void processMove(char move, Adventurer adventurer) throws OutOfMapException {
		Position newPosition = null;
		if (move == 'A') {
			switch (adventurer.getOrientation()) {
			case OUEST:
				newPosition = new Position(adventurer.getPosition().getxAxis() - 1,
						adventurer.getPosition().getyAxis());
				porcessNewPosition(adventurer, newPosition);
				break;
			case NORD:
				newPosition = new Position(adventurer.getPosition().getxAxis(),
						adventurer.getPosition().getyAxis() - 1);
				porcessNewPosition(adventurer, newPosition);
				break;
			case EST:
				newPosition = new Position(adventurer.getPosition().getxAxis() + 1,
						adventurer.getPosition().getyAxis());
				porcessNewPosition(adventurer, newPosition);
				break;
			case SUD:
				newPosition = new Position(adventurer.getPosition().getxAxis(),
						adventurer.getPosition().getyAxis() + 1);
				porcessNewPosition(adventurer, newPosition);
				break;
			default:
			}
		} else if (move == 'G') {
			switch (adventurer.getOrientation()) {
			case OUEST:
				adventurer.setOrientation(Orientation.SUD);
				break;
			case NORD:
				adventurer.setOrientation(Orientation.OUEST);
				break;
			case EST:
				adventurer.setOrientation(Orientation.NORD);
				break;
			case SUD:
				adventurer.setOrientation(Orientation.EST);
				break;
			default:
			}

		} else if (move == 'D') {
			switch (adventurer.getOrientation()) {
			case OUEST:
				adventurer.setOrientation(Orientation.NORD);
				break;
			case NORD:
				adventurer.setOrientation(Orientation.EST);
				break;
			case EST:
				adventurer.setOrientation(Orientation.SUD);
				break;
			case SUD:
				adventurer.setOrientation(Orientation.OUEST);
				break;
			default:
			}
		}
	}

	private void porcessNewPosition(Adventurer adventurer, Position newPosition) throws OutOfMapException {
		if (newPosition.isOut(this.getSize())) {
			throw new OutOfMapException("next position is out of map");
		}
		boolean updatePosition = true;
		for (Adventurer otherAdventurer : adventurers) {
			if (!otherAdventurer.equals(adventurer) && otherAdventurer.isSamePosition(newPosition)) {
				logger.info("An other adventurer is here");
				updatePosition = false;
				break;
			}
		}
		for (Mountain moutain : mountains) {
			if (moutain.isSamePosition(newPosition)) {
				logger.info("A mountain blocked the adventurer");
				updatePosition = false;
				break;
			}
		}
		if (updatePosition) {
			for (Treasure treasure : treasures) {
				if (treasure.isSamePosition(newPosition) && treasure.getTreasureNumber() > 0) {
					treasure.decreaseNumberOfTreasure();
					adventurer.increasedNumberOfTreseaure();
					break;
				}
			}
			adventurer.setPosition(newPosition);
		}
	}
}
