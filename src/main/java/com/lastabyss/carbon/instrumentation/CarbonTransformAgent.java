package com.lastabyss.carbon.instrumentation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.server.v1_7_R4.EnumEntitySpawnZone;
import net.minecraft.server.v1_7_R4.EntitySpawnZone;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.craftbukkit.v1_7_R4.inventory.BannerMeta;
import org.bukkit.craftbukkit.v1_7_R4.inventory.BannerMeta.BannerPattern;

import com.google.common.collect.ImmutableMap;

import java.lang.instrument.UnmodifiableClassException;

/**
 *
 * @author Navid
 */
public class CarbonTransformAgent implements ClassFileTransformer {

	//All pretransformed classes should be located in the pretransformedclasses folder inside the jar

	private static Instrumentation instrumentation = null;
	private static CarbonTransformAgent transformer;

	// Public static void main() but for this agent
	@SuppressWarnings("unchecked")
	public static void agentmain(String string, Instrumentation instrument) {
		instrumentation = instrument;

		LogManager.getLogger().info("[Carbon] Loaded transformer agent!");

		transformer = new CarbonTransformAgent();
		instrumentation.addTransformer(transformer);
		try {
			// redefine classes
			instrumentation.redefineClasses(
				new ClassDefinition(
					Class.forName("net.minecraft.server.v1_7_R4.DataWatcher"),
					getPreTransformedClass("net/minecraft/server/v1_7_R4/DataWatcher")
				),
				new ClassDefinition(
					Class.forName("net.minecraft.server.v1_7_R4.EntityTracker"),
					getPreTransformedClass("net/minecraft/server/v1_7_R4/EntityTracker")
				),
				new ClassDefinition(
					Class.forName("net.minecraft.server.v1_7_R4.EntityTrackerEntry"),
					getPreTransformedClass("net/minecraft/server/v1_7_R4/EntityTrackerEntry")
				),
				new ClassDefinition(
					Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack"),
					getPreTransformedClass("org/bukkit/craftbukkit/v1_7_R4/inventory/CraftItemStack")
				),
				new ClassDefinition(
					Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaItem"),
					getPreTransformedClass("org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem")
				),
				new ClassDefinition(
					Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemFactory"),
					getPreTransformedClass("org/bukkit/craftbukkit/v1_7_R4/inventory/CraftItemFactory")
				),
				new ClassDefinition(
					Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaItem$SerializableMeta"),
					getPreTransformedClass("org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem$SerializableMeta")
				),
				new ClassDefinition(
					Class.forName("net.minecraft.server.v1_7_R4.SpawnerCreature"),
					getPreTransformedClass("net/minecraft/server/v1_7_R4/SpawnerCreature")
				),
				new ClassDefinition(
					Class.forName("net.minecraft.server.v1_7_R4.SpawnerCreature$1"),
					getPreTransformedClass("net/minecraft/server/v1_7_R4/SpawnerCreature$1")
				),
				new ClassDefinition(
					Class.forName("org.bukkit.Material"),
					getPreTransformedClass("org/bukkit/Material")
				)
			);

			// inject banner meta into the server
			ClassLoader cl = ClassLoader.getSystemClassLoader();
			Field packagesField = cl.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("packages");
			packagesField.setAccessible(true);
			HashMap<String, Package> packages = (HashMap<String, Package>) packagesField.get(cl);
			synchronized (packages) {
				for (Package pkg : packages.values()) {
					if (pkg.getName().equals("org.bukkit.craftbukkit.v1_7_R4.inventory")) {
						// unseal
						Object sealBase = null;
						Field sealBaseField = pkg.getClass().getDeclaredField("sealBase");
						sealBaseField.setAccessible(true);
						sealBase = sealBaseField.get(pkg);
						sealBaseField.set(pkg, null);
						// load classes
						BannerMeta.init();
						BannerPattern.init();
						// seal back
						sealBaseField.set(pkg, sealBase);
					}
					if (pkg.getName().equals("net.minecraft.server.v1_7_R4")) {
						// unseal
						Object sealBase = null;
						Field sealBaseField = pkg.getClass().getDeclaredField("sealBase");
						sealBaseField.setAccessible(true);
						sealBase = sealBaseField.get(pkg);
						sealBaseField.set(pkg, null);
						// load classes
						EnumEntitySpawnZone.init();
						EntitySpawnZone.init();
						// seal back
						sealBaseField.set(pkg, sealBase);
					}
				}
			}

			// add bannermeta to CraftMetaItem SerializableMeta class
			ImmutableMap<Class<?>, String> newClassMap = ImmutableMap.<Class<?>, String> builder()
				.put(Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaBook"), "BOOK")
				.put(Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaSkull"), "SKULL")
				.put(Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaLeatherArmor"), "LEATHER_ARMOR")
				.put(Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaMap"), "MAP")
				.put(Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaPotion"), "POTION")
				.put(Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaEnchantedBook"), "ENCHANTED")
				.put(Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaFirework"), "FIREWORK")
				.put(Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaCharge"), "FIREWORK_EFFECT")
				.put(Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.BannerMeta"), "BANNER")
				.put(Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaItem"), "UNSPECIFIC")
			.build();
			ImmutableMap<String, Constructor<?>> newConstructorMap;
			ImmutableMap.Builder<String, Constructor<?>> classConstructorBuilder = ImmutableMap.builder();
			for (Entry<Class<?>, String> mapping : newClassMap.entrySet()) {
				try {
					classConstructorBuilder.put(mapping.getValue(), (mapping.getKey()).getDeclaredConstructor(new Class[] { Map.class }));
				} catch (NoSuchMethodException e) {
					throw new AssertionError(e);
				}
			}
			newConstructorMap = classConstructorBuilder.build();
			Class<? extends ConfigurationSerializable> smclass = (Class<? extends ConfigurationSerializable>) Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaItem$SerializableMeta");
			setStaticFinalField(smclass, "classMap", newClassMap);
			setStaticFinalField(smclass, "constructorMap", newConstructorMap);
		} catch (ClassNotFoundException | IOException | UnmodifiableClassException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | AssertionError t) {
			t.printStackTrace(System.out);
			Bukkit.shutdown();
		}
	}

	private static void setStaticFinalField(Class<?> clazz, String fieldname, Object newValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = clazz.getDeclaredField(fieldname);
		field.setAccessible(true);
		Field fieldModifiers = Field.class.getDeclaredField("modifiers");
		fieldModifiers.setAccessible(true);
		fieldModifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, newValue);
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		if (
			className.equals("org/bukkit/craftbukkit/v1_7_R4/inventory/CraftItemStack") ||
			className.equals("org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem") ||
			className.equals("org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem$SerializableMeta") ||
			className.equals("org/bukkit/craftbukkit/v1_7_R4/inventory/CraftItemFactory") ||
			className.equals("net/minecraft/server/v1_7_R4/EntityTracker") ||
			className.equals("net/minecraft/server/v1_7_R4/EntityTrackerEntry") ||
			className.equals("net/minecraft/server/v1_7_R4/DataWatcher") ||
			className.equals("net/minecraft/server/v1_7_R4/SpawnerCreature") ||
			className.equals("net/minecraft/server/v1_7_R4/SpawnerCreature$1") ||
			className.equals("org/bukkit/Material")
		) {
			LogManager.getLogger().log(Level.INFO, "[Carbon] Transforming "+className);
			try {
				return getPreTransformedClass(className);
			} catch (Exception e) {
				e.printStackTrace(System.out);
				System.out.println("Failed to transform class!");
			}
		}
		return null;
	}

        /**
         * Gets class before transformation.
         * @param className
         * @return
         * @throws IOException 
         */
	private static byte[] getPreTransformedClass(String className) throws IOException {
		InputStream stream = CarbonTransformAgent.class.getClassLoader().getResourceAsStream("pretransformedclasses/" + className + ".class");
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[4096];
		while ((nRead = stream.read(data)) != -1) {
			buffer.write(data, 0, nRead);
		}
		buffer.flush();
		return buffer.toByteArray();
	}

	public static void killAgent() {
		instrumentation.removeTransformer(transformer);
	}

}
