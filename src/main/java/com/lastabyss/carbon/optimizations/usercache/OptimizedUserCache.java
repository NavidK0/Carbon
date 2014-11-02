package com.lastabyss.carbon.optimizations.usercache;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.spigotmc.SpigotConfig;

import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.UserCache;
import net.minecraft.util.com.google.common.base.Charsets;
import net.minecraft.util.com.google.common.collect.Lists;
import net.minecraft.util.com.google.common.io.Files;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;
import net.minecraft.util.com.mojang.authlib.Agent;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.org.apache.commons.io.IOUtils;

public class OptimizedUserCache extends UserCache {

	private File userCacheFile;
	private LinkedHashMap<UUID, UserCacheEntry> uuidToProfile = new LinkedHashMap<UUID, UserCacheEntry>(16, 0.75F, true);
	private HashMap<String, UserCacheEntry> stringToProfile = new HashMap<String, UserCacheEntry>();
	private Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(UserCacheEntry.class, new UserCacheEntryJsonSerializer()).create();
	private UserCacheEntryType type = new UserCacheEntryType();

	public OptimizedUserCache(MinecraftServer minecraftserver, File file) {
		super(minecraftserver, file);
		this.userCacheFile = file;
		b();
	}

	@Override
	public void a(GameProfile gameProfile) {
		String playername = gameProfile.getName().toLowerCase(Locale.ROOT);
		UUID uuid = gameProfile.getId();
		UserCacheEntry entry = new UserCacheEntry(gameProfile);
		if (uuidToProfile.containsKey(uuid)) {
			stringToProfile.remove(uuidToProfile.get(uuid).getProfile().getName().toLowerCase(Locale.ROOT));
			stringToProfile.put(playername, entry);
		} else {
			uuidToProfile.put(uuid, entry);
			stringToProfile.put(playername, entry);
		}
	}

	@Override
	public GameProfile getProfile(String name) {
		String playername = name.toLowerCase(Locale.ROOT);
		UserCacheEntry entry = stringToProfile.get(playername);
		if (entry != null && entry.isExpired()) {
			stringToProfile.remove(playername);
			uuidToProfile.remove(entry.getProfile().getId());
			return null;
		}
		if (entry != null) {
			uuidToProfile.get(entry.getProfile().getId()); //push profile to the top of access ordered linkedhashmap, do not remove this
			return entry.getProfile();
		} else {
			GameProfile profile = lookupProfile(MinecraftServer.getServer(), playername);
			if (profile != null) {
				a(profile);
				return profile;
			}
			return null;
		}
	}

	@Override
	public GameProfile a(UUID uuid) {
		UserCacheEntry entry = uuidToProfile.get(uuid);
		return entry == null ? null : entry.getProfile();
	}

	@Override
	public String[] a() {
		ArrayList<String> list = Lists.newArrayList(stringToProfile.keySet());
		return list.toArray(new String[list.size()]);
	}

	@Override
	public void c() {
		ArrayList<UserCacheEntry> list = new ArrayList<UserCacheEntry>();
		int saved = 0;
		for (UserCacheEntry entry : uuidToProfile.values()) {
			if (saved > SpigotConfig.userCacheCap) {
				break;
			}
			list.add(entry);
			saved++;
		}
		String data = gson.toJson(list);
		BufferedWriter bufferedwriter = null;
		try  {
			bufferedwriter = Files.newWriter(userCacheFile, Charsets.UTF_8);
			bufferedwriter.write(data);
		} catch (FileNotFoundException filenotfoundexception) {
		} catch (IOException ioexception) {
		} finally {
			IOUtils.closeQuietly(bufferedwriter);
		}
	}

	@Override
	public void b() {
		if (userCacheFile == null) {
			return;
		}
		List<UserCacheEntry> datalist = null;
		BufferedReader reader = null;
		try {
			reader = Files.newReader(userCacheFile, Charsets.UTF_8);
			datalist = this.gson.fromJson(reader, type);
		} catch (FileNotFoundException exception) {
		} finally {
			IOUtils.closeQuietly(reader);
		}
		if (datalist != null) {
			uuidToProfile.clear();
			stringToProfile.clear();
			for (UserCacheEntry entry : datalist) {
				uuidToProfile.put(entry.getProfile().getId(), entry);
				stringToProfile.put(entry.getProfile().getName().toLowerCase(Locale.ROOT), entry);
			}
		}
	}

	private GameProfile lookupProfile(MinecraftServer minecraftserver, String name) {
		GameProfile[] gameProfileArrayHolder = new GameProfile[1];
		GameProfileLookup gameProfileLookup = new GameProfileLookup(gameProfileArrayHolder);
		minecraftserver.getGameProfileRepository().findProfilesByNames(new String[] { name }, Agent.MINECRAFT, gameProfileLookup);
		if ((!minecraftserver.getOnlineMode()) && (gameProfileArrayHolder[0] == null)) {
			UUID uuid = EntityHuman.a(new GameProfile(null, name));
			GameProfile gameprofile = new GameProfile(uuid, name);
			gameProfileLookup.onProfileLookupSucceeded(gameprofile);
		}
		return gameProfileArrayHolder[0];
	}

}
