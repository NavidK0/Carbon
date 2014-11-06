package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.StructureBoundingBox;
import net.minecraft.server.v1_7_R4.World;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;
import com.lastabyss.carbon.utils.nmsclasses.IBlockState;

public class WorldGenMonumentSimpleTopRoom extends WorldGenMonumentPiece {

	public WorldGenMonumentSimpleTopRoom() {
	}

	public WorldGenMonumentSimpleTopRoom(BlockFace var1, WorldGenMonumentRoomDefinition var2, Random var3) {
		super(1, var1, var2, 1, 1, 1);
	}

	public boolean a(World world, Random random, StructureBoundingBox bb) {
		if (this.definition.id / 25 > 0) {
			this.a(world, bb, 0, 0, this.definition.c[BlockFace.DOWN.getId()]);
		}

		if (this.definition.b[BlockFace.UP.getId()] == null) {
			this.a(world, bb, 1, 4, 1, 6, 4, 6, blockStatePrismarineRough);
		}

		for (int var4 = 1; var4 <= 6; ++var4) {
			for (int var5 = 1; var5 <= 6; ++var5) {
				if (random.nextInt(3) != 0) {
					int var6 = 2 + (random.nextInt(4) == 0 ? 0 : 1);
					this.a(world, bb, var4, var6, var5, var4, 3, var5, new IBlockState(Blocks.SPONGE, 1), new IBlockState(Blocks.SPONGE, 1), false);
				}
			}
		}

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

		return true;
	}

}
