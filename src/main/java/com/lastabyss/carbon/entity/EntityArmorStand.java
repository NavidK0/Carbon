package com.lastabyss.carbon.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.entity.bukkit.ArmorStand;
import com.lastabyss.carbon.utils.Utilities;

import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.DamageSource;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityArrow;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityItem;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityMinecartRideable;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemArmor;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;
import net.minecraft.server.v1_7_R4.Vec3D;
import net.minecraft.server.v1_7_R4.WatchableObject;
import net.minecraft.server.v1_7_R4.World;

public class EntityArmorStand extends EntityLiving {

	//IMPORTANT: IT NEEDS TO BE NAMED EntityArmorStand so EntityTracker can track this entity

	private static final ArmorStandPose defaultHeadPose = new ArmorStandPose(0.0F, 0.0F, 0.0F);
	private static final ArmorStandPose defaultBodyPose = new ArmorStandPose(0.0F, 0.0F, 0.0F);
	private static final ArmorStandPose defaultLeftArmPose = new ArmorStandPose(-10.0F, 0.0F, -10.0F);
	private static final ArmorStandPose defaultRightArmPose = new ArmorStandPose(-15.0F, 0.0F, 10.0F);
	private static final ArmorStandPose defaultLeftLegPose = new ArmorStandPose(-1.0F, 0.0F, -1.0F);
	private static final ArmorStandPose defaultRightLegPose = new ArmorStandPose(1.0F, 0.0F, 1.0F);
	private final ItemStack[] equipment = new ItemStack[5];
	private boolean invisible;
	private long lastDamageTime;
	private int disabledSlots;

	public EntityArmorStand(World world) {
		super(world);
		this.X = this.isNoGravity();
		this.a(0.5F, 1.975F);
	}

	public EntityArmorStand(World world, double x, double y, double z) {
		this(world);
		this.setPosition(x, y, z);
	}

	@Override
	protected void c() {
		super.c();
		this.datawatcher.a(10, (byte) 0);
		this.datawatcher.a(11, defaultHeadPose);
		this.datawatcher.a(12, defaultBodyPose);
		this.datawatcher.a(13, defaultLeftArmPose);
		this.datawatcher.a(14, defaultRightArmPose);
		this.datawatcher.a(15, defaultLeftLegPose);
		this.datawatcher.a(16, defaultRightLegPose);
	}

	@Override
	public ItemStack getEquipment(int slot) {
		return this.equipment[slot];
	}

	@Override
	public ItemStack be() {
		return this.equipment[0];
	}

	@Override
	public ItemStack[] getEquipment() {
		return this.equipment;
	}

	@Override
	public void setEquipment(int slot, ItemStack itemStack) {
		this.equipment[slot] = itemStack;
	}

	/*@Override
	 * has something to do with replace item command
	public boolean d(int var1, ItemStack var2) {
		int var3;
		if (var1 == 99) {
			var3 = 0;
		} else {
			var3 = var1 - 100 + 1;
			if (var3 < 0 || var3 >= this.equipment.length) {
				return false;
			}
		}

		if (var2 != null && EntityInsentient.c(var2) != var3 && (var3 != 4 || !(var2.getItem() instanceof ItemBlock))) {
			return false;
		} else {
			this.setEquimpent(var3, var2);
			return true;
		}
	}*/

	@Override
	public void b(NBTTagCompound tag) {
		super.b(tag);
		NBTTagList equipment = new NBTTagList();

		for (int i = 0; i < this.equipment.length; ++i) {
			NBTTagCompound etag = new NBTTagCompound();
			if (this.equipment[i] != null) {
				this.equipment[i].save(etag);
			}

			equipment.add(etag);
		}

		tag.set("Equipment", equipment);
		//if (this.isCustomNameVisible() && (this.getCustomName() == null || this.getCustomName().length() == 0)) {
		//	tag.put("CustomNameVisible", this.isCustomNameVisible());
		//}

		tag.setBoolean("Invisible", this.isInvisible());
		tag.setBoolean("Small", this.isSmall());
		tag.setBoolean("ShowArms", this.showArms());
		tag.setInt("DisabledSlots", this.disabledSlots);
		tag.setBoolean("NoGravity", this.isNoGravity());
		tag.setBoolean("NoBasePlate", this.isNoBasePlate());
		tag.set("Pose", this.getPose());
	}

