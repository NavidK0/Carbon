package com.lastabyss.carbon.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.IContainer;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.World;

import com.google.common.collect.Lists;
import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.utils.nmsclasses.BlockFace;
import com.lastabyss.carbon.utils.nmsclasses.Position;

public class BlockPistonBlocksCalc {

	private final World world;
	private final Position position;
	private final Position relative;
	private final BlockFace face;
	private final List<Position> blocksToPush = Lists.newArrayList();
	private final List<Position> blocksToBreak = Lists.newArrayList();

	public BlockPistonBlocksCalc(World world, Position position, BlockFace face, boolean isPush) {
		this.world = world;
		this.position = position;
		if (isPush) {
			this.face = face;
			this.relative = position.getRelative(face);
		} else {
			this.face = face.getOpposite();
			this.relative = position.getRelative(face, 2);
		}
	}

	public boolean calculate() {
		this.blocksToPush.clear();
		this.blocksToBreak.clear();
		Block block = this.world.getType(relative.getX(), relative.getY(), relative.getZ());
		if (!canPushBlock(block, this.world, this.relative, this.face, false)) {
			if (block.getMaterial().getPushReaction() != 1) {
				return false;
			} else {
				this.blocksToBreak.add(this.relative);
				return true;
			}
		} else if (!canPush(this.relative)) {
			return false;
		} else {
			for (int i = 0; i < this.blocksToPush.size(); ++i) {
				Position position = this.blocksToPush.get(i);
				if (this.world.getType(position.getX(), position.getY(), position.getZ()) == Carbon.injector().slimeBlock && !this.canPushWithSides(position)) {
					return false;
				}
			}

			return true;
		}
	}

	private boolean canPush(Position position) {
		Block block = this.world.getType(position.getX(), position.getY(), position.getZ());
		if (block.getMaterial() == Material.AIR) {
			return true;
		} else if (!canPushBlock(block, this.world, position, this.face, false)) {
			return true;
		} else if (position.equals(this.position)) {
			return true;
		} else if (this.blocksToPush.contains(position)) {
			return true;
		} else {
			int blocksToAdd = 1;
			if (blocksToAdd + this.blocksToPush.size() > 12) {
				return false;
			} else {
				while (block == Carbon.injector().slimeBlock) {
					Position nextBlock = position.getRelative(this.face.getOpposite(), blocksToAdd);
					block = this.world.getType(nextBlock.getX(), nextBlock.getY(), nextBlock.getZ());
					if (block.getMaterial() == Material.AIR || !canPushBlock(block, this.world, nextBlock, this.face, false) || nextBlock.equals(this.position)) {
						break;
					}

					++blocksToAdd;
					if (blocksToAdd + this.blocksToPush.size() > 12) {
						return false;
					}
				}

				int blocksCount = 0;
				for (int i = blocksToAdd - 1; i >= 0; --i) {
					this.blocksToPush.add(position.getRelative(this.face.getOpposite(), i));
					++blocksCount;
				}

				int relativeDist = 1;
				while (true) {
					Position calcBlock = position.getRelative(this.face, relativeDist);
					int blockNumber = this.blocksToPush.indexOf(calcBlock);
					if (blockNumber > -1) {
						this.sort(blocksCount, blockNumber);

						for (int i = 0; i <= blockNumber + blocksCount; ++i) {
							Position pushPosition = (Position) this.blocksToPush.get(i);
							if (this.world.getType(pushPosition.getX(), pushPosition.getY(), pushPosition.getZ()) == Carbon.injector().slimeBlock && !this.canPushWithSides(pushPosition)) {
								return false;
							}
						}

						return true;
					}

					block = this.world.getType(calcBlock.getX(), calcBlock.getY(), calcBlock.getZ());
					if (block.getMaterial() == Material.AIR) {
						return true;
					}

					if (!canPushBlock(block, this.world, calcBlock, this.face, true) || calcBlock.equals(this.position)) {
						return false;
					}

					if (block.getMaterial().getPushReaction() == 1) {
						this.blocksToBreak.add(calcBlock);
						return true;
					}

					if (this.blocksToPush.size() >= 12) {
						return false;
					}

					this.blocksToPush.add(calcBlock);
					++blocksCount;
					++relativeDist;
				}
			}
		}
	}

	private void sort(int blocksCount, int blockNumber) {
		ArrayList<Position> part1 = Lists.newArrayList();
		ArrayList<Position> part2 = Lists.newArrayList();
		ArrayList<Position> part3 = Lists.newArrayList();
		part1.addAll(this.blocksToPush.subList(0, blockNumber));
		part2.addAll(this.blocksToPush.subList(this.blocksToPush.size() - blocksCount, this.blocksToPush.size()));
		part3.addAll(this.blocksToPush.subList(blockNumber, this.blocksToPush.size() - blocksCount));
		this.blocksToPush.clear();
		this.blocksToPush.addAll(part1);
		this.blocksToPush.addAll(part2);
		this.blocksToPush.addAll(part3);
	}

	private boolean canPushWithSides(Position position) {
		for (BlockFace blockFace : BlockFace.values()) {
			if (blockFace.getAxis() != this.face.getAxis() && !this.canPush(position.getRelative(blockFace))) {
				return false;
			}
		}
		return true;
	}

	public List<Position> getBlocksToPush() {
		return this.blocksToPush;
	}

	public List<Position> getBlocksToBreak() {
		return this.blocksToBreak;
	}

	public static boolean canPushBlock(final Block block, final World world, Position position, BlockFace face, final boolean flag) {
        if (block == Blocks.OBSIDIAN) {
            return false;
        }
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();
        if (y >= 0 && (face != BlockFace.DOWN || y != 0)  && y <= world.getHeight() - 1 && (face != BlockFace.UP || y != world.getHeight() - 1)) {
	        if (block != Blocks.PISTON && block != Blocks.PISTON_STICKY) {
	            if (block.f(world, x, y, z) == -1.0f) {
	                return false;
	            }
	            if (block.h() == 2) {
	                return false;
	            }
	            if (block.h() == 1) {
	                return flag;
	            }
	        }
	        else if ((world.getData(x, y, z) & 0x8) != 0x0) {
	            return false;
	        }
	        return !(block instanceof IContainer);
        }
        return false;
    }

}
