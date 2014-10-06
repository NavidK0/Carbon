package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.sounds.Sounds;
import net.minecraft.server.v1_7_R4.Material;

/**
 *
 * @author Navid
 */
public class BlockIronPressurePlate extends BlockPressurePlateWeighted {

    public BlockIronPressurePlate() {
        super("iron_block", Material.ORE, 150);
        c(0.5f);
        a(Sounds.WOOD);
        c("weightedPlate_heavy");
    }

}