	@Override
	public void a(NBTTagCompound tag) {
		super.a(tag);
		if (tag.hasKeyOfType("Equipment", 9)) {
			NBTTagList equipment = tag.getList("Equipment", 10);

			for (int i = 0; i < this.equipment.length; ++i) {
				this.equipment[i] = ItemStack.createStack(equipment.get(i));
			}
		}

		this.setInvisible(tag.getBoolean("Invisible"));
		this.setSmall(tag.getBoolean("Small"));
		this.setShowArms(tag.getBoolean("ShowArms"));
		this.disabledSlots = tag.getInt("DisabledSlots");
		this.setNoGravity(tag.getBoolean("NoGravity"));
		this.setNoBasePlate(tag.getBoolean("NoBasePlate"));
		this.X = this.isNoGravity();
		NBTTagCompound posetag = tag.getCompound("Pose");
		this.setPose(posetag);
	}

	private void setPose(NBTTagCompound tag) {
		NBTTagList head = tag.getList("Head", 5);
		if (head.size() > 0) {
			this.setHeadPose(new ArmorStandPose(head));
		} else {
			this.setHeadPose(defaultHeadPose);
		}

		NBTTagList body = tag.getList("Body", 5);
		if (body.size() > 0) {
			this.setBodyPose(new ArmorStandPose(body));
		} else {
			this.setBodyPose(defaultBodyPose);
		}

		NBTTagList leftarm = tag.getList("LeftArm", 5);
		if (leftarm.size() > 0) {
			this.setLeftArmPose(new ArmorStandPose(leftarm));
		} else {
			this.setLeftArmPose(defaultLeftArmPose);
		}

		NBTTagList rightarm = tag.getList("RightArm", 5);
		if (rightarm.size() > 0) {
			this.setRightArmPose(new ArmorStandPose(rightarm));
		} else {
			this.setRightArmPose(defaultRightArmPose);
		}

		NBTTagList leftleg = tag.getList("LeftLeg", 5);
		if (leftleg.size() > 0) {
			this.setLeftLegPose(new ArmorStandPose(leftleg));
		} else {
			this.setLeftLegPose(defaultLeftLegPose);
		}

		NBTTagList rightleg = tag.getList("RightLeg", 5);
		if (rightleg.size() > 0) {
			this.setRightLegPose(new ArmorStandPose(rightleg));
		} else {
			this.setRightLegPose(defaultRightLegPose);
		}

	}

	private NBTTagCompound getPose() {
		NBTTagCompound pose = new NBTTagCompound();

		ArmorStandPose head = getHeadPose();
		if (!defaultHeadPose.equals(head)) {
			pose.set("Head", head.asNBTList());
		}

		ArmorStandPose body = getHeadPose();
		if (!defaultBodyPose.equals(body)) {
			pose.set("Body", body.asNBTList());
		}

		ArmorStandPose leftarm = getHeadPose();
		if (!defaultLeftArmPose.equals(leftarm)) {
			pose.set("LeftArm", leftarm.asNBTList());
		}

		ArmorStandPose rightarm = getHeadPose();
		if (!defaultRightArmPose.equals(rightarm)) {
			pose.set("RightArm", rightarm.asNBTList());
		}

		ArmorStandPose leftleg = getHeadPose();
		if (!defaultLeftLegPose.equals(leftleg)) {
			pose.set("LeftLeg", leftleg.asNBTList());
		}

		ArmorStandPose rightleg = getHeadPose();
		if (!defaultRightLegPose.equals(rightleg)) {
			pose.set("RightLeg", rightleg.asNBTList());
		}

		return pose;
	}

