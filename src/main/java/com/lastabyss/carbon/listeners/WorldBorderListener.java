package com.lastabyss.carbon.listeners;

import com.lastabyss.carbon.packets.PacketPlayOutWorldBorder;
import com.lastabyss.carbon.packets.PacketPlayOutWorldBorder.WorldBorderAction;
import com.lastabyss.carbon.utils.Utilities;
import com.lastabyss.carbon.worldborder.WorldBorder;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class WorldBorderListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(WorldBorder.getInstance(event.getPlayer().getWorld()), WorldBorderAction.INITIALIZE);
		Utilities.sendPacket(event.getPlayer(), packet);
	}

	@EventHandler
	public void onPlayerWorldChange(PlayerChangedWorldEvent event) {
		PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(WorldBorder.getInstance(event.getPlayer().getWorld()), WorldBorderAction.INITIALIZE);
		Utilities.sendPacket(event.getPlayer(), packet);
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(WorldBorder.getInstance(event.getPlayer().getWorld()), WorldBorderAction.INITIALIZE);
		Utilities.sendPacket(event.getPlayer(), packet);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (!WorldBorder.getInstance(event.getPlayer().getWorld()).isInside(event.getBlock())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (!WorldBorder.getInstance(event.getPlayer().getWorld()).isInside(event.getBlock())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (!WorldBorder.getInstance(event.getPlayer().getWorld()).isInside(event.getTo()) && WorldBorder.getInstance(event.getPlayer().getWorld()).isInside(event.getFrom())) {
			event.setCancelled(true);
		}
	}

}
