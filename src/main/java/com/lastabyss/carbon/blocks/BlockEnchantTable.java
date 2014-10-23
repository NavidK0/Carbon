package com.lastabyss.carbon.blocks;

import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;

import com.lastabyss.carbon.entity.TileEntityOptimizedEnchantTable;
import com.lastabyss.carbon.inventory.EnchantingContainer;

import net.minecraft.server.v1_7_R4.Container;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_7_R4.TileEntity;
import net.minecraft.server.v1_7_R4.TileEntityEnchantTable;
import net.minecraft.server.v1_7_R4.World;

public class BlockEnchantTable extends net.minecraft.server.v1_7_R4.BlockEnchantmentTable {

	public BlockEnchantTable() {
		super();
		c(5.0F);
		b(2000.0F);
		c("enchantmentTable");
		d("enchanting_table");
	}

	@Override
	public boolean interact(World world, int x, int y, int z, EntityHuman entityhuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
		TileEntityEnchantTable localTileEntityEnchantTable = (TileEntityEnchantTable) world.getTileEntity(x, y, z);
		String name = localTileEntityEnchantTable.b() ? localTileEntityEnchantTable.a() : null;
		EntityPlayer player = (EntityPlayer) entityhuman;
		Container container = CraftEventFactory.callInventoryOpenEvent(player, new EnchantingContainer(entityhuman.inventory, world, x, y, z));
		if (container == null) {
			return true;
		}
		int newWidnowId = player.nextContainerCounter();
		player.playerConnection.sendPacket(new PacketPlayOutOpenWindow(newWidnowId, 4, name != null ? name : "" , 0, name != null));
		player.activeContainer = container;
		player.activeContainer.windowId = newWidnowId;
		player.activeContainer.addSlotListener(player);
		return true;
	}

	public TileEntity a(World paramWorld, int paramInt) {
		return new TileEntityOptimizedEnchantTable();
	}

}
