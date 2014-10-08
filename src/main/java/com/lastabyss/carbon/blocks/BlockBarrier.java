package com.lastabyss.carbon.blocks;

import net.minecraft.server.v1_7_R4.BlockHalfTransparent;
import net.minecraft.server.v1_7_R4.CreativeModeTab;
import net.minecraft.server.v1_7_R4.Material;

public class BlockBarrier extends BlockHalfTransparent {

  public BlockBarrier() {
    super("barrier", Material.BUILDABLE_GLASS, false);
    a(CreativeModeTab.b);
    //Sets infinite hardness.
    s();
    //Sets blast
    b(6000000.0F);
    //Prevents piston pushing
    H();
    //Allows light to pass through
    s = true;
  }

  @Override
  public int getDropData(int i)
  {
    return i;
    
  }
  
}