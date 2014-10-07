package com.lastabyss.carbon.generator;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.generator.populator.StoneVariantPopulator;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.WorldType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;


/**
 *
 * @author Navid
 */
public class CarbonWorldGenerator implements Listener {
    Carbon plugin;
    public final static StoneVariantPopulator dioritePopulator = new StoneVariantPopulator(Material.STONE, (byte) 1, 33, 4);
    public final static StoneVariantPopulator andesitePopulator = new StoneVariantPopulator(Material.STONE, (byte) 3, 33, 4);
    public final static StoneVariantPopulator granitePopulator = new StoneVariantPopulator(Material.STONE, (byte) 5, 33, 4);

    public CarbonWorldGenerator(Carbon plugin) {
    this.plugin = plugin;
    }
    
    public void regenerate() {
        for (int x = 4501; x <= 9000; x++) {
                for (int z = 4501; z <= 9000; z++) {
                    org.bukkit.World world = Bukkit.getServer().getWorld("Survival");
                    Chunk chunk = world.getChunkAt(world.getBlockAt(x, 256, z));
                    world.regenerateChunk(chunk.getX(), chunk.getZ());
                    world.unloadChunk(chunk.getX(), chunk.getZ(), false);
                }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldLoad(final WorldLoadEvent evt) {
        //Run populator tasks on world load.
        BukkitRunnable run = new BukkitRunnable() {
            @Override
            public void run() {
                    org.bukkit.World world = evt.getWorld();
                    if (world != null) {
                    if ((!world.getPopulators().contains(dioritePopulator)
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
        };
        run.runTask(plugin);
    }

}
