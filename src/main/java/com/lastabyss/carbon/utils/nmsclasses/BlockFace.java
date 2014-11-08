package com.lastabyss.carbon.utils.nmsclasses;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Random;

import net.minecraft.server.v1_7_R4.MathHelper;

public enum BlockFace {

	DOWN(0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y), 
	UP(1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y), 
	NORTH(2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z), 
	SOUTH(3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z), 
	WEST(4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X), 
	EAST(5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X);

	private static final BlockFace[] byId = new BlockFace[6];
	private static final BlockFace[] directionById = new BlockFace[4];
	private static final Map<String, BlockFace> byName = Maps.newHashMap();

	static {
		for (BlockFace face : values()) {
			byId[face.id] = face;
			if (face.getAxis().isHorizontal()) {
				directionById[face.directionId] = face;
			}

			byName.put(face.getName().toLowerCase(), face);
		}
	}

	public static BlockFace getById(int id) {
		return byId[MathHelper.a(id % byId.length)];
	}

	public static BlockFace fromDirection(int directionId) {
		return directionById[MathHelper.a(directionId % directionById.length)];
	}

	public static BlockFace fromDirection(double entityYaw) {
		return fromDirection(MathHelper.floor(entityYaw / 90.0D + 0.5D) & 3);
	}

	public static BlockFace getRandom(Random random) {
		return values()[random.nextInt(values().length)];
	}

	private final int id;
	private final int oppositeId;
	private final int directionId;
	private final String name;
	private final Axis axis;
	private final AxisDirection axisDirection;

	private BlockFace(int id, int oppositeId, int directionId, String name, AxisDirection axisDirection, Axis axis) {
		this.id = id;
		this.directionId = directionId;
		this.oppositeId = oppositeId;
		this.name = name;
		this.axis = axis;
		this.axisDirection = axisDirection;
	}

	public int getId() {
		return this.id;
	}

	public int toDirection() {
		return this.directionId;
	}

	public AxisDirection getAxisDirection() {
		return this.axisDirection;
	}

	public BlockFace getOpposite() {
		return getById(this.oppositeId);
	}

	public int getFrontDirectionX() {
		return this.axis == Axis.X ? this.axisDirection.getDirection() : 0;
	}

	public int getFrontDirectionY() {
		return this.axis == Axis.Y ? this.axisDirection.getDirection() : 0;
	}

	public int getFrontDirectionZ() {
		return this.axis == Axis.Z ? this.axisDirection.getDirection() : 0;
	}

	public String getName() {
		return this.name;
	}

	public Axis getAxis() {
		return this.axis;
	}

	public String toString() {
		return this.name;
	}

}
