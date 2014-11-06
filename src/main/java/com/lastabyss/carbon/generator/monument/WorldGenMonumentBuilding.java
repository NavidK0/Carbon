package com.lastabyss.carbon.generator.monument;

import com.google.common.collect.Lists;
import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_7_R4.StructureBoundingBox;
import net.minecraft.server.v1_7_R4.World;

public class WorldGenMonumentBuilding extends WorldGenMonumentPiece {

	private WorldGenMonumentRoomDefinition o;
	private WorldGenMonumentRoomDefinition p;
	private List<WorldGenMonumentPiece> pieces = Lists.newArrayList();

	public WorldGenMonumentBuilding() {
	}

	public WorldGenMonumentBuilding(Random random, int var2, int var3, BlockFace face) {
		super(0);
		this.g = face.toDirection();
		switch (face) {
			case NORTH:
			case SOUTH:
				this.f = new StructureBoundingBox(var2, 39, var3, var2 + 58 - 1, 61, var3 + 58 - 1);
				break;
			default:
				this.f = new StructureBoundingBox(var2, 39, var3, var2 + 58 - 1, 61, var3 + 58 - 1);
		}

		List<WorldGenMonumentRoomDefinition> roomDefinitions = getRoomDefinitions(random);

		this.o.d = true;
		this.pieces.add(new WorldGenMonumentPiece1(BlockFace.fromDirection(g), this.o));
		this.pieces.add(new WorldGenMonumentPiece2(BlockFace.fromDirection(g), this.p, random));
		ArrayList<WorldGenMonumentRoomFitHelper> fithelpers = Lists.newArrayList();
		fithelpers.add(new WorldGenMonumentXYDoubleRoomFitHelper());
		fithelpers.add(new WorldGenMonumentYZDoubleRoomFitHelper());
		fithelpers.add(new WorldGenMonumentZDoubleRoomFitHelper());
		fithelpers.add(new WorldGenMonumentXDoubleRoomFitHelper());
		fithelpers.add(new WorldGenMonumentYDoubleRoomFitHelper());
		fithelpers.add(new WorldGenMonumentFitSimpleRoomTopHelper());
		fithelpers.add(new WorldGenMonumentFitSimpleRoomHelper());

		for (WorldGenMonumentRoomDefinition definition : roomDefinitions) {
			if (!definition.d && !definition.b()) {
				Iterator<WorldGenMonumentRoomFitHelper> helper = fithelpers.iterator();
				while (helper.hasNext()) {
					WorldGenMonumentRoomFitHelper var10 = (WorldGenMonumentRoomFitHelper) helper.next();
					if (var10.a(definition)) {
						this.pieces.add(var10.getPiece(BlockFace.fromDirection(g), definition, random));
						break;
					}
				}
			}
		}

		int var14 = this.f.b;
		int var15 = this.a(9, 22);
		int var16 = this.b(9, 22);
		Iterator<WorldGenMonumentPiece> var17 = this.pieces.iterator();

		while (var17.hasNext()) {
			WorldGenMonumentPiece var11 = (WorldGenMonumentPiece) var17.next();
			var11.getBoundingBox().a(var15, var14, var16);
		}

		StructureBoundingBox var18 = buildBB(this.a(1, 1), this.a(1), this.b(1, 1), this.a(23, 21), this.a(8), this.b(23, 21));
		StructureBoundingBox var19 = buildBB(this.a(34, 1), this.a(1), this.b(34, 1), this.a(56, 21), this.a(8), this.b(56, 21));
		StructureBoundingBox var12 = buildBB(this.a(22, 22), this.a(13), this.b(22, 22), this.a(35, 35), this.a(17), this.b(35, 35));
		int var13 = random.nextInt();
		this.pieces.add(new WorldGenMonumentWingRoom(BlockFace.fromDirection(g), var18, var13++));
		this.pieces.add(new WorldGenMonumentWingRoom(BlockFace.fromDirection(g), var19, var13++));
		this.pieces.add(new WorldGenMonumentPenthouse(BlockFace.fromDirection(g), var12));
	}

