package com.lastabyss.carbon.ai;

import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.PathPoint;

public class NavigationPathPoint extends PathPoint {

	public final int x;
	public final int y;
	public final int z;
	private final int hash;
	int index = -1;
	float totalPathDistance;
	float distanceToNext;
	float distanceToTarget;
	NavigationPathPoint previous;
	public boolean visited;

	public NavigationPathPoint(int x, int y, int z) {
		super(x, y, z);
		this.x = x;
		this.y = y;
		this.z = z;
		this.hash = calcHash(x, y, z);
	}

	public static int calcHash(int x, int y, int z) {
		return y & 255 | (x & 32767) << 8 | (z & 32767) << 24 | (x < 0 ? Integer.MIN_VALUE : 0) | (z < 0 ? '\u8000' : 0);
	}

	public float getDistance(NavigationPathPoint pathPoint) {
		float x = (float) (pathPoint.x - this.x);
		float y = (float) (pathPoint.y - this.y);
		float z = (float) (pathPoint.z - this.z);
		return MathHelper.c(x * x + y * y + z * z);
	}

	public float getDistanceSquared(NavigationPathPoint pathPoint) {
		float x = (float) (pathPoint.x - this.x);
		float y = (float) (pathPoint.y - this.y);
		float z = (float) (pathPoint.z - this.z);
		return x * x + y * y + z * z;
	}

	public boolean equals(Object object) {
		if (!(object instanceof NavigationPathPoint)) {
			return false;
		} else {
			NavigationPathPoint pathPoint = (NavigationPathPoint) object;
			return this.hash == pathPoint.hash && this.x == pathPoint.x && this.y == pathPoint.y && this.z == pathPoint.z;
		}
	}

	public int hashCode() {
		return this.hash;
	}

	public boolean isAssigned() {
		return this.index >= 0;
	}

	public String toString() {
		return this.x + ", " + this.y + ", " + this.z;
	}

	@Override
	public float a(final PathPoint pathPoint) {
		if (pathPoint instanceof NavigationPathPoint) {
			return getDistance((NavigationPathPoint) pathPoint);
		}
		throw new RuntimeException("Conversion is not supported yet");
	}

	@Override
	public float b(final PathPoint pathPoint) {
		if (pathPoint instanceof NavigationPathPoint) {
			return getDistanceSquared((NavigationPathPoint) pathPoint);
		}
		throw new RuntimeException("Conversion is not supported yet");
	}

	@Override
    public boolean a() {
		return isAssigned();
    }

}
