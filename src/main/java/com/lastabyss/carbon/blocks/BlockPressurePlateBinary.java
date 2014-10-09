package com.lastabyss.carbon.blocks;

import java.util.Iterator;
import java.util.List;

import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.plugin.PluginManager;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EnumMobType;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.World;

public class BlockPressurePlateBinary extends BlockPressurePlateAbstract {

    private EnumMobType a;

    protected BlockPressurePlateBinary(String s, Material material, EnumMobType enummobtype) {
        super(s, material);
        this.a = enummobtype;
    }

    @Override
    protected int d(int i) {
        return i > 0 ? 1 : 0;
    }

    @Override
    protected int c(int i) {
        return i == 1 ? 15 : 0;
    }

	@Override
	protected int e(World world, int i, int j, int k) {
		List<?> list = null;
		if (this.a == EnumMobType.EVERYTHING) {
			list = world.getEntities((Entity) null, this.a(i, j, k));
		}
		if (this.a == EnumMobType.MOBS) {
			list = world.a(EntityLiving.class, this.a(i, j, k));
		}
		if (this.a == EnumMobType.PLAYERS) {
			list = world.a(EntityHuman.class, this.a(i, j, k));
		}
		if (list != null && !list.isEmpty()) {
			Iterator<?> iterator = list.iterator();
			while (iterator.hasNext()) {
				Entity entity = (Entity) iterator.next();
				if (this.c(world.getData(i, j, k)) == 0) {
					org.bukkit.World bworld = world.getWorld();
					PluginManager manager = world.getServer().getPluginManager();
					Cancellable cancellable;
					if (entity instanceof EntityHuman) {
						cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman) entity, Action.PHYSICAL, i, j, k, -1, null);
					} else {
						cancellable = new EntityInteractEvent(entity.getBukkitEntity(), bworld.getBlockAt(i, j, k));
						manager.callEvent((EntityInteractEvent) cancellable);
					}
					if (cancellable.isCancelled()) {
						continue;
					}
				}
				if (!entity.az()) {
					return 15;
				}
			}
		}
		return 0;
	}

}