package com.lastabyss.carbon.ai;

import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.RandomPositionGenerator;
import net.minecraft.server.v1_7_R4.Vec3D;

public class PathfinderGoalNewRandomStroll extends PathfinderWrapper {

	private EntityCreature entityCreature;
	private double b;
	private double c;
	private double d;
	private double e;
	private int f;
	private boolean g;

	public PathfinderGoalNewRandomStroll(EntityCreature creature, double var2) {
		this(creature, var2, 120);
	}

	public PathfinderGoalNewRandomStroll(EntityCreature creature, double d1, int d2) {
		entityCreature = creature;
		e = d1;
		f = d2;
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

		Vec3D vec = RandomPositionGenerator.a(entityCreature, 10, 7);
		if (vec == null) {
			return false;
		} else {
			b = vec.a;
			c = vec.b;
			d = vec.c;
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
		entityCreature.getNavigation().a(b, c, d, e);
	}

	public void f() {
		g = true;
	}

	public void b(int var1) {
		f = var1;
	}

}
