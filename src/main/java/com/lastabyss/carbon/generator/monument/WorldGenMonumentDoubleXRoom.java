package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import net.minecraft.server.v1_7_R4.StructureBoundingBox;
import net.minecraft.server.v1_7_R4.World;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

public class WorldGenMonumentDoubleXRoom extends WorldGenMonumentPiece {

	public WorldGenMonumentDoubleXRoom() {
	}

	public WorldGenMonumentDoubleXRoom(BlockFace face, WorldGenMonumentRoomDefinition definition, Random random) {
		super(1, face, definition, 2, 1, 1);
	}

	public boolean a(World world, Random random, StructureBoundingBox bb) {
		WorldGenMonumentRoomDefinition var4 = this.definition.b[BlockFace.EAST.getId()];
		WorldGenMonumentRoomDefinition var5 = this.definition;
		if (this.definition.id / 25 > 0) {
			this.a(world, bb, 8, 0, var4.c[BlockFace.DOWN.getId()]);
			this.a(world, bb, 0, 0, var5.c[BlockFace.DOWN.getId()]);
		}

		if (var5.b[BlockFace.UP.getId()] == null) {
			this.a(world, bb, 1, 4, 1, 7, 4, 6, blockStatePrismarineRough);
		}

		if (var4.b[BlockFace.UP.getId()] == null) {
			this.a(world, bb, 8, 4, 1, 14, 4, 6, blockStatePrismarineRough);
		}

		this.a(world, bb, 0, 3, 0, 0, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 15, 3, 0, 15, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 3, 0, 15, 3, 0, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 3, 7, 14, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 0, 2, 0, 0, 2, 7, blockStatePrismarineRough, blockStatePrismarineRough, false);
		this.a(world, bb, 15, 2, 0, 15, 2, 7, blockStatePrismarineRough, blockStatePrismarineRough, false);
		this.a(world, bb, 1, 2, 0, 15, 2, 0, blockStatePrismarineRough, blockStatePrismarineRough, false);
		this.a(world, bb, 1, 2, 7, 14, 2, 7, blockStatePrismarineRough, blockStatePrismarineRough, false);
		this.a(world, bb, 0, 1, 0, 0, 1, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 15, 1, 0, 15, 1, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 1, 0, 15, 1, 0, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 1, 7, 14, 1, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 5, 1, 0, 10, 1, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 6, 2, 0, 9, 2, 3, blockStatePrismarineRough, blockStatePrismarineRough, false);
		this.a(world, bb, 5, 3, 0, 10, 3, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, blockStateSealantern, 6, 2, 3, bb);
		this.a(world, blockStateSealantern, 9, 2, 3, bb);
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

		return true;
	}

}
