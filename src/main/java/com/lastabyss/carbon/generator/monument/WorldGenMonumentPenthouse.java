package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

import net.minecraft.server.v1_7_R4.StructureBoundingBox;
import net.minecraft.server.v1_7_R4.World;

public class WorldGenMonumentPenthouse extends WorldGenMonumentPiece {

	public WorldGenMonumentPenthouse() {
	}

	public WorldGenMonumentPenthouse(BlockFace face, StructureBoundingBox bb) {
		super(face, bb);
	}

	public boolean a(World world, Random random, StructureBoundingBox bb) {
		this.a(world, bb, 2, -1, 2, 11, -1, 11, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 0, -1, 0, 1, -1, 11, blockStatePrismarineRough, blockStatePrismarineRough, false);
		this.a(world, bb, 12, -1, 0, 13, -1, 11, blockStatePrismarineRough, blockStatePrismarineRough, false);
		this.a(world, bb, 2, -1, 0, 11, -1, 1, blockStatePrismarineRough, blockStatePrismarineRough, false);
		this.a(world, bb, 2, -1, 12, 11, -1, 13, blockStatePrismarineRough, blockStatePrismarineRough, false);
		this.a(world, bb, 0, 0, 0, 0, 0, 13, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 13, 0, 0, 13, 0, 13, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 0, 0, 12, 0, 0, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 0, 13, 12, 0, 13, blockStatePrismarineBricks, blockStatePrismarineBricks, false);

		for (int var4 = 2; var4 <= 11; var4 += 3) {
			this.a(world, blockStateSealantern, 0, 0, var4, bb);
			this.a(world, blockStateSealantern, 13, 0, var4, bb);
			this.a(world, blockStateSealantern, var4, 0, 0, bb);
		}

		this.a(world, bb, 2, 0, 3, 4, 0, 9, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 9, 0, 3, 11, 0, 9, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 4, 0, 9, 9, 0, 11, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, blockStatePrismarineBricks, 5, 0, 8, bb);
		this.a(world, blockStatePrismarineBricks, 8, 0, 8, bb);
		this.a(world, blockStatePrismarineBricks, 10, 0, 10, bb);
		this.a(world, blockStatePrismarineBricks, 3, 0, 10, bb);
		this.a(world, bb, 3, 0, 3, 3, 0, 7, blockStatePrismarineDark, blockStatePrismarineDark, false);
		this.a(world, bb, 10, 0, 3, 10, 0, 7, blockStatePrismarineDark, blockStatePrismarineDark, false);
		this.a(world, bb, 6, 0, 10, 7, 0, 10, blockStatePrismarineDark, blockStatePrismarineDark, false);
		byte var7 = 3;

		for (int var5 = 0; var5 < 2; ++var5) {
			for (int var6 = 2; var6 <= 8; var6 += 3) {
				this.a(world, bb, var7, 0, var6, var7, 2, var6, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			var7 = 10;
		}

		this.a(world, bb, 5, 0, 10, 5, 2, 10, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 8, 0, 10, 8, 2, 10, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 6, -1, 7, 7, -1, 8, blockStatePrismarineDark, blockStatePrismarineDark, false);
		this.a(world, bb, 6, -1, 3, 7, -1, 4, blockStateWater, blockStateWater, false);
		this.a(world, bb, 6, 1, 6);
		return true;
	}
}
