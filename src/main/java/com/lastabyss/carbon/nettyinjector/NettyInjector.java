package com.lastabyss.carbon.nettyinjector;

import java.lang.reflect.Field;
import java.util.List;

import com.lastabyss.carbon.utils.Utilities;

import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.ServerConnection;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelFuture;
import net.minecraft.util.io.netty.channel.ChannelHandler;

public class NettyInjector {

	@SuppressWarnings("unchecked")
	public static void injectStreamSerializer() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		ServerConnection serverConnection = MinecraftServer.getServer().getServerConnection();
		List<NetworkManager> networkManagersList = ((List<NetworkManager>) Utilities.setAccessible(Field.class, serverConnection.getClass().getDeclaredField("f"), true).get(serverConnection));
		Channel channel = (Channel) ((List<ChannelFuture>) Utilities.setAccessible(Field.class, serverConnection.getClass().getDeclaredField("e"), true).get(serverConnection)).get(0).channel();
		ChannelHandler serverHandler = channel.pipeline().first();
		Utilities.setAccessible(Field.class, serverHandler.getClass().getDeclaredField("childHandler"), true).set(serverHandler, new ServerConnectionChannel(serverConnection, networkManagersList));
	}

}
