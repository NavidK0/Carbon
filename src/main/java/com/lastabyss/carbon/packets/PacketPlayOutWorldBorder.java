package com.lastabyss.carbon.packets;

import com.lastabyss.carbon.worldborder.WorldBorder;

import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;
import net.minecraft.server.v1_7_R4.PacketListener;

import java.io.IOException;

public class PacketPlayOutWorldBorder extends Packet {

	private WorldBorderAction action;
	private int teleportBoundary;
	private double x;
	private double z;
	private double radius;
	private double oldRadius;
	private long speed;
	private int warningTime;
	private int warningBlocks;

	public PacketPlayOutWorldBorder(WorldBorder worldborder, WorldBorderAction action) {
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

	public void writeEnum(PacketDataSerializer serializer, Enum<?> value) {
		writeVarInt(serializer, value.ordinal());
	}

	public void writeVarInt(PacketDataSerializer serializer, int i) {
		while ((i & -128) != 0) {
			serializer.writeByte(i & 127 | 128);
			i >>>= 7;
		}
		serializer.writeByte(i);
	}

	public void writeVarLong(PacketDataSerializer serializer, long l) {
		while ((l & -128L) != 0L) {
			serializer.writeByte((int) (l & 127L) | 128);
			l >>>= 7;
		}
		serializer.writeByte((int) l);
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
				writeVarLong(serializer, this.speed);
				break;
			}
			case SET_CENTER: {
				serializer.writeDouble(this.x);
				serializer.writeDouble(this.z);
				break;
			}
			case SET_WARNING_BLOCKS: {
				writeVarInt(serializer, this.warningBlocks);
				break;
			}
			case SET_WARNING_TIME: {
				writeVarInt(serializer, this.warningTime);
				break;
			}
			case INITIALIZE: {
				serializer.writeDouble(this.x);
				serializer.writeDouble(this.z);
				serializer.writeDouble(this.oldRadius);
				serializer.writeDouble(this.radius);
				writeVarLong(serializer, this.speed);
				writeVarInt(serializer, this.teleportBoundary);
				writeVarInt(serializer, this.warningBlocks);
				writeVarInt(serializer, this.warningTime);
			}
		}
	}

	public enum WorldBorderAction {
		SET_SIZE, LERP_SIZE, SET_CENTER, INITIALIZE, SET_WARNING_TIME, SET_WARNING_BLOCKS
	}

	@Override
	public void a(PacketDataSerializer serializer) throws IOException {
	}

	@Override
	public void handle(PacketListener listener) {
	}

}
