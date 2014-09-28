package net.o2gaming.carbon.generator;

import java.util.Random;
import net.minecraft.server.v1_7_R4.BiomeBase;
import net.minecraft.server.v1_7_R4.BiomeDecorator;
import net.minecraft.server.v1_7_R4.BlockFlowers;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.World;
import net.minecraft.server.v1_7_R4.WorldGenCactus;
import net.minecraft.server.v1_7_R4.WorldGenDeadBush;
import net.minecraft.server.v1_7_R4.WorldGenFlowers;
import net.minecraft.server.v1_7_R4.WorldGenHugeMushroom;
import net.minecraft.server.v1_7_R4.WorldGenLiquids;
import net.minecraft.server.v1_7_R4.WorldGenMinable;
import net.minecraft.server.v1_7_R4.WorldGenPumpkin;
import net.minecraft.server.v1_7_R4.WorldGenReed;
import net.minecraft.server.v1_7_R4.WorldGenSand;
import net.minecraft.server.v1_7_R4.WorldGenTreeAbstract;
import net.minecraft.server.v1_7_R4.WorldGenWaterLily;
import net.minecraft.server.v1_7_R4.WorldGenerator;

public class CarbonBiomeDecorator extends BiomeDecorator {

    public ChunkProviderGeneratePropertiesHolder chunkProperties = ChunkProviderGeneratePropertiesHolder.create();

    public CarbonBiomeDecorator() {
        this.f = new WorldGenSand(Blocks.SAND, 7);
        this.g = new WorldGenSand(Blocks.GRAVEL, 6);
        this.h = new WorldGenMinable(Blocks.DIRT, 32);
        this.i = new WorldGenMinable(Blocks.GRAVEL, 32);
        this.j = new WorldGenMinable(Blocks.COAL_ORE, 16);
        this.k = new WorldGenMinable(Blocks.IRON_ORE, 8);
        this.l = new WorldGenMinable(Blocks.GOLD_ORE, 8);
        this.m = new WorldGenMinable(Blocks.REDSTONE_ORE, 7);
        this.n = new WorldGenMinable(Blocks.DIAMOND_ORE, 7);
        this.o = new WorldGenMinable(Blocks.LAPIS_ORE, 6);
        this.p = new WorldGenFlowers(Blocks.YELLOW_FLOWER);
        this.q = new WorldGenFlowers(Blocks.BROWN_MUSHROOM);
        this.r = new WorldGenFlowers(Blocks.RED_MUSHROOM);
        this.s = new WorldGenHugeMushroom();
        this.t = new WorldGenReed();
        this.u = new WorldGenCactus();
        this.v = new WorldGenWaterLily();
        this.y = 2;
        this.z = 1;
        this.E = 1;
        this.F = 3;
        this.G = 1;
        this.I = true;
    }

    @Override
    public void a(World world, Random random, BiomeBase biomebase, int i, int j) {
        if (this.a != null) {
            throw new RuntimeException("Already decorating!!");
        } else {
            this.a = world;
            this.b = random;
            this.c = i;
            this.d = j;
            this.a(biomebase);
            this.a = null;
            this.b = null;
        }
    }

