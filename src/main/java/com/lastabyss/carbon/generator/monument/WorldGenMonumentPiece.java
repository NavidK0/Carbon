package com.lastabyss.carbon.generator.monument;

import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.StructureBoundingBox;
import net.minecraft.server.v1_7_R4.StructurePiece;
import net.minecraft.server.v1_7_R4.World;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.entity.EntityGuardian;
import com.lastabyss.carbon.utils.nmsclasses.BlockFace;
import com.lastabyss.carbon.utils.nmsclasses.IBlockState;

public abstract class WorldGenMonumentPiece extends StructurePiece {

	protected static final IBlockState blockStatePrismarineRough = new IBlockState(Carbon.injector().prismarineBlock, 0);
	protected static final IBlockState blockStatePrismarineBricks = new IBlockState(Carbon.injector().prismarineBlock, 1);
	protected static final IBlockState blockStatePrismarineDark = new IBlockState(Carbon.injector().prismarineBlock, 2);
	protected static final IBlockState blockStatePrismarineBricksCopy = blockStatePrismarineBricks;
	protected static final IBlockState blockStateSealantern = new IBlockState(Carbon.injector().seaLanternBlock);
	protected static final IBlockState blockStateWater = new IBlockState(Blocks.WATER);
	protected static final int defg = a(2, 0, 0);
	protected static final int defh = a(2, 2, 0);
	protected static final int defi = a(0, 1, 0);
	protected static final int defj = a(4, 1, 0);
	protected WorldGenMonumentRoomDefinition definition;

	protected static final int a(int var0, int var1, int var2) {
		return var1 * 25 + var2 * 5 + var0;
	}

	public WorldGenMonumentPiece() {
		super(0);
	}

	public WorldGenMonumentPiece(int var1) {
		super(var1);
	}

	public WorldGenMonumentPiece(BlockFace face, StructureBoundingBox bb) {
		super(1);
		this.g = face.toDirection();
		this.f = bb;
	}

	protected WorldGenMonumentPiece(int gd, BlockFace face, WorldGenMonumentRoomDefinition definition, int defX, int defY, int defZ) {
		super(gd);
		this.g = face.toDirection();
		this.definition = definition;
		int id = definition.id;
		int i1 = id % 5;
		int i2 = id / 5 % 5;
		int i3 = id / 25;
		if (face != BlockFace.NORTH && face != BlockFace.SOUTH) {
			this.f = new StructureBoundingBox(0, 0, 0, defZ * 8 - 1, defY * 4 - 1, defX * 8 - 1);
		} else {
			this.f = new StructureBoundingBox(0, 0, 0, defX * 8 - 1, defY * 4 - 1, defZ * 8 - 1);
		}

		switch (face) {
			case NORTH:
				this.f.a(i1 * 8, i3 * 4, -(i2 + defZ) * 8 + 1);
				break;
			case SOUTH:
				this.f.a(i1 * 8, i3 * 4, i2 * 8);
				break;
			case WEST:
				this.f.a(-(i2 + defZ) * 8 + 1, i3 * 4, i1 * 8);
				break;
			default:
				this.f.a(i2 * 8, i3 * 4, i1 * 8);
		}

	}

	@Override
	protected void a(NBTTagCompound var1) {
	}

	@Override
	protected void b(NBTTagCompound var1) {
	}

	protected void a(World world, StructureBoundingBox bb, int var3, int var4, boolean var5) {
		if (var5) {
			this.a(world, bb, var3 + 0, 0, var4 + 0, var3 + 2, 0, var4 + 8 - 1, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, var3 + 5, 0, var4 + 0, var3 + 8 - 1, 0, var4 + 8 - 1, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, var3 + 3, 0, var4 + 0, var3 + 4, 0, var4 + 2, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, var3 + 3, 0, var4 + 5, var3 + 4, 0, var4 + 8 - 1, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, var3 + 3, 0, var4 + 2, var3 + 4, 0, var4 + 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, var3 + 3, 0, var4 + 5, var3 + 4, 0, var4 + 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, var3 + 2, 0, var4 + 3, var3 + 2, 0, var4 + 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, var3 + 5, 0, var4 + 3, var3 + 5, 0, var4 + 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		} else {
			this.a(world, bb, var3 + 0, 0, var4 + 0, var3 + 8 - 1, 0, var4 + 8 - 1, blockStatePrismarineRough, blockStatePrismarineRough, false);
		}

	}

	protected void a(World world, StructureBoundingBox bb, int var3, int var4, int var5, int var6, int var7, int var8, IBlockState blockState) {
		for (int var10 = var4; var10 <= var7; ++var10) {
			for (int var11 = var3; var11 <= var6; ++var11) {
				for (int var12 = var5; var12 <= var8; ++var12) {
					if (this.a(world, var11, var10, var12, bb) == blockStateWater.getBlock()) {
						this.a(world, blockState.getBlock(), blockState.getData(), var11, var10, var12, bb);
					}
				}
			}
		}
	}

	protected boolean a(StructureBoundingBox bb, int var2, int var3, int var4, int var5) {
		int var6 = this.a(var2, var3);
		int var7 = this.b(var2, var3);
		int var8 = this.a(var4, var5);
		int var9 = this.b(var4, var5);
		return bb.a(Math.min(var6, var8), Math.min(var7, var9), Math.max(var6, var8), Math.max(var7, var9));
	}

	protected boolean a(World world, StructureBoundingBox bb, int var3, int var4, int var5) {
		int var6 = this.a(var3, var5);
		int var7 = this.a(var4);
		int var8 = this.b(var3, var5);
		if (bb.b(var6, var7, var8)) {
			EntityGuardian guardian = new EntityGuardian(world);
			guardian.setElder(true);
			guardian.setHealth(guardian.getMaxHealth());
			guardian.setPositionRotation((double) var6 + 0.5D, (double) var7, (double) var8 + 0.5D, 0.0F, 0.0F);
			guardian.prepare(null);
			world.addEntity(guardian);
			return true;
		} else {
			return false;
		}
	}

	//wrapper methods

	protected void a(World world, StructureBoundingBox bb, int i1, int i2, int i3, int i4, int i5, int i6, IBlockState blockState1, IBlockState blockState2, boolean b) {
		a(world, bb, i1, i2, i3, i4, i5, i6, blockState1.getBlock(), blockState1.getData(), blockState2.getBlock(), blockState2.getData(), b);
	}

	protected void a(World world, IBlockState blockState, int i1, int i2, int i3, StructureBoundingBox bb) {
		a(world, blockState.getBlock(), blockState.getData(), i1, i2, i3, bb);
	}

	protected void b(World world, IBlockState blockState, int i1, int i2, int i3, StructureBoundingBox bb) {
		b(world, blockState.getBlock(), blockState.getData(), i1, i2, i3, bb);
	}

	protected StructureBoundingBox getBoundingBox() {
		return c();
	}

}
