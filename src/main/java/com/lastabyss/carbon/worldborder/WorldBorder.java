package com.lastabyss.carbon.worldborder;

import com.google.common.collect.Lists;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.ChunkCoordIntPair;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.Position;

public class WorldBorder {

	private static WorldBorder instance = new WorldBorder();
	public static WorldBorder getInstance() {
		return instance;
	}

	private final List<WorldBorderChangeListener> listeners = Lists.newArrayList();
	private double x = 0.0D;
	private double z = 0.0D;
	private double oldRadius = 6.0E7D;
	private double currentRadius;
	private long lerpEndTime;
	private long lerpStartTime;
	private int portalTeleportBoundary;
	private double damageAmount;
	private double damageBuffer;
	private int warningTime;
	private int warningBlocks;

	protected WorldBorder() {
		this.currentRadius = this.oldRadius;
		this.portalTeleportBoundary = 29999984;
		this.damageAmount = 0.2D;
		this.damageBuffer = 5.0D;
		this.warningTime = 15;
		this.warningBlocks = 5;
		listeners.add(new WorldBorderPlayerUpdater());
	}

	public boolean isInside(Position position) {
		return (double) (position.getX() + 1) > this.getMinX()
				&& (double) position.getX() < this.getMaxX()
				&& (double) (position.getZ() + 1) > this.getMinZ()
				&& (double) position.getZ() < this.getMaxZ();
	}

	public boolean isInside(Location location) {
		return (double) (location.getX() + 1) > this.getMinX()
				&& (double) location.getX() < this.getMaxX()
				&& (double) (location.getZ() + 1) > this.getMinZ()
				&& (double) location.getZ() < this.getMaxZ();
	}

	public boolean isInside(Block block) {
		return (double) (block.getX() + 1) > this.getMinX()
				&& (double) block.getX() < this.getMaxX()
				&& (double) (block.getZ() + 1) > this.getMinZ()
				&& (double) block.getZ() < this.getMaxZ();
	}

	public boolean isInside(ChunkCoordIntPair var1) {
		return (double) getBlockMaxX(var1) > this.getMinX()
				&& (double) getBlockMinX(var1) < this.getMaxX()
				&& (double) getBlockMaxZ(var1) > this.getMinZ()
				&& (double) getBlockMinZ(var1) < this.getMaxZ();
	}
	public int getBlockMinX(ChunkCoordIntPair c) {
		return c.x << 4;
	}

	public int getBlockMinZ(ChunkCoordIntPair c) {
		return c.z<< 4;
	}

	public int getBlockMaxX(ChunkCoordIntPair c) {
		return (c.x << 4) + 15;
	}

	public int getBlockMaxZ(ChunkCoordIntPair c) {
		return (c.z << 4) + 15;
	}

	public boolean isInside(AxisAlignedBB axis) {
		return axis.d > this.getMinX() && axis.a < this.getMaxX() && axis.f > this.getMinZ() && axis.c < this.getMaxZ();
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
				return this.oldRadius + (this.currentRadius - this.oldRadius)
						* var1;
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
