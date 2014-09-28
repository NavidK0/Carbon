package net.o2gaming.carbon.generator;

import net.minecraft.util.com.google.gson.JsonArray;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonPrimitive;
import net.minecraft.util.com.google.gson.JsonSyntaxException;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;

public class JSONParser {

	public static boolean isArray(JsonObject jsonObject, String path) {
		return !has(jsonObject, path) ? false : jsonObject.get(path).isJsonArray();
	}

	public static String getString(JsonObject jsonObject, String path) {
		if (jsonObject.has(path)) {
			return getAsString(jsonObject.get(path), path);
		} else {
			throw new JsonSyntaxException("Missing " + path + ", expected to find a string");
		}
	}

	public static boolean getBoolean(JsonObject jsonObject, String path, boolean defaultValue) {
		return jsonObject.has(path) ? getAsBoolean(jsonObject.get(path), path) : defaultValue;
	}

	public static float getFloat(JsonObject jsonObject, String path, float defaultValue) {
		return jsonObject.has(path) ? getAsFloat(jsonObject.get(path), path) : defaultValue;
	}

	public static int getInt(JsonObject jsonObject, String path) {
		if (jsonObject.has(path)) {
			return getAsInt(jsonObject.get(path), path);
		} else {
			throw new JsonSyntaxException("Missing " + path + ", expected to find a Int");
		}
	}

	public static int getInt(JsonObject jsonObject, String path, int defaultValue) {
		return jsonObject.has(path) ? getAsInt(jsonObject.get(path), path) : defaultValue;
	}

	public static JsonObject getJsonObject(JsonElement jsonObject, String path) {
		if (jsonObject.isJsonObject()) {
			return jsonObject.getAsJsonObject();
		} else {
			throw new JsonSyntaxException("Expected " + path + " to be a JsonObject, was " + toString(jsonObject));
		}
	}

	public static JsonArray getArray(JsonObject jsonObject, String path) {
		if (jsonObject.has(path)) {
			return getAsArray(jsonObject.get(path), path);
		} else {
			throw new JsonSyntaxException("Missing " + path + ", expected to find a JsonArray");
		}
	}

	private static String toString(JsonElement jsonElement) {
		String string = StringUtils.abbreviateMiddle(String.valueOf(jsonElement), "...", 10);
		if (jsonElement == null) {
			return "null (missing)";
		} else if (jsonElement.isJsonNull()) {
			return "null (json)";
		} else if (jsonElement.isJsonArray()) {
			return "an array (" + string + ")";
		} else if (jsonElement.isJsonObject()) {
			return "an object (" + string + ")";
		} else {
			if (jsonElement.isJsonPrimitive()) {
				JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
				if (jsonPrimitive.isNumber()) {
					return "a number (" + string + ")";
				}

				if (jsonPrimitive.isBoolean()) {
					return "a boolean (" + string + ")";
				}
			}
			return string;
		}
	}

	private static boolean has(JsonObject jsonObject, String path) {
		return jsonObject == null ? false : jsonObject.get(path) != null;
	}

	private static String getAsString(JsonElement element, String path) {
		if (element.isJsonPrimitive()) {
			return element.getAsString();
		} else {
			throw new JsonSyntaxException("Expected " + path + " to be a string, was " + toString(element));
		}
	}

	private static boolean getAsBoolean(JsonElement jsonElement, String path) {
		if (jsonElement.isJsonPrimitive()) {
			return jsonElement.getAsBoolean();
		} else {
			throw new JsonSyntaxException("Expected " + path + " to be a Boolean, was " + toString(jsonElement));
		}
	}

	private static float getAsFloat(JsonElement jsonElement, String path) {
		if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
			return jsonElement.getAsFloat();
		} else {
			throw new JsonSyntaxException("Expected " + path + " to be a Float, was " + toString(jsonElement));
		}
	}

	private static int getAsInt(JsonElement jsonElement, String path) {
		if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
			return jsonElement.getAsInt();
		} else {
			throw new JsonSyntaxException("Expected " + path + " to be a Int, was " + toString(jsonElement));
		}
	}

	private static JsonArray getAsArray(JsonElement jsonElement, String path) {
		if (jsonElement.isJsonArray()) {
			return jsonElement.getAsJsonArray();
		} else {
			throw new JsonSyntaxException("Expected " + path + " to be a JsonArray, was " + toString(jsonElement));
		}
	}

}