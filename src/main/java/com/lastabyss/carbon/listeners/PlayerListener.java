package com.lastabyss.carbon.listeners;

import com.lastabyss.carbon.Carbon;

import net.minecraft.server.v1_7_R4.EntityPlayer;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.lastabyss.carbon.inventory.AdditionalNBTDataPlayerAbilities;

import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    
    private Carbon plugin;

    public PlayerListener(Carbon plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerLogin(PlayerLoginEvent event) {
            EntityPlayer nmsPlayer = ((CraftPlayer) event.getPlayer()).getHandle();
            nmsPlayer.abilities = new AdditionalNBTDataPlayerAbilities();
    }

    @SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerInteractMobSpawner(PlayerInteractEvent evt) {
        if (plugin.getConfig().getBoolean("features.monsterEggMobSpawner", true)) {
            if (evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (evt.getPlayer().getItemInHand().getType() == Material.MONSTER_EGG && evt.getClickedBlock().getType() == Material.MOB_SPAWNER) {
                    ItemStack egg = evt.getPlayer().getItemInHand();
                    CreatureSpawner cs = (CreatureSpawner) evt.getClickedBlock().getState();
                    cs.setSpawnedType(EntityType.fromId(egg.getData().getData()));
                    cs.update(true);
                    evt.setUseItemInHand(Event.Result.DENY);
                    evt.setCancelled(true);
                }
            }
        }
    }

}
