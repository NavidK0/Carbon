package com.lastabyss.carbon.inventory;

import net.minecraft.server.v1_7_R4.IInventory;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Slot;

public class SlotEnchant extends Slot {

	SlotEnchant(EnchantingContainer paramContainerEnchantTable, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3) {
		super(paramIInventory, paramInt1, paramInt2, paramInt3);
	}

	@Override
	public int getMaxStackSize() {
		return 1;
	}

	@Override
	public boolean isAllowed(ItemStack paramItemStack) {
		return true;
	}

}
