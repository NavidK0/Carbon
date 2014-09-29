
package net.o2gaming.carbon;

import java.io.File;
import java.util.logging.Logger;

import net.o2gaming.carbon.generator.CarbonWorldGenerator;
import net.o2gaming.carbon.listeners.BlockListener;
import net.o2gaming.carbon.listeners.ItemListener;
import net.o2gaming.carbon.protocolmodifier.ProtocolBlockListener;
import net.o2gaming.carbon.protocolmodifier.ProtocolItemListener;
import net.o2gaming.carbon.reflection.Injector;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Carbon extends JavaPlugin {
  private BlockListener blockListener = new BlockListener(this);
  private ItemListener itemListener = new ItemListener(this);
  private CarbonWorldGenerator worldGenerator = new CarbonWorldGenerator(this);
  public static final Logger log = Logger.getLogger("minecraft");
  private static Injector injector;
  private File dataFolder;
  
    @Override
    public void onLoad() {
        try {
          DynamicEnumType.loadReflection();
        }
        catch (NoSuchMethodException|SecurityException|ClassNotFoundException|IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException e) {
          e.printStackTrace();
        }
        injector = new Injector();
        injector.registerAll();
        injector.registerRecipes();
        worldGenerator.inject();
        log.info("Carbon has finished injecting all 1.8 functionalities.");
    }

    @Override
    public void onEnable() {
         dataFolder = new File(getDataFolder(), getDescription().getName());
         if (!dataFolder.exists()) {
             dataFolder.mkdir();
        }
        saveDefaultConfig();
        reloadConfig();
        worldGenerator.populate();
        getServer().getPluginManager().registerEvents(this.blockListener, this);
        getServer().getPluginManager().registerEvents(this.itemListener, this);
        getServer().getPluginManager().registerEvents(this.worldGenerator, this);

        try {
            new ProtocolBlockListener(this).init();
            new ProtocolItemListener(this).init();
        } catch (Throwable t) {
        }
        
        log.info("[Carbon] Carbon is enabled.");
    }
    
    //There is no way to reload this plugin safely do to the fact it adds 1.8 blocks into the server.
  
    public static Injector injector() {
        return injector;
    }

    public BlockListener getBlockListener() {
        return this.blockListener;
    }

    public ItemListener getItemListener() {
        return this.itemListener;
    }

    public CarbonWorldGenerator getWorldGenerator() {
        return worldGenerator;
    }
  
}