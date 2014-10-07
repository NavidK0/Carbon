package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.sounds.Sounds;
import net.minecraft.server.v1_7_R4.EnumMobType;
import net.minecraft.server.v1_7_R4.Material;

/**
 *
 * @author Navid
 */
public class BlockStonePressurePlate extends BlockPressurePlateBinary {

    public BlockStonePressurePlate() {
        super("stone", Material.STONE, EnumMobType.MOBS);
        a(Sounds.STONE);
        c(0.5f);
        c("pressurePlate");
    }

}
