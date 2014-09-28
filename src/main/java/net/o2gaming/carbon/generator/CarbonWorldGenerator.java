package net.o2gaming.carbon.generator;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.v1_7_R4.BiomeDecorator;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.WorldGenMinable;
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
                Carbon.log.log(Level.INFO, "[Carbon] Editing world: {0}", world.getName());
                Carbon.log.info("[Carbon] Modifying world generation...");
                inject();
                Carbon.log.info("[Carbon] Done with world generation.");
                Carbon.log.log(Level.INFO, "[Carbon] Adding populator for world: {0}", world.getName());
                //world.getPopulators().add(new CarbonPopulator());
                //world.getPopulators().add(new OrePopulator(Material.STONE, (byte)1, Material.STONE, (byte)0, 10, 33));
                Carbon.log.log(Level.INFO, "[Carbon] Done editing world: {0}", world.getName());
            } else {
                Carbon.log.log(Level.INFO, "[Carbon] World {0} doesn't exist! Cannot populate!", s);
            }
        }
    }
    
    /**
     * Injects the CarbonWorldGenMinable in place of the BiomeDecorater used by minecraft.
     * This DOES NOT WORK YET.
     */
    private void inject() {
        Class clazz = BiomeDecorator.class;
        try {
            Field h = clazz.getDeclaredField("h");
            h.setAccessible(true);
            h.set(new CarbonWorldGenMinable(Blocks.DIRT, 32), new CarbonWorldGenMinable(Blocks.DIRT, 32));
            
            Field i = clazz.getDeclaredField("i");
            i.setAccessible(true);
            i.set(new CarbonWorldGenMinable(Blocks.GRAVEL, 32), new CarbonWorldGenMinable(Blocks.GRAVEL, 32));
            
            Field j = clazz.getDeclaredField("j");
            j.setAccessible(true);
            j.set(new CarbonWorldGenMinable(Blocks.COAL_ORE, 16), new CarbonWorldGenMinable(Blocks.COAL_ORE, 16));
            
            Field k = clazz.getDeclaredField("k");
            k.setAccessible(true);
            k.set(new CarbonWorldGenMinable(Blocks.IRON_ORE, 8), new CarbonWorldGenMinable(Blocks.IRON_ORE, 8));
            
            Field l = clazz.getDeclaredField("l");
            l.setAccessible(true);
            l.set(new CarbonWorldGenMinable(Blocks.GOLD_ORE, 8), new CarbonWorldGenMinable(Blocks.GOLD_ORE, 8));
            
            Field m = clazz.getDeclaredField("m");
            m.setAccessible(true);
            m.set(new CarbonWorldGenMinable(Blocks.REDSTONE_ORE, 7), new CarbonWorldGenMinable(Blocks.REDSTONE_ORE, 7));
            
            Field n = clazz.getDeclaredField("n");
            n.setAccessible(true);
            n.set(new CarbonWorldGenMinable(Blocks.DIAMOND_ORE, 7), new CarbonWorldGenMinable(Blocks.DIAMOND_ORE, 7));
            
            Field o = clazz.getDeclaredField("o");
            o.setAccessible(true);
            o.set(new CarbonWorldGenMinable(Blocks.LAPIS_ORE, 6), new CarbonWorldGenMinable(Blocks.LAPIS_ORE, 6));
            
        } catch (NoSuchFieldException | SecurityException ex) {
            Logger.getLogger(CarbonWorldGenerator.class.getName()).log(Level.SEVERE, null, ex);
            Carbon.log.warning("[Carbon] Failed to inject CarbonWorldGenMinable! Ore generation will not be possible!");
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(CarbonWorldGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CarbonWorldGenerator.class.getName()).log(Level.SEVERE, null, ex);
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
