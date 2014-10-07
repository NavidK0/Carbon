/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lastabyss.carbon.entity.bukkit;

import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftAnimals;

import com.lastabyss.carbon.entity.EntityRabbit;

/**
 *
 * @author Navid
 */
public class Rabbit extends CraftAnimals {

	public Rabbit(CraftServer server, EntityRabbit entity) {
		super(server, entity);
	}

}
