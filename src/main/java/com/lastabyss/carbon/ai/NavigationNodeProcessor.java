package com.lastabyss.carbon.ai;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.IBlockAccess;
import net.minecraft.server.v1_7_R4.IntHashMap;
import net.minecraft.server.v1_7_R4.MathHelper;

public abstract class NavigationNodeProcessor {

	protected IBlockAccess blockAccess;
	protected IntHashMap map = new IntHashMap();
	protected int c;
	protected int d;
	protected int e;

	public void a(IBlockAccess blockAccess, Entity entity) {
		this.blockAccess = blockAccess;
		this.map.c();
		this.c = MathHelper.floor(entity.width + 1.0F);
		this.d = MathHelper.floor(entity.height + 1.0F);
		this.e = MathHelper.floor(entity.width + 1.0F);
	}

	public void a() {
	}

	protected NavigationPathPoint a(int var1, int var2, int var3) {
		int var4 = NavigationPathPoint.calcHash(var1, var2, var3);
		NavigationPathPoint var5 = (NavigationPathPoint) this.map.get(var4);
		if (var5 == null) {
			var5 = new NavigationPathPoint(var1, var2, var3);
			this.map.a(var4, var5);
		}

		return var5;
	}

	public abstract NavigationPathPoint a(Entity var1);

	public abstract NavigationPathPoint a(Entity var1, double var2, double var4, double var6);

	public abstract int a(NavigationPathPoint[] var1, Entity var2, NavigationPathPoint var3, NavigationPathPoint var4, float var5);

}
