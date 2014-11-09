package com.lastabyss.carbon.ai;

public class NavigationPath {

	private NavigationPathPoint[] pathPoints = new NavigationPathPoint[1024];
	private int count;

	public NavigationPathPoint addPoint(NavigationPathPoint pathPoint) {
		if (pathPoint.index >= 0) {
			throw new IllegalStateException("OW KNOWS!");
		} else {
			if (this.count == this.pathPoints.length) {
				NavigationPathPoint[] var2 = new NavigationPathPoint[this.count << 1];
				System.arraycopy(this.pathPoints, 0, var2, 0, this.count);
				this.pathPoints = var2;
			}

			this.pathPoints[this.count] = pathPoint;
			pathPoint.index = this.count;
			this.sortBack(this.count++);
			return pathPoint;
		}
	}

	public void clearPath() {
		this.count = 0;
	}

	public NavigationPathPoint remove() {
		NavigationPathPoint pathPoint = this.pathPoints[0];
		this.pathPoints[0] = this.pathPoints[--this.count];
		this.pathPoints[this.count] = null;
		if (this.count > 0) {
			this.sortForward(0);
		}

		pathPoint.index = -1;
		return pathPoint;
	}

	public void changeDistance(NavigationPathPoint pathPoint, float distance) {
		float var3 = pathPoint.distanceToTarget;
		pathPoint.distanceToTarget = distance;
		if (distance < var3) {
			this.sortBack(pathPoint.index);
		} else {
			this.sortForward(pathPoint.index);
		}

	}

	private void sortBack(int index) {
		NavigationPathPoint pathPoint = this.pathPoints[index];

		int i;
		for (float distance = pathPoint.distanceToTarget; index > 0; index = i) {
			i = index - 1 >> 1;
			NavigationPathPoint otherPathPoint = this.pathPoints[i];
			if (distance >= otherPathPoint.distanceToTarget) {
				break;
			}

			this.pathPoints[index] = otherPathPoint;
			otherPathPoint.index = index;
		}

		this.pathPoints[index] = pathPoint;
		pathPoint.index = index;
	}

	private void sortForward(int index) {
		NavigationPathPoint pathPoint = this.pathPoints[index];
		float distance = pathPoint.distanceToTarget;

		while (true) {
			int i = 1 + (index << 1);
			int j = i + 1;
			if (i >= this.count) {
				break;
			}

			NavigationPathPoint var6 = this.pathPoints[i];
			float var7 = var6.distanceToTarget;
			NavigationPathPoint var8;
			float var9;
			if (j >= this.count) {
				var8 = null;
				var9 = Float.POSITIVE_INFINITY;
			} else {
				var8 = this.pathPoints[j];
				var9 = var8.distanceToTarget;
			}

			if (var7 < var9) {
				if (var7 >= distance) {
					break;
				}

				this.pathPoints[index] = var6;
				var6.index = index;
				index = i;
			} else {
				if (var9 >= distance) {
					break;
				}

				this.pathPoints[index] = var8;
				var8.index = index;
				index = j;
			}
		}

		this.pathPoints[index] = pathPoint;
		pathPoint.index = index;
	}

	public boolean isEmpty() {
		return this.count == 0;
	}

}
