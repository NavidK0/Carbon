package com.lastabyss.carbon;

import com.lastabyss.carbon.blocks.BlockAnvil;
import com.lastabyss.carbon.blocks.BlockBarrier;
import com.lastabyss.carbon.blocks.BlockDaylightDetector;
import com.lastabyss.carbon.blocks.BlockEnchantTable;
import com.lastabyss.carbon.blocks.BlockGoldPressurePlate;
import com.lastabyss.carbon.blocks.BlockIronPressurePlate;
import com.lastabyss.carbon.blocks.BlockIronTrapdoor;
import com.lastabyss.carbon.blocks.BlockOptimizedChest;
import com.lastabyss.carbon.blocks.BlockOptimizedEnderChest;
import com.lastabyss.carbon.blocks.BlockPiston;
import com.lastabyss.carbon.blocks.BlockPrismarine;
import com.lastabyss.carbon.blocks.BlockRedSandstone;
import com.lastabyss.carbon.blocks.BlockRedSandstoneStairs;
import com.lastabyss.carbon.blocks.BlockRedstoneComparator;
import com.lastabyss.carbon.blocks.BlockRedstoneTorchOff;
import com.lastabyss.carbon.blocks.BlockRedstoneTorchOn;
import com.lastabyss.carbon.blocks.BlockSeaLantern;
import com.lastabyss.carbon.blocks.BlockSlime;
import com.lastabyss.carbon.blocks.BlockSponge;
import com.lastabyss.carbon.blocks.BlockStandingBanner;
import com.lastabyss.carbon.blocks.BlockStep;
import com.lastabyss.carbon.blocks.BlockStone;
import com.lastabyss.carbon.blocks.BlockStoneButton;
import com.lastabyss.carbon.blocks.BlockStonePressurePlate;
import com.lastabyss.carbon.blocks.BlockTorch;
import com.lastabyss.carbon.blocks.BlockWallBanner;
import com.lastabyss.carbon.blocks.BlockWoodButton;
import com.lastabyss.carbon.blocks.BlockWoodPressurePlate;
import com.lastabyss.carbon.blocks.BlockWoodenDoor;
import com.lastabyss.carbon.blocks.BlockWoodenFence;
import com.lastabyss.carbon.blocks.BlockWoodenFenceGate;
import com.lastabyss.carbon.commands.CommandParticle;
import com.lastabyss.carbon.commands.CommandTitle;
import com.lastabyss.carbon.commands.CommandWorldBorder;
import com.lastabyss.carbon.entity.ArmorStandPose;
import com.lastabyss.carbon.entity.EntityArmorStand;
import com.lastabyss.carbon.entity.EntityEndermite;
import com.lastabyss.carbon.entity.EntityGuardian;
import com.lastabyss.carbon.entity.EntityItemFrame;
import com.lastabyss.carbon.entity.EntityRabbit;
import com.lastabyss.carbon.entity.TileEntityBanner;
import com.lastabyss.carbon.entity.TileEntityOptimizedChest;
import com.lastabyss.carbon.entity.TileEntityOptimizedEnchantTable;
import com.lastabyss.carbon.entity.TileEntityOptimizedEnderChest;
import com.lastabyss.carbon.entity.TileEntityPiston;
import com.lastabyss.carbon.entity.bukkit.ArmorStand;
import com.lastabyss.carbon.entity.bukkit.Endermite;
import com.lastabyss.carbon.entity.bukkit.Guardian;
import com.lastabyss.carbon.entity.bukkit.Rabbit;
import com.lastabyss.carbon.generator.monument.WorldGenMonumentBuilding;
import com.lastabyss.carbon.generator.monument.WorldGenMonumentDoubleXRoom;
import com.lastabyss.carbon.generator.monument.WorldGenMonumentDoubleXYRoom;
import com.lastabyss.carbon.generator.monument.WorldGenMonumentDoubleYRoom;
import com.lastabyss.carbon.generator.monument.WorldGenMonumentDoubleYZRoom;
import com.lastabyss.carbon.generator.monument.WorldGenMonumentDoubleZRoom;
import com.lastabyss.carbon.generator.monument.WorldGenMonumentPenthouse;
import com.lastabyss.carbon.generator.monument.WorldGenMonumentPiece1;
import com.lastabyss.carbon.generator.monument.WorldGenMonumentPiece2;
import com.lastabyss.carbon.generator.monument.WorldGenMonumentSimpleRoom;
import com.lastabyss.carbon.generator.monument.WorldGenMonumentSimpleTopRoom;
import com.lastabyss.carbon.generator.monument.WorldGenMonumentStart;
import com.lastabyss.carbon.items.ItemArmorStand;
import com.lastabyss.carbon.items.ItemBanner;
import com.lastabyss.carbon.items.ItemCookedMutton;
import com.lastabyss.carbon.items.ItemCookedRabbit;
import com.lastabyss.carbon.items.ItemHanging;
import com.lastabyss.carbon.items.ItemMutton;
import com.lastabyss.carbon.items.ItemPrismarineCrystal;
import com.lastabyss.carbon.items.ItemPrismarineShard;
import com.lastabyss.carbon.items.ItemRabbit;
import com.lastabyss.carbon.items.ItemRabbitFoot;
import com.lastabyss.carbon.items.ItemRabbitHide;
import com.lastabyss.carbon.items.ItemRabbitStew;
import com.lastabyss.carbon.items.ItemWoodenDoor;
import com.lastabyss.carbon.nettyinjector.NettyInjector;
import com.lastabyss.carbon.packets.PacketPlayInUseEntity;
import com.lastabyss.carbon.packets.PacketPlayOutWorldBorder;
import com.lastabyss.carbon.recipes.RecipesBanners;
import com.lastabyss.carbon.utils.Utilities;
import com.lastabyss.carbon.worldborder.WorldBorder;
import com.lastabyss.enchants.EnchantmentDepthStrider;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.Enchantment;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntitySpawnZone;
import net.minecraft.server.v1_7_R4.EntityTypes;
import net.minecraft.server.v1_7_R4.EnumEntitySpawnZone;
import net.minecraft.server.v1_7_R4.EnumProtocol;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemAnvil;
import net.minecraft.server.v1_7_R4.ItemBlock;
import net.minecraft.server.v1_7_R4.ItemMultiTexture;
import net.minecraft.server.v1_7_R4.ItemReed;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.MobEffectList;
import net.minecraft.server.v1_7_R4.MonsterEggInfo;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_7_R4.PotionBrewer;
import net.minecraft.server.v1_7_R4.TileEntity;
import net.minecraft.server.v1_7_R4.World;
import net.minecraft.server.v1_7_R4.WorldGenFactory;
import net.minecraft.util.gnu.trove.map.TObjectIntMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;
import org.spigotmc.SpigotDebreakifier;

import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

/**
 * The injector class is the driver behind Carbon.
 * @author Navid
 */
public class Injector {
    
  private static Carbon plugin;
  
