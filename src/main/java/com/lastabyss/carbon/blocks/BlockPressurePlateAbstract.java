package com.lastabyss.carbon.blocks;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.CreativeModeTab;
import net.minecraft.server.v1_7_R4.IBlockAccess;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.World;

public abstract class BlockPressurePlateAbstract extends net.minecraft.server.v1_7_R4.BlockPressurePlateAbstract {

    protected BlockPressurePlateAbstract(String s, Material material) {
        super(s, material);
        this.a(CreativeModeTab.d);
        this.a(true);
        this.b(this.d(15));
    }

    @Override
    public boolean canPlace(World world, int i, int j, int k) {
        return World.a((IBlockAccess) world, i, j - 1, k) || BlockWoodenFence.a(world.getType(i, j - 1, k));
    }

    @Override
    public void doPhysics(World world, int i, int j, int k, Block block) {
        boolean flag = false;

        if (!World.a((IBlockAccess) world, i, j - 1, k) && !BlockWoodenFence.a(world.getType(i, j - 1, k))) {
            flag = true;
        }

        if (flag) {
            this.b(world, i, j, k, world.getData(i, j, k), 0);
            world.setAir(i, j, k);
        }
    }

}