	@Override
	public boolean S() {
		return false;
	}

	@Override
	protected void o(Entity var1) {
	}

	@Override
	protected void bo() {
		@SuppressWarnings("unchecked")
		List<Entity> list = this.world.getEntities(this, this.boundingBox);
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); ++i) {
				Entity entity = (Entity) list.get(i);
				if (entity instanceof EntityMinecartRideable && Utilities.getDistanceSqToEntity(this, entity) <= 0.2D) {
					entity.collide(this);
				}
			}
		}
	}

	public boolean interactAt(EntityHuman human, Vec3D at) {
		if (!this.world.isStatic) {
			byte newslot = 0;
			ItemStack iteminhand = human.be();
			boolean hasItemInHand = iteminhand != null;
			if (hasItemInHand && iteminhand.getItem() instanceof ItemArmor) {
				ItemArmor itemArmor = (ItemArmor) iteminhand.getItem();
				if (itemArmor.b == 3) {
					newslot = 1;
				} else if (itemArmor.b == 2) {
					newslot = 2;
				} else if (itemArmor.b == 1) {
					newslot = 3;
				} else if (itemArmor.b == 0) {
					newslot = 4;
				}
			}

			if (hasItemInHand && (iteminhand.getItem() == Items.SKULL || iteminhand.getItem() == Item.getItemOf(Blocks.PUMPKIN))) {
				newslot = 4;
			}

			byte slot = 0;
			boolean isSmall = this.isSmall();
			double interactY = isSmall ? at.b * 2.0D : at.b;
			if (interactY >= 0.1D && interactY < 0.1D + (isSmall ? 0.8D : 0.45D) && this.equipment[1] != null) {
				slot = 1;
			} else if (interactY >= 0.9D + (isSmall ? 0.3D : 0.0D) && interactY < 0.9D + (isSmall ? 1.0D : 0.7D) && this.equipment[3] != null) {
				slot = 3;
			} else if (interactY >= 0.4D && interactY < 0.4D + (isSmall ? 1.0D : 0.8D) && this.equipment[2] != null) {
				slot = 2;
			} else if (interactY >= 1.6D && this.equipment[4] != null) {
				slot = 4;
			}

			boolean alreadyHasEquipment = this.equipment[slot] != null;
			if ((this.disabledSlots & 1 << slot) != 0 || (this.disabledSlots & 1 << newslot) != 0) {
				slot = newslot;
				if ((this.disabledSlots & 1 << newslot) != 0) {
					if ((this.disabledSlots & 1) != 0) {
						return true;
					}

					slot = 0;
				}
			}

			if (hasItemInHand && newslot == 0 && !this.showArms()) {
				return true;
			} else {
				if (hasItemInHand) {
					this.setEquipment(human, newslot);
				} else if (alreadyHasEquipment) {
					this.setEquipment(human, slot);
				}

				return true;
			}
		} else {
			return true;
		}
	}

	private void setEquipment(EntityHuman human, int slot) {
		ItemStack itemStackInSlot = this.equipment[slot];
		if (itemStackInSlot == null || (this.disabledSlots & 1 << slot + 8) == 0) {
			if (itemStackInSlot != null || (this.disabledSlots & 1 << slot + 16) == 0) {
				int itemInHandIndex = human.inventory.itemInHandIndex;
				ItemStack itemInHand = human.inventory.getItem(itemInHandIndex);
				ItemStack equipmentItemStack;
				if (human.abilities.canInstantlyBuild && (itemStackInSlot == null || itemStackInSlot.getItem() == Item.getItemOf(Blocks.AIR)) && itemInHand != null) {
					equipmentItemStack = itemInHand.cloneItemStack();
					equipmentItemStack.count = 1;
					this.setEquipment(slot, equipmentItemStack);
				} else if (itemInHand != null && itemInHand.count > 1) {
					if (itemStackInSlot == null) {
						equipmentItemStack = itemInHand.cloneItemStack();
						equipmentItemStack.count = 1;
						this.setEquipment(slot, equipmentItemStack);
						--itemInHand.count;
					}
				} else {
					this.setEquipment(slot, itemInHand);
					human.inventory.setItem(itemInHandIndex, itemStackInSlot);
				}
			}
		}
	}

	@Override
	public boolean damageEntity(DamageSource source, float damage) {
		if (!this.invisible) {
			if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, source, damage)) {
				return false;
			}
			if (DamageSource.OUT_OF_WORLD.equals(source)) {
				this.die();
				return false;
			} else if (this.isInvulnerable() && source != DamageSource.OUT_OF_WORLD && !isDamageSourceInstantlyDestroys(source.getEntity())) {
				return false;
			} else if (source.isExplosion()) {
				this.dropEquipment();
				this.die();
				return false;
			} else if (DamageSource.FIRE.equals(source)) {
				if (!this.isBurning()) {
					this.setOnFire(5);
				} else {
					this.receiveDamage(0.15F);
				}

				return false;
			} else if (DamageSource.BURN.equals(source) && this.getHealth() > 0.5F) {
				this.receiveDamage(4.0F);
				return false;
			} else {
				boolean isArrow = "arrow".equals(source.translationIndex);
				boolean isPlayer = "player".equals(source.translationIndex);
				if (!isPlayer && !isArrow) {
					return false;
				} else {
					if (source.getEntity() instanceof EntityArrow) {
						source.getEntity().die();
					}

					if (source.getEntity() instanceof EntityHuman && !((EntityHuman) source.getEntity()).abilities.mayBuild) {
						return false;
					} else if (isDamageSourceInstantlyDestroys(source.getEntity())) {
						this.spawnParticles();
						this.die();
						return false;
					} else {
						long time = this.world.getTime();
						if (time - this.lastDamageTime > 5L && !isArrow) {
							this.lastDamageTime = time;
						} else {
							this.drop();
							this.spawnParticles();
							this.die();
						}

						return false;
					}
				}
			}
		} else {
			return false;
		}
	}

	private boolean isDamageSourceInstantlyDestroys(Entity entity) {
		return entity instanceof EntityHuman && ((EntityHuman) entity).abilities.canInstantlyBuild;
	}

	private void spawnParticles() {
		/*if (this.world instanceof WorldServer) {
			((WorldServer) this.world).spawnParticle(, this.locX, this.locY + (double) this.width / 1.5D, this.locZ, 10, (double) (this.height / 4.0F), (double) (this.width / 4.0F), (double) (this.height / 4.0F), 0.05D, new int[] { Block.getId(Blocks.WOOD) });
		}*/
	}

	private void receiveDamage(float damage) {
		float health = this.getHealth();
		health -= damage;
		if (health <= 0.5F) {
			this.dropEquipment();
			this.die();
		} else {
			this.setHealth(health);
		}

	}

	private void drop() {
		dropItem(locX, locY, locZ, new ItemStack(Carbon.injector().armorStandItem));
		this.dropEquipment();
	}

	private void dropEquipment() {
		for (int i = 0; i < this.equipment.length; ++i) {
			if (this.equipment[i] != null && this.equipment[i].count > 0) {
				if (this.equipment[i] != null) {
					dropItem(locX, locY + 1, locZ, this.equipment[i]);
				}

				this.equipment[i] = null;
			}
		}

	}

	private void dropItem(double locX, double locY, double locZ, ItemStack itemStack) {
		EntityItem item = new EntityItem(world, locX, locY, locZ, itemStack);
		item.pickupDelay = 10;
		world.addEntity(item);
	}

	@Override
	protected float f(float var1, float var2) {
		this.aN = this.lastYaw;
		this.aM = this.yaw;
		return 0.0F;
	}

	@Override
	public float getHeadHeight() {
		return this.isBaby() ? this.width * 0.5F : this.width * 0.9F;
	}

	@Override
	public void e(float var1, float var2) {
		if (!this.isNoGravity()) {
			super.e(var1, var2);
		}
	}

	@Override
	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
		super.setInvisible(invisible);
	}

	@Override
	public boolean isBaby() {
		return this.isSmall();
	}

	private void setSmall(boolean small) {
		byte prevstate = this.datawatcher.getByte(10);
		if (small) {
			prevstate = (byte) (prevstate | 1);
		} else {
			prevstate &= -2;
		}

		this.datawatcher.watch(10, prevstate);
	}

	public boolean isSmall() {
		return (this.datawatcher.getByte(10) & 1) != 0;
	}

	private void setNoGravity(boolean nogravity) {
		byte prevstate = this.datawatcher.getByte(10);
		if (nogravity) {
			prevstate = (byte) (prevstate | 2);
		} else {
			prevstate &= -3;
		}

		this.datawatcher.watch(10, prevstate);
	}

	public boolean isNoGravity() {
		return (this.datawatcher.getByte(10) & 2) != 0;
	}

	private void setShowArms(boolean showArms) {
		byte prevstate = this.datawatcher.getByte(10);
		if (showArms) {
			prevstate = (byte) (prevstate | 4);
		} else {
			prevstate &= -5;
		}

		this.datawatcher.watch(10, prevstate);
	}

	public boolean showArms() {
		return (this.datawatcher.getByte(10) & 4) != 0;
	}

	private void setNoBasePlate(boolean nobaseplate) {
		byte prevstate = this.datawatcher.getByte(10);
		if (nobaseplate) {
			prevstate = (byte) (prevstate | 8);
		} else {
			prevstate &= -9;
		}

		this.datawatcher.watch(10, prevstate);
	}

	public boolean isNoBasePlate() {
		return (this.datawatcher.getByte(10) & 8) != 0;
	}

	public void setHeadPose(ArmorStandPose pose) {
		this.datawatcher.watch(11, pose);
	}

	public ArmorStandPose getHeadPose() {
		try {
			return getArmorStandPose(11);
		} catch (Exception e) {
			return defaultHeadPose;
		}
	}

	public void setBodyPose(ArmorStandPose pose) {
		this.datawatcher.watch(12, pose);
	}

	public ArmorStandPose getBodyPose() {
		try {
			return getArmorStandPose(12);
		} catch (Exception e) {
			return defaultBodyPose;
		}
	}

	public void setLeftArmPose(ArmorStandPose pose) {
		this.datawatcher.watch(13, pose);
	}

	public ArmorStandPose getLeftArmPose() {
		try {
			return getArmorStandPose(13);
		} catch (Exception e) {
			return defaultLeftArmPose;
		}
	}

	public void setRightArmPose(ArmorStandPose pose) {
		this.datawatcher.watch(14, pose);
	}

	public ArmorStandPose getRightArmPose() {
		try {
			return getArmorStandPose(14);
		} catch (Exception e) {
			return defaultRightArmPose;
		}
	}

	public void setLeftLegPose(ArmorStandPose pose) {
		this.datawatcher.watch(15, pose);
	}

	public ArmorStandPose getLeftLegPose() {
		try {
			return getArmorStandPose(15);
		} catch (Exception e) {
			return defaultLeftLegPose;
		}
	}

	public void setRightLegPose(ArmorStandPose pose) {
		this.datawatcher.watch(16, pose);
	}

	public ArmorStandPose getRightLegPose() {
		try {
			return getArmorStandPose(16);
		} catch (Exception e) {
			return defaultRightLegPose;
		}
	}

	private ArmorStandPose getArmorStandPose(int id) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return (ArmorStandPose) ((WatchableObject) Utilities.setAccessible(Method.class, datawatcher.getClass().getDeclaredMethod("i", int.class), true).invoke(datawatcher, id)).b();
	}

    private ArmorStand bukkitEntity;
    @Override
    public CraftEntity getBukkitEntity() {
    	if (bukkitEntity == null) {
    		bukkitEntity = new ArmorStand(MinecraftServer.getServer().server, this); 
    	}
    	return bukkitEntity;
    }

}