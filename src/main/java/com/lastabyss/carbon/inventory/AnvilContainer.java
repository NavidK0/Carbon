package com.lastabyss.carbon.inventory;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

import com.lastabyss.carbon.utils.Utilities;

import net.minecraft.server.v1_7_R4.Enchantment;
import net.minecraft.server.v1_7_R4.EnchantmentManager;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.IInventory;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.PlayerInventory;
import net.minecraft.server.v1_7_R4.World;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;

public class AnvilContainer extends net.minecraft.server.v1_7_R4.ContainerAnvil {

	private IInventory result;
	private IInventory crafting;
	private EntityHuman human;

	public AnvilContainer(PlayerInventory playerInventory, World world, int anvilX, int anvilY, int anvilZ, EntityHuman who) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		super(playerInventory, world, anvilX, anvilY, anvilZ, who);
		result = (IInventory) Utilities.setAccessible(Field.class, getClass().getSuperclass().getDeclaredField("g"), true).get(this);
		crafting = (IInventory) Utilities.setAccessible(Field.class, getClass().getSuperclass().getDeclaredField("h"), true).get(this);
		human = who;
	}

	//field a is the repair cost
	@SuppressWarnings("unchecked")
	@Override
	public void e() {
		try {
			ItemStack itemToRepair = this.crafting.getItem(0);
			this.a = 1;
			int addedRepairCost = 0;
			if (itemToRepair == null) {
				this.result.setItem(0, (ItemStack) null);
				this.a = 0;
			} else {
				ItemStack itemToRepairClone = itemToRepair.cloneItemStack();
				ItemStack itemDonor = this.crafting.getItem(1);
				Map<Integer, Integer> itemEnchants = EnchantmentManager.a(itemToRepairClone);
				boolean isBookWithEnchants = false;
				int penaltyRepairCost = itemToRepair.getRepairCost() + (itemDonor == null ? 0 : itemDonor.getRepairCost());
				setRepairAmount(0);
				if (itemDonor != null) {
					isBookWithEnchants = itemDonor.getItem() == Items.ENCHANTED_BOOK && Items.ENCHANTED_BOOK.g(itemDonor).size() > 0;
					if (itemToRepairClone.g() && itemToRepairClone.getItem().a(itemToRepair, itemDonor)) {
						int l = Math.min(itemToRepairClone.j(), itemToRepairClone.l() / 4);
						if (l <= 0) {
							this.result.setItem(0, (ItemStack) null);
							this.a = 0;
							return;
						}
						int repairAmount = 0;
						for (repairAmount = 0; l > 0 && repairAmount < itemDonor.count; ++repairAmount) {
							itemToRepairClone.setData(itemToRepairClone.j() - l);
							addedRepairCost++;
							l = Math.min(itemToRepairClone.j(), itemToRepairClone.l() / 4);
						}
						setRepairAmount(repairAmount);
					} else {
						if (!isBookWithEnchants && (itemToRepairClone.getItem() != itemDonor.getItem() || !itemToRepairClone.g())) {
							this.result.setItem(0, (ItemStack) null);
							this.a = 0;
							return;
						}
						if (itemToRepairClone.g() && !isBookWithEnchants) {
							int repairAmount = itemToRepair.l() - itemToRepair.j() + itemDonor.l() - itemDonor.j() + itemToRepairClone.l() * 12 / 100;
							int repairedWearout = itemToRepairClone.l() - repairAmount;
							if (repairedWearout < 0) {
								repairedWearout = 0;
							}
							if (repairedWearout < itemToRepairClone.j()) {
								itemToRepairClone.setData(repairedWearout);
								addedRepairCost += 2;
							}
						}
						Map<Integer, Integer> donorEnchants = EnchantmentManager.a(itemDonor);
						Iterator<Integer> donorEnchantsIdIterator = donorEnchants.keySet().iterator();
						while (donorEnchantsIdIterator.hasNext()) {
							int donorEnchantId = donorEnchantsIdIterator.next();
							Enchantment donorEnchantment = Enchantment.byId[donorEnchantId];
							int itemEnchantLevel = itemEnchants.containsKey(donorEnchantId) ? itemEnchants.get(donorEnchantId) : 0;
							int donorEnchantLevel = donorEnchants.get(donorEnchantId);
							int newItemEnchantLevel;
							if (itemEnchantLevel == donorEnchantLevel) {
								newItemEnchantLevel = ++donorEnchantLevel;
							} else {
								newItemEnchantLevel = Math.max(donorEnchantLevel, itemEnchantLevel);
							}
							donorEnchantLevel = newItemEnchantLevel;
							boolean canEnchant = donorEnchantment.canEnchant(itemToRepair) || this.human.abilities.canInstantlyBuild || itemToRepair.getItem() == Items.ENCHANTED_BOOK;
							Iterator<Integer> itemEnchantsIdIterator = itemEnchants.keySet().iterator();
							while (itemEnchantsIdIterator.hasNext()) {
								int itemEnchantId = itemEnchantsIdIterator.next();
								if (itemEnchantId != donorEnchantId && !donorEnchantment.a(Enchantment.byId[itemEnchantId])) {
									canEnchant = false;
									addedRepairCost++;
								}
							}
							if (canEnchant) {
								if (donorEnchantLevel > donorEnchantment.getMaxLevel()) {
									donorEnchantLevel = donorEnchantment.getMaxLevel();
								}
								itemEnchants.put(donorEnchantId, donorEnchantLevel);
								int enchantCost = 0;
								switch (donorEnchantment.getRandomWeight()) {
									case 1: {
										enchantCost = 8;
										break;
									}
									case 2: {
										enchantCost = 4;
										break;
									}
									case 5: {
										enchantCost = 2;
										break;
									}
									case 10: {
										enchantCost = 1;
										break;
									}
									default: {
										break;
									}
								}
								if (isBookWithEnchants) {
									enchantCost = Math.max(1, enchantCost / 2);
								}
								addedRepairCost += enchantCost * donorEnchantLevel;
							}
						}
					}
				}
				int renameAddedRepairCost = 0;
				String resultItemName = getResultItemName();
				if (StringUtils.isBlank(resultItemName)) {
					if (itemToRepair.hasName()) {
						renameAddedRepairCost = 1;
						addedRepairCost += renameAddedRepairCost;
						itemToRepairClone.t();
					}
				} else if (!resultItemName.equals(itemToRepair.getName())) {
					renameAddedRepairCost = 1;
					addedRepairCost += renameAddedRepairCost;
					itemToRepairClone.c(resultItemName);
				}
				this.a = penaltyRepairCost + addedRepairCost;
				if (addedRepairCost <= 0) {
					itemToRepairClone = null;
				}
				if (renameAddedRepairCost == addedRepairCost && renameAddedRepairCost > 0 && this.a >= 40) {
					this.a = 39;
				}
				if (this.a >= 40 && !this.human.abilities.canInstantlyBuild) {
					itemToRepairClone = null;
				}
				if (itemToRepairClone != null) {
					int repairPenalty = itemToRepairClone.getRepairCost();
					if (itemDonor != null && repairPenalty < itemDonor.getRepairCost()) {
						repairPenalty = itemDonor.getRepairCost();
					}
					repairPenalty = repairPenalty * 2 + 1;
					itemToRepairClone.setRepairCost(repairPenalty);
					EnchantmentManager.a(itemEnchants, itemToRepairClone);
				}
				this.result.setItem(0, itemToRepairClone);
				this.b();
			}
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace(System.out);
			human.getBukkitEntity().closeInventory();
		}
	}

	private String getResultItemName() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return (String) Utilities.setAccessible(Field.class, getClass().getSuperclass().getDeclaredField("n"), true).get(this);
	}

	private void setRepairAmount(int cost) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Utilities.setAccessible(Field.class, getClass().getSuperclass().getDeclaredField("m"), true).set(this, cost);
	}

}
