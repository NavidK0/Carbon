package com.lastabyss.carbon.entity.bukkit;

import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftMonster;
import org.bukkit.entity.EntityType;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.entity.EntityEndermite;

/**
 * Apparently nothing goes in these.
 * @author Navid
 */
public class Endermite extends CraftMonster {

	public Endermite(CraftServer server, EntityEndermite entity) {
		super(server, entity);
	}

	@Override
	public EntityType getType() {
		return Carbon.injector().endermiteEntity;
	}

}
