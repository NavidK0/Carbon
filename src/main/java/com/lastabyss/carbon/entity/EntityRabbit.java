package com.lastabyss.carbon.entity;

import java.lang.reflect.Field;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityAgeable;
import net.minecraft.server.v1_7_R4.EntityAnimal;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.EntityWolf;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.IEntitySelector;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.MobEffectList;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.PathEntity;
import net.minecraft.server.v1_7_R4.PathfinderGoalBreed;
import net.minecraft.server.v1_7_R4.PathfinderGoalFloat;
import net.minecraft.server.v1_7_R4.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_7_R4.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_7_R4.PathfinderGoalPanic;
import net.minecraft.server.v1_7_R4.PathfinderGoalTempt;
import net.minecraft.server.v1_7_R4.Vec3D;
import net.minecraft.server.v1_7_R4.World;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.ai.PathfinderGoalNewRandomStroll;
import com.lastabyss.carbon.ai.rabbit.PathfinderGoalRabbitAvoidEntity;
import com.lastabyss.carbon.ai.rabbit.PathfinderGoalRabbitRaidFarm;
import com.lastabyss.carbon.ai.rabbit.RabbitJumpController;
import com.lastabyss.carbon.ai.rabbit.RabbitMoveController;
import com.lastabyss.carbon.entity.bukkit.Rabbit;
import com.lastabyss.carbon.utils.Utilities;

/**
 *
 * @author Navid
 */
public class EntityRabbit extends EntityAnimal {

	private int bm = 0;
	private int bn = 0;
	private int bq = 0;
	private boolean bo = false;
	private boolean bp = false;
	private int moreCarrotTicks;
	private PathfinderGoalRabbitAvoidEntity avoidEntityGoal;
	private EntityRabbit parent = null;
	private MoveType move;

	public final static int TYPE_BROWN = 0;
	public final static int TYPE_WHITE = 1;
	public final static int TYPE_BLACK = 2;
	public final static int TYPE_BLACK_AND_WHITE = 3;
	public final static int TYPE_GOLD = 4;
	public final static int TYPE_SALT_AND_PEPPER = 5;
	public final static int TYPE_KILLER = 99;

