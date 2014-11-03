package com.lastabyss.carbon.listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.io.File;

public class CommandListener implements Listener {

  @EventHandler
  public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    FileConfiguration spigot = YamlConfiguration.loadConfiguration(new File(Bukkit.getServer().getWorldContainer(), "spigot.yml"));
    if (event.getMessage().equalsIgnoreCase("/reload") && event.getPlayer().hasPermission("bukkit.command.reload")) {
      // Restarts server if server is set up for it.
      if (spigot.getBoolean("settings.restart-on-crash")) {
        Bukkit.getLogger().severe("[Carbon] Restarting server due to reload command!");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
      } else {
        // Call to server shutdown on disable.
        // Won't hurt if server already disables itself, but will prevent plugin unload/reload.
        Bukkit.getLogger().severe("[Carbon] Stopping server due to reload command!");
        Bukkit.shutdown();
      }
    }
    }

  @EventHandler
  public void onServerCommand(ServerCommandEvent event) {
    FileConfiguration spigot = YamlConfiguration.loadConfiguration(new File(Bukkit.getServer().getWorldContainer(), "spigot.yml"));

    if (event.getCommand().equalsIgnoreCase("reload")) {
      // Restarts server if server is set up for it.
      if (spigot.getBoolean("settings.restart-on-crash")) {
        Bukkit.getLogger().severe("Restarting server due to reload command!");
        event.setCommand("restart");
      } else {
        // Call to server shutdown on disable.
        // Won't hurt if server already disables itself, but will prevent plugin unload/reload.
        Bukkit.getLogger().severe("Stopping server due to reload command!");
        Bukkit.shutdown();
      }
    }
  }
}
