package com.lastabyss.carbon.worldborder;

import com.google.common.collect.Lists;

import net.minecraft.server.v1_7_R4.Entity;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.WeakHashMap;

public class WorldBorder {

	private static WeakHashMap<World, WorldBorder> worldsWorldBorder = new WeakHashMap<World, WorldBorder>();
	public static WorldBorder getInstance(World world) {
		if (!worldsWorldBorder.containsKey(world)) {
			WorldBorder newWorldBorder = new WorldBorder(world);
			worldsWorldBorder.put(world, newWorldBorder);
			YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(world.getWorldFolder(), "worldborder.yml"));
			newWorldBorder.setCenter(config.getDouble("x", newWorldBorder.x), config.getDouble("z", newWorldBorder.z));
			newWorldBorder.setDamageAmount(config.getDouble("damageAmount", newWorldBorder.damageAmount));
			newWorldBorder.setDamageBuffer(config.getDouble("damageBuffer", newWorldBorder.damageBuffer));
			newWorldBorder.setWarningBlocks(config.getInt("warningBlocks", newWorldBorder.warningBlocks));
			newWorldBorder.setWarningTime(config.getInt("warningTime", newWorldBorder.warningTime));
			long lerpTime = config.getLong("lerpTime", 0);
			double oldSize = config.getDouble("oldRadius", newWorldBorder.oldRadius);
			if (lerpTime > 0L) {
				newWorldBorder.changeSize(oldSize, config.getDouble("currentRadius", oldSize), lerpTime);
			} else {
				newWorldBorder.setSize(oldSize);
			}
		}
		return worldsWorldBorder.get(world);
	}

	public static void save() {
		for (Entry<World, WorldBorder> entry : worldsWorldBorder.entrySet()) {
			WorldBorder worldborder = entry.getValue();
			YamlConfiguration config = new YamlConfiguration();
			config.set("x", worldborder.x);
			config.set("z", worldborder.z);
			config.set("damageAmount", worldborder.damageAmount);
			config.set("damageBuffer", worldborder.damageBuffer);
			config.set("warningBlocks", worldborder.warningBlocks);
			config.set("warningTime", worldborder.warningTime);
			if (worldborder.getStatus() != EnumWorldBorderStatus.STATIONARY) {
				config.set("lerpTime", worldborder.lerpEndTime - worldborder.lerpStartTime);
				config.set("currentRadius", worldborder.currentRadius);
			}
			config.set("oldRadius", worldborder.oldRadius);
			try {
				config.save(new File(entry.getKey().getWorldFolder(), "worldborder.yml"));
			} catch (IOException e) {
                          e.printStackTrace(System.out);
			}
		}
	}

	private final List<WorldBorderChangeListener> listeners = Lists.newArrayList();
	private double x = 0.0D;
	private double z = 0.0D;
	private double oldRadius = 6.0E7D;
	private double currentRadius = oldRadius;
	private long lerpEndTime;
	private long lerpStartTime;
	private int portalTeleportBoundary = 29999984;
	private double damageAmount = 0.2D;
	private double damageBuffer = 5.0D;
	private int warningTime = 15;
	private int warningBlocks = 5;

	protected WorldBorder(World world) {
		listeners.add(new WorldBorderPlayerUpdater(world));
	}

	public boolean isInside(Location location) {
		return location.getX() + 1 > this.getMinX()
			&& location.getX() - 1 < this.getMaxX()
			&& location.getZ() + 1 > this.getMinZ()
			&& location.getZ() - 1 < this.getMaxZ();
	}

	public boolean isInside(Block block) {
		return (double) (block.getX() + 1) > this.getMinX()
				&& (double) block.getX() - 1 < this.getMaxX()
				&& (double) (block.getZ() + 1) > this.getMinZ()
				&& (double) block.getZ() - 1 < this.getMaxZ();
	}

	public double getDistance(Entity entity) {
		return this.getDistance(entity.locX, entity.locZ);
	}

	public double getDistance(double locX, double locZ) {
		double distMinZ = locZ - this.getMinZ();
		double distMaxZ = this.getMaxZ() - locZ;
		double distMinX = locX - this.getMinX();
		double distMaxX = this.getMaxX() - locX;
		double dist = Math.min(distMinX, distMaxX);
		dist = Math.min(dist, distMinZ);
		return Math.min(dist, distMaxZ);
	}

	public EnumWorldBorderStatus getStatus() {
		return this.currentRadius < this.oldRadius ? EnumWorldBorderStatus.SHRINKING : (this.currentRadius > this.oldRadius ? EnumWorldBorderStatus.GROWING : EnumWorldBorderStatus.STATIONARY);
	}

	public double getMinX() {
		double x = this.getX() - this.getOldRadius() / 2.0D;
		if (x < (double) (-this.portalTeleportBoundary)) {
			x = (double) (-this.portalTeleportBoundary);
		}

		return x;
	}

	public double getMinZ() {
		double var1 = this.getZ() - this.getOldRadius() / 2.0D;
		if (var1 < (double) (-this.portalTeleportBoundary)) {
			var1 = (double) (-this.portalTeleportBoundary);
		}

		return var1;
	}

	public double getMaxX() {
		double x = this.getX() + this.getOldRadius() / 2.0D;
		if (x > (double) this.portalTeleportBoundary) {
			x = (double) this.portalTeleportBoundary;
		}

		return x;
	}

	public double getMaxZ() {
		double z = this.getZ() + this.getOldRadius() / 2.0D;
		if (z > (double) this.portalTeleportBoundary) {
			z = (double) this.portalTeleportBoundary;
		}

		return z;
	}

	public double getX() {
		return this.x;
	}

	public double getZ() {
		return this.z;
	}

	public void setCenter(double x, double z) {
		this.x = x;
		this.z = z;

		for (WorldBorderChangeListener listener : this.getChangeListeners()) {
			listener.onSetCenter(this, x, z);
		}
	}

	public double getOldRadius() {
		if (this.getStatus() != EnumWorldBorderStatus.STATIONARY) {
			double var1 = (double) ((float) (System.currentTimeMillis() - this.lerpStartTime) / (float) (this.lerpEndTime - this.lerpStartTime));
			if (var1 < 1.0D) {
				return this.oldRadius + (this.currentRadius - this.oldRadius) * var1;
			}

			this.setSize(this.currentRadius);
		}

		return this.oldRadius;
	}

	public long getSpeed() {
		return this.getStatus() != EnumWorldBorderStatus.STATIONARY ? this.lerpEndTime
				- System.currentTimeMillis()
				: 0L;
	}

	public double getCurrentRadius() {
		return this.currentRadius;
	}

	public void setSize(double size) {
		this.oldRadius = size;
		this.currentRadius = size;
		this.lerpEndTime = System.currentTimeMillis();
		this.lerpStartTime = this.lerpEndTime;

		for (WorldBorderChangeListener listener : this.getChangeListeners()) {
			listener.onSizeSet(this, size);
		}
	}

	public void changeSize(double oldRadius, double currentRadius, long time) {
		this.oldRadius = oldRadius;
		this.currentRadius = currentRadius;
		this.lerpStartTime = System.currentTimeMillis();
		this.lerpEndTime = this.lerpStartTime + time;

		for (WorldBorderChangeListener listener : this.getChangeListeners()) {
			listener.onSizeChange(this, oldRadius, currentRadius, time);
		}
	}

	protected List<WorldBorderChangeListener> getChangeListeners() {
		return Lists.newArrayList(this.listeners);
	}

	public void addChangeListener(WorldBorderChangeListener listener) {
		this.listeners.add(listener);
	}

	public void setPortalTeleportBoundary(int portalTeleportBoundary) {
		this.portalTeleportBoundary = portalTeleportBoundary;
	}

	public int getPortalTeleportBoundary() {
		return this.portalTeleportBoundary;
	}

	public double getDamageBuffer() {
		return this.damageBuffer;
	}

	public void setDamageBuffer(double buffer) {
		this.damageBuffer = buffer;

		for (WorldBorderChangeListener listener : this.getChangeListeners()) {
			listener.onSetDamageBuffer(this, buffer);
		}
	}

	public double getDamageAmount() {
		return this.damageAmount;
	}

	public void setDamageAmount(double damage) {
		this.damageAmount = damage;

		for (WorldBorderChangeListener listener : this.getChangeListeners()) {
			listener.onSetDamageAmount(this, damage);
		}
	}

	public int getWarningTime() {
		return this.warningTime;
	}

	public void setWarningTime(int time) {
		this.warningTime = time;

		for (WorldBorderChangeListener listener : this.getChangeListeners()) {
			listener.onSetWarningTime(this, time);
		}
	}

	public int getWarningBlocks() {
		return this.warningBlocks;
	}

	public void setWarningBlocks(int blocks) {
		this.warningBlocks = blocks;

		for (WorldBorderChangeListener listener : this.getChangeListeners()) {
			listener.onSetWarningBlocks(this, blocks);
		}
	}

}
