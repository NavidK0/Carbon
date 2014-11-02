package com.lastabyss.carbon.optimizations.usercache;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import net.minecraft.server.v1_7_R4.UserCache;
import net.minecraft.util.com.google.gson.JsonDeserializationContext;
import net.minecraft.util.com.google.gson.JsonDeserializer;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonParseException;
import net.minecraft.util.com.google.gson.JsonSerializationContext;
import net.minecraft.util.com.google.gson.JsonSerializer;
import net.minecraft.util.com.mojang.authlib.GameProfile;

public class UserCacheEntryJsonSerializer implements JsonDeserializer<UserCacheEntry>, JsonSerializer<UserCacheEntry> {

	@Override
	public JsonElement serialize(UserCacheEntry entry, Type type, JsonSerializationContext ctx) {
		JsonObject jsonobject = new JsonObject();
		jsonobject.addProperty("name", entry.getProfile().getName());
		UUID uuid = entry.getProfile().getId();
		jsonobject.addProperty("uuid", uuid == null ? "" : uuid.toString());
		jsonobject.addProperty("expiresOn", UserCache.a.format(entry.getExpireDate()));
		return jsonobject;
	}

	@Override
	public UserCacheEntry deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		if (element.isJsonObject()) {
			JsonObject jsonobject = element.getAsJsonObject();
			JsonElement nameElement = jsonobject.get("name");
			JsonElement uuidElement = jsonobject.get("uuid");
			JsonElement expireDateElement = jsonobject.get("expiresOn");
			if ((nameElement != null) && (uuidElement != null)) {
				String name = uuidElement.getAsString();
				String uuidstring = nameElement.getAsString();
				Date date = null;
				if (expireDateElement != null) {
					try {
						date = UserCache.a.parse(expireDateElement.getAsString());
					} catch (ParseException parseexception) {
						date = null;
					}
				}
				if ((uuidstring != null) && (name != null)) {
					UUID uuid;
					try {
						uuid = UUID.fromString(name);
					} catch (Throwable throwable) {
						return null;
					}
					UserCacheEntry usercacheentry = new UserCacheEntry(new GameProfile(uuid, uuidstring), date);

					return usercacheentry;
				}
				return null;
			}
			return null;
		}
		return null;
	}

}
