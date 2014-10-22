package com.lastabyss.carbon.recipes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.Recipe;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.recipes.bukkit.DynamicBannerRecipe;

import net.minecraft.server.v1_7_R4.IRecipe;
import net.minecraft.server.v1_7_R4.InventoryCrafting;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.World;

public class RecipesBannerCopy implements IRecipe {

	@Override
	public int a() {
		return 2;
	}

	@Override
	public ItemStack b() {
		return null;
	}

	@Override
	public List<ItemStack> getIngredients() {
		return new ArrayList<ItemStack>();
	}

	@Override
	public Recipe toBukkitRecipe() {
		return new DynamicBannerRecipe();
	}

	@Override
	public ItemStack a(InventoryCrafting inventoryCrafting) {
		for (int i = 0; i < inventoryCrafting.getSize(); ++i) {
			ItemStack itemStackInSlot = inventoryCrafting.getItem(i);
			if (itemStackInSlot != null && EnumBannerPatterns.getPatternsCount(itemStackInSlot) > 0) {
				ItemStack copiedBanner = itemStackInSlot.cloneItemStack();
				copiedBanner.count = 2;
				return copiedBanner;
			}
		}
		return null;
	}

	@Override
	public boolean a(InventoryCrafting inventoryCrafting, World var2) {
		ItemStack bannerToCopyFrom = null;
		ItemStack bannerToCopyTo = null;
		for (int i = 0; i < inventoryCrafting.getSize(); ++i) {
			ItemStack itemStackInSlot = inventoryCrafting.getItem(i);
			if (itemStackInSlot != null) {
				if (itemStackInSlot.getItem() != Carbon.injector().standingBannerItem) {
					return false;
				}

				if (bannerToCopyFrom != null && bannerToCopyTo != null) {
					return false;
				}

				int baseColor = EnumBannerPatterns.getBaseColor(itemStackInSlot);
				boolean hasPatterns = EnumBannerPatterns.getPatternsCount(itemStackInSlot) > 0;
				if (bannerToCopyFrom != null) {
					if (hasPatterns) {
						return false;
					}

					if (baseColor != (EnumBannerPatterns.getBaseColor(bannerToCopyFrom))) {
						return false;
					}

					bannerToCopyTo = itemStackInSlot;
				} else if (bannerToCopyTo != null) {
					if (!hasPatterns) {
						return false;
					}

					if (baseColor != (EnumBannerPatterns.getBaseColor(bannerToCopyTo))) {
						return false;
					}

					bannerToCopyFrom = itemStackInSlot;
				} else if (hasPatterns) {
					bannerToCopyFrom = itemStackInSlot;
				} else {
					bannerToCopyTo = itemStackInSlot;
				}
			}
		}

		return bannerToCopyFrom != null && bannerToCopyTo != null;
	}

}