  //Blocks
  public Block stoneBlock = new BlockStone();
  public Block redstoneTorchBlockOn = new BlockRedstoneTorchOn();
  public Block redstoneTorchBlockOff = new BlockRedstoneTorchOff();
  public Block slimeBlock = new BlockSlime();
  public Block torchBlock = new BlockTorch();
  public Block redSandstoneBlock = new BlockRedSandstone();
  public Block barrierBlock = new BlockBarrier();
  public Block spruceFenceBlock = new BlockWoodenFence("spruce_fence", net.minecraft.server.v1_7_R4.Material.WOOD);
  public Block birchFenceBlock = new BlockWoodenFence("birch_fence", net.minecraft.server.v1_7_R4.Material.WOOD);
  public Block jungleFenceBlock = new BlockWoodenFence("jungle_fence", net.minecraft.server.v1_7_R4.Material.WOOD);
  public Block darkOakFenceBlock = new BlockWoodenFence("dark_oak_fence", net.minecraft.server.v1_7_R4.Material.WOOD);
  public Block acaciaFenceBlock = new BlockWoodenFence("acacia_fence", net.minecraft.server.v1_7_R4.Material.WOOD);
  public Block spruceFenceBlockGate = new BlockWoodenFenceGate();
  public Block birchFenceBlockGate = new BlockWoodenFenceGate();
  public Block jungleFenceBlockGate = new BlockWoodenFenceGate();
  public Block darkOakFenceBlockGate = new BlockWoodenFenceGate();
  public Block acaciaFenceBlockGate = new BlockWoodenFenceGate();
  public Block spruceDoorBlock = new BlockWoodenDoor(ItemWoodenDoor.DoorType.SPRUCE);
  public Block birchDoorBlock = new BlockWoodenDoor(ItemWoodenDoor.DoorType.BIRCH);
  public Block jungleDoorBlock = new BlockWoodenDoor(ItemWoodenDoor.DoorType.JUNGLE);
  public Block darkOakDoorBlock = new BlockWoodenDoor(ItemWoodenDoor.DoorType.DARK_OAK);
  public Block acaciaDoorBlock = new BlockWoodenDoor(ItemWoodenDoor.DoorType.ACACIA);
  public Block ironTrapDoorBlock = new BlockIronTrapdoor();
  public Block redSandstoneStairsBlock = new BlockRedSandstoneStairs(this.redSandstoneBlock, 0);
  public Block redSandstoneSlabBlock = new BlockStep(false, true);
  public Block redSandstoneDoubleSlabBlock = new BlockStep(true, true);
  public Block prismarineBlock = new BlockPrismarine();
  public Block seaLanternBlock = new BlockSeaLantern();
  public Block spongeBlock = new BlockSponge();
  public Block stoneButtonBlock = new BlockStoneButton();
  public Block woodButtonBlock = new BlockWoodButton();
  public Block standingBannerBlock = new BlockStandingBanner();
  public Block wallBannerBlock = new BlockWallBanner();
  public Block stonePlateBlock = new BlockStonePressurePlate();
  public Block woodPlateBlock = new BlockWoodPressurePlate();
  public Block goldPlateBlock = new BlockGoldPressurePlate();
  public Block ironPlateBlock = new BlockIronPressurePlate();
  public Block anvilBlock = new BlockAnvil();
  public Block enchantTableBlock = new BlockEnchantTable();
  public Block daylightDetectorBlock = new BlockDaylightDetector(false);
  public Block daylightDetectorInvertedBlock = new BlockDaylightDetector(true);
  public Block optimizedChestBlock = new BlockOptimizedChest(0);
  public Block optimizedTrappedChestBlock = new BlockOptimizedChest(1);
  public Block optimizedEnderChestBlock = new BlockOptimizedEnderChest();
  public Block redstoneComparatorOffBlock = new BlockRedstoneComparator(false);
  public Block redstoneComparatorOnBlock = new BlockRedstoneComparator(true);
  public Block pistonBlock = new BlockPiston(false);
  public Block pistonStickyBlock = new BlockPiston(true);

  //Bukkit materials
  public Material slimeMat = Utilities.addMaterial("SLIME", 165);
  public Material barrierMat = Utilities.addMaterial("BARRIER", 166);
  public Material ironTrapdoorMat = Utilities.addMaterial("IRON_TRAPDOOR", 167);
  public Material prismarineBlockMat = Utilities.addMaterial("PRISMARINE", 168);
  public Material seaLaternMat = Utilities.addMaterial("SEA_LANTERN", 169);
  public Material freeStandingBannerMat = Utilities.addMaterial("STANDING_BANNER", 176);
  public Material wallMountedBannerMat = Utilities.addMaterial("WALL_BANNER", 177);
  public Material daylightDetectorInvertedMat = Utilities.addMaterial("DAYLIGHT_DETECTOR_INVERTED", 178);
  public Material redSandstoneMat = Utilities.addMaterial("RED_SANDSTONE", 179);
  public Material redSandstoneStairsMat = Utilities.addMaterial("RED_SANDSTONE_STAIRS", 180);
  public Material redSandstoneDoubleSlabMat = Utilities.addMaterial("RED_SANDSTONE_DOUBLESLAB", 181);
  public Material redSandstoneSlabMat = Utilities.addMaterial("RED_SANDSTONE_SLAB", 182);
  public Material spruceFenceGateMat = Utilities.addMaterial("SPRUCE_FENCE_GATE", 183);
  public Material birchFenceGateMat = Utilities.addMaterial("BIRCH_FENCE_GATE", 184);
  public Material jungleFenceGateMat = Utilities.addMaterial("JUNGLE_FENCE_GATE", 185);
  public Material darkOakFenceGateMat = Utilities.addMaterial("DARK_OAK_FENCE_GATE", 186);
  public Material acaciaFenceGateMat = Utilities.addMaterial("ACACIA_FENCE_GATE", 187);
  public Material spruceFenceMat = Utilities.addMaterial("SPRUCE_FENCE", 188);
  public Material birchFenceMat = Utilities.addMaterial("BIRCH_FENCE", 189);
  public Material jungleFenceMat = Utilities.addMaterial("JUNGLE_FENCE", 190);
  public Material darkOakFenceMat = Utilities.addMaterial("DARK_OAK_FENCE", 191);
  public Material acaciaFenceMat = Utilities.addMaterial("ACACIA_FENCE", 192);
  public Material spruceDoorBlockMat = Utilities.addMaterial("SPRUCE_DOOR_BLOCK", 193);
  public Material birchDoorBlockMat = Utilities.addMaterial("BIRCH_DOOR_BLOCK", 194);
  public Material jungleDoorBlockMat = Utilities.addMaterial("JUNGLE_DOOR_BLOCK", 195);
  public Material acaciaDoorBlockMat = Utilities.addMaterial("ACACIA_DOOR_BLOCK", 196);
  public Material darkOakDoorBlockMat = Utilities.addMaterial("DARK_OAK_DOOR_BLOCK", 197);
  public Material prismarineShardMat = Utilities.addMaterial("PRISMARINE_SHARD", 409);
  public Material prismarineCrystalsMat = Utilities.addMaterial("PRISMARINE_CRYSTALS", 410);
  public Material rabbitItemMat = Utilities.addMaterial("RABBIT", 411);
  public Material cookedRabbitItemMat = Utilities.addMaterial("COOKED_RABBIT", 412);
  public Material rabbitStewItemMat = Utilities.addMaterial("RABBIT_STEW", 413);
  public Material rabbitFootItemMat = Utilities.addMaterial("RABBIT_FOOT", 414);
  public Material rabbitHideItemMat = Utilities.addMaterial("RABBIT_HIDE", 415);
  public Material armorStandEntityMat = Utilities.addMaterial("ARMOR_STAND", 416);
  public Material muttonItemMat = Utilities.addMaterial("MUTTON", 423);
  public Material cookedMuttonItemMat = Utilities.addMaterial("COOKED_MUTTON", 424);
  public Material bannerItemMat = Utilities.addMaterial("BANNER", 425);
  public Material spruceDoorMat = Utilities.addMaterial("SPRUCE_DOOR", 427);
  public Material birchDoorMat = Utilities.addMaterial("BIRCH_DOOR", 428);
  public Material jungleDoorMat = Utilities.addMaterial("JUNGLE_DOOR", 429);
  public Material acaciaDoorMat = Utilities.addMaterial("ACACIA_DOOR", 430);
  public Material darkOakDoorMat = Utilities.addMaterial("DARK_OAK_DOOR", 431);
  
