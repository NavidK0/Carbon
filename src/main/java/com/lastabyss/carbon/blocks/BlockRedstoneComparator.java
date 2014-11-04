package com.lastabyss.carbon.blocks;

import java.util.List;
import java.util.Random;

import com.lastabyss.carbon.entity.EntityItemFrame;

import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.BlockDiodeAbstract;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.Direction;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.IBlockAccess;
import net.minecraft.server.v1_7_R4.IContainer;
import net.minecraft.server.v1_7_R4.IEntitySelector;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.TileEntity;
import net.minecraft.server.v1_7_R4.TileEntityComparator;
import net.minecraft.server.v1_7_R4.World;

public class BlockRedstoneComparator extends net.minecraft.server.v1_7_R4.BlockDiodeAbstract implements IContainer {

	public BlockRedstoneComparator(boolean on) {
		super(on);
		isTileEntity = true;
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

	@Override
	public Item getDropType(final int n, final Random random, final int n2) {
		return Items.REDSTONE_COMPARATOR;
	}

	@Override
	protected int b(final int n) {
		return 2;
	}

	@Override
	protected BlockDiodeAbstract e() {
		return Blocks.REDSTONE_COMPARATOR_ON;
	}

	@Override
	protected BlockDiodeAbstract i() {
		return Blocks.REDSTONE_COMPARATOR_OFF;
	}

	@Override
	public int b() {
		return 37;
	}

	@Override
	protected boolean c(final int n) {
		return this.a || (n & 0x8) != 0x0;
	}

	@Override
	protected int f(final IBlockAccess blockAccess, final int n, final int n2, final int n3, final int n4) {
		return this.e(blockAccess, n, n2, n3).a();
	}

	private int j(final World iblockaccess, final int i, final int j, final int k, final int l) {
		if (!this.d(l)) {
			return this.h(iblockaccess, i, j, k, l);
		}
		return Math.max(this.h(iblockaccess, i, j, k, l) - this.h((IBlockAccess) iblockaccess, i, j, k, l), 0);
	}

	public boolean d(final int n) {
		return (n & 0x4) == 0x4;
	}

	@Override
	protected boolean a(final World iblockaccess, final int i, final int j, final int k, final int l) {
		final int h = this.h(iblockaccess, i, j, k, l);
		if (h >= 15) {
			return true;
		}
		if (h == 0) {
			return false;
		}
		final int h2 = this.h((IBlockAccess) iblockaccess, i, j, k, l);
		return h2 == 0 || h >= h2;
	}

	@Override
	protected int h(World world, int x, int y, int z, int face) {
		int power = super.h(world, x, y, z, face);
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

	public TileEntityComparator e(final IBlockAccess blockAccess, final int x, final int y, final int z) {
		return (TileEntityComparator) blockAccess.getTileEntity(x, y, z);
	}

	@Override
	public boolean interact(final World world, final int x, final int y, final int z, final EntityHuman entityHuman, final int n4, final float n5, final float n6, final float n7) {
		final int data = world.getData(x, y, z);
		final boolean b = this.a | (data & 0x8) != 0x0;
		final boolean b2 = !this.d(data);
		final int n8 = (b2 ? 4 : 0) | (b ? 8 : 0);
		world.makeSound(x + 0.5, y + 0.5, z + 0.5, "random.click", 0.3f, b2 ? 0.55f : 0.5f);
		world.setData(x, y, z, n8 | (data & 0x3), 2);
		this.c(world, x, y, z, world.random);
		return true;
	}

	@Override
	protected void b(final World world, final int x, final int y, final int z, final Block block) {
		if (!world.a(x, y, z, this)) {
			final int data = world.getData(x, y, z);
			if (this.j(world, x, y, z, data) != this.e((IBlockAccess) world, x, y, z).a() || this.c(data) != this.a(world, x, y, z, data)) {
				if (this.i(world, x, y, z, data)) {
					world.a(x, y, z, this, this.b(0), -1);
				} else {
					world.a(x, y, z, this, this.b(0), 0);
				}
			}
		}
	}

	private void c(final World world, final int x, final int y, final int z, final Random random) {
		final int data = world.getData(x, y, z);
		final int j = this.j(world, x, y, z, data);
		final int a = this.e((IBlockAccess) world, x, y, z).a();
		this.e((IBlockAccess) world, x, y, z).a(j);
		if (a != j || !this.d(data)) {
			final boolean a2 = this.a(world, x, y, z, data);
			final boolean b = this.a || (data & 0x8) != 0x0;
			if (b && !a2) {
				world.setData(x, y, z, data & 0xFFFFFFF7, 2);
			} else if (!b && a2) {
				world.setData(x, y, z, data | 0x8, 2);
			}
			this.e(world, x, y, z);
		}
	}

	@Override
	public void a(final World world, final int x, final int y, final int z, final Random random) {
		if (this.a) {
			world.setTypeAndData(x, y, z, this.i(), world.getData(x, y, z) | 0x8, 4);
		}
		this.c(world, x, y, z, random);
	}

	@Override
	public void onPlace(final World world, final int x, final int y, final int z) {
		super.onPlace(world, x, y, z);
		world.setTileEntity(x, y, z, this.a(world, 0));
	}

	@Override
	public void remove(final World world, final int x, final int y, final int z, final Block block, final int l) {
		super.remove(world, x, y, z, block, l);
		world.p(x, y, z);
		this.e(world, x, y, z);
	}

	@Override
	public boolean a(final World world, final int x, final int y, final int z, final int n4, final int n5) {
		super.a(world, x, y, z, n4, n5);
		final TileEntity tileEntity = world.getTileEntity(x, y, z);
		return tileEntity != null && tileEntity.c(n4, n5);
	}

	@Override
	public TileEntity a(final World world, final int n) {
		return new TileEntityComparator();
	}

}
