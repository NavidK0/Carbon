package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

class WorldGenMonumentFitSimpleRoomHelper implements WorldGenMonumentRoomFitHelper {

	public boolean a(WorldGenMonumentRoomDefinition definition) {
		return true;
	}

	public WorldGenMonumentPiece getPiece(BlockFace face, WorldGenMonumentRoomDefinition definition, Random var3) {
		definition.d = true;
		return new WorldGenMonumentSimpleRoom(face, definition, var3);
	}

}
