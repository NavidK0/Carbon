package com.lastabyss.carbon.ai;

import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.PathEntity;

import com.lastabyss.carbon.utils.Utilities;

public class PathfinderGoalAttackOnCollide extends PathfinderWrapper {

	protected EntityCreature entity;
	private int animationTicks;
	private double speed;
	private boolean ignoreWalls;
	private PathEntity path;
	private Class<?> colliderClass;
	private int keepTarget;
	private double i;
	private double j;
	private double k;

	public PathfinderGoalAttackOnCollide(EntityCreature entity, Class<?> colliderClass, double speed, boolean ignoreWalls) {
		this(entity, speed, ignoreWalls);
		this.colliderClass = colliderClass;
	}

	public PathfinderGoalAttackOnCollide(EntityCreature entity, double speed, boolean ignoreWalls) {
		this.entity = entity;
		this.speed = speed;
		this.ignoreWalls = ignoreWalls;
		setMutexBits(3);
	}

	@Override
	public boolean canExecute() {
		EntityLiving target = entity.getGoalTarget();
		if (target == null) {
			return false;
		} else if (!target.isAlive()) {
			return false;
		} else if ((colliderClass != null) && !colliderClass.isAssignableFrom(target.getClass())) {
			return false;
		} else {
			path = entity.getNavigation().a(target);
			return path != null;
		}
	}

	@Override
	public boolean canContinue() {
		EntityLiving target = entity.getGoalTarget();
		return target == null ? false : (!target.isAlive() ? false : (!ignoreWalls ? !entity.getNavigation().g() : entity.b(MathHelper.floor(target.locX), MathHelper.floor(target.locY), MathHelper.floor(target.locZ))));
	}

	@Override
	public void setup() {
		entity.getNavigation().a(path, speed);
		keepTarget = 0;
	}

	@Override
	public void finish() {
		entity.getNavigation().h();
	}

	@Override
	public void execute() {
		EntityLiving target = entity.getGoalTarget();
		entity.getControllerLook().a(target, 30.0F, 30.0F);
		double distSq = Utilities.getDistanceSqTo(entity, target.locX, target.boundingBox.b, target.locZ);
		--keepTarget;
		if (
			(ignoreWalls || entity.getEntitySenses().canSee(target)) &&
			(keepTarget <= 0) &&
			(((i == 0.0D) && (j == 0.0D) && (k == 0.0D)) || (Utilities.getDistanceSqTo(target, i, j, k) >= 1.0D) || (entity.aI().nextFloat() < 0.05F))
		) {
			i = target.locX;
			j = target.boundingBox.b;
			k = target.locZ;
			keepTarget = 4 + entity.aI().nextInt(7);
			if (distSq > 1024.0D) {
				keepTarget += 10;
			} else if (distSq > 256.0D) {
				keepTarget += 5;
			}

			if (!entity.getNavigation().a(target, speed)) {
				keepTarget += 15;
			}
		}

		animationTicks--;
		if ((distSq <= getHandReachDist(target)) && (animationTicks <= 0)) {
			animationTicks = 20;
			if (entity.be() != null) {
				entity.ba();
			}

			entity.n(target);
		}

	}

	protected double getHandReachDist(EntityLiving target) {
		return (entity.width * 2.0F * entity.width * 2.0F) + target.width;
	}

}
