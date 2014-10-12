package com.lastabyss.carbon.protocolblocker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.utils.Utilities;

public class BukkitProtocolBlocker extends ProtocolBlocker {

	public BukkitProtocolBlocker(Carbon plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		int version = Utilities.getProtocolVersion(event.getPlayer());
		if (restrictedProtocols.containsKey(version)) {
			event.getPlayer().kickPlayer(restrictedProtocols.get(version));
		}
	}

}
