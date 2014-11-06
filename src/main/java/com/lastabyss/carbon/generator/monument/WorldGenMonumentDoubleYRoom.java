package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import net.minecraft.server.v1_7_R4.StructureBoundingBox;
import net.minecraft.server.v1_7_R4.World;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

public class WorldGenMonumentDoubleYRoom extends WorldGenMonumentPiece {

	public WorldGenMonumentDoubleYRoom() {
	}

	public WorldGenMonumentDoubleYRoom(BlockFace face, WorldGenMonumentRoomDefinition definition, Random random) {
		super(1, face, definition, 1, 2, 1);
	}

	public boolean a(World world, Random random, StructureBoundingBox bb) {
		if (this.definition.id / 25 > 0) {
			this.a(world, bb, 0, 0, this.definition.c[BlockFace.DOWN.getId()]);
		}

		WorldGenMonumentRoomDefinition var4 = this.definition.b[BlockFace.UP.getId()];
		if (var4.b[BlockFace.UP.getId()] == null) {
			this.a(world, bb, 1, 8, 1, 6, 8, 6, blockStatePrismarineRough);
		}

		this.a(world, bb, 0, 4, 0, 0, 4, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 7, 4, 0, 7, 4, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 4, 0, 6, 4, 0, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 4, 7, 6, 4, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 2, 4, 1, 2, 4, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 4, 2, 1, 4, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 5, 4, 1, 5, 4, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 6, 4, 2, 6, 4, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 2, 4, 5, 2, 4, 6, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 1, 4, 5, 1, 4, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 5, 4, 5, 5, 4, 6, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		this.a(world, bb, 6, 4, 5, 6, 4, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
		WorldGenMonumentRoomDefinition var5 = this.definition;

		for (int var6 = 1; var6 <= 5; var6 += 4) {
			byte var7 = 0;
			if (var5.c[BlockFace.SOUTH.getId()]) {
				this.a(world, bb, 2, var6, var7, 2, var6 + 2, var7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 5, var6, var7, 5, var6 + 2, var7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 3, var6 + 2, var7, 4, var6 + 2, var7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			} else {
				this.a(world, bb, 0, var6, var7, 7, var6 + 2, var7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 0, var6 + 1, var7, 7, var6 + 1, var7, blockStatePrismarineRough, blockStatePrismarineRough, false);
			}

			var7 = 7;
			if (var5.c[BlockFace.NORTH.getId()]) {
				this.a(world, bb, 2, var6, var7, 2, var6 + 2, var7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 5, var6, var7, 5, var6 + 2, var7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 3, var6 + 2, var7, 4, var6 + 2, var7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			} else {
				this.a(world, bb, 0, var6, var7, 7, var6 + 2, var7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 0, var6 + 1, var7, 7, var6 + 1, var7, blockStatePrismarineRough, blockStatePrismarineRough, false);
			}

			byte var8 = 0;
			if (var5.c[BlockFace.WEST.getId()]) {
				this.a(world, bb, var8, var6, 2, var8, var6 + 2, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, var8, var6, 5, var8, var6 + 2, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, var8, var6 + 2, 3, var8, var6 + 2, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			} else {
				this.a(world, bb, var8, var6, 0, var8, var6 + 2, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, var8, var6 + 1, 0, var8, var6 + 1, 7, blockStatePrismarineRough, blockStatePrismarineRough, false);
			}

			var8 = 7;
			if (var5.c[BlockFace.EAST.getId()]) {
				this.a(world, bb, var8, var6, 2, var8, var6 + 2, 2, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, var8, var6, 5, var8, var6 + 2, 5, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, var8, var6 + 2, 3, var8, var6 + 2, 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			} else {
				this.a(world, bb, var8, var6, 0, var8, var6 + 2, 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, var8, var6 + 1, 0, var8, var6 + 1, 7, blockStatePrismarineRough, blockStatePrismarineRough, false);
			}

			var5 = var4;
		}

		return true;
	}
}
