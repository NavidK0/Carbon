package com.lastabyss.carbon.commands;

import java.io.IOException;

import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;
import net.minecraft.server.v1_7_R4.PacketListener;
import net.minecraft.server.v1_7_R4.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_7_R4.PacketPlayOutListener;

public class PacketPlayOutWorldBorder extends PacketPlayOutCustomPayload {

	private WorldBorderAction action;
	private int teleportBoundary;
	private double x;
	private double z;
	private double radius;
	private double oldRadius;
	private long speed;
	private int warningTime;
	private int warningBlocks;

	public PacketPlayOutWorldBorder() {
	}

	public PacketPlayOutWorldBorder(WorldBorder worldborder,
			WorldBorderAction action) {
		this.action = action;
		this.x = worldborder.getX();
		this.z = worldborder.getZ();
		this.oldRadius = worldborder.getOldRadius();
		this.radius = worldborder.getCurrentRadius();
		this.speed = worldborder.getSpeed();
		this.teleportBoundary = worldborder.getPortalTeleportBoundary();
		this.warningBlocks = worldborder.getWarningBlocks();
		this.warningTime = worldborder.getWarningTime();
	}

	public void a(PacketDataSerializer serializer) {
		this.action = (WorldBorderAction) readEnum(serializer,
				WorldBorderAction.class);
		switch (action) {
		case SET_SIZE: {
			this.radius = serializer.readDouble();
			break;
		}
		case LERP_SIZE: {
			this.oldRadius = serializer.readDouble();
			this.radius = serializer.readDouble();
			this.speed = serializer.readLong();
			break;
		}
		case SET_CENTER: {
			this.x = serializer.readDouble();
			this.z = serializer.readDouble();
			break;
		}
		case SET_WARNING_BLOCKS: {
			this.warningBlocks = serializer.readInt();
			break;
		}
		case SET_WARNING_TIME: {
			this.warningTime = serializer.readInt();
			break;
		}
		case INITIALIZE: {
			this.x = serializer.readDouble();
			this.z = serializer.readDouble();
			this.oldRadius = serializer.readDouble();
			this.radius = serializer.readDouble();
			this.speed = serializer.readLong();
			this.teleportBoundary = serializer.readInt();
			this.warningBlocks = serializer.readInt();
			this.warningTime = serializer.readInt();
		}
		}
	}

	public <T extends Enum<T>> Enum<T> readEnum(
			PacketDataSerializer serializer, Class<T> enumClass) {
		return enumClass.getEnumConstants()[serializer.readInt()];
	}

	public void writeEnum(PacketDataSerializer serializer, Enum<?> value) {
		serializer.writeInt(value.ordinal());
	}

	public void b(PacketDataSerializer serializer) {
		writeEnum(serializer, this.action);
		switch (action) {
		case SET_SIZE: {
			serializer.writeDouble(this.radius);
			break;
		}
		case LERP_SIZE: {
			serializer.writeDouble(this.oldRadius);
			serializer.writeDouble(this.radius);
			serializer.writeLong(this.speed);
			break;
		}
		case SET_CENTER: {
			serializer.writeDouble(this.x);
			serializer.writeDouble(this.z);
			break;
		}
		case SET_WARNING_BLOCKS: {
			serializer.writeInt(this.warningBlocks);
			break;
		}
		case SET_WARNING_TIME: {
			serializer.writeInt(this.warningTime);
			break;
		}
		case INITIALIZE: {
			serializer.writeDouble(this.x);
			serializer.writeDouble(this.z);
			serializer.writeDouble(this.oldRadius);
			serializer.writeDouble(this.radius);
			serializer.writeLong(this.speed);
			serializer.writeInt(this.teleportBoundary);
			serializer.writeInt(this.warningBlocks);
			serializer.writeInt(this.warningTime);
		}
		}
	}

	public enum WorldBorderAction {
		SET_SIZE, LERP_SIZE, SET_CENTER, INITIALIZE, SET_WARNING_TIME, SET_WARNING_BLOCKS;
	}

	public void a(PacketPlayOutListener packetplayoutlistener) {
		packetplayoutlistener.a(this);
	}

	public void handle(PacketListener packetlistener) {
		this.a((PacketPlayOutListener) packetlistener);
	}

}
