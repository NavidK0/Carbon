package org.bukkit.craftbukkit.v1_7_R4.inventory;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.IAttribute;
import net.minecraft.server.v1_7_R4.NBTBase;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagDouble;
import net.minecraft.server.v1_7_R4.NBTTagInt;
import net.minecraft.server.v1_7_R4.NBTTagList;
import net.minecraft.server.v1_7_R4.NBTTagLong;
import net.minecraft.server.v1_7_R4.NBTTagString;
import net.minecraft.util.gnu.trove.map.hash.TObjectDoubleHashMap;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.craftbukkit.v1_7_R4.Overridden;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaItem.SerializableMeta;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.spigotmc.ValidateUtils;

@DelegateDeserialization(SerializableMeta.class)
class CraftMetaItem implements ItemMeta, Repairable {

	static class ItemMetaKey {
		final String BUKKIT;
		final String NBT;

		@Retention(RetentionPolicy.SOURCE)
		@Target({ java.lang.annotation.ElementType.FIELD })
		static @interface Specific {
			To value();

			public static enum To {
				BUKKIT, NBT;

				private To() {
				}
			}
		}

		ItemMetaKey(String both) {
			this(both, both);
		}

		ItemMetaKey(String nbt, String bukkit) {
			this.NBT = nbt;
			this.BUKKIT = bukkit;
		}
	}

	@SerializableAs("ItemMeta")
	public static class SerializableMeta implements ConfigurationSerializable {

		private SerializableMeta() {
		}

		static final String TYPE_FIELD = "meta-type";
		static final ImmutableMap<Class<? extends CraftMetaItem>, String> classMap; 
		static final ImmutableMap<String, Constructor<? extends CraftMetaItem>> constructorMap;

		static {
			ImmutableMap.Builder<Class<? extends CraftMetaItem>, String> classMapBuilder = ImmutableMap.builder();
			classMapBuilder
				.put(CraftMetaBook.class, "BOOK")
				.put(CraftMetaSkull.class, "SKULL")
				.put(CraftMetaLeatherArmor.class, "LEATHER_ARMOR")
				.put(CraftMetaMap.class, "MAP")
				.put(CraftMetaPotion.class, "POTION")
				.put(CraftMetaEnchantedBook.class, "ENCHANTED")
				.put(CraftMetaFirework.class, "FIREWORK")
				.put(CraftMetaCharge.class, "FIREWORK_EFFECT")
				.put(CraftMetaItem.class, "UNSPECIFIC");
			classMap = classMapBuilder.build();
			ImmutableMap.Builder<String, Constructor<? extends CraftMetaItem>> classConstructorBuilder = ImmutableMap.builder();
			for (Entry<Class<? extends CraftMetaItem>, String> mapping : classMap.entrySet()) {
				try {
					classConstructorBuilder.put(mapping.getValue(), (mapping.getKey()).getDeclaredConstructor(new Class[] { Map.class }));
				} catch (NoSuchMethodException e) {
					throw new AssertionError(e);
				}
			}
			constructorMap = classConstructorBuilder.build();
		}

		public static ItemMeta deserialize(Map<String, Object> map) throws Throwable {
			Validate.notNull(map, "Cannot deserialize null map");

			String type = getString(map, "meta-type", false);
			Constructor<? extends CraftMetaItem> constructor = constructorMap.get(type);
			if (constructor == null) {
				throw new IllegalArgumentException(type + " is not a valid " + "meta-type");
			}
			try {
				return (ItemMeta) constructor.newInstance(new Object[] { map });
			} catch (InstantiationException e) {
				throw new AssertionError(e);
			} catch (IllegalAccessException e) {
				throw new AssertionError(e);
			} catch (InvocationTargetException e) {
				throw e.getCause();
			}
		}

		public Map<String, Object> serialize() {
			throw new AssertionError();
		}

		static String getString(Map<?, ?> map, Object field, boolean nullable) {
			return (String) getObject(String.class, map, field, nullable);
		}

