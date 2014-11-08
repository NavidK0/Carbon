package com.lastabyss.carbon.entity;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;

import com.lastabyss.carbon.ai.PathfinderGoalAttackOnCollide;
import com.lastabyss.carbon.ai.PathfinderGoalNewRandomStroll;
import com.lastabyss.carbon.entity.bukkit.Endermite;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityMonster;
import net.minecraft.server.v1_7_R4.EnumMonsterType;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.PathfinderGoalFloat;
import net.minecraft.server.v1_7_R4.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_7_R4.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_7_R4.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_7_R4.World;

/**
 * These things are god awful.
 * 
 * @author Navid
 */
public class EntityEndermite extends EntityMonster {

	private int ticksLived = 0;
	private boolean playerSpawned = false;

	public EntityEndermite(World world) {
		super(world);
		a(0.3F, 0.7F);
		this.b = 3;
		this.goalSelector.a(1, new PathfinderGoalFloat(this));
		this.goalSelector.a(2, new PathfinderGoalAttackOnCollide(this, EntityHuman.class, 1.0D, false));
		this.goalSelector.a(3, new PathfinderGoalNewRandomStroll(this, 1.0D));
		this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
		this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 10, true));
	}

	public void spawn(Location l) {
		this.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getPitch(), l.getYaw());
		this.playerSpawned = true;
		((CraftWorld) l.getWorld()).getHandle().addEntity(this);
	}

	@Override
	protected void aD() {
		super.aD();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
		this.getAttributeInstance(GenericAttributes.d).setValue(0.25D);
		this.getAttributeInstance(GenericAttributes.e).setValue(2.0D);
	}

	// read
	@Override
	public void a(NBTTagCompound tag) {
		super.a(tag);
		this.ticksLived = tag.getInt("Lifetime");
		this.playerSpawned = tag.getBoolean("PlayerSpawned");
	}

	// write
	@Override
	public void b(NBTTagCompound tag) {
		super.b(tag);
		tag.setInt("Lifetime", this.ticksLived);
		tag.setBoolean("PlayerSpawned", this.playerSpawned);
	}

	@Override
	public boolean bk() {
		return true;
	}

	@Override
	public float getHeadHeight() {
		return 0.1F;
	}

	@Override
	protected boolean g_() {
		return false;
	}

	@Override
	protected String t() {
		return "mob.silverfish.say";
	}

	@Override
	protected String aT() {
		return "mob.silverfish.hit";
	}

	@Override
	protected String aU() {
		return "mob.silverfish.kill";
	}

	@Override
	protected void a(int x, int y, int z, Block block) {
		this.makeSound("mob.silverfish.step", 0.15F, 1.0F);
	}

	@Override
	protected Item getLoot() {
		return null;
	}

	@Override
	public void h() {
		this.aM = this.yaw;
		super.h();
	}

	@Override
	public void e() {
		super.e();
		if (!this.persistent) {
			++this.ticksLived;
		}

		if (this.ticksLived >= 2400) {
			this.die();
		}
	}

	@Override
	protected boolean j_() {
		return true;
	}

	@Override
	public boolean canSpawn() {
		if (super.canSpawn()) {
			EntityHuman entityhuman = this.world.findNearbyPlayer(this, 5.0D);
			return entityhuman == null;
		} else {
			return false;
		}
	}

	@Override
	public EnumMonsterType getMonsterType() {
		return EnumMonsterType.ARTHROPOD;
	}

	private Endermite bukkitEntity;

	@Override
	public CraftEntity getBukkitEntity() {
		if (bukkitEntity == null) {
			bukkitEntity = new Endermite(MinecraftServer.getServer().server, this);
		}
		return bukkitEntity;
	}

}