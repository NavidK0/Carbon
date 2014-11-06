package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import net.minecraft.server.v1_7_R4.StructureBoundingBox;
import net.minecraft.server.v1_7_R4.World;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

public class WorldGenMonumentDoubleZRoom extends WorldGenMonumentPiece {

	public WorldGenMonumentDoubleZRoom() {
	}

	public WorldGenMonumentDoubleZRoom(BlockFace face, WorldGenMonumentRoomDefinition definition, Random random) {
		super(1, face, definition, 1, 1, 2);
	}

	public boolean a(World world, Random random, StructureBoundingBox bb) {
		WorldGenMonumentRoomDefinition var4 = this.definition.b[BlockFace.NORTH.getId()];
		WorldGenMonumentRoomDefinition var5 = this.definition;
		if (this.definition.id / 25 > 0) {
			this.a(world, bb, 0, 8, var4.c[BlockFace.DOWN.getId()]);
			this.a(world, bb, 0, 0, var5.c[BlockFace.DOWN.getId()]);
		}

		if (var5.b[BlockFace.UP.getId()] == null) {
			this.a(world, bb, 1, 4, 1, 6, 4, 7, blockStatePrismarineRough);
		}

		if (var4.b[BlockFace.UP.getId()] == null) {
			this.a(world, bb, 1, 4, 8, 6, 4, 14, blockStatePrismarineRough);
		}

		this.a(world, bb, 0, 3, 0, 0, 3, 15, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 7, 3, 0, 7, 3, 15, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 3, 0, 7, 3, 0, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 3, 15, 6, 3, 15, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 0, 2, 0, 0, 2, 15, blockStatePrismarineRough, blockStatePrismarineRough, false);
		this.a(world, bb, 7, 2, 0, 7, 2, 15, blockStatePrismarineRough, blockStatePrismarineRough, false);
		this.a(world, bb, 1, 2, 0, 7, 2, 0, blockStatePrismarineRough, blockStatePrismarineRough, false);
		this.a(world, bb, 1, 2, 15, 6, 2, 15, blockStatePrismarineRough, blockStatePrismarineRough, false);
		this.a(world, bb, 0, 1, 0, 0, 1, 15, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 7, 1, 0, 7, 1, 15, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 1, 0, 7, 1, 0, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 1, 15, 6, 1, 15, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 1, 1, 1, 1, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 6, 1, 1, 6, 1, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 3, 1, 1, 3, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 6, 3, 1, 6, 3, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 1, 13, 1, 1, 14, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 6, 1, 13, 6, 1, 14, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 3, 13, 1, 3, 14, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 6, 3, 13, 6, 3, 14, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 2, 1, 6, 2, 3, 6, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 5, 1, 6, 5, 3, 6, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 2, 1, 9, 2, 3, 9, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 5, 1, 9, 5, 3, 9, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 3, 2, 6, 4, 2, 6, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 3, 2, 9, 4, 2, 9, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 2, 2, 7, 2, 2, 8, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 5, 2, 7, 5, 2, 8, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, blockStateSealantern, 2, 2, 5, bb);
		this.a(world, blockStateSealantern, 5, 2, 5, bb);
		this.a(world, blockStateSealantern, 2, 2, 10, bb);
		this.a(world, blockStateSealantern, 5, 2, 10, bb);
		this.a(world, blockStatePrismarineBricks, 2, 3, 5, bb);
		this.a(world, blockStatePrismarineBricks, 5, 3, 5, bb);
		this.a(world, blockStatePrismarineBricks, 2, 3, 10, bb);
		this.a(world, blockStatePrismarineBricks, 5, 3, 10, bb);
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

		return true;
	}
}
