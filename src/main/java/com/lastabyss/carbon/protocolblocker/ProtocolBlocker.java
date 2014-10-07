package com.lastabyss.carbon.protocolblocker;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.utils.Utilities;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

public class ProtocolBlocker implements Listener {

	private Carbon plugin;
        private YamlConfiguration config = null;

	public ProtocolBlocker(Carbon plugin) {
		this.plugin = plugin;
	}

	public void loadConfig() {
		File configFile = new File(plugin.getDataFolder(), "protocolblocker.yml");
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
                Writer writer = new FileWriter(configFile);
                            try {
                                writer.write("#protocolversion: kickmessage (multiple lines allowed)");
                            }
                            finally {
                                writer.close();
                            }
                                config = YamlConfiguration.loadConfiguration(configFile);
                                config.set("4", "You cannot log on this server with version 1.7.9! Please upgrade to 1.8!");
                                config.set("5", "You cannot log on this server with version 1.7.10! Please upgrade to 1.8!");
                                config.save(configFile);
                                config = YamlConfiguration.loadConfiguration(configFile);
			} catch (IOException e) {
                          e.printStackTrace();
			}
		} else {
                    config = YamlConfiguration.loadConfiguration(configFile);
                }
		for (String version : config.getKeys(false)) {
			try {
				restrictedProtocols.put(Integer.parseInt(version), ChatColor.translateAlternateColorCodes('&', config.getString(version)));
			} catch (Exception e) {
                          e.printStackTrace();
			}
		}
	}

	private HashMap<Integer, String> restrictedProtocols = new HashMap<Integer, String>();

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
            if (config == null) return;
		int version = Utilities.getProtocolVersion(event.getPlayer());
		if (restrictedProtocols.containsKey(version)) {
			event.getPlayer().kickPlayer(restrictedProtocols.get(version));
		}
	}

}
