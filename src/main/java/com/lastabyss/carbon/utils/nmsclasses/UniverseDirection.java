package com.lastabyss.carbon.utils.nmsclasses;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.util.com.google.common.base.Predicate;
import net.minecraft.util.com.google.common.collect.Iterators;

public enum UniverseDirection implements Predicate<BlockFace>, Iterable<BlockFace> {

	HORIZONTAL, VERTICAL;

	public BlockFace[] getBlockFaces() {
		if (this == HORIZONTAL) {
			return new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
		}
		if (this == VERTICAL) {
			return new BlockFace[] { BlockFace.UP, BlockFace.DOWN };
		}
		throw new Error("Someone\'s been tampering with the universe!");
	}

	public BlockFace getRandomBlockFace(Random random) {
		BlockFace[] faces = this.getBlockFaces();
		return faces[random.nextInt(faces.length)];
	}

	public boolean apply(BlockFace blockFace) {
		return blockFace != null && blockFace.getAxis().getUniverseDirection() == this;
	}

	public Iterator<BlockFace> iterator() {
		return Iterators.forArray(this.getBlockFaces());
	}

}