  @SuppressWarnings("deprecation")
  public MaterialData graniteMat = new MaterialData(Material.STONE, (byte)1);
  @SuppressWarnings("deprecation")
  public MaterialData dioriteMat = new MaterialData(Material.STONE, (byte)3);
  @SuppressWarnings("deprecation")
  public MaterialData andesiteMat = new MaterialData(Material.STONE, (byte)5);
  @SuppressWarnings("deprecation")
  public MaterialData redSandMat = new MaterialData(Material.SAND, (byte)1);
  @SuppressWarnings("deprecation")
  public MaterialData oakWoodMat = new MaterialData(Material.WOOD, (byte)0);
  @SuppressWarnings("deprecation")
  public MaterialData spruceWoodMat = new MaterialData(Material.WOOD, (byte)1);
  @SuppressWarnings("deprecation")
  public MaterialData birchWoodMat = new MaterialData(Material.WOOD, (byte)2);
  @SuppressWarnings("deprecation")
  public MaterialData jungleWoodMat = new MaterialData(Material.WOOD, (byte)3);
  @SuppressWarnings("deprecation")
  public MaterialData acaciaWoodMat = new MaterialData(Material.WOOD, (byte)4);
  @SuppressWarnings("deprecation")
  public MaterialData darkOakWoodMat = new MaterialData(Material.WOOD, (byte)5);
  @SuppressWarnings("deprecation")
  public MaterialData wetSpongeMat = new MaterialData(Material.SPONGE, (byte)1);

  //Items
  public Item stoneItem = new ItemMultiTexture(this.stoneBlock, this.stoneBlock, BlockStone.names).b("stone");
  public Item torchItem = new ItemBlock(this.torchBlock);
  public Item redstoneTorchItemOn = new ItemBlock(this.redstoneTorchBlockOn);
  public Item redstoneTorchItemOff = new ItemBlock(this.redstoneTorchBlockOff);
  public Item slimeItem = new ItemBlock(this.slimeBlock);
  public Item barrierItem = new ItemBlock(this.barrierBlock);
  public Item redSandstoneItem = new ItemMultiTexture(this.redSandstoneBlock, this.redSandstoneBlock, BlockRedSandstone.names).b("red_sandstone");
  public Item spruceFenceGateItem = new ItemBlock(this.spruceFenceBlockGate);
  public Item birchFenceGateItem = new ItemBlock(this.birchFenceBlockGate);
  public Item jungleFenceGateItem = new ItemBlock(this.jungleFenceBlockGate);
  public Item darkOakFenceGateItem = new ItemBlock(this.darkOakFenceBlockGate);
  public Item acaciaFenceGateItem = new ItemBlock(this.acaciaFenceBlockGate);
  public Item spruceFenceItem = new ItemBlock(this.spruceFenceBlock);
  public Item birchFenceItem = new ItemBlock(this.birchFenceBlock);
  public Item jungleFenceItem = new ItemBlock(this.jungleFenceBlock);
  public Item darkOakFenceItem = new ItemBlock(this.darkOakFenceBlock);
  public Item acaciaFenceItem = new ItemBlock(this.acaciaFenceBlock);
  public Item spruceDoorItem = new ItemWoodenDoor(ItemWoodenDoor.DoorType.SPRUCE);
  public Item birchDoorItem = new ItemWoodenDoor(ItemWoodenDoor.DoorType.BIRCH);
  public Item jungleDoorItem = new ItemWoodenDoor(ItemWoodenDoor.DoorType.JUNGLE);
  public Item darkOakDoorItem = new ItemWoodenDoor(ItemWoodenDoor.DoorType.DARK_OAK);
  public Item acaciaDoorItem = new ItemWoodenDoor(ItemWoodenDoor.DoorType.ACACIA);
  public Item ironTrapDoorItem = new ItemBlock(this.ironTrapDoorBlock);
  public Item redSandstoneStairsItem = new ItemBlock(this.redSandstoneStairsBlock);
  public Item redSandstoneSlabItem = new ItemBlock(this.redSandstoneSlabBlock);
  public Item redSandstoneDoubleSlabItem = new ItemBlock(this.redSandstoneDoubleSlabBlock);
  public Item prismarineItem = new ItemMultiTexture(this.prismarineBlock, this.prismarineBlock, BlockPrismarine.names);
  public Item seaLanternItem = new ItemBlock(this.seaLanternBlock);
  public Item spongeItem = new ItemMultiTexture(this.spongeBlock, this.spongeBlock, BlockSponge.names);
  public Item stoneButtonItem = new ItemBlock(stoneButtonBlock);
  public Item woodButtonItem = new ItemBlock(woodButtonBlock);
  public Item standingBannerItem = new ItemBanner(standingBannerBlock);
  public Item stonePlateItem = new ItemBlock(stonePlateBlock);
  public Item woodPlateItem = new ItemBlock(woodPlateBlock);
  public Item goldPlateItem = new ItemBlock(goldPlateBlock);
  public Item ironPlateItem = new ItemBlock(ironPlateBlock);
  public Item anvilItem = new ItemAnvil(anvilBlock);
  public Item enchantTableItem = new ItemBlock(enchantTableBlock);
  public Item daylightDetectorItem = new ItemBlock(daylightDetectorBlock);
  public Item armorStandItem = new ItemArmorStand();
  public Item optimizedChestItem = new ItemBlock(optimizedChestBlock);
  public Item optimizedTrappedChestItem = new ItemBlock(optimizedTrappedChestBlock);
  public Item optimizedEnderChestItem = new ItemBlock(optimizedEnderChestBlock);
  public Item pistonItem = new ItemBlock(pistonBlock);
  public Item pistonStickyItem = new ItemBlock(pistonStickyBlock);

