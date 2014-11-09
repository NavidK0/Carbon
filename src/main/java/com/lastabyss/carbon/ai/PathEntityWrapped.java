package com.lastabyss.carbon.ai;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.PathEntity;
import net.minecraft.server.v1_7_R4.PathPoint;
import net.minecraft.server.v1_7_R4.Vec3D;

public class PathEntityWrapped extends PathEntity {

	private final NavigationPathPoint[] pathPoints;
	private int currentPathIndex;
	private int pathLength;

	public PathEntityWrapped(NavigationPathPoint[] pathpoints) {
		super(new PathPoint[0]);
		this.pathPoints = pathpoints;
		this.pathLength = pathpoints.length;
	}

	public void incrementCurrentIndex() {
		++this.currentPathIndex;
	}

	public boolean isFinished() {
		return this.currentPathIndex >= this.pathLength;
	}

	public NavigationPathPoint getFinalPathPoint() {
		return this.pathLength > 0 ? this.pathPoints[this.pathLength - 1] : null;
	}

	public NavigationPathPoint getPathPointFromIndex(int index) {
		return this.pathPoints[index];
	}

	public int getCurrentPathLength() {
		return this.pathLength;
	}

	public void setCurrentPathLength(int pathLength) {
		this.pathLength = pathLength;
	}

	public int getCurrentPathIndex() {
		return this.currentPathIndex;
	}

	public void setCurrentPathIndex(int currentPathIndex) {
		this.currentPathIndex = currentPathIndex;
	}

	public Vec3D getVectorFromIndex(Entity entitiy, int pathIndex) {
		double x = (double) this.pathPoints[pathIndex].x + (double) ((int) (entitiy.width + 1.0F)) * 0.5D;
		double y = (double) this.pathPoints[pathIndex].y;
		double z = (double) this.pathPoints[pathIndex].z + (double) ((int) (entitiy.width + 1.0F)) * 0.5D;
		return Vec3D.a(x, y, z);
	}

	public Vec3D getPosition(Entity entitiy) {
		return this.getVectorFromIndex(entitiy, this.currentPathIndex);
	}

	public boolean isSamePath(PathEntityWrapped pathEntity) {
		if (pathEntity == null) {
			return false;
		} else if (pathEntity.pathPoints.length != this.pathPoints.length) {
			return false;
		} else {
			for (int i = 0; i < this.pathPoints.length; ++i) {
				if (this.pathPoints[i].x != pathEntity.pathPoints[i].x || this.pathPoints[i].y != pathEntity.pathPoints[i].y || this.pathPoints[i].z != pathEntity.pathPoints[i].z) {
					return false;
				}
			}

			return true;
		}
	}

	public boolean isDestinationSame(Vec3D var1) {
		NavigationPathPoint pathPoint = this.getFinalPathPoint();
		return pathPoint == null ? false : pathPoint.x == (int) var1.a && pathPoint.z == (int) var1.c;
	}

	@Override
    public void a() {
		incrementCurrentIndex();
    }

	@Override
    public boolean b() {
		return isFinished();
    }

	@Override
    public PathPoint c() {
		return getFinalPathPoint();
    }

	@Override
    public PathPoint a(final int n) {
		return getPathPointFromIndex(n);
    }

	@Override
    public int d() {
    	return getCurrentPathLength();
    }
    
	@Override
    public void b(final int c) {
		setCurrentPathLength(c);
    }
    
	@Override
    public int e() {
		return getCurrentPathIndex();
    }
    
	@Override
    public void c(final int b) {
		setCurrentPathIndex(b);
    }
    
	@Override
    public Vec3D a(final Entity entity, final int n) {
		return getVectorFromIndex(entity, n);
    }
    
	@Override
    public Vec3D a(final Entity entity) {
		return getPosition(entity);
    }
    
	@Override
    public boolean a(final PathEntity pathEntity) {
		if (pathEntity instanceof PathEntityWrapped) {
			return isSamePath((PathEntityWrapped) pathEntity);
		}
		throw new RuntimeException("Conversion is not supported yet");
    }
    
	@Override
    public boolean b(final Vec3D vec3D) {
		return isDestinationSame(vec3D);
    }

}
