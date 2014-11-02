package com.lastabyss.carbon.optimizations.world;

import net.minecraft.server.v1_7_R4.WorldServer;

import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class WorldTileEntityListInjectorListener implements Listener {

	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		WorldServer nmsWorld = ((CraftWorld) event.getWorld()).getHandle();
		nmsWorld.tileEntityList = new OptimizedTileEntityList();
	}

}
