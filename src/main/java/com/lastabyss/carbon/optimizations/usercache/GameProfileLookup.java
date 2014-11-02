package com.lastabyss.carbon.optimizations.usercache;

import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.ProfileLookupCallback;

public class GameProfileLookup implements ProfileLookupCallback {

	private GameProfile[] gameProfileHolderArray;

	public GameProfileLookup(GameProfile[] gameProfileHolderArray) {
		this.gameProfileHolderArray = gameProfileHolderArray;
	}

	@Override
	public void onProfileLookupFailed(GameProfile profile, Exception exception) {
		this.gameProfileHolderArray[0] = null;
	}

	@Override
	public void onProfileLookupSucceeded(GameProfile profile) {
		this.gameProfileHolderArray[0] = profile;
	}

}
