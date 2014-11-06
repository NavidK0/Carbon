package com.lastabyss.carbon.utils.nmsclasses;

import net.minecraft.server.v1_7_R4.MathHelper;

import com.google.common.base.Objects;

public class Vec3i implements Comparable<Vec3i> {

	public static final Vec3i ZERO = new Vec3i(0, 0, 0);
	private final int x;
	private final int y;
	private final int z;

	public Vec3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3i(double x, double y, double z) {
		this(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof Vec3i)) {
			return false;
		} else {
			Vec3i vec3i = (Vec3i) obj;
			return this.getX() != vec3i.getX() ? false : (this.getY() != vec3i.getY() ? false : this.getZ() == vec3i.getZ());
		}
	}

	public int hashCode() {
		return (this.getY() + this.getZ() * 31) * 31 + this.getX();
	}

	public int compareTo(Vec3i vec3i) {
		return this.getY() == vec3i.getY() ? (this.getZ() == vec3i.getZ() ? this.getX() - vec3i.getX() : this.getZ() - vec3i.getZ()) : this.getY() - vec3i.getY();
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getZ() {
		return this.z;
	}

	public Vec3i crossProduct(Vec3i vec3i) {
		return new Vec3i(this.getY() * vec3i.getZ() - this.getZ() * vec3i.getY(), this.getZ() * vec3i.getX() - this.getX() * vec3i.getZ(), this.getX() * vec3i.getY() - this.getY() * vec3i.getX());
	}

	public double getDistSq(double x, double y, double z) {
		double diffX = (double) this.getX() - x;
		double diffY = (double) this.getY() - y;
		double diffZ = (double) this.getZ() - z;
		return diffX * diffX + diffY * diffY + diffZ * diffZ;
	}

	public double getDistSqFromCenter(double x, double y, double z) {
		return getDistSq(x - 0.5, y - 0.5, z - 0.5);
	}

	public double i(Vec3i var1) {
		return this.getDistSq((double) var1.getX(), (double) var1.getY(), (double) var1.getZ());
	}

	public String toString() {
		return Objects.toStringHelper((Object) this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
	}


}
