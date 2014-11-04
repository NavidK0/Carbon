package com.lastabyss.carbon.blocks;

import java.util.List;
import com.lastabyss.carbon.entity.EntityItemFrame;

import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.BlockDirectional;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.Direction;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.IEntitySelector;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.World;

public class BlockRedstoneComparator extends net.minecraft.server.v1_7_R4.BlockRedstoneComparator {

	public BlockRedstoneComparator(boolean on) {
		super(on);
		c(0.0F);
		a(f);
		c("comparator");
		H();
		if (on) {
			a(0.625F);
			d("comparator_on");
		} else {
			d("comparator_off");
		}
	}

	private int getBlockDiodeAbstractPower(World world, int i, int j, int k, int l) {
        final int i2 = BlockDirectional.l(l);
        final int j2 = i + Direction.a[i2];
        final int k2 = k + Direction.b[i2];
        final int l2 = world.getBlockFacePower(j2, j, k2, Direction.d[i2]);
        return (l2 >= 15) ? l2 : Math.max(l2, (world.getType(j2, j, k2) == Blocks.REDSTONE_WIRE) ? world.getData(j2, j, k2) : 0);
	}

	@Override
	protected int h(World world, int x, int y, int z, int face) {
		int power = getBlockDiodeAbstractPower(world, x, y, z, face);
		final int direction = l(face);
		int realX = x + Direction.a[direction];
		int realZ = z + Direction.b[direction];
		Block block = world.getType(realX, y, realZ);
		if (block.isComplexRedstone()) {
			power = block.g(world, realX, y, realZ, Direction.f[direction]);
		} else if ((power < 15) && (block.r())) {
			realX += Direction.a[direction];
			realZ += Direction.b[direction];

			block = world.getType(realX, y, realZ);
			if (block.isComplexRedstone()) {
				power = block.g(world, realX, y, realZ, Direction.f[direction]);
			} else if (block.getMaterial() == Material.AIR) {
				@SuppressWarnings("unchecked")
				List<EntityItemFrame> entities = world.a(
					EntityItemFrame.class,
					AxisAlignedBB.a(realX, y, realZ, realX + 1, y + 1, realZ + 1),
					new IEntitySelector() {
						@Override
						public boolean a(Entity entity) {
							int framedirection = (MathHelper.floor((entity.yaw / 90.0F) + 0.5D) & 3);
							if (framedirection == 0) {
								framedirection = 2;
							} else if (framedirection == 2) {
								framedirection = 0;
							}
							return framedirection == direction;
						}
					}
				);
				if (entities.size() == 1) {
					power = ((EntityItemFrame) entities.get(0)).getRedstonePower();
				}
			}
		}
		return power;
	}

}
