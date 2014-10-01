package com.lastabyss.carbon.generator;

import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;
import net.minecraft.util.com.google.gson.JsonSyntaxException;

public class ChunkProviderGeneratePropertiesHolder {

	static final Gson gson = (new GsonBuilder()).registerTypeAdapter(ChunkProviderGeneratePropertiesHolder.class, new ChunkProviderGeneratePropertiesSerializer()).create();

	public static ChunkProviderGeneratePropertiesHolder fromJSON(String jsonString) {
		if (jsonString.length() == 0) {
			return new ChunkProviderGeneratePropertiesHolder();
		} else {
			try {
				return (ChunkProviderGeneratePropertiesHolder) gson.fromJson(jsonString, ChunkProviderGeneratePropertiesHolder.class);
			} catch (JsonSyntaxException var2) {
				return new ChunkProviderGeneratePropertiesHolder();
			}
		}
	}

	public float coordinateScale = 684.412F;
	public float heightScale = 684.412F;
	public float upperLimitScale = 512.0F;
	public float lowerLimitScale = 512.0F;
	public float depthNoiseScaleX = 200.0F;
	public float depthNoiseScaleZ = 200.0F;
	public float depthNoiseScaleExponent = 0.5F;
	public float mainNoiseScaleX = 80.0F;
	public float mainNoiseScaleY = 160.0F;
	public float mainNoiseScaleZ = 80.0F;
	public float baseSize = 8.5F;
	public float stretchY = 12.0F;
	public float biomeDepthWeight = 1.0F;
	public float biomeDepthOffset = 0.0F;
	public float biomeScaleWeight = 1.0F;
	public float biomeScaleOffset = 0.0F;
	public int seaLevel = 63;
	public boolean useCaves = true;
	public boolean useDungeons = true;
	public int dungeonChance = 8;
	public boolean useStrongholds = true;
	public boolean useVillages = true;
	public boolean useMineShafts = true;
	public boolean useTemples = true;
	public boolean useMonuments = true;
	public boolean useRavines = true;
	public boolean useWaterLakes = true;
	public int waterLakeChance = 4;
	public boolean useLavaLakes = true;
	public int lavaLakeChance = 80;
	public boolean useLavaOceans = false;
	public int fixedBiome = -1;
	public int biomeSize = 4;
	public int riverSize = 4;
	public int dirtSize = 33;
	public int dirtCount = 10;
	public int dirtMinHeight = 0;
	public int dirtMaxHeight = 256;
	public int gravelSize = 33;
	public int gravelCount = 8;
	public int gravelMinHeight = 0;
	public int gravelMaxHeight = 256;
	public int graniteSize = 33;
	public int graniteCount = 10;
	public int graniteMinHeight = 0;
	public int graniteMaxHeight = 80;
	public int dioriteSize = 33;
	public int dioriteCount = 10;
	public int dioriteMinHeight = 0;
	public int dioriteMaxHeight = 80;
	public int andesiteSize = 33;
	public int andesiteCount = 10;
	public int andesiteMinHeight = 0;
	public int andesiteMaxHeight = 80;
	public int coalSize = 17;
	public int coalCount = 20;
	public int coalMinHeight = 0;
	public int coalMaxHeight = 128;
	public int ironSize = 9;
	public int ironCount = 20;
	public int ironMinHeight = 0;
	public int ironMaxHeight = 64;
	public int goldSize = 9;
	public int goldCount = 2;
	public int goldMinHeight = 0;
	public int goldMaxHeight = 32;
	public int redstoneSize = 8;
	public int redstoneCount = 8;
	public int redstoneMinHeight = 0;
	public int redstoneMaxHeight = 16;
	public int diamondSize = 8;
	public int diamondCount = 1;
	public int diamondMinHeight = 0;
	public int diamondMaxHeight = 16;
	public int lapisSize = 7;
	public int lapisCount = 1;
	public int lapisCenterHeight = 16;
	public int lapisSpread = 16;

