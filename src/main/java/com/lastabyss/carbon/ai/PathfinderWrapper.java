package com.lastabyss.carbon.ai;

import net.minecraft.server.v1_7_R4.PathfinderGoal;

/**
 *
 * @author Navid
 */
public abstract class PathfinderWrapper extends PathfinderGoal {

	@Override
	public boolean a() {
		return canExecute();
	}

	@Override
	public boolean b() {
		return canContinue();
	}

	@Override
	public void c() {
		setup();
	}

	@Override
	public void d() {
		finish();
	}

	@Override
	public void e() {
		execute();
	}

	// Pathfinding logic is in order

	public abstract boolean canExecute();

	public void setup() {
	}

	public void execute() {
	}

	public boolean canContinue() {
		return canExecute();
	}

	public void finish() {
	}

	public int getMutexBits() {
		return j();
	}

	public void setMutexBits(int i) {
		a(i);
	}

}
