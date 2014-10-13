package com.lastabyss.carbon.blocks;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.World;

public class BlockWallBanner extends BlockBanner {

	public BlockWallBanner() {
		super();
	}

	@Override
	public void doPhysics(World world, int i, int j, int k, Block block) {
		int l = world.getData(i, j, k);
		if ((l == 2) && (world.getType(i, j, k + 1).getMaterial().isBuildable())) {
			return;
		}
		if ((l == 3) && (world.getType(i, j, k - 1).getMaterial().isBuildable())) {
			return;
		}
		if ((l == 4) && (world.getType(i + 1, j, k).getMaterial().isBuildable())) {
			return;
		}
		if ((l == 5) && (world.getType(i - 1, j, k).getMaterial().isBuildable())) {
			return;
		}
		b(world, i, j, k, world.getData(i, j, k), 0);
		world.setAir(i, j, k);
		super.doPhysics(world, i, j, k, block);
	}

}
