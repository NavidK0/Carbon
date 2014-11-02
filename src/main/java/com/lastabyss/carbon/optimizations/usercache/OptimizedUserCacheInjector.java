package com.lastabyss.carbon.optimizations.usercache;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.lastabyss.carbon.utils.Utilities;

import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.UserCache;

public class OptimizedUserCacheInjector {

	public static void injectUserCache() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		UserCache oldUserCache = MinecraftServer.getServer().getUserCache();
		File oldUserCacheFile = (File) Utilities.setAccessible(Field.class, oldUserCache.getClass().getDeclaredField("g"), true).get(oldUserCache);
		OptimizedUserCache newUserCache = new OptimizedUserCache(MinecraftServer.getServer(), oldUserCacheFile);
		Field field = MinecraftServer.class.getDeclaredField("X");
		field.setAccessible(true);
		Field fieldModifiers = Field.class.getDeclaredField("modifiers");
		fieldModifiers.setAccessible(true);
		fieldModifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(MinecraftServer.getServer(), newUserCache);
	}

}
