package com.lastabyss.carbon.blocks;

import java.util.List;

import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.Facing;
import net.minecraft.server.v1_7_R4.IBlockAccess;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.TileEntity;
import net.minecraft.server.v1_7_R4.World;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlock;

import com.lastabyss.carbon.entity.TileEntityPiston;
import com.lastabyss.carbon.events.CarbonBlockPistonExtendEvent;
import com.lastabyss.carbon.events.CarbonBlockPistonRetractEvent;
import com.lastabyss.carbon.utils.nmsclasses.BlockFace;
import com.lastabyss.carbon.utils.nmsclasses.IBlockState;
import com.lastabyss.carbon.utils.nmsclasses.Position;

public class BlockPiston extends net.minecraft.server.v1_7_R4.BlockPiston {

    private final boolean isSticky;
	public BlockPiston(boolean isSticky) {
		super(isSticky);
		this.isSticky = isSticky;
	}

    @Override
    public int b() {
        return 16;
    }

    @Override
    public boolean c() {
        return false;
    }

    @Override
    public boolean interact(final World world, final int x, final int y, final int z, final EntityHuman entityhuman, final int l, final float f, final float f1, final float f2) {
        return false;
    }

    @Override
    public void postPlace(final World world, final int x, final int y, final int z, final EntityLiving entityliving, final ItemStack itemstack) {
        final int face = getFace(world, x, y, z, entityliving);
        world.setData(x, y, z, face, 2);
        runAction(world, x, y, z);
    }

    @Override
    public void doPhysics(final World world, final int x, final int y, final int z, final Block block) {
        runAction(world, x, y, z);
    }

    @Override
    public void onPlace(final World world, final int x, final int y, final int z) {
        if (world.getTileEntity(x, y, z) == null) {
            runAction(world, x, y, z);
        }
    }

    private void runAction(final World world, final int x, final int y, final int z) {
        final int face = world.getData(x, y, z);
        final int clampedFace = clampFace(face);
        if (clampedFace != 7) {
            final boolean flag = isPowered(world, x, y, z, clampedFace);
            if (flag && !isExtended(face)) {
            	BlockPistonBlocksCalc calc = new BlockPistonBlocksCalc(world, new Position(x, y, z), BlockFace.getById(face), true);
                if (calc.calculate()) {
                	List<Position> blocks = calc.getBlocksToBreak();
                	blocks.addAll(calc.getBlocksToPush());
                	CarbonBlockPistonExtendEvent extendEvent = new CarbonBlockPistonExtendEvent(world.getWorld().getBlockAt(x, y, z), blocks, CraftBlock.notchToBlockFace(clampedFace));
                	Bukkit.getPluginManager().callEvent(extendEvent);
                	if (extendEvent.isCancelled()) {
                		return;
                	}
                    world.playBlockAction(x, y, z, this, 0, clampedFace);
                }
            } else if (!flag && isExtended(face)) {
                world.setData(x, y, z, clampedFace, 2);
                BlockFace retrface = BlockFace.getById(clampedFace);
                world.setAir(x + retrface.getFrontDirectionX(), retrface.getFrontDirectionY(), retrface.getFrontDirectionZ());
            	BlockPistonBlocksCalc calc = new BlockPistonBlocksCalc(world, new Position(x, y, z), retrface, false);
            	calc.calculate();
            	List<Position> blocks = calc.getBlocksToBreak();
            	blocks.addAll(calc.getBlocksToPush());
            	CarbonBlockPistonRetractEvent retractEvent = new CarbonBlockPistonRetractEvent(world.getWorld().getBlockAt(x, y, z), blocks, CraftBlock.notchToBlockFace(clampedFace).getOppositeFace());
            	Bukkit.getPluginManager().callEvent(retractEvent);
            	if (retractEvent.isCancelled()) {
            		world.setData(x, y, z, clampedFace | 0x8, 2);
            		return;
            	}
                world.playBlockAction(x, y, z, this, 1, clampedFace);
            }
        }
    }

