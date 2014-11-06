package com.lastabyss.carbon.utils.nmsclasses;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.Vec3D;

public class Position extends Vec3i {

	public static final Position ZERO = new Position(0, 0, 0);

	public Position(int x, int y, int z) {
		super(x, y, z);
	}

	public Position(double x, double y, double z) {
		super(x, y, z);
	}

	public Position(Entity entity) {
		this(entity.locX, entity.locY, entity.locZ);
	}

	public Position(Vec3D vec) {
		this(vec.a, vec.b, vec.c);
	}

	public Position(Vec3i vec) {
		this(vec.getX(), vec.getY(), vec.getZ());
	}

	public Position add(double x, double y, double z) {
		return new Position((double) this.getX() + x, (double) this.getY() + y, (double) this.getZ() + z);
	}

	public Position add(int x, int y, int z) {
		return new Position(this.getX() + x, this.getY() + y, this.getZ() + z);
	}

	public Position add(Vec3i vec) {
		return new Position(this.getX() + vec.getX(), this.getY() + vec.getY(), this.getZ() + vec.getZ());
	}

	public Position multiply(int var1) {
		return new Position(this.getX() * var1, this.getY() * var1, this.getZ() * var1);
	}

	public Position getUp() {
		return this.getUp(1);
	}

	public Position getUp(int dist) {
		return this.getRelative(BlockFace.UP, dist);
	}

	public Position getDown() {
		return this.getDown(1);
	}

	public Position getDown(int dist) {
		return this.getRelative(BlockFace.DOWN, dist);
	}

	public Position getNorth() {
		return this.getNorth(1);
	}

	public Position getNorth(int dist) {
		return this.getRelative(BlockFace.NORTH, dist);
	}

	public Position getSouth() {
		return this.getSouth(1);
	}

	public Position getSouth(int dist) {
		return this.getRelative(BlockFace.SOUTH, dist);
	}

	public Position getWest() {
		return this.getWest(1);
	}

	public Position getWest(int dist) {
		return this.getRelative(BlockFace.WEST, dist);
	}

	public Position getEast() {
		return this.getEast(1);
	}

	public Position getEast(int dist) {
		return this.getRelative(BlockFace.EAST, dist);
	}

	public Position getRelative(BlockFace face) {
		return this.getRelative(face, 1);
	}

	public Position getRelative(BlockFace face, int dist) {
		return new Position(this.getX() + face.getFrontDirectionX() * dist, this.getY() + face.getFrontDirectionY() * dist, this.getZ() + face.getFrontDirectionZ() * dist);
	}

	public Position crossProduct(Vec3i vec) {
		return new Position(this.getY() * vec.getZ() - this.getZ() * vec.getY(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getY() - this.getY() * vec.getX());
	}

}
