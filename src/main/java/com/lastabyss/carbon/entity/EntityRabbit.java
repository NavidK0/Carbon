package com.lastabyss.carbon.entity;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.entity.bukkit.Rabbit;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.EntityAgeable;
import net.minecraft.server.v1_7_R4.EntityAnimal;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.PathfinderGoalBreed;
import net.minecraft.server.v1_7_R4.PathfinderGoalFloat;
import net.minecraft.server.v1_7_R4.PathfinderGoalFollowParent;
import net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_7_R4.PathfinderGoalPanic;
import net.minecraft.server.v1_7_R4.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_7_R4.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_7_R4.PathfinderGoalTempt;
import net.minecraft.server.v1_7_R4.World;

/**
 *
 * @author Navid
 */
public class EntityRabbit extends EntityAnimal {
            
    static enum EnumMoveType {
        NONE("NONE", 0, 0.0F, 0.0F, 30, 1),
        HOP("HOP", 1, 0.8F, 0.2F, 20, 10),
        STEP("STEP", 2, 1.0F, 0.45F, 14, 14),
        SPRINT("SPRINT", 3, 1.75F, 0.4F, 1, 8),
        ATTACK("ATTACK", 4, 2.0F, 0.7F, 7, 8);
        private final float f;
        private final float g;
        private final int h;
        private final int i;

        private EnumMoveType(String str, int i, float p3, float p4, int p5, int p6)
        {
            this.f = p3;
            this.g = p4;
            this.h = p5;
            this.i = p6;
        }

        public float a()
        {
            return this.f;
        }

        public float b()
        {
            return this.g;
        }

        public int c()
        {
            return this.h;
        }

        public int d()
        {
            return this.i;
        }
    }

    public EntityRabbit(World world) {
        super(world);
        this.a(0.9F, 0.9F);
        this.getNavigation().a(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));
        this.goalSelector.a(2, new PathfinderGoalBreed(this, 0.8D));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.2D, Items.CARROT_STICK, false));
        this.goalSelector.a(4, new PathfinderGoalTempt(this, 1.2D, Items.CARROT, false));
        this.goalSelector.a(5, new PathfinderGoalFollowParent(this, 1.1D));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
    }

    public boolean bk() {
        return true;
    }

    protected void aD() {
        super.aD();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
        this.getAttributeInstance(GenericAttributes.d).setValue(0.25D);
    }

    public boolean bE() {
        ItemStack itemstack = ((EntityHuman) this.passenger).be();

        return itemstack != null && itemstack.getItem() == Items.CARROT_STICK;
    }

    protected void c() {
        super.c();
        this.datawatcher.a(16, (byte) 0);
    }

    protected String t() {
        return "mob.rabbit.idle";
    }

    protected String aT() {
        return "mob.rabbit.hurt";
    }

    protected String aU() {
        return "mob.rabbit.death";
    }

    protected void a(int i, int j, int k, Block block) {
        this.makeSound("mob.rabbit.hop", 0.15F, 1.0F);
    }

    public boolean a(EntityHuman entityhuman) {
        if (super.a(entityhuman)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Item getLoot() {
        return this.isBurning() ? Items.GRILLED_PORK : Items.PORK;
    }

    @Override
    protected void getRareDrop(int i) {
        this.a(Carbon.injector().rabbitFootItem, 1);
    }
    
    
    @Override
    protected void dropDeathLoot(boolean flag, int i) {
        int var3 = this.random.nextInt(2) + this.random.nextInt(1 + i);
        int var4;

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.a(Carbon.injector().rabbitHideItem, 1);
        }

        var3 = this.random.nextInt(2);

        for (var4 = 0; var4 < var3; ++var4)
        {
            if (this.isBurning())
            {
                this.a(Carbon.injector().cookedRabbitItem, 1);
            }
            else
            {
                this.a(Carbon.injector().rabbitItem, 1);
            }
        }
    }
    
    public EntityRabbit b(EntityAgeable entityageable) {
        return new EntityRabbit(this.world);
    }

    public boolean c(ItemStack itemstack) {
        return itemstack != null && itemstack.getItem() == Items.CARROT;
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.b(entityageable);
    }

    private Rabbit bukkitEntity;
    @Override
    public CraftEntity getBukkitEntity() {
    	if (bukkitEntity == null) {
    		bukkitEntity = new Rabbit(MinecraftServer.getServer().server, this); 
    	}
    	return bukkitEntity;
    }

}
