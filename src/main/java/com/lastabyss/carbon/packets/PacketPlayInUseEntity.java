package com.lastabyss.carbon.packets;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.lastabyss.carbon.entity.EntityArmorStand;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EnumEntityUseAction;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;
import net.minecraft.server.v1_7_R4.PacketPlayInListener;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.server.v1_7_R4.Vec3D;
import net.minecraft.util.io.netty.buffer.Unpooled;

public class PacketPlayInUseEntity extends net.minecraft.server.v1_7_R4.PacketPlayInUseEntity {

	private float atX;
	private float atY;
	private float atZ;
	private int entityId;
	private EnumEntityUseAction action;

	@Override
	public void a(PacketDataSerializer packetdataserializer) {
		PacketDataSerializer dataForSuperClass = new PacketDataSerializer(Unpooled.buffer(), packetdataserializer.version);
		if (packetdataserializer.version < 16) {
			this.entityId = packetdataserializer.readInt();
			dataForSuperClass.writeInt(this.entityId);
			this.action = EnumEntityUseAction.values()[(packetdataserializer.readByte() % EnumEntityUseAction.values().length)];
			dataForSuperClass.writeByte(action.ordinal());
		} else {
			this.entityId = packetdataserializer.a();
			dataForSuperClass.b(this.entityId);
			int action = packetdataserializer.a();
			dataForSuperClass.b(action);
			if (action == 2) {
				this.atX = packetdataserializer.readFloat();
				dataForSuperClass.writeFloat(this.atX);
				this.atY = packetdataserializer.readFloat();
				dataForSuperClass.writeFloat(this.atY);
				this.atZ = packetdataserializer.readFloat();
				dataForSuperClass.writeFloat(this.atZ);
			} else {
				this.action = EnumEntityUseAction.values()[(action % EnumEntityUseAction.values().length)];
			}
		}
		super.a(dataForSuperClass);
	}

	@Override
	public void a(PacketPlayInListener packetplayinlistener) {
		if (action == null && packetplayinlistener instanceof PlayerConnection) {
			PlayerConnection connection = (PlayerConnection) packetplayinlistener;
			Entity entity = connection.player.world.getEntity(entityId);
			if (entity instanceof EntityArmorStand) {
				PlayerInteractEntityEvent event = new PlayerInteractEntityEvent(connection.player.getBukkitEntity(), entity.getBukkitEntity());
				Bukkit.getServer().getPluginManager().callEvent(event);
				if (!event.isCancelled()) {
					((EntityArmorStand) entity).interactAt(connection.player, Vec3D.a(atX, atY, atZ));
				}
			}
		} else {
			packetplayinlistener.a(this);
		}
	}

}
