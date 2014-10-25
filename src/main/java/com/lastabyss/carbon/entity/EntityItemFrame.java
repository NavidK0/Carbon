package com.lastabyss.carbon.entity;

import java.util.List;

import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityHanging;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.World;

public class EntityItemFrame extends net.minecraft.server.v1_7_R4.EntityItemFrame {

	public EntityItemFrame(World world) {
		super(world);
		direction = -1;
	}

	public EntityItemFrame(World world, int i, int j, int k, int l) {
		super(world, i, j, k, l);
		setDirection(l);
	}

	@Override
	public boolean survives() {
		if (!world.getType(x, y, z).getMaterial().isBuildable()) {
			return false;
		}
		Block blockAt = world.getType(MathHelper.floor(locX), MathHelper.floor(locY), MathHelper.floor(locZ));
		if (blockAt != Blocks.AIR) {
			AxisAlignedBB blockbounds = AxisAlignedBB.a(
				MathHelper.floor(locX) + blockAt.x(), MathHelper.floor(locY) + blockAt.z(), MathHelper.floor(locZ) + blockAt.B(),
				MathHelper.floor(locX) + blockAt.y(), MathHelper.floor(locY) + blockAt.A(), MathHelper.floor(locZ) + blockAt.C()
			);
			if (boundingBox.b(blockbounds)) {
				return false;
			}
		}
		@SuppressWarnings("unchecked")
		List<Entity> entityList = this.world.getEntities(this, this.boundingBox);
		if (entityList.size() == 0) {
			return true;
		}
		for (Entity entity : entityList) {
			if (entity instanceof EntityHanging) {
				return false;
			}
		}
		return true;
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
	public void setItem(ItemStack itemStack) {
		super.setItem(itemStack);
		if (direction != -1) {
			world.updateAdjacentComparators(x, y, z, Blocks.AIR);
		}
	}

	@Override
	public int getRotation() {
		return getDataWatcher().getByte(9);
	}

	@Override
	public void setRotation(int rotation) {
		getDataWatcher().watch(9, rotation % 8);
		getDataWatcher().watch(3, rotation % 4);
		if (direction != -1) {
			world.updateAdjacentComparators(x, y, z, Blocks.AIR);
		}
	}

	@Override
	public void die() {
		super.die();
		if (direction != -1) {
			world.updateAdjacentComparators(x, y, z, Blocks.AIR);
		}
	}

	public int getRedstonePower() {
		return getItem() == null ? 0 : getRotation() % 8 + 1;
	}

}
