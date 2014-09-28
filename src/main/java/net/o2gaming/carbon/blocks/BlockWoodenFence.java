package net.o2gaming.carbon.blocks;

import net.minecraft.server.v1_7_R4.BlockFence;
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

}
