package com.lastabyss.carbon.nettyinjector;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.NetworkStatistics;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.util.com.google.common.collect.BiMap;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;

public class PacketDecoder extends net.minecraft.server.v1_7_R4.PacketDecoder {

	private static final Logger logger = LogManager.getLogger();
	private static final Marker logmarker = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.b);

	public PacketDecoder(NetworkStatistics networkstatistics) {
		super(networkstatistics);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void decode(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf, List list) throws IOException {
		int i = bytebuf.readableBytes();
		if (i != 0) {
			PacketDataSerializer packetdataserializer = new PacketDataSerializer(bytebuf, NetworkManager.getVersion(channelhandlercontext.channel()));
			int j = packetdataserializer.a();
			Packet packet = Packet.a((BiMap) channelhandlercontext.channel().attr(NetworkManager.e).get(), j);
			if (packet == null) {
				throw new IOException("Bad packet id " + j);
			}
			packet.a(packetdataserializer);
			if (packetdataserializer.readableBytes() > 0) {
				throw new IOException("Packet was larger than I expected, found " + packetdataserializer.readableBytes() + " bytes extra whilst reading packet " + j);
			}
			list.add(packet);
			if (logger.isDebugEnabled()) {
				logger.debug(logmarker, " IN: [{}:{}] {}[{}]", new Object[] { channelhandlercontext.channel().attr(NetworkManager.d).get(), Integer.valueOf(j), packet.getClass().getName(), packet.b() });
			}
		}
	}

}
