package com.lastabyss.carbon.ai;

import com.lastabyss.carbon.utils.nmsclasses.Position;

import net.minecraft.server.v1_7_R4.AttributeInstance;
import net.minecraft.server.v1_7_R4.ChunkCache;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.Navigation;
import net.minecraft.server.v1_7_R4.PathEntity;
import net.minecraft.server.v1_7_R4.Vec3D;
import net.minecraft.server.v1_7_R4.World;

public abstract class NavigationWrapper extends Navigation {

	public static double getSquaredDistance(Vec3D vec1, Vec3D vec2) {
		double x = vec1.a - vec2.a;
		double y = vec1.b - vec2.b;
		double z = vec1.c - vec2.c;
		return x * x + y * y + z * z;
	}

	protected EntityInsentient entity;
	protected World world;
	protected PathEntityWrapped path;
	protected double speed;
	private final AttributeInstance searchRange;
	private int ticks;
	private int lastPositionCheckTicks;
	private Vec3D lastPositionCheck = Vec3D.a(0.0D, 0.0D, 0.0D);
	private float multiplier = 1.0F;
	private final NavigationPathFinder pathfinder;

	public NavigationWrapper(EntityInsentient entity, World world) {
		super(entity, world);
		this.entity = entity;
		this.world = world;
		this.searchRange = entity.getAttributeInstance(GenericAttributes.b);
		this.pathfinder = this.createPathfinder();
	}

	protected abstract NavigationPathFinder createPathfinder();

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public float getRange() {
		return (float) this.searchRange.getValue();
	}

	public final PathEntityWrapped getPathTo(double x, double y, double z) {
		return this.getPathTo(new Position(MathHelper.floor(x), (int) y, MathHelper.floor(z)));
	}

	public PathEntityWrapped getPathTo(Position position) {
		if (!this.canNavigate()) {
			return null;
		} else {
			float range = this.getRange();
			Position entityPosition = new Position(this.entity);
			int blockRange = (int) (range + 8.0F);
			Position pos1 = entityPosition.add(-blockRange, -blockRange, -blockRange);
			Position pos2 = entityPosition.add(blockRange, blockRange, blockRange);
			ChunkCache chunkCache = new ChunkCache(this.world, pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ(), 0);
			PathEntityWrapped path = this.pathfinder.createPath(chunkCache, this.entity, position, range);
			return path;
		}
	}

	public boolean moveTo(double x, double y, double z, double speed) {
		PathEntityWrapped path = this.getPathTo((double) MathHelper.floor(x), (double) ((int) y), (double) MathHelper.floor(z));
		return this.setPath(path, speed);
	}

