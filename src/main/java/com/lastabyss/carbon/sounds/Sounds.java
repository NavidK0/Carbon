package com.lastabyss.carbon.sounds;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.StepSound;

/**
 * Literally just a remap of StepSounds in Block.class
 * @author Navid
 */
public class Sounds {
    public static final StepSound STONE = Block.e;
    public static final StepSound WOOD = Block.f;
    public static final StepSound GRAVEL = Block.g;
    public static final StepSound GRASS = Block.h;
    public static final StepSound METAL = Block.i;
    public static final StepSound PISTON = Block.j;
    public static final StepSound GLASS = Block.k;
    public static final StepSound CLOTH = Block.l;
    public static final StepSound SAND = Block.m;
    public static final StepSound SNOW = Block.n;
    public static final StepSound LADDER = Block.o;
    public static final StepSound ANVIL = Block.p;
    public static final StepSound SLIME = new StepSound("slime", 1.0f, 1.0f) {
        @Override
        public String getBreakSound() {
            return "mob.slime.big";
        }
        @Override
        public String getPlaceSound() {
            return "mob.slime.big";
        }
        @Override
        public String getStepSound() {
            return "mob.slime.small";
        }
    };
}
