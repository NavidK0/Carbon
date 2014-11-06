package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import net.minecraft.server.v1_7_R4.StructureBoundingBox;
import net.minecraft.server.v1_7_R4.World;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;
import com.lastabyss.carbon.utils.nmsclasses.IBlockState;

public class WorldGenMonumentDoubleXYRoom extends WorldGenMonumentPiece {

	public WorldGenMonumentDoubleXYRoom() {
	}

	public WorldGenMonumentDoubleXYRoom(BlockFace face, WorldGenMonumentRoomDefinition definition, Random random) {
		super(1, face, definition, 2, 2, 1);
	}

	public boolean a(World world, Random random, StructureBoundingBox bb) {
		WorldGenMonumentRoomDefinition var4 = this.definition.b[BlockFace.EAST.getId()];
		WorldGenMonumentRoomDefinition var5 = this.definition;
		WorldGenMonumentRoomDefinition var6 = var5.b[BlockFace.UP.getId()];
		WorldGenMonumentRoomDefinition var7 = var4.b[BlockFace.UP.getId()];
		if (this.definition.id / 25 > 0) {
			this.a(world, bb, 8, 0, var4.c[BlockFace.DOWN.getId()]);
			this.a(world, bb, 0, 0, var5.c[BlockFace.DOWN.getId()]);
		}

		if (var6.b[BlockFace.UP.getId()] == null) {
			this.a(world, bb, 1, 8, 1, 7, 8, 6, blockStatePrismarineRough);
		}

		if (var7.b[BlockFace.UP.getId()] == null) {
			this.a(world, bb, 8, 8, 1, 14, 8, 6, blockStatePrismarineRough);
		}

		for (int var8 = 1; var8 <= 7; ++var8) {
			IBlockState var9 = blockStatePrismarineBricks;
			if (var8 == 2 || var8 == 6) {
				var9 = blockStatePrismarineRough;
			}

			this.a(world, bb, 0, var8, 0, 0, var8, 7, var9, var9, false);
			this.a(world, bb, 15, var8, 0, 15, var8, 7, var9, var9, false);
			this.a(world, bb, 1, var8, 0, 15, var8, 0, var9, var9, false);
			this.a(world, bb, 1, var8, 7, 14, var8, 7, var9, var9, false);
		}

		this.a(world, bb, 2, 1, 3, 2, 7, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 3, 1, 2, 4, 7, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 3, 1, 5, 4, 7, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 13, 1, 3, 13, 7, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 11, 1, 2, 12, 7, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 11, 1, 5, 12, 7, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 5, 1, 3, 5, 3, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 10, 1, 3, 10, 3, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 5, 7, 2, 10, 7, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 5, 5, 2, 5, 7, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 10, 5, 2, 10, 7, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 5, 5, 5, 5, 7, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 10, 5, 5, 10, 7, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, blockStatePrismarineBricks, 6, 6, 2, bb);
		this.a(world, blockStatePrismarineBricks, 9, 6, 2, bb);
		this.a(world, blockStatePrismarineBricks, 6, 6, 5, bb);
		this.a(world, blockStatePrismarineBricks, 9, 6, 5, bb);
		this.a(world, bb, 5, 4, 3, 6, 4, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 9, 4, 3, 10, 4, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, blockStateSealantern, 5, 4, 2, bb);
		this.a(world, blockStateSealantern, 5, 4, 5, bb);
		this.a(world, blockStateSealantern, 10, 4, 2, bb);
		this.a(world, blockStateSealantern, 10, 4, 5, bb);
		if (var5.c[BlockFace.SOUTH.getId()]) {
			this.a(world, bb, 3, 1, 0, 4, 2, 0, blockStateWater, blockStateWater, false);
		}

		if (var5.c[BlockFace.NORTH.getId()]) {
			this.a(world, bb, 3, 1, 7, 4, 2, 7, blockStateWater, blockStateWater, false);
		}

		if (var5.c[BlockFace.WEST.getId()]) {
			this.a(world, bb, 0, 1, 3, 0, 2, 4, blockStateWater, blockStateWater, false);
		}

		if (var4.c[BlockFace.SOUTH.getId()]) {
			this.a(world, bb, 11, 1, 0, 12, 2, 0, blockStateWater, blockStateWater, false);
		}

		if (var4.c[BlockFace.NORTH.getId()]) {
			this.a(world, bb, 11, 1, 7, 12, 2, 7, blockStateWater, blockStateWater, false);
		}

		if (var4.c[BlockFace.EAST.getId()]) {
			this.a(world, bb, 15, 1, 3, 15, 2, 4, blockStateWater, blockStateWater, false);
		}

		if (var6.c[BlockFace.SOUTH.getId()]) {
			this.a(world, bb, 3, 5, 0, 4, 6, 0, blockStateWater, blockStateWater, false);
		}

		if (var6.c[BlockFace.NORTH.getId()]) {
			this.a(world, bb, 3, 5, 7, 4, 6, 7, blockStateWater, blockStateWater, false);
		}

		if (var6.c[BlockFace.WEST.getId()]) {
			this.a(world, bb, 0, 5, 3, 0, 6, 4, blockStateWater, blockStateWater, false);
		}

		if (var7.c[BlockFace.SOUTH.getId()]) {
			this.a(world, bb, 11, 5, 0, 12, 6, 0, blockStateWater, blockStateWater, false);
		}

		if (var7.c[BlockFace.NORTH.getId()]) {
			this.a(world, bb, 11, 5, 7, 12, 6, 7, blockStateWater, blockStateWater, false);
		}

		if (var7.c[BlockFace.EAST.getId()]) {
			this.a(world, bb, 15, 5, 3, 15, 6, 4, blockStateWater, blockStateWater, false);
		}

		return true;
	}
}
