package com.lastabyss.carbon.listeners;

import net.minecraft.server.v1_7_R4.EntityPlayer;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.lastabyss.carbon.inventory.AdditionalNBTDataPlayerAbilities;

public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		EntityPlayer nmsPlayer = ((CraftPlayer) event.getPlayer()).getHandle();
		nmsPlayer.abilities = new AdditionalNBTDataPlayerAbilities();
	}

}
