package net.o2gaming.carbon.blocks;

import net.minecraft.server.v1_7_R4.BlockTrapdoor;
import net.minecraft.server.v1_7_R4.Material;


/**
 *
 * @author Navid
 */
public class BlockIronTrapdoor extends BlockTrapdoor {

    public BlockIronTrapdoor() {
        super(Material.ORE);
        c(3.0f);
        b(25);
        c("iron_trapdoor");
        d("iron_trapdoor");
        H();
    }
}
