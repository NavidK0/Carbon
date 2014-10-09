package com.lastabyss.carbon.generator;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.server.v1_7_R4.BiomeBase;
import net.minecraft.server.v1_7_R4.BiomeBigHills;
import net.minecraft.server.v1_7_R4.BiomeForest;
import net.minecraft.server.v1_7_R4.BiomeJungle;
import net.minecraft.server.v1_7_R4.BiomeMeta;
import net.minecraft.server.v1_7_R4.BiomePlains;
import net.minecraft.server.v1_7_R4.BiomeSavanna;
import net.minecraft.server.v1_7_R4.BiomeSwamp;
import net.minecraft.server.v1_7_R4.BiomeTaiga;

import com.lastabyss.carbon.entity.EntityRabbit;

//Entity Gen's
public class CarbonEntityGenerator {

	@SuppressWarnings("unchecked")
	public void injectRabbitSpawner() {
		// adds rabbits to spawn
		try {
			Field atField = net.minecraft.server.v1_7_R4.BiomeBase.class.getDeclaredField("at");
			atField.setAccessible(true);

			for (BiomeBase biomeBase : BiomeBase.getBiomes()) {
				if (biomeBase != null) {
					if (
						biomeBase instanceof BiomePlains ||
						biomeBase instanceof BiomeBigHills ||
						biomeBase instanceof BiomeForest ||
						biomeBase instanceof BiomeTaiga ||
						biomeBase instanceof BiomeSwamp ||
						biomeBase instanceof BiomeJungle ||
						biomeBase instanceof BiomeSavanna
					) {
						((List<BiomeMeta>) atField.get(biomeBase)).add(new BiomeMeta(EntityRabbit.class, 10, 3, 3));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
