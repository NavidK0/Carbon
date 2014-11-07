package com.lastabyss.carbon.generator;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.generator.populator.StoneVariantPopulator;

import java.util.logging.Level;

import net.minecraft.server.v1_7_R4.WorldServer;

import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;


/**
 *
 * @author Navid
 */
public class CarbonWorldGenerator implements Listener {
    Carbon plugin;
    public final static StoneVariantPopulator dioritePopulator = new StoneVariantPopulator(Material.STONE, (byte) 1, 33, 10);
    public final static StoneVariantPopulator andesitePopulator = new StoneVariantPopulator(Material.STONE, (byte) 3, 33, 10);
    public final static StoneVariantPopulator granitePopulator = new StoneVariantPopulator(Material.STONE, (byte) 5, 33, 10);

    public CarbonWorldGenerator(Carbon plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWorldInit(WorldInitEvent event) {
    	//Add new chunkprovidergenerate
    	if (plugin.getConfig().getStringList("options.worlds").contains(event.getWorld().getName()) && event.getWorld().getEnvironment() == Environment.NORMAL) {
	    	WorldServer nmsWorld = ((CraftWorld) event.getWorld()).getHandle();
	    	nmsWorld.chunkProviderServer.chunkProvider = new ChunkProviderGenerate(nmsWorld, nmsWorld.getSeed(), nmsWorld.getWorldData().shouldGenerateMapFeatures());
    	}
    }

    @EventHandler()
    public void onWorldLoad(WorldLoadEvent evt) {
        //Add populator on world load
        World world = evt.getWorld();
        if (
            plugin.getConfig().getStringList("options.worlds").contains(world.getName()) && (
                !world.getPopulators().contains(dioritePopulator)
                && !world.getPopulators().contains(andesitePopulator)
                && !world.getPopulators().contains(granitePopulator)
            )
        ) {
            Carbon.log.log(Level.INFO, "[Carbon] Editing world: {0}", world.getName());
            Carbon.log.log(Level.INFO, "[Carbon] Adding populator for world: {0}", world.getName());
            world.getPopulators().add(dioritePopulator);
            world.getPopulators().add(andesitePopulator);
            world.getPopulators().add(granitePopulator);
            Carbon.log.log(Level.INFO, "[Carbon] Done editing world: {0}", world.getName());
    	}
    }

    public void populate() {
    	//Add populators to worlds
        for(String s : plugin.getConfig().getStringList("options.worlds")) {
            org.bukkit.World world = plugin.getServer().getWorld(s);
            if (world != null) {
               Carbon.log.log(Level.INFO, "[Carbon] Editing world: {0}", world.getName());
               Carbon.log.log(Level.INFO, "[Carbon] Adding populator for world: {0}", world.getName());
               world.getPopulators().add(dioritePopulator);
               world.getPopulators().add(andesitePopulator);
               world.getPopulators().add(granitePopulator);
               Carbon.log.log(Level.INFO, "[Carbon] Done editing world: {0}", world.getName());
            }  
        }
    }

    public void reset() {
        //Reset populators
        for (World world : Bukkit.getWorlds()) {
            Carbon.log.log(Level.INFO, "[Carbon] Resetting populator for world: {0}", world.getName());
            world.getPopulators().remove(dioritePopulator);
            world.getPopulators().remove(andesitePopulator);
            world.getPopulators().remove(granitePopulator);
            Carbon.log.log(Level.INFO, "[Carbon] Done resetting populators for world: {0}", world.getName());
        }
    }

}