	private StructureBoundingBox buildBB(int var0, int var1, int var2, int var3, int var4, int var5) {
		return new StructureBoundingBox(Math.min(var0, var3), Math.min(var1, var4), Math.min(var2, var5), Math.max(var0, var3), Math.max(var1, var4), Math.max(var2, var5));
	}

	private List<WorldGenMonumentRoomDefinition> getRoomDefinitions(Random random) {
		WorldGenMonumentRoomDefinition[] definitions = new WorldGenMonumentRoomDefinition[75];

		int var3;
		int var4;
		byte var5;
		int var6;
		for (var3 = 0; var3 < 5; ++var3) {
			for (var4 = 0; var4 < 4; ++var4) {
				var5 = 0;
				var6 = a(var3, var5, var4);
				definitions[var6] = new WorldGenMonumentRoomDefinition(var6);
			}
		}

		for (var3 = 0; var3 < 5; ++var3) {
			for (var4 = 0; var4 < 4; ++var4) {
				var5 = 1;
				var6 = a(var3, var5, var4);
				definitions[var6] = new WorldGenMonumentRoomDefinition(var6);
			}
		}

		for (var3 = 1; var3 < 4; ++var3) {
			for (var4 = 0; var4 < 2; ++var4) {
				var5 = 2;
				var6 = a(var3, var5, var4);
				definitions[var6] = new WorldGenMonumentRoomDefinition(var6);
			}
		}

		this.o = definitions[defg];

		int var8;
		int var9;
		int var11;
		int var12;
		int var13;
		for (var3 = 0; var3 < 5; ++var3) {
			for (var4 = 0; var4 < 5; ++var4) {
				for (int var17 = 0; var17 < 3; ++var17) {
					var6 = a(var3, var17, var4);
					if (definitions[var6] != null) {
						BlockFace[] var7 = BlockFace.values();
						var8 = var7.length;

						for (var9 = 0; var9 < var8; ++var9) {
							BlockFace var10 = var7[var9];
							var11 = var3 + var10.getFrontDirectionX();
							var12 = var17 + var10.getFrontDirectionY();
							var13 = var4 + var10.getFrontDirectionZ();
							if (var11 >= 0 && var11 < 5 && var13 >= 0 && var13 < 5 && var12 >= 0 && var12 < 3) {
								int var14 = a(var11, var12, var13);
								if (definitions[var14] != null) {
									if (var13 != var4) {
										definitions[var6].a(var10.getOpposite(), definitions[var14]);
									} else {
										definitions[var6].a(var10, definitions[var14]);
									}
								}
							}
						}
					}
				}
			}
		}

		WorldGenMonumentRoomDefinition var15;
		WorldGenMonumentRoomDefinition var16;
		WorldGenMonumentRoomDefinition var18;
		definitions[defh].a(BlockFace.UP, var15 = new WorldGenMonumentRoomDefinition(1003));
		definitions[defi].a(BlockFace.SOUTH, var16 = new WorldGenMonumentRoomDefinition(1001));
		definitions[defj].a(BlockFace.SOUTH, var18 = new WorldGenMonumentRoomDefinition(1002));
		var15.d = true;
		var16.d = true;
		var18.d = true;
		this.o.e = true;
		this.p = definitions[a(random.nextInt(4), 0, 2)];
		this.p.d = true;
		this.p.b[BlockFace.EAST.getId()].d = true;
		this.p.b[BlockFace.NORTH.getId()].d = true;
		this.p.b[BlockFace.EAST.getId()].b[BlockFace.NORTH.getId()].d = true;
		this.p.b[BlockFace.UP.getId()].d = true;
		this.p.b[BlockFace.EAST.getId()].b[BlockFace.UP.getId()].d = true;
		this.p.b[BlockFace.NORTH.getId()].b[BlockFace.UP.getId()].d = true;
		this.p.b[BlockFace.EAST.getId()].b[BlockFace.NORTH.getId()].b[BlockFace.UP.getId()].d = true;
		ArrayList<WorldGenMonumentRoomDefinition> var19 = Lists.newArrayList();
		WorldGenMonumentRoomDefinition[] var20 = definitions;
		var8 = definitions.length;

		for (var9 = 0; var9 < var8; ++var9) {
			WorldGenMonumentRoomDefinition var24 = var20[var9];
			if (var24 != null) {
				var24.a();
				var19.add(var24);
			}
		}

		var15.a();
		Collections.shuffle(var19, random);
		int var21 = 1;
		Iterator<WorldGenMonumentRoomDefinition> var22 = var19.iterator();

		while (var22.hasNext()) {
			WorldGenMonumentRoomDefinition var23 = (WorldGenMonumentRoomDefinition) var22.next();
			int var25 = 0;
			var11 = 0;

			while (var25 < 2 && var11 < 5) {
				++var11;
				var12 = random.nextInt(6);
				if (var23.c[var12]) {
					var13 = BlockFace.getById(var12).getOpposite().getId();
					var23.c[var12] = false;
					var23.b[var12].c[var13] = false;
					if (var23.a(var21++) && var23.b[var12].a(var21++)) {
						++var25;
					} else {
						var23.c[var12] = true;
						var23.b[var12].c[var13] = true;
					}
				}
			}
		}

		var19.add(var15);
		var19.add(var16);
		var19.add(var18);
		return var19;
	}

