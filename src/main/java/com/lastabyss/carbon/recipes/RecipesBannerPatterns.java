package com.lastabyss.carbon.recipes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.Recipe;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.recipes.bukkit.DynamicBannerRecipe;

import net.minecraft.server.v1_7_R4.IRecipe;
import net.minecraft.server.v1_7_R4.InventoryCrafting;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;
import net.minecraft.server.v1_7_R4.World;

public class RecipesBannerPatterns implements IRecipe {

	@Override
	public int a() {
		return 10;
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
		ItemStack result = null;

		for (int i = 0; i < inventoryCrafting.getSize(); ++i) {
			ItemStack itemStackInSlot = inventoryCrafting.getItem(i);
			if (itemStackInSlot != null && itemStackInSlot.getItem() == Carbon.injector().standingBannerItem) {
				result = itemStackInSlot.cloneItemStack();
				result.count = 1;
				break;
			}
		}

		EnumBannerPatterns enumPattern = this.getPattern(inventoryCrafting);
		if (enumPattern != null) {
			int color = 0;
			ItemStack itemStack;
			for (int i = 0; i < inventoryCrafting.getSize(); ++i) {
				itemStack = inventoryCrafting.getItem(i);
				if (itemStack != null && itemStack.getItem() == Items.INK_SACK) {
					color = itemStack.getData();
					break;
				}
			}

			if (result.getTag() == null) {
				result.setTag(new NBTTagCompound());
			}
			if (!result.getTag().hasKey("BlockEntityTag")) {
				NBTTagCompound compund = new NBTTagCompound();
				result.getTag().set("BlockEntityTag", compund);
			}
			NBTTagCompound compound = result.getTag().getCompound("BlockEntityTag");
			NBTTagList patterns;
			if (compound.hasKeyOfType("Patterns", 9)) {
				patterns = compound.getList("Patterns", 10);
			} else {
				patterns = new NBTTagList();
				compound.set("Patterns", patterns);
			}
			NBTTagCompound pattern = new NBTTagCompound();
			pattern.setString("Pattern", enumPattern.getPatternName());
			pattern.setInt("Color", color);
			patterns.add(pattern);
		}

		return result;
	}

	@Override
	public boolean a(InventoryCrafting inventoryCrafting, World world) {
		boolean flag = false;

		for (int i = 0; i < inventoryCrafting.getSize(); ++i) {
			ItemStack itemStack = inventoryCrafting.getItem(i);
			if (itemStack != null && itemStack.getItem() == Carbon.injector().standingBannerItem) {
				if (flag) {
					return false;
				}

				if (EnumBannerPatterns.getPatternsCount(itemStack) >= 6) {
					return false;
				}

				flag = true;
			}
		}

		if (!flag) {
			return false;
		} else {
			return this.getPattern(inventoryCrafting) != null;
		}
	}

	private EnumBannerPatterns getPattern(InventoryCrafting inventoryCrafting) {
		for (EnumBannerPatterns bannerPattern : EnumBannerPatterns.values()) {
			if (bannerPattern.canBeCrafted()) {
				boolean patternFound = true;
				if (bannerPattern.needsItem()) {
					boolean foundItem = false;
					boolean foundDye = false;

					for (int i = 0; i < inventoryCrafting.getSize() && patternFound; ++i) {
						ItemStack itemStackInSlot = inventoryCrafting.getItem(i);
						if (itemStackInSlot != null && itemStackInSlot.getItem() != Carbon.injector().standingBannerItem) {
							if (itemStackInSlot.getItem() == Items.INK_SACK) {
								if (foundDye) {
									patternFound = false;
									break;
								}
								foundDye = true;
							} else {
								if (foundItem || !itemStackInSlot.doMaterialsMatch(bannerPattern.getItem())) {
									patternFound = false;
									break;
								}
								foundItem = true;
							}
						}
					}

					if (!foundItem) {
						patternFound = false;
					}
				} else if (inventoryCrafting.getSize() != bannerPattern.getCraftingGrid().length * bannerPattern.getCraftingGrid()[0].length()) {
					patternFound = false;
				} else {
					int data = -1;
					for (int i = 0; i < inventoryCrafting.getSize() && patternFound; ++i) {
						int row = i / 3;
						int column = i % 3;
						ItemStack itemStackInSlot = inventoryCrafting.getItem(i);
						if (itemStackInSlot != null && itemStackInSlot.getItem() != Carbon.injector().standingBannerItem) {
							if (itemStackInSlot.getItem() != Items.INK_SACK) {
								patternFound = false;
								break;
							}
							if (data != -1 && data != itemStackInSlot.getData()) {
								patternFound = false;
								break;
							}

							if (bannerPattern.getCraftingGrid()[row].charAt(column) == 32) {
								patternFound = false;
								break;
							}

							data = itemStackInSlot.getData();
						} else if (bannerPattern.getCraftingGrid()[row].charAt(column) != 32) {
							patternFound = false;
							break;
						}
					}
				}

				if (patternFound) {
					return bannerPattern;
				}
			}
		}

		return null;
	}

}
