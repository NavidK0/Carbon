package com.lastabyss.carbon.generator;

import com.lastabyss.carbon.generator.populator.StoneVariantPopulator;
import java.util.logging.Level;
import com.lastabyss.carbon.Carbon;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;


/**
 *
 * @author Navid
 */
public class CarbonWorldGenerator implements Listener {
    Carbon plugin;

    public CarbonWorldGenerator(Carbon plugin) {
    this.plugin = plugin;
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
                    world.getPopulators().add(new StoneVariantPopulator(Material.STONE, (byte) 1, 33, 4));
                    world.getPopulators().add(new StoneVariantPopulator(Material.STONE, (byte) 3, 33, 4));
                    world.getPopulators().add(new StoneVariantPopulator(Material.STONE, (byte) 5, 33, 4));
                    Carbon.log.log(Level.INFO, "[Carbon] Done editing world: {0}", world.getName());
                }
                }
            }
        };
        run.runTask(plugin);
    }

}