	public ChunkProviderGeneratePropertiesHolder() {
		this.coordinateScale = 684.412F;
		this.heightScale = 684.412F;
		this.upperLimitScale = 512.0F;
		this.lowerLimitScale = 512.0F;
		this.depthNoiseScaleX = 200.0F;
		this.depthNoiseScaleZ = 200.0F;
		this.depthNoiseScaleExponent = 0.5F;
		this.mainNoiseScaleX = 80.0F;
		this.mainNoiseScaleY = 160.0F;
		this.mainNoiseScaleZ = 80.0F;
		this.baseSize = 8.5F;
		this.stretchY = 12.0F;
		this.biomeDepthWeight = 1.0F;
		this.biomeDepthOffset = 0.0F;
		this.biomeScaleWeight = 1.0F;
		this.biomeScaleOffset = 0.0F;
		this.seaLevel = 63;
		this.useCaves = true;
		this.useDungeons = true;
		this.dungeonChance = 8;
		this.useStrongholds = true;
		this.useVillages = true;
		this.useMineShafts = true;
		this.useTemples = true;
		this.useMonuments = true;
		this.useRavines = true;
		this.useWaterLakes = true;
		this.waterLakeChance = 4;
		this.useLavaLakes = true;
		this.lavaLakeChance = 80;
		this.useLavaOceans = false;
		this.fixedBiome = -1;
		this.biomeSize = 4;
		this.riverSize = 4;
		this.dirtSize = 33;
		this.dirtCount = 10;
		this.dirtMinHeight = 0;
		this.dirtMaxHeight = 256;
		this.gravelSize = 33;
		this.gravelCount = 8;
		this.gravelMinHeight = 0;
		this.gravelMaxHeight = 256;
		this.graniteSize = 33;
		this.graniteCount = 10;
		this.graniteMinHeight = 0;
		this.graniteMaxHeight = 80;
		this.dioriteSize = 33;
		this.dioriteCount = 10;
		this.dioriteMinHeight = 0;
		this.dioriteMaxHeight = 80;
		this.andesiteSize = 33;
		this.andesiteCount = 10;
		this.andesiteMinHeight = 0;
		this.andesiteMaxHeight = 80;
		this.coalSize = 17;
		this.coalCount = 20;
		this.coalMinHeight = 0;
		this.coalMaxHeight = 128;
		this.ironSize = 9;
		this.ironCount = 20;
		this.ironMinHeight = 0;
		this.ironMaxHeight = 64;
		this.goldSize = 9;
		this.goldCount = 2;
		this.goldMinHeight = 0;
		this.goldMaxHeight = 32;
		this.redstoneSize = 8;
		this.redstoneCount = 8;
		this.redstoneMinHeight = 0;
		this.redstoneMaxHeight = 16;
		this.diamondSize = 8;
		this.diamondCount = 1;
		this.diamondMinHeight = 0;
		this.diamondMaxHeight = 16;
		this.lapisSize = 7;
		this.lapisCount = 1;
		this.lapisCenterHeight = 16;
		this.lapisSpread = 16;
	}


