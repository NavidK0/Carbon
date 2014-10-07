package com.lastabyss.carbon;

import com.lastabyss.carbon.generator.CarbonWorldGenerator;
import com.lastabyss.carbon.listeners.BlockListener;
import com.lastabyss.carbon.listeners.CommandListener;
import com.lastabyss.carbon.listeners.ItemListener;
import com.lastabyss.carbon.listeners.WorldBorderListener;
import com.lastabyss.carbon.protocolblocker.ProtocolBlocker;
import com.lastabyss.carbon.protocolmodifier.ProtocolBlockListener;
import com.lastabyss.carbon.protocolmodifier.ProtocolEntityListener;
import com.lastabyss.carbon.protocolmodifier.ProtocolItemListener;
import com.lastabyss.carbon.reflection.Injector;
import com.lastabyss.carbon.utils.Metrics;
import com.lastabyss.carbon.utils.Utilities;
import com.lastabyss.carbon.worldborder.WorldBorder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Carbon extends JavaPlugin {

  private BlockListener blockListener = new BlockListener();
  private CommandListener commandListener = new CommandListener();
  private ItemListener itemListener = new ItemListener(this);
  private WorldBorderListener worldBorderListener = new WorldBorderListener();

  private ProtocolBlocker protocolBlocker = new ProtocolBlocker(this);
  private CarbonWorldGenerator worldGenerator = new CarbonWorldGenerator(this);

  private PluginDescriptionFile pluginDescriptionFile = this.getDescription();
  private FileConfiguration spigot = YamlConfiguration.loadConfiguration(new File(getServer().getWorldContainer(), "spigot.yml"));

  public static final Logger log = Bukkit.getLogger();

  private static Injector injector;
  private double localConfigVersion = 0.3;

  @Override
  public void onLoad() {
    //call to server shutdown if worlds are already loaded, prevents various errors when loading plugin on the fly
    if (!Bukkit.getWorlds().isEmpty()) {
      log.log(Level.SEVERE, "World loaded before{0} {1}! (Was {2} loaded on the fly?)", new Object[]{pluginDescriptionFile.getName(), pluginDescriptionFile.getVersion(), pluginDescriptionFile.getName()});
      if (spigot.getBoolean("settings.restart-on-crash")) {
        getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
      }

      Bukkit.shutdown();
      return;
    }

    //inject new blocks
    try {
      DynamicEnumType.loadReflection();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    injector = new Injector();
    injector.registerAll();
    injector.registerRecipes();
    worldGenerator.populate();
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
    getServer().getPluginManager().registerEvents(blockListener, this);
    getServer().getPluginManager().registerEvents(commandListener, this);
    getServer().getPluginManager().registerEvents(itemListener, this);
    getServer().getPluginManager().registerEvents(worldGenerator, this);
    getServer().getPluginManager().registerEvents(worldBorderListener, this);
    protocolBlocker.loadConfig();
    getServer().getPluginManager().registerEvents(protocolBlocker, this);

    if (getConfig().getDouble("donottouch.configVersion", 0.0f) < localConfigVersion) {
      log.warning(
          "Please delete your Carbon config and let it regenerate! Yours is outdated and may cause issues with the mod!");
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
      log.info("ProtocolLib not found, not hooking. 1.7 clients not supported.");
    }
    try {
        Metrics metrics = new Metrics(this);
        metrics.start();
    } catch (IOException e) {}
    log.info("Carbon is enabled.");
  }

  @Override
  public void onDisable() {
    WorldBorder.save();
  }

  //There is no way to reload this plugin safely do to the fact it adds 1.8 blocks into the server.

  public static Injector injector() {
    return injector;
  }

}
