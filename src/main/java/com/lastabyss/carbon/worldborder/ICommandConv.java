package com.lastabyss.carbon.worldborder;

import net.minecraft.server.v1_7_R4.ChunkCoordinates;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.ICommandListener;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import net.minecraft.server.v1_7_R4.World;

import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ICommandConv implements ICommandListener {
	private Player p;
	public ICommandConv(Player p){
		this.p = p;
	}
	@Override
	public boolean a(int arg0, String arg1) {
		return false;
	}

	@Override
	public ChunkCoordinates getChunkCoordinates() {
		return new ChunkCoordinates(p.getLocation().getBlockX(),p.getLocation().getBlockY(),p.getLocation().getBlockZ());
	}

	@Override
	public String getName() {
		return p.getName();
	}

	@Override
	public IChatBaseComponent getScoreboardDisplayName() {
		return null;
	}

	@Override
	public World getWorld() {
		return ((CraftWorld)p.getWorld()).getHandle();
	}

	@Override
	public void sendMessage(IChatBaseComponent arg0) {
	    PacketPlayOutChat packet = new PacketPlayOutChat(arg0, true);
	    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

}
