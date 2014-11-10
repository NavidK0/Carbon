package com.lastabyss.enchants;

import net.minecraft.server.v1_7_R4.Enchantment;
import net.minecraft.server.v1_7_R4.EnchantmentSlotType;

public class EnchantmentDepthStrider extends Enchantment {

	public EnchantmentDepthStrider(final int id, final int weight) {
		super(id, weight, EnchantmentSlotType.ARMOR_FEET);
		this.b("waterWalker");
	}

	public int a(int var1) {
		return var1 * 10;
	}

	public int b(int var1) {
		return this.a(var1) + 15;
	}

	public int getMaxLevel() {
		return 3;
	}

}