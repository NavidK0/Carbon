package com.lastabyss.carbon.protocolmodifier;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.entity.ArmorStandPose;
import com.lastabyss.carbon.inventory.EnchantingContainer;
import com.lastabyss.carbon.utils.Utilities;

import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;
import net.minecraft.server.v1_7_R4.WatchableObject;
import net.minecraft.util.io.netty.buffer.Unpooled;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

public class ProtocolItemListener {

	private Carbon plugin;

	public ProtocolItemListener(Carbon plugin) {
		this.plugin = plugin;
	}

	private int[] replacements = new int[4096];

	public ProtocolItemListener loadRemapList() {
		for (int i = 0; i < replacements.length; i++) {
			replacements[i] = -1;
		}
		// slime -> emerald block
		replacements[165] = plugin.getConfig().getInt("protocollib.items.slime", 133);
		// barrier -> bedrock
		replacements[166] = plugin.getConfig().getInt("protocollib.items.barrier", 7);
		// iron trapdoor -> trapdoor
		replacements[167] = plugin.getConfig().getInt("protocollib.items.iron_trapdoor", 96);
		// prismarine -> mossy cobblestone
		replacements[168] = plugin.getConfig().getInt("protocollib.items.prismarine", 48);
		// sea lantern -> glowstone
		replacements[169] = plugin.getConfig().getInt("protocollib.items.sea_lantern", 89);
		// red sandstone -> sandstone
		replacements[179] = plugin.getConfig().getInt("protocollib.items.red_sandstone", 24);
		// red sandstone stairs -> sandstone stairs
		replacements[180] = plugin.getConfig().getInt("protocollib.items.red_sandstone_stairs", 128);
		// red sandstone doubleslab -> double step
		replacements[181] = plugin.getConfig().getInt("protocollib.items.red_sandstone_doubleslab", 43);
		// red sandstone slab -> step
		replacements[182] = plugin.getConfig().getInt("protocollib.items.red_sandstone_slab", 44);
		// all fence gates -> fence gate
		replacements[183] = plugin.getConfig().getInt("protocollib.items.fence_gates", 107);
		replacements[184] = plugin.getConfig().getInt("protocollib.items.fence_gates", 107);
		replacements[185] = plugin.getConfig().getInt("protocollib.items.fence_gates", 107);
		replacements[186] = plugin.getConfig().getInt("protocollib.items.fence_gates", 107);
		replacements[187] = plugin.getConfig().getInt("protocollib.items.fence_gates", 107);
		// all fences -> fence
		replacements[188] = plugin.getConfig().getInt("protocollib.items.fences", 85);
		replacements[189] = plugin.getConfig().getInt("protocollib.items.fences", 85);
		replacements[190] = plugin.getConfig().getInt("protocollib.items.fences", 85);
		replacements[191] = plugin.getConfig().getInt("protocollib.items.fences", 85);
		replacements[192] = plugin.getConfig().getInt("protocollib.items.fences", 85);
		// all doors -> door
		replacements[427] = plugin.getConfig().getInt("protocollib.items.doors", 324);
		replacements[428] = plugin.getConfig().getInt("protocollib.items.doors", 324);
		replacements[429] = plugin.getConfig().getInt("protocollib.items.doors", 324);
		replacements[430] = plugin.getConfig().getInt("protocollib.items.doors", 324);
		replacements[431] = plugin.getConfig().getInt("protocollib.items.doors", 324);
		// rabbit raw meat -> chicken raw meat
		replacements[411] = plugin.getConfig().getInt("protocollib.items.rabbit_meat", 365);
		// rabbit cooked meat -> chicken cooked meat
		replacements[412] = plugin.getConfig().getInt("protocollib.items.rabbit_cooked_meat", 366);
		// rabbit stew -> mushroom stew
		replacements[413] = plugin.getConfig().getInt("protocollib.items.rabbit_stew", 282);
		// raw mutton -> chicken raw meat
		replacements[423] = plugin.getConfig().getInt("protocollib.items.mutton", 365);
		// cooked mutton -> chicken cooked meat
		replacements[424] = plugin.getConfig().getInt("protocollib.items.cooked_mutton", 366);
		// banner -> sign
		replacements[425] = plugin.getConfig().getInt("protocollib.items.banner", 323);
		// everything else -> stone
		replacements[409] = plugin.getConfig().getInt("protocollib.items.prismarine_shard", 1);
		replacements[410] = plugin.getConfig().getInt("protocollib.items.prismarine_crystals", 1);
		replacements[414] = plugin.getConfig().getInt("protocollib.items.rabbit_foot", 1);
		replacements[415] = plugin.getConfig().getInt("protocollib.items.rabbit_hide", 1);
		replacements[416] = plugin.getConfig().getInt("protocollib.items.armor_stand", 1);
		return this;
	}

