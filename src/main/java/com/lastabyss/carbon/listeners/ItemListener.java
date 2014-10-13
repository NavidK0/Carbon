package com.lastabyss.carbon.listeners;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.entity.EntityEndermite;

import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
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
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 *
 * @author Navid
 */
public class ItemListener implements Listener {

    private Carbon plugin;
    private Random random = new Random();

    public ItemListener(Carbon plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTeleport(PlayerTeleportEvent e) {
        if (e.getCause() == TeleportCause.ENDER_PEARL && plugin.getConfig().getBoolean("mobSpawning.endermites", true)
                && e.getTo().getWorld().getAllowMonsters()) {
            Random rand = new Random();
            if(rand.nextInt(100) < 5) {//5% probability of spawning
            	EntityEndermite ender = new EntityEndermite(((CraftWorld) e.getFrom().getWorld()).getHandle());
                ender.spawn(e.getFrom());
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityKilled(EntityDeathEvent e) {
        if (e.getEntity().getType() != EntityType.SHEEP || !plugin.getConfig().getBoolean("options.sheep.dropMutton", true)
                || !e.getEntity().getWorld().isGameRule("doMobLoot") || !(e.getEntity() instanceof Ageable)) {
            return;
        }
        Ageable entity = (Ageable) e.getEntity();
        if (!entity.isAdult()) {
            return;
        }
        boolean fireaspect = false;
        int looting = 1;
        if (entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent dEvent = (EntityDamageByEntityEvent) entity.getLastDamageCause();
            if (dEvent.getDamager() != null && dEvent.getDamager() instanceof Player) {
                ItemStack hand = ((Player) dEvent.getDamager()).getItemInHand();
                fireaspect = hand.containsEnchantment(Enchantment.FIRE_ASPECT);
                if (hand.containsEnchantment(Enchantment.LOOT_BONUS_MOBS))
                    looting += hand.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
                if (looting < 1) //Incase a plugin sets an enchantment level to be negative
                    looting = 1;
            } else if (dEvent.getDamager() != null && dEvent.getDamager() instanceof Arrow) {
                Arrow a = (Arrow) dEvent.getDamager();
                if (a.getFireTicks() > 0)
                    fireaspect = true;
            }
        }
        if (entity.getLastDamageCause().getCause() == DamageCause.FIRE_TICK || entity.getLastDamageCause().getCause() == DamageCause.FIRE
                || entity.getLastDamageCause().getCause() == DamageCause.LAVA || fireaspect)
            e.getDrops().add(new ItemStack(Carbon.injector().cookedMuttonItemMat, random.nextInt(2) + 1 + random.nextInt(looting)));
        else
            e.getDrops().add(new ItemStack(Carbon.injector().muttonItemMat, random.nextInt(2) + 1 + random.nextInt(looting)));
    }

    HashSet<UUID> recentCreepers = new HashSet<UUID>();

    public void runCreepersResetTak() {
    	Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
    		@Override
    		public void run() {
    			recentCreepers.clear();
    		}
    	}, 0, 1);
    }

    @EventHandler
    public void onCreeperDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        if (entity.getLastDamageCause() == null || entity.getLastDamageCause().getCause() == null
                || entity.getLastDamageCause().getCause() != DamageCause.ENTITY_EXPLOSION
                || !plugin.getConfig().getBoolean("options.creeper.dropMobHead", true)
                || !entity.getWorld().isGameRule("doMobLoot")
                || !(entity.getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) entity.getLastDamageCause();
        if (damageEvent.getDamager() == null || damageEvent.getDamager().getType() != EntityType.CREEPER
                || !((Creeper) damageEvent.getDamager()).isPowered()
                || recentCreepers.contains(damageEvent.getDamager().getUniqueId())) return;
        ItemStack skullItem = null;
        if (entity.getType() == EntityType.SKELETON) {
            Skeleton skeleton = (Skeleton) e.getEntity();
            if (skeleton.getSkeletonType() == Skeleton.SkeletonType.NORMAL)
                skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)0);  
            else if (skeleton.getSkeletonType() == Skeleton.SkeletonType.WITHER)
                skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)1);
        } else if (entity.getType() == EntityType.ZOMBIE)
            skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)2); 
        else if (entity.getType() == EntityType.PLAYER) {
            Player p = (Player) entity;
            skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
            SkullMeta meta = (SkullMeta) skullItem.getItemMeta();
            meta.setOwner(p.getName());
            meta.setDisplayName(p.getName() + "'s Head");
            skullItem.setItemMeta(meta);
        } else if (entity.getType() == EntityType.CREEPER)
            skullItem = new ItemStack(Material.SKULL_ITEM, 1, (short)4); 
        if (skullItem != null) {
            final UUID uuid = damageEvent.getDamager().getUniqueId();
            recentCreepers.add(uuid); // Temporarily track UUID to prevent another head drop
            entity.getWorld().dropItemNaturally(entity.getLocation(), skullItem);
        }
    }

}
