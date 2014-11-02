package com.lastabyss.carbon.nettyinjector;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.utils.Utilities;

import net.minecraft.server.v1_7_R4.ChatComponentText;
import net.minecraft.server.v1_7_R4.EnumProtocol;
import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.NetworkStatistics;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;
import net.minecraft.server.v1_7_R4.PacketHandshakingInSetProtocol;
import net.minecraft.server.v1_7_R4.PacketLoginOutDisconnect;
import net.minecraft.util.com.google.common.collect.BiMap;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;

public class PacketDecoder extends net.minecraft.server.v1_7_R4.PacketDecoder {

	private static final Logger logger = LogManager.getLogger();
	private static final Marker logmarker = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.b);

	private static HashMap<Integer, String> restrictedProtocols = new HashMap<Integer, String>();
	public static void loadConfig(Carbon plugin) {
		File configFile = new File(plugin.getDataFolder(), "protocolblocker.yml");
		if (!configFile.exists()) {
			try {
				YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
				config.set("4", "You cannot log on this server with version 1.7.9! Please upgrade to 1.8!");
				config.set("5", "You cannot log on this server with version 1.7.10! Please upgrade to 1.8!");
				config.save(configFile);
				config = YamlConfiguration.loadConfiguration(configFile);
			} catch (IOException e) {
				e.printStackTrace(System.out);
			}
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		restrictedProtocols.clear();
		for (String version : config.getKeys(false)) {
			try {
				restrictedProtocols.put(Integer.parseInt(version), ChatColor.translateAlternateColorCodes('&', config.getString(version)));
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
	}

	public PacketDecoder(NetworkStatistics networkstatistics) {
		super(networkstatistics);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void decode(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf, List list) throws IOException {
		int i = bytebuf.readableBytes();
		if (i != 0) {
			PacketDataSerializer packetdataserializer = new PacketDataSerializer(bytebuf, NetworkManager.getVersion(channelhandlercontext.channel()));
			int packetId = packetdataserializer.a();
			Packet packet = Packet.a((BiMap) channelhandlercontext.channel().attr(NetworkManager.e).get(), packetId);
			if (packet == null) {
				throw new IOException("Bad packet id " + packetId);
			}
			packet.a(packetdataserializer);
			if (packetdataserializer.readableBytes() > 0) {
				throw new IOException("Packet was larger than I expected, found " + packetdataserializer.readableBytes() + " bytes extra whilst reading packet " + packetId);
			}
			if (packet instanceof PacketHandshakingInSetProtocol) {
				try {
					int version = Utilities.setAccessible(Field.class, packet.getClass().getDeclaredField("a"), true).getInt(packet);
					EnumProtocol nextprotocol = (EnumProtocol) Utilities.setAccessible(Field.class, packet.getClass().getDeclaredField("d"), true).get(packet);
					if (restrictedProtocols.containsKey(version) && nextprotocol == EnumProtocol.LOGIN) {
						//setup channel attributes
						channelhandlercontext.channel().attr(NetworkManager.d).set(EnumProtocol.LOGIN);
						channelhandlercontext.channel().attr(NetworkManager.e).set(EnumProtocol.LOGIN.a());
						channelhandlercontext.channel().attr(NetworkManager.f).set(EnumProtocol.LOGIN.b());
						//send packetloginoutdisconnect
						PacketLoginOutDisconnect disconnectPacket = new PacketLoginOutDisconnect(new ChatComponentText(restrictedProtocols.get(version)));
						channelhandlercontext.channel().writeAndFlush(disconnectPacket);
						//disconnect channel
						channelhandlercontext.close();
					}
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace(System.out);
				}
			}
			list.add(packet);
			if (logger.isDebugEnabled()) {
				logger.debug(logmarker, " IN: [{}:{}] {}[{}]", new Object[] { channelhandlercontext.channel().attr(NetworkManager.d).get(), Integer.valueOf(packetId), packet.getClass().getName(), packet.b() });
			}
		}
	}

}
