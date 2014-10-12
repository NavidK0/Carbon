package com.lastabyss.carbon;

import com.lastabyss.carbon.generator.CarbonEntityGenerator;
import com.lastabyss.carbon.generator.CarbonWorldGenerator;
import com.lastabyss.carbon.listeners.BlockListener;
import com.lastabyss.carbon.listeners.CommandListener;
import com.lastabyss.carbon.listeners.EntityListener;
import com.lastabyss.carbon.listeners.ItemListener;
import com.lastabyss.carbon.listeners.PlayerListener;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Carbon extends JavaPlugin {

  private BlockListener blockListener = new BlockListener();
  private CommandListener commandListener = new CommandListener();
  private ItemListener itemListener = new ItemListener(this);
  private WorldBorderListener worldBorderListener = new WorldBorderListener();
  private EntityListener entityListener = new EntityListener();
  private PlayerListener playerListener = new PlayerListener();

  private ProtocolBlocker protocolBlocker = new ProtocolBlocker(this);
  private CarbonWorldGenerator worldGenerator = new CarbonWorldGenerator(this);
  private CarbonEntityGenerator entityGenerator = new CarbonEntityGenerator(this);

  private PluginDescriptionFile pluginDescriptionFile = this.getDescription();
  private FileConfiguration spigotConfig = YamlConfiguration.loadConfiguration(new File(getServer().getWorldContainer(), "spigot.yml"));

  public static final Logger log = Bukkit.getLogger();

  private static Injector injector;
  private double localConfigVersion = 0.5;

  @Override
  public void onLoad() {
    //call to server shutdown if worlds are already loaded, prevents various errors when loading plugin on the fly
    if (!Bukkit.getWorlds().isEmpty()) {
      log.log(Level.SEVERE, "World loaded before{0} {1}! (Was {2} loaded on the fly?)", new Object[]{pluginDescriptionFile.getName(), pluginDescriptionFile.getVersion(), pluginDescriptionFile.getName()});
      if (spigotConfig.getBoolean("settings.restart-on-crash")) {
        getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
      }

      Bukkit.shutdown();
      return;
    }

    //Prepare for injection of enumerators... a necessary evil
    try {
      DynamicEnumType.loadReflection();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    injector = new Injector();
    injector.registerAll();
    injector.registerRecipes();
    
    entityGenerator.injectNewCreatures();
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
    getServer().getPluginManager().registerEvents(entityListener, this);
    getServer().getPluginManager().registerEvents(worldBorderListener, this);
    getServer().getPluginManager().registerEvents(playerListener, this);
    protocolBlocker.loadConfig();
    getServer().getPluginManager().registerEvents(protocolBlocker, this);
    
    if (getConfig().getDouble("donottouch.configVersion", 0.0f) < localConfigVersion) {
      log.warning(
          "Please delete your Carbon config and let it regenerate! Yours is outdated and may cause issues with the mod!");
    }

    if (getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
      try {
        new ProtocolBlockListener(this).loadRemapList().init();
        new ProtocolItemListener(this).loadRemapList().init();
        new ProtocolEntityListener(this).loadRemapList().init();
        protocolBlocker.initProtocolLibListener();
      } catch (Throwable t) {
        t.printStackTrace();
      }
    } else {
      log.info("ProtocolLib not found, not hooking. 1.7 clients not supported. ProtocolBlocker will kick clients only after full join.");
    }
    try {
        Metrics metrics = new Metrics(this);
        metrics.start();
    } catch (IOException e) {}
    log.info("Carbon is enabled.");
  }

  //We don't want to rely on Vault for this plugin, so the command shall be OP only for now.
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("carbon")) {
            if (sender.isOp()) {
                if (args.length == 0) {
                    printHelpMenu(sender);
                    return true;
                }
                if (args.length == 1) {
                    String arg = args[0];
                    if (arg.equalsIgnoreCase("reload")) {
                        worldGenerator.reset();
                        sender.sendMessage(ChatColor.GREEN + "[Carbon] The world generator has been reset for all worlds.");
                        log.log(Level.INFO, "{0}[Carbon] The world generator has been reset for all worlds.", ChatColor.GREEN);
                        reloadConfig();
                        protocolBlocker.loadConfig();
                        sender.sendMessage(ChatColor.GREEN + "[Carbon] The config has been reloaded.");
                        log.log(Level.INFO, "{0}[Carbon] The config has been reloaded.", ChatColor.GREEN);
                        worldGenerator.populate();
                    } else {
                        sender.sendMessage(ChatColor.RED + "[Carbon] Invalid argument!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "[Carbon] Invalid argument length!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "[Carbon] You must be opped in order to use this command!");
            }
        }
        return true;
    }

  @Override
  public void onDisable() {
    WorldBorder.save();
  }

  //There is no way to reload this plugin safely do to the fact it adds 1.8 blocks into the server.

  public static Injector injector() {
    return injector;
  }

    public BlockListener getBlockListener() {
        return blockListener;
    }

    public WorldBorderListener getWorldBorderListener() {
        return worldBorderListener;
    }

    public CarbonWorldGenerator getWorldGenerator() {
        return worldGenerator;
    }

    public CommandListener getCommandListener() {
        return commandListener;
    }

    public CarbonEntityGenerator getEntityGenerator() {
        return entityGenerator;
    }

    public EntityListener getEntityListener() {
        return entityListener;
    }

    public ItemListener getItemListener() {
        return itemListener;
    }

    public double getLocalConfigVersion() {
        return localConfigVersion;
    }

    public ProtocolBlocker getProtocolBlocker() {
        return protocolBlocker;
    }

  private void printHelpMenu(CommandSender sender) {
      sender.sendMessage(ChatColor.DARK_GRAY + "--=======" + ChatColor.DARK_RED + "Carbon" + ChatColor.DARK_GRAY + "=======--");
      sender.sendMessage(ChatColor.DARK_GRAY + "Version: " + ChatColor.DARK_RED + getDescription().getVersion());
      sender.sendMessage(ChatColor.DARK_GRAY + "Authors: " + ChatColor.DARK_RED + getDescription().getAuthors().toString().replaceAll("\\[|\\]", ""));
      sender.sendMessage(ChatColor.DARK_GRAY + "Other contributors:" + ChatColor.DARK_RED + " pupnewfster, Stefenatefun, Jikoo, Wombosvideo, mcmonkey4eva, sickray34s");
      sender.sendMessage(ChatColor.DARK_GRAY + "IRC:" + ChatColor.DARK_RED + " irc.esper.net");
      sender.sendMessage(ChatColor.DARK_GRAY + "Channel:" + ChatColor.DARK_RED + " #Carbon");
      sender.sendMessage(ChatColor.DARK_GRAY + "Page:" + ChatColor.DARK_RED + " http://www.spigotmc.org/resources/carbon.1258/");
      sender.sendMessage(ChatColor.DARK_GRAY + "Use /carbon" + ChatColor.DARK_RED + " reload " + ChatColor.DARK_GRAY + "to reload the configuration from disk.");
  }

}
