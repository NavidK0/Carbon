package com.lastabyss.carbon.generator.monument;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;
import com.lastabyss.carbon.utils.nmsclasses.UniverseDirection;

import java.util.Random;

import net.minecraft.server.v1_7_R4.StructureBoundingBox;
import net.minecraft.server.v1_7_R4.StructureStart;
import net.minecraft.server.v1_7_R4.World;

public class WorldGenMonumentStart extends StructureStart {

	private boolean d;

	public WorldGenMonumentStart() {
	}

	public WorldGenMonumentStart(World world, Random random, int var3, int var4) {
		super(var3, var4);
		this.b(world, random, var3, var4);
	}

	@SuppressWarnings("unchecked")
	private void b(World world, Random random, int var3, int var4) {
		random.setSeed(world.getSeed());
		long var5 = random.nextLong();
		long var7 = random.nextLong();
		long var9 = (long) var3 * var5;
		long var11 = (long) var4 * var7;
		random.setSeed(var9 ^ var11 ^ world.getSeed());
		int var13 = var3 * 16 + 8 - 29;
		int var14 = var4 * 16 + 8 - 29;
		BlockFace var15 = UniverseDirection.HORIZONTAL.getRandomBlockFace(random);
		this.a.add(new WorldGenMonumentBuilding(random, var13, var14, var15));
		this.c();
		this.d = true;
	}

	@Override
	public void a(World world, Random random, StructureBoundingBox bb) {
		if (!this.d) {
			this.a.clear();
			this.b(world, random, this.e(), this.f());
		}

		super.a(world, random, bb);
	}

}
