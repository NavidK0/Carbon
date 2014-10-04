package com.lastabyss.carbon.listeners;

import com.lastabyss.carbon.Carbon;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * 
 * @author Navid, Aust1n46
 */
public class BlockListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onSlimeBlockPlace(BlockPlaceEvent evt) {
		if (evt.getBlock().getType() == Material.getMaterial("slime")) {
			Location location = evt.getBlock().getLocation();
			Random rand = new Random();
			boolean bool = rand.nextBoolean();
			if (bool)
				evt.getPlayer().getWorld().playSound(location, Sound.SLIME_WALK, 1.0F, 10.0F);
			else
				evt.getPlayer().getWorld().playSound(location, Sound.SLIME_WALK2, 0.5F, 10.0F);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onCoarseDirtBreak(BlockBreakEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
			return;
		if (event.getBlock().getState().getData().toString().equals("DIRT(1)")) {
			event.getBlock().setType(Material.AIR);
			ItemStack item = new ItemStack(Material.DIRT, 1);
			item.setDurability((short) 1);
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDoorBreak(BlockBreakEvent evt) {
		if (evt.getPlayer().getGameMode() == GameMode.CREATIVE)
			return;
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
		if (event.getBlock().getType().toString().contains("door"))
			return;
		switch (event.getBlock().getRelative(BlockFace.UP).getType().toString()) {
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
		if (event.getBlockPlaced().getState().getData().toString().equals("SPONGE(0)")) {
			int x = event.getBlockPlaced().getX() - 5;
			int y = event.getBlockPlaced().getY() - 5;
			int z = event.getBlockPlaced().getZ() - 5;
			for (int a = x; a <= x + 10; a++)
				for (int b = y; b <= y + 10; b++)
					for (int c = z; c <= z + 10; c++)
						if (event.getBlockPlaced().getWorld().getBlockAt(a, b, c).getType().equals(Material.WATER) ||
								(event.getBlockPlaced().getWorld().getBlockAt(a, b, c).getType().equals(Material.STATIONARY_WATER))) {
							event.getBlockPlaced().getWorld().getBlockAt(a, b, c).setType(Material.AIR);
							event.getBlockPlaced().setData((byte) 1);
						}
		}
	}

	public boolean isTouching(Block block, String material) {
		for (BlockFace b : BlockFace.values())
			if (block.getRelative(b).getType().equals(Material.getMaterial(material)))
				return true;
		return false;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void slabInteract(PlayerInteractEvent event) {
		if (event.getItem() == null || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getItem().getType() != Carbon.injector().redSandstoneSlabMat)
			return;
		Block clickedBlock = event.getClickedBlock();
		if (clickedBlock.getType() == Carbon.injector().redSandstoneSlabMat)
			if ((clickedBlock.getData() == 0 && event.getBlockFace() == BlockFace.UP) || (clickedBlock.getData() == 8 && event.getBlockFace() == BlockFace.DOWN)) {
				setDoubleSlab(event.getPlayer(), clickedBlock);
				event.setCancelled(true);
				return;
			}
		Block block = event.getClickedBlock();
		Block adjacent = block.getRelative(event.getBlockFace());
		if (adjacent.getType() == Carbon.injector().redSandstoneSlabMat) {
			setDoubleSlab(event.getPlayer(), adjacent);
			event.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	private void setDoubleSlab(Player player, Block block) {
		block.setType(Carbon.injector().redSandstoneDoubleSlabMat);
		block.setData((byte) 0);
		if (player.getGameMode() != GameMode.CREATIVE) {
			if (player.getItemInHand().getAmount() > 1)
				player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
			else
				player.setItemInHand(null);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onSlimeBlockFall(EntityDamageEvent evt) {
		if (evt.getCause().equals(DamageCause.FALL) && evt.getEntity().getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType().equals(Carbon.injector().slimeMat))
			evt.setCancelled(true);
	}
}
