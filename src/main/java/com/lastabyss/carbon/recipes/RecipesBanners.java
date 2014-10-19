package com.lastabyss.carbon.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import com.lastabyss.carbon.Carbon;

import net.minecraft.server.v1_7_R4.CraftingManager;

/**
 *
 * @author Navid
 */
public class RecipesBanners {

	@SuppressWarnings("unchecked")
	public void register() {
		//basic recipes
		for (int c = 0; c < 16; c++) {
			@SuppressWarnings("deprecation")
			ShapedRecipe defaultBanners = new ShapedRecipe(new ItemStack(Carbon.injector().bannerItemMat, 1, (short) (15 - c))).shape(new String[] { "www", "www", " s " }).setIngredient('w', Material.WOOL, c).setIngredient('s', Material.STICK);
			Bukkit.addRecipe(defaultBanners);
		}

		//patterns recipe
		CraftingManager.getInstance().getRecipes().add(new RecipesBannerPatterns());

		//copy recipe
		CraftingManager.getInstance().getRecipes().add(new RecipesBannerCopy());
	}

}
