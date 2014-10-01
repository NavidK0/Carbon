package com.lastabyss.carbon.blocks;

import com.lastabyss.carbon.Carbon;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.World;

public class BlockTorch extends net.minecraft.server.v1_7_R4.BlockTorch {

	public BlockTorch() {
		super();
		c(0.0F);
		a(0.9375F);
		a(f);
		c("torch");
		d("torch_on");
	}

	@Override
	public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
		if(paramWorld.c(paramInt1 - 1, paramInt2, paramInt3, true)) {
			return true;
		}
		if(paramWorld.c(paramInt1 + 1, paramInt2, paramInt3, true)) {
			return true;
		}
		if(paramWorld.c(paramInt1, paramInt2, paramInt3 - 1, true)) {
			return true;
		}
		if(paramWorld.c(paramInt1, paramInt2, paramInt3 + 1, true)) {
			return true;
		}
		if(m(paramWorld, paramInt1, paramInt2 - 1, paramInt3)) {
			return true;
		}
		return false;
	}

	private boolean m(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
		if(World.a(paramWorld, paramInt1, paramInt2, paramInt3)) {
			return true;
		}
		Block localBlock = paramWorld.getType(paramInt1, paramInt2, paramInt3);
		if((localBlock == Blocks.FENCE) || (localBlock == Carbon.injector().darkOakFenceBlock) || (localBlock == Carbon.injector().spruceFenceBlock) || (localBlock == Carbon.injector().jungleFenceBlock) || (localBlock == Carbon.injector().acaciaFenceBlock) || (localBlock == Carbon.injector().birchFenceBlock) || (localBlock == Blocks.NETHER_FENCE) || (localBlock == Blocks.GLASS) || (localBlock == Blocks.COBBLE_WALL)) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock) {
		if(e(paramWorld, paramInt1, paramInt2, paramInt3)) {
			int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
			int j = 0;
			if((!paramWorld.c(paramInt1 - 1, paramInt2, paramInt3, true)) && (i == 1)) {
				j = 1;
			}
			if((!paramWorld.c(paramInt1 + 1, paramInt2, paramInt3, true)) && (i == 2)) {
				j = 1;
			}
			if((!paramWorld.c(paramInt1, paramInt2, paramInt3 - 1, true)) && (i == 3)) {
				j = 1;
			}
			if((!paramWorld.c(paramInt1, paramInt2, paramInt3 + 1, true)) && (i == 4)) {
				j = 1;
			}
			if((!m(paramWorld, paramInt1, paramInt2 - 1, paramInt3)) && (i == 5)) {
				j = 1;
			}
			if(j != 0) {
				b(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3), 0);
				paramWorld.setAir(paramInt1, paramInt2, paramInt3);
				return true;
			}
		}
		else {
			return true;
		}
		return false;
	}

	@Override
	public int getPlacedData(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt5) {
		int i = paramInt5;
		if((paramInt4 == 1) && (m(paramWorld, paramInt1, paramInt2 - 1, paramInt3))) {
			i = 5;
		}
		if((paramInt4 == 2) && (paramWorld.c(paramInt1, paramInt2, paramInt3 + 1, true))) {
			i = 4;
		}
		if((paramInt4 == 3) && (paramWorld.c(paramInt1, paramInt2, paramInt3 - 1, true))) {
			i = 3;
		}
		if((paramInt4 == 4) && (paramWorld.c(paramInt1 + 1, paramInt2, paramInt3, true))) {
			i = 2;
		}
		if((paramInt4 == 5) && (paramWorld.c(paramInt1 - 1, paramInt2, paramInt3, true))) {
			i = 1;
		}
		return i;
	}
}
