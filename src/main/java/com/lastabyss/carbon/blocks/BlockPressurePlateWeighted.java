package com.lastabyss.carbon.blocks;

import java.util.Iterator;

import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.World;

public class BlockPressurePlateWeighted extends BlockPressurePlateAbstract {

    private final int a;

    protected BlockPressurePlateWeighted(String s, Material material, int i) {
        super(s, material);
        this.a = i;
    }

	protected int e(World world, int i, int j, int k) {
		int l = 0;
		Iterator<?> iterator = world.a(Entity.class, this.a(i, j, k)).iterator();
		while (iterator.hasNext()) {
			Entity entity = (Entity) iterator.next();
			Cancellable cancellable;
			if (entity instanceof EntityHuman) {
				cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman) entity, Action.PHYSICAL, i, j, k, -1, null);
			} else {
				cancellable = new EntityInteractEvent(entity.getBukkitEntity(), world.getWorld().getBlockAt(i, j, k));
				world.getServer().getPluginManager().callEvent((EntityInteractEvent) cancellable);
			}
			if (!cancellable.isCancelled()) {
				l++;
			}
		}
		l = Math.min(l, this.a);
		if (l <= 0) {
			return 0;
		}
		float f = (float) Math.min(this.a, l) / (float) this.a;
		return MathHelper.f(f * 15.0F);
	}

    protected int c(int i) {
        return i;
    }

    protected int d(int i) {
        return i;
    }

    public int a(World world) {
        return 10;
    }
}