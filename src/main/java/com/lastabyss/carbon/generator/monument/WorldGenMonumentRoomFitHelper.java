package com.lastabyss.carbon.generator.monument;

import java.util.Random;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

interface WorldGenMonumentRoomFitHelper {

	boolean a(WorldGenMonumentRoomDefinition var1);

	WorldGenMonumentPiece getPiece(BlockFace var1, WorldGenMonumentRoomDefinition var2, Random var3);

}