	public String toString() {
		return gson.toJson((Object) this);
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj != null && this.getClass() == obj.getClass()) {
			ChunkProviderGeneratePropertiesHolder properties = (ChunkProviderGeneratePropertiesHolder) obj;
			return this.andesiteCount != properties.andesiteCount ? false : (this.andesiteMaxHeight != properties.andesiteMaxHeight ? false : (this.andesiteMinHeight != properties.andesiteMinHeight ? false : (this.andesiteSize != properties.andesiteSize ? false : (Float.compare(properties.baseSize, this.baseSize) != 0 ? false : (Float.compare(properties.biomeDepthOffset, this.biomeDepthOffset) != 0 ? false : (Float.compare(properties.biomeDepthWeight, this.biomeDepthWeight) != 0 ? false : (Float.compare(properties.biomeScaleOffset, this.biomeScaleOffset) != 0 ? false : (Float.compare(properties.biomeScaleWeight, this.biomeScaleWeight) != 0 ? false : (this.biomeSize != properties.biomeSize ? false : (this.coalCount != properties.coalCount ? false : (this.coalMaxHeight != properties.coalMaxHeight ? false : (this.coalMinHeight != properties.coalMinHeight ? false
					: (this.coalSize != properties.coalSize ? false : (Float.compare(properties.coordinateScale, this.coordinateScale) != 0 ? false : (Float.compare(properties.depthNoiseScaleExponent, this.depthNoiseScaleExponent) != 0 ? false : (Float.compare(properties.depthNoiseScaleX, this.depthNoiseScaleX) != 0 ? false : (Float.compare(properties.depthNoiseScaleZ, this.depthNoiseScaleZ) != 0 ? false : (this.diamondCount != properties.diamondCount ? false : (this.diamondMaxHeight != properties.diamondMaxHeight ? false : (this.diamondMinHeight != properties.diamondMinHeight ? false : (this.diamondSize != properties.diamondSize ? false : (this.dioriteCount != properties.dioriteCount ? false : (this.dioriteMaxHeight != properties.dioriteMaxHeight ? false : (this.dioriteMinHeight != properties.dioriteMinHeight ? false : (this.dioriteSize != properties.dioriteSize ? false : (this.dirtCount != properties.dirtCount ? false
							: (this.dirtMaxHeight != properties.dirtMaxHeight ? false : (this.dirtMinHeight != properties.dirtMinHeight ? false : (this.dirtSize != properties.dirtSize ? false : (this.dungeonChance != properties.dungeonChance ? false : (this.fixedBiome != properties.fixedBiome ? false : (this.goldCount != properties.goldCount ? false : (this.goldMaxHeight != properties.goldMaxHeight ? false : (this.goldMinHeight != properties.goldMinHeight ? false : (this.goldSize != properties.goldSize ? false : (this.graniteCount != properties.graniteCount ? false : (this.graniteMaxHeight != properties.graniteMaxHeight ? false : (this.graniteMinHeight != properties.graniteMinHeight ? false : (this.graniteSize != properties.graniteSize ? false : (this.gravelCount != properties.gravelCount ? false : (this.gravelMaxHeight != properties.gravelMaxHeight ? false : (this.gravelMinHeight != properties.gravelMinHeight ? false
									: (this.gravelSize != properties.gravelSize ? false : (Float.compare(properties.heightScale, this.heightScale) != 0 ? false : (this.ironCount != properties.ironCount ? false : (this.ironMaxHeight != properties.ironMaxHeight ? false : (this.ironMinHeight != properties.ironMinHeight ? false : (this.ironSize != properties.ironSize ? false : (this.lapisCenterHeight != properties.lapisCenterHeight ? false : (this.lapisCount != properties.lapisCount ? false : (this.lapisSize != properties.lapisSize ? false : (this.lapisSpread != properties.lapisSpread ? false : (this.lavaLakeChance != properties.lavaLakeChance ? false : (Float.compare(properties.lowerLimitScale, this.lowerLimitScale) != 0 ? false : (Float.compare(properties.mainNoiseScaleX, this.mainNoiseScaleX) != 0 ? false : (Float.compare(properties.mainNoiseScaleY,
											this.mainNoiseScaleY) != 0 ? false : (Float.compare(properties.mainNoiseScaleZ, this.mainNoiseScaleZ) != 0 ? false : (this.redstoneCount != properties.redstoneCount ? false : (this.redstoneMaxHeight != properties.redstoneMaxHeight ? false : (this.redstoneMinHeight != properties.redstoneMinHeight ? false : (this.redstoneSize != properties.redstoneSize ? false : (this.riverSize != properties.riverSize ? false : (this.seaLevel != properties.seaLevel ? false : (Float.compare(properties.stretchY, this.stretchY) != 0 ? false : (Float.compare(properties.upperLimitScale, this.upperLimitScale) != 0 ? false : (this.useCaves != properties.useCaves ? false : (this.useDungeons != properties.useDungeons ? false : (this.useLavaLakes != properties.useLavaLakes ? false : (this.useLavaOceans != properties.useLavaOceans ? false
											: (this.useMineShafts != properties.useMineShafts ? false : (this.useRavines != properties.useRavines ? false : (this.useStrongholds != properties.useStrongholds ? false : (this.useTemples != properties.useTemples ? false : (this.useMonuments != properties.useMonuments ? false : (this.useVillages != properties.useVillages ? false : (this.useWaterLakes != properties.useWaterLakes ? false : this.waterLakeChance == properties.waterLakeChance))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))));
		} else {
			return false;
		}
	}

	public int hashCode() {
		int hash = this.coordinateScale != 0.0F ? Float.floatToIntBits(this.coordinateScale) : 0;
		hash = 31 * hash + (this.heightScale != 0.0F ? Float.floatToIntBits(this.heightScale) : 0);
		hash = 31 * hash + (this.upperLimitScale != 0.0F ? Float.floatToIntBits(this.upperLimitScale) : 0);
		hash = 31 * hash + (this.lowerLimitScale != 0.0F ? Float.floatToIntBits(this.lowerLimitScale) : 0);
		hash = 31 * hash + (this.depthNoiseScaleX != 0.0F ? Float.floatToIntBits(this.depthNoiseScaleX) : 0);
		hash = 31 * hash + (this.depthNoiseScaleZ != 0.0F ? Float.floatToIntBits(this.depthNoiseScaleZ) : 0);
		hash = 31 * hash + (this.depthNoiseScaleExponent != 0.0F ? Float.floatToIntBits(this.depthNoiseScaleExponent) : 0);
		hash = 31 * hash + (this.mainNoiseScaleX != 0.0F ? Float.floatToIntBits(this.mainNoiseScaleX) : 0);
		hash = 31 * hash + (this.mainNoiseScaleY != 0.0F ? Float.floatToIntBits(this.mainNoiseScaleY) : 0);
		hash = 31 * hash + (this.mainNoiseScaleZ != 0.0F ? Float.floatToIntBits(this.mainNoiseScaleZ) : 0);
		hash = 31 * hash + (this.baseSize != 0.0F ? Float.floatToIntBits(this.baseSize) : 0);
		hash = 31 * hash + (this.stretchY != 0.0F ? Float.floatToIntBits(this.stretchY) : 0);
		hash = 31 * hash + (this.biomeDepthWeight != 0.0F ? Float.floatToIntBits(this.biomeDepthWeight) : 0);
		hash = 31 * hash + (this.biomeDepthOffset != 0.0F ? Float.floatToIntBits(this.biomeDepthOffset) : 0);
		hash = 31 * hash + (this.biomeScaleWeight != 0.0F ? Float.floatToIntBits(this.biomeScaleWeight) : 0);
		hash = 31 * hash + (this.biomeScaleOffset != 0.0F ? Float.floatToIntBits(this.biomeScaleOffset) : 0);
		hash = 31 * hash + this.seaLevel;
		hash = 31 * hash + (this.useCaves ? 1 : 0);
		hash = 31 * hash + (this.useDungeons ? 1 : 0);
		hash = 31 * hash + this.dungeonChance;
		hash = 31 * hash + (this.useStrongholds ? 1 : 0);
		hash = 31 * hash + (this.useVillages ? 1 : 0);
		hash = 31 * hash + (this.useMineShafts ? 1 : 0);
		hash = 31 * hash + (this.useTemples ? 1 : 0);
		hash = 31 * hash + (this.useMonuments ? 1 : 0);
		hash = 31 * hash + (this.useRavines ? 1 : 0);
		hash = 31 * hash + (this.useWaterLakes ? 1 : 0);
		hash = 31 * hash + this.waterLakeChance;
		hash = 31 * hash + (this.useLavaLakes ? 1 : 0);
		hash = 31 * hash + this.lavaLakeChance;
		hash = 31 * hash + (this.useLavaOceans ? 1 : 0);
		hash = 31 * hash + this.fixedBiome;
		hash = 31 * hash + this.biomeSize;
		hash = 31 * hash + this.riverSize;
		hash = 31 * hash + this.dirtSize;
		hash = 31 * hash + this.dirtCount;
		hash = 31 * hash + this.dirtMinHeight;
		hash = 31 * hash + this.dirtMaxHeight;
		hash = 31 * hash + this.gravelSize;
		hash = 31 * hash + this.gravelCount;
		hash = 31 * hash + this.gravelMinHeight;
		hash = 31 * hash + this.gravelMaxHeight;
		hash = 31 * hash + this.graniteSize;
		hash = 31 * hash + this.graniteCount;
		hash = 31 * hash + this.graniteMinHeight;
		hash = 31 * hash + this.graniteMaxHeight;
		hash = 31 * hash + this.dioriteSize;
		hash = 31 * hash + this.dioriteCount;
		hash = 31 * hash + this.dioriteMinHeight;
		hash = 31 * hash + this.dioriteMaxHeight;
		hash = 31 * hash + this.andesiteSize;
		hash = 31 * hash + this.andesiteCount;
		hash = 31 * hash + this.andesiteMinHeight;
		hash = 31 * hash + this.andesiteMaxHeight;
		hash = 31 * hash + this.coalSize;
		hash = 31 * hash + this.coalCount;
		hash = 31 * hash + this.coalMinHeight;
		hash = 31 * hash + this.coalMaxHeight;
		hash = 31 * hash + this.ironSize;
		hash = 31 * hash + this.ironCount;
		hash = 31 * hash + this.ironMinHeight;
		hash = 31 * hash + this.ironMaxHeight;
		hash = 31 * hash + this.goldSize;
		hash = 31 * hash + this.goldCount;
		hash = 31 * hash + this.goldMinHeight;
		hash = 31 * hash + this.goldMaxHeight;
		hash = 31 * hash + this.redstoneSize;
		hash = 31 * hash + this.redstoneCount;
		hash = 31 * hash + this.redstoneMinHeight;
		hash = 31 * hash + this.redstoneMaxHeight;
		hash = 31 * hash + this.diamondSize;
		hash = 31 * hash + this.diamondCount;
		hash = 31 * hash + this.diamondMinHeight;
		hash = 31 * hash + this.diamondMaxHeight;
		hash = 31 * hash + this.lapisSize;
		hash = 31 * hash + this.lapisCount;
		hash = 31 * hash + this.lapisCenterHeight;
		hash = 31 * hash + this.lapisSpread;
		return hash;
	}

	public static ChunkProviderGeneratePropertiesHolder create() {
		return new ChunkProviderGeneratePropertiesHolder();
	}

}