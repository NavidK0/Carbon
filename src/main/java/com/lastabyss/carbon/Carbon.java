
package com.lastabyss.carbon;

import java.io.File;
import java.util.logging.Logger;

import com.lastabyss.carbon.generator.CarbonWorldGenerator;
import com.lastabyss.carbon.listeners.BlockListener;
import com.lastabyss.carbon.listeners.ItemListener;
import com.lastabyss.carbon.protocolmodifier.ProtocolBlockListener;
import com.lastabyss.carbon.protocolmodifier.ProtocolItemListener;
import com.lastabyss.carbon.reflection.Injector;

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
    	//call to server shutdown if worlds are already loaded, prevents various errors when loading plugin on the fly
    	if (!Bukkit.getWorlds().isEmpty()) {
    		Bukkit.shutdown();
    		return;
    	}

    	//inject new blocks
        try {
          DynamicEnumType.loadReflection();
        }
        catch (NoSuchMethodException|SecurityException|ClassNotFoundException|IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException e) {
          e.printStackTrace();
        }
        injector = new Injector();
        injector.registerAll();
        injector.registerRecipes();
        
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
        	t.printStackTrace();
        }
        
        log.info("[Carbon] Carbon is enabled.");
    }

    @Override
    public void onDisable() {
    	//call to server shutdown on disable, won't hurt if server already disables itself, but will prevent plugin unload/reload
    	Bukkit.shutdown();
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