		static boolean getBoolean(Map<?, ?> map, Object field) {
			Boolean value = (Boolean) getObject(Boolean.class, map, field, true);
			return (value != null) && (value.booleanValue());
		}

		static <T> T getObject(Class<T> clazz, Map<?, ?> map, Object field, boolean nullable) {
			Object object = map.get(field);
			if (clazz.isInstance(object)) {
				return (T) clazz.cast(object);
			}
			if (object == null) {
				if (!nullable) {
					throw new NoSuchElementException(map + " does not contain " + field);
				}
				return null;
			}
			throw new IllegalArgumentException(field + "(" + object + ") is not a valid " + clazz);
		}
	}

	static final ItemMetaKey NAME = new ItemMetaKey("Name", "display-name");
	static final ItemMetaKey DISPLAY = new ItemMetaKey("display");
	static final ItemMetaKey LORE = new ItemMetaKey("Lore", "lore");
	static final ItemMetaKey ENCHANTMENTS = new ItemMetaKey("ench", "enchants");
	static final ItemMetaKey ENCHANTMENTS_ID = new ItemMetaKey("id");
	static final ItemMetaKey ENCHANTMENTS_LVL = new ItemMetaKey("lvl");
	static final ItemMetaKey REPAIR = new ItemMetaKey("RepairCost", "repair-cost");
	static final ItemMetaKey ATTRIBUTES = new ItemMetaKey("AttributeModifiers");
	static final ItemMetaKey ATTRIBUTES_IDENTIFIER = new ItemMetaKey("AttributeName");
	static final ItemMetaKey ATTRIBUTES_NAME = new ItemMetaKey("Name");
	static final ItemMetaKey ATTRIBUTES_VALUE = new ItemMetaKey("Amount");
	static final ItemMetaKey ATTRIBUTES_TYPE = new ItemMetaKey("Operation");
	static final ItemMetaKey ATTRIBUTES_UUID_HIGH = new ItemMetaKey("UUIDMost");
	static final ItemMetaKey ATTRIBUTES_UUID_LOW = new ItemMetaKey("UUIDLeast");
	static final ItemMetaKey UNBREAKABLE = new ItemMetaKey("Unbreakable");
	private String displayName;
	private List<String> lore;
	private Map<Enchantment, Integer> enchantments;
	private int repairCost;
	private final NBTTagList attributes;

	CraftMetaItem(CraftMetaItem meta) {
		if (meta == null) {
			this.attributes = null;
			return;
		}
		this.displayName = meta.displayName;
		if (meta.hasLore()) {
			this.lore = new ArrayList<String>(meta.lore);
		}
		if (meta.hasEnchants()) {
			this.enchantments = new HashMap<Enchantment, Integer>(meta.enchantments);
		}
		this.repairCost = meta.repairCost;
		this.attributes = meta.attributes;
		this.spigot.setUnbreakable(meta.spigot.isUnbreakable());
	}

