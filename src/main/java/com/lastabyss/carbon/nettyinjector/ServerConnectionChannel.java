package com.lastabyss.carbon.nettyinjector;

import java.util.List;

import net.minecraft.server.v1_7_R4.HandshakeListener;
import net.minecraft.server.v1_7_R4.LegacyPingHandler;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.PacketPrepender;
import net.minecraft.server.v1_7_R4.PacketSplitter;
import net.minecraft.server.v1_7_R4.ServerConnection;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelException;
import net.minecraft.util.io.netty.channel.ChannelInitializer;
import net.minecraft.util.io.netty.channel.ChannelOption;
import net.minecraft.util.io.netty.handler.timeout.ReadTimeoutHandler;

public class ServerConnectionChannel extends ChannelInitializer<Channel> {

	private ServerConnection connection;
	private List<NetworkManager> networkManagers;

	public ServerConnectionChannel(ServerConnection connection, List<NetworkManager> networkManagers) {
		this.connection = connection;
		this.networkManagers = networkManagers;
	}

	@Override
	protected void initChannel(Channel channel) {
		try {
			channel.config().setOption(ChannelOption.IP_TOS, Integer.valueOf(24));
		} catch (ChannelException channelexception) {
		}
		try {
			channel.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(false));
		} catch (ChannelException channelexception1) {
		}
		channel.pipeline()
		.addLast("timeout", new ReadTimeoutHandler(30))
		.addLast("legacy_query", new LegacyPingHandler(connection))
		.addLast("splitter", new PacketSplitter())
		.addLast("decoder", new PacketDecoder(NetworkManager.h))
		.addLast("prepender", new PacketPrepender())
		.addLast("encoder", new PacketEncoder(NetworkManager.h));
		NetworkManager networkmanager = new NetworkManager(false);

		networkManagers.add(networkmanager);
		channel.pipeline().addLast("packet_handler", networkmanager);
		networkmanager.a(new HandshakeListener(MinecraftServer.getServer(), networkmanager));
	}

}
