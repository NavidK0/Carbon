package com.lastabyss.carbon.blocks;

import java.util.Random;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.CreativeModeTab;
import net.minecraft.server.v1_7_R4.EnumSkyBlock;
import net.minecraft.server.v1_7_R4.IBlockAccess;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.TileEntity;
import net.minecraft.server.v1_7_R4.TileEntityLightDetector;
import net.minecraft.server.v1_7_R4.World;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;

public class BlockDaylightDetector extends net.minecraft.server.v1_7_R4.BlockDaylightDetector {

    private boolean inverted;

    public BlockDaylightDetector(boolean inverted) {
        super();
        this.inverted = inverted;
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
        c(0.2f);
        this.a(CreativeModeTab.d);
    }

    public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
    }

    public int b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return iblockaccess.getData(i, j, k);
    }

    public void a(World world, int i, int j, int k, Random random) {}

    public void doPhysics(World world, int i, int j, int k, Block block) {}

    public void onPlace(World world, int i, int j, int k) {}

    public void e(World world, int x, int y, int z) {
        //If (world.worldProvider.hasNoSky())
        if (!world.worldProvider.g) {
            int oldCurrent = world.getData(x, y, z);
            int newCurrent = world.b(EnumSkyBlock.SKY, x, y, z) - world.j;
            float f = world.d(1.0F);

            if (f < 3.1415927F) {
                f += (0.0F - f) * 0.2F;
            } else {
                f += (6.2831855F - f) * 0.2F;
            }

            newCurrent = Math.round((float) newCurrent * MathHelper.cos(f));
            if (newCurrent < 0) {
                newCurrent = 0;
            }

            if (newCurrent > 15) {
                newCurrent = 15;
            }
            
            if (inverted) {
                newCurrent = 15 - newCurrent;
            }

            if (oldCurrent != newCurrent) {
                newCurrent = CraftEventFactory.callRedstoneChange(world, x, y, z, oldCurrent, newCurrent).getNewCurrent(); // CraftBukkit - Call BlockRedstoneEvent
                world.setData(x, y, z, newCurrent, 3);
            }
        }
    }

    @Override
    public Item getDropType(int i, Random random, int j) {
        return Item.getItemOf(Blocks.DAYLIGHT_DETECTOR);
    }

    public boolean d() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public boolean isPowerSource() {
        return true;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    public boolean isInverted() {
        return inverted;
    }

    public TileEntity a(World world, int i) {
        return new TileEntityLightDetector();
    }
}
