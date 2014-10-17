package net.minecraft.server.v1_7_R4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spigotmc.AsyncCatcher;
import org.spigotmc.TrackingRange;

public class EntityTracker {

	private static final Logger a = LogManager.getLogger();
	private final WorldServer world;
	private Set<EntityTrackerEntry> c = new HashSet<EntityTrackerEntry>();
	public IntHashMap trackedEntities = new IntHashMap();
	private int e;

	public EntityTracker(WorldServer worldserver) {
		this.world = worldserver;
		this.e = worldserver.getMinecraftServer().getPlayerList().d();
	}

	public void track(Entity entity) {
		if ((entity instanceof EntityPlayer)) {
			addEntity(entity, 512, 2);
			EntityPlayer entityplayer = (EntityPlayer) entity;
			Iterator<EntityTrackerEntry> iterator = this.c.iterator();
			while (iterator.hasNext()) {
				EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();
				if (entitytrackerentry.tracker != entityplayer) {
					entitytrackerentry.updatePlayer(entityplayer);
				}
			}
		} else if ((entity instanceof EntityFishingHook)) {
			addEntity(entity, 64, 5, true);
		} else if ((entity instanceof EntityArrow)) {
			addEntity(entity, 64, 20, false);
		} else if ((entity instanceof EntitySmallFireball)) {
			addEntity(entity, 64, 10, false);
		} else if ((entity instanceof EntityFireball)) {
			addEntity(entity, 64, 10, false);
		} else if ((entity instanceof EntitySnowball)) {
			addEntity(entity, 64, 10, true);
		} else if ((entity instanceof EntityEnderPearl)) {
			addEntity(entity, 64, 10, true);
		} else if ((entity instanceof EntityEnderSignal)) {
			addEntity(entity, 64, 4, true);
		} else if ((entity instanceof EntityEgg)) {
			addEntity(entity, 64, 10, true);
		} else if ((entity instanceof EntityPotion)) {
			addEntity(entity, 64, 10, true);
		} else if ((entity instanceof EntityThrownExpBottle)) {
			addEntity(entity, 64, 10, true);
		} else if ((entity instanceof EntityFireworks)) {
			addEntity(entity, 64, 10, true);
		} else if ((entity instanceof EntityItem)) {
			addEntity(entity, 64, 20, true);
		} else if ((entity instanceof EntityMinecartAbstract)) {
			addEntity(entity, 80, 3, true);
		} else if ((entity instanceof EntityBoat)) {
			addEntity(entity, 80, 3, true);
		} else if ((entity instanceof EntitySquid)) {
			addEntity(entity, 64, 3, true);
		} else if ((entity instanceof EntityWither)) {
			addEntity(entity, 80, 3, false);
		} else if ((entity instanceof EntityBat)) {
			addEntity(entity, 80, 3, false);
		} else if ((entity instanceof IAnimal)) {
			addEntity(entity, 80, 3, true);
		} else if ((entity instanceof EntityEnderDragon)) {
			addEntity(entity, 160, 3, true);
		} else if ((entity instanceof EntityTNTPrimed)) {
			addEntity(entity, 160, 10, true);
		} else if ((entity instanceof EntityFallingBlock)) {
			addEntity(entity, 160, 20, true);
		} else if ((entity instanceof EntityHanging)) {
			addEntity(entity, 160, Integer.MAX_VALUE, false);
		} else if ((entity instanceof EntityExperienceOrb)) {
			addEntity(entity, 160, 20, true);
		} else if ((entity instanceof EntityEnderCrystal)) {
			addEntity(entity, 256, Integer.MAX_VALUE, false);
		} else if (entity.getClass() != null && entity.getClass().getSimpleName().equals("EntityArmorStand")) {
			addEntity(entity, 160, 3, true);
		}
	}

	public void addEntity(Entity entity, int i, int j) {
		addEntity(entity, i, j, false);
	}

