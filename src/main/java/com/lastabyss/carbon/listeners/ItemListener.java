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
    public void onEntityKilled(EntityDeathEvent e) {
        if (plugin.getConfig().getBoolean("options.sheep.dropMutton", true)) {
            if(e.getEntityType().equals(EntityType.SHEEP)) {
                if(e.getEntity() instanceof Ageable) {
                    Ageable entity = (Ageable) e.getEntity();
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
                            e.getDrops().add(new ItemStack(Carbon.injector().cookedMuttonItemMat, random.nextInt(2) + 1 + random.nextInt(looting)));
                        else
                            e.getDrops().add(new ItemStack(Carbon.injector().muttonItemMat, random.nextInt(2) + 1 + random.nextInt(looting)));
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onCreeperDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        if (plugin.getConfig().getBoolean("options.creeper.dropMobHead", true))
            if (entity.getLastDamageCause().getCause().equals(DamageCause.ENTITY_EXPLOSION) &&
                    entity.getLastDamageCause() instanceof EntityDamageByEntityEvent &&
                    ((EntityDamageByEntityEvent) entity.getLastDamageCause()).getDamager() != null &&
                    ((EntityDamageByEntityEvent) entity.getLastDamageCause()).getDamager() instanceof Creeper &&
                    ((Creeper)((EntityDamageByEntityEvent) entity.getLastDamageCause()).getDamager()).isPowered()){
                ItemStack skullItem = null;
                if (entity.getType().equals(EntityType.SKELETON)) {
                    Skeleton skeleton = (Skeleton) e.getEntity();
                    if (skeleton.getSkeletonType() == Skeleton.SkeletonType.NORMAL)
                        skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)0);  
                    else if (skeleton.getSkeletonType() == Skeleton.SkeletonType.WITHER)
                        skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)1);
                } else if (entity.getType().equals(EntityType.ZOMBIE))
                    skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)2); 
                else if (entity.getType().equals(EntityType.PLAYER)) {
                    Player p = (Player) entity;
                    skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                    SkullMeta meta = (SkullMeta) skullItem.getItemMeta();
                    meta.setOwner(p.getName());
                    meta.setDisplayName(p.getName() + "'s Head");
                    skullItem.setItemMeta(meta);
                } else if (entity.getType().equals(EntityType.CREEPER))
                    skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)4); 
                if (skullItem != null)
                    entity.getWorld().dropItemNaturally(entity.getLocation(), skullItem);
            }
    }
}