    private boolean isPowered(final World world, final int x, final int y, final int z, final int face) {
        return ((face != 0) && world.isBlockFacePowered(x, y - 1, z, 0)) || ((face != 1) && world.isBlockFacePowered(x, y + 1, z, 1)) || ((face != 2) && world.isBlockFacePowered(x, y, z - 1, 2)) || ((face != 3) && world.isBlockFacePowered(x, y, z + 1, 3)) || ((face != 5) && world.isBlockFacePowered(x + 1, y, z, 5)) || ((face != 4) && world.isBlockFacePowered(x - 1, y, z, 4)) || world.isBlockFacePowered(x, y, z, 0) || world.isBlockFacePowered(x, y + 2, z, 1) || world.isBlockFacePowered(x, y + 1, z - 1, 2) || world.isBlockFacePowered(x, y + 1, z + 1, 3) || world.isBlockFacePowered(x - 1, y + 1, z, 4) || world.isBlockFacePowered(x + 1, y + 1, z, 5);
    }

    @Override
    public boolean a(final World world, int x, int y, int z, final int action, final int face) {
        final boolean powered = isPowered(world, x, y, z, face);
        if (powered && (action == 1)) {
            world.setData(x, y, z, face | 0x8, 2);
            return false;
        }
        if (!powered && (action == 0)) {
            return false;
        }
        if (action == 0) {
            if (!move(world, x, y, z, face, true)) {
                return false;
            }
            world.setData(x, y, z, face | 0x8, 2);
            world.makeSound(x + 0.5, y + 0.5, z + 0.5, "tile.piston.out", 0.5f, (world.random.nextFloat() * 0.25f) + 0.6f);
        }
        else if (action == 1) {
            final TileEntity tileentity = world.getTileEntity(x + Facing.b[face], y + Facing.c[face], z + Facing.d[face]);
            if (tileentity instanceof TileEntityPiston) {
                ((TileEntityPiston)tileentity).f();
            }
            world.setTypeAndData(x, y, z, Blocks.PISTON_MOVING, face, 3);
            world.setTileEntity(x, y, z, getPistonTileEntity(this, face, face, false));
            if (isSticky) {
                final int j2 = x + (Facing.b[face] * 2);
                final int k2 = y + (Facing.c[face] * 2);
                final int l2 = z + (Facing.d[face] * 2);
                Block block = world.getType(j2, k2, l2);
                boolean flag2 = false;
                if (block == Blocks.PISTON_MOVING) {
                    final TileEntity tileentity2 = world.getTileEntity(j2, k2, l2);
                    if (tileentity2 instanceof TileEntityPiston) {
                        final TileEntityPiston tileentitypiston = (TileEntityPiston)tileentity2;
                        if ((tileentitypiston.c() == face) && tileentitypiston.b()) {
                            tileentitypiston.f();
                            block = tileentitypiston.a();
                            flag2 = true;
                        }
                    }
                }
                if (!flag2 && (block.getMaterial() != Material.AIR) && BlockPistonBlocksCalc.canPushBlock(block, world, new Position(j2, k2, l2), BlockFace.getById(face), false) && ((block.h() == 0) || (block == Blocks.PISTON) || (block == Blocks.PISTON_STICKY))) {
					move(world, x, y, z, face, false);
                }
            }
            else {
                world.setAir(x + Facing.b[face], y + Facing.c[face], z + Facing.d[face]);
            }
            world.makeSound(x + 0.5, y + 0.5, z + 0.5, "tile.piston.in", 0.5f, (world.random.nextFloat() * 0.15f) + 0.6f);
        }
        return true;
    }

