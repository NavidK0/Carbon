package com.lastabyss.carbon.ai.rabbit;

import net.minecraft.server.v1_7_R4.ControllerJump;

import com.lastabyss.carbon.entity.EntityRabbit;
import com.lastabyss.carbon.entity.EntityRabbit.MoveType;

public class RabbitJumpController extends ControllerJump {

	private EntityRabbit rabbit;
    private boolean b;
	private boolean d;

	public RabbitJumpController(EntityRabbit rabbit) {
		super(rabbit);
		d = false;
		this.rabbit = rabbit;
	}

	public boolean c() {
		return b;
	}

	public boolean d() {
		return d;
	}


    @Override
	public void a() {
        b = true;
    }

	public void a(boolean var1) {
		d = var1;
	}

	@Override
	public void b() {
		if (b) {
			rabbit.b(MoveType.STEP);
			b = false;
		}
	}

}
