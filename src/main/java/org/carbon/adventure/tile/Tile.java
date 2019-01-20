package org.carbon.adventure.tile;

public abstract class Tile {

	protected Position position;

	protected boolean isSamePosition;

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Tile(Position position) {
		super();
		this.position = position;
	}

	public boolean isSamePosition(Position otherPosition) {
		return this.position.equals(otherPosition);
	}

}
