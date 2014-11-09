package com.lastabyss.carbon.ai.rabbit;

import java.util.List;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.IEntitySelector;
import net.minecraft.server.v1_7_R4.Navigation;
import net.minecraft.server.v1_7_R4.PathEntity;
import net.minecraft.server.v1_7_R4.RandomPositionGenerator;
import net.minecraft.server.v1_7_R4.Vec3D;

import com.lastabyss.carbon.ai.ImprovedEntitySelector;
import com.lastabyss.carbon.ai.PathfinderWrapper;
import com.lastabyss.carbon.utils.Utilities;

public class PathfinderGoalRabbitAvoidEntity extends PathfinderWrapper {

	public final IEntitySelector mainSelector = new IEntitySelector() {
		@Override
		public boolean a(Entity entity) {
			return entity.isAlive() && creature.getEntitySenses().canSee(entity);
		}
	};
	protected EntityCreature creature;
	private double d;
	private double e;
	protected Entity c;
	private float xzGrow;
	private PathEntity pathEntity;
	private Navigation navigation;
	private IEntitySelector customSelector;

	public PathfinderGoalRabbitAvoidEntity(EntityCreature creature, IEntitySelector customSelector, float xzGrow, double var4, double var6) {
		this.creature = creature;
		this.customSelector = customSelector;
		this.xzGrow = xzGrow;
		d = var4;
		e = var6;
		navigation = creature.getNavigation();
		setMutexBits(1);
	}

	@Override
	public boolean canExecute() {
		List<?> entities = creature.world.getEntities(creature, creature.boundingBox.grow(xzGrow, 3.0D, xzGrow), ImprovedEntitySelector.and(mainSelector, customSelector));
		if (entities.isEmpty()) {
			return false;
		} else {
			c = (Entity) entities.get(0);
			Vec3D var2 = RandomPositionGenerator.b(creature, 16, 7, Vec3D.a(c.locX, c.locY, c.locY));
			if (var2 == null) {
				return false;
			} else if (Utilities.getDistanceSqToVec(c, var2) < Utilities.getDistanceSqToEntity(creature, c)) {
				return false;
			} else {
				pathEntity = navigation.a(var2.a, var2.b, var2.c);
				return pathEntity == null ? false : pathEntity.b(var2);
			}
		}
	}

	@Override
	public boolean canContinue() {
		return !navigation.g();
	}

	@Override
	public void setup() {
		navigation.a(pathEntity, d);
	}

	@Override
	public void finish() {
		c = null;
	}

	@Override
	public void execute() {
		if (Utilities.getDistanceSqToEntity(creature, c) < 49.0D) {
			creature.getNavigation().a(e);
		} else {
			creature.getNavigation().a(d);
		}

	}

}
