package com.lastabyss.carbon.listeners;

import com.lastabyss.carbon.Carbon;
import org.bukkit.GameMode;
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
	public void onCoarseDirtBreak(BlockBreakEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
			return;
		if (event.getBlock().getState().getData().toString().equals("DIRT(1)")) {
			event.getBlock().setType(Material.AIR);
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.DIRT, 1, (short) 1));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDoorBreak(BlockBreakEvent evt) {
		if (evt.getPlayer().getGameMode() == GameMode.CREATIVE)
			return;
        String value = evt.getBlock().getType().toString();
			if (value.equalsIgnoreCase("spruce_door"))
				evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(), new ItemStack(Carbon.injector().spruceDoorMat, 1));
        else if (value.equalsIgnoreCase("birch_door"))
				evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(), new ItemStack(Carbon.injector().birchDoorMat, 1));
            else if (value.equalsIgnoreCase("jungle_door"))
				evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(), new ItemStack(Carbon.injector().jungleDoorMat, 1));
            else if (value.equalsIgnoreCase("acacia_door"))
				evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(), new ItemStack(Carbon.injector().acaciaDoorMat, 1));
            else if (value.equalsIgnoreCase("dark_oak_door"))
				evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(), new ItemStack(Carbon.injector().darkOakDoorMat, 1));
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onIndirectDoorBreak(BlockBreakEvent event) {
		if (event.getBlock().getType().toString().contains("door"))
			return;
		String value = event.getBlock().getRelative(BlockFace.UP).getType().toString();
        if (value.equalsIgnoreCase("spruce_door"))
				event.getBlock().getWorld().dropItemNaturally(event.getBlock().getRelative(BlockFace.UP).getLocation(), new ItemStack(Carbon.injector().spruceDoorMat, 1));
        else if (value.equalsIgnoreCase("birch_door"))
				event.getBlock().getWorld().dropItemNaturally(event.getBlock().getRelative(BlockFace.UP).getLocation(), new ItemStack(Carbon.injector().birchDoorMat, 1));
        else if (value.equalsIgnoreCase("jungle_door"))
				event.getBlock().getWorld().dropItemNaturally(event.getBlock().getRelative(BlockFace.UP).getLocation(), new ItemStack(Carbon.injector().jungleDoorMat, 1));
        else if (value.equalsIgnoreCase("acacia_door"))
				event.getBlock().getWorld().dropItemNaturally(event.getBlock().getRelative(BlockFace.UP).getLocation(), new ItemStack(Carbon.injector().acaciaDoorMat, 1));
        else if (value.equalsIgnoreCase("dark_oak_door"))
				event.getBlock().getWorld().dropItemNaturally(event.getBlock().getRelative(BlockFace.UP).getLocation(), new ItemStack(Carbon.injector().darkOakDoorMat, 1));
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onSpongePlace(BlockPlaceEvent event) {
		if(event.getBlock().getState().getData().toString().equals("SPONGE(0)") && (isTouching(event.getBlock(), Material.WATER) || isTouching(event.getBlock(), Material.STATIONARY_WATER))) {
			int count = 0;
			for(int i = 1; i < 8; i++)
				for(int x = -i; x <= i; x++)
					for(int y = -i; y <= i; y++)
						for(int z = -i; z <= i; z++) {
							int a = event.getBlock().getX() + x;
							int b = event.getBlock().getY() + y;
							int c = event.getBlock().getZ() + z;
							if(event.getBlock().getWorld().getBlockAt(a, b, c).getType().equals(Material.WATER) || (event.getBlock().getWorld().getBlockAt(a, b, c).getType().equals(Material.STATIONARY_WATER))) {
								event.getBlock().getWorld().getBlockAt(a, b, c).setTypeId(0, false);//Ignores physics
								event.getBlock().setData((byte) 1);
								count++;
								if(count >= 65)
									return;
							}
						}
		}
	}

	public boolean isTouching(Block block, Material material) {
		for(BlockFace b : BlockFace.values())
			if(block.getRelative(b).getType() == material) return true;
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
		Block adjacent = clickedBlock.getRelative(event.getBlockFace());
		if (adjacent.getType() == Carbon.injector().redSandstoneSlabMat) {
			setDoubleSlab(event.getPlayer(), adjacent);
			event.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	private void setDoubleSlab(Player player, Block block) {
		block.setType(Carbon.injector().redSandstoneDoubleSlabMat);
		block.setData((byte) 0);
		block.getWorld().playSound(block.getLocation(), Sound.DIG_STONE, 1, 1);
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