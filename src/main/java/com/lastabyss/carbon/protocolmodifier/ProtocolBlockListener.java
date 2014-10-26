package com.lastabyss.carbon.protocolmodifier;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketEvent;
import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.utils.Utilities;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;

import java.lang.reflect.InvocationTargetException;

public class ProtocolBlockListener {

	private Carbon plugin;

	public ProtocolBlockListener(Carbon plugin) {
		this.plugin = plugin;
	}

	private int[] replacements = new int[4096];

	public ProtocolBlockListener loadRemapList() {
		for (int i = 0; i < replacements.length; i++) {
			replacements[i] = -1;
		}
		// slime -> emerald block
		replacements[165] = plugin.getConfig().getInt("protocollib.blocks.slime", 133);
		// barrier -> ? (probably not needed) (or maybe glass?)
		replacements[166] = plugin.getConfig().getInt("protocollib.blocks.barrier", 20);
		// iron trapdoor -> trapdoor
		replacements[167] = plugin.getConfig().getInt("protocollib.blocks.iron_trapdoor", 96);
		// prismarine -> mossy cobblestone
		replacements[168] = plugin.getConfig().getInt("protocollib.blocks.prismarine", 48);
		// sea lantern -> glowstone
		replacements[169] = plugin.getConfig().getInt("protocollib.blocks.sea_lantern", 89);
		// standing banner -> standing sign
		replacements[176] = plugin.getConfig().getInt("protocollib.blocks.standing_banner", 63);
		// wall banner -> wall sign
		replacements[177] = plugin.getConfig().getInt("protocollib.blocks.wall_banner", 68);
		// red sandstone -> sandstone
		replacements[179] = plugin.getConfig().getInt("protocollib.blocks.red_sandstone", 24);
		// red sandstone stairs -> sandstone stairs
		replacements[180] = plugin.getConfig().getInt("protocollib.blocks.red_sandstone_stairs", 128);
		// red sandstone doubleslab -> double step
		replacements[181] = plugin.getConfig().getInt("protocollib.blocks.red_sandstone_doubleslab", 43);
		// red sandstone slab -> step
		replacements[182] = plugin.getConfig().getInt("protocollib.blocks.red_sandstone_slab", 44);
		// all fence gates -> fence gate
		replacements[183] = plugin.getConfig().getInt("protocollib.blocks.fence_gates", 107);
		replacements[184] = plugin.getConfig().getInt("protocollib.blocks.fence_gates", 107);
		replacements[185] = plugin.getConfig().getInt("protocollib.blocks.fence_gates", 107);
		replacements[186] = plugin.getConfig().getInt("protocollib.blocks.fence_gates", 107);
		replacements[187] = plugin.getConfig().getInt("protocollib.blocks.fence_gates", 107);
		// all fences -> fence
		replacements[188] = plugin.getConfig().getInt("protocollib.blocks.fences", 85);
		replacements[189] = plugin.getConfig().getInt("protocollib.blocks.fences", 85);
		replacements[190] = plugin.getConfig().getInt("protocollib.blocks.fences", 85);
		replacements[191] = plugin.getConfig().getInt("protocollib.blocks.fences", 85);
		replacements[192] = plugin.getConfig().getInt("protocollib.blocks.fences", 85);
		// all doors -> door
		replacements[193] = plugin.getConfig().getInt("protocollib.blocks.doors", 64);
		replacements[194] = plugin.getConfig().getInt("protocollib.blocks.doors", 64);
		replacements[195] = plugin.getConfig().getInt("protocollib.blocks.doors", 64);
		replacements[196] = plugin.getConfig().getInt("protocollib.blocks.doors", 64);
		replacements[197] = plugin.getConfig().getInt("protocollib.blocks.doors", 64);
		//inverted daylight detector -> daylight detector
		replacements[178] = plugin.getConfig().getInt("protocollib.blocks.inverted_daylight_detector", 151);
		return this;
	}

	public void init() {
		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter
				.params(plugin, PacketType.Play.Server.MAP_CHUNK)
				.listenerPriority(ListenerPriority.HIGHEST)
			) {
				@Override
				public void onPacketSending(PacketEvent event) {
					if (Utilities.getProtocolVersion(event.getPlayer()) == Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
						return;
					}
					//chunk packet is split to 16 columns 16*16*16, if column doesn't have any blocks - it is not sent
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
				PacketAdapter
				.params(plugin, PacketType.Play.Server.MAP_CHUNK_BULK)
				.listenerPriority(ListenerPriority.HIGHEST)
			) {
				@Override
				public void onPacketSending(PacketEvent event) {
					if (Utilities.getProtocolVersion(event.getPlayer()) == Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
						return;
					}
					//the same as map chunk, but we have multiple chunks data store in inflatedbuffers
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

		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter
				.params(plugin, PacketType.Play.Server.BLOCK_CHANGE)
				.listenerPriority(ListenerPriority.HIGHEST)
			) {
				@Override
				public void onPacketSending(PacketEvent event) {
					if (Utilities.getProtocolVersion(event.getPlayer()) == Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
						return;
					}
					//create a new packet with modified block and send it (Had to do it because block change packets are shared)
					net.minecraft.server.v1_7_R4.Block block = event.getPacket().getSpecificModifier(net.minecraft.server.v1_7_R4.Block.class).read(0);
					int id = net.minecraft.server.v1_7_R4.Block.getId(block);
					if (replacements[id] != -1) {
						event.setCancelled(true);
						PacketContainer newpacket = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
						net.minecraft.server.v1_7_R4.Block newBlock = net.minecraft.server.v1_7_R4.Block.getById(replacements[id]);
						newpacket.getSpecificModifier(net.minecraft.server.v1_7_R4.Block.class).write(0, newBlock);
						newpacket.getIntegers().write(0, event.getPacket().getIntegers().read(0));
						newpacket.getIntegers().write(1, event.getPacket().getIntegers().read(1));
						newpacket.getIntegers().write(2, event.getPacket().getIntegers().read(2));
						newpacket.getIntegers().write(3, event.getPacket().getIntegers().read(3));
						try {
							ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), newpacket, false);
						} catch (InvocationTargetException e) {
                                                  e.printStackTrace(System.out);
						}
					}
				}
			}
		);

		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter
				.params(plugin, PacketType.Play.Server.MULTI_BLOCK_CHANGE)
				.listenerPriority(ListenerPriority.HIGHEST)
			) {
				@Override
				public void onPacketSending(PacketEvent event) {
					if (Utilities.getProtocolVersion(event.getPlayer()) == Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
						return;
					}
					//the format is: 4 bytes (1st byte - ZX (or XZ, i don't remember and that doesn't matter here), 2nd byte - Y, 3,4th bytes - id 12 bits + data 4 bits)
					byte[] bytes = event.getPacket().getByteArrays().read(0);
					for (int i = 2; i < bytes.length; i+= 4) {
						int iddata = ((bytes[i] & 0xFF) << 8) | (bytes[i + 1] & 0xFF);
						int id = iddata >> 4;
						int data = iddata & 0xF;
						if (replacements[id] != -1) {
							int newiddata = replacements[id] << 4 | data;
							bytes[i] = (byte) (newiddata >> 8);
							bytes[i + 1] = (byte) newiddata;
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
