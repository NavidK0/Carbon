package com.lastabyss.carbon.protocolmodifier;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.utils.Utilities;

public class ProtocolEntityListener {

	private Carbon plugin;

	public ProtocolEntityListener(Carbon plugin) {
		this.plugin = plugin;
	}

	private int replacementsLiving[] = new int[256];
	private int replacementsObjects[] = new int[256];

	public ProtocolEntityListener loadRemapList() {
		for (int i = 0; i < replacementsLiving.length; i++) {
			replacementsLiving[i] = -1;
		}
		// endermite -> silverfish
		replacementsLiving[67] = plugin.getConfig().getInt("protocollib.entities.silverfish", 60);
		// guardian -> sqiud
		replacementsLiving[68] = plugin.getConfig().getInt("protocollib.entities.endermites", 94);
		// rabbit -> chicken
		replacementsLiving[101] = plugin.getConfig().getInt("protocollib.entities.rabbits", 93);

		for (int i = 0; i < replacementsObjects.length; i++) {
			replacementsObjects[i] = -1;
		}
		// armor stand -> ender crystal (just because it has the same height)
		replacementsObjects[78] = plugin.getConfig().getInt("protocollib.entities.armorstand", 51);
		return this;
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
					if (Utilities.getProtocolVersion(event.getPlayer()) == Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
						return;
					}
					int type = event.getPacket().getIntegers().read(1);
					if (replacementsLiving[type] != -1) {
						event.getPacket().getIntegers().write(1, replacementsLiving[type]);
					}
				}
			}
		);

		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter
				.params(plugin, PacketType.Play.Server.SPAWN_ENTITY)
				.listenerPriority(ListenerPriority.HIGHEST)
			) {
				@Override
				public void onPacketSending(PacketEvent event) {
					if (Utilities.getProtocolVersion(event.getPlayer()) == Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
						return;
					}
					int type = event.getPacket().getIntegers().read(9);
					if (replacementsObjects[type] != -1) {
						event.getPacket().getIntegers().write(9, replacementsObjects[type]);
					}
				}
			}
		);

	}

}
