package com.lastabyss.carbon.optimizations.world;

import java.util.HashSet;

import net.minecraft.server.v1_7_R4.TileEntity;
import net.minecraft.server.v1_7_R4.TileEntityChest;
import net.minecraft.server.v1_7_R4.TileEntityCommand;
import net.minecraft.server.v1_7_R4.TileEntityComparator;
import net.minecraft.server.v1_7_R4.TileEntityDispenser;
import net.minecraft.server.v1_7_R4.TileEntityEnchantTable;
import net.minecraft.server.v1_7_R4.TileEntityEnderChest;
import net.minecraft.server.v1_7_R4.TileEntityEnderPortal;
import net.minecraft.server.v1_7_R4.TileEntityFlowerPot;
import net.minecraft.server.v1_7_R4.TileEntityNote;
import net.minecraft.server.v1_7_R4.TileEntityRecordPlayer;
import net.minecraft.server.v1_7_R4.TileEntitySign;
import net.minecraft.server.v1_7_R4.TileEntitySkull;

public class OptimizedTileEntityList extends HashSet<TileEntity> {

	private static final long serialVersionUID = 3112991409504261787L;

	@Override
	public boolean add(TileEntity tileEntity) {
		if (
			tileEntity instanceof TileEntityChest ||
			tileEntity instanceof TileEntityEnderChest ||
			tileEntity instanceof TileEntityRecordPlayer ||
			tileEntity instanceof TileEntityDispenser ||
			tileEntity instanceof TileEntitySign ||
			tileEntity instanceof TileEntityNote ||
			tileEntity instanceof TileEntityEnderPortal ||
			tileEntity instanceof TileEntityCommand ||
			tileEntity instanceof TileEntitySkull ||
			tileEntity instanceof TileEntityComparator ||
			tileEntity instanceof TileEntityFlowerPot ||
			tileEntity instanceof TileEntityEnchantTable
		) {
			return false;
		} else {
			return super.add(tileEntity);
		}
	}

}
