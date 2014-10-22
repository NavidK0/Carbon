
package com.lastabyss.carbon.blocks;

import java.util.Random;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.CreativeModeTab;
import net.minecraft.server.v1_7_R4.Item;


public class BlockStone extends net.minecraft.server.v1_7_R4.BlockStone {
    
    public static final String[] names = {"stone", "andesite", "smooth_andesite", "diorite", "smooth_diorite", "granite", "smooth_granite"};
  
    public BlockStone() {
        a(CreativeModeTab.b);
        //Sets hardness of block
        c(1.5f);
        //Sets blast
        b(10.0F);
    }

    @Override
    public Item getDropType(int i, Random random, int j) {
        if (i == 0) {
            return Item.getItemOf(Blocks.COBBLESTONE);
        } else {
            return Item.getItemOf(this);
        }
    }

    @Override
    public int getDropData(int i) {
        return i;
    }
}
