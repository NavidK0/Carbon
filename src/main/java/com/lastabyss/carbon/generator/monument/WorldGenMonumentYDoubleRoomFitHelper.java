package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

class WorldGenMonumentYDoubleRoomFitHelper implements WorldGenMonumentRoomFitHelper {

	public boolean a(WorldGenMonumentRoomDefinition definition) {
		return definition.c[BlockFace.UP.getId()] && !definition.b[BlockFace.UP.getId()].d;
	}

	public WorldGenMonumentPiece getPiece(BlockFace face, WorldGenMonumentRoomDefinition definition, Random random) {
		definition.d = true;
		definition.b[BlockFace.UP.getId()].d = true;
		return new WorldGenMonumentDoubleYRoom(face, definition, random);
	}

}
