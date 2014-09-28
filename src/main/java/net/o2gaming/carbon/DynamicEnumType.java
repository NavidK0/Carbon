package net.o2gaming.carbon;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

public class DynamicEnumType
{
	private static Method reflectionFactory;
	private static Object reflectionRealFactory;
	private static Method newconstructorAccesor;
	private static Method constructorAccesor;
	private static Method newFieldAccesor;
	private static Method fieldAccesorSet;

	public static void loadReflection() throws NoSuchMethodException,
			SecurityException, ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException
	{
		reflectionFactory = Class.forName("sun.reflect.ReflectionFactory")
				.getDeclaredMethod("getReflectionFactory", new Class[0]);

		reflectionFactory.setAccessible(true);
		reflectionRealFactory = reflectionFactory.invoke(null, new Object[0]);

		fieldAccesorSet = Class.forName("sun.reflect.FieldAccessor")
				.getDeclaredMethod("set",
						new Class[] { Object.class, Object.class });
		newFieldAccesor = Class.forName("sun.reflect.ReflectionFactory")
				.getDeclaredMethod("newFieldAccessor",
						new Class[] { Field.class, Boolean.TYPE });
		constructorAccesor = Class.forName("sun.reflect.ConstructorAccessor")
				.getDeclaredMethod("newInstance", Object[].class);

		constructorAccesor.setAccessible(true);
		newconstructorAccesor = Class.forName("sun.reflect.ReflectionFactory")
				.getDeclaredMethod("newConstructorAccessor",
						new Class[] { Constructor.class });
	}

	private static void setFailsafeFieldValue(Field field, Object target,
			Object value) throws NoSuchFieldException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException
	{
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		int modifiers = modifiersField.getInt(field);
		modifiers &= -17;
		modifiersField.setInt(field, modifiers);
		Object fieldAccesor = newFieldAccesor.invoke(reflectionRealFactory,
				new Object[] { field, Boolean.valueOf(false) });
		fieldAccesorSet.invoke(fieldAccesor, new Object[] { target, value });
	}

	private static void blankField(Class<?> enumClass, String fieldName)
			throws NoSuchFieldException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException
	{
		for (Field field : Class.class.getDeclaredFields())
		{
			if (field.getName().contains(fieldName))
			{
				AccessibleObject.setAccessible(new Field[] { field }, true);
				setFailsafeFieldValue(field, enumClass, null);
				break;
			}
		}
	}

	private static void cleanEnumCache(Class<?> enumClass)
			throws NoSuchFieldException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException
	{
		blankField(enumClass, "enumConstantDirectory");
		blankField(enumClass, "enumConstants");
	}

	private static Object getConstructorAccessor(Class<?> enumClass,
			Class<?>[] additionalParameterTypes) throws NoSuchMethodException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, SecurityException
	{
		Class<?>[] parameterTypes = new Class[additionalParameterTypes.length + 2];
		parameterTypes[0] = String.class;
		parameterTypes[1] = Integer.TYPE;
		System.arraycopy(additionalParameterTypes, 0, parameterTypes, 2,
				additionalParameterTypes.length);
		return newconstructorAccesor
				.invoke(reflectionRealFactory,  enumClass
						.getDeclaredConstructor(parameterTypes) );
	}
	public static Object makeEnum(Class<?> enumClass, String value,
			int ordinal, Class<?>[] additionalTypes, Object[] additionalValues)
			throws Exception
	{
		Object[] parms = new Object[additionalValues.length + 2];
		parms[0] = value;
		parms[1] = Integer.valueOf(ordinal);
		System.arraycopy(additionalValues, 0, parms, 2, additionalValues.length);

		return (Enum<?>) enumClass.cast(constructorAccesor.invoke(
				getConstructorAccessor(enumClass, additionalTypes),
				new Object[] { parms }));
	}

	@SuppressWarnings("unchecked")
	public static <T extends Enum<?>> T addEnum(Class<T> enumType,
			String enumName, Class<?>[] paramTypes, Object[] paramValues)
	{
		if (!Enum.class.isAssignableFrom(enumType))
		{
			throw new RuntimeException("class " + enumType
					+ " is not an instance of Enum");
		}

		Field valuesField = null;
		Field[] fields = Material.class.getDeclaredFields();
		for (Field field : fields)
		{
			if (field.getName().contains("$VALUES"))
			{
				valuesField = field;
				break;
			}
		}
		AccessibleObject.setAccessible(new Field[] { valuesField }, true);
		try
		{
			T[] previousValues = (T[]) valuesField.get(enumType);
			List<T> values = new ArrayList<T>(Arrays.asList(previousValues));

			T newValue = (T) makeEnum(enumType, enumName, values.size(),
					paramTypes, paramValues);
			values.add(newValue);

			setFailsafeFieldValue(valuesField, null,
					values.toArray((T[]) Array.newInstance(enumType, 0)));

			cleanEnumCache(enumType);

			return newValue;
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}