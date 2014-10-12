package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.entity.TileEntityOptimizedChest;

import net.minecraft.server.v1_7_R4.TileEntity;
import net.minecraft.server.v1_7_R4.World;

public class BlockOptimizedChest extends net.minecraft.server.v1_7_R4.BlockChest {

	public BlockOptimizedChest(int i) {
		super(i);
		c(2.5F);
		a(f);
		c("chest");
	}

	@Override
	public TileEntity a(World paramWorld, int paramInt) {
		return new TileEntityOptimizedChest();
	}

}
