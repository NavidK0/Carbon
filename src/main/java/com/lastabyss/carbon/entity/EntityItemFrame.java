package com.lastabyss.carbon.entity;

import net.minecraft.server.v1_7_R4.World;

public class EntityItemFrame extends net.minecraft.server.v1_7_R4.EntityItemFrame {

	public EntityItemFrame(World world) {
		super(world);
	}

	public EntityItemFrame(World world, int i, int j, int k, int l) {
		super(world, i, j, k, l);
		setDirection(l);
	}

	@Override
	public int f() {
		return 12;
	}

	@Override
	public int i() {
		return 12;
	}

	@Override
	public int getRotation() {
		return getDataWatcher().getByte(9);
	}

	@Override
	public void setRotation(int rotation) {
		getDataWatcher().watch(9, rotation % 8);
		//legacy rotation
		getDataWatcher().watch(3, rotation % 4);
	}

}
