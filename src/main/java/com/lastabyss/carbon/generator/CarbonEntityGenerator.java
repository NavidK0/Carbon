package com.lastabyss.carbon.generator;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.server.v1_7_R4.BiomeBase;
import net.minecraft.server.v1_7_R4.BiomeMeta;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.entity.EntityGuardian;
import com.lastabyss.carbon.entity.EntityRabbit;

//Entity Gen's
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
    
    private void injectGuardianSpawner() {
        //spawns guardians in deep oceans
        try {
            for (Field f : BiomeBase.class.getDeclaredFields())
                if (f.getType().getSimpleName().equals(BiomeBase.class.getSimpleName()))
                    if (f.get(null) != null && f.getName().contains("DEEP_OCEAN"))
                        for (Field list : BiomeBase.class.getDeclaredFields())
                            if (list.getType().getSimpleName().equals(List.class.getSimpleName())) {
                                list.setAccessible(true);
                                @SuppressWarnings("unchecked")
                                List<BiomeMeta> metaList = (List<BiomeMeta>) list.get(f.get(null));
                                metaList.add(new BiomeMeta(EntityGuardian.class, 10, 3, 3));
                             }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
    private void injectRabbitSpawner() {
        //adds rabbits to spawn 
        try {
            for (Field f : BiomeBase.class.getDeclaredFields())
                if (f.getType().getSimpleName().equals(BiomeBase.class.getSimpleName()))
                    if (f.get(null) != null && (f.getName().contains("PLAINS") || f.getName().contains("FOREST") || f.getName().contains("EXTREME_HILLS") ||
                        f.getName().contains("JUNGLE") || f.getName().contains("TAIGA") || f.getName().contains("SAVANNA")))
                        for (Field list : BiomeBase.class.getDeclaredFields())
                            if (list.getType().getSimpleName().equals(List.class.getSimpleName())) {
                                list.setAccessible(true);
                                @SuppressWarnings("unchecked")
                                List<BiomeMeta> metaList = (List<BiomeMeta>) list.get(f.get(null));
                                metaList.add(new BiomeMeta(EntityRabbit.class, 10, 3, 3));
                             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}