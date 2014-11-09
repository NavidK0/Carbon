package com.lastabyss.carbon.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;

import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.DamageSource;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityMonster;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.EntitySquid;
import net.minecraft.server.v1_7_R4.EnumDifficulty;
import net.minecraft.server.v1_7_R4.EnumGamemode;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.IEntitySelector;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.MobEffect;
import net.minecraft.server.v1_7_R4.MobEffectList;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_7_R4.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_7_R4.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_7_R4.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_7_R4.World;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.ai.PathfinderGoalNewRandomStroll;
import com.lastabyss.carbon.ai.guardian.GuardianLookController;
import com.lastabyss.carbon.ai.guardian.GuardianMoveController;
import com.lastabyss.carbon.ai.guardian.GuardianNavigation;
import com.lastabyss.carbon.ai.guardian.PathfinderGoalGuardianAttack;
import com.lastabyss.carbon.entity.bukkit.Guardian;
import com.lastabyss.carbon.utils.Utilities;

/**
 *
 * @author Navid
 */
public class EntityGuardian extends EntityMonster {

	private PathfinderGoalNewRandomStroll stroll;

	private boolean elder = false;

	public EntityGuardian(World world) {
		super(world);
		this.a(0.85F, 0.85F);
		this.b = 10;
		// Add pathfinding goals
		stroll = new PathfinderGoalNewRandomStroll(this, 1.0D, 80);
		goalSelector.a(4, new PathfinderGoalGuardianAttack(this, stroll));
		PathfinderGoalMoveTowardsRestriction movetowards = new PathfinderGoalMoveTowardsRestriction(this, 1.0D);
		goalSelector.a(5, movetowards);
		goalSelector.a(7, stroll);
		goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityGuardian.class, 12.0F, 0.01F));
		goalSelector.a(9, new PathfinderGoalRandomLookaround(this));
		movetowards.a(3);
		stroll.setMutexBits(3);
		targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityLiving.class, 10, true, false, new IEntitySelector() {
			@Override
			public boolean a(Entity entitylivingObj) {
				EntityLiving entityliving = (EntityLiving) entitylivingObj;
				return ((entityliving instanceof EntityHuman) || (entityliving instanceof EntitySquid)) && (Utilities.getDistanceSqToEntity(entityliving, EntityGuardian.this) > 9.0D);
			}
		}));
		try {
			Utilities.setAccessible(Field.class, EntityInsentient.class.getDeclaredField("lookController"), true).set(this, new GuardianLookController(this));
			Utilities.setAccessible(Field.class, EntityInsentient.class.getDeclaredField("navigation"), true).set(this, new GuardianNavigation(this, this.world));
			Utilities.setAccessible(Field.class, EntityInsentient.class.getDeclaredField("moveController"), true).set(this, new GuardianMoveController(this));
		} catch (Exception e) {
			Carbon.log.info("[Carbon] Failed to set guardian look&move controller & navigation: " + e.getMessage());
		}
	}

	// entityInit
	@Override
	protected void c() {
		super.c();
		datawatcher.a(16, 0);
		datawatcher.a(17, 0);
	}

	protected void addData(int data, boolean add) {
		int prevvalue = datawatcher.getInt(16);
		if (add) {
			datawatcher.watch(16, prevvalue | data);
		} else {
			datawatcher.watch(16, prevvalue & ~data);
		}
	}

	public void addData2(boolean add) {
		this.addData(2, add);
	}

	// applyEntityAttributes()
	@Override
	protected void aD() {
		super.aD();
		if (!isElder()) {
			// Attack damage
			getAttributeInstance(GenericAttributes.e).setValue(6.0D);
			// Movement Speed
			getAttributeInstance(GenericAttributes.d).setValue(0.5D);
			// Follow range
			getAttributeInstance(GenericAttributes.b).setValue(16.0D);
			// Max health, obviously
			getAttributeInstance(GenericAttributes.maxHealth).setValue(30.0D);
		} else {
			// Attack damage
			getAttributeInstance(GenericAttributes.e).setValue(8.0D);
			// Movement Speed
			getAttributeInstance(GenericAttributes.d).setValue(0.30000001192092896D);
			// Max health, obviously
			getAttributeInstance(GenericAttributes.maxHealth).setValue(80.0D);

			this.a(1.9975F, 1.9975F);
			ak = true;
		}
	}

	// getLivingSound
	@Override
	protected String t() {
		return !M() ? "mob.guardian.land.idle" : (isElder() ? "mob.guardian.elder.idle" : "mob.guardian.idle");
	}

	// getHurtSound
	@Override
	protected String aT() {
		return !M() ? "mob.guardian.land.hit" : (isElder() ? "mob.guardian.elder.hit" : "mob.guardian.hit");
	}

	// getDeathSound
	@Override
	protected String aU() {
		return !M() ? "mob.guardian.land.death" : (isElder() ? "mob.guardian.elder.death" : "mob.guardian.death");
	}

	@Override
	protected Item getLoot() {
		return isBurning() ? Items.COOKED_FISH : Items.RAW_FISH;
	}

	// canTriggerWalking
	@Override
	protected boolean g_() {
		return false;
	}

	@Override
	protected void dropDeathLoot(boolean flag, int i) {
		int var3 = random.nextInt(3) + random.nextInt(i + 1);

		if (var3 > 0) {
			this.a(new ItemStack(Carbon.injector().prismarineShardItem, var3, 0), 1.0F);
		}

		if (random.nextInt(3 + i) > 1) {
			if (!isBurning()) {
				this.a(new ItemStack(Items.RAW_FISH, 1, 0), 1.0F);
			} else {
				this.a(new ItemStack(Items.RAW_FISH, 1, 0), 1.0F);
			}
		} else if (random.nextInt(3 + i) > 1) {
			this.a(new ItemStack(Carbon.injector().prismarineCrystalItem, 1, 0), 1.0F);
		}

		if (flag && isElder()) {
			this.a(new ItemStack(Blocks.SPONGE, 1, 1), 1.0F);
		}
	}

	private boolean isDataSet(int data) {
		return (datawatcher.getInt(16) & data) != 0;
	}

	public boolean isData2Set() {
		return isDataSet(2);
	}

	@Override
	public boolean bk() {
		return true;
	}

	@Override
	public void e() {
		if (M()) {
			this.setAirTicks(300);
		} else if (onGround) {
			motY += 0.5D;
			motX += ((random.nextFloat() * 2.0F) - 1.0F) * 0.4F;
			motZ += ((random.nextFloat() * 2.0F) - 1.0F) * 0.4F;
			yaw = random.nextFloat() * 360.0F;
			onGround = false;
			al = true;
		}

		if (hasBeamTarget()) {
			yaw = aO;
		}

		super.e();
	}

	@Override
	public boolean damageEntity(DamageSource source, float damage) {
		if (!this.isData2Set() && !source.isMagic() && (source.getEntity() instanceof EntityLiving)) {
			EntityLiving damager = (EntityLiving) source.getEntity();
			if (!source.isExplosion()) {
				damager.damageEntity(DamageSource.a(this), 2.0F);
				damager.makeSound("damage.thorns", 0.5F, 1.0F);
			}
		}
		stroll.force();
		return super.damageEntity(source, damage);
	}

	public boolean hasBeamTarget() {
		return datawatcher.getInt(17) != 0;
	}

	public void updateBeamTarget(int id) {
		datawatcher.watch(17, id);
	}

	public EntityLiving getBeamTarget() {
		if (!hasBeamTarget()) {
			return null;
		}
		return getGoalTarget();
	}

	@Override
	public void i(int id) {
		super.i(id);
		if (id == 16) {
			if (isElder() && (width < 1.0F)) {
				a(1.9975F, 1.9975F);
			}
		}
	}

	// getTalkInterval
	@Override
	public int q() {
		return 160;
	}

	@Override
	public boolean j_() {
		return true;
	}

	@Override
	public void e(float f1, float f2) {
		if (M()) {
			this.a(f1, f2, 0.1F);
			move(motX, motY, motZ);
			motX *= 0.8999999761581421D;
			motY *= 0.8999999761581421D;
			motZ *= 0.8999999761581421D;
			if (!this.isData2Set() && (getGoalTarget() == null)) {
				motY -= 0.005D;
			}
		} else {
			super.e(f1, f2);
		}
	}

	@Override
	protected void bp() {
		super.bp();
		if (isElder()) {
			if (((ticksLived + getId()) % 1200) == 0) {
				MobEffectList effect = MobEffectList.SLOWER_DIG;
				ArrayList<EntityPlayer> players = new ArrayList<EntityPlayer>();
				for (Object obj : world.players) {
					EntityPlayer worldplayer = (EntityPlayer) obj;
					if ((worldplayer.playerInteractManager.getGameMode() == EnumGamemode.SURVIVAL || worldplayer.playerInteractManager.getGameMode() == EnumGamemode.ADVENTURE) && Utilities.getDistanceSqToEntity(worldplayer, this) < 2500.0D) {
						players.add(worldplayer);
					}
				}
				for (EntityPlayer player : players) {
					if (!player.hasEffect(effect) || (player.getEffect(effect).getAmplifier() < 2) || (player.getEffect(effect).getDuration() < 1200)) {
						player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(10, 0.0F));
						player.addEffect(new MobEffect(effect.id, 6000, 2));
					}
				}
			}

			if (!bY()) {
				this.a(MathHelper.floor(locX), MathHelper.floor(locY), MathHelper.floor(locZ), 16);
			}
		}
	}

	// readEntityFromNBT
	@Override
	public void a(NBTTagCompound tagCompound) {
		super.a(tagCompound);
		setElder(tagCompound.getBoolean("Elder"));
	}

	// writeEntityFromNBT
	@Override
	public void b(NBTTagCompound tagCompound) {
		super.b(tagCompound);
		tagCompound.setBoolean("Elder", isElder());
	}

	public boolean isElder() {
		return elder;
	}

	public void setElder(boolean elder) {
		this.elder = elder;
		addData(4, elder);
	}

	@Override
	public float a(int x, int y, int z) {
		return world.getType(x, y, z).getMaterial() == Material.WATER ? (10.0F + world.b(x, y, z)) - 0.5F : super.a(x, y, z);
	}

	@Override
	public boolean canSpawn() {
        final int i = MathHelper.floor(this.locX);
        final int j = MathHelper.floor(this.boundingBox.b);
        final int k = MathHelper.floor(this.locZ);
		return
			this.world.difficulty != EnumDifficulty.PEACEFUL &&
			this.a(i, j, k) >= 0.0f && 
			world.a(boundingBox, this) && 
			world.getCubes(this, boundingBox).isEmpty() &&
			random.nextInt(20) == 0;
	}

	private Guardian bukkitEntity;

	@Override
	public CraftEntity getBukkitEntity() {
		if (bukkitEntity == null) {
			bukkitEntity = new Guardian(MinecraftServer.getServer().server, this);
		}
		return bukkitEntity;
	}

	

}
