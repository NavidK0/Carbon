package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

class WorldGenMonumentFitSimpleRoomTopHelper implements WorldGenMonumentRoomFitHelper {

	public boolean a(WorldGenMonumentRoomDefinition definition) {
		return !definition.c[BlockFace.WEST.getId()] && !definition.c[BlockFace.EAST.getId()] && !definition.c[BlockFace.NORTH.getId()] && !definition.c[BlockFace.SOUTH.getId()] && !definition.c[BlockFace.UP.getId()];
	}

	public WorldGenMonumentPiece getPiece(BlockFace face, WorldGenMonumentRoomDefinition definition, Random var3) {
		definition.d = true;
		return new WorldGenMonumentSimpleTopRoom(face, definition, var3);
	}

}