  public Item rabbitItem = new ItemRabbit();
  public Item cookedRabbitItem = new ItemCookedRabbit();
  public Item rabbitStewItem = new ItemRabbitStew();
  public Item rabbitFootItem = new ItemRabbitFoot();
  public Item rabbitHideItem = new ItemRabbitHide();
  public Item muttonItem = new ItemMutton();
  public Item cookedMuttonItem = new ItemCookedMutton();
  public Item prismarineShardItem = new ItemPrismarineShard();
  public Item prismarineCrystalItem = new ItemPrismarineCrystal();
  public Item redstoneComparatorItem = new ItemReed(redstoneComparatorOffBlock);
  public Item frameItem = new ItemHanging(EntityItemFrame.class);

  //Entities
  public EntityType endermiteEntity = Utilities.addEntity("ENDERMITE", 67, Endermite.class);
  public EntityType guardianEntity = Utilities.addEntity("GUARDIAN", 68, Guardian.class);
  public EntityType rabbitEntity = Utilities.addEntity("RABBIT", 101, Rabbit.class);
  public EntityType armorStandEntity = Utilities.addEntity("ARMOR_STAND", 30, ArmorStand.class);

  public Map<World, WorldBorder> worldBorders = new HashMap<World, WorldBorder>();

  public Injector(Carbon plugin) {
      Injector.plugin = plugin;
  }

    public static void registerBlock(int id, String name, Block block) {
        if (plugin.getConfig().getBoolean("modify.blocks." + name, true)) {
            Block.REGISTRY.a(id, name, block);
            if (plugin.getConfig().getBoolean("debug.verbose", false))
                Carbon.log.log(Level.INFO, "[Carbon] Block {0} was registered into Minecraft.", name);
        }
    }

  public static void registerBlock(int id, String name, Block block, Item item) {
      if (plugin.getConfig().getBoolean("modify.blocks." + name, true)) {
        Block.REGISTRY.a(id, name, block);
        Item.REGISTRY.a(id, name, item);
        if (plugin.getConfig().getBoolean("debug.verbose", false))
            Carbon.log.log(Level.INFO, "[Carbon] Block {0} with item {1} was registered into Minecraft.", new Object[]{name + "(" + block.getName() + ")", item.getName()});
      }
  }

  public static void registerItem(int id, String name, Item item) {
      if (plugin.getConfig().getBoolean("modify.items." + name, true)) {
        Item.REGISTRY.a(id, name, item);
        if (plugin.getConfig().getBoolean("debug.verbose", false))
            Carbon.log.log(Level.INFO, "[Carbon] Item {0} was registered into Minecraft.", name);
      }
  }

  @SuppressWarnings("unchecked")
  public static void registerTileEntity(Class<? extends TileEntity> entityClass, String name) {
      if (plugin.getConfig().getBoolean("modify.tileentities." + name, true)) {
        try {
            ((Map<String, Class<? extends TileEntity>>)Utilities.setAccessible(Field.class, TileEntity.class.getDeclaredField("i"), true).get(null)).put(name, entityClass);
            ((Map<Class<? extends TileEntity>, String>)Utilities.setAccessible(Field.class, TileEntity.class.getDeclaredField("j"), true).get(null)).put(entityClass, name);
            if (plugin.getConfig().getBoolean("debug.verbose", false))
              Carbon.log.log(Level.INFO, "[Carbon] Tile Entity {0} was registered into Minecraft.", entityClass.getCanonicalName());
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
          e.printStackTrace(System.out);
        }
      }
  }

  @SuppressWarnings("unchecked")
  public static void registerDataWatcherType(Class<?> type, int id) {
        try {
            Field classToIdField = DataWatcher.class.getDeclaredField("classToId");
            classToIdField.setAccessible(true);
            ((TObjectIntMap<Class<?>>) classToIdField.get(null)).put(type, id);
            if (plugin.getConfig().getBoolean("debug.verbose", false))
                Carbon.log.log(Level.INFO, "[Carbon] DataWatcher type {0} was registered into Minecraft.", type.getCanonicalName());
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace(System.out);
        }
  }

  @SuppressWarnings("unchecked")
  public static void registerEntity(Class<? extends Entity> entityClass, String name, int id) {
      if (plugin.getConfig().getBoolean("modify.entities." + name, true)) {
        try {
    	  ((Map<String, Class<? extends Entity>>) Utilities.setAccessible(Field.class, EntityTypes.class.getDeclaredField("c"), true).get(null)).put(name, entityClass);
    	  ((Map<Class<? extends Entity>, String>) Utilities.setAccessible(Field.class, EntityTypes.class.getDeclaredField("d"), true).get(null)).put(entityClass, name);
    	  ((Map<Integer, Class<? extends Entity>>) Utilities.setAccessible(Field.class, EntityTypes.class.getDeclaredField("e"), true).get(null)).put(id, entityClass);
    	  ((Map<Class<? extends Entity>, Integer>) Utilities.setAccessible(Field.class, EntityTypes.class.getDeclaredField("f"), true).get(null)).put(entityClass, id);
    	  ((Map<String, Integer>) Utilities.setAccessible(Field.class, EntityTypes.class.getDeclaredField("g"), true).get(null)).put(name, id);
          if (plugin.getConfig().getBoolean("debug.verbose", false))
            Carbon.log.log(Level.INFO, "[Carbon] Entity {0} was registered into Minecraft.", entityClass.getCanonicalName());
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
          e.printStackTrace(System.out);
        }
      }
  }

  @SuppressWarnings("unchecked")      
  public static void registerEntity(Class<? extends Entity> entityClass, String name, int id, int monsterEgg, int monsterEggData) {
      if (plugin.getConfig().getBoolean("modify.entities." + name, true)) {
        try {
    	  registerEntity(entityClass, name, id);
    	  EntityTypes.eggInfo.put(id, new MonsterEggInfo(id, monsterEgg, monsterEggData));
        } catch (SecurityException | IllegalArgumentException e) {
          e.printStackTrace(System.out);
        }
      }
  }

  @SuppressWarnings("unchecked")
  public static void registerPacket(EnumProtocol protocol, Class<? extends Packet> packetClass, int packetID, boolean isClientbound) {
      if (plugin.getConfig().getBoolean("modify.packets." + packetID, true)) {
        try {
           if (isClientbound) {
             protocol.b().put(packetID, packetClass);
           } else {
             protocol.a().put(packetID, packetClass);
           }
           Field mapField = EnumProtocol.class.getDeclaredField("f");
           mapField.setAccessible(true);
           Map<Class<? extends Packet>, EnumProtocol> map = (Map<Class<? extends Packet>, EnumProtocol>) mapField.get(null);
           map.put(packetClass, protocol);
           if (plugin.getConfig().getBoolean("debug.verbose", false))
              Carbon.log.log(Level.INFO, "[Carbon] Packet {0} was registered into Minecraft with ID: " + packetID, packetClass.getCanonicalName());
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
           e.printStackTrace(System.out);
        }
      }
  }