	public void setUnknownMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}

	public PathEntityWrapped getPathTo(Entity entity) {
		if (!this.canNavigate()) {
			return null;
		} else {
			float range = this.getRange();
			Position up = (new Position(this.entity)).getUp();
			int blockRange = (int) (range + 16.0F);
			Position pos1 = up.add(-blockRange, -blockRange, -blockRange);
			Position pos2 = up.add(blockRange, blockRange, blockRange);
			ChunkCache chunkCache = new ChunkCache(this.world, pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ(), 0);
			PathEntityWrapped path = this.pathfinder.createPath(chunkCache, this.entity, entity, range);
			return path;
		}
	}

	public boolean moveTo(Entity entity, double speed) {
		PathEntityWrapped path = this.getPathTo(entity);
		return path != null ? this.setPath(path, speed) : false;
	}

	public boolean setPath(PathEntityWrapped path, double speed) {
		if (path == null) {
			this.path = null;
			return false;
		} else {
			if (!path.isSamePath(this.path)) {
				this.path = path;
			}

			this.rempoveSunlitPathPoints();
			if (this.path.getCurrentPathLength() == 0) {
				return false;
			} else {
				this.speed = speed;
				Vec3D var4 = this.getEntityPosition();
				this.lastPositionCheckTicks = this.ticks;
				this.lastPositionCheck = var4;
				return true;
			}
		}
	}

	public PathEntityWrapped getPath() {
		return this.path;
	}

	public void doTick() {
		++this.ticks;
		if (!this.noPath()) {
			Vec3D vec1;
			if (this.canNavigate()) {
				this.pathMove();
			} else if (this.path != null && this.path.getCurrentPathIndex() < this.path.getCurrentPathLength()) {
				vec1 = this.getEntityPosition();
				Vec3D vec2 = this.path.getVectorFromIndex(this.entity, this.path.getCurrentPathIndex());
				if (vec1.b > vec2.b && !this.entity.onGround && MathHelper.floor(vec1.a) == MathHelper.floor(vec2.a) && MathHelper.floor(vec1.c) == MathHelper.floor(vec2.c)) {
					this.path.setCurrentPathIndex(this.path.getCurrentPathIndex() + 1);
				}
			}

			if (!this.noPath()) {
				vec1 = this.path.getPosition(this.entity);
				if (vec1 != null) {
					this.entity.getControllerMove().a(vec1.a, vec1.b, vec1.c, this.speed);
				}
			}
		}
	}

	protected void pathMove() {
		Vec3D position = this.getEntityPosition();
		int var2 = this.path.getCurrentPathLength();

		for (int i = this.path.getCurrentPathIndex(); i < this.path.getCurrentPathLength(); ++i) {
			if (this.path.getPathPointFromIndex(i).y != (int) position.b) {
				var2 = i;
				break;
			}
		}

		float distance = this.entity.width * this.entity.width * this.multiplier;

		for (int i = this.path.getCurrentPathIndex(); i < var2; ++i) {
			Vec3D vec = this.path.getVectorFromIndex(this.entity, i);
			if (getSquaredDistance(position, vec) < (double) distance) {
				this.path.setCurrentPathIndex(i + 1);
			}
		}

		int var9 = (int) this.entity.height + 1;

		for (int i = var2 - 1; i >= this.path.getCurrentPathIndex(); --i) {
			if (this.isInStraightLine(position, this.path.getVectorFromIndex(this.entity, i), MathHelper.f(this.entity.width), var9, MathHelper.f(this.entity.width))) {
				this.path.setCurrentPathIndex(i);
				break;
			}
		}

		this.checkPosition(position);
	}

	protected void checkPosition(Vec3D vec) {
		if (this.ticks - this.lastPositionCheckTicks > 100) {
			if (getSquaredDistance(vec, this.lastPositionCheck) < 2.25D) {
				this.clearPath();
			}

			this.lastPositionCheckTicks = this.ticks;
			this.lastPositionCheck = vec;
		}

	}

	public boolean noPath() {
		return this.path == null || this.path.isFinished();
	}

	public void clearPath() {
		this.path = null;
	}

	protected abstract Vec3D getEntityPosition();

	protected abstract boolean canNavigate();

	protected boolean isInLiquid() {
		return this.entity.M() || this.entity.P();
	}

	protected void rempoveSunlitPathPoints() {
	}

	protected abstract boolean isInStraightLine(Vec3D var1, Vec3D var2, int var3, int var4, int var5);

	@Override
	public void a(final double d) {
		setSpeed(d);
	}

	@Override
	public float d() {
		return getRange();
	}

	@Override
	public PathEntity a(final double x, final double y, final double z) {
		return getPathTo(x, y, z);
	}

	@Override
	public boolean a(final double x, final double y, final double z, final double speed) {
		return moveTo(x, y, z, speed);
	}

	@Override
	public PathEntity a(final Entity entity) {
		return getPathTo(entity);
	}

	@Override
	public boolean a(final Entity entity, final double speed) {
		return moveTo(entity, speed);
	}

	@Override
	public boolean a(final PathEntity pathEntity, final double speed) {
		if (pathEntity instanceof PathEntityWrapped) {
			return setPath((PathEntityWrapped) pathEntity, speed);
		}
		throw new RuntimeException("Conversion is not supported yet");
	}

	@Override
	public PathEntity e() {
		return getPath();
	}

	@Override
	public void f() {
		doTick();
	}

	@Override
	public boolean g() {
		return noPath();
	}

	@Override
	public void h() {
		clearPath();
	}

}
