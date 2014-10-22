package com.lastabyss.carbon.inventory.bukkit;

import com.lastabyss.carbon.inventory.EnchantingContainerInventory;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryEnchanting extends CraftInventory implements EnchantingInventory {

	public CraftInventoryEnchanting(EnchantingContainerInventory inventory) {
		super(inventory);
	}

	public void setItem(ItemStack item) {
		setItem(0, item);
	}

	public ItemStack getItem() {
		return getItem(0);
	}

}
