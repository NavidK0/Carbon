package com.lastabyss.carbon.listeners;

import java.util.Random;

import com.lastabyss.carbon.Carbon;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Navid
 */
public class ItemListener implements Listener {

    Carbon plugin;
    Random random = new Random();

    public ItemListener(Carbon plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityKilled(EntityDeathEvent event) {
        if (plugin.getConfig().getBoolean("options.sheep.dropMutton")) {
            if(event.getEntityType() == EntityType.SHEEP) {
                if(event.getEntity() instanceof Ageable) {
                    Ageable entity = (Ageable) event.getEntity();
                    if(entity.isAdult()) {
                        boolean fireaspect = false;
                        int looting = 1;
                        if(entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                            EntityDamageByEntityEvent dEvent = (EntityDamageByEntityEvent) entity.getLastDamageCause();
                            if(dEvent.getDamager() != null && dEvent.getDamager() instanceof Player) {
                                ItemStack hand = ((Player) dEvent.getDamager()).getItemInHand();
                                fireaspect = hand.containsEnchantment(Enchantment.FIRE_ASPECT);
                                if(hand.containsEnchantment(Enchantment.LOOT_BONUS_MOBS))
                                    looting += hand.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
                                if(looting < 1) //Incase a plugin sets an enchantment level to be negative
                                    looting = 1;
                            } else if(dEvent.getDamager() != null && dEvent.getDamager() instanceof Arrow) {
                                Arrow a = (Arrow) dEvent.getDamager();
                                if(a.getFireTicks() > 0)
                                    fireaspect = true;
                            }
                        }
                        if(entity.getLastDamageCause().getCause().equals(DamageCause.FIRE_TICK) || entity.getLastDamageCause().getCause().equals(DamageCause.FIRE) ||
                                entity.getLastDamageCause().getCause().equals(DamageCause.LAVA) || fireaspect)
                            event.getDrops().add(new ItemStack(Carbon.injector().cookedMuttonItemMat, random.nextInt(2) + 1 + random.nextInt(looting)));
                        else
                            event.getDrops().add(new ItemStack(Carbon.injector().muttonItemMat, random.nextInt(2) + 1 + random.nextInt(looting)));
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onCreeperDeath(EntityDeathEvent evt) {
        
    }
}
