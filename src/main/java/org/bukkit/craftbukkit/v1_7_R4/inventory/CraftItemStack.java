package org.bukkit.craftbukkit.v1_7_R4.inventory;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import net.minecraft.server.v1_7_R4.EnchantmentManager;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@DelegateDeserialization(org.bukkit.inventory.ItemStack.class)
public final class CraftItemStack extends org.bukkit.inventory.ItemStack {

	@SuppressWarnings("deprecation")
	public static net.minecraft.server.v1_7_R4.ItemStack asNMSCopy(org.bukkit.inventory.ItemStack original) {
		if ((original instanceof CraftItemStack)) {
			CraftItemStack stack = (CraftItemStack) original;
			return stack.handle == null ? null : stack.handle.cloneItemStack();
		}
		if ((original == null) || (original.getTypeId() <= 0)) {
			return null;
		}
		Item item = CraftMagicNumbers.getItem(original.getType());
		if (item == null) {
			return null;
		}
		net.minecraft.server.v1_7_R4.ItemStack stack = new net.minecraft.server.v1_7_R4.ItemStack(item, original.getAmount(), original.getDurability());
		if (original.hasItemMeta()) {
			setItemMeta(stack, original.getItemMeta());
		}
		return stack;
	}

	public static net.minecraft.server.v1_7_R4.ItemStack copyNMSStack(net.minecraft.server.v1_7_R4.ItemStack original, int amount) {
		net.minecraft.server.v1_7_R4.ItemStack stack = original.cloneItemStack();
		stack.count = amount;
		return stack;
	}

	public static org.bukkit.inventory.ItemStack asBukkitCopy(net.minecraft.server.v1_7_R4.ItemStack original) {
		if (original == null) {
			return new org.bukkit.inventory.ItemStack(Material.AIR);
		}
		ItemStack stack = new ItemStack(CraftMagicNumbers.getMaterial(original.getItem()), original.count, (short) original.getData());
		if (hasItemMeta(original)) {
			stack.setItemMeta(getItemMeta(original));
		}
		return stack;
	}

	public static CraftItemStack asCraftMirror(net.minecraft.server.v1_7_R4.ItemStack original) {
		return new CraftItemStack(original);
	}

	public static CraftItemStack asCraftCopy(org.bukkit.inventory.ItemStack original) {
		if ((original instanceof CraftItemStack)) {
			CraftItemStack stack = (CraftItemStack) original;
			return new CraftItemStack(stack.handle == null ? null : stack.handle.cloneItemStack());
		}
		return new CraftItemStack(original);
	}

	public static CraftItemStack asNewCraftStack(Item item) {
		return asNewCraftStack(item, 1);
	}

	public static CraftItemStack asNewCraftStack(Item item, int amount) {
		return new CraftItemStack(CraftMagicNumbers.getMaterial(item), amount, (short) 0, null);
	}

	net.minecraft.server.v1_7_R4.ItemStack handle;

	private CraftItemStack(net.minecraft.server.v1_7_R4.ItemStack item) {
		this.handle = item;
	}

	@SuppressWarnings("deprecation")
	private CraftItemStack(org.bukkit.inventory.ItemStack item) {
		this(item.getTypeId(), item.getAmount(), item.getDurability(), item.hasItemMeta() ? item.getItemMeta() : null);
	}

	private CraftItemStack(Material type, int amount, short durability, ItemMeta itemMeta) {
		setType(type);
		setAmount(amount);
		setDurability(durability);
		setItemMeta(itemMeta);
	}

	@SuppressWarnings("deprecation")
	private CraftItemStack(int typeId, int amount, short durability, ItemMeta itemMeta) {
		this(Material.getMaterial(typeId), amount, durability, itemMeta);
	}

