package com.lastabyss.carbon;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sun.reflect.ConstructorAccessor;
import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

public class DynamicEnumType {

	private static void setField(Field field, Object target, Object value) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		FieldAccessor fieldAccessor = ReflectionFactory.getReflectionFactory().newFieldAccessor(field, true);
		fieldAccessor.set(target, value);
	}

	private static void cleanEnumCache(Class<?> enumClass) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		setField(Class.class.getDeclaredField("enumConstantDirectory"), enumClass, null);
	}

	public static Object makeEnum(Class<?> enumClass, String name, int ordinal, Class<?>[] paramTypes, Object[] paramValues) throws Exception {
		ArrayList<Class<?>> allParamTypes = new ArrayList<Class<?>>();
		allParamTypes.add(String.class);
		allParamTypes.add(Integer.TYPE);
		allParamTypes.addAll(Arrays.asList(paramTypes));

		ArrayList<Object> allParamValues = new ArrayList<Object>();
		allParamValues.add(name);
		allParamValues.add(Integer.valueOf(ordinal));
		allParamValues.addAll(Arrays.asList(paramValues));

		Constructor<?> enumConstructor = enumClass.getDeclaredConstructor((Class[]) allParamTypes.toArray(new Class[0]));
		ConstructorAccessor constructorAccessor = ReflectionFactory.getReflectionFactory().newConstructorAccessor(enumConstructor);
		return constructorAccessor.newInstance(allParamValues.toArray(new Object[0]));
	}

	@SuppressWarnings("unchecked")
	public static <T extends Enum<?>> T addEnum(Class<T> enumType, String enumName, Class<?>[] paramTypes, Object[] paramValues) {
		if (!Enum.class.isAssignableFrom(enumType)) {
			throw new RuntimeException("class " + enumType + " is not an instance of Enum");
		}
		try {
			Field valuesField = enumType.getDeclaredField("$VALUES");

			valuesField.setAccessible(true);
			T[] previousValues = (T[]) valuesField.get(enumType);
			List<T> values = new ArrayList<T>(Arrays.asList(previousValues));

			T newValue = (T) makeEnum(enumType, enumName, values.size(), paramTypes, paramValues);
			values.add(newValue);

			setField(valuesField, null, values.toArray((Enum[]) Array.newInstance(enumType, 0)));

			cleanEnumCache(enumType);

			return newValue;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
