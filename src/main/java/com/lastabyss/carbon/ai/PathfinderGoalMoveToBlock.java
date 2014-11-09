package com.lastabyss.carbon.ai;

import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.World;

import com.lastabyss.carbon.utils.nmsclasses.Position;

public abstract class PathfinderGoalMoveToBlock extends PathfinderWrapper {

	private final EntityCreature creature;
	private final double speed;
	protected int cooldown;
	private int timeSpent;
	private int maxTimeSpent;
	protected Position pos;
	private boolean reachedTarget;
	private int scanRadius;

	public PathfinderGoalMoveToBlock(EntityCreature creature, double navigationFloatValue, int speed) {
		this.pos = Position.ZERO;
		this.creature = creature;
		this.speed = navigationFloatValue;
		this.scanRadius = speed;
		setMutexBits(5);
	}

	@Override
	public boolean canExecute() {
		if (cooldown > 0) {
			--cooldown;
			return false;
		} else {
			cooldown = 200 + creature.aI().nextInt(200);
			return searchTargetBlock();
		}
	}

	@Override
	public boolean canContinue() {
		return (timeSpent >= -maxTimeSpent) && (timeSpent <= 1200) && this.checkBlock(creature.world, pos);
	}

	@Override
	public void setup() {
		creature.getNavigation().a((pos.getX()) + 0.5D, pos.getY() + 1, (pos.getZ()) + 0.5D, speed);
		timeSpent = 0;
		maxTimeSpent = creature.aI().nextInt(creature.aI().nextInt(1200) + 1200) + 1200;
	}

	@Override
	public void finish() {
	}

	@Override
	public void execute() {
		if (pos.getUp().getDistSqFromCenter(creature.locX, creature.locY, creature.locZ) > 1.0D) {
			reachedTarget = false;
			++timeSpent;
			if ((timeSpent % 40) == 0) {
				creature.getNavigation().a(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, speed);
			}
		} else {
			reachedTarget = true;
			--timeSpent;
		}

	}

	protected boolean reachedTarget() {
		return reachedTarget;
	}

	private boolean searchTargetBlock() {
		Position creaturePosition = new Position(creature);

		for (int yAdd = 0; yAdd <= 1; yAdd = yAdd > 0 ? -yAdd : 1 - yAdd) {
			for (int radius = 0; radius < scanRadius; ++radius) {
				for (int xAdd = 0; xAdd <= radius; xAdd = xAdd > 0 ? -xAdd : 1 - xAdd) {
					for (int zAdd = (xAdd < radius) && (xAdd > -radius) ? radius : 0; zAdd <= radius; zAdd = zAdd > 0 ? -zAdd : 1 - zAdd) {
						Position position = creaturePosition.add(xAdd, yAdd - 1, zAdd);
						if (creature.b(position.getX(), position.getY(), position.getZ()) && this.checkBlock(creature.world, position)) {
							pos = position;
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	protected abstract boolean checkBlock(World world, Position position);

}
