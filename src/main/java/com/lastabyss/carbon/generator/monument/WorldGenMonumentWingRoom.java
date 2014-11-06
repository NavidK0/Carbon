package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import net.minecraft.server.v1_7_R4.StructureBoundingBox;
import net.minecraft.server.v1_7_R4.World;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

public class WorldGenMonumentWingRoom extends WorldGenMonumentPiece {

	private int o;

	public WorldGenMonumentWingRoom() {
	}

	public WorldGenMonumentWingRoom(BlockFace face, StructureBoundingBox bb, int var3) {
		super(face, bb);
		this.o = var3 & 1;
	}

	public boolean a(World world, Random random, StructureBoundingBox bb) {
		if (this.o == 0) {
			int var4;
			for (var4 = 0; var4 < 4; ++var4) {
				this.a(world, bb, 10 - var4, 3 - var4, 20 - var4, 12 + var4, 3 - var4, 20, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			this.a(world, bb, 7, 0, 6, 15, 0, 16, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 6, 0, 6, 6, 3, 20, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 16, 0, 6, 16, 3, 20, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 7, 1, 7, 7, 1, 20, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 15, 1, 7, 15, 1, 20, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 7, 1, 6, 9, 3, 6, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 13, 1, 6, 15, 3, 6, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 8, 1, 7, 9, 1, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 13, 1, 7, 14, 1, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 9, 0, 5, 13, 0, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 10, 0, 7, 12, 0, 7, blockStatePrismarineDark, blockStatePrismarineDark, false);
			this.a(world, bb, 8, 0, 10, 8, 0, 12, blockStatePrismarineDark, blockStatePrismarineDark, false);
			this.a(world, bb, 14, 0, 10, 14, 0, 12, blockStatePrismarineDark, blockStatePrismarineDark, false);

			for (var4 = 18; var4 >= 7; var4 -= 3) {
				this.a(world, blockStateSealantern, 6, 3, var4, bb);
				this.a(world, blockStateSealantern, 16, 3, var4, bb);
			}

			this.a(world, blockStateSealantern, 10, 0, 10, bb);
			this.a(world, blockStateSealantern, 12, 0, 10, bb);
			this.a(world, blockStateSealantern, 10, 0, 12, bb);
			this.a(world, blockStateSealantern, 12, 0, 12, bb);
			this.a(world, blockStateSealantern, 8, 3, 6, bb);
			this.a(world, blockStateSealantern, 14, 3, 6, bb);
			this.a(world, blockStatePrismarineBricks, 4, 2, 4, bb);
			this.a(world, blockStateSealantern, 4, 1, 4, bb);
			this.a(world, blockStatePrismarineBricks, 4, 0, 4, bb);
			this.a(world, blockStatePrismarineBricks, 18, 2, 4, bb);
			this.a(world, blockStateSealantern, 18, 1, 4, bb);
			this.a(world, blockStatePrismarineBricks, 18, 0, 4, bb);
			this.a(world, blockStatePrismarineBricks, 4, 2, 18, bb);
			this.a(world, blockStateSealantern, 4, 1, 18, bb);
			this.a(world, blockStatePrismarineBricks, 4, 0, 18, bb);
			this.a(world, blockStatePrismarineBricks, 18, 2, 18, bb);
			this.a(world, blockStateSealantern, 18, 1, 18, bb);
			this.a(world, blockStatePrismarineBricks, 18, 0, 18, bb);
			this.a(world, blockStatePrismarineBricks, 9, 7, 20, bb);
			this.a(world, blockStatePrismarineBricks, 13, 7, 20, bb);
			this.a(world, bb, 6, 0, 21, 7, 4, 21, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 15, 0, 21, 16, 4, 21, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 11, 2, 16);
		} else if (this.o == 1) {
			this.a(world, bb, 9, 3, 18, 13, 3, 20, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 9, 0, 18, 9, 2, 18, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 13, 0, 18, 13, 2, 18, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			byte var8 = 9;
			byte var5 = 20;
			byte var6 = 5;

			int var7;
			for (var7 = 0; var7 < 2; ++var7) {
				this.a(world, blockStatePrismarineBricks, var8, var6 + 1, var5, bb);
				this.a(world, blockStateSealantern, var8, var6, var5, bb);
				this.a(world, blockStatePrismarineBricks, var8, var6 - 1, var5, bb);
				var8 = 13;
			}

			this.a(world, bb, 7, 3, 7, 15, 3, 14, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			var8 = 10;

			for (var7 = 0; var7 < 2; ++var7) {
				this.a(world, bb, var8, 0, 10, var8, 6, 10, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, var8, 0, 12, var8, 6, 12, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, blockStateSealantern, var8, 0, 10, bb);
				this.a(world, blockStateSealantern, var8, 0, 12, bb);
				this.a(world, blockStateSealantern, var8, 4, 10, bb);
				this.a(world, blockStateSealantern, var8, 4, 12, bb);
				var8 = 12;
			}

			var8 = 8;

			for (var7 = 0; var7 < 2; ++var7) {
				this.a(world, bb, var8, 0, 7, var8, 2, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, var8, 0, 14, var8, 2, 14, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				var8 = 14;
			}

			this.a(world, bb, 8, 3, 8, 8, 3, 13, blockStatePrismarineDark, blockStatePrismarineDark, false);
			this.a(world, bb, 14, 3, 8, 14, 3, 13, blockStatePrismarineDark, blockStatePrismarineDark, false);
			this.a(world, bb, 11, 5, 13);
		}

		return true;
	}
}
