package com.lastabyss.carbon.reflection;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.blocks.*;
import com.lastabyss.carbon.commands.WorldBorder;
import com.lastabyss.carbon.entity.EntityEndermite;
import com.lastabyss.carbon.entity.EntityGuardian;
import com.lastabyss.carbon.entity.EntityRabbit;
import com.lastabyss.carbon.entity.bukkit.Endermite;
import com.lastabyss.carbon.entity.bukkit.Guardian;
import com.lastabyss.carbon.entity.bukkit.Rabbit;
import com.lastabyss.carbon.items.*;
import com.lastabyss.carbon.packets.PacketPlayOutWorldBorder;
import com.lastabyss.carbon.utils.Utilities;
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
  
  //Bukkit materials
  public Material slimeMat = Utilities.addMaterial("SLIME", 165);
  public Material barrierMat = Utilities.addMaterial("BARRIER", 166);
  public Material ironTrapdoorMat = Utilities.addMaterial("IRON_TRAPDOOR", 167);
  public Material prismarineBlockMat = Utilities.addMaterial("PRISMARINE", 168);
  public Material seaLaternMat = Utilities.addMaterial("SEA_LANTERN", 169);
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
  
  public static void registerBlock(Material mat, int id, String name, Block block)
  {
    Block.REGISTRY.a(id, name, block);
  }
  
  public static void registerBlock(Material mat, int id, String name, Block block, Item item) {
    Block.REGISTRY.a(id, name, block);
    Item.REGISTRY.a(id, name, item);
    
  }
  
  public static void registerItem(Material mat, int id, String name, Item item) {
    Item.REGISTRY.a(id, name, item);
  }
  
  public static void registerEntity(Class<? extends Entity> entityClass, String name, int id, int monsterEgg, int monsterEggData2) {
      try {
          Class<EntityTypes> clazz = EntityTypes.class;
          Method register = clazz.getDeclaredMethod("a", new Class[] {Class.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE});
          register.setAccessible(true);
          register.invoke(null, entityClass, name, id, monsterEgg, monsterEggData2);
      } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
      }
  }

  public void registerAll() {
    //Register blocks
    registerBlock(Material.STONE, 1, "stone", stoneBlock, stoneItem);
    registerBlock(Material.SPONGE, 19, "sponge", spongeBlock, spongeItem);
    registerBlock(Material.STONE_BUTTON, 77, "stone_button", stoneButtonBlock,stoneButtonItem);
    registerBlock(Material.TORCH, 50, "torch", torchBlock, torchItem);
    registerBlock(Material.REDSTONE_TORCH_OFF, 75, "unlit_redstone_torch", redstoneTorchBlockOff, redstoneTorchItemOff);
    registerBlock(Material.REDSTONE_TORCH_ON, 76, "redstone_torch", redstoneTorchBlockOn, redstoneTorchItemOn);
    registerBlock(Material.WOOD_BUTTON, 143, "wooden_button", woodButtonBlock, woodButtonItem);
    registerBlock(slimeMat, 165, "slime", slimeBlock, slimeItem);
    registerBlock(barrierMat, 166, "barrier", barrierBlock, barrierItem);
    registerBlock(ironTrapdoorMat, 167, "iron_trapdoor", ironTrapDoorBlock, ironTrapDoorItem);
    registerBlock(prismarineBlockMat, 168, "prismarine", prismarineBlock, prismarineItem);
    registerBlock(seaLaternMat, 169, "sea_lantern", seaLanternBlock, seaLanternItem);
    registerBlock(redSandstoneMat, 179, "red_sandstone", redSandstoneBlock, redSandstoneItem);
    registerBlock(redSandstoneStairsMat, 180, "red_sandstone_stairs", redSandstoneStairsBlock, redSandstoneStairsItem);
    registerBlock(redSandstoneDoubleSlabMat, 181, "double_stone_slab2", redSandstoneDoubleSlabBlock, redSandstoneDoubleSlabItem);
    registerBlock(redSandstoneSlabMat, 182, "stone_slab2", redSandstoneSlabBlock, redSandstoneSlabItem);
    registerBlock(spruceFenceGateMat, 183, "spruce_fence_gate", spruceFenceBlockGate, spruceFenceGateItem);
    registerBlock(birchFenceGateMat, 184, "birch_fence_gate", birchFenceBlockGate, birchFenceGateItem);
    registerBlock(jungleFenceGateMat, 185, "jungle_fence_gate", jungleFenceBlockGate, jungleFenceGateItem);
    registerBlock(darkOakFenceGateMat, 186, "dark_oak_fence_gate", darkOakFenceBlockGate, darkOakFenceGateItem);
    registerBlock(acaciaFenceGateMat, 187, "acacia_fence_gate", acaciaFenceBlockGate, acaciaFenceGateItem);
    registerBlock(spruceFenceMat, 188, "spruce_fence", spruceFenceBlock, spruceFenceItem);
    registerBlock(birchFenceMat, 189, "birch_fence", birchFenceBlock, birchFenceItem);
    registerBlock(jungleFenceMat, 190, "jungle_fence", jungleFenceBlock, jungleFenceItem);
    registerBlock(darkOakFenceMat, 191, "dark_oak_fence", darkOakFenceBlock, darkOakFenceItem);
    registerBlock(acaciaFenceMat, 192, "acacia_fence", acaciaFenceBlock, acaciaFenceItem);
    registerBlock(spruceDoorMat, 193, "spruce_door", spruceDoorBlock);
    registerItem(spruceDoorMat, 427, "spruce_door", spruceDoorItem);
    registerBlock(birchDoorMat, 194, "birch_door", birchDoorBlock);
    registerItem(birchDoorMat, 428, "birch_door", birchDoorItem);
    registerBlock(jungleDoorMat, 195, "jungle_door", jungleDoorBlock);
    registerItem(jungleDoorMat, 429, "jungle_door", jungleDoorItem);
    registerBlock(acaciaDoorMat, 196, "acacia_door", acaciaDoorBlock);
    registerItem(acaciaDoorMat, 430, "acacia_door", acaciaDoorItem);
    registerBlock(darkOakDoorMat, 197, "dark_oak_door", darkOakDoorBlock);
    registerItem(darkOakDoorMat, 431, "dark_oak_door", darkOakDoorItem);
    

    //Register items
    registerItem(prismarineShardMat, 409, "prismarine_shard", prismarineShardItem);
    registerItem(prismarineCrystalsMat, 410, "prismarine_crystals", prismarineCrystalItem);
    registerItem(rabbitItemMat, 411, "rabbit", rabbitItem);
    registerItem(cookedRabbitItemMat, 412, "cooked_rabbit", cookedRabbitItem);
    registerItem(rabbitStewItemMat, 413, "rabbit_stew", rabbitStewItem);
    registerItem(rabbitFootItemMat, 414, "rabbit_foot", rabbitFootItem);
    registerItem(rabbitHideItemMat, 415, "rabbit_hide", rabbitHideItem);
    registerItem(muttonItemMat, 423, "mutton", muttonItem);
    registerItem(cookedMuttonItemMat, 424, "cooked_mutton", cookedMuttonItem);
    
    //Register entities (data copied straight from 1.8, from EntityList.java)
    registerEntity(EntityEndermite.class, "Endermite", 67, 1447446, 7237230);
    registerEntity(EntityGuardian.class, "Guardian", 68, 5931634, 15826224);
    registerEntity(EntityRabbit.class, "Rabbit", 101, 10051392, 7555121);
    
    //Register commands from 1.8
    //Utilities.registerBukkitCommand("minecraft", new CommandWorldBorder());

    //Register additional packets
    EnumProtocol.PLAY.b().put(68, PacketPlayOutWorldBorder.class);
    

    //inject our new stone, sponge, torch and redstone torches to blocks class
    try {
        Class<Blocks> blocksClass = Blocks.class;
        setStaticFinalField(blocksClass, "STONE", Carbon.injector().stoneBlock);
        setStaticFinalField(blocksClass, "SPONGE", Carbon.injector().spongeBlock);
        setStaticFinalField(blocksClass, "TORCH", Carbon.injector().torchBlock);
        setStaticFinalField(blocksClass, "REDSTONE_TORCH_ON", Carbon.injector().redstoneTorchBlockOn);
        setStaticFinalField(blocksClass, "REDSTONE_TORCH_OFF", Carbon.injector().redstoneTorchBlockOff);
        setStaticFinalField(blocksClass, "STONE_BUTTON", Carbon.injector().stoneButtonBlock);
        setStaticFinalField(blocksClass, "WOOD_BUTTON", Carbon.injector().woodButtonBlock);
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
      Material m = ((Recipe)ri.next()).getResult().getType();
      if(m.equals(Material.WOOD_DOOR) || m.equals(Material.IRON_DOOR) || m.equals(Material.FENCE_GATE) || m.equals(Material.FENCE) ||m.equals(Material.STONE) || m.equals(Material.SMOOTH_BRICK))
        ri.remove();
    }

    //now register our recipes
    ShapedRecipe coarseDirt = new ShapedRecipe(new ItemStack(Material.DIRT, 4, (short)1)).shape(new String[] { "dg", "gd" }).setIngredient('d', Material.DIRT).setIngredient('g', Material.GRAVEL);
    addRecipe(coarseDirt);
    

    ShapedRecipe coarseDirt2 = new ShapedRecipe(new ItemStack(Material.DIRT, 4, (short)1)).shape(new String[] { "gd", "dg" }).setIngredient('d', Material.DIRT).setIngredient('g', Material.GRAVEL);
    addRecipe(coarseDirt2);
    

    ShapedRecipe diorite = new ShapedRecipe(new ItemStack(Material.STONE, 2, (short)3)).shape(new String[] { "cq", "qc" }).setIngredient('c', Material.COBBLESTONE).setIngredient('q', Material.QUARTZ);
    addRecipe(diorite);
    

    ShapedRecipe diorite2 = new ShapedRecipe(new ItemStack(Material.STONE, 2, (short)3)).shape(new String[] { "cq", "qc" }).setIngredient('c', Material.COBBLESTONE).setIngredient('q', Material.QUARTZ);
    addRecipe(diorite2);
    

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
    

    ShapedRecipe redSandstoneStairs2 = new ShapedRecipe(new ItemStack(this.redSandstoneStairsMat, 4)).shape(new String[] { "s  ", "ss ", "sss" }).setIngredient('s', this.redSandstoneMat);
    addRecipe(redSandstoneStairs2);
    


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
    

    ShapedRecipe oakGate = new ShapedRecipe(new ItemStack(Material.FENCE_GATE, 1)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', Material.WOOD, 0).setIngredient('*', Material.STICK);
    addRecipe(oakGate);
    

    ShapedRecipe spruceGate = new ShapedRecipe(new ItemStack(this.spruceFenceGateMat, 1)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', Material.WOOD, 1).setIngredient('*', Material.STICK);
    addRecipe(spruceGate);
    

    ShapedRecipe birchGate = new ShapedRecipe(new ItemStack(this.birchFenceGateMat, 1)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', Material.WOOD, 2).setIngredient('*', Material.STICK);
    addRecipe(birchGate);
    

    ShapedRecipe jungleGate = new ShapedRecipe(new ItemStack(this.jungleFenceGateMat, 1)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', Material.WOOD, 3).setIngredient('*', Material.STICK);
    addRecipe(jungleGate);
    

    ShapedRecipe darkOakGate = new ShapedRecipe(new ItemStack(this.darkOakFenceGateMat, 1)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', Material.WOOD, 5).setIngredient('*', Material.STICK);
    addRecipe(darkOakGate);
    

    ShapedRecipe acaciaGate = new ShapedRecipe(new ItemStack(this.acaciaFenceGateMat, 1)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', Material.WOOD, 4).setIngredient('*', Material.STICK);
    addRecipe(acaciaGate);
    

    ShapedRecipe oakDoor = new ShapedRecipe(new ItemStack(Material.WOOD_DOOR, 3)).shape(new String[] { "xx ", "xx ", "xx " }).setIngredient('x', Material.WOOD, 0);
    addRecipe(oakDoor);
    

    ShapedRecipe ironDoor = new ShapedRecipe(new ItemStack(Material.IRON_DOOR, 3)).shape(new String[] { "xx ", "xx ", "xx " }).setIngredient('x', Material.IRON_INGOT);
    addRecipe(ironDoor);
    

    ShapedRecipe spruceDoor = new ShapedRecipe(new ItemStack(this.spruceDoorMat, 3)).shape(new String[] { "xx ", "xx ", "xx " }).setIngredient('x', Material.WOOD, 1);
    addRecipe(spruceDoor);
    

    ShapedRecipe birchDoor = new ShapedRecipe(new ItemStack(this.birchDoorMat, 3)).shape(new String[] { "xx ", "xx ", "xx " }).setIngredient('x', Material.WOOD, 2);
    addRecipe(birchDoor);
    

    ShapedRecipe jungleDoor = new ShapedRecipe(new ItemStack(this.jungleDoorMat, 3)).shape(new String[] { "xx ", "xx ", "xx " }).setIngredient('x', Material.WOOD, 3);
    addRecipe(jungleDoor);
    

    ShapedRecipe darkOakDoor = new ShapedRecipe(new ItemStack(this.darkOakDoorMat, 3)).shape(new String[] { "xx ", "xx ", "xx " }).setIngredient('x', Material.WOOD, 5);
    addRecipe(darkOakDoor);
    

    ShapedRecipe acaciaDoor = new ShapedRecipe(new ItemStack(this.acaciaDoorMat, 3)).shape(new String[] { "xx ", "xx ", "xx " }).setIngredient('x', Material.WOOD, 4);
    addRecipe(acaciaDoor);
    

    ShapedRecipe ironTrapDoor = new ShapedRecipe(new ItemStack(this.ironTrapdoorMat, 1)).shape(new String[] { "xx", "xx" }).setIngredient('x', Material.IRON_INGOT);
    addRecipe(ironTrapDoor);
    

    FurnaceRecipe wetSpongeFurnace = new FurnaceRecipe(new ItemStack(Material.SPONGE, 1, (short)0), Material.SPONGE).setInput(Material.SPONGE, 1);
    addRecipe(wetSpongeFurnace);
    
    FurnaceRecipe cobbleFurnace = new FurnaceRecipe(new ItemStack(Material.STONE, 1, (short)0), Material.STONE).setInput(Material.COBBLESTONE, 1);
    addRecipe(cobbleFurnace);

    FurnaceRecipe rawRabbit = new FurnaceRecipe(new ItemStack(this.cookedRabbitItemMat, 1, (short)0), this.cookedRabbitItemMat).setInput(this.rabbitItemMat);
    addRecipe(rawRabbit);
 
    FurnaceRecipe crackedStoneBricks = new FurnaceRecipe(new ItemStack(Material.SMOOTH_BRICK, 1, (short)2), Material.SMOOTH_BRICK).setInput(Material.SMOOTH_BRICK);
    addRecipe(crackedStoneBricks);    

    FurnaceRecipe rawMutton = new FurnaceRecipe(new ItemStack(this.cookedMuttonItemMat, 1, (short)0), this.cookedMuttonItemMat).setInput(this.muttonItemMat);
    addRecipe(rawMutton);
    
    ShapedRecipe rabbitStew = new ShapedRecipe(new ItemStack(this.rabbitStewItemMat, 1)).shape(new String[] { " r ", "cpm", " b " }).setIngredient('r', this.cookedRabbitItemMat).setIngredient('c', Material.CARROT_ITEM).setIngredient('p', Material.BAKED_POTATO).setIngredient('m', Material.RED_MUSHROOM).setIngredient('b', Material.BOWL);
    addRecipe(rabbitStew);
    
    ShapedRecipe rabbitStew2 = new ShapedRecipe(new ItemStack(this.rabbitStewItemMat, 1)).shape(new String[] { " r ", "cpm", " b " }).setIngredient('r', this.cookedRabbitItemMat).setIngredient('c', Material.CARROT_ITEM).setIngredient('p', Material.BAKED_POTATO).setIngredient('m', Material.BROWN_MUSHROOM).setIngredient('b', Material.BOWL);
    addRecipe(rabbitStew2);

    ShapelessRecipe chiseledStone = new ShapelessRecipe(new ItemStack(Material.SMOOTH_BRICK, 1, (short)2)).addIngredient(Material.STONE, 1).addIngredient(Material.VINE);
    addRecipe(chiseledStone);

    ShapelessRecipe chiseledStone2 = new ShapelessRecipe(new ItemStack(Material.SMOOTH_BRICK, 1, (short)3)).addIngredient(Material.STEP, 5).addIngredient(Material.STEP, 5);
    addRecipe(chiseledStone2);

    ShapedRecipe stoneBricks = new ShapedRecipe(new ItemStack(Material.SMOOTH_BRICK, 4)).shape(new String[] { "xx", "xx" }).setIngredient('x', Material.STONE);
    addRecipe(stoneBricks);
    
    ShapelessRecipe mossyStoneBrick = new ShapelessRecipe(new ItemStack(Material.SMOOTH_BRICK, 1, (short)1)).addIngredient(1, Material.SMOOTH_BRICK).addIngredient(1, Material.VINE);
    addRecipe(mossyStoneBrick);
    
    ShapelessRecipe mossStone = new ShapelessRecipe(new ItemStack(Material.MOSSY_COBBLESTONE, 1)).addIngredient(1, Material.COBBLESTONE).addIngredient(1, Material.VINE);
    addRecipe(mossStone);
    
    ShapedRecipe leather = new ShapedRecipe(new ItemStack(Material.LEATHER, 1)).shape(new String[] { "ll", "ll" }).setIngredient('l', this.rabbitHideItemMat);
    addRecipe(leather);

  }
  
  /**
  private void worldBorderListeners() {
      p_72364_1_[0].getWorldBorder().addListener(new IBorderListener()
        {
            private static final String __OBFID = "CL_00002267";
            public void onSizeChanged(WorldBorder border, double newSize)
            {
                ServerConfigurationManager.this.sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_SIZE));
            }
            public void func_177692_a(WorldBorder border, double p_177692_2_, double p_177692_4_, long p_177692_6_)
            {
                ServerConfigurationManager.this.sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.LERP_SIZE));
            }
            public void onCenterChanged(WorldBorder border, double x, double z)
            {
                ServerConfigurationManager.this.sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_CENTER));
            }
            public void onWarningTimeChanged(WorldBorder border, int p_177691_2_)
            {
                ServerConfigurationManager.this.sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_WARNING_TIME));
            }
            public void onWarningDistanceChanged(WorldBorder border, int p_177690_2_)
            {
                ServerConfigurationManager.this.sendPacketToAllPlayers(new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_WARNING_BLOCKS));
            }
            public void func_177696_b(WorldBorder border, double p_177696_2_) {}
            public void func_177695_c(WorldBorder border, double p_177695_2_) {}
        });
  }
  * **/

  public void addRecipe(Recipe recipe) {
    Bukkit.getServer().addRecipe(recipe);
  }
}