	@SuppressWarnings("unchecked")
	public void addEntity(Entity entity, int i, int j, boolean flag) {
		AsyncCatcher.catchOp("entity track");
		i = TrackingRange.getEntityTrackingRange(entity, i);
		if (i > this.e) {
			i = this.e;
		}
		try {
			if (this.trackedEntities.b(entity.getId())) {
				throw new IllegalStateException("Entity is already tracked!");
			}
			EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(entity, i, j, flag);

			this.c.add(entitytrackerentry);
			this.trackedEntities.a(entity.getId(), entitytrackerentry);
			entitytrackerentry.scanPlayers(this.world.players);
		} catch (Throwable throwable) {
			CrashReport crashreport = CrashReport.a(throwable, "Adding entity to track");
			CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity To Track");

			crashreportsystemdetails.a("Tracking range", i + " blocks");
			crashreportsystemdetails.a("Update interval", new CrashReportEntityTrackerUpdateInterval(this, j));
			entity.a(crashreportsystemdetails);
			CrashReportSystemDetails crashreportsystemdetails1 = crashreport.a("Entity That Is Already Tracked");

			((EntityTrackerEntry) this.trackedEntities.get(entity.getId())).tracker.a(crashreportsystemdetails1);
			try {
				throw new ReportedException(crashreport);
			} catch (ReportedException reportedexception) {
				a.error("\"Silently\" catching entity tracking error.", reportedexception);
			}
		}
	}

	public void untrackEntity(Entity entity) {
		AsyncCatcher.catchOp("entity untrack");
		if ((entity instanceof EntityPlayer)) {
			EntityPlayer entityplayer = (EntityPlayer) entity;
			Iterator<EntityTrackerEntry> iterator = this.c.iterator();
			while (iterator.hasNext()) {
				EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();

				entitytrackerentry.a(entityplayer);
			}
		}
		EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry) this.trackedEntities.d(entity.getId());
		if (entitytrackerentry1 != null) {
			this.c.remove(entitytrackerentry1);
			entitytrackerentry1.a();
		}
	}

	@SuppressWarnings("unchecked")
	public void updatePlayers() {
		ArrayList<EntityPlayer> arraylist = new ArrayList<EntityPlayer>();
		Iterator<EntityTrackerEntry> iterator = this.c.iterator();
		while (iterator.hasNext()) {
			EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();

			entitytrackerentry.track(this.world.players);
			if ((entitytrackerentry.n) && ((entitytrackerentry.tracker instanceof EntityPlayer))) {
				arraylist.add((EntityPlayer) entitytrackerentry.tracker);
			}
		}
		for (int i = 0; i < arraylist.size(); i++) {
			EntityPlayer entityplayer = (EntityPlayer) arraylist.get(i);
			Iterator<EntityTrackerEntry> iterator1 = this.c.iterator();
			while (iterator1.hasNext()) {
				EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry) iterator1.next();
				if (entitytrackerentry1.tracker != entityplayer) {
					entitytrackerentry1.updatePlayer(entityplayer);
				}
			}
		}
	}

	public void a(Entity entity, Packet packet) {
		EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) this.trackedEntities.get(entity.getId());
		if (entitytrackerentry != null) {
			entitytrackerentry.broadcast(packet);
		}
	}

	public void sendPacketToEntity(Entity entity, Packet packet) {
		EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) this.trackedEntities.get(entity.getId());
		if (entitytrackerentry != null) {
			entitytrackerentry.broadcastIncludingSelf(packet);
		}
	}

	public void untrackPlayer(EntityPlayer entityplayer) {
		Iterator<EntityTrackerEntry> iterator = this.c.iterator();
		while (iterator.hasNext()) {
			EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();

			entitytrackerentry.clear(entityplayer);
		}
	}

	public void a(EntityPlayer entityplayer, Chunk chunk) {
		Iterator<EntityTrackerEntry> iterator = this.c.iterator();
		while (iterator.hasNext()) {
			EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();
			if ((entitytrackerentry.tracker != entityplayer) && (entitytrackerentry.tracker.ah == chunk.locX) && (entitytrackerentry.tracker.aj == chunk.locZ)) {
				entitytrackerentry.updatePlayer(entityplayer);
			}
		}
	}
}