    @Override
    protected void a(BiomeBase biomebase) {
        this.a();

        int x;
        int y;
        int z;

        for (x = 0; x < this.F; ++x) {
            y = this.c + this.b.nextInt(16) + 8;
            z = this.d + this.b.nextInt(16) + 8;
            //generate(World, Random, int y, int chunk, int z)
            this.f.generate(this.a, this.b, y, this.a.i(y, z), z);
        }

        for (x = 0; x < this.G; ++x) {
            y = this.c + this.b.nextInt(16) + 8;
            z = this.d + this.b.nextInt(16) + 8;
            this.e.generate(this.a, this.b, y, this.a.i(y, z), z);
        }

        for (x = 0; x < this.E; ++x) {
            y = this.c + this.b.nextInt(16) + 8;
            z = this.d + this.b.nextInt(16) + 8;
            this.g.generate(this.a, this.b, y, this.a.i(y, z), z);
        }

        x = this.x;
        if (this.b.nextInt(10) == 0) {
            ++x;
        }

        int l;
        int i1;

        for (y = 0; y < x; ++y) {
            z = this.c + this.b.nextInt(16) + 8;
            l = this.d + this.b.nextInt(16) + 8;
            i1 = this.a.getHighestBlockYAt(z, l);
            WorldGenTreeAbstract worldgentreeabstract = biomebase.a(this.b);

            worldgentreeabstract.a(1.0D, 1.0D, 1.0D);
            if (worldgentreeabstract.generate(this.a, this.b, z, i1, l)) {
                worldgentreeabstract.b(this.a, this.b, z, i1, l);
            }
        }

        for (y = 0; y < this.H; ++y) {
            z = this.c + this.b.nextInt(16) + 8;
            l = this.d + this.b.nextInt(16) + 8;
            this.s.generate((net.minecraft.server.v1_7_R4.World) this.a, this.b, z, this.a.getHighestBlockYAt(z, l), l);
        }

        for (y = 0; y < this.y; ++y) {
            z = this.c + this.b.nextInt(16) + 8;
            l = this.d + this.b.nextInt(16) + 8;
            i1 = this.b.nextInt(this.a.getHighestBlockYAt(z, l) + 32);
            String s = biomebase.a(this.b, z, i1, l);
            BlockFlowers blockflowers = BlockFlowers.e(s);

            if (blockflowers.getMaterial() != Material.AIR) {
                this.p.a(blockflowers, BlockFlowers.f(s));
                this.p.generate((net.minecraft.server.v1_7_R4.World) this.a, this.b, z, i1, l);
            }
        }

        for (y = 0; y < this.z; ++y) {
            z = this.c + this.b.nextInt(16) + 8;
            l = this.d + this.b.nextInt(16) + 8;
            i1 = this.b.nextInt(this.a.getHighestBlockYAt(z, l) * 2);
            WorldGenerator worldgenerator = biomebase.b(this.b);

            worldgenerator.generate(this.a, this.b, z, i1, l);
        }

        for (y = 0; y < this.A; ++y) {
            z = this.c + this.b.nextInt(16) + 8;
            l = this.d + this.b.nextInt(16) + 8;
            i1 = this.b.nextInt(this.a.getHighestBlockYAt(z, l) * 2);
            new WorldGenDeadBush(Blocks.DEAD_BUSH).generate((net.minecraft.server.v1_7_R4.World) this.a, this.b, z, i1, l);
        }

        for (y = 0; y < this.w; ++y) {
            z = this.c + this.b.nextInt(16) + 8;
            l = this.d + this.b.nextInt(16) + 8;

            for (i1 = this.b.nextInt(this.a.getHighestBlockYAt(z, l) * 2); i1 > 0 && this.a.isEmpty(z, i1 - 1, l); --i1) {
                ;
            }

            this.v.generate(this.a, this.b, z, i1, l);
        }

        for (y = 0; y < this.B; ++y) {
            if (this.b.nextInt(4) == 0) {
                z = this.c + this.b.nextInt(16) + 8;
                l = this.d + this.b.nextInt(16) + 8;
                i1 = this.a.getHighestBlockYAt(z, l);
                this.q.generate(this.a, this.b, z, i1, l);
            }

            if (this.b.nextInt(8) == 0) {
                z = this.c + this.b.nextInt(16) + 8;
                l = this.d + this.b.nextInt(16) + 8;
                i1 = this.b.nextInt(this.a.getHighestBlockYAt(z, l) * 2);
                this.r.generate(this.a, this.b, z, i1, l);
            }
        }

        if (this.b.nextInt(4) == 0) {
            y = this.c + this.b.nextInt(16) + 8;
            z = this.d + this.b.nextInt(16) + 8;
            l = this.b.nextInt(this.a.getHighestBlockYAt(y, z) * 2);
            this.q.generate(this.a, this.b, y, l, z);
        }

        if (this.b.nextInt(8) == 0) {
            y = this.c + this.b.nextInt(16) + 8;
            z = this.d + this.b.nextInt(16) + 8;
            l = this.b.nextInt(this.a.getHighestBlockYAt(y, z) * 2);
            this.r.generate(this.a, this.b, y, l, z);
        }

        for (y = 0; y < this.C; ++y) {
            z = this.c + this.b.nextInt(16) + 8;
            l = this.d + this.b.nextInt(16) + 8;
            i1 = this.b.nextInt(this.a.getHighestBlockYAt(z, l) * 2);
            this.t.generate(this.a, this.b, z, i1, l);
        }

        for (y = 0; y < 10; ++y) {
            z = this.c + this.b.nextInt(16) + 8;
            l = this.d + this.b.nextInt(16) + 8;
            i1 = this.b.nextInt(this.a.getHighestBlockYAt(z, l) * 2);
            this.t.generate(this.a, this.b, z, i1, l);
        }

        if (this.b.nextInt(32) == 0) {
            y = this.c + this.b.nextInt(16) + 8;
            z = this.d + this.b.nextInt(16) + 8;
            l = this.b.nextInt(this.a.getHighestBlockYAt(y, z) * 2);
            (new WorldGenPumpkin()).generate(this.a, this.b, y, l, z);
        }

        for (y = 0; y < this.D; ++y) {
            z = this.c + this.b.nextInt(16) + 8;
            l = this.d + this.b.nextInt(16) + 8;
            i1 = this.b.nextInt(this.a.getHighestBlockYAt(z, l) * 2);
            this.u.generate(this.a, this.b, z, i1, l);
        }

        if (this.I) {
            for (y = 0; y < 50; ++y) {
                z = this.c + this.b.nextInt(16) + 8;
                l = this.b.nextInt(this.b.nextInt(248) + 8);
                i1 = this.d + this.b.nextInt(16) + 8;
                (new WorldGenLiquids(Blocks.WATER)).generate(this.a, this.b, z, l, i1);
            }

            for (y = 0; y < 20; ++y) {
                z = this.c + this.b.nextInt(16) + 8;
                l = this.b.nextInt(this.b.nextInt(this.b.nextInt(240) + 8) + 8);
                i1 = this.d + this.b.nextInt(16) + 8;
                (new WorldGenLiquids(Blocks.LAVA)).generate(this.a, this.b, z, l, i1);
            }
        }
    }

