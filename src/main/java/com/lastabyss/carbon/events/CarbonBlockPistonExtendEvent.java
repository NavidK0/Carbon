package com.lastabyss.carbon.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockPistonExtendEvent;

public class CarbonBlockPistonExtendEvent extends org.bukkit.event.block.BlockPistonExtendEvent {

	private ArrayList<Block> blocks = new ArrayList<Block>();

	public CarbonBlockPistonExtendEvent(Block block, List<com.lastabyss.carbon.utils.nmsclasses.Position> positions, BlockFace direction) {
		super(block, -1, direction);
		for (com.lastabyss.carbon.utils.nmsclasses.Position position : positions) {
			blocks.add(block.getWorld().getBlockAt(position.getX(), position.getY(), position.getZ()));
		}
	}

	@Override
	public List<Block> getBlocks() {	
		return blocks;
	}

	@Override
	public HandlerList getHandlers() {
		return super.getHandlers();
	}

	public static HandlerList getHandlerList() {
		return BlockPistonExtendEvent.getHandlerList();
	}

}