	@Override
	@SuppressWarnings("deprecation")
	public int getTypeId() {
		return this.handle != null ? CraftMagicNumbers.getId(this.handle.getItem()) : 0;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setTypeId(int type) {
		if (type == 0) {
			this.handle = null;
		} else if (CraftMagicNumbers.getItem(type) == null) {
			this.handle = null;
		} else if (this.handle == null) {
			this.handle = new net.minecraft.server.v1_7_R4.ItemStack(CraftMagicNumbers.getItem(type), 1, 0);
		} else {
			this.handle.setItem(CraftMagicNumbers.getItem(type));
			if (hasItemMeta()) {
				setItemMeta(this.handle, getItemMeta(this.handle));
			}
		}
		setData(null);
	}

	@Override
	public int getAmount() {
		return this.handle != null ? this.handle.count : 0;
	}

	@Override
	public void setAmount(int amount) {
		if (this.handle == null) {
			return;
		}
		if (amount == 0) {
			this.handle = null;
		} else {
			this.handle.count = amount;
		}
	}

	@Override
	public void setDurability(short durability) {
		if (this.handle != null) {
			this.handle.setData(durability);
		}
	}

	@Override
	public short getDurability() {
		if (this.handle != null) {
			return (short) this.handle.getData();
		}
		return -1;
	}

	@Override
	public int getMaxStackSize() {
		return this.handle == null ? Material.AIR.getMaxStackSize() : this.handle.getItem().getMaxStackSize();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void addUnsafeEnchantment(Enchantment ench, int level) {
		Validate.notNull(ench, "Cannot add null enchantment");
		if (!makeTag(this.handle)) {
			return;
		}
		NBTTagList list = getEnchantmentList(this.handle);
		if (list == null) {
			list = new NBTTagList();
			this.handle.tag.set(CraftMetaItem.ENCHANTMENTS.NBT, list);
		}
		int size = list.size();
		for (int i = 0; i < size; i++) {
			NBTTagCompound tag = list.get(i);
			short id = tag.getShort(CraftMetaItem.ENCHANTMENTS_ID.NBT);
			if (id == ench.getId()) {
				tag.setShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT, (short) level);
				return;
			}
		}
		NBTTagCompound tag = new NBTTagCompound();
		tag.setShort(CraftMetaItem.ENCHANTMENTS_ID.NBT, (short) ench.getId());
		tag.setShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT, (short) level);
		list.add(tag);
	}

	static boolean makeTag(net.minecraft.server.v1_7_R4.ItemStack item) {
		if (item == null) {
			return false;
		}
		if (item.tag == null) {
			item.setTag(new NBTTagCompound());
		}
		return true;
	}

	@Override
	public boolean containsEnchantment(Enchantment ench) {
		return getEnchantmentLevel(ench) > 0;
	}

	@Override
	@SuppressWarnings("deprecation")
	public int getEnchantmentLevel(Enchantment ench) {
		Validate.notNull(ench, "Cannot find null enchantment");
		if (this.handle == null) {
			return 0;
		}
		return EnchantmentManager.getEnchantmentLevel(ench.getId(), this.handle);
	}

	@Override
	@SuppressWarnings("deprecation")
	public int removeEnchantment(Enchantment ench) {
		Validate.notNull(ench, "Cannot remove null enchantment");

		NBTTagList list = getEnchantmentList(this.handle);
		if (list == null) {
			return 0;
		}
		int index = Integer.MIN_VALUE;
		int level = Integer.MIN_VALUE;
		int size = list.size();
		for (int i = 0; i < size; i++) {
			NBTTagCompound enchantment = list.get(i);
			int id = 0xFFFF & enchantment.getShort(CraftMetaItem.ENCHANTMENTS_ID.NBT);
			if (id == ench.getId()) {
				index = i;
				level = 0xFFFF & enchantment.getShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT);
				break;
			}
		}
		if (index == Integer.MIN_VALUE) {
			return 0;
		}
		if (size == 1) {
			this.handle.tag.remove(CraftMetaItem.ENCHANTMENTS.NBT);
			if (this.handle.tag.isEmpty()) {
				this.handle.tag = null;
			}
			return level;
		}
		NBTTagList listCopy = new NBTTagList();
		for (int i = 0; i < size; i++) {
			if (i != index) {
				listCopy.add(list.get(i));
			}
		}
		this.handle.tag.set(CraftMetaItem.ENCHANTMENTS.NBT, listCopy);

