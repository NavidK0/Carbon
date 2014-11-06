package com.lastabyss.carbon.utils.nmsclasses;

import java.util.Map;

import com.google.common.collect.Maps;

public enum Axis {

	X("x", UniverseDirection.HORIZONTAL), 
	Y("y", UniverseDirection.VERTICAL), 
	Z("z", UniverseDirection.HORIZONTAL);

	private static final Map<String, Axis> byName = Maps.newHashMap();
	static {
		for (Axis axis : values()) {
			byName.put(axis.getName().toLowerCase(), axis);
		}

	}

	private final String name;
	private final UniverseDirection unverseDirection;

	private Axis(String name, UniverseDirection universeDirection) {
		this.name = name;
		this.unverseDirection = universeDirection;
	}

	public String getName() {
		return this.name;
	}

	public boolean isVertical() {
		return this.unverseDirection == UniverseDirection.VERTICAL;
	}

	public boolean isHorizontal() {
		return this.unverseDirection == UniverseDirection.HORIZONTAL;
	}

	public String toString() {
		return this.name;
	}

	public boolean apply(BlockFace face) {
		return face != null && face.getAxis() == this;
	}

	public UniverseDirection getUniverseDirection() {
		return this.unverseDirection;
	}

}
