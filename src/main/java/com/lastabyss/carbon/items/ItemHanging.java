package com.lastabyss.carbon.items;

import net.minecraft.server.v1_7_R4.Direction;
import net.minecraft.server.v1_7_R4.EntityHanging;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityPainting;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.World;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.painting.PaintingPlaceEvent;
import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlock;

import com.lastabyss.carbon.entity.EntityItemFrame;

@SuppressWarnings("deprecation")
public class ItemHanging extends net.minecraft.server.v1_7_R4.ItemHanging {

	private final Class<?> entityClass;

	public ItemHanging(Class<?> oclass) {
		super(oclass);
		this.entityClass = oclass;
	}

	public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
		if (l == 0) {
			return false;
		}
		if (l == 1) {
			return false;
		}
		int i1 = Direction.e[l];
		EntityHanging entityhanging = a(world, i, j, k, i1);
		if (!entityhuman.a(i, j, k, l, itemstack)) {
			return false;
		}
		if ((entityhanging != null) && (entityhanging.survives())) {
			if (!world.isStatic) {
				Player who = entityhuman == null ? null : (Player) entityhuman.getBukkitEntity();
				Block blockClicked = world.getWorld().getBlockAt(i, j, k);
				BlockFace blockFace = CraftBlock.notchToBlockFace(l);

				HangingPlaceEvent event = new HangingPlaceEvent((Hanging) entityhanging.getBukkitEntity(), who, blockClicked, blockFace);
				world.getServer().getPluginManager().callEvent(event);

				PaintingPlaceEvent paintingEvent = null;
				if ((entityhanging instanceof EntityPainting)) {
					paintingEvent = new PaintingPlaceEvent((Painting) entityhanging.getBukkitEntity(), who, blockClicked, blockFace);
					paintingEvent.setCancelled(event.isCancelled());
					world.getServer().getPluginManager().callEvent(paintingEvent);
				}
				if ((event.isCancelled()) || ((paintingEvent != null) && (paintingEvent.isCancelled()))) {
					return false;
				}
				world.addEntity(entityhanging);
			}
			itemstack.count -= 1;
		}
		return true;
	}

	private EntityHanging a(World world, int i, int j, int k, int l) {
		return this.entityClass == EntityItemFrame.class ? new EntityItemFrame(world, i, j, k, l) : this.entityClass == EntityPainting.class ? new EntityPainting(world, i, j, k, l) : null;
	}

}
