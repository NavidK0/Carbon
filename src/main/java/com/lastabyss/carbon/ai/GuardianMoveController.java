package com.lastabyss.carbon.ai;

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
		return this.f;
	}

	@Override
	public double b() {
		return this.e;
	}

	@Override
	public void a(final double d0, final double d1, final double d2, final double d3) {
		this.b = d0;
		this.c = d1;
		this.d = d2;
		this.e = d3;
		this.f = true;
	}

	@Override
	public void c() {
		if (this.f && !this.guardian.getNavigation().g()) {
			double var1 = this.b - this.guardian.locX;
			double var3 = this.c - this.guardian.locY;
			double var5 = this.d - this.guardian.locZ;
			double var7 = var1 * var1 + var3 * var3 + var5 * var5;
			var7 = (double) MathHelper.sqrt(var7);
			var3 /= var7;
			float var9 = (float) (Math.atan2(var5, var1) * 180.0D / 3.1415927410125732D) - 90.0F;
			this.guardian.yaw = this.a(this.guardian.yaw, var9, 30.0F);
			this.guardian.aM = this.guardian.yaw;
			float var10 = (float) (this.e * this.guardian.getAttributeInstance(GenericAttributes.d).getValue());
			this.guardian.i(this.guardian.bl() + (var10 - this.guardian.bl()) * 0.125F);
			double var11 = Math.sin((double) (this.guardian.ticksLived + this.guardian.getId()) * 0.5D) * 0.05D;
			double var13 = Math.cos((double) (this.guardian.yaw * 3.1415927F / 180.0F));
			double var15 = Math.sin((double) (this.guardian.yaw * 3.1415927F / 180.0F));
			this.guardian.motX += var11 * var13;
			this.guardian.motZ += var11 * var15;
			var11 = Math.sin((double) (this.guardian.ticksLived + this.guardian.getId()) * 0.75D) * 0.05D;
			this.guardian.motY += var11 * (var15 + var13) * 0.25D;
			this.guardian.motY += (double) this.guardian.bl() * var3 * 0.1D;
			GuardianLookController controllerLook = (GuardianLookController) this.guardian.getControllerLook();
			double var18 = this.guardian.locX + var1 / var7 * 2.0D;
			double var20 = (double) this.guardian.getHeadHeight() + this.guardian.locY + var3 / var7 * 1.0D;
			double var22 = this.guardian.locZ + var5 / var7 * 2.0D;
			double var24 = controllerLook.e();
			double var26 = controllerLook.f();
			double var28 = controllerLook.g();
			if (!controllerLook.b()) {
				var24 = var18;
				var26 = var20;
				var28 = var22;
			}

			this.guardian.getControllerLook().a(var24 + (var18 - var24) * 0.125D, var26 + (var20 - var26) * 0.125D, var28 + (var22 - var28) * 0.125D, 10.0F, 40.0F);
			guardian.addData2(true);
		} else {
			this.guardian.i(0.0F);
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
