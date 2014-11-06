package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

class WorldGenMonumentZDoubleRoomFitHelper implements WorldGenMonumentRoomFitHelper {

	public boolean a(WorldGenMonumentRoomDefinition definition) {
		return definition.c[BlockFace.NORTH.getId()] && !definition.b[BlockFace.NORTH.getId()].d;
	}

	public WorldGenMonumentPiece getPiece(BlockFace face, WorldGenMonumentRoomDefinition definition, Random var3) {
		WorldGenMonumentRoomDefinition var4 = definition;
		if (!definition.c[BlockFace.NORTH.getId()] || definition.b[BlockFace.NORTH.getId()].d) {
			var4 = definition.b[BlockFace.SOUTH.getId()];
		}

		var4.d = true;
		var4.b[BlockFace.NORTH.getId()].d = true;
		return new WorldGenMonumentDoubleZRoom(face, var4, var3);
	}

}
