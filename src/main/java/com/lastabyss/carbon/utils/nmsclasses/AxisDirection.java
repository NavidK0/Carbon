package com.lastabyss.carbon.utils.nmsclasses;

public enum AxisDirection {

	POSITIVE(1, "Towards positive"), NEGATIVE(-1, "Towards negative");
	private final int direction;
	private final String name;

	private AxisDirection(int direction, String name) {
		this.direction = direction;
		this.name = name;
	}

	public int getDirection() {
		return this.direction;
	}

	public String toString() {
		return this.name;
	}

}