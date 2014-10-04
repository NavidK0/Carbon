package com.lastabyss.carbon.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.lastabyss.carbon.packets.PacketPlayOutWorldBorder;
import com.lastabyss.carbon.packets.PacketPlayOutWorldBorder.WorldBorderAction;
import com.lastabyss.carbon.utils.Utilities;
import com.lastabyss.carbon.worldborder.WorldBorder;

public class WorldBorderListener implements Listener {

	@EventHandler
	public void onPayerJoin(PlayerJoinEvent event) {
		PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(WorldBorder.getInstance(), WorldBorderAction.INITIALIZE);
		Utilities.sendPacket(event.getPlayer(), packet);
	}

}
