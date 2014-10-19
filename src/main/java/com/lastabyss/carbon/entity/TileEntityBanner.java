package com.lastabyss.carbon.entity;

import com.lastabyss.carbon.recipes.EnumBannerPatterns;

import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_7_R4.TileEntity;

public class TileEntityBanner extends TileEntity {

	private int baseColor;
	private NBTTagList patterns;

	public void setItemValues(ItemStack itemStack) {
		this.patterns = null;
		if (itemStack.hasTag() && itemStack.getTag().hasKeyOfType("BlockEntityTag", 10)) {
			NBTTagCompound compound = itemStack.getTag().getCompound("BlockEntityTag");

			if (compound.hasKeyOfType("Base", 99)) {
				this.baseColor = compound.getInt("Base");
			} else {
				this.baseColor = itemStack.getData() & 15;
			}

			if (compound.hasKey("Patterns")) {
				this.patterns = (NBTTagList) compound.getList("Patterns", 10).clone();
			}

			//transform lore banners (legacy format hat was used in Carbon 1.6.4)
			NBTTagList lorepatterns = EnumBannerPatterns.fromLoreToNBT(itemStack);
			if (lorepatterns != null) {
				for (int i = 0; i < lorepatterns.size(); i++) {
					patterns.add(lorepatterns.get(i));
				}
			}
		} else {
			this.baseColor = itemStack.getData() & 15;
		}
	}

	//Read
	@Override
	public void a(NBTTagCompound compound) {
		super.a(compound);
		this.baseColor = compound.getInt("Base");
		this.patterns = compound.getList("Patterns", 10);
	}

	//Write
	@Override
	public void b(NBTTagCompound compound) {
		super.b(compound);
		compound.setInt("Base", this.baseColor);

		if (this.patterns != null) {
			compound.set("Patterns", this.patterns);
		}
	}

	@Override
	public Packet getUpdatePacket() {
		NBTTagCompound updatePacketTag = new NBTTagCompound();
		this.b(updatePacketTag);
		return new PacketPlayOutTileEntityData(x, y, z, 6, updatePacketTag);
	}

	public int getBaseColor() {
		return this.baseColor;
	}

}
