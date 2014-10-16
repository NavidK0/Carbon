package com.lastabyss.carbon.nettyinjector;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.NetworkStatistics;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutUpdateTime;
import net.minecraft.util.com.google.common.collect.BiMap;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;

public class PacketEncoder extends net.minecraft.server.v1_7_R4.PacketEncoder {

	private static final Logger logger = LogManager.getLogger();
	private static final Marker logmarker = MarkerManager.getMarker("PACKET_SENT", NetworkManager.b);

	public PacketEncoder(NetworkStatistics networkstatistics) {
		super(networkstatistics);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void encode(ChannelHandlerContext channelhandlercontext, Object object, ByteBuf bytebuf) throws IOException {
		Packet packet = (Packet) object;
		//clamp packet play out time to not exceed integer
		if (packet instanceof PacketPlayOutUpdateTime) {
			try {
				Field fullTimeField = packet.getClass().getDeclaredField("a");
				fullTimeField.setAccessible(true);
				long fullTime = fullTimeField.getLong(packet);
				if (fullTime > Integer.MAX_VALUE) {
					fullTime = fullTime | ((24000L * 89400L) - 1L);
					fullTimeField.set(packet, fullTimeField);
				}
				Field dayTimeField = packet.getClass().getDeclaredField("b");
				dayTimeField.setAccessible(true);
				long dayTime = dayTimeField.getLong(packet);
				if (dayTime > 24000L) {
					dayTime = dayTime | (24000L - 1);
					dayTimeField.set(packet, dayTimeField);
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			}
		}
		Integer packetid = ((BiMap<Integer, Class<? extends Packet>>) channelhandlercontext.channel().attr(NetworkManager.f).get()).inverse().get(packet.getClass());
		if (logger.isDebugEnabled()) {
			logger.debug(logmarker, "OUT: [{}:{}] {}[{}]", channelhandlercontext.channel().attr(NetworkManager.d).get(), packetid, packet.getClass().getName(), packet.b());
		}
		if (packetid == null) {
			throw new IOException("Can't serialize unregistered packet");
		}
		PacketDataSerializer packetdataserializer = new PacketDataSerializer(bytebuf, NetworkManager.getVersion(channelhandlercontext.channel()));

		packetdataserializer.b(packetid.intValue());
		packet.b(packetdataserializer);
		//don't use packet statistic because it is a waste of resources
	}

}
