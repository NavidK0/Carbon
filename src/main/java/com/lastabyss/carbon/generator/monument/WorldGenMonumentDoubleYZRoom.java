package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import net.minecraft.server.v1_7_R4.StructureBoundingBox;
import net.minecraft.server.v1_7_R4.World;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;
import com.lastabyss.carbon.utils.nmsclasses.IBlockState;

public class WorldGenMonumentDoubleYZRoom extends WorldGenMonumentPiece {

	public WorldGenMonumentDoubleYZRoom() {
	}

	public WorldGenMonumentDoubleYZRoom(BlockFace face, WorldGenMonumentRoomDefinition definition, Random random) {
		super(1, face, definition, 1, 2, 2);
	}

	public boolean a(World world, Random random, StructureBoundingBox bb) {
		WorldGenMonumentRoomDefinition var4 = this.definition.b[BlockFace.NORTH.getId()];
		WorldGenMonumentRoomDefinition var5 = this.definition;
		WorldGenMonumentRoomDefinition var6 = var4.b[BlockFace.UP.getId()];
		WorldGenMonumentRoomDefinition var7 = var5.b[BlockFace.UP.getId()];
		if (this.definition.id / 25 > 0) {
			this.a(world, bb, 0, 8, var4.c[BlockFace.DOWN.getId()]);
			this.a(world, bb, 0, 0, var5.c[BlockFace.DOWN.getId()]);
		}

		if (var7.b[BlockFace.UP.getId()] == null) {
			this.a(world, bb, 1, 8, 1, 6, 8, 7, blockStatePrismarineRough);
		}

		if (var6.b[BlockFace.UP.getId()] == null) {
			this.a(world, bb, 1, 8, 8, 6, 8, 14, blockStatePrismarineRough);
		}

		int var8;
		IBlockState var9;
		for (var8 = 1; var8 <= 7; ++var8) {
			var9 = blockStatePrismarineBricks;
			if (var8 == 2 || var8 == 6) {
				var9 = blockStatePrismarineRough;
			}

			this.a(world, bb, 0, var8, 0, 0, var8, 15, var9, var9, false);
			this.a(world, bb, 7, var8, 0, 7, var8, 15, var9, var9, false);
			this.a(world, bb, 1, var8, 0, 6, var8, 0, var9, var9, false);
			this.a(world, bb, 1, var8, 15, 6, var8, 15, var9, var9, false);
		}

		for (var8 = 1; var8 <= 7; ++var8) {
			var9 = blockStatePrismarineDark;
			if (var8 == 2 || var8 == 6) {
				var9 = blockStateSealantern;
			}

			this.a(world, bb, 3, var8, 7, 4, var8, 8, var9, var9, false);
		}

		if (var5.c[BlockFace.SOUTH.getId()]) {
			this.a(world, bb, 3, 1, 0, 4, 2, 0, blockStateWater, blockStateWater, false);
		}

		if (var5.c[BlockFace.EAST.getId()]) {
			this.a(world, bb, 7, 1, 3, 7, 2, 4, blockStateWater, blockStateWater, false);
		}

		if (var5.c[BlockFace.WEST.getId()]) {
			this.a(world, bb, 0, 1, 3, 0, 2, 4, blockStateWater, blockStateWater, false);
		}

		if (var4.c[BlockFace.NORTH.getId()]) {
			this.a(world, bb, 3, 1, 15, 4, 2, 15, blockStateWater, blockStateWater, false);
		}

		if (var4.c[BlockFace.WEST.getId()]) {
			this.a(world, bb, 0, 1, 11, 0, 2, 12, blockStateWater, blockStateWater, false);
		}

		if (var4.c[BlockFace.EAST.getId()]) {
			this.a(world, bb, 7, 1, 11, 7, 2, 12, blockStateWater, blockStateWater, false);
		}

		if (var7.c[BlockFace.SOUTH.getId()]) {
			this.a(world, bb, 3, 5, 0, 4, 6, 0, blockStateWater, blockStateWater, false);
		}

		if (var7.c[BlockFace.EAST.getId()]) {
			this.a(world, bb, 7, 5, 3, 7, 6, 4, blockStateWater, blockStateWater, false);
			this.a(world, bb, 5, 4, 2, 6, 4, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 6, 1, 2, 6, 3, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 6, 1, 5, 6, 3, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		}

		if (var7.c[BlockFace.WEST.getId()]) {
			this.a(world, bb, 0, 5, 3, 0, 6, 4, blockStateWater, blockStateWater, false);
			this.a(world, bb, 1, 4, 2, 2, 4, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 1, 1, 2, 1, 3, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 1, 1, 5, 1, 3, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		}

		if (var6.c[BlockFace.NORTH.getId()]) {
			this.a(world, bb, 3, 5, 15, 4, 6, 15, blockStateWater, blockStateWater, false);
		}

		if (var6.c[BlockFace.WEST.getId()]) {
			this.a(world, bb, 0, 5, 11, 0, 6, 12, blockStateWater, blockStateWater, false);
			this.a(world, bb, 1, 4, 10, 2, 4, 13, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 1, 1, 10, 1, 3, 10, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 1, 1, 13, 1, 3, 13, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		}

		if (var6.c[BlockFace.EAST.getId()]) {
			this.a(world, bb, 7, 5, 11, 7, 6, 12, blockStateWater, blockStateWater, false);
			this.a(world, bb, 5, 4, 10, 6, 4, 13, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 6, 1, 10, 6, 3, 10, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 6, 1, 13, 6, 3, 13, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		}

		return true;
	}
}
