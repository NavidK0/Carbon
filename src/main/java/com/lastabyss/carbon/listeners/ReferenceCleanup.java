package com.lastabyss.carbon.listeners;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

import com.lastabyss.carbon.Carbon;

public class ReferenceCleanup implements Listener {

	private Carbon plugin;

	public ReferenceCleanup(Carbon plugin) {
		this.plugin = plugin;
	}

	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		if (plugin.getConfig().getBoolean("optimizations.cleanupreferences.player", false)) {
			executor.schedule(new Runnable() {
				@Override
				public void run() {
					nullFields(player);
				}
			}, 1, TimeUnit.SECONDS);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onWorldUnload(WorldUnloadEvent event) {
		final World world = event.getWorld();
		if (plugin.getConfig().getBoolean("optimizations.cleanupreferences.world", false)) {
			executor.schedule(new Runnable() {
				@Override
				public void run() {
					List<World> loadedworlds = Bukkit.getWorlds();
					for (World loadedworld : loadedworlds) {
						if (loadedworld == world) {
							return;
						}
					}
					nullFields(world);
				}
			}, 1, TimeUnit.SECONDS);
		}
	}

	private static void nullFields(Object obj) {
		Class<?> clazz = obj.getClass();
		do {
			for (Field field : clazz.getDeclaredFields()) {
				if (!field.getType().isPrimitive() && !Modifier.isStatic(field.getModifiers())) {
					try {
						setField(field, obj, null);
					} catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					}
				}
			}
		} while ((clazz = clazz.getSuperclass()) != null);
	}

	private static void setField(Field field, Object target, Object value) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		FieldAccessor fieldAccessor = ReflectionFactory.getReflectionFactory().newFieldAccessor(field, true);
		fieldAccessor.set(target, value);
	}

}
