package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.sounds.Sounds;
import net.minecraft.server.v1_7_R4.Material;

/**
 *
 * @author Navid
 */
public class BlockGoldPressurePlate extends BlockPressurePlateWeighted {

    public BlockGoldPressurePlate() {
        super("gold_block", Material.ORE, 15);
        c(0.5f);
        a(Sounds.WOOD);
        c("weightedPlate_light");
    }

}
