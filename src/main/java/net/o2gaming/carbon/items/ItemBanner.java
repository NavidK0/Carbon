/**
package net.o2gaming.carbon.items;

import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.CreativeModeTab;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.ItemBlock;
import net.minecraft.server.v1_7_R4.LocaleI18n;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.Position;
import net.minecraft.server.v1_7_R4.TileEntity;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

public class ItemBanner extends ItemBlock {

	public ItemBanner() {
		super(Blocks.STANDING_BANNER);
		this.maxStackSize = 16;
		a(CreativeModeTab.l);
                
		this.a(true);
		this.setDurability(0);
	}

	public boolean a(ItemStack var1, EntityHuman var2, World var3, Position var4, BlockFace var5, float var6, float var7, float var8) {
		if (var5 == BlockFace.DOWN) {
			return false;
		} else if (!var3.getBlockState(var4).getBlock().getMaterial().isBuildable()) {
			return false;
		} else {
			var4 = var4.getRelative(var5);
			if (!var2.a(var4, var5, var1)) {
				return false;
			} else if (!Blocks.STANDING_BANNER.c(var3, var4)) {
				return false;
			} else if (var3.isStatic) {
				return true;
			} else {
				if (var5 == BlockFace.UP) {
					int var9 = MathHelper.toFixedPointInt((double) ((var2.yaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
					var3.setBlockAt(var4, Blocks.STANDING_BANNER.getBlockState().a(BlockStandingSign.a, Integer.valueOf(var9)), 3);
				} else {
					var3.setBlockAt(var4, Blocks.WALL_BANNER.getBlockState().a(BlockWallSign.a, var5), 3);
				}

				--var1.amount;
				TileEntity var10 = var3.getTileEntity(var4);
				if (var10 instanceof TileEntityBanner) {
					((TileEntityBanner) var10).a(var1);
				}

				return true;
			}
		}
	}

	public String a(ItemStack var1) {
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
	}
}
* **/