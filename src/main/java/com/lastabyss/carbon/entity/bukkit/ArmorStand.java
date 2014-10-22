package com.lastabyss.carbon.entity.bukkit;

import net.minecraft.server.v1_7_R4.EntityLiving;

import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.entity.EntityType;

import com.lastabyss.carbon.Carbon;

public class ArmorStand extends CraftLivingEntity {

	public ArmorStand(CraftServer server, EntityLiving entity) {
		super(server, entity);
	}

	@Override
	public EntityType getType() {
		return Carbon.injector().armorStandEntity;
	}

}
