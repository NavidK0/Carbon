package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.Carbon;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.BlockFence;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.Material;

/**
 *
 * @author Navid
 */
public class BlockWoodenFence extends BlockFence {

    public BlockWoodenFence(String string, Material mtrl) {
        super(string, mtrl);
        c(2f);
    }
    
    public static boolean a(Block block) {
        return block == Blocks.FENCE || block == Blocks.NETHER_FENCE ||
                block == Carbon.injector().acaciaFenceBlock || block == Carbon.injector().birchFenceBlock ||
                block == Carbon.injector().darkOakFenceBlock || block == Carbon.injector().jungleFenceBlock ||
                block == Carbon.injector().spruceFenceBlock;
    }

}
