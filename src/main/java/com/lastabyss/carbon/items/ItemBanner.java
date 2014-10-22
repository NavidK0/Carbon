package com.lastabyss.carbon.items;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.entity.TileEntityBanner;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.CreativeModeTab;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.ItemBlock;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.TileEntity;
import net.minecraft.server.v1_7_R4.World;

import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlock;

public class ItemBanner extends ItemBlock {

	public ItemBanner(Block block) {
		super(block);
		this.maxStackSize = 16;
		a(CreativeModeTab.l);
		this.a(true);
		this.setMaxDurability(0);
	}

	@Override
	public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int x, int y, int z, int blockFace, float paramFloat1, float paramFloat2, float paramFloat3) {
		if (blockFace == CraftBlock.blockFaceToNotch(BlockFace.DOWN)) {
			return false;
		}
		if (paramWorld.getType(x, y, z).getMaterial().isReplaceable()) {
			return false;
		}
		org.bukkit.block.Block bblock = paramWorld.getWorld().getBlockAt(x, y, z);
		bblock = bblock.getRelative(CraftBlock.notchToBlockFace(blockFace));
		x = bblock.getX();
		y = bblock.getY();
		z = bblock.getZ();
		if (!paramEntityHuman.a(x, y, z, blockFace, paramItemStack) || !paramWorld.getType(x, y, z).getMaterial().isReplaceable()) {
			return false;
		}
		if (blockFace == CraftBlock.blockFaceToNotch(BlockFace.UP)) {
			int data = MathHelper.floor(((paramEntityHuman.yaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
			paramWorld.setTypeAndData(x, y, z, Carbon.injector().standingBannerBlock, data, 2);
		} else {
			paramWorld.setTypeAndData(x, y, z, Carbon.injector().wallBannerBlock, blockFace, 2);
		}

		--paramItemStack.count;
		TileEntity te = paramWorld.getTileEntity(x, y, z);
		if (te instanceof TileEntityBanner) {
			((TileEntityBanner) te).setItemValues(paramItemStack);
		}

		return true;
	}

}