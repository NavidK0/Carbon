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
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
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
        Carbon.log.info("[Carbon] Done with world generation editing.");
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
        Class stoneClazz = Blocks.class;
        Field stoneField = stoneClazz.getDeclaredField("STONE");
        stoneField.setAccessible(true);
        Field stoneModifiers = Field.class.getDeclaredField("modifiers");
        stoneModifiers.setAccessible(true);
        stoneModifiers.setInt(stoneField, stoneField.getModifiers() & ~Modifier.FINAL);
        stoneField.set(Blocks.STONE, Carbon.injector().stoneBlock);
        Carbon.log.info("[Carbon] Injected Carbon.injector().stoneBlock into Blocks.STONE");
        
        Class spongeClazz = Blocks.class;
        Field spongeField = spongeClazz.getDeclaredField("SPONGE");
        spongeField.setAccessible(true);
        Field spongeModifiers = Field.class.getDeclaredField("modifiers");
        spongeModifiers.setAccessible(true);
        spongeModifiers.setInt(spongeField, spongeField.getModifiers() & ~Modifier.FINAL);
        spongeField.set(Blocks.SPONGE, Carbon.injector().spongeBlock);
        Carbon.log.info("[Carbon] Injected Carbon.injector().spongeBlock into Blocks.SPONGE");
        
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldLoad(WorldLoadEvent evt) {
        if (plugin.getConfig().getStringList("options.worlds").contains(evt.getWorld().getName())) {
            org.bukkit.World world = evt.getWorld();
            if (world != null) {
                Carbon.log.log(Level.INFO, "[Carbon] Editing world: {0}", world.getName());
                Carbon.log.log(Level.INFO, "[Carbon] Adding populator for world: {0}", world.getName());
                //world.getPopulators().add(new CarbonPopulator());
                //world.getPopulators().add(new OrePopulator(Material.STONE, (byte)1, Material.STONE, (byte)0, 10, 33));
                Carbon.log.log(Level.INFO, "[Carbon] Done editing world: {0}", world.getName());
            } else {
                Carbon.log.log(Level.INFO, "[Carbon] World {0} doesn\'t exist! Cannot populate!", world.getName());
            }
        }
    }

    public class CarbonPopulator extends BlockPopulator {
            @Override
            public void populate(org.bukkit.World world, Random random, Chunk chunk) {
                    //Insert generator code here
            }

        }
}
