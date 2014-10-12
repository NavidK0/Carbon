package com.lastabyss.carbon.items;

import com.lastabyss.carbon.Carbon;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.CreativeModeTab;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.ItemDoor;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.World;

/**
 *
 * @author Navid
 */
public class ItemWoodenDoor extends ItemDoor {
    public DoorType type;
    
    public enum DoorType {
        OAK("wooden_door"),
        SPRUCE("spruce_door"),
        BIRCH("birch_door"),
        JUNGLE("jungle_door"),
        ACACIA("acacia_door"),
        DARK_OAK("dark_oak_door");
        
        String name;

        private DoorType(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
    }
    
    //Door stack sizes are now 64
    public ItemWoodenDoor(DoorType type) {
        super(Material.WOOD);
        this.maxStackSize = 1;
        this.type = type;
        a(CreativeModeTab.d);
        c(type.getName());
        f(type.getName());
        maxStackSize = 64;
    }
  
  //Places the correct door type
  @Override
  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int x, int y, int z, int blockFace, float paramFloat1, float paramFloat2, float paramFloat3) {
    if (blockFace != 1) {
      return false;
    }
    if (!paramWorld.getType(x, y, z).getMaterial().isReplaceable()) {
      y++;
    }
    Block localBlock;
    
    switch(type) {
        case SPRUCE:
            localBlock = Carbon.injector().spruceDoorBlock;
            break;
        case BIRCH:
            localBlock = Carbon.injector().birchDoorBlock;
            break;
        case JUNGLE:
            localBlock = Carbon.injector().jungleDoorBlock;
            break;
        case ACACIA:
            localBlock = Carbon.injector().acaciaDoorBlock;
            break;
        case DARK_OAK:
            localBlock = Carbon.injector().darkOakDoorBlock;
            break;
        default:
            localBlock = Blocks.WOODEN_DOOR;
    }
    if ((!paramEntityHuman.a(x, y, z, blockFace, paramItemStack)) || (!paramEntityHuman.a(x, y + 1, z, blockFace, paramItemStack))) {
      return false;
    }
    if (!localBlock.canPlace(paramWorld, x, y, z)) {
      return false;
    }
    int integer = MathHelper.floor((paramEntityHuman.yaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 0x3;
    
    place(paramWorld, x, y, z, integer, localBlock);
    
    paramItemStack.count -= 1;
    return true;
  }
  
  public static void place(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Block paramBlock) {
    int i = 0;
    int j = 0;
    if (paramInt4 == 0) {
      j = 1;
    }
    if (paramInt4 == 1) {
      i = -1;
    }
    if (paramInt4 == 2) {
      j = -1;
    }
    if (paramInt4 == 3) {
      i = 1;
    }
    int k = (paramWorld.getType(paramInt1 - i, paramInt2, paramInt3 - j).r() ? 1 : 0) + (paramWorld.getType(paramInt1 - i, paramInt2 + 1, paramInt3 - j).r() ? 1 : 0);
    int m = (paramWorld.getType(paramInt1 + i, paramInt2, paramInt3 + j).r() ? 1 : 0) + (paramWorld.getType(paramInt1 + i, paramInt2 + 1, paramInt3 + j).r() ? 1 : 0);
    
    int n = (paramWorld.getType(paramInt1 - i, paramInt2, paramInt3 - j) == paramBlock) || (paramWorld.getType(paramInt1 - i, paramInt2 + 1, paramInt3 - j) == paramBlock) ? 1 : 0;
    int i1 = (paramWorld.getType(paramInt1 + i, paramInt2, paramInt3 + j) == paramBlock) || (paramWorld.getType(paramInt1 + i, paramInt2 + 1, paramInt3 + j) == paramBlock) ? 1 : 0;
    
    int i2 = 0;
    if ((n != 0) && (i1 == 0)) {
      i2 = 1;
    } else if (m > k) {
      i2 = 1;
    }
    paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, paramBlock, paramInt4, 2);
    paramWorld.setTypeAndData(paramInt1, paramInt2 + 1, paramInt3, paramBlock, 0x8 | (i2 != 0 ? 1 : 0), 2);
  }

}
