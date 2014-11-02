package com.lastabyss.carbon.generator;

import java.util.*;

import com.lastabyss.carbon.generator.monument.WorldGenMonument;

import net.minecraft.server.v1_7_R4.BiomeBase;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.BlockFalling;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.Chunk;
import net.minecraft.server.v1_7_R4.ChunkPosition;
import net.minecraft.server.v1_7_R4.EnumCreatureType;
import net.minecraft.server.v1_7_R4.IChunkProvider;
import net.minecraft.server.v1_7_R4.IProgressUpdate;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.NoiseGenerator3;
import net.minecraft.server.v1_7_R4.NoiseGeneratorOctaves;
import net.minecraft.server.v1_7_R4.SpawnerCreature;
import net.minecraft.server.v1_7_R4.World;
import net.minecraft.server.v1_7_R4.WorldGenBase;
import net.minecraft.server.v1_7_R4.WorldGenCanyon;
import net.minecraft.server.v1_7_R4.WorldGenCaves;
import net.minecraft.server.v1_7_R4.WorldGenDungeons;
import net.minecraft.server.v1_7_R4.WorldGenLakes;
import net.minecraft.server.v1_7_R4.WorldGenLargeFeature;
import net.minecraft.server.v1_7_R4.WorldGenMineshaft;
import net.minecraft.server.v1_7_R4.WorldGenStronghold;
import net.minecraft.server.v1_7_R4.WorldGenVillage;
import net.minecraft.server.v1_7_R4.WorldType;

public class ChunkProviderGenerate implements IChunkProvider {

	private Random i;
	private NoiseGeneratorOctaves j;
	private NoiseGeneratorOctaves k;
	private NoiseGeneratorOctaves l;
	private NoiseGenerator3 m;
	public NoiseGeneratorOctaves a;
	public NoiseGeneratorOctaves b;
	public NoiseGeneratorOctaves c;
	private World n;
	private final boolean o;
	private WorldType p;
	private final double[] q;
	private final float[] r;
	private double[] s;
	private WorldGenBase t;
	private WorldGenStronghold u;
	private WorldGenVillage v;
	private WorldGenMineshaft w;
	private WorldGenLargeFeature x;
	private WorldGenBase y;
	private WorldGenMonument monumentGenerator = new WorldGenMonument();
	private BiomeBase[] z;
	double[] d;
	double[] e;
	double[] f;
	double[] g;
	int[][] h;

	public ChunkProviderGenerate(final World n, final long n2, final boolean o) {
		super();
		this.s = new double[256];
		this.t = new WorldGenCaves();
		this.u = new WorldGenStronghold();
		this.v = new WorldGenVillage();
		this.w = new WorldGenMineshaft();
		this.x = new WorldGenLargeFeature();
		this.y = new WorldGenCanyon();
		this.h = new int[32][32];
		this.n = n;
		this.o = o;
		this.p = n.getWorldData().getType();
		this.i = new Random(n2);
		this.j = new NoiseGeneratorOctaves(this.i, 16);
		this.k = new NoiseGeneratorOctaves(this.i, 16);
		this.l = new NoiseGeneratorOctaves(this.i, 8);
		this.m = new NoiseGenerator3(this.i, 4);
		this.a = new NoiseGeneratorOctaves(this.i, 10);
		this.b = new NoiseGeneratorOctaves(this.i, 16);
		this.c = new NoiseGeneratorOctaves(this.i, 8);
		this.q = new double[825];
		this.r = new float[25];
		for (int i = -2; i <= 2; ++i) {
			for (int j = -2; j <= 2; ++j) {
				this.r[i + 2 + (j + 2) * 5] = 10.0f / MathHelper.c(i * i + j * j + 0.2f);
			}
		}
	}

