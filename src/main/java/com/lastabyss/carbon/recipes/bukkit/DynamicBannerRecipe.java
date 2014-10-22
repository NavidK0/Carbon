package com.lastabyss.carbon.recipes.bukkit;

import com.lastabyss.carbon.Carbon;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class DynamicBannerRecipe implements Recipe {

	private ItemStack banner = new ItemStack(Carbon.injector().bannerItemMat);

	@Override
	public ItemStack getResult() {
		return banner;
	}

}
