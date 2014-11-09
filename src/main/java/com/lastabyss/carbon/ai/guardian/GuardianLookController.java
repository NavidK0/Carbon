package com.lastabyss.carbon.ai.guardian;

import net.minecraft.server.v1_7_R4.ControllerLook;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.MathHelper;

import org.bukkit.craftbukkit.v1_7_R4.TrigMath;

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
		a = entityinsentient;
	}

	@Override
	public void a(final Entity entity, final float f, final float f1) {
		e = entity.locX;
		if (entity instanceof EntityLiving) {
			this.f = entity.locY + entity.getHeadHeight();
		} else {
			this.f = (entity.boundingBox.b + entity.boundingBox.e) / 2.0;
		}
		g = entity.locZ;
		b = f;
		c = f1;
		d = true;
	}

	@Override
	public void a(final double d0, final double d1, final double d2, final float f, final float f1) {
		e = d0;
		this.f = d1;
		g = d2;
		b = f;
		c = f1;
		d = true;
	}

	@Override
	public void a() {
		a.pitch = 0.0f;
		if (d) {
			d = false;
			final double d0 = e - a.locX;
			final double d = f - (a.locY + a.getHeadHeight());
			final double d2 = g - a.locZ;
			final double d3 = MathHelper.sqrt((d0 * d0) + (d2 * d2));
			final float f = (float) ((TrigMath.atan2(d2, d0) * 180.0) / 3.1415927410125732) - 90.0f;
			final float f2 = (float) (-((TrigMath.atan2(d, d3) * 180.0) / 3.1415927410125732));
			a.pitch = this.a(a.pitch, f2, c);
			a.aO = this.a(a.aO, f, b);
		} else {
			a.aO = this.a(a.aO, a.aM, 10.0f);
		}
		final float f3 = MathHelper.g(a.aO - a.aM);
		if (!a.getNavigation().g()) {
			if (f3 < -75.0f) {
				a.aO = a.aM - 75.0f;
			}
			if (f3 > 75.0f) {
				a.aO = a.aM + 75.0f;
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
		return d;
	}

	public double e() {
		return e;
	}

	public double f() {
		return f;
	}

	public double g() {
		return g;
	}

}
