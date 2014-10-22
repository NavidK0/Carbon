package com.lastabyss.carbon.items;

import java.util.List;
import java.util.Random;

import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlock;

import com.lastabyss.carbon.entity.ArmorStandPose;
import com.lastabyss.carbon.entity.EntityArmorStand;

import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.World;

public class ItemArmorStand extends Item {

	public ItemArmorStand() {
		maxStackSize = 16;
	}

	@Override
	public boolean interactWith(ItemStack itemStack, EntityHuman human, World world, int x, int y, int z, int blockFace, float paramFloat1, float paramFloat2, float paramFloat3) {
		if (blockFace == CraftBlock.blockFaceToNotch(BlockFace.DOWN)) {
			return false;
		}
		if (!world.getType(x, y, z).getMaterial().isReplaceable()) {
	    	y++;
	    }
		List<?> entities = world.getEntities(null, AxisAlignedBB.a(x, y, z, x + 1.0D, y + 2.0D, z + 1.0D));
		if (entities.size() > 0) {
			return false;
		} else {
			if (!world.isStatic) {
				EntityArmorStand armorStand = new EntityArmorStand(world, x + 0.5D, y, z + 0.5D);
				float yaw = (float) MathHelper.floor((clampYaw(human.yaw - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				armorStand.setPositionRotation(x + 0.5D, y, z + 0.5D, yaw, 0.0F);
				this.setRandomBodyPose(armorStand, world.random);
				NBTTagCompound itemStackTag = itemStack.getTag();
				if (itemStackTag != null && itemStackTag.hasKeyOfType("EntityTag", 10)) {
					//NBTTagCompound tag = new NBTTagCompound();
					//armorStand.writeIfNoPassenger(tag);
					//tag.copyFrom(itemStackTag.getCompound("EntityTag"));
					//armorStand.load(tag);
				}

				world.addEntity(armorStand);
			}

			--itemStack.count;
			return true;
		}
	}

	private void setRandomBodyPose(EntityArmorStand armorStand, Random random) {
		ArmorStandPose headpose = armorStand.getHeadPose();
		float f1 = random.nextFloat() * 5.0F;
		float d2 = random.nextFloat() * 20.0F - 10.0F;
		ArmorStandPose pose = new ArmorStandPose(headpose.getX() + f1, headpose.getY() + d2, headpose.getZ());
		armorStand.setHeadPose(pose);
		headpose = armorStand.getBodyPose();
		f1 = random.nextFloat() * 10.0F - 5.0F;
		pose = new ArmorStandPose(headpose.getX(), headpose.getY() + f1, headpose.getZ());
		armorStand.setBodyPose(pose);
	}

	private float clampYaw(float yaw) {
		yaw %= 360.0F;
		if (yaw >= 180.0F) {
			yaw -= 360.0F;
		}

		if (yaw < -180.0F) {
			yaw += 360.0F;
		}

		return yaw;
	}

}
