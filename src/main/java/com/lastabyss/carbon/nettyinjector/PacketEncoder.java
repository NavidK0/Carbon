package com.lastabyss.carbon.nettyinjector;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.lastabyss.carbon.utils.Utilities;

import net.minecraft.server.v1_7_R4.EnumProtocol;
import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.NetworkStatistics;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;
import net.minecraft.server.v1_7_R4.PacketStatusOutServerInfo;
import net.minecraft.server.v1_7_R4.ServerPing;
import net.minecraft.server.v1_7_R4.ServerPingServerData;
import net.minecraft.util.com.google.common.collect.BiMap;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;

public class PacketEncoder extends net.minecraft.server.v1_7_R4.PacketEncoder {

	private static final Logger logger = LogManager.getLogger();
	private static final Marker logmarker = MarkerManager.getMarker("PACKET_SENT", NetworkManager.b);

	public PacketEncoder(NetworkStatistics networkstatistics) {
		super(networkstatistics);
	}

	private boolean[] newpackets = new boolean[256];
	{
		for (int i = 0x41; i < 0x49; i++) {
			newpackets[i] = true;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void encode(ChannelHandlerContext channelhandlercontext, Object object, ByteBuf bytebuf) throws IOException {
		Packet packet = (Packet) object;
		Integer packetid = ((BiMap<Integer, Class<? extends Packet>>) channelhandlercontext.channel().attr(NetworkManager.f).get()).inverse().get(packet.getClass());
		if (logger.isDebugEnabled()) {
			logger.debug(logmarker, "OUT: [{}:{}] {}[{}]", channelhandlercontext.channel().attr(NetworkManager.d).get(), packetid, packet.getClass().getName(), packet.b());
		}
		if (packetid == null) {
			throw new IOException("Can't serialize unregistered packet");
		}
		//skip new packets for 1.7 client
		int version = NetworkManager.getVersion(channelhandlercontext.channel());
		if (version != Utilities.CLIENT_1_8_PROTOCOL_VERSION && channelhandlercontext.channel().attr(NetworkManager.d).get() == EnumProtocol.PLAY && newpackets[packetid.intValue()]) {
			return;
		}

		PacketDataSerializer packetdataserializer = new PacketDataSerializer(bytebuf, version);
		if (BlockedProtocols.is17Blocked() && packet instanceof PacketStatusOutServerInfo) {
			try {
				ServerPing serverPing = (ServerPing) Utilities.setAccessible(Field.class, PacketStatusOutServerInfo.class.getDeclaredField("b"), true).get(packet);
				serverPing.setServerInfo(new ServerPingServerData(serverPing.c().a(), 47));
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace(System.out);
			}
		}
		packetdataserializer.b(packetid.intValue());
		packet.b(packetdataserializer);
		//don't use packet statistic because it is a waste of resources
	}

}