		return level;
	}

	@Override
	public Map<Enchantment, Integer> getEnchantments() {
		return getEnchantments(this.handle);
	}

	@SuppressWarnings("deprecation")
	static Map<Enchantment, Integer> getEnchantments(net.minecraft.server.v1_7_R4.ItemStack item) {
		NBTTagList list = (item != null) && (item.hasEnchantments()) ? item.getEnchantments() : null;
		if ((list == null) || (list.size() == 0)) {
			return ImmutableMap.of();
		}
		ImmutableMap.Builder<Enchantment, Integer> result = ImmutableMap.builder();
		for (int i = 0; i < list.size(); i++) {
			int id = 0xFFFF & list.get(i).getShort(CraftMetaItem.ENCHANTMENTS_ID.NBT);
			int level = 0xFFFF & list.get(i).getShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT);

			result.put(Enchantment.getById(id), Integer.valueOf(level));
		}
		return result.build();
	}

	static NBTTagList getEnchantmentList(net.minecraft.server.v1_7_R4.ItemStack item) {
		return (item != null) && (item.hasEnchantments()) ? item.getEnchantments() : null;
	}

	public CraftItemStack clone() {
		CraftItemStack itemStack = (CraftItemStack) super.clone();
		if (this.handle != null) {
			itemStack.handle = this.handle.cloneItemStack();
		}
		return itemStack;
	}

	@Override
	public ItemMeta getItemMeta() {
		return getItemMeta(this.handle);
	}

	public static ItemMeta getItemMeta(net.minecraft.server.v1_7_R4.ItemStack item) {
		if (!hasItemMeta(item)) {
			return CraftItemFactory.instance().getItemMeta(getType(item));
		}
		Material mat = getType(item);
		if (mat == Material.WRITTEN_BOOK || mat == Material.BOOK_AND_QUILL) {
			return new CraftMetaBook(item.tag);
		}
		if (mat == Material.SKULL_ITEM) {
			return new CraftMetaSkull(item.tag);
		}
		if (mat == Material.LEATHER_HELMET || mat == Material.LEATHER_CHESTPLATE || mat == Material.LEATHER_LEGGINGS || mat == Material.LEATHER_BOOTS) {
			return new CraftMetaLeatherArmor(item.tag);
		}
		if (mat == Material.POTION) {
			return new CraftMetaPotion(item.tag);
		}
		if (mat == Material.MAP) {
			return new CraftMetaMap(item.tag);
		}
		if (mat == Material.FIREWORK) {
			return new CraftMetaFirework(item.tag);
		}
		if (mat == Material.FIREWORK_CHARGE) {
			return new CraftMetaCharge(item.tag);
		}
		if (mat == Material.ENCHANTED_BOOK) {
			return new CraftMetaEnchantedBook(item.tag);
		}
		if (mat.toString().equals("BANNER")) {
			return new BannerMeta(item.getData(), item.tag);
		}
		return new CraftMetaItem(item.tag);
	}

	@SuppressWarnings("deprecation")
	static Material getType(net.minecraft.server.v1_7_R4.ItemStack item) {
		Material material = Material.getMaterial(item == null ? 0 : CraftMagicNumbers.getId(item.getItem()));
		return material == null ? Material.AIR : material;
	}

	@Override
	public boolean setItemMeta(ItemMeta itemMeta) {
		return setItemMeta(this.handle, itemMeta);
	}

	public static boolean setItemMeta(net.minecraft.server.v1_7_R4.ItemStack item, ItemMeta itemMeta) {
		if (item == null) {
			return false;
		}
		if (CraftItemFactory.instance().equals(itemMeta, null)) {
			item.tag = null;
			return true;
		}
		if (!CraftItemFactory.instance().isApplicable(itemMeta, getType(item))) {
			return false;
		}
		NBTTagCompound tag = new NBTTagCompound();
		item.setTag(tag);

		((CraftMetaItem) itemMeta).applyToItem(tag);
		return true;
	}

	@Override
	public boolean isSimilar(org.bukkit.inventory.ItemStack stack) {
		if (stack == null) {
			return false;
		}
		if (stack == this) {
			return true;
		}
		if (!(stack instanceof CraftItemStack)) {
			return stack.getClass() == ItemStack.class && stack.isSimilar(this);
		}
		CraftItemStack that = (CraftItemStack) stack;
		if (this.handle == that.handle) {
			return true;
		}
		if ((this.handle == null) || (that.handle == null)) {
			return false;
		}
		if (!(that.getTypeId() == getTypeId() && getDurability() == that.getDurability())) {
			return false;
		}
		return hasItemMeta() ? that.hasItemMeta() && handle.tag.equals(that.handle.tag) : !that.hasItemMeta();
	}

	@Override
	public boolean hasItemMeta() {
		return hasItemMeta(this.handle);
	}

	static boolean hasItemMeta(net.minecraft.server.v1_7_R4.ItemStack item) {
		return !(item == null || item.tag == null || item.tag.isEmpty());
	}

}