	public boolean a(World var1, Random var2, StructureBoundingBox var3) {
		this.a(false, 0, var1, var2, var3);
		this.a(true, 33, var1, var2, var3);
		this.b(var1, var2, var3);
		this.c(var1, var2, var3);
		this.d(var1, var2, var3);
		this.e(var1, var2, var3);
		this.f(var1, var2, var3);
		this.g(var1, var2, var3);

		int var4;
		for (var4 = 0; var4 < 7; ++var4) {
			int var5 = 0;

			while (var5 < 7) {
				if (var5 == 0 && var4 == 3) {
					var5 = 6;
				}

				int var6 = var4 * 9;
				int var7 = var5 * 9;

				for (int var8 = 0; var8 < 4; ++var8) {
					for (int var9 = 0; var9 < 4; ++var9) {
						this.a(var1, blockStatePrismarineBricks, var6 + var8, 0, var7 + var9, var3);
						this.b(var1, blockStatePrismarineBricks, var6 + var8, -1, var7 + var9, var3);
					}
				}

				if (var4 != 0 && var4 != 6) {
					var5 += 6;
				} else {
					++var5;
				}
			}
		}

		for (var4 = 0; var4 < 5; ++var4) {
			this.a(var1, var3, -1 - var4, 0 + var4 * 2, -1 - var4, -1 - var4, 23, 58 + var4, blockStateWater, blockStateWater, false);
			this.a(var1, var3, 58 + var4, 0 + var4 * 2, -1 - var4, 58 + var4, 23, 58 + var4, blockStateWater, blockStateWater, false);
			this.a(var1, var3, 0 - var4, 0 + var4 * 2, -1 - var4, 57 + var4, 23, -1 - var4, blockStateWater, blockStateWater, false);
			this.a(var1, var3, 0 - var4, 0 + var4 * 2, 58 + var4, 57 + var4, 23, 58 + var4, blockStateWater, blockStateWater, false);
		}

		Iterator<WorldGenMonumentPiece> var10 = this.pieces.iterator();

		while (var10.hasNext()) {
			WorldGenMonumentPiece var11 = (WorldGenMonumentPiece) var10.next();
			if (var11.getBoundingBox().a(var3)) {
				var11.a(var1, var2, var3);
			}
		}

		return true;
	}

