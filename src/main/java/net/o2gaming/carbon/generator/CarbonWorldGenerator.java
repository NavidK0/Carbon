package net.o2gaming.carbon.generator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.v1_7_R4.BiomeDecorator;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.World;
import net.o2gaming.carbon.Carbon;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.generator.BlockPopulator;
import sun.reflect.generics.scope.ClassScope;


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
        Carbon.log.info("[Carbon] Modifying world generation...");
                try {
                    inject();
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(CarbonWorldGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
        Carbon.log.info("[Carbon] Done with world generation.");
        for (String s : plugin.getConfig().getStringList("options.worlds")) {
            org.bukkit.World world = plugin.getServer().getWorld(s);
            if (world != null) {
                Carbon.log.log(Level.INFO, "[Carbon] Editing world: {0}", world.getName());
                Carbon.log.log(Level.INFO, "[Carbon] Adding populator for world: {0}", world.getName());
                //world.getPopulators().add(new CarbonPopulator());
                //world.getPopulators().add(new OrePopulator(Material.STONE, (byte)1, Material.STONE, (byte)0, 10, 33));
                Carbon.log.log(Level.INFO, "[Carbon] Done editing world: {0}", world.getName());
            } else {
                Carbon.log.log(Level.INFO, "[Carbon] World {0} doesn\'t exist! Cannot populate!", s);
            }
        }
    }
    
    /**
     * Injects the CarbonWorldGenMinable in place of the BiomeDecorater used by minecraft.
     * I said to myself "screw it" and decided to modify Blocks.STONE to
     * our custom stone block.
     * 
     */
    private void inject() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        /**
         * Initiate the worst horrible thing you could possibly ever do in Java
         */
        Class clazz = Blocks.class;
        Field stoneField = clazz.getDeclaredField("STONE");
        stoneField.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(stoneField, stoneField.getModifiers() & ~Modifier.FINAL);
        stoneField.set(Blocks.STONE, Carbon.injector().stoneBlock);
        Carbon.log.info("[Carbon] Injected Carbon.injector().stoneBlock into Blocks.STONE");
        
        /**
        World world;
        Class clazz = BiomeDecorator.class; 
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
        * **/
        
    }

    public class CarbonPopulator extends BlockPopulator {
            @Override
            public void populate(org.bukkit.World world, Random random, Chunk chunk) {
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
