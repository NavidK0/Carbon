package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.entity.TileEntityBanner;

import java.util.Random;

import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.BlockContainer;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.TileEntity;
import net.minecraft.server.v1_7_R4.World;

public class BlockBanner extends BlockContainer {

	public BlockBanner() {
		super(Material.WOOD);
		this.isTileEntity = true;
		c(1.0f);
	}

	@Override
	public TileEntity a(World arg0, int arg1) {
		return new TileEntityBanner();
	}

	@Override
	public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
		return null;
	}

	@Override
	public Item getDropType(int i, Random random, int j) {
		return Carbon.injector().standingBannerItem;
	}

	@Override
	public int b() {
		return -1;
	}

	@Override
	public boolean c() {
		return false;
	}

	@Override
	public boolean d() {
		return false;
	}

	@Override
	public void dropNaturally(World world, int x, int y, int z, int data, float chance, int idk) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof TileEntityBanner) {
			ItemStack itemStack = new net.minecraft.server.v1_7_R4.ItemStack(Carbon.injector().standingBannerItem, 1, ((TileEntityBanner) tileEntity).getBaseColor());
			NBTTagCompound compound = new NBTTagCompound();
			tileEntity.b(compound);
			compound.remove("x");
			compound.remove("y");
			compound.remove("z");
			compound.remove("id");
			itemStack.setTag(new NBTTagCompound());
			itemStack.getTag().set("BlockEntityTag", compound);
			a(world, x, y, z, itemStack);
		} else {
			super.dropNaturally(world, x, y, z, data, chance, idk);
		}
	}

}