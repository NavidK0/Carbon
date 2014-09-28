package net.o2gaming.carbon.blocks;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.CreativeModeTab;
import net.minecraft.server.v1_7_R4.Material;

public class BlockBarrier extends Block {

  public BlockBarrier() {
    super(Material.STONE);
    a(CreativeModeTab.b);
    //Sets infinite hardness.
    s();
    //Sets blast
    b(6000000.0F);
    //Prevents piston pushing
    H();
  }

  @Override
  public int getDropData(int i)
  {
    return i;
    
  }
  
}