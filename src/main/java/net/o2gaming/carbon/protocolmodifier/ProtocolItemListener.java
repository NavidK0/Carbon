package net.o2gaming.carbon.protocolmodifier;

import net.o2gaming.carbon.Carbon;

import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

public class ProtocolItemListener {

	private Carbon plugin;

	public ProtocolItemListener(Carbon plugin) {
		this.plugin = plugin;
	}

	private int[] replacements = new int[4096];
	{
		for (int i = 0; i < replacements.length; i++) {
			replacements[i] = -1;
		}
		//TODO: all materials
		//slime -> emerald block
		replacements[165] = 133;
		//barrier -> ? (this is totally needed here to avoid client crash, the question is should we use glass or bedrock) (stone as of now)
		replacements[166] = 1;
		//iron trapdoor -> trapdoor
		replacements[167] = 96;
		//prismarine -> mossy cobblestone
		replacements[168] = 48;
		//sea lantern -> glowstone
		replacements[169] = 89;
		//red sandstone -> sandstone
		replacements[179] = 24;
		//red sandstone stairs -> sandstone stairs
		replacements[180] = 128;
		//red sandstone doubleslab -> double step
		replacements[181] = 43;
		//red sandstone slab -> step
		replacements[182] = 44;
		//all fence gates -> fence gate
		replacements[183] = 107;
		replacements[184] = 107;
		replacements[185] = 107;
		replacements[186] = 107;
		replacements[187] = 107;
		//all fences -> fence
		replacements[188] = 85;
		replacements[189] = 85;
		replacements[190] = 85;
		replacements[191] = 85;
		replacements[192] = 85;
		//all doors -> door
		replacements[427] = 64;
		replacements[428] = 64;
		replacements[429] = 64;
		replacements[430] = 64;
		replacements[431] = 64;
		//everything else -> stone as of now
		replacements[409] = 1;
		replacements[410] = 1;
		replacements[411] = 1;
		replacements[412] = 1;
		replacements[413] = 1;
		replacements[414] = 1;
		replacements[415] = 1;
		replacements[416] = 1;
		replacements[423] = 1;
		replacements[424] = 1;
	}

	public void init() {
		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter.params(plugin, PacketType.Play.Server.WINDOW_ITEMS)
			) {
				@SuppressWarnings("deprecation")
				@Override
				public void onPacketSending(PacketEvent event) {
					if (ProtocolLibrary.getProtocolManager().getProtocolVersion(event.getPlayer()) == 47) {
						return;
					}
					//replace all items with valid ones
					ItemStack[] items = event.getPacket().getItemArrayModifier().read(0);
					for (int i = 0; i < items.length; i++) {
						if (items[i] == null) {
							continue;
						}
						int itemid = items[i].getTypeId();
						if (replacements[itemid] != -1) {
							items[i].setTypeId(replacements[itemid]);
						}
					}
				}
			}
		);

		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter.params(plugin, PacketType.Play.Server.SET_SLOT)
			) {
				@SuppressWarnings("deprecation")
				@Override
				public void onPacketSending(PacketEvent event) {
					if (ProtocolLibrary.getProtocolManager().getProtocolVersion(event.getPlayer()) == 47) {
						return;
					}
					//replace item with valid one
					ItemStack item = event.getPacket().getItemModifier().read(0);
					if (item == null) {
						return;
					}
					int itemid = item.getTypeId();
					if (replacements[itemid] != -1) {
						item.setTypeId(replacements[itemid]);
					}
				}
			}
		);

		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter.params(plugin, PacketType.Play.Server.ENTITY_EQUIPMENT)
			) {
				@SuppressWarnings("deprecation")
				@Override
				public void onPacketSending(PacketEvent event) {
					if (ProtocolLibrary.getProtocolManager().getProtocolVersion(event.getPlayer()) == 47) {
						return;
					}
					//replace item valid one
					ItemStack item = event.getPacket().getItemModifier().read(0);
					if (item == null) {
						return;
					}
					int itemid = item.getTypeId();
					if (replacements[itemid] != -1) {
						item.setTypeId(replacements[itemid]);
					}
				}
			}
		);
	}

}
