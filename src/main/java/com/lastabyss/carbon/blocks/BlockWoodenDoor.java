package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.items.ItemWoodenDoor.DoorType;

import java.util.Random;

import net.minecraft.server.v1_7_R4.BlockDoor;
import net.minecraft.server.v1_7_R4.CreativeModeTab;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.Material;

/**
 *
 * @author Navid
 */
public class BlockWoodenDoor extends BlockDoor {
    public DoorType type;
    
    public BlockWoodenDoor(DoorType type) {
        super(Material.WOOD);
        this.type = type;
        a(CreativeModeTab.d);
        c(3f);
        b(15f);
        H();
        
    }

    @Override
    public Item getDropType(int i, Random random, int j) {
         return null;
    }

    @Override
    public int getDropCount(int i, Random random) {
        return 0;
    }
    
    

}
    

