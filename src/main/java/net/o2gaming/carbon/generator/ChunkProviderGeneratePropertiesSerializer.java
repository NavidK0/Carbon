package net.o2gaming.carbon.generator;

import net.minecraft.server.v1_7_R4.BiomeBase;
import net.minecraft.util.com.google.gson.JsonDeserializationContext;
import net.minecraft.util.com.google.gson.JsonDeserializer;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonSerializationContext;
import net.minecraft.util.com.google.gson.JsonSerializer;

public class ChunkProviderGeneratePropertiesSerializer implements JsonDeserializer, JsonSerializer {

        @Override
	public ChunkProviderGeneratePropertiesHolder deserialize(JsonElement jsonElement, java.lang.reflect.Type type, JsonDeserializationContext jdc) {
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		ChunkProviderGeneratePropertiesHolder properties = new ChunkProviderGeneratePropertiesHolder();

		try {
			properties.coordinateScale = JSONParser.getFloat(jsonObject, "coordinateScale", properties.coordinateScale);
			properties.heightScale = JSONParser.getFloat(jsonObject, "heightScale", properties.heightScale);
			properties.lowerLimitScale = JSONParser.getFloat(jsonObject, "lowerLimitScale", properties.lowerLimitScale);
			properties.upperLimitScale = JSONParser.getFloat(jsonObject, "upperLimitScale", properties.upperLimitScale);
			properties.depthNoiseScaleX = JSONParser.getFloat(jsonObject, "depthNoiseScaleX", properties.depthNoiseScaleX);
			properties.depthNoiseScaleZ = JSONParser.getFloat(jsonObject, "depthNoiseScaleZ", properties.depthNoiseScaleZ);
			properties.depthNoiseScaleExponent = JSONParser.getFloat(jsonObject, "depthNoiseScaleExponent", properties.depthNoiseScaleExponent);
			properties.mainNoiseScaleX = JSONParser.getFloat(jsonObject, "mainNoiseScaleX", properties.mainNoiseScaleX);
			properties.mainNoiseScaleY = JSONParser.getFloat(jsonObject, "mainNoiseScaleY", properties.mainNoiseScaleY);
			properties.mainNoiseScaleZ = JSONParser.getFloat(jsonObject, "mainNoiseScaleZ", properties.mainNoiseScaleZ);
			properties.baseSize = JSONParser.getFloat(jsonObject, "baseSize", properties.baseSize);
			properties.stretchY = JSONParser.getFloat(jsonObject, "stretchY", properties.stretchY);
			properties.biomeDepthWeight = JSONParser.getFloat(jsonObject, "biomeDepthWeight", properties.biomeDepthWeight);
			properties.biomeDepthOffset = JSONParser.getFloat(jsonObject, "biomeDepthOffset", properties.biomeDepthOffset);
			properties.biomeScaleWeight = JSONParser.getFloat(jsonObject, "biomeScaleWeight", properties.biomeScaleWeight);
			properties.biomeScaleOffset = JSONParser.getFloat(jsonObject, "biomeScaleOffset", properties.biomeScaleOffset);
			properties.seaLevel = JSONParser.getInt(jsonObject, "seaLevel", properties.seaLevel);
			properties.useCaves = JSONParser.getBoolean(jsonObject, "useCaves", properties.useCaves);
			properties.useDungeons = JSONParser.getBoolean(jsonObject, "useDungeons", properties.useDungeons);
			properties.dungeonChance = JSONParser.getInt(jsonObject, "dungeonChance", properties.dungeonChance);
			properties.useStrongholds = JSONParser.getBoolean(jsonObject, "useStrongholds", properties.useStrongholds);
			properties.useVillages = JSONParser.getBoolean(jsonObject, "useVillages", properties.useVillages);
			properties.useMineShafts = JSONParser.getBoolean(jsonObject, "useMineShafts", properties.useMineShafts);
			properties.useTemples = JSONParser.getBoolean(jsonObject, "useTemples", properties.useTemples);
			properties.useMonuments = JSONParser.getBoolean(jsonObject, "useMonuments", properties.useMonuments);
			properties.useRavines = JSONParser.getBoolean(jsonObject, "useRavines", properties.useRavines);
			properties.useWaterLakes = JSONParser.getBoolean(jsonObject, "useWaterLakes", properties.useWaterLakes);
			properties.waterLakeChance = JSONParser.getInt(jsonObject, "waterLakeChance", properties.waterLakeChance);
			properties.useLavaLakes = JSONParser.getBoolean(jsonObject, "useLavaLakes", properties.useLavaLakes);
			properties.lavaLakeChance = JSONParser.getInt(jsonObject, "lavaLakeChance", properties.lavaLakeChance);
			properties.useLavaOceans = JSONParser.getBoolean(jsonObject, "useLavaOceans", properties.useLavaOceans);
			properties.fixedBiome = JSONParser.getInt(jsonObject, "fixedBiome", properties.fixedBiome);
			if (properties.fixedBiome < 38 && properties.fixedBiome >= -1) {
				if (properties.fixedBiome >= BiomeBase.HELL.id) {
					properties.fixedBiome += 2;
				}
			} else {
				properties.fixedBiome = -1;
			}

			properties.biomeSize = JSONParser.getInt(jsonObject, "biomeSize", properties.biomeSize);
			properties.riverSize = JSONParser.getInt(jsonObject, "riverSize", properties.riverSize);
			properties.dirtSize = JSONParser.getInt(jsonObject, "dirtSize", properties.dirtSize);
			properties.dirtCount = JSONParser.getInt(jsonObject, "dirtCount", properties.dirtCount);
			properties.dirtMinHeight = JSONParser.getInt(jsonObject, "dirtMinHeight", properties.dirtMinHeight);
			properties.dirtMaxHeight = JSONParser.getInt(jsonObject, "dirtMaxHeight", properties.dirtMaxHeight);
			properties.gravelSize = JSONParser.getInt(jsonObject, "gravelSize", properties.gravelSize);
			properties.gravelCount = JSONParser.getInt(jsonObject, "gravelCount", properties.gravelCount);
			properties.gravelMinHeight = JSONParser.getInt(jsonObject, "gravelMinHeight", properties.gravelMinHeight);
			properties.gravelMaxHeight = JSONParser.getInt(jsonObject, "gravelMaxHeight", properties.gravelMaxHeight);
			properties.graniteSize = JSONParser.getInt(jsonObject, "graniteSize", properties.graniteSize);
			properties.graniteCount = JSONParser.getInt(jsonObject, "graniteCount", properties.graniteCount);
			properties.graniteMinHeight = JSONParser.getInt(jsonObject, "graniteMinHeight", properties.graniteMinHeight);
			properties.graniteMaxHeight = JSONParser.getInt(jsonObject, "graniteMaxHeight", properties.graniteMaxHeight);
			properties.dioriteSize = JSONParser.getInt(jsonObject, "dioriteSize", properties.dioriteSize);
			properties.dioriteCount = JSONParser.getInt(jsonObject, "dioriteCount", properties.dioriteCount);
			properties.dioriteMinHeight = JSONParser.getInt(jsonObject, "dioriteMinHeight", properties.dioriteMinHeight);
			properties.dioriteMaxHeight = JSONParser.getInt(jsonObject, "dioriteMaxHeight", properties.dioriteMaxHeight);
			properties.andesiteSize = JSONParser.getInt(jsonObject, "andesiteSize", properties.andesiteSize);
			properties.andesiteCount = JSONParser.getInt(jsonObject, "andesiteCount", properties.andesiteCount);
			properties.andesiteMinHeight = JSONParser.getInt(jsonObject, "andesiteMinHeight", properties.andesiteMinHeight);
			properties.andesiteMaxHeight = JSONParser.getInt(jsonObject, "andesiteMaxHeight", properties.andesiteMaxHeight);
			properties.coalSize = JSONParser.getInt(jsonObject, "coalSize", properties.coalSize);
			properties.coalCount = JSONParser.getInt(jsonObject, "coalCount", properties.coalCount);
			properties.coalMinHeight = JSONParser.getInt(jsonObject, "coalMinHeight", properties.coalMinHeight);
			properties.coalMaxHeight = JSONParser.getInt(jsonObject, "coalMaxHeight", properties.coalMaxHeight);
			properties.ironSize = JSONParser.getInt(jsonObject, "ironSize", properties.ironSize);
			properties.ironCount = JSONParser.getInt(jsonObject, "ironCount", properties.ironCount);
			properties.ironMinHeight = JSONParser.getInt(jsonObject, "ironMinHeight", properties.ironMinHeight);
			properties.ironMaxHeight = JSONParser.getInt(jsonObject, "ironMaxHeight", properties.ironMaxHeight);
			properties.goldSize = JSONParser.getInt(jsonObject, "goldSize", properties.goldSize);
			properties.goldCount = JSONParser.getInt(jsonObject, "goldCount", properties.goldCount);
			properties.goldMinHeight = JSONParser.getInt(jsonObject, "goldMinHeight", properties.goldMinHeight);
			properties.goldMaxHeight = JSONParser.getInt(jsonObject, "goldMaxHeight", properties.goldMaxHeight);
			properties.redstoneSize = JSONParser.getInt(jsonObject, "redstoneSize", properties.redstoneSize);
			properties.redstoneCount = JSONParser.getInt(jsonObject, "redstoneCount", properties.redstoneCount);
			properties.redstoneMinHeight = JSONParser.getInt(jsonObject, "redstoneMinHeight", properties.redstoneMinHeight);
			properties.redstoneMaxHeight = JSONParser.getInt(jsonObject, "redstoneMaxHeight", properties.redstoneMaxHeight);
			properties.diamondSize = JSONParser.getInt(jsonObject, "diamondSize", properties.diamondSize);
			properties.diamondCount = JSONParser.getInt(jsonObject, "diamondCount", properties.diamondCount);
			properties.diamondMinHeight = JSONParser.getInt(jsonObject, "diamondMinHeight", properties.diamondMinHeight);
			properties.diamondMaxHeight = JSONParser.getInt(jsonObject, "diamondMaxHeight", properties.diamondMaxHeight);
			properties.lapisSize = JSONParser.getInt(jsonObject, "lapisSize", properties.lapisSize);
			properties.lapisCount = JSONParser.getInt(jsonObject, "lapisCount", properties.lapisCount);
			properties.lapisCenterHeight = JSONParser.getInt(jsonObject, "lapisCenterHeight", properties.lapisCenterHeight);
			properties.lapisSpread = JSONParser.getInt(jsonObject, "lapisSpread", properties.lapisSpread);
		} catch (Exception ex) {
		}

		return properties;
	}

