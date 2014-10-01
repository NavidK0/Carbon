package com.lastabyss.carbon.blocks;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.CreativeModeTab;
import net.minecraft.server.v1_7_R4.Material;

public class BlockRedSandstone extends Block
{
    public static final String[] names = { "default", "chiseled", "smooth" };

    public BlockRedSandstone() {
        super(Material.STONE);
        a(CreativeModeTab.b);
        c(0.8f);
    }

    @Override
    public int getDropData(int i) {
            return i;
    }
}