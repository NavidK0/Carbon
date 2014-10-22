package com.lastabyss.carbon.blocks;

import java.util.Random;

import org.bukkit.Server;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.material.MaterialData;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.World;
import net.minecraft.server.v1_7_R4.WorldProviderTheEnd;
import com.lastabyss.carbon.Carbon;

public class BlockFire extends net.minecraft.server.v1_7_R4.BlockFire {
	private int[] a = new int[256];
	private int[] b = new int[256];

	public BlockFire() {
	}

	public static void e() {
		Blocks.FIRE.a(getId(Blocks.WOOD), 5, 20);
		Blocks.FIRE.a(getId(Blocks.WOOD_DOUBLE_STEP), 5, 20);
		Blocks.FIRE.a(getId(Blocks.WOOD_STEP), 5, 20);
		Blocks.FIRE.a(getId(Blocks.FENCE), 5, 20);
		Blocks.FIRE.a(getId(Carbon.injector().acaciaFenceBlock), 5, 20);
		Blocks.FIRE.a(getId(Carbon.injector().darkOakFenceBlock), 5, 20);
		Blocks.FIRE.a(getId(Carbon.injector().jungleFenceBlock), 5, 20);
		Blocks.FIRE.a(getId(Carbon.injector().spruceFenceBlock), 5, 20);
		Blocks.FIRE.a(getId(Carbon.injector().birchFenceBlock), 5, 20);
		Blocks.FIRE.a(getId(Blocks.WOOD_STAIRS), 5, 20);
		Blocks.FIRE.a(getId(Blocks.BIRCH_WOOD_STAIRS), 5, 20);
		Blocks.FIRE.a(getId(Blocks.SPRUCE_WOOD_STAIRS), 5, 20);
		Blocks.FIRE.a(getId(Blocks.JUNGLE_WOOD_STAIRS), 5, 20);
		Blocks.FIRE.a(getId(Blocks.LOG), 5, 5);
		Blocks.FIRE.a(getId(Blocks.LOG2), 5, 5);
		Blocks.FIRE.a(getId(Blocks.LEAVES), 30, 60);
		Blocks.FIRE.a(getId(Blocks.LEAVES2), 30, 60);
		Blocks.FIRE.a(getId(Blocks.BOOKSHELF), 30, 20);
		Blocks.FIRE.a(getId(Blocks.TNT), 15, 100);
		Blocks.FIRE.a(getId(Blocks.LONG_GRASS), 60, 100);
		Blocks.FIRE.a(getId(Blocks.DOUBLE_PLANT), 60, 100);
		Blocks.FIRE.a(getId(Blocks.YELLOW_FLOWER), 60, 100);
		Blocks.FIRE.a(getId(Blocks.RED_ROSE), 60, 100);
		Blocks.FIRE.a(getId(Blocks.WOOL), 30, 60);
		Blocks.FIRE.a(getId(Blocks.VINE), 15, 100);
		Blocks.FIRE.a(getId(Blocks.COAL_BLOCK), 5, 5);
		Blocks.FIRE.a(getId(Blocks.HAY_BLOCK), 60, 20);
		Blocks.FIRE.a(getId(Blocks.WOOL_CARPET), 60, 20);
	}

	@Override
	public int a(World world) {
		return 30;
	}

