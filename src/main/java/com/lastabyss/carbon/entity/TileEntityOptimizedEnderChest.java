package com.lastabyss.carbon.entity;

import net.minecraft.server.v1_7_R4.Blocks;

public class TileEntityOptimizedEnderChest extends net.minecraft.server.v1_7_R4.TileEntityEnderChest {

	private boolean closed = true;
	private boolean opened = false;

	private int ticks;

	@Override
	public void h() {
		if (++this.ticks % 20 * 4 == 0) {
			this.world.playBlockAction(this.x, this.y, this.z, Blocks.ENDER_CHEST, 1, this.j);
		}
	}

	@Override
	public void a() {
		super.a();
		if (world == null) {
			return;
		}
		if (!opened) {
			opened = true;
			closed = false;
			this.world.makeSound(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D, "random.chestopen", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
		}
	}

	@Override
	public void b() {
		super.b();
		if (world == null) {
			return;
		}
		if (j == 0 && !closed) {
			closed = true;
			opened = false;
			this.world.makeSound(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D, "random.chestclosed", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
		}
	}

}
