package org.carbon.adventure.tile;

public class Position {

	private int xAxis;
	private int yAxis;

	public Position(int xAxis, int yAxis) {
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}

	public int getxAxis() {
		return xAxis;
	}

	public void setxAxis(int xAxis) {
		this.xAxis = xAxis;
	}

	public int getyAxis() {
		return yAxis;
	}

	public void setyAxis(int yAxis) {
		this.yAxis = yAxis;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Position) {
			Position position = (Position) obj;
			return position.getxAxis() == this.xAxis && position.getyAxis() == this.yAxis;
		}
		return false;
	}

	@Override
	public String toString() {
		return "[xAxis=" + xAxis + ", yAxis=" + yAxis + "]";
	}

	/**
	 * Check if the position is out of a map with (x,y)size
	 * 
	 * @param size
	 * @return
	 */
	public boolean isOut(Position size) {
		return this.xAxis + 1 > size.getxAxis() || this.yAxis + 1 > size.getyAxis();
	}

}
