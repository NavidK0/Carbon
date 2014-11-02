package com.lastabyss.carbon.generator.populator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

/**
 *
 * @author Navid
 */
public class StoneVariantPopulator extends BlockPopulator {

    private Material blockType;
    private byte data;
    private int pockets;
    private int max;

    public StoneVariantPopulator(Material blockType, byte data, int max, int pockets) {
        this.blockType = blockType;
        this.data = data;
        this.max = max;
        this.pockets = pockets;
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int x, y, z, i;
        int worldChunkX = chunk.getX() * 16;
        int worldChunkZ = chunk.getZ() * 16;
        for (i = 0; i < pockets; i++) {
            x = worldChunkX + random.nextInt(16);
            z = worldChunkZ + random.nextInt(16);
            y = random.nextInt(257);
            this.createVein(world, max, x, y, z);
        }
    }
    
    @SuppressWarnings("deprecation")
	public void createVein(World world, int size, int x, int y, int z) {
        Random random = new Random(world.getSeed());
        float f = random.nextFloat() * 3.141593F;
        double d1 = x + 8 + Math.sin(f) * size / 8.0F;
        double d2 = x + 8 - Math.sin(f) * size / 8.0F;
        double d3 = z + 8 + Math.cos(f) * size / 8.0F;
        double d4 = z + 8 - Math.cos(f) * size / 8.0F;
        double d5 = y + random.nextInt(3) - 2;
        double d6 = y + random.nextInt(3) - 2;
        for (int i = 0; i <= size; i++) {
            double d7 = d1 + (d2 - d1) * i / size;
            double d8 = d5 + (d6 - d5) * i / size;
            double d9 = d3 + (d4 - d3) * i / size;
            double d10 = random.nextDouble() * size / 16.0D;
            double d11 = (Math.sin(i * 3.141593F / size) + 1.0F) * d10 + 1.0D;
            double d12 = (Math.sin(i * 3.141593F / size) + 1.0F) * d10 + 1.0D;
            int j = (int) Math.floor(d7 - d11 / 2.0D);
            int k = (int) Math.floor(d8 - d12 / 2.0D);
            int m = (int) Math.floor(d9 - d11 / 2.0D);
            int n = (int) Math.floor(d7 + d11 / 2.0D);
            int i1 = (int) Math.floor(d8 + d12 / 2.0D);
            int i2 = (int) Math.floor(d9 + d11 / 2.0D);
            for (int i3 = j; i3 <= n; i3++) {
                double d13 = (i3 + 0.5D - d7) / (d11 / 2.0D);
                if (d13 * d13 < 1.0D) {
                    for (int i4 = k; i4 <= i1; i4++) {
                        double d14 = (i4 + 0.5D - d8) / (d12 / 2.0D);
                        if (d13 * d13 + d14 * d14 < 1.0D) {
                            for (int i5 = m; i5 <= i2; i5++) {
                                double d15 = (i5 + 0.5D - d9) / (d11 / 2.0D);
                                if ((d13 * d13 + d14 * d14 + d15 * d15 >= 1.0D) || (world.getBlockAt(i3, i4, i5).getType() != Material.STONE)) {
                                    continue;
                                }
                                world.getBlockAt(i3, i4, i5).setTypeIdAndData(blockType.getId(), data, true);
                            }
                        }
                    }
                }
            }
        }
    }

}
