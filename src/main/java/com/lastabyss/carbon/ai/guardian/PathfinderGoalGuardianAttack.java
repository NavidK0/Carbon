package com.lastabyss.carbon.ai.guardian;

import net.minecraft.server.v1_7_R4.DamageSource;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EnumDifficulty;
import net.minecraft.server.v1_7_R4.GenericAttributes;

import com.lastabyss.carbon.ai.PathfinderGoalNewRandomStroll;
import com.lastabyss.carbon.ai.PathfinderWrapper;
import com.lastabyss.carbon.entity.EntityGuardian;
import com.lastabyss.carbon.utils.Utilities;

public class PathfinderGoalGuardianAttack extends PathfinderWrapper {

	private EntityGuardian guardian;
	private int b;
	private PathfinderGoalNewRandomStroll stroll;

	public PathfinderGoalGuardianAttack(EntityGuardian guardian, PathfinderGoalNewRandomStroll stroll) {
		this.guardian = guardian;
		this.stroll = stroll;
		setMutexBits(3);
	}

	@Override
	public boolean canExecute() {
		EntityLiving entity = guardian.getGoalTarget();
		return (entity != null) && entity.isAlive();
	}

	@Override
	public void setup() {
		b = -10;
		guardian.getNavigation().h();
		guardian.getControllerLook().a(guardian.getGoalTarget(), 90.0F, 90.0F);
		guardian.al = true;
	}

	@Override
	public void finish() {
		guardian.updateBeamTarget(0);
		guardian.setGoalTarget(null);
		stroll.force();
	}

	@Override
	public void execute() {
		EntityLiving target = guardian.getGoalTarget();
		guardian.getNavigation().h();
		guardian.getControllerLook().a(target, 90.0F, 90.0F);
		if (!guardian.hasLineOfSight(target)) {
			guardian.setGoalTarget(null);
		} else {
			++b;
			if (b == 0) {
				guardian.updateBeamTarget(guardian.getGoalTarget().getId());
				guardian.world.broadcastEntityEffect(guardian, (byte) 21);
			} else if (b >= (guardian.isElder() ? 60 : 80)) {
				float damage = 1.0F;
				if (guardian.world.difficulty == EnumDifficulty.HARD) {
					damage += 2.0F;
				}

				if (guardian.isElder()) {
					damage += 2.0F;
				}

				target.damageEntity(DamageSource.b(guardian, guardian), damage);
				target.damageEntity(DamageSource.mobAttack(guardian), (float) guardian.getAttributeInstance(GenericAttributes.e).getValue());
				guardian.setGoalTarget(null);
			} else if ((b >= 60) && ((b % 20) == 0)) {
			}

			super.execute();
		}
	}

	@Override
	public boolean canContinue() {
		return super.canContinue() && (guardian.isElder() || (Utilities.getDistanceSqToEntity(guardian, guardian.getGoalTarget()) > 9.0D));
	}

}
