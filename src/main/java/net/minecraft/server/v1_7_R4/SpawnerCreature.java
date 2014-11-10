package net.minecraft.server.v1_7_R4;

import java.util.List;

import net.minecraft.server.v1_7_R4.WeightedRandom;

import java.util.Random;

import net.minecraft.server.v1_7_R4.BiomeBase;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.IBlockAccess;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.GroupDataEntity;
import net.minecraft.server.v1_7_R4.BiomeMeta;
import net.minecraft.server.v1_7_R4.ChunkCoordinates;

import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.lang.Exception;

import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EnumCreatureType;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.EntityHuman;

import java.util.Iterator;

import org.bukkit.craftbukkit.v1_7_R4.util.LongHash;

import java.lang.Long;
import java.lang.Class;

import net.minecraft.server.v1_7_R4.WorldServer;
import net.minecraft.server.v1_7_R4.Chunk;
import net.minecraft.server.v1_7_R4.ChunkPosition;
import net.minecraft.server.v1_7_R4.World;

import java.lang.Boolean;

import org.bukkit.craftbukkit.v1_7_R4.util.LongObjectHashMap;

public final class SpawnerCreature {

	private LongObjectHashMap<Boolean> a = new LongObjectHashMap<Boolean>();

	public SpawnerCreature() {
	}

	protected static ChunkPosition getRandomPosition(World world, int chunkX, int chunkZ) {
		Chunk chunk = world.getChunkAt(chunkX, chunkZ);
		int x = chunkX * 16 + world.random.nextInt(16);
		int z = chunkZ * 16 + world.random.nextInt(16);
		int y = someStrangeMathOp(chunk.b(x & 0xF, z & 0xF) + 1, 16);
		int realY = world.random.nextInt(y > 0 ? y : chunk.h() + 16 - 1);
		return new ChunkPosition(x, realY, z);
	}

	private static int someStrangeMathOp(int i, int m) {
		if (m == 0) {
			return 0;
		} else if (i == 0) {
			return m;
		} else {
			if (i < 0) {
				m *= -1;
			}

			int remainder = i % m;
			return remainder == 0 ? i : i + m - remainder;
		}
	}

