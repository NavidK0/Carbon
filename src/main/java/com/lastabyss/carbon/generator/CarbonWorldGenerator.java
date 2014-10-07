package com.lastabyss.carbon.generator;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.generator.populator.StoneVariantPopulator;
import java.util.logging.Level;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;


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
    
    @EventHandler()
    public void onWorldLoad(WorldLoadEvent evt) {
        //Run populator tasks on world load.
        BukkitRunnable run = new BukkitRunnable() {
            @Override
            public void run() {
                for(String s : plugin.getConfig().getStringList("options.worlds")) {
                    org.bukkit.World world = plugin.getServer().getWorld(s);
                    if (world != null) {
                    if (world.getName().equals(s) && (!world.getPopulators().contains(dioritePopulator)
                            && !world.getPopulators().contains(andesitePopulator)
                            && !world.getPopulators().contains(granitePopulator))) {
                    Carbon.log.log(Level.INFO, "[Carbon] Editing world: {0}", world.getName());
                    Carbon.log.log(Level.INFO, "[Carbon] Adding populator for world: {0}", world.getName());
                    world.getPopulators().add(dioritePopulator);
                    world.getPopulators().add(andesitePopulator);
                    world.getPopulators().add(granitePopulator);
                    Carbon.log.log(Level.INFO, "[Carbon] Done editing world: {0}", world.getName());
                }
                    }
                }
            }
        };
        run.runTask(plugin);
    }

    public void populate() {
        //Run populator tasks after server starts.
        BukkitRunnable run = new BukkitRunnable() {
            @Override
            public void run() {
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
        };
        run.runTask(plugin);
    }
    
    public void reset() {
        //Reset populators
        BukkitRunnable run = new BukkitRunnable() {
            @Override
            public void run() {
                for(String s : plugin.getConfig().getStringList("options.worlds")) {
                    org.bukkit.World world = plugin.getServer().getWorld(s);
                if (world != null) {
                    Carbon.log.log(Level.INFO, "[Carbon] Resetting populator for world: {0}", world.getName());
                    world.getPopulators().remove(dioritePopulator);
                    world.getPopulators().remove(andesitePopulator);
                    world.getPopulators().remove(granitePopulator);
                    Carbon.log.log(Level.INFO, "[Carbon] Done resetting populators for world: {0}", world.getName());
                }
                }
            }
        };
        run.runTask(plugin);
    }

}
