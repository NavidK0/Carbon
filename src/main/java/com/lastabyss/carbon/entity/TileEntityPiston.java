package com.lastabyss.carbon.entity;

import java.util.List;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.Facing;
import net.minecraft.server.v1_7_R4.NBTTagCompound;

public class TileEntityPiston extends net.minecraft.server.v1_7_R4.TileEntityPiston {

	private Block block;
	private int blockData;
	private int face;
	private boolean extending;
	private float progressCopy;
	private float progress;

	public TileEntityPiston() {
		super();
	}

	public TileEntityPiston(final Block block, final int blockData, final int face, final boolean extending) {
		super(block, blockData, face, extending, false);
		this.block = block;
		this.blockData = blockData;
		this.face = face;
		this.extending = extending;
	}

	@Override
	public Block a() {
		return block;
	}

	@Override
	public int p() {
		return blockData;
	}

	@Override
	public boolean b() {
		return extending;
	}

	@Override
	public int c() {
		return face;
	}

	@Override
	public float a(float f) {
		if (f > 1.0f) {
			f = 1.0f;
		}
		return progress + ((progressCopy - progress) * f);
	}

	@SuppressWarnings("unchecked")
	private void moveEntities(float f, final float f1) {
		if (extending) {
			f = 1.0f - f;
		} else {
			--f;
		}
		final AxisAlignedBB axisalignedbb = Blocks.PISTON_MOVING.a(world, x, y, z, block, f, face);
		if (axisalignedbb != null) {
			final List<Entity> list = world.getEntities(null, axisalignedbb);
			if (!list.isEmpty()) {
				for (final Entity entity : list) {
					if (extending && block == Carbon.injector().slimeBlock) {
						BlockFace blockFace = BlockFace.getById(face);
						switch (blockFace.getAxis()) {
							case X: {
								entity.motX = blockFace.getFrontDirectionX();
								break;
							}
							case Y: {
								entity.motY = blockFace.getFrontDirectionY();
								break;
							}
							case Z: {
								entity.motZ = blockFace.getFrontDirectionZ();
								break;
							}
						}
					} else {
						entity.move(f1 * Facing.b[face], f1 * Facing.c[face], f1 * Facing.d[face]);
					}
				}
			}
		}
	}

	@Override
	public void f() {
		if ((progress < 1.0f) && (world != null)) {
			final float n = 1.0f;
			progressCopy = n;
			progress = n;
			world.p(x, y, z);
			s();
			if (world.getType(x, y, z) == Blocks.PISTON_MOVING) {
				world.setTypeAndData(x, y, z, block, blockData, 3);
				world.e(x, y, z, block);
			}
		}
	}

	@Override
	public void h() {
		if (world == null) {
			return;
		}
		progress = progressCopy;
		if (progress >= 1.0f) {
			this.moveEntities(1.0f, 0.25f);
			world.p(x, y, z);
			s();
			if (world.getType(x, y, z) == Blocks.PISTON_MOVING) {
				world.setTypeAndData(x, y, z, block, blockData, 3);
				world.e(x, y, z, block);
			}
		} else {
			progressCopy += 0.5f;
			if (progressCopy >= 1.0f) {
				progressCopy = 1.0f;
			}
			if (extending) {
				this.moveEntities(progressCopy, (progressCopy - progress) + 0.0625f);
			}
		}
	}

	@Override
	public void a(final NBTTagCompound nbttagcompound) {
		super.a(nbttagcompound);
		block = Block.getById(nbttagcompound.getInt("blockId"));
		blockData = nbttagcompound.getInt("blockData");
		face = nbttagcompound.getInt("facing");
		float progressData = nbttagcompound.getFloat("progress");
		progressCopy = progressData;
		progress = progressData;
		extending = nbttagcompound.getBoolean("extending");
	}

	@Override
	public void b(final NBTTagCompound nbttagcompound) {
		super.b(nbttagcompound);
		nbttagcompound.setInt("blockId", Block.getId(block));
		nbttagcompound.setInt("blockData", blockData);
		nbttagcompound.setInt("facing", face);
		nbttagcompound.setFloat("progress", progress);
		nbttagcompound.setBoolean("extending", extending);
	}

}