	public void a(final int n, final int n2, final Block[] array) {
		final int n3 = 63;
		this.z = this.n.getWorldChunkManager().getBiomes(this.z, n * 4 - 2, n2 * 4 - 2, 10, 10);
		this.a(n * 4, 0, n2 * 4);
		for (int i = 0; i < 4; ++i) {
			final int n4 = i * 5;
			final int n5 = (i + 1) * 5;
			for (int j = 0; j < 4; ++j) {
				final int n6 = (n4 + j) * 33;
				final int n7 = (n4 + j + 1) * 33;
				final int n8 = (n5 + j) * 33;
				final int n9 = (n5 + j + 1) * 33;
				for (int k = 0; k < 32; ++k) {
					final double n10 = 0.125;
					double n11 = this.q[n6 + k];
					double n12 = this.q[n7 + k];
					double n13 = this.q[n8 + k];
					double n14 = this.q[n9 + k];
					final double n15 = (this.q[n6 + k + 1] - n11) * n10;
					final double n16 = (this.q[n7 + k + 1] - n12) * n10;
					final double n17 = (this.q[n8 + k + 1] - n13) * n10;
					final double n18 = (this.q[n9 + k + 1] - n14) * n10;
					for (int l = 0; l < 8; ++l) {
						final double n19 = 0.25;
						double n20 = n11;
						double n21 = n12;
						final double n22 = (n13 - n11) * n19;
						final double n23 = (n14 - n12) * n19;
						for (int n24 = 0; n24 < 4; ++n24) {
							final int n25 = n24 + i * 4 << 12 | 0 + j * 4 << 8 | k * 8 + l;
							final int n26 = 256;
							int n27 = n25 - n26;
							final double n28 = 0.25;
							final double n29 = n20;
							final double n30 = (n21 - n20) * n28;
							double n31 = n29 - n30;
							for (int n32 = 0; n32 < 4; ++n32) {
								if ((n31 += n30) > 0.0) {
									array[n27 += n26] = Blocks.STONE;
								} else if (k * 8 + l < n3) {
									array[n27 += n26] = Blocks.STATIONARY_WATER;
								} else {
									array[n27 += n26] = null;
								}
							}
							n20 += n22;
							n21 += n23;
						}
						n11 += n15;
						n12 += n16;
						n13 += n17;
						n14 += n18;
					}
				}
			}
		}
	}

