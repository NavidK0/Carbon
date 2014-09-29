package net.o2gaming.carbon.generator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.v1_7_R4.Blocks;
import net.o2gaming.carbon.Carbon;
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

    public void inject() {
        Carbon.log.info("[Carbon] Modifying world generation...");
                try {
                    modifyWorldGeneration();
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(CarbonWorldGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
        Carbon.log.info("[Carbon] Done with world generation editing.");
        
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
                    world.getPopulators().add(new StoneVariantPopulator(Material.STONE, (byte) 1, 33, 10));
                    world.getPopulators().add(new StoneVariantPopulator(Material.STONE, (byte) 3, 33, 10));
                    world.getPopulators().add(new StoneVariantPopulator(Material.STONE, (byte) 5, 33, 10));
                    Carbon.log.log(Level.INFO, "[Carbon] Done editing world: {0}", world.getName());
                }
                }
            }
        };
        run.runTask(plugin);
    }
    
    /**
     * Injects the CarbonWorldGenMinable in place of the BiomeDecorater used by minecraft.
     * I said to myself "screw it" and decided to modify Blocks.STONE to
     * our custom stone block.
     * 
     */
    private void modifyWorldGeneration() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
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

}
