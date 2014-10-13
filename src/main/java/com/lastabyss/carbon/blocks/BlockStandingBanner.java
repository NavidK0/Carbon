package com.lastabyss.carbon.blocks;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.World;

public class BlockStandingBanner extends BlockBanner {

	public BlockStandingBanner() {
		super();
	}

	@Override
	public void doPhysics(World world, int x, int y, int z, Block block) {
		if (!world.getType(x, y - 1, z).getMaterial().isBuildable()) {
			b(world, x, y, z, world.getData(x, y, z), 0);
			world.setAir(x, y, z);
		}
		super.doPhysics(world, x, y, z, block);
	}

}
