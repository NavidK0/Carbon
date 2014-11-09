package com.lastabyss.carbon.ai.rabbit;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.BlockCarrots;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.World;

import com.lastabyss.carbon.ai.PathfinderGoalMoveToBlock;
import com.lastabyss.carbon.entity.EntityRabbit;
import com.lastabyss.carbon.utils.nmsclasses.Position;

public class PathfinderGoalRabbitRaidFarm extends PathfinderGoalMoveToBlock {

	private final EntityRabbit rabbit;
	private boolean isZeroCarrotTicks;
	private boolean targetBlockFound = false;

	public PathfinderGoalRabbitRaidFarm(EntityRabbit rabbit) {
		super(rabbit, 0.699999988079071D, 16);
		this.rabbit = rabbit;
	}

	@Override
	public boolean canExecute() {
		if (cooldown <= 0) {
			if (!rabbit.world.getGameRules().getBoolean("mobGriefing")) {
				return false;
			}

			targetBlockFound = false;
			isZeroCarrotTicks = rabbit.isZeroCarrotTicks();
		}

		return super.canExecute();
	}

	@Override
	public boolean canContinue() {
		return targetBlockFound && super.canContinue();
	}

	@Override
	public void setup() {
		super.setup();
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public void execute() {
		super.execute();
		rabbit.getControllerLook().a(pos.getX() + 0.5D, pos.getY() + 1, pos.getZ() + 0.5D, 10.0F, rabbit.x());
		if (reachedTarget()) {
			World world = rabbit.world;
			Position position = pos.getUp();
			Block block = world.getType(position.getX(), position.getY(), position.getZ());
			if (targetBlockFound && (block instanceof BlockCarrots) && world.getData(position.getX(), position.getY(), position.getZ()) == 7) {
				world.setTypeAndData(position.getX(), position.getY(), position.getZ(), Blocks.AIR, 0, 2);
				rabbit.setFullCarrotTicks();
			}

			targetBlockFound = false;
			cooldown = 10;
		}

	}

	@Override
	protected boolean checkBlock(World world, Position position) {
		if (world.getType(position.getX(), position.getY(), position.getZ()) == Blocks.SOIL) {
			position = position.getUp();
			Block block = world.getType(position.getX(), position.getY(), position.getZ());
			if ((block instanceof BlockCarrots) && world.getData(position.getX(), position.getY(), position.getZ()) == 7 && isZeroCarrotTicks && !targetBlockFound) {
				targetBlockFound = true;
				return true;
			}
		}
		return false;
	}

}
