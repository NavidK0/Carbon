package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import net.minecraft.server.v1_7_R4.StructureBoundingBox;
import net.minecraft.server.v1_7_R4.World;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

public class WorldGenMonumentPiece1 extends WorldGenMonumentPiece {

	public WorldGenMonumentPiece1() {
	}

	public WorldGenMonumentPiece1(BlockFace face, WorldGenMonumentRoomDefinition definition) {
		super(1, face, definition, 1, 1, 1);
	}

	public boolean a(World world, Random random, StructureBoundingBox bb) {
		this.a(world, bb, 0, 3, 0, 2, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 5, 3, 0, 7, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 0, 2, 0, 1, 2, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 6, 2, 0, 7, 2, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 0, 1, 0, 0, 1, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 7, 1, 0, 7, 1, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 0, 1, 7, 7, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 1, 0, 2, 3, 0, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 5, 1, 0, 6, 3, 0, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		if (this.definition.c[BlockFace.NORTH.getId()]) {
			this.a(world, bb, 3, 1, 7, 4, 2, 7, blockStateWater, blockStateWater, false);
		}

		if (this.definition.c[BlockFace.WEST.getId()]) {
			this.a(world, bb, 0, 1, 3, 1, 2, 4, blockStateWater, blockStateWater, false);
		}

		if (this.definition.c[BlockFace.EAST.getId()]) {
			this.a(world, bb, 6, 1, 3, 7, 2, 4, blockStateWater, blockStateWater, false);
		}

		return true;
	}
}
