package org.bukkit.craftbukkit.v1_7_R4.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;

import com.google.common.collect.ImmutableMap;

import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;

public class BannerMeta extends CraftMetaItem {

	public static void init() {
	}

	private int baseColor = 1;
	private HashMap<String, Integer> patternsData = new HashMap<String, Integer>();

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
				patternsData.put(pattern.getString("Pattern"), pattern.getInt("Color"));
			}
		}
	}

	@SuppressWarnings("unchecked")
	BannerMeta(Map<String, Object> map) {
		super(map);
		baseColor = (int) map.get("BaseColor");
		patternsData = (HashMap<String, Integer>) map.get("Patterns");
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
		compound.setInt("Base", baseColor);
		NBTTagList patterns = new NBTTagList();
		for (Entry<String, Integer> entry : patternsData.entrySet()) {
			NBTTagCompound pattern = new NBTTagCompound();
			pattern.setString("Pattern", entry.getKey());
			pattern.setInt("Color", entry.getValue());
			patterns.add(pattern);
		}
		if (patterns.size() != 0) {
			compound.set("Patterns", patterns);
		}
	}

	public int getBaseColor() {
		return baseColor;
	}

	public HashMap<String, Integer> getPatterns() {
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
		hash = 61 * hash + this.baseColor ^ 0b010010110;
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
		return (BannerMeta) super.clone();
	}

}
