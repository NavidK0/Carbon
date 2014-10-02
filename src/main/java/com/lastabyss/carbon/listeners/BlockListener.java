package com.lastabyss.carbon.listeners;

import com.lastabyss.carbon.Carbon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.PistonBaseMaterial;

/**
 * 
 * @author Navid, Aust1n46
 */
public class BlockListener implements Listener {
  private Carbon plugin;
  
  public BlockListener(Carbon plugin) {
    this.plugin = plugin;
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled = true)
  public void onSlimeBlockPlace(BlockPlaceEvent evt) {
    if (evt.getBlock().getType() == Material.getMaterial("slime")) {
      Location location = evt.getBlock().getLocation();
      Random rand = new Random();
      boolean bool = rand.nextBoolean();
      if (bool) {
        evt.getPlayer().getWorld().playSound(location, Sound.SLIME_WALK, 1.0F, 10.0F);
      } else {
        evt.getPlayer().getWorld().playSound(location, Sound.SLIME_WALK2, 0.5F, 10.0F);
      }
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled = true)
  public void onCoarseDirtBreak(BlockBreakEvent event) {
    if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
    if (event.getBlock().getState().getData().toString().equals("DIRT(1)")) {
      event.getBlock().setType(Material.AIR);
      ItemStack item = new ItemStack(Material.DIRT, 1);
      item.setDurability((short)1);
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled = true)
  public void onDoorBreak(BlockBreakEvent evt) {
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
    }
  }
  
@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onIndirectDoorBreak(BlockBreakEvent event) {
        if(event.getBlock().getType().toString().contains("door")) return;
        switch(event.getBlock().getRelative(BlockFace.UP).getType().toString()) {
        case "spruce_door":
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getRelative(BlockFace.UP).getLocation(), new ItemStack(Carbon.injector().spruceDoorMat, 1));
            break;
        case "birch_door":
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getRelative(BlockFace.UP).getLocation(), new ItemStack(Carbon.injector().birchDoorMat, 1));
            break;
        case "jungle_door":
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getRelative(BlockFace.UP).getLocation(), new ItemStack(Carbon.injector().jungleDoorMat, 1));
            break;
        case "acacia_door":
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getRelative(BlockFace.UP).getLocation(), new ItemStack(Carbon.injector().acaciaDoorMat, 1));
            break;
        case "dark_oak_door":
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getRelative(BlockFace.UP).getLocation(), new ItemStack(Carbon.injector().darkOakDoorMat, 1));
            break;
        }
    }
    
    @SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onSpongePlace(BlockPlaceEvent event) {
		if(event.getBlockPlaced().getState().getData().toString().equals("SPONGE(0)")) {
			int x = event.getBlockPlaced().getX() - 5;
			int y = event.getBlockPlaced().getY() - 5;
			int z = event.getBlockPlaced().getZ() - 5;			
			for(int a = x; a <= x + 10; a ++) {
				for(int b = y; b <= y + 10; b ++) {
					for(int c = z; c <= z + 10; c ++) {						
						if(event.getBlockPlaced().getWorld().getBlockAt(a, b, c).getType() == Material.WATER || (event.getBlockPlaced().getWorld().getBlockAt(a, b, c).getType() == Material.STATIONARY_WATER)) {
							event.getBlockPlaced().getWorld().getBlockAt(a, b, c).setType(Material.AIR);
							event.getBlockPlaced().setData((byte) 1);
						}
					}
				}
			}
		}
	}
  
   @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void slabInteract(PlayerInteractEvent event) {
	    if (event.getItem() == null) {
	    	return;
	    }
        if(event.getItem().getType().toString().equals("red_sandstone_slab")) {
            if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(event.getClickedBlock().getState().getData().toString().equals("red_sandstone_slab(0)")) {
                    if(event.getBlockFace().toString().equals("UP")) {
                        event.getClickedBlock().setType(Carbon.injector().redSandstoneDoubleSlabMat);
                        if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                            event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
                        }
                        event.setCancelled(true);
                    }
                }
                if(event.getClickedBlock().getState().getData().toString().equals("red_sandstone_slab(8)")) {
                    if(event.getBlockFace().toString().equals("DOWN")) {
                        event.getClickedBlock().setType(Carbon.injector().redSandstoneDoubleSlabMat);
                        event.getClickedBlock().setData((byte) 0);
                        if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                            event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
                        }
                        event.setCancelled(true);
                    }
                }          
                Block block = event.getClickedBlock();
                switch(event.getBlockFace().toString()) {
                case "NORTH": {             	
                    Block adjacent = block.getWorld().getBlockAt(block.getX(), block.getY(), block.getZ() - 1);                    
                    if(adjacent.getType().toString().equals("red_sandstone_slab")) {
                        adjacent.setType(Carbon.injector().redSandstoneDoubleSlabMat);
                        adjacent.setData((byte) 0);
                        if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                            event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
                        }
                        event.setCancelled(true);
                    }
                    break;
                }
                case "SOUTH": {
                    Block adjacent = block.getWorld().getBlockAt(block.getX(), block.getY(), block.getZ() + 1);
                    if(adjacent.getType().toString().equals("red_sandstone_slab")) {
                        adjacent.setType(Carbon.injector().redSandstoneDoubleSlabMat);
                        adjacent.setData((byte) 0);
                        if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                            event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
                        }
                        event.setCancelled(true);
                    }
                    break;
                }
                case "WEST": {
                    Block adjacent = block.getWorld().getBlockAt(block.getX() - 1, block.getY(), block.getZ());
                    if(adjacent.getType().toString().equals("red_sandstone_slab")) {
                        adjacent.setType(Carbon.injector().redSandstoneDoubleSlabMat);
                        adjacent.setData((byte) 0);
                        if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                            event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
                        }
                        event.setCancelled(true);
                    }
                    break;
                }
                case "EAST": {
                    Block adjacent = block.getWorld().getBlockAt(block.getX() + 1, block.getY(), block.getZ());
                    if(adjacent.getType().toString().equals("red_sandstone_slab")) {
                        adjacent.setType(Carbon.injector().redSandstoneDoubleSlabMat);
                        adjacent.setData((byte) 0);
                        if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                            event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
                        }
                        event.setCancelled(true);
                    }
                    break;
                }
                case "DOWN": {
                    Block adjacent = block.getWorld().getBlockAt(block.getX(), block.getY() - 1, block.getZ());
                    if(adjacent.getType().toString().equals("red_sandstone_slab")) {
                        adjacent.setType(Carbon.injector().redSandstoneDoubleSlabMat);
                        adjacent.setData((byte) 0);
                        if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                            event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
                        }
                        event.setCancelled(true);
                    }
                    break;
                }
                case "UP": {
                    Block adjacent = block.getWorld().getBlockAt(block.getX(), block.getY() + 1, block.getZ());
                    if(adjacent.getType().toString().equals("red_sandstone_slab")) {
                        adjacent.setType(Carbon.injector().redSandstoneDoubleSlabMat);
                        adjacent.setData((byte) 0);
                        if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                            event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
                        }
                        event.setCancelled(true);
                    }
                    break;
                }
                }
            }
        }
    }
  
    
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
  public void onSlimeBlockFall(EntityDamageEvent evt) {
    if ((evt.getCause() == EntityDamageEvent.DamageCause.FALL) && 
      (evt.getEntity().getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() == Material.getMaterial("slime"))) {
      evt.setCancelled(true);
    }
  }
  
}
