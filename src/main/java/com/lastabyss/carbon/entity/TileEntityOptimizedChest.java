package com.lastabyss.carbon.entity;

public class TileEntityOptimizedChest extends net.minecraft.server.v1_7_R4.TileEntityChest {

	private boolean closed = true;
	private boolean opened = false;

	@Override
	public void h() {
	}

	@Override
	public void startOpen() {
		super.startOpen();
		if (world == null) {
			return;
		}
		i();
		if (i == null && k == null) {
			if (!opened) {
				opened = true;
				closed = false;
				double soundX = this.x + 0.5D;
				double soundZ = this.z + 0.5D;
				if (this.l != null) {
					soundZ += 0.5D;
				}
				if (this.j != null) {
					soundX += 0.5D;
				}
				this.world.makeSound(soundX, this.y + 0.5D, soundZ, "random.chestopen", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
			}
		}
	}

	@Override
	public void closeContainer() {
		super.closeContainer();
		o = getViewers().size();
		if (world == null) {
			return;
		}
		if (i == null && k == null) {
			if (o == 0 && !closed) {
				closed = true;
				opened = false;
				double soundX = this.x + 0.5D;
				double soundZ = this.z + 0.5D;
				if (this.l != null) {
					soundZ += 0.5D;
				}
				if (this.j != null) {
					soundX += 0.5D;
				}
				this.world.makeSound(soundX, this.y + 0.5D, soundZ, "random.chestclosed", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
			}
		}
	}

}
