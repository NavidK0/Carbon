package com.lastabyss.carbon.worldborder;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.lastabyss.carbon.packets.PacketPlayOutWorldBorder;
import com.lastabyss.carbon.packets.PacketPlayOutWorldBorder.WorldBorderAction;
import com.lastabyss.carbon.utils.Utilities;

import net.minecraft.server.v1_7_R4.Packet;

public class WorldBorderPlayerUpdater implements WorldBorderChangeListener {

	private World world;
	public WorldBorderPlayerUpdater(World world) {
		this.world = world;
	}

	public void onSizeSet(WorldBorder worldborder, double size) {
		sendToAll(new PacketPlayOutWorldBorder(worldborder, WorldBorderAction.SET_SIZE));
	}

	public void onSizeChange(WorldBorder worldborder, double oldRadius, double newRadius, long time) {
		sendToAll(new PacketPlayOutWorldBorder(worldborder, WorldBorderAction.LERP_SIZE));
	}

	public void onSetCenter(WorldBorder worldborder, double x, double z) {
		sendToAll(new PacketPlayOutWorldBorder(worldborder, WorldBorderAction.SET_CENTER));
	}

	public void onSetWarningTime(WorldBorder worldborder, int time) {
		sendToAll(new PacketPlayOutWorldBorder(worldborder, WorldBorderAction.SET_WARNING_TIME));
	}

	public void onSetWarningBlocks(WorldBorder worldborder, int blocks) {
		sendToAll(new PacketPlayOutWorldBorder(worldborder, WorldBorderAction.SET_WARNING_BLOCKS));
	}

	public void onSetDamageAmount(WorldBorder worldborder, double damage) {
	}

	public void onSetDamageBuffer(WorldBorder worldborder, double buffer) {
	}

	private void sendToAll(Packet packet) {
		for (Player player : world.getPlayers()) {
			if (player.getWorld().equals(world)) {
				Utilities.sendPacket(player, packet);
			}
		}
	}

}
