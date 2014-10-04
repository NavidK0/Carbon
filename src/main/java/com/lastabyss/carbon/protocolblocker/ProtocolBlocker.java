package com.lastabyss.carbon.protocolblocker;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.lastabyss.carbon.Carbon;

public class ProtocolBlocker implements Listener {

	private Carbon plugin;

	public ProtocolBlocker(Carbon plugin) {
		this.plugin = plugin;
	}

	public void loadConfig() {
		File configFile = new File(plugin.getDataFolder(), "protocolblocker.yml");
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
			}
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		for (String version : config.getKeys(false)) {
			try {
				restrictedProtocols.put(Integer.parseInt(version), ChatColor.translateAlternateColorCodes('&', config.getString(version)));
			} catch (Exception e) {
			}
		}
	}

	private HashMap<Integer, String> restrictedProtocols = new HashMap<Integer, String>();

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		int version = ((CraftPlayer) event.getPlayer()).getHandle().playerConnection.networkManager.getVersion();
		if (restrictedProtocols.containsKey(version)) {
			event.getPlayer().kickPlayer(restrictedProtocols.get(version));
		}
	}

}