  public static void registerSpigotDebreakifierAddition(Block block, Block replacement) {
      try {
          Method method = SpigotDebreakifier.class.getDeclaredMethod("replace", Block.class, Block.class);
          method.setAccessible(true);
          method.invoke(null, block, replacement);
          if (plugin.getConfig().getBoolean("debug.verbose", false))
            Carbon.log.log(Level.INFO, "[Carbon] SpigotDebreakfier for block {0} with replacement {1} was registered into Minecraft.", new String[] {block.getName(), replacement.getName()});
      } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
          e.printStackTrace(System.out);
      }
  }

  @SuppressWarnings("unchecked")
  public static void registerPotionEffect(int effectId, String durations, String amplifier) {
      if (plugin.getConfig().getBoolean("modify.potions." + effectId, true)) {
        try {
            ((Map<Integer, String>)Utilities.setAccessible(Field.class, PotionBrewer.class.getDeclaredField("effectDurations"), true).get(null)).put(effectId, durations);
            ((Map<Integer, String>)Utilities.setAccessible(Field.class, PotionBrewer.class.getDeclaredField("effectAmplifiers"), true).get(null)).put(effectId, amplifier);
            if (plugin.getConfig().getBoolean("debug.verbose", false))
                Carbon.log.log(Level.INFO, "[Carbon] PoitonEffect {0} was registered into Minecraft.", effectId);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace(System.out);
        }
      }
  }

  public static void registerEnchantment(Enchantment enhcantment) {
	  if (plugin.getConfig().getBoolean("modify.enchantments."+enhcantment.id)) {
		  try {
			  ArrayList<Enchantment> enchants = new ArrayList<Enchantment>(Arrays.asList(Enchantment.c));
			  enchants.add(enhcantment);
			  setStaticFinalField(Enchantment.class.getField("c"), enchants.toArray(new Enchantment[0]));
		      if (plugin.getConfig().getBoolean("debug.verbose", false))
		          Carbon.log.log(Level.INFO, "[Carbon] Enchantment {0} was registered into Minecraft.", enhcantment);
		  } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
	          e.printStackTrace(System.out);
	      }
	  }
  }

  public static void registerWorldGenFactoryAddition(boolean isStructureStart, Class<?> clazz, String string) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    Method method = Utilities.setAccessible(Method.class, WorldGenFactory.class.getDeclaredMethod(isStructureStart ? "b" : "a", Class.class, String.class), true);
    method.invoke(null, clazz, string);
  }
  
  public void registerAll() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, InvocationTargetException, NoSuchMethodException  {
    //Register datawatcher types
    registerDataWatcherType(ArmorStandPose.class, 7);

    //Register blocks
    registerBlock(1, "stone", stoneBlock, stoneItem);
    registerBlock(19, "sponge", spongeBlock, spongeItem);
    registerBlock(77, "stone_button", stoneButtonBlock,stoneButtonItem);
    registerBlock(50, "torch", torchBlock, torchItem);
    registerBlock(70, "stone_pressure_plate", stonePlateBlock, stonePlateItem);
    registerBlock(72, "wooden_pressure_plate", woodPlateBlock, woodPlateItem);
    registerBlock(75, "unlit_redstone_torch", redstoneTorchBlockOff, redstoneTorchItemOff);
    registerBlock(76, "redstone_torch", redstoneTorchBlockOn, redstoneTorchItemOn);
    registerBlock(143, "wooden_button", woodButtonBlock, woodButtonItem);
    registerBlock(147, "light_weighted_pressure_plate", goldPlateBlock, goldPlateItem);
    registerBlock(148, "heavy_weighted_pressure_plate", ironPlateBlock, ironPlateItem);
    registerBlock(149, "unpowered_comparator", redstoneComparatorOffBlock);
    registerBlock(150, "powered_comparator", redstoneComparatorOnBlock);
    registerBlock(151, "daylight_detector", daylightDetectorBlock, daylightDetectorItem);
    registerBlock(165, "slime", slimeBlock, slimeItem);
    registerBlock(166, "barrier", barrierBlock, barrierItem);
    registerBlock(167, "iron_trapdoor", ironTrapDoorBlock, ironTrapDoorItem);
    registerBlock(168, "prismarine", prismarineBlock, prismarineItem);
    registerBlock(169, "sea_lantern", seaLanternBlock, seaLanternItem);
    registerBlock(176, "standing_banner", standingBannerBlock);
    registerBlock(177, "wall_banner", wallBannerBlock);
    registerBlock(178, "daylight_detector_inverted", daylightDetectorInvertedBlock);
    registerBlock(179, "red_sandstone", redSandstoneBlock, redSandstoneItem);
    registerBlock(180, "red_sandstone_stairs", redSandstoneStairsBlock, redSandstoneStairsItem);
    registerBlock(181, "double_stone_slab2", redSandstoneDoubleSlabBlock, redSandstoneDoubleSlabItem);
    registerBlock(182, "stone_slab2", redSandstoneSlabBlock, redSandstoneSlabItem);
    registerBlock(183, "spruce_fence_gate", spruceFenceBlockGate, spruceFenceGateItem);
    registerBlock(184, "birch_fence_gate", birchFenceBlockGate, birchFenceGateItem);
    registerBlock(185, "jungle_fence_gate", jungleFenceBlockGate, jungleFenceGateItem);
    registerBlock(186, "dark_oak_fence_gate", darkOakFenceBlockGate, darkOakFenceGateItem);
    registerBlock(187, "acacia_fence_gate", acaciaFenceBlockGate, acaciaFenceGateItem);
    registerBlock(188, "spruce_fence", spruceFenceBlock, spruceFenceItem);
    registerBlock(189, "birch_fence", birchFenceBlock, birchFenceItem);
    registerBlock(190, "jungle_fence", jungleFenceBlock, jungleFenceItem);
    registerBlock(191, "dark_oak_fence", darkOakFenceBlock, darkOakFenceItem);
    registerBlock(192, "acacia_fence", acaciaFenceBlock, acaciaFenceItem);
    registerBlock(193, "spruce_door", spruceDoorBlock);
    registerItem(427, "spruce_door", spruceDoorItem);
    registerBlock(194, "birch_door", birchDoorBlock);
    registerItem(428, "birch_door", birchDoorItem);
    registerBlock(195, "jungle_door", jungleDoorBlock);
    registerItem(429, "jungle_door", jungleDoorItem);
    registerBlock(196, "acacia_door", acaciaDoorBlock);
    registerItem(430, "acacia_door", acaciaDoorItem);
    registerBlock(197, "dark_oak_door", darkOakDoorBlock);
    registerItem(431, "dark_oak_door", darkOakDoorItem);
    registerBlock(145, "anvil", anvilBlock, anvilItem);
    registerBlock(116, "enchanting_table", enchantTableBlock, enchantTableItem);
    registerBlock(54, "chest", optimizedChestBlock, optimizedChestItem);
    registerBlock(146, "trapped_chest", optimizedTrappedChestBlock, optimizedTrappedChestItem);
    registerBlock(130, "ender_chest", optimizedEnderChestBlock, optimizedEnderChestItem);
    registerBlock(33, "piston", pistonBlock, pistonItem);
    registerBlock(29, "sticky_piston", pistonStickyBlock, pistonStickyItem);

    //Register items
    registerItem(409, "prismarine_shard", prismarineShardItem);
    registerItem(410, "prismarine_crystals", prismarineCrystalItem);
    registerItem(411, "rabbit", rabbitItem);
    registerItem(412, "cooked_rabbit", cookedRabbitItem);
    registerItem(413, "rabbit_stew", rabbitStewItem);
    registerItem(414, "rabbit_foot", rabbitFootItem);
    registerItem(415, "rabbit_hide", rabbitHideItem);
    registerItem(423, "mutton", muttonItem);
    registerItem(424, "cooked_mutton", cookedMuttonItem);
    registerItem(425, "banner", standingBannerItem);
    registerItem(416, "armor_stand", armorStandItem);
    registerItem(389, "item_frame", frameItem);
    registerItem(404, "comparator", redstoneComparatorItem);

    //Register entities (data copied straight from 1.8, from EntityList.java)
    registerEntity(EntityEndermite.class, "Endermite", 67, 1447446, 7237230);
    registerEntity(EntityGuardian.class, "Guardian", 68, 5931634, 15826224);
    registerEntity(EntityRabbit.class, "Rabbit", 101, 10051392, 7555121);
    registerEntity(EntityArmorStand.class, "ArmorStand", 30);
    registerEntity(EntityItemFrame.class, "ItemFrame", 18);

    //Register tile entities
    registerTileEntity(TileEntityBanner.class, "Banner");
    registerTileEntity(TileEntityOptimizedChest.class, "Chest");
    registerTileEntity(TileEntityOptimizedEnderChest.class, "EnderChest");
    registerTileEntity(TileEntityOptimizedEnchantTable.class, "EnchantTable");
    registerTileEntity(TileEntityPiston.class, "Piston");

    //Register commands from 1.8
    if (plugin.getConfig().getBoolean("modify.commands.worldborder", true))
        Utilities.registerBukkitCommand("minecraft", new CommandWorldBorder());
    if (plugin.getConfig().getBoolean("modify.commands.particle", true))
        Utilities.registerBukkitCommand("minecraft", new CommandParticle());
    if (plugin.getConfig().getBoolean("modify.commands.title", true))
        Utilities.registerBukkitCommand("minecraft", new CommandTitle());

    //Register additional packets
    registerPacket(EnumProtocol.PLAY, PacketPlayOutWorldBorder.class, 68, true);
    registerPacket(EnumProtocol.PLAY, PacketPlayInUseEntity.class, 2, false);

    //Register additional 1.8 client replacement to prevent crashes
    registerSpigotDebreakifierAddition(redSandstoneDoubleSlabBlock, redSandstoneSlabBlock);

    //Register additional enchantments
    Utilities.setAccessible(Field.class, org.bukkit.enchantments.Enchantment.class.getDeclaredField("acceptingNew"), true).set(null, true);
    registerEnchantment(new EnchantmentDepthStrider(8, 2));
    Utilities.setAccessible(Field.class, org.bukkit.enchantments.Enchantment.class.getDeclaredField("acceptingNew"), true).set(null, false);

    //Register additional potion effects
    registerPotionEffect(MobEffectList.JUMP.getId(), "0 & 1 & !2 & 3", "5");

    //update Blocks.class and Items.class
    for (Field field : Blocks.class.getDeclaredFields()) {
    	field.setAccessible(true);
    	if (Block.class.isAssignableFrom(field.getType())) {
    		Block block = (Block) field.get(null);
    		block = Block.getById(Block.getId(block));
    		setStaticFinalField(field, block);
    	}
    }
    for (Field field : Items.class.getDeclaredFields()) {
    	field.setAccessible(true);
    	if (Item.class.isAssignableFrom(field.getType())) {
    		Item item = (Item) field.get(null);
    		item = Item.getById(Item.getId(item));
    		setStaticFinalField(field, item);
    	}
    }

    //inject custom netty stream serializer
    NettyInjector.injectStreamSerializer();

    //check if we actually have a needed constructor for the packet
    new PacketPlayOutEntityTeleport(0, 0, 0, 0, (byte) 0, (byte) 0, true, true);

    //inject some world gen factory types
    registerWorldGenFactoryAddition(true, WorldGenMonumentStart.class, "Monument");
    registerWorldGenFactoryAddition(false, WorldGenMonumentBuilding.class, "OMB");
    registerWorldGenFactoryAddition(false, WorldGenMonumentPiece2.class, "OMCR");
    registerWorldGenFactoryAddition(false, WorldGenMonumentDoubleXRoom.class, "OMDXR");
    registerWorldGenFactoryAddition(false, WorldGenMonumentDoubleXYRoom.class, "OMDXYR");
    registerWorldGenFactoryAddition(false, WorldGenMonumentDoubleYRoom.class, "OMDYR");
    registerWorldGenFactoryAddition(false, WorldGenMonumentDoubleYZRoom.class, "OMDYZR");
    registerWorldGenFactoryAddition(false, WorldGenMonumentDoubleZRoom.class, "OMDZR");
    registerWorldGenFactoryAddition(false, WorldGenMonumentPiece1.class, "OMEntry");
    registerWorldGenFactoryAddition(false, WorldGenMonumentPenthouse.class, "OMPenthouse");
    registerWorldGenFactoryAddition(false, WorldGenMonumentSimpleRoom.class, "OMSimple");
    registerWorldGenFactoryAddition(false, WorldGenMonumentSimpleTopRoom.class, "OMSimpleT");

    //inject additional spawn zone classes
    EntitySpawnZone.register(EntityRabbit.class, EnumEntitySpawnZone.ON_GROUND);
    EntitySpawnZone.register(EntityEndermite.class, EnumEntitySpawnZone.ON_GROUND);
    EntitySpawnZone.register(EntityGuardian.class, EnumEntitySpawnZone.IN_WATER);
  }

  private static void setStaticFinalField(Field field, Object newValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
      field.setAccessible(true);
      Field fieldModifiers = Field.class.getDeclaredField("modifiers");
      fieldModifiers.setAccessible(true);
      fieldModifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
	  FieldAccessor fieldAccessor = ReflectionFactory.getReflectionFactory().newFieldAccessor(field, true);
	  fieldAccessor.set(null, newValue);
  }

  public void registerRecipes() {
      //reset recipes, this will restore original recipes, but with proper items now
      Bukkit.resetRecipes();

      //Remove all recipes that have the new items so we can add the recipes later
      Iterator<Recipe> ri = Bukkit.getServer().recipeIterator();
      while (ri.hasNext()) {
          Material m = (ri.next()).getResult().getType();
          if(m.equals(Material.WOOD_DOOR) || m.equals(Material.IRON_DOOR) || m.equals(Material.FENCE_GATE) || m.equals(Material.FENCE) || m.equals(Material.STONE) || m.equals(Material.SMOOTH_BRICK))
              ri.remove();
      }

      //now register our recipes
      ShapedRecipe coarseDirt = new ShapedRecipe(new ItemStack(Material.DIRT, 4, (short)1)).shape(new String[] { "dg", "gd" }).setIngredient('d', Material.DIRT).setIngredient('g', Material.GRAVEL);
      addRecipe(coarseDirt);

      ShapedRecipe diorite = new ShapedRecipe(new ItemStack(Material.STONE, 2, (short)3)).shape(new String[] { "cq", "qc" }).setIngredient('c', Material.COBBLESTONE).setIngredient('q', Material.QUARTZ);
      addRecipe(diorite);

      ShapelessRecipe andesite = new ShapelessRecipe(new ItemStack(Material.STONE, 2, (short)5)).addIngredient(dioriteMat).addIngredient(Material.COBBLESTONE);
      addRecipe(andesite);

      ShapelessRecipe granite = new ShapelessRecipe(new ItemStack(Material.STONE, 1, (short)1)).addIngredient(dioriteMat).addIngredient(Material.QUARTZ);
      addRecipe(granite);

      ShapedRecipe polishedDiorite = new ShapedRecipe(new ItemStack(Material.STONE, 4, (short)4)).shape(new String[] { "ss", "ss" }).setIngredient('s', dioriteMat);
      addRecipe(polishedDiorite);

      ShapedRecipe polishedAndesite = new ShapedRecipe(new ItemStack(Material.STONE, 4, (short)6)).shape(new String[] { "ss", "ss" }).setIngredient('s', andesiteMat);
      addRecipe(polishedAndesite);

      ShapedRecipe polishedGranite = new ShapedRecipe(new ItemStack(Material.STONE, 4, (short)2)).shape(new String[] { "ss", "ss" }).setIngredient('s', graniteMat);
      addRecipe(polishedGranite);

      ShapedRecipe slimeRec = new ShapedRecipe(new ItemStack(this.slimeMat)).shape(new String[] { "sss", "sss", "sss" }).setIngredient('s', Material.SLIME_BALL);
      addRecipe(slimeRec);

      ShapelessRecipe slimeBalls = new ShapelessRecipe(new ItemStack(Material.SLIME_BALL, 9)).addIngredient(this.slimeMat);
      addRecipe(slimeBalls);

      ShapedRecipe redSandstone = new ShapedRecipe(new ItemStack(this.redSandstoneMat, 1, (short)0)).shape(new String[] { "ss", "ss" }).setIngredient('s', redSandMat);
      addRecipe(redSandstone);

      ShapedRecipe smoothRedSandstone = new ShapedRecipe(new ItemStack(this.redSandstoneMat, 4, (short)2)).shape(new String[] { "ss", "ss" }).setIngredient('s', this.redSandstoneMat);
      addRecipe(smoothRedSandstone);

      ShapedRecipe chiseledRedSandstone = new ShapedRecipe(new ItemStack(this.redSandstoneMat, 4, (short)1)).shape(new String[] { "s", "s" }).setIngredient('s', this.redSandstoneSlabMat);
      addRecipe(chiseledRedSandstone);

      ShapedRecipe redSandstoneSlabs = new ShapedRecipe(new ItemStack(this.redSandstoneSlabMat, 6, (short)0)).shape(new String[] { "sss" }).setIngredient('s', this.redSandstoneMat);
      addRecipe(redSandstoneSlabs);

      ShapedRecipe redSandstoneStairs = new ShapedRecipe(new ItemStack(this.redSandstoneStairsMat, 4)).shape(new String[] { "  s", " ss", "sss" }).setIngredient('s', this.redSandstoneMat);
      addRecipe(redSandstoneStairs);

      ShapedRecipe seaLanternRecipe = new ShapedRecipe(new ItemStack(this.seaLaternMat)).shape(new String[] { "*x*", "xxx", "*x*" }).setIngredient('*', this.prismarineShardMat).setIngredient('x', this.prismarineCrystalsMat);
      addRecipe(seaLanternRecipe);

      ShapedRecipe prismarineRecipe = new ShapedRecipe(new ItemStack(this.prismarineBlockMat, 1, (short)0)).shape(new String[] { "**", "**" }).setIngredient('*', this.prismarineShardMat);
      addRecipe(prismarineRecipe);

      ShapelessRecipe prismarineBricksRecipe = new ShapelessRecipe(new ItemStack(this.prismarineBlockMat, 1, (short)1)).addIngredient(9, this.prismarineShardMat);
      addRecipe(prismarineBricksRecipe);

      @SuppressWarnings("deprecation")
	  ShapedRecipe prismarineDarkRecipe = new ShapedRecipe(new ItemStack(this.prismarineBlockMat, 1, (short)2)).shape(new String[] { "***", "*x*", "***" }).setIngredient('*', this.prismarineShardMat).setIngredient('x', Material.INK_SACK, 0);
      addRecipe(prismarineDarkRecipe);

      ShapedRecipe oakFence = new ShapedRecipe(new ItemStack(Material.FENCE, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', oakWoodMat).setIngredient('x', Material.STICK);
      addRecipe(oakFence);

      ShapedRecipe spruceFence = new ShapedRecipe(new ItemStack(this.spruceFenceMat, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', spruceWoodMat).setIngredient('x', Material.STICK);
      addRecipe(spruceFence);

      ShapedRecipe birchFence = new ShapedRecipe(new ItemStack(this.birchFenceMat, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', birchWoodMat).setIngredient('x', Material.STICK);
      addRecipe(birchFence);

      ShapedRecipe jungleFence = new ShapedRecipe(new ItemStack(this.jungleFenceMat, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', jungleWoodMat).setIngredient('x', Material.STICK);
      addRecipe(jungleFence);

      ShapedRecipe acaciaFence = new ShapedRecipe(new ItemStack(this.acaciaFenceMat, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', acaciaWoodMat).setIngredient('x', Material.STICK);
      addRecipe(acaciaFence);
      
      ShapedRecipe darkOakFence = new ShapedRecipe(new ItemStack(this.darkOakFenceMat, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', darkOakWoodMat).setIngredient('x', Material.STICK);
      addRecipe(darkOakFence);

      ShapedRecipe oakGate = new ShapedRecipe(new ItemStack(Material.FENCE_GATE)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', oakWoodMat).setIngredient('*', Material.STICK);
      addRecipe(oakGate);

      ShapedRecipe spruceGate = new ShapedRecipe(new ItemStack(this.spruceFenceGateMat)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', spruceWoodMat).setIngredient('*', Material.STICK);
      addRecipe(spruceGate);

      ShapedRecipe birchGate = new ShapedRecipe(new ItemStack(this.birchFenceGateMat)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', birchWoodMat).setIngredient('*', Material.STICK);
      addRecipe(birchGate);

      ShapedRecipe jungleGate = new ShapedRecipe(new ItemStack(this.jungleFenceGateMat)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', jungleWoodMat).setIngredient('*', Material.STICK);
      addRecipe(jungleGate);

      ShapedRecipe acaciaGate = new ShapedRecipe(new ItemStack(this.acaciaFenceGateMat)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', acaciaWoodMat).setIngredient('*', Material.STICK);
      addRecipe(acaciaGate);

      ShapedRecipe darkOakGate = new ShapedRecipe(new ItemStack(this.darkOakFenceGateMat)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', darkOakWoodMat).setIngredient('*', Material.STICK);
      addRecipe(darkOakGate);

      ShapedRecipe oakDoor = new ShapedRecipe(new ItemStack(Material.WOOD_DOOR, 3)).shape(new String[] { "xx", "xx", "xx" }).setIngredient('x', oakWoodMat);
      addRecipe(oakDoor);

      ShapedRecipe ironDoor = new ShapedRecipe(new ItemStack(Material.IRON_DOOR, 3)).shape(new String[] { "xx", "xx", "xx" }).setIngredient('x', Material.IRON_INGOT);
      addRecipe(ironDoor);

      ShapedRecipe spruceDoor = new ShapedRecipe(new ItemStack(this.spruceDoorMat, 3)).shape(new String[] { "xx", "xx", "xx" }).setIngredient('x', spruceWoodMat);
      addRecipe(spruceDoor);

      ShapedRecipe birchDoor = new ShapedRecipe(new ItemStack(this.birchDoorMat, 3)).shape(new String[] { "xx", "xx", "xx" }).setIngredient('x', birchWoodMat);
      addRecipe(birchDoor);

      ShapedRecipe jungleDoor = new ShapedRecipe(new ItemStack(this.jungleDoorMat, 3)).shape(new String[] { "xx", "xx", "xx" }).setIngredient('x', jungleWoodMat);
      addRecipe(jungleDoor);

      ShapedRecipe acaciaDoor = new ShapedRecipe(new ItemStack(this.acaciaDoorMat, 3)).shape(new String[] { "xx", "xx", "xx" }).setIngredient('x', acaciaWoodMat);
      addRecipe(acaciaDoor);

      ShapedRecipe darkOakDoor = new ShapedRecipe(new ItemStack(this.darkOakDoorMat, 3)).shape(new String[] { "xx", "xx", "xx" }).setIngredient('x', darkOakWoodMat);
      addRecipe(darkOakDoor);

      ShapedRecipe ironTrapDoor = new ShapedRecipe(new ItemStack(this.ironTrapdoorMat)).shape(new String[] { "xx", "xx" }).setIngredient('x', Material.IRON_INGOT);
      addRecipe(ironTrapDoor);

      FurnaceRecipe wetSpongeFurnace = new FurnaceRecipe(new ItemStack(Material.SPONGE, 1, (short)0), Material.SPONGE).setInput(wetSpongeMat);
      addRecipe(wetSpongeFurnace);

      FurnaceRecipe cobbleFurnace = new FurnaceRecipe(new ItemStack(Material.STONE, 1, (short)0), Material.STONE).setInput(Material.COBBLESTONE);
      addRecipe(cobbleFurnace);

      FurnaceRecipe rawRabbit = new FurnaceRecipe(new ItemStack(this.cookedRabbitItemMat, 1, (short)0), this.cookedRabbitItemMat).setInput(this.rabbitItemMat);
      addRecipe(rawRabbit);

      FurnaceRecipe crackedStoneBricks = new FurnaceRecipe(new ItemStack(Material.SMOOTH_BRICK, 1, (short)2), Material.SMOOTH_BRICK).setInput(Material.SMOOTH_BRICK);
      addRecipe(crackedStoneBricks);

      FurnaceRecipe rawMutton = new FurnaceRecipe(new ItemStack(this.cookedMuttonItemMat, 1, (short)0), this.cookedMuttonItemMat).setInput(this.muttonItemMat);
      addRecipe(rawMutton);

      ShapedRecipe rabbitStew = new ShapedRecipe(new ItemStack(this.rabbitStewItemMat)).shape(new String[] { " r ", "cpm", " b " }).setIngredient('r', this.cookedRabbitItemMat).setIngredient('c', Material.CARROT_ITEM).setIngredient('p', Material.BAKED_POTATO).setIngredient('m', Material.RED_MUSHROOM).setIngredient('b', Material.BOWL);
      addRecipe(rabbitStew);

      ShapedRecipe rabbitStew2 = new ShapedRecipe(new ItemStack(this.rabbitStewItemMat)).shape(new String[] { " r ", "cpm", " b " }).setIngredient('r', this.cookedRabbitItemMat).setIngredient('c', Material.CARROT_ITEM).setIngredient('p', Material.BAKED_POTATO).setIngredient('m', Material.BROWN_MUSHROOM).setIngredient('b', Material.BOWL);
      addRecipe(rabbitStew2);

      @SuppressWarnings("deprecation")
	  ShapedRecipe chiseledStone = new ShapedRecipe(new ItemStack(Material.SMOOTH_BRICK, 1, (short)3)).shape(new String[] { "s", "s"}).setIngredient('s', Material.STEP, 5);
      addRecipe(chiseledStone);

      ShapedRecipe stoneBricks = new ShapedRecipe(new ItemStack(Material.SMOOTH_BRICK, 4)).shape(new String[] { "xx", "xx" }).setIngredient('x', Material.STONE);
      addRecipe(stoneBricks);

      ShapelessRecipe mossyStoneBrick = new ShapelessRecipe(new ItemStack(Material.SMOOTH_BRICK, 1, (short)1)).addIngredient(Material.SMOOTH_BRICK).addIngredient(Material.VINE);
      addRecipe(mossyStoneBrick);

      ShapelessRecipe mossStone = new ShapelessRecipe(new ItemStack(Material.MOSSY_COBBLESTONE)).addIngredient(Material.COBBLESTONE).addIngredient(Material.VINE);
      addRecipe(mossStone);

      ShapedRecipe leather = new ShapedRecipe(new ItemStack(Material.LEATHER)).shape(new String[] { "ll", "ll" }).setIngredient('l', this.rabbitHideItemMat);
      addRecipe(leather);

      ShapedRecipe armorStand = new ShapedRecipe(new ItemStack(this.armorStandEntityMat)).shape(new String[] {"iii", " i ", "isi"}).setIngredient('i', Material.STICK).setIngredient('s', Material.STEP);
      addRecipe(armorStand);

      new RecipesBanners().register();
  }

  public void addRecipe(Recipe recipe) {
    Bukkit.getServer().addRecipe(recipe);
  }
}