        @Override
	public JsonElement serialize(Object prop, java.lang.reflect.Type type, JsonSerializationContext jsc) {
            ChunkProviderGeneratePropertiesHolder properties = (ChunkProviderGeneratePropertiesHolder) prop;
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("coordinateScale", (Number) properties.coordinateScale);
		jsonObject.addProperty("heightScale", (Number) properties.heightScale);
		jsonObject.addProperty("lowerLimitScale", (Number) properties.lowerLimitScale);
		jsonObject.addProperty("upperLimitScale", (Number) properties.upperLimitScale);
		jsonObject.addProperty("depthNoiseScaleX", (Number) properties.depthNoiseScaleX);
		jsonObject.addProperty("depthNoiseScaleZ", (Number) properties.depthNoiseScaleZ);
		jsonObject.addProperty("depthNoiseScaleExponent", (Number) properties.depthNoiseScaleExponent);
		jsonObject.addProperty("mainNoiseScaleX", (Number) properties.mainNoiseScaleX);
		jsonObject.addProperty("mainNoiseScaleY", (Number) properties.mainNoiseScaleY);
		jsonObject.addProperty("mainNoiseScaleZ", (Number) properties.mainNoiseScaleZ);
		jsonObject.addProperty("baseSize", (Number) properties.baseSize);
		jsonObject.addProperty("stretchY", (Number) properties.stretchY);
		jsonObject.addProperty("biomeDepthWeight", (Number) properties.biomeDepthWeight);
		jsonObject.addProperty("biomeDepthOffset", (Number) properties.biomeDepthOffset);
		jsonObject.addProperty("biomeScaleWeight", (Number) properties.biomeScaleWeight);
		jsonObject.addProperty("biomeScaleOffset", (Number) properties.biomeScaleOffset);
		jsonObject.addProperty("seaLevel", (Number) properties.seaLevel);
		jsonObject.addProperty("useCaves", properties.useCaves);
		jsonObject.addProperty("useDungeons", properties.useDungeons);
		jsonObject.addProperty("dungeonChance", (Number) properties.dungeonChance);
		jsonObject.addProperty("useStrongholds", properties.useStrongholds);
		jsonObject.addProperty("useVillages", properties.useVillages);
		jsonObject.addProperty("useMineShafts", properties.useMineShafts);
		jsonObject.addProperty("useTemples", properties.useTemples);
		jsonObject.addProperty("useMonuments", properties.useMonuments);
		jsonObject.addProperty("useRavines", properties.useRavines);
		jsonObject.addProperty("useWaterLakes", properties.useWaterLakes);
		jsonObject.addProperty("waterLakeChance", (Number) properties.waterLakeChance);
		jsonObject.addProperty("useLavaLakes", properties.useLavaLakes);
		jsonObject.addProperty("lavaLakeChance", (Number) properties.lavaLakeChance);
		jsonObject.addProperty("useLavaOceans", properties.useLavaOceans);
		jsonObject.addProperty("fixedBiome", (Number) properties.fixedBiome);
		jsonObject.addProperty("biomeSize", (Number) properties.biomeSize);
		jsonObject.addProperty("riverSize", (Number) properties.riverSize);
		jsonObject.addProperty("dirtSize", (Number) properties.dirtSize);
		jsonObject.addProperty("dirtCount", (Number) properties.dirtCount);
		jsonObject.addProperty("dirtMinHeight", (Number) properties.dirtMinHeight);
		jsonObject.addProperty("dirtMaxHeight", (Number) properties.dirtMaxHeight);
		jsonObject.addProperty("gravelSize", (Number) properties.gravelSize);
		jsonObject.addProperty("gravelCount", (Number) properties.gravelCount);
		jsonObject.addProperty("gravelMinHeight", (Number) properties.gravelMinHeight);
		jsonObject.addProperty("gravelMaxHeight", (Number) properties.gravelMaxHeight);
		jsonObject.addProperty("graniteSize", (Number) properties.graniteSize);
		jsonObject.addProperty("graniteCount", (Number) properties.graniteCount);
		jsonObject.addProperty("graniteMinHeight", (Number) properties.graniteMinHeight);
		jsonObject.addProperty("graniteMaxHeight", (Number) properties.graniteMaxHeight);
		jsonObject.addProperty("dioriteSize", (Number) properties.dioriteSize);
		jsonObject.addProperty("dioriteCount", (Number) properties.dioriteCount);
		jsonObject.addProperty("dioriteMinHeight", (Number) properties.dioriteMinHeight);
		jsonObject.addProperty("dioriteMaxHeight", (Number) properties.dioriteMaxHeight);
		jsonObject.addProperty("andesiteSize", (Number) properties.andesiteSize);
		jsonObject.addProperty("andesiteCount", (Number) properties.andesiteCount);
		jsonObject.addProperty("andesiteMinHeight", (Number) properties.andesiteMinHeight);
		jsonObject.addProperty("andesiteMaxHeight", (Number) properties.andesiteMaxHeight);
		jsonObject.addProperty("coalSize", (Number) properties.coalSize);
		jsonObject.addProperty("coalCount", (Number) properties.coalCount);
		jsonObject.addProperty("coalMinHeight", (Number) properties.coalMinHeight);
		jsonObject.addProperty("coalMaxHeight", (Number) properties.coalMaxHeight);
		jsonObject.addProperty("ironSize", (Number) properties.ironSize);
		jsonObject.addProperty("ironCount", (Number) properties.ironCount);
		jsonObject.addProperty("ironMinHeight", (Number) properties.ironMinHeight);
		jsonObject.addProperty("ironMaxHeight", (Number) properties.ironMaxHeight);
		jsonObject.addProperty("goldSize", (Number) properties.goldSize);
		jsonObject.addProperty("goldCount", (Number) properties.goldCount);
		jsonObject.addProperty("goldMinHeight", (Number) properties.goldMinHeight);
		jsonObject.addProperty("goldMaxHeight", (Number) properties.goldMaxHeight);
		jsonObject.addProperty("redstoneSize", (Number) properties.redstoneSize);
		jsonObject.addProperty("redstoneCount", (Number) properties.redstoneCount);
		jsonObject.addProperty("redstoneMinHeight", (Number) properties.redstoneMinHeight);
		jsonObject.addProperty("redstoneMaxHeight", (Number) properties.redstoneMaxHeight);
		jsonObject.addProperty("diamondSize", (Number) properties.diamondSize);
		jsonObject.addProperty("diamondCount", (Number) properties.diamondCount);
		jsonObject.addProperty("diamondMinHeight", (Number) properties.diamondMinHeight);
		jsonObject.addProperty("diamondMaxHeight", (Number) properties.diamondMaxHeight);
		jsonObject.addProperty("lapisSize", (Number) properties.lapisSize);
		jsonObject.addProperty("lapisCount", (Number) properties.lapisCount);
		jsonObject.addProperty("lapisCenterHeight", (Number) properties.lapisCenterHeight);
		jsonObject.addProperty("lapisSpread", (Number) properties.lapisSpread);
		return jsonObject;
	}

}