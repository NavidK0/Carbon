package com.lastabyss.carbon.inventory;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_7_R4.InventorySubcontainer;
import net.minecraft.server.v1_7_R4.ItemStack;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

public class EnchantingContainerInventory extends InventorySubcontainer {

	final EnchantingContainer enchantTable;
	public List<HumanEntity> transaction = new ArrayList<HumanEntity>();
	public Player player;
	private int maxStack = 64;

	EnchantingContainerInventory(EnchantingContainer containerenchanttable, String s, boolean flag, int i) {
		super(s, flag, i);
		this.enchantTable = containerenchanttable;
	}

	public ItemStack[] getContents() {
		return this.items;
	}

	public void onOpen(CraftHumanEntity who) {
		this.transaction.add(who);
	}

	public void onClose(CraftHumanEntity who) {
		this.transaction.remove(who);
	}

	public List<HumanEntity> getViewers() {
		return this.transaction;
	}

	public InventoryHolder getOwner() {
		return this.player;
	}

	public void setMaxStackSize(int size) {
		this.maxStack = size;
	}

	public int getMaxStackSize() {
		return this.maxStack;
	}

	public void update() {
		super.update();
		this.enchantTable.a(this);
	}

}
