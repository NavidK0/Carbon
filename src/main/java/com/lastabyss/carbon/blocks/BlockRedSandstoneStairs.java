package com.lastabyss.carbon.blocks;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.BlockStairs;

/**
 *
 * @author Navid
 */
public class BlockRedSandstoneStairs extends BlockStairs {

    public BlockRedSandstoneStairs(Block block, int i) {
        super(block, i);
        c("red_sandstone_stairs");
        c(0.8f);
        b(4);
    }

    
}
