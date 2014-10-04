package com.lastabyss.carbon.blocks;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.CreativeModeTab;
import net.minecraft.server.v1_7_R4.Material;

/**
 *
 * @author Navid
 */
public class BlockPrismarine extends Block {
    
    public static final String[] names = {"prismarine_rough", "prismarine_bricks", "prismarine_dark"};
    
    public BlockPrismarine() {
        super(Material.STONE);
        a(CreativeModeTab.b);
        //Sets hardness of block
        c(1.2f);
        //Sets blast
        b(10.0F);
    }

    @Override
    public int getDropData(int i) {
        return i;
    }
    
}
