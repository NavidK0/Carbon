/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lastabyss.carbon.entity;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.ai.PathfinderWrapper;
import com.lastabyss.carbon.entity.bukkit.Guardian;
import com.lastabyss.carbon.utils.Utilities;

import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityMonster;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.PathfinderGoalFloat;
import net.minecraft.server.v1_7_R4.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_7_R4.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_7_R4.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_7_R4.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_7_R4.World;

/**
 *
 * @author Navid
 */
public class EntityGuardian extends EntityMonster {
    public float bp;
    public float previousSquidPitch;
    public float br;
    public float previousSquidYaw;
    public float bt;
    public float prevSquidRotation;
    public float bv;
    public float lastTentacleRotation;
    private float bx;
    private float by;
    private float bz;
    private float bA;
    private float bB;
    private float bC;
    
    private boolean elder = false;
    private int fire;

    public EntityGuardian(World world) {
        super(world);
        this.a(0.85F, 0.85F);
        this.by = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
        this.expToDrop = 10;
        //Add pathfinding goals
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 10, true));
    }

    //entityInit
    @Override
    protected void c() {
        super.c();
        this.datawatcher.a(16, 0);
        this.datawatcher.a(17, 0);
    }

    @Override
    public void C() {
        super.C();
        this.setAirTicks(300);
    }
    
    
    
    protected void addElderData(int var1, boolean var2) {
        int var3 = this.datawatcher.getInt(16);
        if (var2) {
            this.datawatcher.watch(16, var3 | var1);
        } else {
           this.datawatcher.watch(16, var3 & ~var1);
        }
    }

    //applyEntityAttributes()
    @Override
    protected void aD() {
        super.aD();
        if (!isElder()) {
            //Attack damage
            this.getAttributeInstance(GenericAttributes.e).setValue(6.0D);
            //Movement Speed
            this.getAttributeInstance(GenericAttributes.d).setValue(0.5D);
            //Follow range
            this.getAttributeInstance(GenericAttributes.b).setValue(16.0D);
            //Max health, obviously
            this.getAttributeInstance(GenericAttributes.maxHealth).setValue(30.0D);
        } else {
            //Attack damage
            this.getAttributeInstance(GenericAttributes.e).setValue(8.0D);
            //Movement Speed
            this.getAttributeInstance(GenericAttributes.d).setValue(0.30000001192092896D);
            //Max health, obviously
            this.getAttributeInstance(GenericAttributes.maxHealth).setValue(80.0D);
            
            this.a(1.9975F, 1.9975F);
            this.ak = true;
        }
    }

    //getLivingSound
    @Override
    protected String t() {
        return !this.isInWater() ? "mob.guardian.land.idle" : (this.isElder() ? "mob.guardian.elder.idle" : "mob.guardian.idle");
    }

    //getHurtSound
    @Override
    protected String aT() {
        return !this.isInWater() ? "mob.guardian.land.hit" : (this.isElder() ? "mob.guardian.elder.hit" : "mob.guardian.hit");
    }

    //getDeathSound
    @Override
    protected String aU() {
        return !this.isInWater() ? "mob.guardian.land.death" : (this.isElder() ? "mob.guardian.elder.death" : "mob.guardian.death");
    }

    @Override
    protected Item getLoot() {
        return this.isBurning() ? Items.COOKED_FISH : Items.RAW_FISH;
    }

    //canTriggerWalking
    @Override
    protected boolean g_() {
        return false;
    }

    @Override
    protected void dropDeathLoot(boolean flag, int i) {
        int var3 = this.random.nextInt(3) + this.random.nextInt(i + 1);

        if (var3 > 0)
        { 
            this.a(new ItemStack(Carbon.injector().prismarineShardItem, var3, 0), 1.0F);
        }

        if (this.random.nextInt(3 + i) > 1)
        {
            if (!this.isBurning()) {
                this.a(new ItemStack(Items.RAW_FISH, 1, 0), 1.0F);
            } else {
               this.a(new ItemStack(Items.RAW_FISH, 1, 0), 1.0F); 
            }
        }
        else if (this.random.nextInt(3 + i) > 1)
        {
            this.a(new ItemStack(Carbon.injector().prismarineCrystalItem, 1, 0), 1.0F);
        }

        if (flag && this.isElder())
        {
            this.a(new ItemStack(Blocks.SPONGE, 1, 1), 1.0F);
        }
    }

    //isInWater
    @Override
    public boolean M() {
        return this.world.a(this.boundingBox.grow(0.0D, -0.6000000238418579D, 0.0D), Material.WATER, this);
    }
    
    

    /** 
     onLivingUpdate()
     This is the entity update loop.
     Stupid obfuscation.
    **/
    @Override
    public void e() {
        super.e();
        this.previousSquidPitch = this.bp;
        this.previousSquidYaw = this.br;
        this.prevSquidRotation = this.bt;
        this.lastTentacleRotation = this.bv;
        this.bt += this.by;
        if (this.bt > 6.2831855F) {
            this.bt -= 6.2831855F;
            if (this.random.nextInt(10) == 0) {
                this.by = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
            }
        }

        if (this.M()) {
            float f;

            if (this.bt < 3.1415927F) {
                f = this.bt / 3.1415927F;
                this.bv = MathHelper.sin(f * f * 3.1415927F) * 3.1415927F * 0.25F;
                if ((double) f > 0.75D) {
                    this.bx = 1.0F;
                    this.bz = 1.0F;
                } else {
                    this.bz *= 0.8F;
                }
            } else {
                this.bv = 0.0F;
                this.bx *= 0.9F;
                this.bz *= 0.99F;
            }

            if (!this.world.isStatic) {
                this.motX = (double) (this.bA * this.bx);
                this.motY = (double) (this.bB * this.bx);
                this.motZ = (double) (this.bC * this.bx);
            }

            f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.aM += (-((float) Math.atan2(this.motX, this.motZ)) * 180.0F / 3.1415927F - this.aM) * 0.1F;
            this.yaw = this.aM;
            this.br += 3.1415927F * this.bz * 1.5F;
            this.bp += (-((float) Math.atan2((double) f, this.motY)) * 180.0F / 3.1415927F - this.bp) * 0.1F;
        } else {
            this.bv = MathHelper.abs(MathHelper.sin(this.bt)) * 3.1415927F * 0.25F;
            if (!this.world.isStatic) {
                this.motX = 0.0D;
                this.motY -= 0.08D;
                this.motY *= 0.9800000190734863D;
                this.motZ = 0.0D;
            }

            this.bp = (float) ((double) this.bp + (double) (-90.0F - this.bp) * 0.02D);
        }
    }

    //getTalkInterval
    @Override
    public int q() {
        return 160;
    }
    
    /**
     * Returns if this entity is in water and will end up adding the waters velocity to the entity
    public boolean handleWaterMovement()
    {
        if (this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox().expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D), Material.water, this)) {
            if (!this.inWater && !this.firstUpdate)
            {
                this.resetHeight();
            }

            this.fallDistance = 0.0F;
            this.inWater = true;
            this.fire = 0;
        }
        else
        {
            this.inWater = false;
        }

        return this.inWater;
    }
    **/

    //moveEntityWithHeading/
    @Override
    public void e(float f, float f1) {
        this.move(this.motX, this.motY, this.motZ);
    }

    //Jump helper
    @Override
    protected void bq() {
        ++this.aU;
        if (this.aU > 100) {
            this.bA = this.bB = this.bC = 0.0F;
        } else if (this.random.nextInt(50) == 0 || !this.inWater || this.bA == 0.0F && this.bB == 0.0F && this.bC == 0.0F) {
            float f = this.random.nextFloat() * 3.1415927F * 2.0F;

            this.bA = MathHelper.cos(f) * 0.2F;
            this.bB = -0.1F + this.random.nextFloat() * 0.2F;
            this.bC = MathHelper.sin(f) * 0.2F;
        }

        this.w();
    }

    //We can make a guess as to which one of these is write, and which one of these is read...
    //readEntityFromNBT
    @Override
    public void a(NBTTagCompound tagCompound) {
        super.a(tagCompound);
        this.setElder(tagCompound.getBoolean("Elder"));
    }
    
    //writeEntityFromNBT
    @Override
    public void b(NBTTagCompound tagCompound) {
        super.b(tagCompound);
        tagCompound.setBoolean("Elder", this.isElder());
    }
    
    public boolean isElder(){
        return elder;
    }

    public boolean isInWater() {
        return inWater;
    }
    
    public void setElder(boolean elder) {
        this.elder = elder;
        addElderData(4, elder);
    }

    @Override
    public boolean canSpawn() {
        return this.locY > 45.0D && this.locY < 63.0D && super.canSpawn();
    }

    private Guardian bukkitEntity;
    @Override
    public CraftEntity getBukkitEntity() {
    	if (bukkitEntity == null) {
    		bukkitEntity = new Guardian(MinecraftServer.getServer().server, this); 
    	}
    	return bukkitEntity;
    }

    class AIGuardianAttack extends PathfinderWrapper {

        private EntityGuardian guardian = EntityGuardian.this;
        
        public AIGuardianAttack() {
            setMutexBits(3);
        }

        
        @Override
        public boolean canExecute() {
            EntityLiving var1 = this.guardian.getGoalTarget();
            return var1 != null && var1.isAlive();
        }

        @Override
        public boolean canContinue() {
            return super.canContinue() && (this.guardian.isElder() || Utilities.getDistanceSqToEntity(this.guardian, this.guardian.getGoalTarget()) > 9.0D);
        }

        @Override
        public void finish() {
            super.finish();
        }
        
    }
}