	public EntityRabbit(World world) {
		super(world);
		move = MoveType.HOP;
		this.a(0.9F, 0.9F);
		try {
			Utilities.setAccessible(Field.class, EntityInsentient.class.getDeclaredField("bm"), true).set(this, new RabbitJumpController(this));
			Utilities.setAccessible(Field.class, EntityInsentient.class.getDeclaredField("moveController"), true).set(this, new RabbitMoveController(this));
		} catch (Exception e) {
			this.die();
			Carbon.log.info("[Carbon] Failed to set rabbit jump&move controller: " + e.getMessage());
			return;
		}
		getNavigation().a(true);
		goalSelector.a(1, new PathfinderGoalFloat(this));
		goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));
		goalSelector.a(2, new PathfinderGoalTempt(this, 1.0D, Items.CARROT, false));
		goalSelector.a(3, new PathfinderGoalBreed(this, 0.8D));
		goalSelector.a(5, new PathfinderGoalRabbitRaidFarm(this));
		goalSelector.a(5, new PathfinderGoalNewRandomStroll(this, 0.6D));
		goalSelector.a(11, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
		avoidEntityGoal = new PathfinderGoalRabbitAvoidEntity(this, new IEntitySelector() {
			@Override
			public boolean a(Entity entity) {
				return entity instanceof EntityWolf;
			}
		}, 16.0F, 1.33D, 1.33D);
		goalSelector.a(4, avoidEntityGoal);
		b(0.0);
	}

	@Override
	public boolean bk() {
		return true;
	}

	@Override
	protected void aD() {
		super.aD();
		getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
		getAttributeInstance(GenericAttributes.d).setValue(0.25D);
	}

	@Override
	public boolean bE() {
		ItemStack itemstack = ((EntityHuman) passenger).be();

		return (itemstack != null) && (itemstack.getItem() == Items.CARROT_STICK);
	}

	@Override
    protected void bj() {
        this.motY = getControllerMove().a() && (getControllerMove().e() > (locY + 0.5D)) ? 0.5F : move.b();
        if (this.hasEffect(MobEffectList.JUMP)) {
            this.motY += (this.getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.1f;
        }
        if (this.isSprinting()) {
            final float f = this.yaw * 0.017453292f;
            this.motX -= MathHelper.sin(f) * 0.2f;
            this.motZ += MathHelper.cos(f) * 0.2f;
        }
        this.al = true;
    }

	// read
	@Override
	public void a(NBTTagCompound tag) {
		super.a(tag);
		setRabbitType(tag.getInt("RabbitType"));
		moreCarrotTicks = tag.getInt("MoreCarrotTicks");
	}

	// write
	@Override
	public void b(NBTTagCompound tag) {
		super.b(tag);
		tag.setInt("RabbitType", getRabbitType());
		tag.setInt("MoreCarrotTicks", moreCarrotTicks);
	}

	@Override
	protected void c() {
		super.c();
		datawatcher.a(18, (byte) 0);
	}

	public void setMoveType(MoveType move) {
		this.move = move;
	}

	private void cr() {
		((RabbitJumpController) getControllerJump()).a(true);
	}

	private void cs() {
		((RabbitJumpController) getControllerJump()).a(false);
	}

	private void ct() {
		bq = cm();
	}

	public boolean isZeroCarrotTicks() {
		return moreCarrotTicks == 0;
	}

	public void setFullCarrotTicks() {
		world.addParticle(
			"blockdust_"+String.valueOf(Block.getId(Blocks.CARROTS) << 12 | 7),
			(locX + random.nextFloat() * width * 2.0F) - width,
			locY + 0.5D + random.nextFloat() * height,
			(locZ + random.nextFloat() * width * 2.0F) - width,
			0.0D, 0.0D, 0.0D
		);
		moreCarrotTicks = 100;
	}

	protected int cm() {
		return move.c();
	}

	private void cu() {
		ct();
		cs();
	}

	@Override
	public void e() {
		super.e();
		if (bm != bn) {
			if (bm == 0) {
				world.broadcastEntityEffect(this, (byte) 1);
			}

			++bm;
		} else if (bn != 0) {
			bm = 0;
			bn = 0;
		}
	}

	@Override
	public RabbitMoveController getControllerMove() {
		return (RabbitMoveController) super.getControllerMove();
	}

	@Override
	public void bp() {
		if (getControllerMove().b() > 0.8D) {
			setMoveType(MoveType.SPRINT);
		} else if (move != MoveType.ATTACK) {
			setMoveType(MoveType.HOP);
		}

		if (bq > 0) {
			--bq;
		}

		if (moreCarrotTicks > 0) {
			moreCarrotTicks -= random.nextInt(3);
			if (moreCarrotTicks < 0) {
				moreCarrotTicks = 0;
			}
		}

		if (onGround) {
			if (!bp) {
				this.a(false, MoveType.NONE);
				cu();
			}

			if ((getRabbitType() == 99) && (bq == 0)) {
				EntityLiving target = getGoalTarget();
				if ((target != null) && (Utilities.getDistanceSqToEntity(this, target) < 16.0D)) {
					this.a(target.locX, target.locZ);
					getControllerMove().a(target.locX, target.locY, target.locZ, getControllerMove().b());
					this.b(MoveType.ATTACK);
					bp = true;
				}
			}

			RabbitJumpController jumpController = (RabbitJumpController) getControllerJump();
			if (!jumpController.c()) {
				if (getControllerMove().a() && (bq == 0)) {
					PathEntity var2 = getNavigation().e();
					Vec3D var3 = Vec3D.a(getControllerMove().d(), getControllerMove().e(), getControllerMove().f());
					if ((var2 != null) && (var2.e() < var2.d())) {
						var3 = var2.a(this);
					}

					this.a(var3.a, var3.c);
					this.b(move);
				}
			} else if (!jumpController.d()) {
				cr();
			}
		}

		bp = onGround;
	}

	public void b(double var1) {
		getNavigation().a(var1);
		getControllerMove().a(getControllerMove().d(), getControllerMove().e(), getControllerMove().f(), var1);
	}

	public void a(boolean var1, MoveType var2) {
		super.f(var1);
		if (!var1) {
			if (move == MoveType.ATTACK) {
				move = MoveType.HOP;
			}
		} else {
			this.b(1.5D * var2.a());
			makeSound(ck(), bf(), (((random.nextFloat() - random.nextFloat()) * 0.2F) + 1.0F) * 0.8F);
		}

		bo = var1;
	}

	public void b(MoveType move) {
		this.a(true, move);
		bn = move.d();
		bm = 0;
	}

	public boolean cj() {
		return bo;
	}

	protected String ck() {
		return "mob.rabbit.hop";
	}

	@Override
	protected String t() {
		return "mob.rabbit.idle";
	}

	@Override
	protected String aT() {
		return "mob.rabbit.hurt";
	}

	@Override
	protected String aU() {
		return "mob.rabbit.death";
	}

	@Override
	protected void a(int i, int j, int k, Block block) {
		makeSound("mob.rabbit.hop", 0.15F, 1.0F);
	}

	@Override
	public boolean a(EntityHuman entityhuman) {
		return super.a(entityhuman);
	}

	@Override
	protected Item getLoot() {
		return isBurning() ? Carbon.injector().cookedRabbitItem : Carbon.injector().rabbitItem;
	}

	@Override
	protected void getRareDrop(int i) {
		this.a(Carbon.injector().rabbitFootItem, 1);
	}

	@Override
	protected void dropDeathLoot(boolean flag, int i) {
		int var3 = random.nextInt(2) + random.nextInt(1 + i);
		int var4;

		for (var4 = 0; var4 < var3; ++var4) {
			this.a(Carbon.injector().rabbitHideItem, 1);
		}

		var3 = random.nextInt(2);

		for (var4 = 0; var4 < var3; ++var4) {
			if (isBurning()) {
				this.a(Carbon.injector().cookedRabbitItem, 1);
			} else {
				this.a(Carbon.injector().rabbitItem, 1);
			}
		}
	}

	@Override
	public boolean c(ItemStack itemstack) {
		return (itemstack != null) && (itemstack.getItem() == Items.CARROT);
	}

	public EntityRabbit createRabbitChild(EntityAgeable entityageable) {
		EntityRabbit entity = new EntityRabbit(world);
		entity.setParent((EntityRabbit) entityageable);
		if (entity instanceof EntityRabbit) {
			entity.setRabbitType(getRabbitType());
		}
		return entity;
	}

	public void setRabbitType(int rabbitType) {
		if (rabbitType == TYPE_KILLER) {
			targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
			targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityPlayer.class, 1, true));
			targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityWolf.class, 1, true));

			if (!hasCustomName()) {
				setCustomName("The Killer Bunny");
				setCustomNameVisible(true);
			}
		}

		datawatcher.watch(18, (byte) rabbitType);
	}

	public int getRabbitType() {
		return datawatcher.getByte(18);
	}

	public void setParent(EntityRabbit parent) {
		this.parent = parent;
	}

	public EntityRabbit getParent() {
		return parent;
	}

	public boolean hasParent() {
		return parent != null;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return createRabbitChild(entityageable);
	}

	private Rabbit bukkitEntity;

	@Override
	public CraftEntity getBukkitEntity() {
		if (bukkitEntity == null) {
			bukkitEntity = new Rabbit(MinecraftServer.getServer().server, this);
		}
		return bukkitEntity;
	}

	public static enum MoveType {
		NONE("NONE", 0, 0.0F, 0.0F, 30, 1),
		HOP("HOP", 1, 0.8F, 0.2F, 20, 10),
		STEP("STEP", 2, 1.0F, 0.45F, 14, 14),
		SPRINT("SPRINT", 3, 1.75F, 0.4F, 1, 8),
		ATTACK("ATTACK", 4, 2.0F, 0.7F, 7, 8);
		private final float f;
		private final float g;
		private final int h;
		private final int i;

		private MoveType(String str, int i, float p3, float p4, int p5, int p6) {
			f = p3;
			g = p4;
			h = p5;
			this.i = p6;
		}

		public float a() {
			return f;
		}

		public float b() {
			return g;
		}

		public int c() {
			return h;
		}

		public int d() {
			return i;
		}
	}

}
