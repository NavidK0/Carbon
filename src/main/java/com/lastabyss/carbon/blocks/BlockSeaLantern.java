package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.Carbon;
import java.util.Random;
import net.minecraft.server.v1_7_R4.BlockLightStone;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.Material;

/**
 *
 * @author Navid
 */
public class BlockSeaLantern extends BlockLightStone {

    public BlockSeaLantern() {
        super(Material.SHATTERABLE);
        c(0.3f);
        a(1.0f);
        b(1.5f);
    }
    
    @Override
    public int getDropCount(int paramInt, Random paramRandom) {
    return paramRandom.nextInt(1) + 2;
    }

    @Override
    public int a(Random paramRandom) {
    return 2 + paramRandom.nextInt(3);
    }

    @Override
    public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
    return Carbon.injector().prismarineCrystalItem;
    }

    @Override
    protected boolean E() {
        return true;
    }

    @Override
    public boolean G() {
        return true;
    }
}
