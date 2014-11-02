package com.lastabyss.carbon.generator.monument;

import com.google.common.collect.Lists;
import com.lastabyss.carbon.entity.EntityGuardian;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import net.minecraft.server.v1_7_R4.BiomeBase;
import net.minecraft.server.v1_7_R4.BiomeMeta;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.StructureGenerator;
import net.minecraft.server.v1_7_R4.StructureStart;

public class WorldGenMonument extends StructureGenerator {

	private int f;
	private int g;
	public static final List<BiomeBase> biomes = Arrays.asList(new BiomeBase[] { BiomeBase.OCEAN, BiomeBase.DEEP_OCEAN, BiomeBase.RIVER, BiomeBase.FROZEN_OCEAN, BiomeBase.FROZEN_RIVER });
	private static final List<BiomeMeta> meta = Lists.newArrayList();
	static {
		meta.add(new BiomeMeta(EntityGuardian.class, 1, 2, 4));
	}

	public WorldGenMonument() {
		this.f = 32;
		this.g = 5;
	}

	public WorldGenMonument(Map<?, ?> var1) {
		this();
		Iterator<?> var2 = var1.entrySet().iterator();

		while (var2.hasNext()) {
			Entry<?, ?> var3 = (Entry<?, ?>) var2.next();
			if (((String) var3.getKey()).equals("spacing")) {
				this.f = MathHelper.a((String) var3.getValue(), this.f, 1);
			} else if (((String) var3.getKey()).equals("separation")) {
				this.g = MathHelper.a((String) var3.getValue(), this.g, 1);
			}
		}

	}

	@Override
	public String a() {
		return "Monument";
	}

	@Override
	protected boolean a(int i, int j) {
		int icopy = i;
		int jcopy = j;

		if (i < 0) {
			i -= this.f - 1;
		}

		if (j < 0) {
			j -= this.f - 1;
		}

		int randomi = i / this.f;
		int randomj = j / this.f;
		Random random = this.c.A(randomi, randomj, 10387313);
		randomi *= this.f;
		randomj *= this.f;
		randomi += (random.nextInt(this.f - this.g) + random.nextInt(this.f - this.g)) / 2;
		randomj += (random.nextInt(this.f - this.g) + random.nextInt(this.f - this.g)) / 2;
		if (icopy == randomi && jcopy == randomj) {
			if (this.c.getWorldChunkManager().getBiome(icopy * 16 + 8, jcopy * 16 + 8) != BiomeBase.DEEP_OCEAN) {
				return false;
			}

			boolean hasBiomes = this.c.getWorldChunkManager().a(icopy * 16 + 8, jcopy * 16 + 8, 29, biomes);
			if (hasBiomes) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected StructureStart b(int var1, int var2) {
		return new WorldGenMonumentStart(this.c, this.b, var1, var2);
	}

	public List<BiomeMeta> b() {
		return meta;
	}

	public boolean isInside(int x, int y, int z) {
		Iterator<?> iterator = this.d.values().iterator();
		while (iterator.hasNext()) {
			StructureStart structureStart = (StructureStart) iterator.next();
			if (structureStart.a().b(x, y, z)) {
				return true;
			}
		}

		return false;
	}

}
