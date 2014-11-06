package com.lastabyss.carbon.utils.nmsclasses;

import net.minecraft.server.v1_7_R4.Block;

public class IBlockState {

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
