
package com.lastabyss.carbon;

import java.util.logging.Logger;

import com.lastabyss.carbon.generator.CarbonWorldGenerator;
import com.lastabyss.carbon.listeners.BlockListener;
import com.lastabyss.carbon.listeners.ItemListener;
import com.lastabyss.carbon.listeners.WorldBorderListener;
import com.lastabyss.carbon.protocolblocker.ProtocolBlocker;
import com.lastabyss.carbon.protocolmodifier.ProtocolBlockListener;
import com.lastabyss.carbon.protocolmodifier.ProtocolEntityListener;
import com.lastabyss.carbon.protocolmodifier.ProtocolItemListener;
import com.lastabyss.carbon.reflection.Injector;
import com.lastabyss.carbon.utils.Utilities;
import com.lastabyss.carbon.worldborder.WorldBorder;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Carbon extends JavaPlugin {

  private BlockListener blockListener = new BlockListener();
  private ItemListener itemListener = new ItemListener(this);
  private WorldBorderListener worldBorderListener = new WorldBorderListener();
  private ProtocolBlocker protocolBlocker = new ProtocolBlocker(this);
  private CarbonWorldGenerator worldGenerator = new CarbonWorldGenerator(this);
  public static final Logger log = Logger.getLogger("minecraft");
  private static Injector injector;
  private double localConfigVersion = 0.1;
  
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
        Utilities.instantiate(this);
        saveDefaultConfig();
        if (!this.getDataFolder().exists()) {
        	this.getDataFolder().mkdirs();
        }
        reloadConfig();
        worldGenerator.populate();
        getServer().getPluginManager().registerEvents(this.blockListener, this);
        getServer().getPluginManager().registerEvents(this.itemListener, this);
        getServer().getPluginManager().registerEvents(this.worldGenerator, this);
        getServer().getPluginManager().registerEvents(this.worldBorderListener, this);
        protocolBlocker.loadConfig();
        getServer().getPluginManager().registerEvents(this.protocolBlocker, this);
        
        if (getConfig().getDouble("donottouch.configVersion", 0.0f) < localConfigVersion) {
            log.warning("[Carbon] Please delete your Carbon config and let it regenerate! Yours is outdated and may cause issues with the mod!");
        }

        if (getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
	        try {
	            new ProtocolBlockListener(this).init();
	            new ProtocolItemListener(this).init();
	            new ProtocolEntityListener(this).init();
	        } catch (Throwable t) {
	        	t.printStackTrace();
	        }
        } else {
            log.info("[Carbon] ProtocolLib not found, not hooking. 1.7 clients not supported.");
        }
        log.info("[Carbon] Carbon is enabled.");
    }

    @Override
    public void onDisable() {
    	WorldBorder.save();
    	//call to server shutdown on disable, won't hurt if server already disables itself, but will prevent plugin unload/reload
    	Bukkit.shutdown();
    }
    
    //There is no way to reload this plugin safely do to the fact it adds 1.8 blocks into the server.
  
    public static Injector injector() {
        return injector;
    }

}