	@SuppressWarnings("deprecation")
	private void replaceItemStack(ItemStack itemStack) {
		if (itemStack == null) {
			return;
		}
		int itemid = itemStack.getTypeId();
		if (replacements[itemid] != -1) {
			itemStack.setTypeId(replacements[itemid]);
		}
	}

	private void replaceItemStack(net.minecraft.server.v1_7_R4.ItemStack itemStack) {
		if (itemStack == null) {
			return;
		}
		int itemid = Item.getId(itemStack.getItem());
		if (replacements[itemid] != -1) {
			itemStack.setItem(Item.getById(replacements[itemid]));
		}
	}

	public void init() {
		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter
				.params(plugin, PacketType.Play.Server.WINDOW_ITEMS)
				.listenerPriority(ListenerPriority.HIGHEST)
			) {
				@Override
				public void onPacketSending(PacketEvent event) {
					if (Utilities.getProtocolVersion(event.getPlayer()) == Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
						return;
					}
					// remove lapis slot item in enchant table for old clients
					if (((CraftPlayer) event.getPlayer()).getHandle().activeContainer instanceof EnchantingContainer) {
						ItemStack[] olditems = event.getPacket().getItemArrayModifier().read(0);
						ItemStack[] newitems = new ItemStack[olditems.length - 1];
						newitems[0] = olditems[0];
						System.arraycopy(olditems, 2, newitems, 1, newitems.length - 1);
						event.getPacket().getItemArrayModifier().write(0, newitems);
					}
					// replace all items with valid ones
					ItemStack[] items = event.getPacket().getItemArrayModifier().read(0);
					for (int i = 0; i < items.length; i++) {
						replaceItemStack(items[i]);
					}
				}
			}
		);

		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter
				.params(plugin, PacketType.Play.Server.SET_SLOT)
				.listenerPriority(ListenerPriority.HIGHEST)
			) {
				@Override
				public void onPacketSending(PacketEvent event) {
					if (Utilities.getProtocolVersion(event.getPlayer()) == Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
						return;
					}
					// cancel any lapis set slot packet
					if (((CraftPlayer) event.getPlayer()).getHandle().activeContainer instanceof EnchantingContainer) {
						int slot = event.getPacket().getIntegers().read(1);
						if (slot == 1) {
							event.setCancelled(true);
						} else if (slot > 1) {
							slot--;
							event.getPacket().getIntegers().write(1, slot);
						}
					}
					// replace item with valid one
					ItemStack item = event.getPacket().getItemModifier().read(0);
					replaceItemStack(item);
				}
			}
		);

		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter
				.params(plugin, PacketType.Play.Server.ENTITY_EQUIPMENT)
				.listenerPriority(ListenerPriority.HIGHEST)
			) {
				@Override
				public void onPacketSending(PacketEvent event) {
					if (Utilities.getProtocolVersion(event.getPlayer()) == Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
						return;
					}
					// replace item valid one
					ItemStack item = event.getPacket().getItemModifier().read(0);
					replaceItemStack(item);
				}
			}
		);

		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter
				.params(plugin, PacketType.Play.Server.ENTITY_METADATA)
				.listenerPriority(ListenerPriority.HIGHEST)
			) {
				@Override
				public void onPacketSending(PacketEvent event) {
					if (Utilities.getProtocolVersion(event.getPlayer()) == Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
						return;
					}
					// create a new packet with valid items and send it, also remove armor stand pose data, because it is unknown  (Had to do this because metadata packets are shared)
					event.setCancelled(true);
					PacketContainer newpacket = event.getPacket().deepClone();
					List<?> list = newpacket.getSpecificModifier(List.class).read(0);
					Iterator<?> iterator = list.iterator();
					while (iterator.hasNext()) {
						WatchableObject wobject = (WatchableObject) iterator.next();
						if (wobject.b() instanceof net.minecraft.server.v1_7_R4.ItemStack) {
							net.minecraft.server.v1_7_R4.ItemStack itemStack = (net.minecraft.server.v1_7_R4.ItemStack) wobject.b();
							replaceItemStack(itemStack);
						} else if (wobject.b() instanceof ArmorStandPose) {
							iterator.remove();
						}
					}
					try {
						ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), newpacket, false);
					} catch (InvocationTargetException e) {
						e.printStackTrace(System.out);
					}
				}
			}
		);

		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter
				.params(plugin, PacketType.Play.Server.CUSTOM_PAYLOAD)
				.listenerPriority(ListenerPriority.HIGHEST)
			) {
				@Override
				public void onPacketSending(PacketEvent event) {
					// server sends some sort of payload packet on player join so this check should be first
					if (!event.getPacket().getStrings().read(0).equals("MC|TrList")) {
						return;
					}
					if (Utilities.getProtocolVersion(event.getPlayer()) == Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
						return;
					}
					// repack trade list packet with valid items
					byte[] data = event.getPacket().getByteArrays().read(0);
					PacketDataSerializer dataserializer = new PacketDataSerializer(Unpooled.wrappedBuffer(data));
					PacketDataSerializer newdataserializer = new PacketDataSerializer(Unpooled.buffer(data.length));
					try {
						newdataserializer.writeInt(dataserializer.readInt());
						int count = dataserializer.readByte() & 0xFF;
						newdataserializer.writeByte(count);
						for (int i = 0; i < count; i++) {
							net.minecraft.server.v1_7_R4.ItemStack buyItem1 = dataserializer.c();
							replaceItemStack(buyItem1);
							newdataserializer.a(buyItem1);
	
							net.minecraft.server.v1_7_R4.ItemStack buyItem3 = dataserializer.c();
							replaceItemStack(buyItem3);
							newdataserializer.a(buyItem3);
	
							boolean hasItem = dataserializer.readBoolean();
							newdataserializer.writeBoolean(hasItem);
							if (hasItem) {
								net.minecraft.server.v1_7_R4.ItemStack buyItem2 = dataserializer.c();
								replaceItemStack(buyItem2);
								newdataserializer.a(buyItem2);
							}
	
							newdataserializer.writeBoolean(dataserializer.readBoolean());
						}
						event.getPacket().getByteArrays().write(0, newdataserializer.array());
					} catch (Exception e) {
						e.printStackTrace(System.out);
					}
				}
			}
		);

		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
				PacketAdapter
				.params(plugin, PacketType.Play.Client.WINDOW_CLICK)
				.listenerPriority(ListenerPriority.HIGHEST)
			) {
				@Override
				public void onPacketReceiving(PacketEvent event) {
					if (Utilities.getProtocolVersion(event.getPlayer()) == Utilities.CLIENT_1_8_PROTOCOL_VERSION) {
						return;
					}
					//fix clicked slot in enchanting inventory
					if (((CraftPlayer) event.getPlayer()).getHandle().activeContainer instanceof EnchantingContainer) {
						int clickedslot = event.getPacket().getIntegers().read(1);
						if (clickedslot > 0) {
							clickedslot++;
						}
						event.getPacket().getIntegers().write(1, clickedslot);
					}
				}
			}
		);

	}

}