    @Override
    public void updateShape(final IBlockAccess iblockaccess, final int x, final int y, final int z) {
        final int face = iblockaccess.getData(x, y, z);
        if (isExtended(face)) {
            switch (clampFace(face)) {
                case 0: {
                    this.a(0.0f, 0.25f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 1: {
                    this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
                    break;
                }
                case 2: {
                    this.a(0.0f, 0.0f, 0.25f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 3: {
                    this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.75f);
                    break;
                }
                case 4: {
                    this.a(0.25f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 5: {
                    this.a(0.0f, 0.0f, 0.0f, 0.75f, 1.0f, 1.0f);
                    break;
                }
            }
        }
        else {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public void g() {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public void a(final World world, final int x, final int y, final int z, final AxisAlignedBB axisalignedbb, final List list, final Entity entity) {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.a(world, x, y, z, axisalignedbb, list, entity);
    }

    @Override
    public AxisAlignedBB a(final World world, final int x, final int y, final int z) {
        updateShape(world, x, y, z);
        return super.a(world, x, y, z);
    }

    @Override
    public boolean d() {
        return false;
    }

    public static int clampFace(final int face) {
        if ((face & 0x7) >= Facing.OPPOSITE_FACING.length) {
            return 7;
        }
        return face & 0x7;
    }

    public static boolean isExtended(final int i) {
        return (i & 0x8) != 0x0;
    }

    public static int getFace(final World world, final int x, final int y, final int z, final EntityLiving entityliving) {
        if ((MathHelper.abs((float)entityliving.locX - x) < 2.0f) && (MathHelper.abs((float)entityliving.locZ - z) < 2.0f)) {
            final double d0 = (entityliving.locY + 1.82) - entityliving.height;
            if ((d0 - y) > 2.0) {
                return 1;
            }
            if ((y - d0) > 0.0) {
                return 0;
            }
        }
        final int l = MathHelper.floor(((entityliving.yaw * 4.0f) / 360.0f) + 0.5) & 0x3;
        return (l == 0) ? 2 : ((l == 1) ? 5 : ((l == 2) ? 3 : ((l == 3) ? 4 : 0)));
    }

    private boolean move(final World world, final int x, final int y, final int z, final int faceId, boolean push) {
    	Position position = new Position(x, y, z);
    	BlockFace face = BlockFace.getById(faceId);

		BlockPistonBlocksCalc calc = new BlockPistonBlocksCalc(world, position, face, push);
		List<Position> blocksToPush = calc.getBlocksToPush();
		List<Position> blocksToBreak = calc.getBlocksToBreak();
		if (!calc.calculate()) {
			return false;
		} else {
			int blockCount = blocksToPush.size() + blocksToBreak.size();
			Block[] blocks = new Block[blockCount];
			BlockFace realFace = push ? face : face.getOpposite();

			for (int i = blocksToBreak.size() - 1; i >= 0; --i) {
				Position blockPosition = blocksToBreak.get(i);
				IBlockState blockState = IBlockState.getAt(world, blockPosition);
				blockState.getBlock().b(world, blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), blockState.getData(), 0);
				world.setAir(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
				--blockCount;
				blocks[blockCount] = blockState.getBlock();
			}

			for (int i = blocksToPush.size() - 1; i >= 0; --i) {
				Position blockPosition = blocksToPush.get(i);
				IBlockState blockState = IBlockState.getAt(world, blockPosition);
				world.setAir(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
				blockPosition = blockPosition.getRelative(realFace);
				world.setTypeAndData(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), Blocks.PISTON_MOVING, face.getId() | (isSticky ? 8 : 0), 4);
				world.setTileEntity(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), getPistonTileEntity(blockState.getBlock(), blockState.getData(), face.getId(), push));
				--blockCount;
				blocks[blockCount] = blockState.getBlock();
			}

			Position pistonPosition = position.getRelative(face);
			if (push) {
				IBlockState extesionBlockState = new IBlockState(Blocks.PISTON_EXTENSION, face.getId() | (isSticky ? 8 : 0));
				IBlockState pistonBlockState = new IBlockState(Blocks.PISTON_MOVING, face.getId() | (isSticky ? 8 : 0));
				world.setTypeAndData(pistonPosition.getX(), pistonPosition.getY(), pistonPosition.getZ(), pistonBlockState.getBlock(), pistonBlockState.getData(), 4);
				world.setTileEntity(pistonPosition.getX(), pistonPosition.getY(), pistonPosition.getZ(), getPistonTileEntity(extesionBlockState.getBlock(), extesionBlockState.getData(), face.getId(), true));
			}

			for (int i = blocksToBreak.size() - 1; i >= 0; --i) {
				Position localPosition = blocksToBreak.get(i);
				world.applyPhysics(localPosition.getX(), localPosition.getY(), localPosition.getZ(), blocks[blockCount++]);
			}

			for (int i = blocksToPush.size() - 1; i >= 0; --i) {
				Position localPosition = blocksToPush.get(i);
				world.applyPhysics(localPosition.getX(), localPosition.getY(), localPosition.getZ(), blocks[blockCount++]);
			}

			if (push) {
				world.applyPhysics(pistonPosition.getX(), pistonPosition.getY(), pistonPosition.getZ(), Blocks.PISTON_EXTENSION);
				world.applyPhysics(position.getX(), position.getY(), position.getZ(), this);
			}

			return true;
		}
	}

	private static TileEntityPiston getPistonTileEntity(Block block, int data, int face, boolean extending) {
		return new TileEntityPiston(block, data, face, extending);
	}

}
