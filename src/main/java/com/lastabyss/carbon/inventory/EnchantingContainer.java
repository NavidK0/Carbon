package com.lastabyss.carbon.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

import com.lastabyss.carbon.inventory.bukkit.CraftInventoryEnchanting;
import com.lastabyss.carbon.utils.Utilities;

import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.Container;
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
import net.minecraft.server.v1_7_R4.Slot;
import net.minecraft.server.v1_7_R4.World;

public class EnchantingContainer extends Container {

	private EnchantingContainerInventory enchantSlots = new EnchantingContainerInventory(this, "Enchant", true, 2);
	private EntityPlayer player;
	private Random random = new Random();
	private World world;
	private int x;
	private int y;
	private int z;
	private int[] costs = new int[3];
	private int[] nonRandomEnchants = new int[] { -1, -1, -1 };
	private int enchantSeed;
	private Player bukkitPlayer;

	public EnchantingContainer(PlayerInventory playerInventory, World world, int x, int y, int z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.bukkitPlayer = ((Player) playerInventory.player.getBukkitEntity());
		this.enchantSlots.player = this.bukkitPlayer;
		this.player = (EntityPlayer) playerInventory.player;
		this.enchantSeed = ((AdditionalNBTDataPlayerAbilities) player.abilities).getEnchantSeed();
		a(new SlotEnchant(this, this.enchantSlots, 0, 15, 47));
		a(new SlotLapis(this.enchantSlots, 1, 35, 47));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				a(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			a(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void addSlotListener(ICrafting icrafting) {
		super.addSlotListener(icrafting);
		icrafting.setContainerData(this, 0, this.costs[0]);
		icrafting.setContainerData(this, 1, this.costs[1]);
		icrafting.setContainerData(this, 2, this.costs[2]);
		if (Utilities.getProtocolVersion(player.getBukkitEntity()) == Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
			icrafting.setContainerData(this, 3, (int) (this.enchantSeed & -16));
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
				icrafting.setContainerData(this, 3, (int) (this.enchantSeed & -16));
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

				this.random.setSeed(this.enchantSeed);

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
		ItemStack lapisItem = this.enchantSlots.getItem(1);
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

					EnchantItemEvent event = new EnchantItemEvent((Player) entityhuman.getBukkitEntity(), getBukkitView(), this.world.getWorld().getBlockAt(this.x, this.y, this.z), item, costs[i], enchants, i);
					this.world.getServer().getPluginManager().callEvent(event);

					int level = i + 1;
					boolean hasEnoughLapis = false;
					if (level == 0) {
						hasEnoughLapis = true;
					}
					if (lapisItem != null && level <= lapisItem.count) {
						hasEnoughLapis = true;
					}
					if (((EntityPlayer) entityhuman).playerConnection.networkManager.getVersion() != Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
						hasEnoughLapis = true;
					}
					if ((event.isCancelled()) || ((!hasEnoughLapis || level > entityhuman.expLevel) && (!entityhuman.abilities.canInstantlyBuild)) || (event.getEnchantsToAdd().isEmpty())) {
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
					if (!entityhuman.abilities.canInstantlyBuild) {
						if (lapisItem != null) {
							lapisItem.count -= level;
							if (lapisItem.count <= 0) {
								this.enchantSlots.setItem(1, (ItemStack) null);
							}
						}
					}
					this.enchantSeed = ((AdditionalNBTDataPlayerAbilities) player.abilities).nextEnchantSeed();
					a(this.enchantSlots);
				}
			}
			return true;
		}
		return false;
	}

	private List<EnchantmentInstance> getEnchantsToAdd(ItemStack itemstack, int i, int cost) {
		random.setSeed(this.enchantSeed + i);
		@SuppressWarnings("unchecked")
		List<EnchantmentInstance> list = EnchantmentManager.b(random, itemstack, costs[i]);
		if (itemstack.getItem() == Items.BOOK && list != null && list.size() > 1) {
			list.remove(this.random.nextInt(list.size()));
		}
		return list;
	}

	@Override
	public void b(EntityHuman entityhuman) {
		super.b(entityhuman);
		ItemStack itemstack = this.enchantSlots.splitWithoutUpdate(0);
		if (itemstack != null) {
			entityhuman.drop(itemstack, false);
		}
		ItemStack itemlapis = this.enchantSlots.splitWithoutUpdate(1);
		if (itemlapis != null) {
			entityhuman.drop(itemlapis, false);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public ItemStack b(EntityHuman entityhuman, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.c.get(i);
		if ((slot != null) && (slot.hasItem())) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.cloneItemStack();
			if (i == 0) {
				if (!a(itemstack1, 2, 38, true)) {
					return null;
				}
			} else if (i == 1) {
				if (!this.a(itemstack1, 2, 38, true)) {
					return null;
				}
			} else if (itemstack1.getItem() == Items.INK_SACK && DyeColor.getByData((byte) itemstack1.getData()) == DyeColor.BLUE) {
				if (!this.a(itemstack1, 1, 2, true)) {
					return null;
				}
			} else {
				if ((((Slot) this.c.get(0)).hasItem()) || (!((Slot) this.c.get(0)).isAllowed(itemstack1))) {
					return null;
				}
				if ((itemstack1.hasTag()) && (itemstack1.count == 1)) {
					((Slot) this.c.get(0)).set(itemstack1.cloneItemStack());
					itemstack1.count = 0;
				} else if (itemstack1.count >= 1) {
					((Slot) this.c.get(0)).set(new ItemStack(itemstack1.getItem(), 1, itemstack.getData()));
					itemstack1.count -= 1;
				}
			}
			if (itemstack1.count == 0) {
				slot.set((ItemStack) null);
			} else {
				slot.f();
			}
			if (itemstack1.count == itemstack.count) {
				return null;
			}
			slot.a(entityhuman, itemstack1);
		}
		return itemstack;
	}

	@Override
	public boolean a(EntityHuman human) {
	    return this.world.getType(this.x, this.y, this.z) == Blocks.ENCHANTMENT_TABLE;
	}

	private CraftInventoryView bukkitView;
	public CraftInventoryView getBukkitView() {
		if (this.bukkitView != null) {
			return this.bukkitView;
		}
		CraftInventoryEnchanting inventory = new CraftInventoryEnchanting(this.enchantSlots);
		this.bukkitView = new CraftInventoryView(this.bukkitPlayer, inventory, this);
		return this.bukkitView;
	}

}
