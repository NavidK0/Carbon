package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.sounds.Sounds;
import net.minecraft.server.v1_7_R4.BlockHalfTransparent;
import net.minecraft.server.v1_7_R4.CreativeModeTab;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.World;

public class BlockSlime extends BlockHalfTransparent {
    public BlockSlime() {
        super("slime", Material.CLAY, false);
        a(CreativeModeTab.c);
        this.frictionFactor = 0.8F;
        a(Sounds.SLIME);
        //Sets hardness of block
        c(0);
        H();
    }
    
    //onEntityLanded
    @Override
    public void a(World world, int x, int y, int z, Entity entityIn, float fallDistance) {
        if (entityIn.isSneaking())
          super.a(world, x, y, z, entityIn, fallDistance);
        else 
          super.a(world, x, y, z, entityIn, fallDistance);
        
    }

    @Override
    public void a(World paramaqu, int x, int y, int z, Entity paramwv) {
        if ((Math.abs(paramwv.motY) < 0.1D) && (!paramwv.isSneaking()))
        {
          double mot = 0.4D + Math.abs(paramwv.motY) * 0.2D;
          paramwv.motX *= mot;
          paramwv.motZ *= mot;
        }
        super.a(paramaqu, x, y, z, paramwv);
    }
    
}