	@SuppressWarnings("unchecked")
	public int spawnEntities(WorldServer worldserver, boolean monsterSpawn, boolean animalSpawn, boolean timeFlag) {
		if (!monsterSpawn && !animalSpawn) {
			return 0;
		} else {
			int spawnedEntities = 0;
			this.a.clear();
			for (int i = 0; i < worldserver.players.size(); ++i) {
				EntityHuman entityhuman = (EntityHuman) worldserver.players.get(i);
				int chunkX = MathHelper.floor(entityhuman.locX / 16.0D);
				int chunkZ = MathHelper.floor(entityhuman.locZ / 16.0D);
				byte b = 8;
				for (int j = -b; j <= b; ++j) {
					for (int k = -b; k <= b; ++k) {
						boolean flag3 = j == -b || j == b || k == -b || k == b;
						long chunkCoords = LongHash.toLong(j + chunkX, k + chunkZ);
						if (!this.a.containsKey(chunkCoords)) {
							this.a.put(chunkCoords, flag3);
						}
					}
				}
			}
			ChunkCoordinates spawn = worldserver.getSpawn();
			for (EnumCreatureType enumcreaturetype : EnumCreatureType.values()) {
				int limit = enumcreaturetype.b();
				switch (enumcreaturetype) {
					case MONSTER:
						limit = worldserver.getWorld().getMonsterSpawnLimit();
						break;
					case CREATURE:
						limit = worldserver.getWorld().getAnimalSpawnLimit();
						break;
					case WATER_CREATURE:
						limit = worldserver.getWorld().getWaterAnimalSpawnLimit();
						break;
					case AMBIENT:
						limit = worldserver.getWorld().getAmbientSpawnLimit();
						break;
				}
				if (limit == 0) {
					continue;
				}
				if ((!enumcreaturetype.d() || animalSpawn) && (enumcreaturetype.d() || monsterSpawn) && (!enumcreaturetype.e() || timeFlag) && worldserver.a(enumcreaturetype.a()) <= limit * this.a.size() / 289) {
					Iterator<?> iterator = this.a.keySet().iterator();
					label110: while (iterator.hasNext()) {
						long key = ((Long) iterator.next()).longValue();
						if (!this.a.get(key)) {
							ChunkPosition chunkposition = getRandomPosition(worldserver, LongHash.msw(key), LongHash.lsw(key));
							int x = chunkposition.x;
							int y = chunkposition.y;
							int z = chunkposition.z;
							if (!worldserver.getType(x, y, z).r()) {
								int j2 = 0;
								int k2 = 0;
								while (k2 < 3) {
									int localX = x;
									int localY = y;
									int localZ = z;
									byte rndL = 6;
									BiomeMeta biomemeta = null;
									GroupDataEntity groupdataentity = null;
									int spawnedThisChunk = 0;
									while (true) {
										if (spawnedThisChunk < 4) {
											label103: {
												localX += worldserver.random.nextInt(rndL) - worldserver.random.nextInt(rndL);
												localY += worldserver.random.nextInt(1) - worldserver.random.nextInt(1);
												localZ += worldserver.random.nextInt(rndL) - worldserver.random.nextInt(rndL);
												float mobX = (float) localX + 0.5F;
												float mobY = (float) localY;
												float mobZ = (float) localZ + 0.5F;
												if (worldserver.findNearbyPlayer(mobX, mobY, mobZ, 24.0D) == null) {
													float diffX = mobX - spawn.x;
													float diffY = mobY - spawn.y;
													float diffZ = mobZ - spawn.z;
													float sqDist = diffX * diffX + diffY * diffY + diffZ * diffZ;
													if (sqDist >= 576.0D) {
														if (biomemeta == null) {
															biomemeta = worldserver.a(enumcreaturetype, localX, localY, localZ);
															if (biomemeta == null) {
																break label103;
															}
														}
														if (canSpawn(EntitySpawnZone.getSpawnZone(biomemeta.b), worldserver, localX, localY, localZ)) {
															EntityInsentient entity;
															try {
																entity = (EntityInsentient) biomemeta.b.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldserver });
															} catch (Exception exception) {
																exception.printStackTrace();
																return spawnedEntities;
															}
															entity.setPositionRotation((double) mobX, (double) mobY, (double) mobZ, worldserver.random.nextFloat() * 360.0F, 0.0F);
															if (entity.canSpawn()) {
																++j2;
																groupdataentity = entity.prepare(groupdataentity);
																worldserver.addEntity(entity, SpawnReason.NATURAL);
																if (j2 >= entity.bB()) {
																	continue label110;
																}
															}
															spawnedEntities += j2;
														}
													}
												}
												++spawnedThisChunk;
												continue;
											}
										}
										++k2;
										break;
									}
								}
							}
						}
					}
				}
			}
			return spawnedEntities;
		}
	}

	public static boolean a(EnumCreatureType enumcreaturetype, World world, int x, int y, int z) {
		if (enumcreaturetype.c() == Material.WATER) {
			return world.getType(x, y, z).getMaterial().isLiquid() && world.getType(x, y - 1, z).getMaterial().isLiquid() && !world.getType(x, y + 1, z).r();
		} else if (!World.a((IBlockAccess) world, x, y - 1, z)) {
			return false;
		} else {
			Block block = world.getType(x, y - 1, z);
			return block != Blocks.BEDROCK && !world.getType(x, y, z).r() && !world.getType(x, y, z).getMaterial().isLiquid() && !world.getType(x, y + 1, z).r();
		}
	}

	private static boolean canSpawn(EnumEntitySpawnZone spawnZone, World world, int x, int y, int z) {
		if (spawnZone == EnumEntitySpawnZone.IN_WATER) {
			return world.getType(x, y, z).getMaterial().isLiquid() && world.getType(x, y - 1, z).getMaterial().isLiquid() && !world.getType(x, y + 1, z).r();
		} else if (!World.a((IBlockAccess) world, x, y - 1, z)) {
			return false;
		} else {
			Block block = world.getType(x, y - 1, z);
			return block != Blocks.BEDROCK && !world.getType(x, y, z).r() && !world.getType(x, y, z).getMaterial().isLiquid() && !world.getType(x, y + 1, z).r();
		}
	}

	@SuppressWarnings("unchecked")
	public static void a(World world, BiomeBase biomebase, int origX, int origZ, int rndAddX, int rndAddZ, Random random) {
		List<?> meta = biomebase.getMobs(EnumCreatureType.CREATURE);
		if (!meta.isEmpty()) {
			while (random.nextFloat() < biomebase.g()) {
				BiomeMeta biomemeta = (BiomeMeta) WeightedRandom.a(world.random, meta);
				GroupDataEntity groupdataentity = null;
				int spawnCount = biomemeta.c + random.nextInt(1 + biomemeta.d - biomemeta.c);
				int x = origX + random.nextInt(rndAddX);
				int z = origZ + random.nextInt(rndAddZ);
				int xcopy = x;
				int zcopy = z;
				for (int i = 0; i < spawnCount; ++i) {
					boolean hasSpawned = false;
					for (int j = 0; !hasSpawned && j < 4; ++j) {
						int y = world.i(x, z);
						if (canSpawn(EnumEntitySpawnZone.ON_GROUND, world, x, y, z)) {
							float mobX = (float) x + 0.5F;
							float mobY = (float) y;
							float mobZ = (float) z + 0.5F;
							EntityInsentient entityinsentient;
							try {
								entityinsentient = (EntityInsentient) biomemeta.b.getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
							} catch (Exception exception) {
								exception.printStackTrace();
								continue;
							}
							entityinsentient.setPositionRotation((double) mobX, (double) mobY, (double) mobZ, random.nextFloat() * 360.0F, 0.0F);
							groupdataentity = entityinsentient.prepare(groupdataentity);
							world.addEntity(entityinsentient, SpawnReason.CHUNK_GEN);
							hasSpawned = true;
						}
						x += random.nextInt(5) - random.nextInt(5);
						for (z += random.nextInt(5) - random.nextInt(5); x < origX || x >= origX + rndAddX || z < origZ || z >= origZ + rndAddX; z = zcopy + random.nextInt(5) - random.nextInt(5)) {
							x = xcopy + random.nextInt(5) - random.nextInt(5);
						}
					}
				}
			}
		}
	}

}
