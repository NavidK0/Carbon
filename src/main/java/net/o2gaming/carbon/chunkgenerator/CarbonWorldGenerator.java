package net.o2gaming.carbon.chunkgenerator;

import java.util.Random;
import java.util.logging.Level;
import net.o2gaming.carbon.Carbon;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.generator.BlockPopulator;


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
        for (String s : plugin.getConfig().getStringList("options.worlds")) {
            World world = plugin.getServer().getWorld(s);
            if (world != null) {
                Carbon.log.log(Level.INFO, "[Carbon] Adding populator for world: {0}", world.getName());
                world.getPopulators().add(new CarbonPopulator());
                Carbon.log.log(Level.INFO, "[Carbon] Done populating world: {0}", world.getName());
            } else {
                Carbon.log.log(Level.INFO, "[Carbon] World {0} doesn''t exist! Cannot populate!", s);
            }
        }
    }

    public class CarbonPopulator extends BlockPopulator {
            @Override
            public void populate(World world, Random random, Chunk chunk) {
                    chunk.getBlock(8, 72, 8).setType(Material.GLOWSTONE);
                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            chunk.getBlock(x, 69, z).setType(Material.STONE);
                            chunk.getBlock(x, 69, z).setData((byte)3);
                        }
                    }
            }

        }
}
