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

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.entity.EntityGuardian;
import com.lastabyss.carbon.entity.EntityRabbit;
import net.minecraft.server.v1_7_R4.EnumCreatureType;

public class CarbonEntityGenerator {
    Carbon plugin;
    
    public CarbonEntityGenerator(Carbon plugin) {
        this.plugin = plugin;
    }
    
    public void injectNewCreatures() {
        if (plugin.getConfig().getBoolean("mobSpawning.rabbits", true))
            injectRabbitSpawner();
        if (plugin.getConfig().getBoolean("mobSpawning.guardians", true))
            injectGuardianSpawner();
    }
    
    @SuppressWarnings("unchecked")
    private void injectGuardianSpawner() {
        //spawns guardians in deep oceans
        try {
            for (Field f : BiomeBase.class.getDeclaredFields())
                if (f.getType().getSimpleName().equals(BiomeBase.class.getSimpleName()))
                    if (f.get(null) != null && f.getName().contains("DEEP_OCEAN"))
                        for (Field list : BiomeBase.class.getDeclaredFields())
                            if (list.getType().getSimpleName().equals(List.class.getSimpleName())) {
                                list.setAccessible(true);
                                List<BiomeMeta> metaList = (List<BiomeMeta>) list.get(f.get(null));
                                metaList.add(new BiomeMeta(EntityGuardian.class, 1, 2, 4));
                             }
            } catch (SecurityException e) {
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
	public void injectRabbitSpawner() {
		// adds rabbits to spawn
		try {
			Field atField = net.minecraft.server.v1_7_R4.BiomeBase.class.getDeclaredField("at");
			atField.setAccessible(true);
			for (BiomeBase biomeBase : BiomeBase.getBiomes()) {
				if (biomeBase != null) {
					if (biomeBase instanceof BiomePlains || biomeBase instanceof BiomeBigHills || biomeBase instanceof BiomeForest ||
						biomeBase instanceof BiomeTaiga || biomeBase instanceof BiomeSwamp || biomeBase instanceof BiomeJungle ||
						biomeBase instanceof BiomeSavanna)
							((List<BiomeMeta>) atField.get(biomeBase)).add(new BiomeMeta(EntityRabbit.class, 10, 3, 3));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}