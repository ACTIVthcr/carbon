package org.carbon.adventure.tile;

import java.util.Arrays;

import org.carbon.adventure.exception.OrientationException;

public class Adventurer extends Tile {

	private Orientation orientation;

	private char[] sequence;

	private String name;

	private int treasureFound;

	public Adventurer(String name, Position position, String orientartion, String sequence)
			throws OrientationException {
		super(position);
		this.setOrientation(Orientation.convertStringToOrientation(orientartion));
		this.sequence = sequence.toCharArray();
		this.treasureFound = 0;
		this.name = name;
	}

	public Adventurer(String name, Position position, String orientartion, int treasureFound)
			throws OrientationException {
		super(position);
		this.setOrientation(Orientation.convertStringToOrientation(orientartion));
		this.treasureFound = treasureFound;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public char[] getSequence() {
		return sequence;
	}

	public void setSequence(char[] sequence) {
		this.sequence = sequence;
	}

	public int getTreasureFound() {
		return treasureFound;
	}

	public void setTreasureFound(int treasureFound) {
		this.treasureFound = treasureFound;
	}

	public boolean isBlocked(Tile tile) {
		if (tile instanceof Mountain || tile instanceof Adventurer) {
			return this.isSamePosition(tile.getPosition());
		}
		return false;
	}

	/**
	 * Increased the number of treasure found for this adventurer
	 */
	public void increasedNumberOfTreseaure() {
		treasureFound++;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Adventurer) {
			Adventurer adventurer = (Adventurer) obj;
			return this.getName().equals(adventurer.getName())
					&& this.getOrientation().equals(adventurer.getOrientation())
					&& this.isSamePosition(adventurer.getPosition())
					&& this.getTreasureFound() == adventurer.getTreasureFound();
		}
		return false;
	}

	@Override
	public String toString() {
		return "Adventurer [orientation=" + orientation + ", sequence=" + Arrays.toString(sequence) + ", name=" + name
				+ ", treasureFound=" + treasureFound + "]";
	}

}
