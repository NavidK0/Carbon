package com.lastabyss.carbon.items;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlock;

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
		org.bukkit.block.Block bblock = paramWorld.getWorld().getBlockAt(x, y, z);
		bblock = bblock.getRelative(CraftBlock.notchToBlockFace(blockFace));
		x = bblock.getX();
		y = bblock.getY();
		z = bblock.getZ();
		if (!paramWorld.getType(x, y, z).getMaterial().isReplaceable()) {
			return false;
		}
		/*var4 = var4.getRelative(var5);
		if (!var2.a(var4, var5, var1)) {
			return false;
		} else {*/
			if (blockFace == CraftBlock.blockFaceToNotch(BlockFace.UP)) {
				int data = MathHelper.floor(((paramEntityHuman.yaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
				paramWorld.setTypeAndData(x, y, z, Carbon.injector().standingBannerBlock, data, 2);
			} else {
				//paramWorld.setBlockAt(x, y, z, Carbon.injector().wallBannerBlock, 0, 2);
			}

			--paramItemStack.count;
			TileEntity te = paramWorld.getTileEntity(x, y, z);
			if (te instanceof TileEntityBanner) {
				((TileEntityBanner) te).setItemValues(paramItemStack);
			}

			return true;
		//}
	}

	/*public String a(ItemStack var1) {
		String var2 = "item.banner.";
		akv var3 = this.h(var1);
		var2 = var2 + var3.d() + ".name";
		return LocaleI18n.get(var2);
	}

	private akv h(ItemStack var1) {
		NBTCompoundTag var2 = var1.a("BlockEntityTag", false);
		akv var3 = null;
		if (var2 != null && var2.hasKey("Base")) {
			var3 = akv.a(var2.getInt("Base"));
		} else {
			var3 = akv.a(var1.getWearout());
		}

		return var3;
	}*/

}