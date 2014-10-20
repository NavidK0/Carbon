package org.bukkit.craftbukkit.v1_7_R4.inventory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.configuration.serialization.SerializableAs;

import com.google.common.collect.ImmutableMap;

import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaItem.SerializableMeta;

@DelegateDeserialization(SerializableMeta.class)
public class BannerMeta extends CraftMetaItem {

	public static void init() {
	}

	private int baseColor = -1;
	private LinkedList<BannerPattern> patternsData = new LinkedList<BannerPattern>();

	BannerMeta(int itemData, NBTTagCompound tag) {
		super(tag);
		if (!tag.hasKey("BlockEntityTag")) {
			return;
		}
		NBTTagCompound compound = tag.getCompound("BlockEntityTag");
		if (compound.hasKeyOfType("Base", 99)) {
			this.baseColor = compound.getInt("Base");
		} else {
			this.baseColor = itemData & 15;
		}
		if (compound.hasKeyOfType("Patterns", 9)) {
			NBTTagList patterns = (NBTTagList) compound.getList("Patterns", 10);
			for (int i = 0; i < patterns.size(); i++) {
				NBTTagCompound pattern = patterns.get(i);
				patternsData.add(new BannerPattern(pattern.getString("Pattern"), pattern.getInt("Color")));
			}
		}
	}

	@SuppressWarnings("unchecked")
	BannerMeta(Map<String, Object> map) {
		super(map);
		baseColor = (Integer) map.get("BaseColor");
		Object patternsDataObject = map.get("Patterns");
		if (patternsDataObject instanceof HashMap) {
			patternsData.clear();
			for (Entry<String, Integer> entry : ((HashMap<String, Integer>)patternsDataObject).entrySet()) {
				patternsData.add(new BannerPattern(entry.getKey(), entry.getValue()));
			}
		} else if (patternsDataObject instanceof LinkedList) {
			patternsData = (LinkedList<BannerPattern>) patternsDataObject;
		}
	}

	BannerMeta(CraftMetaItem meta) {
		super(meta);
		if (!(meta instanceof BannerMeta)) {
			return;
		}
		BannerMeta that = (BannerMeta) meta;
		this.patternsData = that.getPatterns();
		this.baseColor = that.getBaseColor();
	}

	@Override
	void applyToItem(NBTTagCompound tag) {
		super.applyToItem(tag);
		tag.set("BlockEntityTag", new NBTTagCompound());
		NBTTagCompound compound = tag.getCompound("BlockEntityTag");
		if (baseColor != -1) {
			compound.setInt("Base", baseColor);
		}
		NBTTagList patterns = new NBTTagList();
		for (BannerPattern bannerpattern : patternsData) {
			NBTTagCompound pattern = new NBTTagCompound();
			pattern.setString("Pattern", bannerpattern.getPattern());
			pattern.setInt("Color", bannerpattern.getColor());
			patterns.add(pattern);
		}
		if (patterns.size() != 0) {
			compound.set("Patterns", patterns);
		}
	}

	public int getBaseColor() {
		return baseColor;
	}

	public LinkedList<BannerPattern> getPatterns() {
		return patternsData;
	}

	@Override
	boolean applicableTo(Material type) {
		return type.toString().equals("BANNER");
	}

	@Override
	boolean isEmpty() {
		return false;
	}

	@Override
	boolean equalsCommon(CraftMetaItem meta) {
		if (!super.equalsCommon(meta)) {
			return false;
		}
		if ((meta instanceof BannerMeta)) {
			BannerMeta that = (BannerMeta) meta;
			return (that.getBaseColor() == baseColor) && (that.getPatterns().equals(patternsData));
		}
		return true;
	}

	@Override
	int applyHash() {
		int original;
		int hash = original = super.applyHash();
		hash = 61 * hash + this.baseColor ^ 0x4B0;
		hash = 31 * hash + patternsData.hashCode();
		return hash != original ? BannerMeta.class.hashCode() ^ hash : hash;
	}

	@Override
	ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder) {
		super.serialize(builder);
		builder.put("BaseColor", baseColor);
		builder.put("Patterns", patternsData);
		return builder;
	}

	@Override
	public BannerMeta clone() {
		BannerMeta cloned = (BannerMeta) super.clone();
		cloned.patternsData = new LinkedList<BannerPattern>(patternsData);
		return cloned;
	}

	@SerializableAs("BannerPattern")
	public static class BannerPattern implements ConfigurationSerializable {

		public static void init() {
			ConfigurationSerialization.registerClass(BannerPattern.class);
		}

		private String pattern;
		private int color;

		public BannerPattern(String pattern, int color) {
			this.pattern = pattern;
			this.color = color;
		}

		public String getPattern() {
			return pattern;
		}

		public int getColor() {
			return color;
		}

		@Override
		public Map<String, Object> serialize() {
			return ImmutableMap.of("Pattern", pattern, "Color", ((Object) Integer.valueOf(color)));
		}

		public static ConfigurationSerializable deserialize(Map<String, Object> map) {
			return new BannerPattern(((String) map.get("Pattern")), (int) map.get("Color"));
		}

	}

}
