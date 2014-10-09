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

import com.lastabyss.carbon.utils.Utilities;

import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.Enchantment;
import net.minecraft.server.v1_7_R4.EnchantmentInstance;
import net.minecraft.server.v1_7_R4.EnchantmentManager;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.ICrafting;
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
	private int[] nonRandomEnchants = new int[] { -1, -1, -1 };

	public EnchantingContainer(PlayerInventory playerInventory, World world, int x, int y, int z) {
		super(playerInventory, world, x, y, z);
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.player = (EntityPlayer) playerInventory.player;
	}

	@Override
	public void addSlotListener(ICrafting icrafting) {
		super.addSlotListener(icrafting);
		icrafting.setContainerData(this, 0, this.costs[0]);
		icrafting.setContainerData(this, 1, this.costs[1]);
		icrafting.setContainerData(this, 2, this.costs[2]);
		if (Utilities.getProtocolVersion(player.getBukkitEntity()) == Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
			icrafting.setContainerData(this, 3, (int) (this.f & -16));
			icrafting.setContainerData(this, 4, this.nonRandomEnchants[0]);
			icrafting.setContainerData(this, 5, this.nonRandomEnchants[1]);
			icrafting.setContainerData(this, 6, this.nonRandomEnchants[2]);
		}
	}

	@Override
	public void b() {
		super.b();
		for (int i = 0; i < this.listeners.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.listeners.get(i);
			icrafting.setContainerData(this, 0, this.costs[0]);
			icrafting.setContainerData(this, 1, this.costs[1]);
			icrafting.setContainerData(this, 2, this.costs[2]);
			if (Utilities.getProtocolVersion(player.getBukkitEntity()) == Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
				icrafting.setContainerData(this, 3, (int) (this.f & -16));
				icrafting.setContainerData(this, 4, this.nonRandomEnchants[0]);
				icrafting.setContainerData(this, 5, this.nonRandomEnchants[1]);
				icrafting.setContainerData(this, 6, this.nonRandomEnchants[2]);
			}
		}
	}

	@Override
	public void a(IInventory iinventory) {
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
					this.nonRandomEnchants[i] = -1;
					if (this.costs[i] < i + 1) {
						this.costs[i] = 0;
					}
				}

				for (int i = 0; i < 3; ++i) {
					if (this.costs[i] > 0) {
						List<EnchantmentInstance> list = getEnchantsToAdd(itemstack, i, costs[i]);
						if (list != null && !list.isEmpty()) {
							EnchantmentInstance enchantmentWithLevel = (EnchantmentInstance) list.get(this.random.nextInt(list.size()));
							this.nonRandomEnchants[i] = enchantmentWithLevel.enchantment.id | enchantmentWithLevel.level << 8;
						}
					}
				}

				CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
				PrepareItemEnchantEvent event = new PrepareItemEnchantEvent(this.player.getBukkitEntity(), getBukkitView(), this.world.getWorld().getBlockAt(this.x, this.y, this.z), item, this.costs, bookShelfs);
				event.setCancelled(!itemstack.x());
				this.world.getServer().getPluginManager().callEvent(event);
				if (event.isCancelled()) {
					for (int i = 0; i < 3; i++) {
						this.costs[i] = 0;
						this.nonRandomEnchants[i] = -1;
					}
					return;
				}
				b();
			} else {
				for (int i = 0; i < 3; i++) {
					this.costs[i] = 0;
					this.nonRandomEnchants[i] = -1;
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
				List<?> list = getEnchantsToAdd(itemstack, i, this.costs[i]);
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

	private List<EnchantmentInstance> getEnchantsToAdd(ItemStack itemstack, int i, int cost) {
		random.setSeed(this.f + i);
		@SuppressWarnings("unchecked")
		List<EnchantmentInstance> list = EnchantmentManager.b(random, itemstack, costs[i]);
		if (itemstack.getItem() == Items.BOOK && list != null && list.size() > 1) {
			list.remove(this.random.nextInt(list.size()));
		}
		return list;
	}

}
