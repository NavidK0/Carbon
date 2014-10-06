package com.lastabyss.carbon.reflection;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.blocks.BlockBanner;
import com.lastabyss.carbon.blocks.BlockBarrier;
import com.lastabyss.carbon.blocks.BlockGoldPressurePlate;
import com.lastabyss.carbon.blocks.BlockIronPressurePlate;
import com.lastabyss.carbon.blocks.BlockIronTrapdoor;
import com.lastabyss.carbon.blocks.BlockPrismarine;
import com.lastabyss.carbon.blocks.BlockRedSandstone;
import com.lastabyss.carbon.blocks.BlockRedSandstoneStairs;
import com.lastabyss.carbon.blocks.BlockRedstoneTorchOff;
import com.lastabyss.carbon.blocks.BlockRedstoneTorchOn;
import com.lastabyss.carbon.blocks.BlockSeaLantern;
import com.lastabyss.carbon.blocks.BlockSlime;
import com.lastabyss.carbon.blocks.BlockSponge;
import com.lastabyss.carbon.blocks.BlockStep;
import com.lastabyss.carbon.blocks.BlockStone;
import com.lastabyss.carbon.blocks.BlockStoneButton;
import com.lastabyss.carbon.blocks.BlockStonePressurePlate;
import com.lastabyss.carbon.blocks.BlockTorch;
import com.lastabyss.carbon.blocks.BlockWoodButton;
import com.lastabyss.carbon.blocks.BlockWoodPressurePlate;
import com.lastabyss.carbon.blocks.BlockWoodenDoor;
import com.lastabyss.carbon.blocks.BlockWoodenFence;
import com.lastabyss.carbon.blocks.BlockWoodenFenceGate;
import com.lastabyss.carbon.commands.CommandWorldBorder;
import com.lastabyss.carbon.entity.EntityEndermite;
import com.lastabyss.carbon.entity.EntityGuardian;
import com.lastabyss.carbon.entity.EntityRabbit;
import com.lastabyss.carbon.entity.TileEntityBanner;
import com.lastabyss.carbon.entity.bukkit.Endermite;
import com.lastabyss.carbon.entity.bukkit.Guardian;
import com.lastabyss.carbon.entity.bukkit.Rabbit;
import com.lastabyss.carbon.items.ItemBanner;
import com.lastabyss.carbon.items.ItemCookedMutton;
import com.lastabyss.carbon.items.ItemCookedRabbit;
import com.lastabyss.carbon.items.ItemMutton;
import com.lastabyss.carbon.items.ItemPrismarineCrystal;
import com.lastabyss.carbon.items.ItemPrismarineShard;
import com.lastabyss.carbon.items.ItemRabbit;
import com.lastabyss.carbon.items.ItemRabbitFoot;
import com.lastabyss.carbon.items.ItemRabbitHide;
import com.lastabyss.carbon.items.ItemRabbitStew;
import com.lastabyss.carbon.items.ItemWoodenDoor;
import com.lastabyss.carbon.packets.PacketPlayOutWorldBorder;
import com.lastabyss.carbon.utils.Utilities;
import com.lastabyss.carbon.worldborder.WorldBorder;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityTypes;
import net.minecraft.server.v1_7_R4.EnumProtocol;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemBlock;
import net.minecraft.server.v1_7_R4.ItemMultiTexture;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.TileEntity;
import net.minecraft.server.v1_7_R4.World;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class Injector {
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
  public Block standingBannerBlock = new BlockBanner();
  public Block wallBannerBlock = new BlockBanner();
  public Block stonePlateBlock = new BlockStonePressurePlate();
  public Block woodPlateBlock = new BlockWoodPressurePlate();
  public Block goldPlateBlock = new BlockGoldPressurePlate();
  public Block ironPlateBlock = new BlockIronPressurePlate();
  
  //Bukkit materials
  public Material slimeMat = Utilities.addMaterial("SLIME", 165);
  public Material barrierMat = Utilities.addMaterial("BARRIER", 166);
  public Material ironTrapdoorMat = Utilities.addMaterial("IRON_TRAPDOOR", 167);
  public Material prismarineBlockMat = Utilities.addMaterial("PRISMARINE", 168);
  public Material seaLaternMat = Utilities.addMaterial("SEA_LANTERN", 169);
  public Material freeStandingBannerMat = Utilities.addMaterial("STANDING_BANNER", 176);
  public Material wallMountedBannerMat = Utilities.addMaterial("WALL_BANNER", 177);
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

  public Item rabbitItem = new ItemRabbit();
  public Item cookedRabbitItem = new ItemCookedRabbit();
  public Item rabbitStewItem = new ItemRabbitStew();
  public Item rabbitFootItem = new ItemRabbitFoot();
  public Item rabbitHideItem = new ItemRabbitHide();
  public Item muttonItem = new ItemMutton();
  public Item cookedMuttonItem = new ItemCookedMutton();
  public Item prismarineShardItem = new ItemPrismarineShard();
  public Item prismarineCrystalItem = new ItemPrismarineCrystal();
  
  //Entities
  public EntityType endermiteEntity = Utilities.addEntity("Endermite", 67, Endermite.class);
  public EntityType guardianEntity = Utilities.addEntity("Guardian", 68, Guardian.class);
  public EntityType rabbitEntity = Utilities.addEntity("Rabbit", 101, Rabbit.class);
  
  public Map<World, WorldBorder> worldBorders = new HashMap<>();
  
  public static void registerBlock(int id, String name, Block block) {
      Block.REGISTRY.a(id, name, block);
  }
  
  public static void registerBlock(int id, String name, Block block, Item item) {
      Block.REGISTRY.a(id, name, block);
      Item.REGISTRY.a(id, name, item);
  }
  
  public static void registerItem(int id, String name, Item item) {
      Item.REGISTRY.a(id, name, item);
  }
  
  public static void registerTileEntity(Class<? extends TileEntity> entityClass, String name) {
      try {
          Class<TileEntity> clazz = TileEntity.class;
          Method register = clazz.getDeclaredMethod("a", Class.class, String.class);
          register.setAccessible(true);
          register.invoke(null, entityClass, name);
      } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
      }
  }
  
  public static void registerEntity(Class<? extends Entity> entityClass, String name, int id, int monsterEgg, int monsterEggData2) {
      try {
          Class<EntityTypes> clazz = EntityTypes.class;
          Method register = clazz.getDeclaredMethod("a", Class.class, String.class, Integer.TYPE, Integer.TYPE,
                                                    Integer.TYPE);
          register.setAccessible(true);
          register.invoke(null, entityClass, name, id, monsterEgg, monsterEggData2);
      } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
      }
  }

  @SuppressWarnings("unchecked")
  public static void registerPacket(EnumProtocol protocol, Class<? extends Packet> packetClass, int packetID, boolean isClientbound) {
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
      } catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
         e.printStackTrace();
      }
  }

  public void registerAll() {
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
    registerBlock(147, "light_weighted_pressure_plate", ironPlateBlock, ironPlateItem);
    registerBlock(148, "heavy_weighted_pressure_plate", goldPlateBlock, goldPlateItem);
    registerBlock(165, "slime", slimeBlock, slimeItem);
    registerBlock(166, "barrier", barrierBlock, barrierItem);
    registerBlock(167, "iron_trapdoor", ironTrapDoorBlock, ironTrapDoorItem);
    registerBlock(168, "prismarine", prismarineBlock, prismarineItem);
    registerBlock(169, "sea_lantern", seaLanternBlock, seaLanternItem);
    registerBlock(176, "standing_banner", standingBannerBlock);
    registerBlock(177, "wall_banner", wallBannerBlock);
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
    
    //Register entities (data copied straight from 1.8, from EntityList.java)
    registerEntity(EntityEndermite.class, "Endermite", 67, 1447446, 7237230);
    registerEntity(EntityGuardian.class, "Guardian", 68, 5931634, 15826224);
    registerEntity(EntityRabbit.class, "Rabbit", 101, 10051392, 7555121);
    
    //Register tile entities
    registerTileEntity(TileEntityBanner.class, "Banner");
    
    //Register commands from 1.8
    Utilities.registerBukkitCommand("minecraft", new CommandWorldBorder());

    //Register additional packets
    registerPacket(EnumProtocol.PLAY, PacketPlayOutWorldBorder.class, 68, true);

    //inject our modified blocks
    //inject our items too
    try {
        Class<Blocks> blocksClass = Blocks.class;
        setStaticFinalField(blocksClass, "STONE", Carbon.injector().stoneBlock);
        setStaticFinalField(blocksClass, "SPONGE", Carbon.injector().spongeBlock);
        setStaticFinalField(blocksClass, "TORCH", Carbon.injector().torchBlock);
        setStaticFinalField(blocksClass, "REDSTONE_TORCH_ON", Carbon.injector().redstoneTorchBlockOn);
        setStaticFinalField(blocksClass, "REDSTONE_TORCH_OFF", Carbon.injector().redstoneTorchBlockOff);
        setStaticFinalField(blocksClass, "STONE_BUTTON", Carbon.injector().stoneButtonBlock);
        setStaticFinalField(blocksClass, "WOOD_BUTTON", Carbon.injector().woodButtonBlock);
        setStaticFinalField(blocksClass, "STONE_PLATE", Carbon.injector().stonePlateBlock);
        setStaticFinalField(blocksClass, "WOOD_PLATE", Carbon.injector().woodPlateBlock);
        setStaticFinalField(blocksClass, "IRON_PLATE", Carbon.injector().ironPlateBlock);
        setStaticFinalField(blocksClass, "GOLD_PLATE", Carbon.injector().goldPlateBlock);
        
     } catch (Throwable t) {
         t.printStackTrace();
         Bukkit.shutdown();
     }
  }

  private void setStaticFinalField(Class<?> clazz, String fieldname, Object newValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
      Field field = clazz.getDeclaredField(fieldname);
      field.setAccessible(true);
      Field fieldModifiers = Field.class.getDeclaredField("modifiers");
      fieldModifiers.setAccessible(true);
      fieldModifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
      field.set(null, newValue);
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
  
      ShapelessRecipe andesite = new ShapelessRecipe(new ItemStack(Material.STONE, 2, (short)5)).addIngredient(Material.STONE, 3).addIngredient(Material.COBBLESTONE);
      addRecipe(andesite);
  
      ShapelessRecipe granite = new ShapelessRecipe(new ItemStack(Material.STONE, 1, (short)1)).addIngredient(Material.STONE, 3).addIngredient(Material.QUARTZ);
      addRecipe(granite);
      
      ShapedRecipe polishedDiorite = new ShapedRecipe(new ItemStack(Material.STONE, 4, (short)4)).shape(new String[] { "ss", "ss" }).setIngredient('s', Material.STONE, 3);
      addRecipe(polishedDiorite);
  
      ShapedRecipe polishedAndesite = new ShapedRecipe(new ItemStack(Material.STONE, 4, (short)6)).shape(new String[] { "ss", "ss" }).setIngredient('s', Material.STONE, 5);
      addRecipe(polishedAndesite);
  
      ShapedRecipe polishedGranite = new ShapedRecipe(new ItemStack(Material.STONE, 4, (short)2)).shape(new String[] { "ss", "ss" }).setIngredient('s', Material.STONE, 1);
      addRecipe(polishedGranite);
  
      ShapedRecipe slimeRec = new ShapedRecipe(new ItemStack(this.slimeMat)).shape(new String[] { "sss", "sss", "sss" }).setIngredient('s', Material.SLIME_BALL);
      addRecipe(slimeRec);
  
      ShapelessRecipe slimeBalls = new ShapelessRecipe(new ItemStack(Material.SLIME_BALL, 9)).addIngredient(this.slimeMat);
      addRecipe(slimeBalls);
  
      ShapedRecipe redSandstone = new ShapedRecipe(new ItemStack(this.redSandstoneMat, 4, (short)0)).shape(new String[] { "ss", "ss" }).setIngredient('s', Material.SAND, 1);
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
  
      ShapedRecipe prismarineDarkRecipe = new ShapedRecipe(new ItemStack(this.prismarineBlockMat, 1, (short)2)).shape(new String[] { "***", "*x*", "***" }).setIngredient('*', this.prismarineShardMat).setIngredient('x', Material.INK_SACK, 0);
      addRecipe(prismarineDarkRecipe);
  
      ShapedRecipe oakFence = new ShapedRecipe(new ItemStack(Material.FENCE, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', Material.WOOD, 0).setIngredient('x', Material.STICK);
      addRecipe(oakFence);
  
      ShapedRecipe spruceFence = new ShapedRecipe(new ItemStack(this.spruceFenceMat, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', Material.WOOD, 1).setIngredient('x', Material.STICK);
      addRecipe(spruceFence);
  
      ShapedRecipe birchFence = new ShapedRecipe(new ItemStack(this.birchFenceMat, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', Material.WOOD, 2).setIngredient('x', Material.STICK);
      addRecipe(birchFence);
  
      ShapedRecipe jungleFence = new ShapedRecipe(new ItemStack(this.jungleFenceMat, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', Material.WOOD, 3).setIngredient('x', Material.STICK);
      addRecipe(jungleFence);
  
      ShapedRecipe darkOakFence = new ShapedRecipe(new ItemStack(this.darkOakFenceMat, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', Material.WOOD, 5).setIngredient('x', Material.STICK);
      addRecipe(darkOakFence);
  
      ShapedRecipe acaciaFence = new ShapedRecipe(new ItemStack(this.acaciaFenceMat, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', Material.WOOD, 4).setIngredient('x', Material.STICK);
      addRecipe(acaciaFence);
  
      ShapedRecipe oakGate = new ShapedRecipe(new ItemStack(Material.FENCE_GATE)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', Material.WOOD, 0).setIngredient('*', Material.STICK);
      addRecipe(oakGate);
  
      ShapedRecipe spruceGate = new ShapedRecipe(new ItemStack(this.spruceFenceGateMat)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', Material.WOOD, 1).setIngredient('*', Material.STICK);
      addRecipe(spruceGate);
  
      ShapedRecipe birchGate = new ShapedRecipe(new ItemStack(this.birchFenceGateMat)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', Material.WOOD, 2).setIngredient('*', Material.STICK);
      addRecipe(birchGate);
  
      ShapedRecipe jungleGate = new ShapedRecipe(new ItemStack(this.jungleFenceGateMat)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', Material.WOOD, 3).setIngredient('*', Material.STICK);
      addRecipe(jungleGate);
  
      ShapedRecipe darkOakGate = new ShapedRecipe(new ItemStack(this.darkOakFenceGateMat)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', Material.WOOD, 5).setIngredient('*', Material.STICK);
      addRecipe(darkOakGate);
  
      ShapedRecipe acaciaGate = new ShapedRecipe(new ItemStack(this.acaciaFenceGateMat)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', Material.WOOD, 4).setIngredient('*', Material.STICK);
      addRecipe(acaciaGate);
  
      ShapedRecipe oakDoor = new ShapedRecipe(new ItemStack(Material.WOOD_DOOR, 3)).shape(new String[] { "xx", "xx", "xx" }).setIngredient('x', Material.WOOD, 0);
      addRecipe(oakDoor);
  
      ShapedRecipe ironDoor = new ShapedRecipe(new ItemStack(Material.IRON_DOOR, 3)).shape(new String[] { "xx", "xx", "xx" }).setIngredient('x', Material.IRON_INGOT);
      addRecipe(ironDoor);
  
      ShapedRecipe spruceDoor = new ShapedRecipe(new ItemStack(this.spruceDoorMat, 3)).shape(new String[] { "xx", "xx", "xx" }).setIngredient('x', Material.WOOD, 1);
      addRecipe(spruceDoor);
  
      ShapedRecipe birchDoor = new ShapedRecipe(new ItemStack(this.birchDoorMat, 3)).shape(new String[] { "xx", "xx", "xx" }).setIngredient('x', Material.WOOD, 2);
      addRecipe(birchDoor);
  
      ShapedRecipe jungleDoor = new ShapedRecipe(new ItemStack(this.jungleDoorMat, 3)).shape(new String[] { "xx", "xx", "xx" }).setIngredient('x', Material.WOOD, 3);
      addRecipe(jungleDoor);
  
      ShapedRecipe darkOakDoor = new ShapedRecipe(new ItemStack(this.darkOakDoorMat, 3)).shape(new String[] { "xx", "xx", "xx" }).setIngredient('x', Material.WOOD, 5);
      addRecipe(darkOakDoor);
  
      ShapedRecipe acaciaDoor = new ShapedRecipe(new ItemStack(this.acaciaDoorMat, 3)).shape(new String[] { "xx", "xx", "xx" }).setIngredient('x', Material.WOOD, 4);
      addRecipe(acaciaDoor);
  
      ShapedRecipe ironTrapDoor = new ShapedRecipe(new ItemStack(this.ironTrapdoorMat)).shape(new String[] { "xx", "xx" }).setIngredient('x', Material.IRON_INGOT);
      addRecipe(ironTrapDoor);
  
      FurnaceRecipe wetSpongeFurnace = new FurnaceRecipe(new ItemStack(Material.SPONGE, 1, (short)0), Material.SPONGE).setInput(Material.SPONGE, 1);
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
      
      //Add temporary banners
      
      for (int c = 0; c < 16; c++) {
          ShapedRecipe defaultBanners = new ShapedRecipe(new ItemStack(bannerItemMat, 1, (short) (15 - c))).shape(new String[] { "www", "www", " s "}).setIngredient('w', Material.WOOL, c).setIngredient('s', Material.STICK);
          addRecipe(defaultBanners);
      }
  }

  public void addRecipe(Recipe recipe) {
    Bukkit.getServer().addRecipe(recipe);
  }
}
