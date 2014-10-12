package com.lastabyss.carbon.nettyinjector;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_7_R4.util.CraftChatMessage;
import org.spigotmc.SpigotDebreakifier;

import com.lastabyss.carbon.Carbon;

import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;
import net.minecraft.server.v1_7_R4.NBTTagString;
import net.minecraft.util.io.netty.buffer.ByteBuf;

public class PacketDataSerializer extends net.minecraft.server.v1_7_R4.PacketDataSerializer {

	public PacketDataSerializer(ByteBuf bytebuf, int version) {
		super(bytebuf, version);
	}

	@Override
	public void a(ItemStack itemstack) {
		if ((itemstack == null) || (itemstack.getItem() == null)) {
			writeShort(-1);
		} else {
			if (this.version >= 47) {
				writeShort(SpigotDebreakifier.getItemId(Item.getId(itemstack.getItem())));
			} else {
				writeShort(Item.getId(itemstack.getItem()));
			}
			writeByte(itemstack.count);
			writeShort(itemstack.getData());
			NBTTagCompound nbttagcompound = null;
			if ((itemstack.getItem().usesDurability()) || (itemstack.getItem().s())) {
				itemstack = itemstack.cloneItemStack();
				//TODO: check banners nbt
				if (itemstack.getItem() != Carbon.injector().standingBannerItem) {
					CraftItemStack.setItemMeta(itemstack, CraftItemStack.getItemMeta(itemstack));
				}
				nbttagcompound = itemstack.tag;
			}
			if ((nbttagcompound != null) && (this.version >= 29)) {
				if ((itemstack.getItem() == Items.WRITTEN_BOOK) && (nbttagcompound.hasKeyOfType("pages", 9))) {
					nbttagcompound = (NBTTagCompound) nbttagcompound.clone();
					NBTTagList nbttaglist = nbttagcompound.getList("pages", 8);
					NBTTagList newList = new NBTTagList();
					for (int i = 0; i < nbttaglist.size(); i++) {
						IChatBaseComponent[] parts = CraftChatMessage.fromString(nbttaglist.getString(i));
						IChatBaseComponent root = parts[0];
						for (int i1 = 1; i1 < parts.length; i1++) {
							IChatBaseComponent c = parts[i1];
							root.a("\n");
							root.addSibling(c);
						}
						newList.add(new NBTTagString(ChatSerializer.a(root)));
					}
					nbttagcompound.set("pages", newList);
				}
			}
			a(nbttagcompound);
		}
	}

}
