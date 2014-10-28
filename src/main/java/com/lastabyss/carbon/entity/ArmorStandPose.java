package com.lastabyss.carbon.entity;

import net.minecraft.server.v1_7_R4.NBTTagFloat;
import net.minecraft.server.v1_7_R4.NBTTagList;

public class ArmorStandPose {

	//IMPORTANT: METHOD NAMES SHOULD STAY IN SYNC WITH DataWatcher

	protected final float x;
	protected final float y;
	protected final float z;

	public ArmorStandPose(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public ArmorStandPose(NBTTagList list) {
		this.x = list.e(0);
		this.y = list.e(1);
		this.z = list.e(2);
	}

	public NBTTagList asNBTList() {
		NBTTagList list = new NBTTagList();
		list.add(new NBTTagFloat(this.x));
		list.add(new NBTTagFloat(this.y));
		list.add(new NBTTagFloat(this.z));
		return list;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ArmorStandPose)) {
			return false;
		} else {
			ArmorStandPose pose = (ArmorStandPose) obj;
			return this.x == pose.x && this.y == pose.y && this.z == pose.z;
		}
	}

	@Override
	public int hashCode() {
		return ((int) x) ^ ((int) y) ^ ((int) z);
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public float getZ() {
		return this.z;
	}
}
