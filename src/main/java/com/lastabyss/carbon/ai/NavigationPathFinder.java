package com.lastabyss.carbon.ai;

import com.lastabyss.carbon.utils.nmsclasses.Position;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.IBlockAccess;

public class NavigationPathFinder {

	private NavigationPath path = new NavigationPath();
	private NavigationPathPoint[] pathpoints = new NavigationPathPoint[32];
	private NavigationNodeProcessor nodeProcessor;

	public NavigationPathFinder(NavigationNodeProcessor nodeProcessor) {
		this.nodeProcessor = nodeProcessor;
	}

	public PathEntityWrapped createPath(IBlockAccess blockAccess, Entity entity, Entity taget, float speed) {
		return this.createPath(blockAccess, entity, taget.locX, taget.boundingBox.b, taget.locZ, speed);
	}

	public PathEntityWrapped createPath(IBlockAccess blockAccess, Entity entity, Position target, float speed) {
		return this.createPath(blockAccess, entity, (double) ((float) target.getX() + 0.5F), (double) ((float) target.getY() + 0.5F), (double) ((float) target.getZ() + 0.5F), speed);
	}

	private PathEntityWrapped createPath(IBlockAccess blockAccess, Entity entity, double targetX, double targetY, double targetZ, float speed) {
		this.path.clearPath();
		this.nodeProcessor.a(blockAccess, entity);
		NavigationPathPoint pointStart = this.nodeProcessor.a(entity);
		NavigationPathPoint pointEnd = this.nodeProcessor.a(entity, targetX, targetY, targetZ);
		PathEntityWrapped path = this.createPath(entity, pointStart, pointEnd, speed);
		this.nodeProcessor.a();
		return path;
	}

	private PathEntityWrapped createPath(Entity entity, NavigationPathPoint point1, NavigationPathPoint point2, float speed) {
		point1.totalPathDistance = 0.0F;
		point1.distanceToNext = point1.getDistanceSquared(point2);
		point1.distanceToTarget = point1.distanceToNext;
		this.path.clearPath();
		this.path.addPoint(point1);
		NavigationPathPoint nextPoint = point1;

		while (!this.path.isEmpty()) {
			NavigationPathPoint pathPoint = this.path.remove();
			if (pathPoint.equals(point2)) {
				return this.createPath(point1, point2);
			}

			if (pathPoint.getDistanceSquared(point2) < nextPoint.getDistanceSquared(point2)) {
				nextPoint = pathPoint;
			}

			pathPoint.visited = true;
			int var7 = this.nodeProcessor.a(this.pathpoints, entity, pathPoint, point2, speed);

			for (int i = 0; i < var7; ++i) {
				NavigationPathPoint iPoint = this.pathpoints[i];
				float var10 = pathPoint.totalPathDistance + pathPoint.getDistanceSquared(iPoint);
				if (var10 < speed * 2.0F && (!iPoint.isAssigned() || var10 < iPoint.totalPathDistance)) {
					iPoint.previous = pathPoint;
					iPoint.totalPathDistance = var10;
					iPoint.distanceToNext = iPoint.getDistanceSquared(point2);
					if (iPoint.isAssigned()) {
						this.path.changeDistance(iPoint, iPoint.totalPathDistance + iPoint.distanceToNext);
					} else {
						iPoint.distanceToTarget = iPoint.totalPathDistance + iPoint.distanceToNext;
						this.path.addPoint(iPoint);
					}
				}
			}
		}

		if (nextPoint == point1) {
			return null;
		} else {
			return this.createPath(point1, nextPoint);
		}
	}

	private PathEntityWrapped createPath(NavigationPathPoint var1, NavigationPathPoint var2) {
		int var3 = 1;

		NavigationPathPoint var4;
		for (var4 = var2; var4.previous != null; var4 = var4.previous) {
			++var3;
		}

		NavigationPathPoint[] var5 = new NavigationPathPoint[var3];
		var4 = var2;
		--var3;

		for (var5[var3] = var2; var4.previous != null; var5[var3] = var4) {
			var4 = var4.previous;
			--var3;
		}

		return new PathEntityWrapped(var5);
	}

}
