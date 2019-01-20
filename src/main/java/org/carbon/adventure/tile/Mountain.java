package org.carbon.adventure.tile;

public class Mountain extends Tile {

	public Mountain(Position position) {
		super(position);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Mountain) {
			Mountain moutain = (Mountain) obj;
			return moutain.getPosition().equals(this.getPosition());
		}
		return false;
	}

}
