package net.o2gaming.carbon.listeners;

import java.util.Random;

import net.o2gaming.carbon.Carbon;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ageable;
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
                        if(entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                            EntityDamageByEntityEvent dEvent = (EntityDamageByEntityEvent) entity.getLastDamageCause();
                            if(dEvent.getDamager() != null && dEvent.getDamager() instanceof Player) {
                                ItemStack hand = ((Player) dEvent.getDamager()).getItemInHand();
                                fireaspect = hand.containsEnchantment(Enchantment.FIRE_ASPECT);
                            }
                        }
                        if(entity.getLastDamageCause().getCause().equals(DamageCause.FIRE_TICK) || entity.getLastDamageCause().getCause().equals(DamageCause.FIRE) ||
                                entity.getLastDamageCause().getCause().equals(DamageCause.LAVA) || fireaspect)
                            event.getDrops().add(new ItemStack(Carbon.injector().cookedMuttonItemMat, random.nextInt(1) + 1));
                        else
                            event.getDrops().add(new ItemStack(Carbon.injector().muttonItemMat, random.nextInt(1) + 1));
                    }
                }
            }
        }
    }
}