	@Override
	public void a(int i, int j, int k) {
		this.a[i] = j;
		this.b[i] = k;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void a(World world, int i, int j, int k, Random random) {
		if(world.getGameRules().getBoolean("doFireTick")) {
			boolean flag = world.getType(i, j - 1, k) == Blocks.NETHERRACK;
			if(((world.worldProvider instanceof WorldProviderTheEnd)) && (world.getType(i, j - 1, k) == Blocks.BEDROCK)) {
				flag = true;
			}
			if(!canPlace(world, i, j, k)) {
				fireExtinguished(world, i, j, k);
			}
			if((!flag) && (world.Q()) && ((world.isRainingAt(i, j, k)) || (world.isRainingAt(i - 1, j, k)) || (world.isRainingAt(i + 1, j, k)) || (world.isRainingAt(i, j, k - 1)) || (world.isRainingAt(i, j, k + 1)))) {
				fireExtinguished(world, i, j, k);
			}
			else {
				int l = world.getData(i, j, k);
				if(l < 15) {
					world.setData(i, j, k, l + random.nextInt(3) / 2, 4);
				}
				world.a(i, j, k, this, a(world) + random.nextInt(10));
				if((!flag) && (!e(world, i, j, k))) {
					if((!World.a(world, i, j - 1, k)) || (l > 3)) {
						fireExtinguished(world, i, j, k);
					}
				}
				else if((!flag) && (!e(world, i, j - 1, k)) && (l == 15) && (random.nextInt(4) == 0)) {
					fireExtinguished(world, i, j, k);
				}
				else {
					boolean flag1 = world.z(i, j, k);
					byte b0 = 0;
					if(flag1) {
						b0 = -50;
					}
					a(world, i + 1, j, k, 300 + b0, random, l);
					a(world, i - 1, j, k, 300 + b0, random, l);
					a(world, i, j - 1, k, 250 + b0, random, l);
					a(world, i, j + 1, k, 250 + b0, random, l);
					a(world, i, j, k - 1, 300 + b0, random, l);
					a(world, i, j, k + 1, 300 + b0, random, l);
					for(int i1 = i - 1; i1 <= i + 1; i1++) {
						for(int j1 = k - 1; j1 <= k + 1; j1++) {
							for(int k1 = j - 1; k1 <= j + 4; k1++) {
								if((i1 != i) || (k1 != j) || (j1 != k)) {
									int l1 = 100;
									if(k1 > j + 1) {
										l1 += (k1 - (j + 1)) * 100;
									}
									int i2 = m(world, i1, k1, j1);
									if(i2 > 0) {
										int j2 = (i2 + 40 + world.difficulty.a() * 7) / (l + 30);
										if(flag1) {
											j2 /= 2;
										}
										if((j2 > 0) && (random.nextInt(l1) <= j2) && ((!world.Q()) || (!world.isRainingAt(i1, k1, j1))) && (!world.isRainingAt(i1 - 1, k1, k)) && (!world.isRainingAt(i1 + 1, k1, j1)) && (!world.isRainingAt(i1, k1, j1 - 1)) && (!world.isRainingAt(i1, k1, j1 + 1))) {
											int k2 = l + random.nextInt(5) / 4;
											if(k2 > 15) {
												k2 = 15;
											}
											if((world.getType(i1, k1, j1) != this) && (!CraftEventFactory.callBlockIgniteEvent(world, i1, k1, j1, i, j, k).isCancelled())) {
												Server server = world.getServer();
												org.bukkit.World bworld = world.getWorld();
												BlockState blockState = bworld.getBlockAt(i1, k1, j1).getState();
												blockState.setTypeId(Block.getId(this));
												blockState.setData(new MaterialData(Block.getId(this), (byte) k2));

												BlockSpreadEvent spreadEvent = new BlockSpreadEvent(blockState.getBlock(), bworld.getBlockAt(i, j, k), blockState);
												server.getPluginManager().callEvent(spreadEvent);
												if(!spreadEvent.isCancelled()) {
													blockState.update(true);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void a(World world, int i, int j, int k, int l, Random random, int i1) {
		int j1 = this.b[Block.getId(world.getType(i, j, k))];
		if(random.nextInt(l) < j1) {
			boolean flag = world.getType(i, j, k) == Blocks.TNT;

			org.bukkit.block.Block theBlock = world.getWorld().getBlockAt(i, j, k);

			BlockBurnEvent event = new BlockBurnEvent(theBlock);
			world.getServer().getPluginManager().callEvent(event);
			if(event.isCancelled()) {
				return;
			}
			if((random.nextInt(i1 + 10) < 5) && (!world.isRainingAt(i, j, k))) {
				int k1 = i1 + random.nextInt(5) / 4;
				if(k1 > 15) {
					k1 = 15;
				}
				world.setTypeAndData(i, j, k, this, k1, 3);
			}
			else {
				world.setAir(i, j, k);
			}
			if(flag) {
				Blocks.TNT.postBreak(world, i, j, k, 1);
			}
		}
	}

	private int m(World world, int i, int j, int k) {
		byte b0 = 0;
		if(!world.isEmpty(i, j, k)) {
			return 0;
		}
		int l = a(world, i + 1, j, k, b0);

		l = a(world, i - 1, j, k, l);
		l = a(world, i, j - 1, k, l);
		l = a(world, i, j + 1, k, l);
		l = a(world, i, j, k - 1, l);
		l = a(world, i, j, k + 1, l);
		return l;
	}

	@Override
	public boolean canPlace(World world, int i, int j, int k) {
		return (World.a(world, i, j - 1, k)) || (e(world, i, j, k));
	}

	@Override
	public void onPlace(World world, int i, int j, int k) {
		if((world.worldProvider.dimension > 0) || (!Blocks.PORTAL.e(world, i, j, k))) {
			if((!World.a(world, i, j - 1, k)) && (!e(world, i, j, k))) {
				fireExtinguished(world, i, j, k);
			}
			else {
				world.a(i, j, k, this, a(world) + world.random.nextInt(10));
			}
		}
	}

	private void fireExtinguished(World world, int x, int y, int z) {
		if(!CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(x, y, z), Blocks.AIR).isCancelled()) {
			world.setAir(x, y, z);
		}
	}
}
