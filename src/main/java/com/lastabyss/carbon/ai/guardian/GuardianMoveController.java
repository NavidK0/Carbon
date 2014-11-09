package com.lastabyss.carbon.ai.guardian;

import net.minecraft.server.v1_7_R4.ControllerMove;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.MathHelper;

import com.lastabyss.carbon.entity.EntityGuardian;

public class GuardianMoveController extends ControllerMove {

	private EntityGuardian guardian;
	private double b;
	private double c;
	private double d;
	private double e;
	private boolean f;

	public GuardianMoveController(EntityGuardian guardian) {
		super(guardian);
		this.guardian = guardian;
	}

	@Override
	public boolean a() {
		return f;
	}

	@Override
	public double b() {
		return e;
	}

	@Override
	public void a(final double d0, final double d1, final double d2, final double d3) {
		b = d0;
		c = d1;
		d = d2;
		e = d3;
		f = true;
	}

	@Override
	public void c() {
		if (f && !guardian.getNavigation().g()) {
			double var1 = b - guardian.locX;
			double var3 = c - guardian.locY;
			double var5 = d - guardian.locZ;
			double var7 = (var1 * var1) + (var3 * var3) + (var5 * var5);
			var7 = MathHelper.sqrt(var7);
			var3 /= var7;
			float var9 = (float) ((Math.atan2(var5, var1) * 180.0D) / 3.1415927410125732D) - 90.0F;
			guardian.yaw = this.a(guardian.yaw, var9, 30.0F);
			guardian.aM = guardian.yaw;
			float var10 = (float) (e * guardian.getAttributeInstance(GenericAttributes.d).getValue());
			guardian.i(guardian.bl() + ((var10 - guardian.bl()) * 0.125F));
			double var11 = Math.sin((guardian.ticksLived + guardian.getId()) * 0.5D) * 0.05D;
			double var13 = Math.cos((guardian.yaw * 3.1415927F) / 180.0F);
			double var15 = Math.sin((guardian.yaw * 3.1415927F) / 180.0F);
			guardian.motX += var11 * var13;
			guardian.motZ += var11 * var15;
			var11 = Math.sin((guardian.ticksLived + guardian.getId()) * 0.75D) * 0.05D;
			guardian.motY += var11 * (var15 + var13) * 0.25D;
			guardian.motY += guardian.bl() * var3 * 0.1D;
			GuardianLookController controllerLook = (GuardianLookController) guardian.getControllerLook();
			double var18 = guardian.locX + ((var1 / var7) * 2.0D);
			double var20 = guardian.getHeadHeight() + guardian.locY + ((var3 / var7) * 1.0D);
			double var22 = guardian.locZ + ((var5 / var7) * 2.0D);
			double var24 = controllerLook.e();
			double var26 = controllerLook.f();
			double var28 = controllerLook.g();
			if (!controllerLook.b()) {
				var24 = var18;
				var26 = var20;
				var28 = var22;
			}

			guardian.getControllerLook().a(var24 + ((var18 - var24) * 0.125D), var26 + ((var20 - var26) * 0.125D), var28 + ((var22 - var28) * 0.125D), 10.0F, 40.0F);
			guardian.addData2(true);
		} else {
			guardian.i(0.0F);
			guardian.addData2(false);
		}
	}

	protected float a(float var1, float var2, float var3) {
		float var4 = MathHelper.g(var2 - var1);
		if (var4 > var3) {
			var4 = var3;
		}

		if (var4 < -var3) {
			var4 = -var3;
		}

		float var5 = var1 + var4;
		if (var5 < 0.0F) {
			var5 += 360.0F;
		} else if (var5 > 360.0F) {
			var5 -= 360.0F;
		}

		return var5;
	}

}
