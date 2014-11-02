package net.minecraft.server.v1_7_R4;

import java.util.HashMap;

import com.google.common.collect.Maps;

public class EntitySpawnZone {

	public static void init() {
	}

	private static final HashMap<Class<? extends Entity>, EnumEntitySpawnZone> zones = Maps.newHashMap();

	public static EnumEntitySpawnZone getSpawnZone(Class<? extends Entity> clazz) {
		return zones.get(clazz);
	}

	public static void register(Class<? extends Entity> clazz, EnumEntitySpawnZone zone) {
		zones.put(clazz, zone);
	}

	static {
		register(EntityBat.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityChicken.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityCow.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityHorse.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityMushroomCow.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityOcelot.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityPig.class, EnumEntitySpawnZone.ON_GROUND);
		//register(EntityRabbit.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntitySheep.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntitySnowman.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntitySquid.class, EnumEntitySpawnZone.IN_WATER);
		register(EntityIronGolem.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityWolf.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityVillager.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityEnderDragon.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityWither.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityBlaze.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityCaveSpider.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityCreeper.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityEnderman.class, EnumEntitySpawnZone.ON_GROUND);
		//register(EntityEndermite.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityGhast.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityGiantZombie.class, EnumEntitySpawnZone.ON_GROUND);
		//register(EntityGuardian.class, EnumEntitySpawnZone.IN_WATER);
		register(EntityMagmaCube.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityPigZombie.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntitySilverfish.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntitySkeleton.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntitySlime.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntitySpider.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityWitch.class, EnumEntitySpawnZone.ON_GROUND);
		register(EntityZombie.class, EnumEntitySpawnZone.ON_GROUND);
	}

}
