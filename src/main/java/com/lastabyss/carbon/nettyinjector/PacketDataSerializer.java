package com.lastabyss.carbon.nettyinjector;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_7_R4.util.CraftChatMessage;
import org.spigotmc.SpigotComponentReverter;
import org.spigotmc.SpigotDebreakifier;

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
				CraftItemStack.setItemMeta(itemstack, CraftItemStack.getItemMeta(itemstack));
				//serverside we store banner patterns as lore, so we will have to recode from lore to nbt when sending item to client
				//if (itemstack.getItem() == Carbon.injector().standingBannerItem) {
				//	EnumBannerPatterns.fromLoreToNBT(itemstack);
				//}
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

	@Override
	public ItemStack c() {
		ItemStack itemstack = null;
		short short1 = readShort();
		if (short1 >= 0) {
			byte b0 = readByte();
			short short2 = readShort();

			itemstack = new ItemStack(Item.getById(short1), b0, short2);
			itemstack.tag = b();
			if (itemstack.tag != null) {
				if ((this.version >= 29) && (itemstack.getItem() == Items.WRITTEN_BOOK) && (itemstack.tag.hasKeyOfType("pages", 9))) {
					NBTTagList nbttaglist = itemstack.tag.getList("pages", 8);
					NBTTagList newList = new NBTTagList();
					for (int i = 0; i < nbttaglist.size(); i++) {
						IChatBaseComponent s = ChatSerializer.a(nbttaglist.getString(i));
						String newString = SpigotComponentReverter.toLegacy(s);
						newList.add(new NBTTagString(newString));
					}
					itemstack.tag.set("pages", newList);
				}
				CraftItemStack.setItemMeta(itemstack, CraftItemStack.getItemMeta(itemstack));
			}
		}
		return itemstack;
	}

}
