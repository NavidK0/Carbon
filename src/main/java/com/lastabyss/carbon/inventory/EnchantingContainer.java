package com.lastabyss.carbon.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.Enchantment;
import net.minecraft.server.v1_7_R4.EnchantmentInstance;
import net.minecraft.server.v1_7_R4.EnchantmentManager;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.IInventory;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.PlayerInventory;
import net.minecraft.server.v1_7_R4.World;

public class EnchantingContainer extends net.minecraft.server.v1_7_R4.ContainerEnchantTable {

	private EntityPlayer player;
	private Random random = new Random();
	private World world;
	private int x;
	private int y;
	private int z;

	public EnchantingContainer(PlayerInventory playerInventory, World world, int x, int y, int z) {
		super(playerInventory, world, x, y, z);
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.player = (EntityPlayer) playerInventory.player;
	}

	@Override
	public void a(IInventory iinventory) {
		// TODO: add known enchantment calculation
		if (iinventory == this.enchantSlots) {
			ItemStack itemstack = iinventory.getItem(0);
			if (itemstack != null) {
				this.f = this.random.nextLong();
				int bookShelfs = 0;
				for (int addZ = -1; addZ <= 1; addZ++) {
					for (int addX = -1; addX <= 1; addX++) {
						if (((addZ != 0) || (addX != 0)) && (this.world.isEmpty(this.x + addX, this.y, this.z + addZ)) && (this.world.isEmpty(this.x + addX, this.y + 1, this.z + addZ))) {
							if (this.world.getType(this.x + addX * 2, this.y, this.z + addZ * 2) == Blocks.BOOKSHELF) {
								bookShelfs++;
							}
							if (this.world.getType(this.x + addX * 2, this.y + 1, this.z + addZ * 2) == Blocks.BOOKSHELF) {
								bookShelfs++;
							}
							if ((addX != 0) && (addZ != 0)) {
								if (this.world.getType(this.x + addX * 2, this.y, this.z + addZ) == Blocks.BOOKSHELF) {
									bookShelfs++;
								}
								if (this.world.getType(this.x + addX * 2, this.y + 1, this.z + addZ) == Blocks.BOOKSHELF) {
									bookShelfs++;
								}
								if (this.world.getType(this.x + addX, this.y, this.z + addZ * 2) == Blocks.BOOKSHELF) {
									bookShelfs++;
								}
								if (this.world.getType(this.x + addX, this.y + 1, this.z + addZ * 2) == Blocks.BOOKSHELF) {
									bookShelfs++;
								}
							}
						}
					}
				}
				for (int i = 0; i < 3; i++) {
					this.costs[i] = EnchantmentManager.a(random, i, bookShelfs, itemstack);
				}
				CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
				PrepareItemEnchantEvent event = new PrepareItemEnchantEvent(this.player.getBukkitEntity(), getBukkitView(), this.world.getWorld().getBlockAt(this.x, this.y, this.z), item, this.costs, bookShelfs);
				event.setCancelled(!itemstack.x());
				this.world.getServer().getPluginManager().callEvent(event);
				if (event.isCancelled()) {
					for (bookShelfs = 0; bookShelfs < 3; bookShelfs++) {
						this.costs[bookShelfs] = 0;
					}
					return;
				}
				b();
			} else {
				for (int i = 0; i < 3; i++) {
					this.costs[i] = 0;
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean a(EntityHuman entityhuman, int i) {
		ItemStack itemstack = this.enchantSlots.getItem(0);
		if ((this.costs[i] > 0) && (itemstack != null) && ((entityhuman.expLevel >= this.costs[i]) || (entityhuman.abilities.canInstantlyBuild))) {
			if (!this.world.isStatic) {
				List<?> list = EnchantmentManager.b(random, itemstack, this.costs[i]);
				if (list == null) {
					list = new ArrayList<Object>();
				}
				boolean isBook = itemstack.getItem() == Items.BOOK;
				if (list != null) {
					Map<org.bukkit.enchantments.Enchantment, Integer> enchants = new HashMap<org.bukkit.enchantments.Enchantment, Integer>();
					for (Object obj : list) {
						EnchantmentInstance instance = (EnchantmentInstance) obj;
						enchants.put(org.bukkit.enchantments.Enchantment.getById(instance.enchantment.id), Integer.valueOf(instance.level));
					}
					CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);

					EnchantItemEvent event = new EnchantItemEvent((Player) entityhuman.getBukkitEntity(), getBukkitView(), this.world.getWorld().getBlockAt(this.x, this.y, this.z), item, i + 1, enchants, i);
					this.world.getServer().getPluginManager().callEvent(event);

					int level = event.getExpLevelCost();
					if ((event.isCancelled()) || ((level > entityhuman.expLevel) && (!entityhuman.abilities.canInstantlyBuild)) || (event.getEnchantsToAdd().isEmpty())) {
						return false;
					}
					if (isBook) {
						itemstack.setItem(Items.ENCHANTED_BOOK);
					}
					for (Map.Entry<org.bukkit.enchantments.Enchantment, Integer> entry : event.getEnchantsToAdd().entrySet()) {
						try {
							if (isBook) {
								int enchantId = ((org.bukkit.enchantments.Enchantment) entry.getKey()).getId();
								if (Enchantment.byId[enchantId] == null) {
									continue;
								}
								EnchantmentInstance enchantment = new EnchantmentInstance(enchantId, ((Integer) entry.getValue()).intValue());
								Items.ENCHANTED_BOOK.a(itemstack, enchantment);
							} else {
								item.addUnsafeEnchantment((org.bukkit.enchantments.Enchantment) entry.getKey(), ((Integer) entry.getValue()).intValue());
							}
						} catch (IllegalArgumentException e) {
						}
					}
					entityhuman.levelDown(-level);

					a(this.enchantSlots);
				}
			}
			return true;
		}
		return false;
	}

}
