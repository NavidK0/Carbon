package com.lastabyss.carbon.inventory;

import java.util.Random;

import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.PlayerAbilities;

public class AdditionalNBTDataPlayerAbilities extends PlayerAbilities {

	public AdditionalNBTDataPlayerAbilities() {
		super();
	}

	private Random enchRnd = new Random();
	private int enchantSeed;

	// write
	@Override
	public void a(NBTTagCompound nbttagcompound) {
		super.a(nbttagcompound);
		nbttagcompound.setInt("XpSeed", enchantSeed);
	}

	// read
	public void b(NBTTagCompound nbttagcompound) {
		super.b(nbttagcompound);
		enchantSeed = nbttagcompound.getInt("XpSeed");
		if (enchantSeed == 0) {
			enchantSeed = enchRnd.nextInt();
		}
	}

	public int getEnchantSeed() {
		return enchantSeed;
	}

	public int nextEnchantSeed() {
		enchantSeed = enchRnd.nextInt();
		return enchantSeed;
	}

}
