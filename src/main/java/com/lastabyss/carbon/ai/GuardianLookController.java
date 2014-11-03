package com.lastabyss.carbon.ai;

import org.bukkit.craftbukkit.v1_7_R4.TrigMath;

import net.minecraft.server.v1_7_R4.ControllerLook;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.MathHelper;

public class GuardianLookController extends ControllerLook {

	private EntityInsentient a;
	private float b;
	private float c;
	private boolean d;
	private double e;
	private double f;
	private double g;

	public GuardianLookController(final EntityInsentient entityinsentient) {
		super(entityinsentient);
		this.a = entityinsentient;
	}

	public void a(final Entity entity, final float f, final float f1) {
		this.e = entity.locX;
		if (entity instanceof EntityLiving) {
			this.f = entity.locY + entity.getHeadHeight();
		} else {
			this.f = (entity.boundingBox.b + entity.boundingBox.e) / 2.0;
		}
		this.g = entity.locZ;
		this.b = f;
		this.c = f1;
		this.d = true;
	}

	public void a(final double d0, final double d1, final double d2, final float f, final float f1) {
		this.e = d0;
		this.f = d1;
		this.g = d2;
		this.b = f;
		this.c = f1;
		this.d = true;
	}

	public void a() {
		this.a.pitch = 0.0f;
		if (this.d) {
			this.d = false;
			final double d0 = this.e - this.a.locX;
			final double d = this.f - (this.a.locY + this.a.getHeadHeight());
			final double d2 = this.g - this.a.locZ;
			final double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
			final float f = (float) (TrigMath.atan2(d2, d0) * 180.0 / 3.1415927410125732) - 90.0f;
			final float f2 = (float) (-(TrigMath.atan2(d, d3) * 180.0 / 3.1415927410125732));
			this.a.pitch = this.a(this.a.pitch, f2, this.c);
			this.a.aO = this.a(this.a.aO, f, this.b);
		} else {
			this.a.aO = this.a(this.a.aO, this.a.aM, 10.0f);
		}
		final float f3 = MathHelper.g(this.a.aO - this.a.aM);
		if (!this.a.getNavigation().g()) {
			if (f3 < -75.0f) {
				this.a.aO = this.a.aM - 75.0f;
			}
			if (f3 > 75.0f) {
				this.a.aO = this.a.aM + 75.0f;
			}
		}
	}

	private float a(final float f, final float f1, final float f2) {
		float f3 = MathHelper.g(f1 - f);
		if (f3 > f2) {
			f3 = f2;
		}
		if (f3 < -f2) {
			f3 = -f2;
		}
		return f + f3;
	}

	public boolean b() {
		return this.d;
	}

	public double e() {
		return this.e;
	}

	public double f() {
		return this.f;
	}

	public double g() {
		return this.g;
	}

}
