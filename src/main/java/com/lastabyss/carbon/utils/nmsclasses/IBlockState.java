package com.lastabyss.carbon.utils.nmsclasses;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.World;

public class IBlockState {

	public static IBlockState getAt(World world, Position position) {
		Block block = world.getType(position.getX(), position.getY(), position.getZ());
		int data = world.getData(position.getX(), position.getY(), position.getZ());
		return new IBlockState(block, data);
	}

	private Block block;
	private int data;

	public IBlockState(Block block) {
		this.block = block;
	}

	public IBlockState(Block block, int data) {
		this.block = block;
		this.data = data;
	}

	public Block getBlock() {
		return block;
	}

	public int getData() {
		return data;
	}

}
