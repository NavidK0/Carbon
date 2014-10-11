package com.lastabyss.carbon.protocolblocker;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.utils.Utilities;

import net.minecraft.server.v1_7_R4.ChatMessage;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;

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
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.bukkit.scheduler.BukkitRunnable;

public class ProtocolBlocker implements Listener {

	private Carbon plugin;
	private YamlConfiguration config = null;

	public ProtocolBlocker(Carbon plugin) {
		this.plugin = plugin;
	}

	private HashMap<Integer, String> restrictedProtocols = new HashMap<Integer, String>();

	public void loadConfig() {
		File configFile = new File(plugin.getDataFolder(), "protocolblocker.yml");
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
				Writer writer = new FileWriter(configFile);
				try {
					writer.write("#protocolversion: kickmessage (multiple lines allowed)");
				} finally {
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

	public void initProtocolLibListener() {
		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter
				.params(plugin, PacketType.Handshake.Client.SET_PROTOCOL)
				.listenerPriority(ListenerPriority.HIGHEST)
			) {
				@Override
				public void onPacketReceiving(PacketEvent event) {
					int version = event.getPacket().getIntegers().read(0);
					if (restrictedProtocols.containsKey(version)) {
						event.setCancelled(true);
						try {
							PacketContainer kick = new PacketContainer(PacketType.Login.Server.DISCONNECT);
							ChatMessage message = new ChatMessage(restrictedProtocols.get(version));
							kick.getSpecificModifier(IChatBaseComponent.class).write(0, message);
							ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), kick);
						} catch (InvocationTargetException e) {
							e.printStackTrace();
							event.getPlayer().kickPlayer(restrictedProtocols.get(version));
						}
					}
				}
			}
		);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		if (config == null)
			return;
		BukkitRunnable task = new BukkitRunnable() {
			@Override
			public void run() {
				int version = Utilities.getProtocolVersion(event.getPlayer());
				if (restrictedProtocols.containsKey(version)) {
					event.getPlayer().kickPlayer(restrictedProtocols.get(version));
				}
			}
		};
		task.runTask(plugin);
	}

}
