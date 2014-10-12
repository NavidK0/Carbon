package com.lastabyss.carbon.protocolblocker;

import com.lastabyss.carbon.Carbon;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public abstract class ProtocolBlocker implements Listener {

	protected Carbon plugin;

	public ProtocolBlocker(Carbon plugin) {
		this.plugin = plugin;
	}

	protected HashMap<Integer, String> restrictedProtocols = new HashMap<Integer, String>();

	public void loadConfig() {
		File configFile = new File(plugin.getDataFolder(), "protocolblocker.yml");
		if (!configFile.exists()) {
			try {
				YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
				config.set("4", "You cannot log on this server with version 1.7.9! Please upgrade to 1.8!");
				config.set("5", "You cannot log on this server with version 1.7.10! Please upgrade to 1.8!");
				config.save(configFile);
				config = YamlConfiguration.loadConfiguration(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		for (String version : config.getKeys(false)) {
			try {
				restrictedProtocols.put(Integer.parseInt(version), ChatColor.translateAlternateColorCodes('&', config.getString(version)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
