package com.lastabyss.carbon.optimizations.usercache;

import java.util.Calendar;
import java.util.Date;

import net.minecraft.util.com.mojang.authlib.GameProfile;

public class UserCacheEntry {

	private GameProfile profile;
	private Date expireDate;

	public UserCacheEntry(GameProfile profile) {
		this.profile = profile;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, 1);
		expireDate = calendar.getTime();
	}

	public UserCacheEntry(GameProfile profile, Date date) {
		this.profile = profile;
		this.expireDate = date;
	}

	public boolean isExpired() {
		return System.currentTimeMillis() > expireDate.getTime();
	}

	public long getExpireDate() {
		return expireDate.getTime();
	}

	public GameProfile getProfile() {
		return profile;
	}

}
