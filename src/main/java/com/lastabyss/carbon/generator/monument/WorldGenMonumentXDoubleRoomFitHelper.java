package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

class WorldGenMonumentXDoubleRoomFitHelper implements WorldGenMonumentRoomFitHelper {

	public boolean a(WorldGenMonumentRoomDefinition definition) {
		return definition.c[BlockFace.EAST.getId()] && !definition.b[BlockFace.EAST.getId()].d;
	}

	public WorldGenMonumentPiece getPiece(BlockFace face, WorldGenMonumentRoomDefinition definition, Random var3) {
		definition.d = true;
		definition.b[BlockFace.EAST.getId()].d = true;
		return new WorldGenMonumentDoubleXRoom(face, definition, var3);
	}

}
