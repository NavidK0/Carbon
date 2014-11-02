package com.lastabyss.carbon.optimizations.usercache;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class UserCacheEntryType implements ParameterizedType {

	@Override
	public Type[] getActualTypeArguments() {
		return new Type[] {
			UserCacheEntry.class
		};
	}

	@Override
	public Type getRawType() {
		return List.class;
	}

	@Override
	public Type getOwnerType() {
		return null;
	}

}
