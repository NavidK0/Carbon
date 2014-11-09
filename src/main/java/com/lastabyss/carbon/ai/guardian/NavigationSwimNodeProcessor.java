package com.lastabyss.carbon.ai.guardian;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.IBlockAccess;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.MathHelper;

import com.lastabyss.carbon.ai.NavigationNodeProcessor;
import com.lastabyss.carbon.ai.NavigationPathPoint;
import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

public class NavigationSwimNodeProcessor extends NavigationNodeProcessor {

	public void a(IBlockAccess var1, Entity var2) {
		super.a(var1, var2);
	}

	public void a() {
		super.a();
	}

	public NavigationPathPoint a(Entity var1) {
		return this.a(MathHelper.floor(var1.boundingBox.a), MathHelper.floor(var1.boundingBox.b + 0.5D), MathHelper.floor(var1.boundingBox.c));
	}

	public NavigationPathPoint a(Entity var1, double var2, double var4, double var6) {
		return this.a(MathHelper.floor(var2 - (double) (var1.width / 2.0F)), MathHelper.floor(var4 + 0.5D), MathHelper.floor(var6 - (double) (var1.width / 2.0F)));
	}

	public int a(NavigationPathPoint[] var1, Entity var2, NavigationPathPoint var3, NavigationPathPoint var4, float var5) {
		int var6 = 0;

		for (BlockFace var10 : BlockFace.values()) {
			NavigationPathPoint var11 = this.a(var2, var3.x + var10.getFrontDirectionX(), var3.y + var10.getFrontDirectionY(), var3.z + var10.getFrontDirectionZ());
			if (var11 != null && !var11.visited && var11.getDistance(var4) < var5) {
				var1[var6++] = var11;
			}
		}

		return var6;
	}

	private NavigationPathPoint a(Entity var1, int var2, int var3, int var4) {
		int var5 = this.b(var1, var2, var3, var4);
		return var5 == -1 ? this.a(var2, var3, var4) : null;
	}

	private int b(Entity var1, int var2, int var3, int var4) {
		for (int var5 = var2; var5 < var2 + this.c; ++var5) {
			for (int var6 = var3; var6 < var3 + this.d; ++var6) {
				for (int var7 = var4; var7 < var4 + this.e; ++var7) {
					Block var9 = this.blockAccess.getType(var5, var6, var7);
					if (var9.getMaterial() != Material.WATER) {
						return 0;
					}
				}
			}
		}

		return -1;
	}

}
