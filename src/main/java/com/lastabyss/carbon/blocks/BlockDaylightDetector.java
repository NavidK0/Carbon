package com.lastabyss.carbon.blocks;

import java.util.Random;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.BlockContainer;
import net.minecraft.server.v1_7_R4.CreativeModeTab;
import net.minecraft.server.v1_7_R4.EnumSkyBlock;
import net.minecraft.server.v1_7_R4.IBlockAccess;
import net.minecraft.server.v1_7_R4.IIcon;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.TileEntity;
import net.minecraft.server.v1_7_R4.TileEntityLightDetector;
import net.minecraft.server.v1_7_R4.World;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;

public class BlockDaylightDetector extends BlockContainer {

    private IIcon[] a = new IIcon[2];
    private final boolean inverted;

    public BlockDaylightDetector(boolean inverted) {
        super(Material.WOOD);
        this.inverted = inverted;
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
        this.a(CreativeModeTab.d);
    }

    @Override
    public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
    }

    @Override
    public int b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return iblockaccess.getData(i, j, k);
    }

    @Override
    public void a(World world, int i, int j, int k, Random random) {}

    @Override
    public void doPhysics(World world, int i, int j, int k, Block block) {}

    @Override
    public void onPlace(World world, int i, int j, int k) {}

    public void e(World world, int i, int j, int k) {
        if (!world.worldProvider.g) {
            int data = world.getData(i, j, k);
            int getLightFor = world.b(EnumSkyBlock.SKY, i, j, k) - world.j;
            float celestialAngleRadians = world.d(1.0F);

            if (celestialAngleRadians < 3.1415927F) {
                celestialAngleRadians += (0.0F - celestialAngleRadians) * 0.2F;
            } else {
                celestialAngleRadians += (6.2831855F - celestialAngleRadians) * 0.2F;
            }

            getLightFor = Math.round((float) getLightFor * MathHelper.cos(celestialAngleRadians));
            if (getLightFor < 0) {
                getLightFor = 0;
            }

            if (getLightFor > 15) {
                getLightFor = 15;
            }

            if (data != getLightFor) {
                getLightFor = CraftEventFactory.callRedstoneChange(world, i, j, k, data, getLightFor).getNewCurrent(); // CraftBukkit - Call BlockRedstoneEvent
                world.setData(i, j, k, getLightFor, 3);
            }
        }
    }

    @Override
    public boolean d() {
        return false;
    }

    @Override
    public boolean c() {
        return false;
    }

    @Override
    public boolean isPowerSource() {
        return true;
    }

    @Override
    public TileEntity a(World world, int i) {
        return new TileEntityLightDetector();
    }
}