	public void a(final int n, final int n2, final Block[] array, final byte[] array2, final BiomeBase[] array3) {
		final double n3 = 0.03125;
		this.s = this.m.a(this.s, n * 16, n2 * 16, 16, 16, n3 * 2.0, n3 * 2.0, 1.0);
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				array3[j + i * 16].a(this.n, this.i, array, array2, n * 16 + i, n2 * 16 + j, this.s[j + i * 16]);
			}
		}
	}

	@Override
	public Chunk getChunkAt(final int n, final int n2) {
		return this.getOrCreateChunk(n, n2);
	}

	@Override
	public Chunk getOrCreateChunk(final int i, final int j) {
		this.i.setSeed(i * 341873128712L + j * 132897987541L);
		final Block[] ablock = new Block[65536];
		final byte[] abyte = new byte[65536];
		this.a(i, j, ablock);
		this.a(i, j, ablock, abyte, this.z = this.n.getWorldChunkManager().getBiomeBlock(this.z, i * 16, j * 16, 16, 16));
		this.t.a(this, this.n, i, j, ablock);
		this.y.a(this, this.n, i, j, ablock);
		if (this.o) {
			this.w.a(this, this.n, i, j, ablock);
			this.v.a(this, this.n, i, j, ablock);
			this.u.a(this, this.n, i, j, ablock);
			this.x.a(this, this.n, i, j, ablock);
			this.monumentGenerator.a(this, this.n, i, j, ablock);
		}
		final Chunk chunk = new Chunk(this.n, ablock, abyte, i, j);
		final byte[] m = chunk.m();
		for (int k = 0; k < m.length; ++k) {
			m[k] = (byte) this.z[k].id;
		}
		chunk.initLighting();
		return chunk;
	}

	private void a(int n, final int n2, int n3) {
		this.g = this.b.a(this.g, n, n3, 5, 5, 200.0, 200.0, 0.5);
		this.d = this.l.a(this.d, n, n2, n3, 5, 33, 5, 8.555150000000001, 4.277575000000001, 8.555150000000001);
		this.e = this.j.a(this.e, n, n2, n3, 5, 33, 5, 684.412, 684.412, 684.412);
		this.f = this.k.a(this.f, n, n2, n3, 5, 33, 5, 684.412, 684.412, 684.412);
		n3 = (n = 0);
		int n4 = 0;
		int n5 = 0;
		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j < 5; ++j) {
				float n6 = 0.0f;
				float n7 = 0.0f;
				float n8 = 0.0f;
				final int n9 = 2;
				final BiomeBase biomeBase = this.z[i + 2 + (j + 2) * 10];
				for (int k = -n9; k <= n9; ++k) {
					for (int l = -n9; l <= n9; ++l) {
						final BiomeBase biomeBase2 = this.z[i + k + 2 + (j + l + 2) * 10];
						float am = biomeBase2.am;
						float an = biomeBase2.an;
						if (this.p == WorldType.AMPLIFIED && am > 0.0f) {
							am = 1.0f + am * 2.0f;
							an = 1.0f + an * 4.0f;
						}
						float n10 = this.r[k + 2 + (l + 2) * 5] / (am + 2.0f);
						if (biomeBase2.am > biomeBase.am) {
							n10 /= 2.0f;
						}
						n6 += an * n10;
						n7 += am * n10;
						n8 += n10;
					}
				}
				final float n11 = n6 / n8;
				final float n12 = n7 / n8;
				final float n13 = n11 * 0.9f + 0.1f;
				final float n14 = (n12 * 4.0f - 1.0f) / 8.0f;
				double n15 = this.g[n5] / 8000.0;
				if (n15 < 0.0) {
					n15 = -n15 * 0.3;
				}
				double n16 = n15 * 3.0 - 2.0;
				double n18;
				if (n16 < 0.0) {
					double n17 = n16 / 2.0;
					if (n17 < -1.0) {
						n17 = -1.0;
					}
					n18 = n17 / 1.4 / 2.0;
				} else {
					if (n16 > 1.0) {
						n16 = 1.0;
					}
					n18 = n16 / 8.0;
				}
				++n5;
				final double n19 = n14;
				final double n20 = n13;
				final double n21 = 8.5 + (n19 + n18 * 0.2) * 8.5 / 8.0 * 4.0;
				for (int n22 = 0; n22 < 33; ++n22) {
					double n23 = (n22 - n21) * 12.0 * 128.0 / 256.0 / n20;
					if (n23 < 0.0) {
						n23 *= 4.0;
					}
					double n24 = MathHelper.b(this.e[n4] / 512.0, this.f[n4] / 512.0, (this.d[n4] / 10.0 + 1.0) / 2.0) - n23;
					if (n22 > 29) {
						final double n25 = (n22 - 29) / 3.0f;
						n24 = n24 * (1.0 - n25) + -10.0 * n25;
					}
					this.q[n4] = n24;
					++n4;
				}
			}
		}
	}

	@Override
	public boolean isChunkLoaded(final int n, final int n2) {
		return true;
	}

	@Override
	public void getChunkAt(final IChunkProvider chunkProvider, final int chunkX, final int chunkZ) {
		BlockFalling.instaFall = true;
		int n3 = chunkX * 16;
		int n4 = chunkZ * 16;
		final BiomeBase biome = this.n.getBiome(n3 + 16, n4 + 16);
		this.i.setSeed(this.n.getSeed());
		this.i.setSeed(chunkX * (this.i.nextLong() / 2L * 2L + 1L) + chunkZ * (this.i.nextLong() / 2L * 2L + 1L) ^ this.n.getSeed());
		boolean a = false;
		if (this.o) {
			this.w.a(this.n, this.i, chunkX, chunkZ);
			a = this.v.a(this.n, this.i, chunkX, chunkZ);
			this.u.a(this.n, this.i, chunkX, chunkZ);
			this.x.a(this.n, this.i, chunkX, chunkZ);
			this.monumentGenerator.a(this.n, this.i, chunkX, chunkZ);
		}
		if (biome != BiomeBase.DESERT && biome != BiomeBase.DESERT_HILLS && !a && this.i.nextInt(4) == 0) {
			new WorldGenLakes(Blocks.STATIONARY_WATER).generate(this.n, this.i, n3 + this.i.nextInt(16) + 8, this.i.nextInt(256), n4 + this.i.nextInt(16) + 8);
		}
		if (!a && this.i.nextInt(8) == 0) {
			final int n5 = n3 + this.i.nextInt(16) + 8;
			final int nextInt = this.i.nextInt(this.i.nextInt(248) + 8);
			final int n6 = n4 + this.i.nextInt(16) + 8;
			if (nextInt < 63 || this.i.nextInt(10) == 0) {
				new WorldGenLakes(Blocks.STATIONARY_LAVA).generate(this.n, this.i, n5, nextInt, n6);
			}
		}
		for (int i = 0; i < 8; ++i) {
			new WorldGenDungeons().generate(this.n, this.i, n3 + this.i.nextInt(16) + 8, this.i.nextInt(256), n4 + this.i.nextInt(16) + 8);
		}
		biome.a(this.n, this.i, n3, n4);
		SpawnerCreature.a(this.n, biome, n3 + 8, n4 + 8, 16, 16, this.i);
		n3 += 8;
		n4 += 8;
		for (int j = 0; j < 16; ++j) {
			for (int k = 0; k < 16; ++k) {
				final int h = this.n.h(n3 + j, n4 + k);
				if (this.n.r(j + n3, h - 1, k + n4)) {
					this.n.setTypeAndData(j + n3, h - 1, k + n4, Blocks.ICE, 0, 2);
				}
				if (this.n.e(j + n3, h, k + n4, true)) {
					this.n.setTypeAndData(j + n3, h, k + n4, Blocks.SNOW, 0, 2);
				}
			}
		}
		BlockFalling.instaFall = false;
	}

	@Override
	public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
		return true;
	}

	@Override
	public void c() {
	}

	@Override
	public boolean unloadChunks() {
		return false;
	}

	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public String getName() {
		return "RandomLevelSource";
	}

	@Override
	public List<?> getMobsFor(final EnumCreatureType enumCreatureType, final int x, final int y, final int z) {
		final BiomeBase biome = this.n.getBiome(x, z);
		if (enumCreatureType == EnumCreatureType.MONSTER && this.x.a(x, y, z)) {
			return this.x.b();
		}
		if (enumCreatureType == EnumCreatureType.MONSTER && this.monumentGenerator.isInside(x, y, z)) {
			return this.monumentGenerator.b();
		}
		return biome.getMobs(enumCreatureType);
	}

	@Override
	public ChunkPosition findNearestMapFeature(final World world, final String s, final int i, final int j, final int k) {
		if ("Stronghold".equals(s) && this.u != null) {
			return this.u.getNearestGeneratedFeature(world, i, j, k);
		}
		return null;
	}

	@Override
	public int getLoadedChunks() {
		return 0;
	}

	@Override
	public void recreateStructures(final int n, final int n2) {
		if (this.o) {
			this.w.a(this, this.n, n, n2, null);
			this.v.a(this, this.n, n, n2, null);
			this.u.a(this, this.n, n, n2, null);
			this.x.a(this, this.n, n, n2, null);
			this.monumentGenerator.a(this, this.n, n, n2, null);
		}
	}

}