	private void a(boolean var1, int var2, World world, Random random, StructureBoundingBox bb) {
		if (this.a(bb, var2, 0, var2 + 23, 20)) {
			this.a(world, bb, var2 + 0, 0, 0, var2 + 24, 0, 20, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, var2 + 0, 1, 0, var2 + 24, 10, 20, blockStateWater, blockStateWater, false);

			int var7;
			for (var7 = 0; var7 < 4; ++var7) {
				this.a(world, bb, var2 + var7, var7 + 1, var7, var2 + var7, var7 + 1, 20, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, var2 + var7 + 7, var7 + 5, var7 + 7, var2 + var7 + 7, var7 + 5, 20, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, var2 + 17 - var7, var7 + 5, var7 + 7, var2 + 17 - var7, var7 + 5, 20, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, var2 + 24 - var7, var7 + 1, var7, var2 + 24 - var7, var7 + 1, 20, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, var2 + var7 + 1, var7 + 1, var7, var2 + 23 - var7, var7 + 1, var7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, var2 + var7 + 8, var7 + 5, var7 + 7, var2 + 16 - var7, var7 + 5, var7 + 7, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			this.a(world, bb, var2 + 4, 4, 4, var2 + 6, 4, 20, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, var2 + 7, 4, 4, var2 + 17, 4, 6, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, var2 + 18, 4, 4, var2 + 20, 4, 20, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, var2 + 11, 8, 11, var2 + 13, 8, 20, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, blockStatePrismarineBricksCopy, var2 + 12, 9, 12, bb);
			this.a(world, blockStatePrismarineBricksCopy, var2 + 12, 9, 15, bb);
			this.a(world, blockStatePrismarineBricksCopy, var2 + 12, 9, 18, bb);
			var7 = var1 ? var2 + 19 : var2 + 5;
			int var8 = var1 ? var2 + 5 : var2 + 19;

			int var9;
			for (var9 = 20; var9 >= 5; var9 -= 3) {
				this.a(world, blockStatePrismarineBricksCopy, var7, 5, var9, bb);
			}

			for (var9 = 19; var9 >= 7; var9 -= 3) {
				this.a(world, blockStatePrismarineBricksCopy, var8, 5, var9, bb);
			}

			for (var9 = 0; var9 < 4; ++var9) {
				int var10 = var1 ? var2 + (24 - (17 - var9 * 3)) : var2 + 17 - var9 * 3;
				this.a(world, blockStatePrismarineBricksCopy, var10, 5, 5, bb);
			}

			this.a(world, blockStatePrismarineBricksCopy, var8, 5, 5, bb);
			this.a(world, bb, var2 + 11, 1, 12, var2 + 13, 7, 12, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, var2 + 12, 1, 11, var2 + 12, 7, 13, blockStatePrismarineRough, blockStatePrismarineRough, false);
		}

	}

	private void b(World world, Random random, StructureBoundingBox bb) {
		if (this.a(bb, 22, 5, 35, 17)) {
			this.a(world, bb, 25, 0, 0, 32, 8, 20, blockStateWater, blockStateWater, false);

			for (int var4 = 0; var4 < 4; ++var4) {
				this.a(world, bb, 24, 2, 5 + var4 * 4, 24, 4, 5 + var4 * 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 22, 4, 5 + var4 * 4, 23, 4, 5 + var4 * 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, blockStatePrismarineBricks, 25, 5, 5 + var4 * 4, bb);
				this.a(world, blockStatePrismarineBricks, 26, 6, 5 + var4 * 4, bb);
				this.a(world, blockStateSealantern, 26, 5, 5 + var4 * 4, bb);
				this.a(world, bb, 33, 2, 5 + var4 * 4, 33, 4, 5 + var4 * 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 34, 4, 5 + var4 * 4, 35, 4, 5 + var4 * 4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, blockStatePrismarineBricks, 32, 5, 5 + var4 * 4, bb);
				this.a(world, blockStatePrismarineBricks, 31, 6, 5 + var4 * 4, bb);
				this.a(world, blockStateSealantern, 31, 5, 5 + var4 * 4, bb);
				this.a(world, bb, 27, 6, 5 + var4 * 4, 30, 6, 5 + var4 * 4, blockStatePrismarineRough, blockStatePrismarineRough, false);
			}
		}

	}

	private void c(World world, Random random, StructureBoundingBox bb) {
		if (this.a(bb, 15, 20, 42, 21)) {
			this.a(world, bb, 15, 0, 21, 42, 0, 21, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 26, 1, 21, 31, 3, 21, blockStateWater, blockStateWater, false);
			this.a(world, bb, 21, 12, 21, 36, 12, 21, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 17, 11, 21, 40, 11, 21, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 16, 10, 21, 41, 10, 21, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 15, 7, 21, 42, 9, 21, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 16, 6, 21, 41, 6, 21, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 17, 5, 21, 40, 5, 21, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 21, 4, 21, 36, 4, 21, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 22, 3, 21, 26, 3, 21, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 31, 3, 21, 35, 3, 21, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 23, 2, 21, 25, 2, 21, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 32, 2, 21, 34, 2, 21, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 28, 4, 20, 29, 4, 21, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, blockStatePrismarineBricks, 27, 3, 21, bb);
			this.a(world, blockStatePrismarineBricks, 30, 3, 21, bb);
			this.a(world, blockStatePrismarineBricks, 26, 2, 21, bb);
			this.a(world, blockStatePrismarineBricks, 31, 2, 21, bb);
			this.a(world, blockStatePrismarineBricks, 25, 1, 21, bb);
			this.a(world, blockStatePrismarineBricks, 32, 1, 21, bb);

			int var4;
			for (var4 = 0; var4 < 7; ++var4) {
				this.a(world, blockStatePrismarineDark, 28 - var4, 6 + var4, 21, bb);
				this.a(world, blockStatePrismarineDark, 29 + var4, 6 + var4, 21, bb);
			}

			for (var4 = 0; var4 < 4; ++var4) {
				this.a(world, blockStatePrismarineDark, 28 - var4, 9 + var4, 21, bb);
				this.a(world, blockStatePrismarineDark, 29 + var4, 9 + var4, 21, bb);
			}

			this.a(world, blockStatePrismarineDark, 28, 12, 21, bb);
			this.a(world, blockStatePrismarineDark, 29, 12, 21, bb);

			for (var4 = 0; var4 < 3; ++var4) {
				this.a(world, blockStatePrismarineDark, 22 - var4 * 2, 8, 21, bb);
				this.a(world, blockStatePrismarineDark, 22 - var4 * 2, 9, 21, bb);
				this.a(world, blockStatePrismarineDark, 35 + var4 * 2, 8, 21, bb);
				this.a(world, blockStatePrismarineDark, 35 + var4 * 2, 9, 21, bb);
			}

			this.a(world, bb, 15, 13, 21, 42, 15, 21, blockStateWater, blockStateWater, false);
			this.a(world, bb, 15, 1, 21, 15, 6, 21, blockStateWater, blockStateWater, false);
			this.a(world, bb, 16, 1, 21, 16, 5, 21, blockStateWater, blockStateWater, false);
			this.a(world, bb, 17, 1, 21, 20, 4, 21, blockStateWater, blockStateWater, false);
			this.a(world, bb, 21, 1, 21, 21, 3, 21, blockStateWater, blockStateWater, false);
			this.a(world, bb, 22, 1, 21, 22, 2, 21, blockStateWater, blockStateWater, false);
			this.a(world, bb, 23, 1, 21, 24, 1, 21, blockStateWater, blockStateWater, false);
			this.a(world, bb, 42, 1, 21, 42, 6, 21, blockStateWater, blockStateWater, false);
			this.a(world, bb, 41, 1, 21, 41, 5, 21, blockStateWater, blockStateWater, false);
			this.a(world, bb, 37, 1, 21, 40, 4, 21, blockStateWater, blockStateWater, false);
			this.a(world, bb, 36, 1, 21, 36, 3, 21, blockStateWater, blockStateWater, false);
			this.a(world, bb, 35, 1, 21, 35, 2, 21, blockStateWater, blockStateWater, false);
			this.a(world, bb, 33, 1, 21, 34, 1, 21, blockStateWater, blockStateWater, false);
		}

	}

	private void d(World world, Random random, StructureBoundingBox bb) {
		if (this.a(bb, 21, 21, 36, 36)) {
			this.a(world, bb, 21, 0, 22, 36, 0, 36, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 21, 1, 22, 36, 23, 36, blockStateWater, blockStateWater, false);

			for (int var4 = 0; var4 < 4; ++var4) {
				this.a(world, bb, 21 + var4, 13 + var4, 21 + var4, 36 - var4, 13 + var4, 21 + var4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 21 + var4, 13 + var4, 36 - var4, 36 - var4, 13 + var4, 36 - var4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 21 + var4, 13 + var4, 22 + var4, 21 + var4, 13 + var4, 35 - var4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
				this.a(world, bb, 36 - var4, 13 + var4, 22 + var4, 36 - var4, 13 + var4, 35 - var4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			this.a(world, bb, 25, 16, 25, 32, 16, 32, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 25, 17, 25, 25, 19, 25, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 32, 17, 25, 32, 19, 25, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 25, 17, 32, 25, 19, 32, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 32, 17, 32, 32, 19, 32, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, blockStatePrismarineBricks, 26, 20, 26, bb);
			this.a(world, blockStatePrismarineBricks, 27, 21, 27, bb);
			this.a(world, blockStateSealantern, 27, 20, 27, bb);
			this.a(world, blockStatePrismarineBricks, 26, 20, 31, bb);
			this.a(world, blockStatePrismarineBricks, 27, 21, 30, bb);
			this.a(world, blockStateSealantern, 27, 20, 30, bb);
			this.a(world, blockStatePrismarineBricks, 31, 20, 31, bb);
			this.a(world, blockStatePrismarineBricks, 30, 21, 30, bb);
			this.a(world, blockStateSealantern, 30, 20, 30, bb);
			this.a(world, blockStatePrismarineBricks, 31, 20, 26, bb);
			this.a(world, blockStatePrismarineBricks, 30, 21, 27, bb);
			this.a(world, blockStateSealantern, 30, 20, 27, bb);
			this.a(world, bb, 28, 21, 27, 29, 21, 27, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 27, 21, 28, 27, 21, 29, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 28, 21, 30, 29, 21, 30, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 30, 21, 28, 30, 21, 29, blockStatePrismarineRough, blockStatePrismarineRough, false);
		}

	}

	private void e(World world, Random random, StructureBoundingBox bb) {
		int var4;
		if (this.a(bb, 0, 21, 6, 58)) {
			this.a(world, bb, 0, 0, 21, 6, 0, 57, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 0, 1, 21, 6, 7, 57, blockStateWater, blockStateWater, false);
			this.a(world, bb, 4, 4, 21, 6, 4, 53, blockStatePrismarineRough, blockStatePrismarineRough, false);

			for (var4 = 0; var4 < 4; ++var4) {
				this.a(world, bb, var4, var4 + 1, 21, var4, var4 + 1, 57 - var4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			for (var4 = 23; var4 < 53; var4 += 3) {
				this.a(world, blockStatePrismarineBricksCopy, 5, 5, var4, bb);
			}

			this.a(world, blockStatePrismarineBricksCopy, 5, 5, 52, bb);

			for (var4 = 0; var4 < 4; ++var4) {
				this.a(world, bb, var4, var4 + 1, 21, var4, var4 + 1, 57 - var4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			this.a(world, bb, 4, 1, 52, 6, 3, 52, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 5, 1, 51, 5, 3, 53, blockStatePrismarineRough, blockStatePrismarineRough, false);
		}

		if (this.a(bb, 51, 21, 58, 58)) {
			this.a(world, bb, 51, 0, 21, 57, 0, 57, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 51, 1, 21, 57, 7, 57, blockStateWater, blockStateWater, false);
			this.a(world, bb, 51, 4, 21, 53, 4, 53, blockStatePrismarineRough, blockStatePrismarineRough, false);

			for (var4 = 0; var4 < 4; ++var4) {
				this.a(world, bb, 57 - var4, var4 + 1, 21, 57 - var4, var4 + 1, 57 - var4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			for (var4 = 23; var4 < 53; var4 += 3) {
				this.a(world, blockStatePrismarineBricksCopy, 52, 5, var4, bb);
			}

			this.a(world, blockStatePrismarineBricksCopy, 52, 5, 52, bb);
			this.a(world, bb, 51, 1, 52, 53, 3, 52, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 52, 1, 51, 52, 3, 53, blockStatePrismarineRough, blockStatePrismarineRough, false);
		}

		if (this.a(bb, 0, 51, 57, 57)) {
			this.a(world, bb, 7, 0, 51, 50, 0, 57, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 7, 1, 51, 50, 10, 57, blockStateWater, blockStateWater, false);

			for (var4 = 0; var4 < 4; ++var4) {
				this.a(world, bb, var4 + 1, var4 + 1, 57 - var4, 56 - var4, var4 + 1, 57 - var4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}
		}

	}

	private void f(World world, Random random, StructureBoundingBox bb) {
		int var4;
		if (this.a(bb, 7, 21, 13, 50)) {
			this.a(world, bb, 7, 0, 21, 13, 0, 50, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 7, 1, 21, 13, 10, 50, blockStateWater, blockStateWater, false);
			this.a(world, bb, 11, 8, 21, 13, 8, 53, blockStatePrismarineRough, blockStatePrismarineRough, false);

			for (var4 = 0; var4 < 4; ++var4) {
				this.a(world, bb, var4 + 7, var4 + 5, 21, var4 + 7, var4 + 5, 54, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			for (var4 = 21; var4 <= 45; var4 += 3) {
				this.a(world, blockStatePrismarineBricksCopy, 12, 9, var4, bb);
			}
		}

		if (this.a(bb, 44, 21, 50, 54)) {
			this.a(world, bb, 44, 0, 21, 50, 0, 50, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 44, 1, 21, 50, 10, 50, blockStateWater, blockStateWater, false);
			this.a(world, bb, 44, 8, 21, 46, 8, 53, blockStatePrismarineRough, blockStatePrismarineRough, false);

			for (var4 = 0; var4 < 4; ++var4) {
				this.a(world, bb, 50 - var4, var4 + 5, 21, 50 - var4, var4 + 5, 54, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			for (var4 = 21; var4 <= 45; var4 += 3) {
				this.a(world, blockStatePrismarineBricksCopy, 45, 9, var4, bb);
			}
		}

		if (this.a(bb, 8, 44, 49, 54)) {
			this.a(world, bb, 14, 0, 44, 43, 0, 50, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 14, 1, 44, 43, 10, 50, blockStateWater, blockStateWater, false);

			for (var4 = 12; var4 <= 45; var4 += 3) {
				this.a(world, blockStatePrismarineBricksCopy, var4, 9, 45, bb);
				this.a(world, blockStatePrismarineBricksCopy, var4, 9, 52, bb);
				if (var4 == 12 || var4 == 18 || var4 == 24 || var4 == 33 || var4 == 39 || var4 == 45) {
					this.a(world, blockStatePrismarineBricksCopy, var4, 9, 47, bb);
					this.a(world, blockStatePrismarineBricksCopy, var4, 9, 50, bb);
					this.a(world, blockStatePrismarineBricksCopy, var4, 10, 45, bb);
					this.a(world, blockStatePrismarineBricksCopy, var4, 10, 46, bb);
					this.a(world, blockStatePrismarineBricksCopy, var4, 10, 51, bb);
					this.a(world, blockStatePrismarineBricksCopy, var4, 10, 52, bb);
					this.a(world, blockStatePrismarineBricksCopy, var4, 11, 47, bb);
					this.a(world, blockStatePrismarineBricksCopy, var4, 11, 50, bb);
					this.a(world, blockStatePrismarineBricksCopy, var4, 12, 48, bb);
					this.a(world, blockStatePrismarineBricksCopy, var4, 12, 49, bb);
				}
			}

			for (var4 = 0; var4 < 3; ++var4) {
				this.a(world, bb, 8 + var4, 5 + var4, 54, 49 - var4, 5 + var4, 54, blockStatePrismarineRough, blockStatePrismarineRough, false);
			}

			this.a(world, bb, 11, 8, 54, 46, 8, 54, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			this.a(world, bb, 14, 8, 44, 43, 8, 53, blockStatePrismarineRough, blockStatePrismarineRough, false);
		}

	}

	private void g(World world, Random random, StructureBoundingBox bb) {
		int var4;
		if (this.a(bb, 14, 21, 20, 43)) {
			this.a(world, bb, 14, 0, 21, 20, 0, 43, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 14, 1, 22, 20, 14, 43, blockStateWater, blockStateWater, false);
			this.a(world, bb, 18, 12, 22, 20, 12, 39, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 18, 12, 21, 20, 12, 21, blockStatePrismarineBricks, blockStatePrismarineBricks, false);

			for (var4 = 0; var4 < 4; ++var4) {
				this.a(world, bb, var4 + 14, var4 + 9, 21, var4 + 14, var4 + 9, 43 - var4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			for (var4 = 23; var4 <= 39; var4 += 3) {
				this.a(world, blockStatePrismarineBricksCopy, 19, 13, var4, bb);
			}
		}

		if (this.a(bb, 37, 21, 43, 43)) {
			this.a(world, bb, 37, 0, 21, 43, 0, 43, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 37, 1, 22, 43, 14, 43, blockStateWater, blockStateWater, false);
			this.a(world, bb, 37, 12, 22, 39, 12, 39, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 37, 12, 21, 39, 12, 21, blockStatePrismarineBricks, blockStatePrismarineBricks, false);

			for (var4 = 0; var4 < 4; ++var4) {
				this.a(world, bb, 43 - var4, var4 + 9, 21, 43 - var4, var4 + 9, 43 - var4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			for (var4 = 23; var4 <= 39; var4 += 3) {
				this.a(world, blockStatePrismarineBricksCopy, 38, 13, var4, bb);
			}
		}

		if (this.a(bb, 15, 37, 42, 43)) {
			this.a(world, bb, 21, 0, 37, 36, 0, 43, blockStatePrismarineRough, blockStatePrismarineRough, false);
			this.a(world, bb, 21, 1, 37, 36, 14, 43, blockStateWater, blockStateWater, false);
			this.a(world, bb, 21, 12, 37, 36, 12, 39, blockStatePrismarineRough, blockStatePrismarineRough, false);

			for (var4 = 0; var4 < 4; ++var4) {
				this.a(world, bb, 15 + var4, var4 + 9, 43 - var4, 42 - var4, var4 + 9, 43 - var4, blockStatePrismarineBricks, blockStatePrismarineBricks, false);
			}

			for (var4 = 21; var4 <= 36; var4 += 3) {
				this.a(world, blockStatePrismarineBricksCopy, var4, 13, 38, bb);
			}
		}

	}
}
