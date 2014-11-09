package com.lastabyss.carbon.ai.guardian;

import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EnumMovingObjectType;
import net.minecraft.server.v1_7_R4.MovingObjectPosition;
import net.minecraft.server.v1_7_R4.Vec3D;
import net.minecraft.server.v1_7_R4.World;

import com.lastabyss.carbon.ai.NavigationPathFinder;
import com.lastabyss.carbon.ai.NavigationWrapper;

public class GuardianNavigation extends NavigationWrapper {

	public GuardianNavigation(EntityInsentient entity, World world) {
		super(entity, world);
	}

	@Override
	protected NavigationPathFinder createPathfinder() {
		return new NavigationPathFinder(new NavigationSwimNodeProcessor());
	}

	@Override
	protected boolean canNavigate() {
		return isInLiquid();
	}

	@Override
	protected Vec3D getEntityPosition() {
		return Vec3D.a(entity.locX, entity.locY + (entity.height * 0.5D), entity.locZ);
	}

	@Override
	protected void pathMove() {
		Vec3D vec = getEntityPosition();
		float var2 = entity.width * entity.width;
		byte var3 = 6;
		if (getSquaredDistance(vec, path.getVectorFromIndex(entity, path.getCurrentPathIndex())) < var2) {
			path.incrementCurrentIndex();
		}

		for (int var4 = Math.min(path.getCurrentPathIndex() + var3, path.getCurrentPathLength() - 1); var4 > path.getCurrentPathIndex(); --var4) {
			Vec3D var5 = path.getVectorFromIndex(entity, var4);
			if ((getSquaredDistance(var5, vec) <= 36.0D) && this.isInStraightLine(vec, var5, 0, 0, 0)) {
				path.setCurrentPathIndex(var4);
				break;
			}
		}

		this.checkPosition(vec);
	}

	@Override
	protected void rempoveSunlitPathPoints() {
		super.rempoveSunlitPathPoints();
	}

	@Override
	protected boolean isInStraightLine(Vec3D var1, Vec3D var2, int var3, int var4, int var5) {
		MovingObjectPosition var6 = world.rayTrace(var1, Vec3D.a(var2.a, var2.b + (entity.height * 0.5D), var2.c), false, true, false);
		return (var6 == null) || (var6.type == EnumMovingObjectType.MISS);
	}

}
