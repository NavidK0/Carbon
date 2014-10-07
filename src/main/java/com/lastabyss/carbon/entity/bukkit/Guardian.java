/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lastabyss.carbon.entity.bukkit;

import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftMonster;
import org.bukkit.entity.EntityType;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.entity.EntityGuardian;

/**
 *
 * @author Navid
 */
public class Guardian extends CraftMonster {

	public Guardian(CraftServer server, EntityGuardian entity) {
		super(server, entity);
	}

	@Override
	public EntityType getType() {
		return Carbon.injector().guardianEntity;
	}

}
