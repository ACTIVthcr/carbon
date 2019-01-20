package org.carbon.adventure.tile;

import org.carbon.adventure.exception.OrientationException;

public enum Orientation {

	NORD("N"), SUD("S"), EST("E"), OUEST("O");

	private final String name;

	Orientation(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static Orientation convertStringToOrientation(String name) throws OrientationException {
		Orientation orientation = null;
		switch (name) {
		case "N":
			orientation = NORD;
			break;
		case "S":
			orientation = SUD;
			break;
		case "E":
			orientation = EST;
			break;
		case "O":
			orientation = OUEST;
			break;
		default:
			throw new OrientationException("Unhandle orientation");
		}
		return orientation;
	}

}
