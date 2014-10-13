package com.lastabyss.carbon.entity;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.recipes.EnumBannerPatterns;

import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_7_R4.TileEntity;

@SuppressWarnings("unused")
public class TileEntityBanner extends TileEntity {

	private int baseColor;
	private NBTTagList patterns;

	public void setItemValues(ItemStack itemStack) {
		this.patterns = null;
		this.baseColor = itemStack.getData() & 0xF;
		//serverside we store banner patterns as lore but tile entity uses nbt, so we recode lore to nbt
		ItemStack clone = itemStack.cloneItemStack();
		EnumBannerPatterns.fromLoreToNBT(clone);
		if (clone.hasTag() && clone.getTag().hasKeyOfType("BlockEntityTag", 10)) {
			NBTTagCompound compound = clone.getTag().getCompound("BlockEntityTag");

			if (compound.hasKey("Patterns")) {
				this.patterns = (NBTTagList) compound.getList("Patterns", 10).clone();
			}

			//no base color nbt support for now
			/*if (compound.hasKeyOfType("Base", 99)) {
				this.baseColor = compound.getInt("Base");
			} else {
				this.baseColor = itemStack.getData() & 15;
			}*/
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
