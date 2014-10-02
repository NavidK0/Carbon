package com.lastabyss.carbon.listeners;

import com.lastabyss.carbon.Carbon;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Skull;

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
        if (plugin.getConfig().getBoolean("creeper.dropMobHead"))
            if (evt.getEntity().getLastDamageCause().getCause() == DamageCause.ENTITY_EXPLOSION) {
                if (evt.getEntity().getLastDamageCause().getEntity().getType() == EntityType.CREEPER) {
                if (!((Creeper)evt.getEntity().getLastDamageCause().getEntity()).isPowered()) {
                    return;
                }
                LivingEntity entity = evt.getEntity();
                ItemStack skullItem = null;
                switch (evt.getEntityType()) {
                    case SKELETON:
                        Skeleton skeleton = (Skeleton) evt.getEntity();
                        if (skeleton.getSkeletonType() == Skeleton.SkeletonType.NORMAL)
                            skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)0);  
                        else if (skeleton.getSkeletonType() == Skeleton.SkeletonType.WITHER)
                            skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)1); 
                        break;
                    case ZOMBIE:
                        skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)2); 
                        break;
                    case PLAYER:
                        Player p = (Player) entity;
                        skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                        SkullMeta meta = (SkullMeta) skullItem.getItemMeta();
                        meta.setOwner(p.getName());
                        meta.setDisplayName(p.getName() + "'s Head");
                        skullItem.setItemMeta(meta);
                        break;
                    case CREEPER:
                        skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)4); 
                        break;
                }
                if (skullItem != null)
                    entity.getWorld().dropItemNaturally(entity.getLocation(), skullItem);
                }
            }
    }
}