	CraftMetaItem(NBTTagCompound tag) {
		if (tag.hasKey(DISPLAY.NBT)) {
			NBTTagCompound display = tag.getCompound(DISPLAY.NBT);
			if (display.hasKey(NAME.NBT)) {
				this.displayName = ValidateUtils.limit(display.getString(NAME.NBT), 1024);
			}
			if (display.hasKey(LORE.NBT)) {
				NBTTagList list = display.getList(LORE.NBT, 8);
				this.lore = new ArrayList<String>(list.size());
				for (int index = 0; index < list.size(); index++) {
					String line = ValidateUtils.limit(list.getString(index), 1024);
					this.lore.add(line);
				}
			}
		}
		this.enchantments = buildEnchantments(tag, ENCHANTMENTS);
		if (tag.hasKey(REPAIR.NBT)) {
			this.repairCost = tag.getInt(REPAIR.NBT);
		}
		if ((tag.get(ATTRIBUTES.NBT) instanceof NBTTagList)) {
			NBTTagList save = null;
			NBTTagList nbttaglist = tag.getList(ATTRIBUTES.NBT, 10);

			TObjectDoubleHashMap<String> attributeTracker = new TObjectDoubleHashMap<String>();
			TObjectDoubleHashMap<String> attributeTrackerX = new TObjectDoubleHashMap<String>();
			Map<String, IAttribute> attributesByName = new HashMap<String, IAttribute>();
			attributeTracker.put("generic.maxHealth", 20.0D);
			attributesByName.put("generic.maxHealth", GenericAttributes.maxHealth);
			attributeTracker.put("generic.followRange", 32.0D);
			attributesByName.put("generic.followRange", GenericAttributes.b);
			attributeTracker.put("generic.knockbackResistance", 0.0D);
			attributesByName.put("generic.knockbackResistance", GenericAttributes.c);
			attributeTracker.put("generic.movementSpeed", 0.7D);
			attributesByName.put("generic.movementSpeed", GenericAttributes.d);
			attributeTracker.put("generic.attackDamage", 1.0D);
			attributesByName.put("generic.attackDamage", GenericAttributes.e);
			NBTTagList oldList = nbttaglist;
			nbttaglist = new NBTTagList();

			List<NBTTagCompound> op0 = new ArrayList<NBTTagCompound>();
			List<NBTTagCompound> op1 = new ArrayList<NBTTagCompound>();
			List<NBTTagCompound> op2 = new ArrayList<NBTTagCompound>();
			for (int i = 0; i < oldList.size(); i++) {
				NBTTagCompound nbttagcompound = oldList.get(i);
				if (nbttagcompound != null) {
					if ((nbttagcompound.get(ATTRIBUTES_UUID_HIGH.NBT) instanceof NBTTagLong)) {
						if ((nbttagcompound.get(ATTRIBUTES_UUID_LOW.NBT) instanceof NBTTagLong)) {
							if (((nbttagcompound.get(ATTRIBUTES_IDENTIFIER.NBT) instanceof NBTTagString)) && (CraftItemFactory.KNOWN_NBT_ATTRIBUTE_NAMES.contains(nbttagcompound.getString(ATTRIBUTES_IDENTIFIER.NBT)))) {
								if (((nbttagcompound.get(ATTRIBUTES_NAME.NBT) instanceof NBTTagString)) && (!nbttagcompound.getString(ATTRIBUTES_NAME.NBT).isEmpty())) {
									if ((nbttagcompound.get(ATTRIBUTES_VALUE.NBT) instanceof NBTTagDouble)) {
										if (((nbttagcompound.get(ATTRIBUTES_TYPE.NBT) instanceof NBTTagInt)) && (nbttagcompound.getInt(ATTRIBUTES_TYPE.NBT) >= 0) && (nbttagcompound.getInt(ATTRIBUTES_TYPE.NBT) <= 2)) {
											switch (nbttagcompound.getInt(ATTRIBUTES_TYPE.NBT)) {
												case 0:
													op0.add(nbttagcompound);
													break;
												case 1:
													op1.add(nbttagcompound);
													break;
												case 2:
													op2.add(nbttagcompound);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			for (NBTTagCompound nbtTagCompound : op0) {
				String name = nbtTagCompound.getString(ATTRIBUTES_IDENTIFIER.NBT);
				if (attributeTracker.containsKey(name)) {
					double val = attributeTracker.get(name);
					val += nbtTagCompound.getDouble(ATTRIBUTES_VALUE.NBT);
					if (val == ((IAttribute) attributesByName.get(name)).a(val)) {
						attributeTracker.put(name, val);
					}
				} else {
					nbttaglist.add(nbtTagCompound);
				}
			}
			for (String name : attributeTracker.keySet()) {
				attributeTrackerX.put(name, attributeTracker.get(name));
			}
			for (NBTTagCompound nbtTagCompound : op1) {
				String name = nbtTagCompound.getString(ATTRIBUTES_IDENTIFIER.NBT);
				if (attributeTracker.containsKey(name)) {
					double val = attributeTracker.get(name);
					double valX = attributeTrackerX.get(name);
					val += valX * nbtTagCompound.getDouble(ATTRIBUTES_VALUE.NBT);
					if (val == ((IAttribute) attributesByName.get(name)).a(val)) {
						attributeTracker.put(name, val);
					}
				} else {
					nbttaglist.add(nbtTagCompound);
				}
			}
			for (NBTTagCompound nbtTagCompound : op2) {
				String name = nbtTagCompound.getString(ATTRIBUTES_IDENTIFIER.NBT);
				if (attributeTracker.containsKey(name)) {
					double val = attributeTracker.get(name);
					val += val * nbtTagCompound.getDouble(ATTRIBUTES_VALUE.NBT);
					if (val == ((IAttribute) attributesByName.get(name)).a(val)) {
						attributeTracker.put(name, val);
					}
				} else {
					nbttaglist.add(nbtTagCompound);
				}
			}
			for (int i = 0; i < nbttaglist.size(); i++) {
				if ((nbttaglist.get(i) instanceof NBTTagCompound)) {
					NBTTagCompound nbttagcompound = nbttaglist.get(i);
					if ((nbttagcompound.get(ATTRIBUTES_UUID_HIGH.NBT) instanceof NBTTagLong)) {
						if ((nbttagcompound.get(ATTRIBUTES_UUID_LOW.NBT) instanceof NBTTagLong)) {
							if (((nbttagcompound.get(ATTRIBUTES_IDENTIFIER.NBT) instanceof NBTTagString)) && (CraftItemFactory.KNOWN_NBT_ATTRIBUTE_NAMES.contains(nbttagcompound.getString(ATTRIBUTES_IDENTIFIER.NBT)))) {
								if (((nbttagcompound.get(ATTRIBUTES_NAME.NBT) instanceof NBTTagString)) && (!nbttagcompound.getString(ATTRIBUTES_NAME.NBT).isEmpty())) {
									if ((nbttagcompound.get(ATTRIBUTES_VALUE.NBT) instanceof NBTTagDouble)) {
										if (((nbttagcompound.get(ATTRIBUTES_TYPE.NBT) instanceof NBTTagInt)) && (nbttagcompound.getInt(ATTRIBUTES_TYPE.NBT) >= 0) && (nbttagcompound.getInt(ATTRIBUTES_TYPE.NBT) <= 2)) {
											if (save == null) {
												save = new NBTTagList();
											}
											NBTTagCompound entry = new NBTTagCompound();
											entry.set(ATTRIBUTES_UUID_HIGH.NBT, nbttagcompound.get(ATTRIBUTES_UUID_HIGH.NBT));
											entry.set(ATTRIBUTES_UUID_LOW.NBT, nbttagcompound.get(ATTRIBUTES_UUID_LOW.NBT));
											entry.set(ATTRIBUTES_IDENTIFIER.NBT, nbttagcompound.get(ATTRIBUTES_IDENTIFIER.NBT));
											entry.set(ATTRIBUTES_NAME.NBT, nbttagcompound.get(ATTRIBUTES_NAME.NBT));
											entry.set(ATTRIBUTES_VALUE.NBT, nbttagcompound.get(ATTRIBUTES_VALUE.NBT));
											entry.set(ATTRIBUTES_TYPE.NBT, nbttagcompound.get(ATTRIBUTES_TYPE.NBT));
											save.add(entry);
										}
									}
								}
							}
						}
					}
				}
			}
			this.attributes = save;
		} else {
			this.attributes = null;
		}
		if (tag.hasKey(UNBREAKABLE.NBT)) {
			this.spigot.setUnbreakable(tag.getBoolean(UNBREAKABLE.NBT));
		}
	}

	@SuppressWarnings("deprecation")
	static Map<Enchantment, Integer> buildEnchantments(NBTTagCompound tag, ItemMetaKey key) {
		if (!tag.hasKey(key.NBT)) {
			return null;
		}
		NBTTagList ench = tag.getList(key.NBT, 10);
		Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>(ench.size());
		for (int i = 0; i < ench.size(); i++) {
			int id = 0xFFFF & ench.get(i).getShort(ENCHANTMENTS_ID.NBT);
			int level = 0xFFFF & ench.get(i).getShort(ENCHANTMENTS_LVL.NBT);

			Enchantment e = Enchantment.getById(id);
			if (e != null) {
				enchantments.put(e, Integer.valueOf(level));
			}
		}
		return enchantments;
	}

	CraftMetaItem(Map<String, Object> map) {
		setDisplayName(SerializableMeta.getString(map, NAME.BUKKIT, true));

		Iterable<?> lore = (Iterable<?>) SerializableMeta.getObject(Iterable.class, map, LORE.BUKKIT, true);
		if (lore != null) {
			safelyAdd(lore, this.lore = new ArrayList<String>(), Integer.MAX_VALUE);
		}
		this.enchantments = buildEnchantments(map, ENCHANTMENTS);

		Integer repairCost = (Integer) SerializableMeta.getObject(Integer.class, map, REPAIR.BUKKIT, true);
		if (repairCost != null) {
			setRepairCost(repairCost.intValue());
		}
		this.attributes = null;

		Boolean unbreakable = (Boolean) SerializableMeta.getObject(Boolean.class, map, UNBREAKABLE.BUKKIT, true);
		if (unbreakable != null) {
			this.spigot.setUnbreakable(unbreakable.booleanValue());
		}
	}

	static Map<Enchantment, Integer> buildEnchantments(Map<String, Object> map, ItemMetaKey key) {
		Map<?, ?> ench = (Map<?, ?>) SerializableMeta.getObject(Map.class, map, key.BUKKIT, true);
		if (ench == null) {
			return null;
		}
		Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>(ench.size());
		for (Map.Entry<?, ?> entry : ench.entrySet()) {
			Enchantment enchantment = Enchantment.getByName(entry.getKey().toString());
			if ((enchantment != null) && ((entry.getValue() instanceof Integer))) {
				enchantments.put(enchantment, (Integer) entry.getValue());
			}
		}
		return enchantments;
	}

	@Overridden
	void applyToItem(NBTTagCompound itemTag) {
		if (hasDisplayName()) {
			setDisplayTag(itemTag, NAME.NBT, new NBTTagString(this.displayName));
		}
		if (hasLore()) {
			setDisplayTag(itemTag, LORE.NBT, createStringList(this.lore));
		}
		applyEnchantments(this.enchantments, itemTag, ENCHANTMENTS);
		if (this.spigot.isUnbreakable()) {
			itemTag.setBoolean(UNBREAKABLE.NBT, true);
		}
		if (hasRepairCost()) {
			itemTag.setInt(REPAIR.NBT, this.repairCost);
		}
		if (this.attributes != null) {
			itemTag.set(ATTRIBUTES.NBT, this.attributes);
		}
	}

	static NBTTagList createStringList(List<String> list) {
		if ((list == null) || (list.isEmpty())) {
			return null;
		}
		NBTTagList tagList = new NBTTagList();
		for (String value : list) {
			tagList.add(new NBTTagString(value));
		}
		return tagList;
	}

	@SuppressWarnings("deprecation")
	static void applyEnchantments(Map<Enchantment, Integer> enchantments, NBTTagCompound tag, ItemMetaKey key) {
		if (enchantments == null) {
			return;
		}
		NBTTagList list = new NBTTagList();
		for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
			NBTTagCompound subtag = new NBTTagCompound();

			subtag.setShort(ENCHANTMENTS_ID.NBT, (short) ((Enchantment) entry.getKey()).getId());
			subtag.setShort(ENCHANTMENTS_LVL.NBT, ((Integer) entry.getValue()).shortValue());

			list.add(subtag);
		}
		tag.set(key.NBT, list);
	}

	void setDisplayTag(NBTTagCompound tag, String key, NBTBase value) {
		NBTTagCompound display = tag.getCompound(DISPLAY.NBT);
		if (!tag.hasKey(DISPLAY.NBT)) {
			tag.set(DISPLAY.NBT, display);
		}
		display.set(key, value);
	}

	@Overridden
	boolean applicableTo(Material type) {
		return type != Material.AIR;
	}

	@Overridden
	boolean isEmpty() {
		return (!hasDisplayName()) && (!hasEnchants()) && (!hasLore()) && (!hasAttributes()) && (!hasRepairCost()) && (!this.spigot.isUnbreakable());
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public final void setDisplayName(String name) {
		this.displayName = name;
	}

	public boolean hasDisplayName() {
		return !Strings.isNullOrEmpty(this.displayName);
	}

	public boolean hasLore() {
		return (this.lore != null) && (!this.lore.isEmpty());
	}

	public boolean hasAttributes() {
		return this.attributes != null;
	}

	public boolean hasRepairCost() {
		return this.repairCost > 0;
	}

	public boolean hasEnchant(Enchantment ench) {
		return (hasEnchants()) && (this.enchantments.containsKey(ench));
	}

	public int getEnchantLevel(Enchantment ench) {
		Integer level = hasEnchants() ? (Integer) this.enchantments.get(ench) : null;
		if (level == null) {
			return 0;
		}
		return level.intValue();
	}

	public Map<Enchantment, Integer> getEnchants() {
		return hasEnchants() ? ImmutableMap.<Enchantment, Integer>copyOf(this.enchantments) : ImmutableMap.<Enchantment, Integer>of();
	}

	public boolean addEnchant(Enchantment ench, int level, boolean ignoreRestrictions) {
		if (this.enchantments == null) {
			this.enchantments = new HashMap<Enchantment, Integer>(4);
		}
		if ((ignoreRestrictions) || ((level >= ench.getStartLevel()) && (level <= ench.getMaxLevel()))) {
			Integer old = (Integer) this.enchantments.put(ench, Integer.valueOf(level));
			return (old == null) || (old.intValue() != level);
		}
		return false;
	}

	public boolean removeEnchant(Enchantment ench) {
		boolean b = (hasEnchants()) && (this.enchantments.remove(ench) != null);
		if ((this.enchantments != null) && (this.enchantments.isEmpty())) {
			this.enchantments = null;
		}
		return b;
	}

	public boolean hasEnchants() {
		return (this.enchantments != null) && (!this.enchantments.isEmpty());
	}

	public boolean hasConflictingEnchant(Enchantment ench) {
		return checkConflictingEnchants(this.enchantments, ench);
	}

	public List<String> getLore() {
		return this.lore == null ? null : new ArrayList<String>(this.lore);
	}

	public void setLore(List<String> lore) {
		if (lore == null) {
			this.lore = null;
		} else if (this.lore == null) {
			safelyAdd(lore, this.lore = new ArrayList<String>(lore.size()), Integer.MAX_VALUE);
		} else {
			this.lore.clear();
			safelyAdd(lore, this.lore, Integer.MAX_VALUE);
		}
	}

	public int getRepairCost() {
		return this.repairCost;
	}

	public void setRepairCost(int cost) {
		this.repairCost = cost;
	}

	public final boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (object == this) {
			return true;
		}
		if (!(object instanceof CraftMetaItem)) {
			return false;
		}
		return CraftItemFactory.instance().equals(this, (ItemMeta) object);
	}

	@Overridden
	boolean equalsCommon(CraftMetaItem that) {
		return (hasDisplayName() ? (that.hasDisplayName()) && (this.displayName.equals(that.displayName)) : !that.hasDisplayName()) && (hasEnchants() ? (that.hasEnchants()) && (this.enchantments.equals(that.enchantments)) : !that.hasEnchants()) && (hasLore() ? (that.hasLore()) && (this.lore.equals(that.lore)) : !that.hasLore()) && (hasAttributes() ? (that.hasAttributes()) && (this.attributes.equals(that.attributes)) : !that.hasAttributes())
				&& (hasRepairCost() ? (that.hasRepairCost()) && (this.repairCost == that.repairCost) : !that.hasRepairCost()) && (this.spigot.isUnbreakable() == that.spigot.isUnbreakable());
	}

	@Overridden
	boolean notUncommon(CraftMetaItem meta) {
		return true;
	}

	public final int hashCode() {
		return applyHash();
	}

	@Overridden
	int applyHash() {
		int hash = 3;
		hash = 61 * hash + (hasDisplayName() ? this.displayName.hashCode() : 0);
		hash = 61 * hash + (hasLore() ? this.lore.hashCode() : 0);
		hash = 61 * hash + (hasEnchants() ? this.enchantments.hashCode() : 0);
		hash = 61 * hash + (hasAttributes() ? this.attributes.hashCode() : 0);
		hash = 61 * hash + (hasRepairCost() ? this.repairCost : 0);
		hash = 61 * hash + (this.spigot.isUnbreakable() ? 1231 : 1237);
		return hash;
	}

	@Overridden
	public CraftMetaItem clone() {
		try {
			CraftMetaItem clone = (CraftMetaItem) super.clone();
			if (this.lore != null) {
				clone.lore = new ArrayList<String>(this.lore);
			}
			if (this.enchantments != null) {
				clone.enchantments = new HashMap<Enchantment, Integer>(this.enchantments);
			}
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}

	@Override
	public final Map<String, Object> serialize() {
		ImmutableMap.Builder<String, Object> map = ImmutableMap.builder();
		map.put(SerializableMeta.TYPE_FIELD, SerializableMeta.classMap.get(getClass()));
		serialize(map);
		return map.build();
	}

	@Overridden
	ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder) {
		if (hasDisplayName()) {
			builder.put(NAME.BUKKIT, this.displayName);
		}
		if (hasLore()) {
			builder.put(LORE.BUKKIT, ImmutableList.copyOf(this.lore));
		}
		serializeEnchantments(this.enchantments, builder, ENCHANTMENTS);
		if (hasRepairCost()) {
			builder.put(REPAIR.BUKKIT, Integer.valueOf(this.repairCost));
		}
		if (this.spigot.isUnbreakable()) {
			builder.put(UNBREAKABLE.BUKKIT, Boolean.valueOf(true));
		}
		return builder;
	}

	static void serializeEnchantments(Map<Enchantment, Integer> enchantments, ImmutableMap.Builder<String, Object> builder, ItemMetaKey key) {
		if ((enchantments == null) || (enchantments.isEmpty())) {
			return;
		}
		ImmutableMap.Builder<String, Integer> enchants = ImmutableMap.builder();
		for (Map.Entry<? extends Enchantment, Integer> enchant : enchantments.entrySet()) {
			enchants.put(((Enchantment) enchant.getKey()).getName(), enchant.getValue());
		}
		builder.put(key.BUKKIT, enchants.build());
	}

	static void safelyAdd(Iterable<?> addFrom, Collection<String> addTo, int maxItemLength) {
		if (addFrom == null) {
			return;
		}
		for (Object object : addFrom) {
			if (!(object instanceof String)) {
				if (object != null) {
					throw new IllegalArgumentException(addFrom + " cannot contain non-string " + object.getClass().getName());
				}
				addTo.add("");
			} else {
				String page = object.toString();
				if (page.length() > maxItemLength) {
					page = page.substring(0, maxItemLength);
				}
				addTo.add(page);
			}
		}
	}

	static boolean checkConflictingEnchants(Map<Enchantment, Integer> enchantments, Enchantment ench) {
		if ((enchantments == null) || (enchantments.isEmpty())) {
			return false;
		}
		for (Enchantment enchant : enchantments.keySet()) {
			if (enchant.conflictsWith(ench)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final String toString() {
		return (String) SerializableMeta.classMap.get(getClass()) + "_META:" + serialize();
	}

	private final ItemMeta.Spigot spigot = new ItemMeta.Spigot() {
		private boolean unbreakable;

		public void setUnbreakable(boolean setUnbreakable) {
			this.unbreakable = setUnbreakable;
		}

		public boolean isUnbreakable() {
			return this.unbreakable;
		}
	};

	public ItemMeta.Spigot spigot() {
		return this.spigot;
	}

}
