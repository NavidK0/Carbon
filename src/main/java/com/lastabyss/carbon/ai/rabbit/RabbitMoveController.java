package com.lastabyss.carbon.ai.rabbit;

import net.minecraft.server.v1_7_R4.ControllerMove;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.MathHelper;

import org.bukkit.craftbukkit.v1_7_R4.TrigMath;

import com.lastabyss.carbon.entity.EntityRabbit;

public class RabbitMoveController extends ControllerMove {

	private EntityRabbit rabbit;
	private double b;
	private double c;
	private double d;
	private double e;
	private boolean f;

	public RabbitMoveController(final EntityRabbit entityRabbit) {
		super(entityRabbit);
		rabbit = entityRabbit;
		b = entityRabbit.locX;
		c = entityRabbit.locY;
		d = entityRabbit.locZ;
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
		if (rabbit.onGround && !rabbit.cj()) {
			rabbit.b(0.0D);
		}
		rabbit.n(0.0f);
		if (f) {
			f = false;
			final int i = MathHelper.floor(rabbit.boundingBox.b + 0.5);
			final double d0 = b - rabbit.locX;
			final double d = this.d - rabbit.locZ;
			final double d2 = c - i;
			final double d3 = (d0 * d0) + (d2 * d2) + (d * d);
			if (d3 >= 2.500000277905201E-7) {
				final float f = (float) ((TrigMath.atan2(d, d0) * 180.0) / 3.1415927410125732) - 90.0f;
				rabbit.yaw = this.a(rabbit.yaw, f, 30.0f);
				rabbit.i((float) (e * rabbit.getAttributeInstance(GenericAttributes.d).getValue()));
				if ((d2 > 0.0) && (((d0 * d0) + (d * d)) < 1.0)) {
					rabbit.getControllerJump().a();
				}
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

	public double d() {
		return b;
	}

	public double e() {
		return c;
	}

	public double f() {
		return d;
	}

}
