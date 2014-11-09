package com.lastabyss.carbon.ai;

import java.util.Random;

import com.lastabyss.carbon.utils.nmsclasses.Position;

import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.Vec3D;

public class NewRandomPositionGenerator {

	private static Vec3D a = Vec3D.a(0.0D, 0.0D, 0.0D);

	public static Vec3D a(EntityCreature creature, int var1, int var2) {
		return c(creature, var1, var2, (Vec3D) null);
	}

	public static Vec3D a(EntityCreature creature, int var1, int var2, Vec3D vec) {
		a = vec.add(-creature.locX, -creature.locY, -creature.locZ);
		return c(creature, var1, var2, a);
	}

	public static Vec3D b(EntityCreature creature, int var1, int var2, Vec3D vec) {
		a = (Vec3D.a(creature.locX, creature.locY, creature.locZ)).add(-vec.a, -vec.b, -vec.c);
		return c(creature, var1, var2, a);
	}

	private static Vec3D c(EntityCreature creature, int var1, int var2, Vec3D vec) {
		Random random = creature.aI();
		boolean var5 = false;
		int x = 0;
		int y = 0;
		int z = 0;
		float var9 = -99999.0F;
		boolean var10;
		if (creature.bY()) {
			double var11 = new Position(creature.bV().x, creature.bV().y, creature.bV().z).getDistSq((double) MathHelper.floor(creature.locX), (double) MathHelper.floor(creature.locY), (double) MathHelper.floor(creature.locZ)) + 4.0D;
			double var13 = (double) (creature.bW() + (float) var1);
			var10 = var11 < var13 * var13;
		} else {
			var10 = false;
		}

		for (int i = 0; i < 10; ++i) {
			int var12 = random.nextInt(2 * var1 + 1) - var1;
			int var18 = random.nextInt(2 * var2 + 1) - var2;
			int var14 = random.nextInt(2 * var1 + 1) - var1;
			if (vec == null || (double) var12 * vec.a + (double) var14 * vec.c >= 0.0D) {
				Position position;
				if (creature.bY() && var1 > 1) {
					position = new Position(creature.bV().x, creature.bV().y, creature.bV().z);
					if (creature.locX > (double) position.getX()) {
						var12 -= random.nextInt(var1 / 2);
					} else {
						var12 += random.nextInt(var1 / 2);
					}

					if (creature.locZ > (double) position.getZ()) {
						var14 -= random.nextInt(var1 / 2);
					} else {
						var14 += random.nextInt(var1 / 2);
					}
				}

				var12 += MathHelper.floor(creature.locX);
				var18 += MathHelper.floor(creature.locY);
				var14 += MathHelper.floor(creature.locZ);
				position = new Position(var12, var18, var14);
				if (!var10 || creature.b(position.getX(), position.getY(), position.getZ())) {
					float var16 = creature.a(position.getX(), position.getY(), position.getZ());
					if (var16 > var9) {
						var9 = var16;
						x = var12;
						y = var18;
						z = var14;
						var5 = true;
					}
				}
			}
		}

		if (var5) {
			return Vec3D.a((double) x, (double) y, (double) z);
		} else {
			return null;
		}
	}

}
