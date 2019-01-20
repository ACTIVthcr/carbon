package org.carbon.adventure.tile;

public class Treasure extends Tile {

	private int treasureNumber;

	public Treasure(Position position, int treasureNumber) {
		super(position);
		this.setTreasureNumber(treasureNumber);
	}

	public int getTreasureNumber() {
		return treasureNumber;
	}

	public void setTreasureNumber(int treasureNumber) {
		this.treasureNumber = treasureNumber;
	}

	/**
	 * Decreased the number of treasure that can be found for this treasure
	 */
	public void decreaseNumberOfTreasure() {
		treasureNumber--;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Treasure) {
			Treasure treasure = (Treasure) obj;
			return this.isSamePosition(treasure.getPosition())
					&& this.getTreasureNumber() == treasure.getTreasureNumber();
		}
		return false;
	}

}
