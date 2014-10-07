package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.entity.TileEntityBanner;
import java.util.Random;
import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.BlockContainer;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.TileEntity;
import net.minecraft.server.v1_7_R4.World;

public class BlockBanner extends BlockContainer {

	public BlockBanner() {
		super(Material.WOOD);
		this.isTileEntity = true;
                c(1.0f);
		// float var1 = 0.25F;
		// float var2 = 1.0F;
		// this.setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1,
		// var2, 0.5F + var1);
	}

	@Override
	public TileEntity a(World arg0, int arg1) {
		return new TileEntityBanner();
	}

	@Override
	public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
		return null;
	}

        @Override
        public Item getDropType(int i, Random random, int j) {
            return Carbon.injector().standingBannerItem;
        }

	@Override
	public boolean c() {
		return false;
	}

        @Override
        public boolean d() {
          return false;
        }

	@Override
	public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
		super.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
		paramWorld.p(paramInt1, paramInt2, paramInt3);
	}

	@Override
	public boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
		super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
		TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
		if(localTileEntity != null) {
			return localTileEntity.c(paramInt4, paramInt5);
		}
		return false;
	}
}
