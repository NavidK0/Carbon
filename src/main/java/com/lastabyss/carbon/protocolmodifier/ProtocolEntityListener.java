package com.lastabyss.carbon.protocolmodifier;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.lastabyss.carbon.Carbon;

public class ProtocolEntityListener {

	private Carbon plugin;

	public ProtocolEntityListener(Carbon plugin) {
		this.plugin = plugin;
	}

	private int replacements[] = new int[256];
	{
		for (int i = 0; i < replacements.length; i++) {
			replacements[i] = -1;
		}
		//endermite -> silverfish
		replacements[67] = 60;
	}

	public void init() {
		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter
				.params(plugin, PacketType.Play.Server.SPAWN_ENTITY_LIVING)
				.listenerPriority(ListenerPriority.HIGHEST)
			) {
				@Override
				public void onPacketSending(PacketEvent event) {
					if (ProtocolLibrary.getProtocolManager().getProtocolVersion(event.getPlayer()) == 47) {
						return;
					}
					int type = event.getPacket().getIntegers().read(1);
					if (replacements[type] != -1) {
						event.getPacket().getIntegers().write(1, replacements[type]);
					}
				}
			}
		);
	}

}
