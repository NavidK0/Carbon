package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.Carbon;
import java.util.Random;
import net.minecraft.server.v1_7_R4.BlockStepAbstract;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.CreativeModeTab;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Material;

/**
 *
 * @author Navid
 */
public class BlockStep extends BlockStepAbstract {
    public static final String[] b = {"redSandStone"};
    private boolean redSand;
    
    public BlockStep(boolean doubled, boolean redSand) {
        super(doubled, Material.STONE);
        a(CreativeModeTab.b);
        this.redSand = redSand;
        c(2.0f);
        b(10.0f);
        c("stoneSlab2");
    }

    @Override
    public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
        if (!redSand)
            return Item.getItemOf(Blocks.STEP);
        else
            return Carbon.injector().redSandstoneSlabItem;
    }

    @Override
    protected ItemStack j(int paramInt) {
        if (!redSand)
            return new ItemStack(Item.getItemOf(Blocks.STEP), 2, paramInt & 0x7);
        else
            return new ItemStack(Carbon.injector().redSandstoneSlabItem, 2, paramInt & 0x7);
    }

    @Override
    public String b(int paramInt) {
        if ((paramInt < 0) || (paramInt >= b.length)) {
            paramInt = 0;
        }
        return super.a() + "." + b[paramInt];
    }
}