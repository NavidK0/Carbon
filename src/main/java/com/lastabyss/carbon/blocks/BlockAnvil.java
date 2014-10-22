package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.inventory.AnvilContainer;
import net.minecraft.server.v1_7_R4.Container;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_7_R4.World;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;

public class BlockAnvil extends net.minecraft.server.v1_7_R4.BlockAnvil {

	public BlockAnvil() {
		super();
		c(5.0F);
		a(p);
		b(2000.0F);
		c("anvil");
	}

	@Override
	public boolean interact(World world, int x, int y, int z, EntityHuman entityhuman, int l, float f, float f1, float f2) {
		try {
			EntityPlayer player = (EntityPlayer) entityhuman;
			Container container = CraftEventFactory.callInventoryOpenEvent(player, new AnvilContainer(entityhuman.inventory, world, x, y, z, entityhuman));
			if (container == null) {
				return true;
			}
			int newWidnowId = player.nextContainerCounter();
			player.playerConnection.sendPacket(new PacketPlayOutOpenWindow(newWidnowId, 8, "Repairing", 0, true));
			player.activeContainer = container;
			player.activeContainer.windowId = newWidnowId;
			player.activeContainer.addSlotListener(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
