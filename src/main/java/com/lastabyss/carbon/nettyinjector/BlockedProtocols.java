package com.lastabyss.carbon.nettyinjector;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import com.lastabyss.carbon.Carbon;

public class BlockedProtocols {

	private final static HashMap<Integer, String> restrictedProtocols = new HashMap<Integer, String>();
	private static boolean is17blocked = false;

	public static void loadConfig(Carbon plugin) {
		File configFile = new File(plugin.getDataFolder(), "protocolblocker.yml");
		if (!configFile.exists()) {
			try {
				YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
				config.set("4", "You cannot log on this server with versions 1.7.2-1.7.5! Please upgrade to 1.8!");
				config.set("5", "You cannot log on this server with versions 1.7.6-1.7.10! Please upgrade to 1.8!");
				config.save(configFile);
				config = YamlConfiguration.loadConfiguration(configFile);
			} catch (IOException e) {
				e.printStackTrace(System.out);
			}
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		restrictedProtocols.clear();
		for (String version : config.getKeys(false)) {
			try {
				restrictedProtocols.put(Integer.parseInt(version), ChatColor.translateAlternateColorCodes('&', config.getString(version)));
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
		if (restrictedProtocols.containsKey(4) && restrictedProtocols.containsKey(5)) {
			is17blocked = true;
		}
	}

	public static String getBlockedMessage(int protocol) {
		return restrictedProtocols.get(protocol);
	}

	public static boolean is17Blocked() {
		return is17blocked;
	}

}
