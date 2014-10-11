package com.lastabyss.carbon.inventory;

import org.bukkit.DyeColor;

import net.minecraft.server.v1_7_R4.IInventory;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.Slot;

public class SlotLapis extends Slot {

	public SlotLapis(IInventory iinventory, int i, int j, int k) {
		super(iinventory, i, j, k);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isAllowed(ItemStack itemStack) {
		return itemStack.getItem() == Items.INK_SACK && DyeColor.getByDyeData((byte) itemStack.getData()) == DyeColor.BLUE;
	}

}
