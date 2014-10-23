package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.entity.TileEntityOptimizedEnderChest;

import net.minecraft.server.v1_7_R4.TileEntity;
import net.minecraft.server.v1_7_R4.World;

public class BlockOptimizedEnderChest extends net.minecraft.server.v1_7_R4.BlockEnderChest {

	public BlockOptimizedEnderChest() {
		super();
		c(22.5F);
		b(1000.0F);
		a(i);
		c("enderChest");
		a(0.5F);
	}

	public TileEntity a(World paramWorld, int paramInt) {
		return new TileEntityOptimizedEnderChest();
	}

}
