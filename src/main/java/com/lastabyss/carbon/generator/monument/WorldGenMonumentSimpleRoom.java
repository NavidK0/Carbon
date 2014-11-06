package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import net.minecraft.server.v1_7_R4.StructureBoundingBox;
import net.minecraft.server.v1_7_R4.World;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

public class WorldGenMonumentSimpleRoom extends WorldGenMonumentPiece {

	private int o;

	public WorldGenMonumentSimpleRoom() {
	}

	public WorldGenMonumentSimpleRoom(BlockFace face, WorldGenMonumentRoomDefinition definition, Random random) {
		super(1, face, definition, 1, 1, 1);
		this.o = random.nextInt(3);
	}

	public boolean a(World world, Random random, StructureBoundingBox bb) {
		if (this.definition.id / 25 > 0) {
			this.a(world, bb, 0, 0, this.definition.c[BlockFace.DOWN.getId()]);
		}

		if (this.definition.b[BlockFace.UP.getId()] == null) {
			this.a(world, bb, 1, 4, 1, 6, 4, 6, blockStatePrismarineRough);
		}

		boolean var4 = this.o != 0 && random.nextBoolean() && !this.definition.c[BlockFace.DOWN.getId()] && !this.definition.c[BlockFace.UP.getId()] && this.definition.c() > 1;
		if (this.o == 0) {
			this.a(world, bb, 0, 1, 0, 2, 1, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 0, 3, 0, 2, 3, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 0, 2, 0, 0, 2, 2, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 1, 2, 0, 2, 2, 0, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, blockStateSealantern, 1, 2, 1, bb);
			this.a(world, bb, 5, 1, 0, 7, 1, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 5, 3, 0, 7, 3, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 7, 2, 0, 7, 2, 2, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 5, 2, 0, 6, 2, 0, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, blockStateSealantern, 6, 2, 1, bb);
			this.a(world, bb, 0, 1, 5, 2, 1, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 0, 3, 5, 2, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 0, 2, 5, 0, 2, 7, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 1, 2, 7, 2, 2, 7, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, blockStateSealantern, 1, 2, 6, bb);
			this.a(world, bb, 5, 1, 5, 7, 1, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 5, 3, 5, 7, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 7, 2, 5, 7, 2, 7, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 5, 2, 7, 6, 2, 7, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, blockStateSealantern, 6, 2, 6, bb);
			if (this.definition.c[BlockFace.SOUTH.getId()]) {
				this.a(world, bb, 3, 3, 0, 4, 3, 0, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			} else {
				this.a(world, bb, 3, 3, 0, 4, 3, 1, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 3, 2, 0, 4, 2, 0, blockStatePrismarineRough, blockStatePrismarineRough, false);
				this.a(world, bb, 3, 1, 0, 4, 1, 1, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			if (this.definition.c[BlockFace.NORTH.getId()]) {
				this.a(world, bb, 3, 3, 7, 4, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			} else {
				this.a(world, bb, 3, 3, 6, 4, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 3, 2, 7, 4, 2, 7, blockStatePrismarineRough, blockStatePrismarineRough, false);
				this.a(world, bb, 3, 1, 6, 4, 1, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			if (this.definition.c[BlockFace.WEST.getId()]) {
				this.a(world, bb, 0, 3, 3, 0, 3, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			} else {
				this.a(world, bb, 0, 3, 3, 1, 3, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 0, 2, 3, 0, 2, 4, blockStatePrismarineRough, blockStatePrismarineRough, false);
				this.a(world, bb, 0, 1, 3, 1, 1, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			if (this.definition.c[BlockFace.EAST.getId()]) {
				this.a(world, bb, 7, 3, 3, 7, 3, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			} else {
				this.a(world, bb, 6, 3, 3, 7, 3, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 7, 2, 3, 7, 2, 4, blockStatePrismarineRough, blockStatePrismarineRough, false);
				this.a(world, bb, 6, 1, 3, 7, 1, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}
		} else if (this.o == 1) {
			this.a(world, bb, 2, 1, 2, 2, 3, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 2, 1, 5, 2, 3, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 5, 1, 5, 5, 3, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 5, 1, 2, 5, 3, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, blockStateSealantern, 2, 2, 2, bb);
			this.a(world, blockStateSealantern, 2, 2, 5, bb);
			this.a(world, blockStateSealantern, 5, 2, 5, bb);
			this.a(world, blockStateSealantern, 5, 2, 2, bb);
			this.a(world, bb, 0, 1, 0, 1, 3, 0, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 0, 1, 1, 0, 3, 1, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 0, 1, 7, 1, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 0, 1, 6, 0, 3, 6, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 6, 1, 7, 7, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 7, 1, 6, 7, 3, 6, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 6, 1, 0, 7, 3, 0, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 7, 1, 1, 7, 3, 1, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, blockStatePrismarineRough, 1, 2, 0, bb);
			this.a(world, blockStatePrismarineRough, 0, 2, 1, bb);
			this.a(world, blockStatePrismarineRough, 1, 2, 7, bb);
			this.a(world, blockStatePrismarineRough, 0, 2, 6, bb);
			this.a(world, blockStatePrismarineRough, 6, 2, 7, bb);
			this.a(world, blockStatePrismarineRough, 7, 2, 6, bb);
			this.a(world, blockStatePrismarineRough, 6, 2, 0, bb);
			this.a(world, blockStatePrismarineRough, 7, 2, 1, bb);
			if (!this.definition.c[BlockFace.SOUTH.getId()]) {
				this.a(world, bb, 1, 3, 0, 6, 3, 0, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 1, 2, 0, 6, 2, 0, blockStatePrismarineRough, blockStatePrismarineRough, false);
				this.a(world, bb, 1, 1, 0, 6, 1, 0, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			if (!this.definition.c[BlockFace.NORTH.getId()]) {
				this.a(world, bb, 1, 3, 7, 6, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 1, 2, 7, 6, 2, 7, blockStatePrismarineRough, blockStatePrismarineRough, false);
				this.a(world, bb, 1, 1, 7, 6, 1, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			if (!this.definition.c[BlockFace.WEST.getId()]) {
				this.a(world, bb, 0, 3, 1, 0, 3, 6, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 0, 2, 1, 0, 2, 6, blockStatePrismarineRough, blockStatePrismarineRough, false);
				this.a(world, bb, 0, 1, 1, 0, 1, 6, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			if (!this.definition.c[BlockFace.EAST.getId()]) {
				this.a(world, bb, 7, 3, 1, 7, 3, 6, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 7, 2, 1, 7, 2, 6, blockStatePrismarineRough, blockStatePrismarineRough, false);
				this.a(world, bb, 7, 1, 1, 7, 1, 6, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}
		} else if (this.o == 2) {
			this.a(world, bb, 0, 1, 0, 0, 1, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 7, 1, 0, 7, 1, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 1, 1, 0, 6, 1, 0, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 1, 1, 7, 6, 1, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 0, 2, 0, 0, 2, 7, blockStatePrismarineDark, blockStatePrismarineDark, false);
			this.a(world, bb, 7, 2, 0, 7, 2, 7, blockStatePrismarineDark, blockStatePrismarineDark, false);
			this.a(world, bb, 1, 2, 0, 6, 2, 0, blockStatePrismarineDark, blockStatePrismarineDark, false);
			this.a(world, bb, 1, 2, 7, 6, 2, 7, blockStatePrismarineDark, blockStatePrismarineDark, false);
			this.a(world, bb, 0, 3, 0, 0, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 7, 3, 0, 7, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 1, 3, 0, 6, 3, 0, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 1, 3, 7, 6, 3, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 0, 1, 3, 0, 2, 4, blockStatePrismarineDark, blockStatePrismarineDark, false);
			this.a(world, bb, 7, 1, 3, 7, 2, 4, blockStatePrismarineDark, blockStatePrismarineDark, false);
			this.a(world, bb, 3, 1, 0, 4, 2, 0, blockStatePrismarineDark, blockStatePrismarineDark, false);
			this.a(world, bb, 3, 1, 7, 4, 2, 7, blockStatePrismarineDark, blockStatePrismarineDark, false);
			if (this.definition.c[BlockFace.SOUTH.getId()]) {
				this.a(world, bb, 3, 1, 0, 4, 2, 0, blockStateWater, blockStateWater, false);
			}

			if (this.definition.c[BlockFace.NORTH.getId()]) {
				this.a(world, bb, 3, 1, 7, 4, 2, 7, blockStateWater, blockStateWater, false);
			}

			if (this.definition.c[BlockFace.WEST.getId()]) {
				this.a(world, bb, 0, 1, 3, 0, 2, 4, blockStateWater, blockStateWater, false);
			}

			if (this.definition.c[BlockFace.EAST.getId()]) {
				this.a(world, bb, 7, 1, 3, 7, 2, 4, blockStateWater, blockStateWater, false);
			}
		}

		if (var4) {
			this.a(world, bb, 3, 1, 3, 4, 1, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 3, 2, 3, 4, 2, 4, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 3, 3, 3, 4, 3, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		}

		return true;
	}
}