    @Override
    protected void a(int i, WorldGenerator worldgenerator, int j, int k) {
        for (int l = 0; l < i; ++l) {
            int i1 = this.c + this.b.nextInt(16);
            int j1 = this.b.nextInt(k - j) + j;
            int k1 = this.d + this.b.nextInt(16);

            worldgenerator.generate(this.a, this.b, i1, j1, k1);
        }
    }

    @Override
    protected void b(int i, WorldGenerator worldgenerator, int j, int k) {
        for (int l = 0; l < i; ++l) {
            int i1 = this.c + this.b.nextInt(16);
            int j1 = this.b.nextInt(k) + this.b.nextInt(k) + (j - k);
            int k1 = this.d + this.b.nextInt(16);

            worldgenerator.generate(this.a, this.b, i1, j1, k1);
        }
    }
             
    @Override
	protected void a() {
            this.a(this.chunkProperties.dirtCount, this.h, this.chunkProperties.dirtMinHeight, this.chunkProperties.dirtMaxHeight);
            this.a(this.chunkProperties.gravelCount, this.i, this.chunkProperties.gravelMinHeight, this.chunkProperties.gravelMaxHeight);
            this.a(this.chunkProperties.dioriteCount, this.k, this.chunkProperties.dioriteMinHeight, this.chunkProperties.dioriteMaxHeight);
            this.a(this.chunkProperties.graniteCount, this.j, this.chunkProperties.graniteMinHeight, this.chunkProperties.graniteMaxHeight);
            this.a(this.chunkProperties.andesiteCount, this.l, this.chunkProperties.andesiteMinHeight, this.chunkProperties.andesiteMaxHeight);
            this.a(this.chunkProperties.coalCount, this.m, this.chunkProperties.coalMinHeight, this.chunkProperties.coalMaxHeight);
            this.a(this.chunkProperties.ironCount, this.n, this.chunkProperties.ironMinHeight, this.chunkProperties.ironMaxHeight);
            this.a(this.chunkProperties.goldCount, this.o, this.chunkProperties.goldMinHeight, this.chunkProperties.goldMaxHeight);
            this.a(this.chunkProperties.redstoneCount, this.p, this.chunkProperties.redstoneMinHeight, this.chunkProperties.redstoneMaxHeight);
            this.a(this.chunkProperties.diamondCount, this.q, this.chunkProperties.diamondMinHeight, this.chunkProperties.diamondMaxHeight);
            this.b(this.chunkProperties.lapisCount, this.r, this.chunkProperties.lapisCenterHeight, this.chunkProperties.lapisSpread);
	}
}