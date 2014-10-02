package com.lastabyss.carbon.entity;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.BlockMonsterEggs;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.DamageSource;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityDamageSource;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityMonster;
import net.minecraft.server.v1_7_R4.EnumMonsterType;
import net.minecraft.server.v1_7_R4.Facing;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.World;
import net.minecraft.util.org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * These things are god awful.
 * @author Navid
 */
public class EntityEndermite extends EntityMonster {

    private int bp;

    public EntityEndermite(World world) {
        super(world);
        a(0.3F, 0.7F);
    }

    @Override
    protected void aD() {
        super.aD();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
        this.getAttributeInstance(GenericAttributes.d).setValue(0.6000000238418579D);
        this.getAttributeInstance(GenericAttributes.e).setValue(2.0D);
    }

    @Override
    protected boolean g_() {
        return false;
    }

    @Override
    protected Entity findTarget() {
        double d0 = 8.0D;

        return this.world.findNearbyVulnerablePlayer(this, d0);
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
    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.isInvulnerable()) {
            return false;
        } else {
            if (this.bp <= 0 && (damagesource instanceof EntityDamageSource || damagesource == DamageSource.MAGIC)) {
                this.bp = 20;
            }

            return super.damageEntity(damagesource, f);
        }
    }

    @Override
    protected void a(Entity entity, float f) {
        if (this.attackTicks <= 0 && f < 1.2F && entity.boundingBox.e > this.boundingBox.b && entity.boundingBox.b < this.boundingBox.e) {
            this.attackTicks = 20;
            this.n(entity);
        }
    }

    @Override
    protected void a(int i, int j, int k, Block block) {
        this.makeSound("mob.silverfish.step", 0.15F, 1.0F);
    }

    @Override
    protected Item getLoot() {
        return Item.getById(0);
    }

    @Override
    public void h() {
        this.aM = this.yaw;
        super.h();
    }

    @Override
    protected void bq() {
        super.bq();
        if (!this.world.isStatic) {
            int i;
            int j;
            int k;
            int l;

            if (this.bp > 0) {
                --this.bp;
                if (this.bp == 0) {
                    i = MathHelper.floor(this.locX);
                    j = MathHelper.floor(this.locY);
                    k = MathHelper.floor(this.locZ);
                    boolean flag = false;

                    for (int i1 = 0; !flag && i1 <= 5 && i1 >= -5; i1 = i1 <= 0 ? 1 - i1 : 0 - i1) {
                        for (l = 0; !flag && l <= 10 && l >= -10; l = l <= 0 ? 1 - l : 0 - l) {
                            for (int j1 = 0; !flag && j1 <= 10 && j1 >= -10; j1 = j1 <= 0 ? 1 - j1 : 0 - j1) {
                                if (this.world.getType(i + l, j + i1, k + j1) == Blocks.MONSTER_EGGS) {
                                    if (!this.world.getGameRules().getBoolean("mobGriefing")) {
                                        int k1 = this.world.getData(i + l, j + i1, k + j1);
                                        ImmutablePair immutablepair = BlockMonsterEggs.b(k1);

                                        this.world.setTypeAndData(i + l, j + i1, k + j1, (Block) immutablepair.getLeft(), ((Integer) immutablepair.getRight()).intValue(), 3);
                                    } else {
                                        this.world.setAir(i + l, j + i1, k + j1, false);
                                    }

                                    Blocks.MONSTER_EGGS.postBreak(this.world, i + l, j + i1, k + j1, 0);
                                    if (this.random.nextBoolean()) {
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (this.target == null && !this.bS()) {
                i = MathHelper.floor(this.locX);
                j = MathHelper.floor(this.locY + 0.5D);
                k = MathHelper.floor(this.locZ);
                int l1 = this.random.nextInt(6);
                Block block = this.world.getType(i + Facing.b[l1], j + Facing.c[l1], k + Facing.d[l1]);

                l = this.world.getData(i + Facing.b[l1], j + Facing.c[l1], k + Facing.d[l1]);
                if (BlockMonsterEggs.a(block)) {
                    this.world.setTypeAndData(i + Facing.b[l1], j + Facing.c[l1], k + Facing.d[l1], Blocks.MONSTER_EGGS, BlockMonsterEggs.a(block, l), 3);
                    this.s();
                    this.die();
                } else {
                    this.bQ();
                }
            } else if (this.target != null && !this.bS()) {
                this.target = null;
            }
        }
    }

    @Override
    public float a(int i, int j, int k) {
        return this.world.getType(i, j - 1, k) == Blocks.STONE ? 10.0F : super.a(i, j, k);
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
}