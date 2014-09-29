package net.o2gaming.carbon.protocolmodifier;

import net.o2gaming.carbon.Carbon;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

public class ChunkDataListener {

	private Carbon plugin;

	public ChunkDataListener(Carbon plugin) {
		this.plugin = plugin;
	}

	private int[] replacements = new int[4096];
	{
		for (int i = 0; i < replacements.length; i++) {
			replacements[i] = -1;
		}
		//slime -> emerald block
		replacements[165] = (byte) 133;
	}

	public void init() {
		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter.params(plugin, PacketType.Play.Server.MAP_CHUNK)
			) {
				@Override
				public void onPacketSending(PacketEvent event) {
					if (ProtocolLibrary.getProtocolManager().getProtocolVersion(event.getPlayer()) == 47) {
						return;
					}
					int blocksNumber = 4096 * getChunkSectionNumber(event.getPacket().getIntegers().read(2));
					byte[] data = event.getPacket().getByteArrays().read(1);
					for (int i = 0; i < blocksNumber; i++) {
						int id = data[i] & 0xFF;
						if (replacements[id] != -1) {
							data[i] = (byte) replacements[id];
						}
					}
				}
			}
		);

		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter.params(plugin, PacketType.Play.Server.MAP_CHUNK_BULK)
			) {
				@Override
				public void onPacketSending(PacketEvent event) {
					if (ProtocolLibrary.getProtocolManager().getProtocolVersion(event.getPlayer()) == 47) {
						return;
					}
					byte[][] inflatedBuffers = event.getPacket().getSpecificModifier(byte[][].class).read(0);
					int[] chunkSectionsData = event.getPacket().getIntegerArrays().read(2);
					for (int chunkNumber = 0; chunkNumber < inflatedBuffers.length; chunkNumber++) {
						int blocksNumber = 4096 * getChunkSectionNumber(chunkSectionsData[chunkNumber]);
						byte[] data =  inflatedBuffers[chunkNumber];
						for (int i = 0; i < blocksNumber; i++) {
							int id = data[i] & 0xFF;
							if (replacements[id] != -1) {
								data[i] = (byte) replacements[id];
							}
						}
					}
				}
			}
		);
	}

	private int getChunkSectionNumber(int bitfield) {
		int count = 0;
		for (int i = 0; i < 16; i++) {
			if ((bitfield & (1 << i)) != 0) {
				count++;
			}
		}
		return count;
	}

}
