package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.sounds.Sounds;
import net.minecraft.server.v1_7_R4.BlockHalfTransparent;
import net.minecraft.server.v1_7_R4.CreativeModeTab;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.World;

public class BlockSlime extends BlockHalfTransparent {
	public BlockSlime() {
		super("slime", Material.CLAY, false);
		a(CreativeModeTab.c);
		this.frictionFactor = 0.8F;
		a(Sounds.SLIME);
		// Sets hardness of block
		c(0);
		H();
	}

	// onEntityLanded
	@Override
	public void a(World world, int x, int y, int z, Entity entityIn, float fallDistance) {
		if (entityIn.isSneaking()) {
			super.a(world, x, y, z, entityIn, fallDistance);
		} else {
			super.a(world, x, y, z, entityIn, fallDistance);
		}
	}

	@Override
	public void a(World world, int x, int y, int z, Entity entity) {
		if ((Math.abs(entity.motY) < 0.1D) && (!entity.isSneaking())) {
			double mot = 0.4D + Math.abs(entity.motY) * 0.2D;
			entity.motX *= mot;
			entity.motZ *= mot;
		}
		super.a(world, x, y, z, entity);
	}

}