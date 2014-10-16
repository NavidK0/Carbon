package org.bukkit.craftbukkit.v1_7_R4.inventory;

import com.google.common.collect.ImmutableSet;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class CraftItemFactory implements ItemFactory {

	private CraftItemFactory() {
	}

	static final Color DEFAULT_LEATHER_COLOR = Color.fromRGB(10511680);

	static final Collection<String> KNOWN_NBT_ATTRIBUTE_NAMES = ImmutableSet.<String>builder()
		.add("generic.attackDamage")
		.add("generic.followRange")
		.add("generic.knockbackResistance")
		.add("generic.maxHealth")
		.add("generic.movementSpeed")
		.add("horse.jumpStrength")
		.add("zombie.spawnReinforcements")
	.build();

	private static final CraftItemFactory instance = new CraftItemFactory();

	static {
		ConfigurationSerialization.registerClass(CraftMetaItem.SerializableMeta.class);
	}

	public boolean isApplicable(ItemMeta meta, ItemStack itemstack) {
		if (itemstack == null) {
			return false;
		}
		return isApplicable(meta, itemstack.getType());
	}

	public boolean isApplicable(ItemMeta meta, Material type) {
		if ((type == null) || (meta == null)) {
			return false;
		}
		if (!(meta instanceof CraftMetaItem)) {
			throw new IllegalArgumentException("Meta of " + meta.getClass().toString() + " not created by " + CraftItemFactory.class.getName());
		}
		return ((CraftMetaItem) meta).applicableTo(type);
	}

	public ItemMeta getItemMeta(Material material) {
		Validate.notNull(material, "Material cannot be null");
		return getItemMeta(material, null);
	}

	private ItemMeta getItemMeta(Material material, CraftMetaItem meta) {
		if (material == Material.AIR) {
			return null;
		}
		if (material == Material.WRITTEN_BOOK || material == Material.BOOK_AND_QUILL) {
			return (meta instanceof CraftMetaBook) ? meta : new CraftMetaBook(meta);
		}
		if (material == Material.SKULL_ITEM) {
			return (meta instanceof CraftMetaSkull) ? meta : new CraftMetaSkull(meta);
		}
		if (material == Material.LEATHER_HELMET || material == Material.LEATHER_CHESTPLATE || material == Material.LEATHER_LEGGINGS || material == Material.LEATHER_BOOTS) {
			return (meta instanceof CraftMetaLeatherArmor) ? meta : new CraftMetaLeatherArmor(meta);
		}
		if (material == Material.POTION) {
			return (meta instanceof CraftMetaPotion) ? meta : new CraftMetaPotion(meta);
		}
		if (material == Material.MAP) {
			return (meta instanceof CraftMetaMap) ? meta : new CraftMetaMap(meta);
		}
		if (material == Material.FIREWORK) {
			return (meta instanceof CraftMetaFirework) ? meta : new CraftMetaFirework(meta);
		}
		if (material == Material.FIREWORK_CHARGE) {
			return (meta instanceof CraftMetaCharge) ? meta : new CraftMetaCharge(meta);
		}
		if (material == Material.ENCHANTED_BOOK) {
			return (meta instanceof CraftMetaEnchantedBook) ? meta : new CraftMetaEnchantedBook(meta);
		}
		if (material.toString().equals("BANNER")) {
			if (meta != null && BannerMeta.class.isAssignableFrom(meta.getClass())) {
				return meta;
			} else {
				return new BannerMeta(meta);
			}
		}
		return new CraftMetaItem(meta);
	}

	public boolean equals(ItemMeta meta1, ItemMeta meta2) {
		if (meta1 == meta2) {
			return true;
		}
		if ((meta1 != null) && (!(meta1 instanceof CraftMetaItem))) {
			throw new IllegalArgumentException("First meta of " + meta1.getClass().getName() + " does not belong to " + CraftItemFactory.class.getName());
		}
		if ((meta2 != null) && (!(meta2 instanceof CraftMetaItem))) {
			throw new IllegalArgumentException("Second meta " + meta2.getClass().getName() + " does not belong to " + CraftItemFactory.class.getName());
		}
		if (meta1 == null) {
			return ((CraftMetaItem) meta2).isEmpty();
		}
		if (meta2 == null) {
			return ((CraftMetaItem) meta1).isEmpty();
		}
		return equals((CraftMetaItem) meta1, (CraftMetaItem) meta2);
	}

	boolean equals(CraftMetaItem meta1, CraftMetaItem meta2) {
		return (meta1.equalsCommon(meta2)) && (meta1.notUncommon(meta2)) && (meta2.notUncommon(meta1));
	}

	public static CraftItemFactory instance() {
		return instance;
	}

	public ItemMeta asMetaFor(ItemMeta meta, ItemStack stack) {
		Validate.notNull(stack, "Stack cannot be null");
		return asMetaFor(meta, stack.getType());
	}

	public ItemMeta asMetaFor(ItemMeta meta, Material material) {
		Validate.notNull(material, "Material cannot be null");
		if (!(meta instanceof CraftMetaItem)) {
			throw new IllegalArgumentException("Meta of " + (meta != null ? meta.getClass().toString() : "null") + " not created by " + CraftItemFactory.class.getName());
		}
		return getItemMeta(material, (CraftMetaItem) meta);
	}

	public Color getDefaultLeatherColor() {
		return DEFAULT_LEATHER_COLOR;
	}
}
