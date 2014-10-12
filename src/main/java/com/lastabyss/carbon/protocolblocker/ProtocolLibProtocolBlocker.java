package com.lastabyss.carbon.protocolblocker;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.server.v1_7_R4.ChatMessage;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.lastabyss.carbon.Carbon;

public class ProtocolLibProtocolBlocker extends ProtocolBlocker {

	public ProtocolLibProtocolBlocker(Carbon plugin) {
		super(plugin);
	}

	public ProtocolLibProtocolBlocker init() {
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
		return this;
	}

}
