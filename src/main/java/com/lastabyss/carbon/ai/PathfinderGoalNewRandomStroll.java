package com.lastabyss.carbon.ai;

import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.Vec3D;

public class PathfinderGoalNewRandomStroll extends PathfinderWrapper {

	private EntityCreature entityCreature;
	private double x;
	private double y;
	private double z;
	private double speed;
	private int f;
	private boolean g;

	public PathfinderGoalNewRandomStroll(EntityCreature creature, double speed) {
		this(creature, speed, 120);
	}

	public PathfinderGoalNewRandomStroll(EntityCreature creature, double speed, int d2) {
		this.entityCreature = creature;
		this.speed = speed;
		this.f = d2;
	}

	@Override
	public boolean canExecute() {
		if (!g) {
			if (entityCreature.aN() >= 100) {
				return false;
			}

			if (entityCreature.aI().nextInt(f) != 0) {
				return false;
			}
		}

		Vec3D vec = NewRandomPositionGenerator.a(entityCreature, 10, 7);
		if (vec == null) {
			return false;
		} else {
			x = vec.a;
			y = vec.b;
			z = vec.c;
			g = false;
			return true;
		}
	}

	@Override
	public boolean canContinue() {
		return !entityCreature.getNavigation().g();
	}

	@Override
	public void setup() {
		entityCreature.getNavigation().a(x, y, z, speed);
	}

	public void force() {
		g = true;
	}

}
