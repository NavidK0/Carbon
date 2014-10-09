package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.sounds.Sounds;
import net.minecraft.server.v1_7_R4.EnumMobType;
import net.minecraft.server.v1_7_R4.Material;

/**
 *
 * @author Navid
 */
public class BlockWoodPressurePlate extends BlockPressurePlateBinary {

    public BlockWoodPressurePlate() {
        super("planks_oak", Material.WOOD, EnumMobType.EVERYTHING);
        a(Sounds.WOOD);
        c(0.5f);
        c("pressurePlate");
    }

}
