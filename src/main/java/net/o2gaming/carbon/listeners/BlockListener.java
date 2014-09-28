package net.o2gaming.carbon.listeners;

import net.o2gaming.carbon.Carbon;
import java.util.Random;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * This listener class is filled with dumb little hacks that couldn't be added through injection.
 * 
 * (2obfuscated4me)
 * 
 * @author Navid
 */
public class BlockListener implements Listener {

    private Carbon plugin;

    public BlockListener(Carbon plugin) {
        this.plugin = plugin;
    }
    
    //Plays a sound when a slime block is placed.
    @EventHandler(priority = EventPriority.LOWEST)
    public void onSlimeBlockPlace(BlockPlaceEvent evt) {
        if (evt.isCancelled()) return;
        if(evt.getBlock().getType() == Material.getMaterial("slime")) {
            Location location = evt.getBlock().getLocation();
            Random rand = new Random();
            boolean bool = rand.nextBoolean();
            if (bool)
                evt.getPlayer().getWorld().playSound(location, Sound.SLIME_ATTACK, 1, 10);
            else
                evt.getPlayer().getWorld().playSound(location, Sound.SLIME_ATTACK, 0.5f, 10);
        }
    }
    
    /**
     * Added by Aust1n46,
     * allows drops of coarse dirt.
     * @param event 
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onCoarseDirtBreak(BlockBreakEvent event) {
        if(event.isCancelled()) return;
        if(event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if(event.getBlock().getState().getData().toString().equals("DIRT(1)")) {
            event.getBlock().setType(Material.AIR);
            ItemStack item = new ItemStack(Material.DIRT, 1);
            item.setDurability((short) 1);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
        }
    }
    
    //Stupid hack to spawn the right type of door because doors would spawn two items otherwise.
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDoorBreak(BlockBreakEvent evt) {
        if (evt.isCancelled()) return;
        if (evt.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        switch (evt.getBlock().getType().toString()) {
            case "spruce_door":
                evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(), new ItemStack(Carbon.injector().spruceDoorMat, 1));
                break;
            case "birch_door":
                evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(), new ItemStack(Carbon.injector().birchDoorMat, 1));
                break;
            case "jungle_door":
                evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(), new ItemStack(Carbon.injector().jungleDoorMat, 1));
                break;
            case "acacia_door":
                evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(), new ItemStack(Carbon.injector().acaciaDoorMat, 1));
                break;
            case "dark_oak_door":
                evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(), new ItemStack(Carbon.injector().darkOakDoorMat, 1));
                break;
        }
    }
    
    //Negates fall damage when falling on a slime block
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSlimeBlockFall(EntityDamageEvent evt) {
        if (evt.getCause() == EntityDamageEvent.DamageCause.FALL &&
                evt.getEntity().getLocation().subtract(0, 1, 0).getBlock().getType() == Material.getMaterial("slime")) {
            evt.setCancelled(true);
        }
    }
}
