package net.o2gaming.carbon.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemBlock;
import net.minecraft.server.v1_7_R4.ItemMultiTexture;
import net.o2gaming.carbon.Carbon;
import net.o2gaming.carbon.Utilities;
import net.o2gaming.carbon.blocks.BlockNewRedstoneTorchOn;
import net.o2gaming.carbon.blocks.BlockNewRedstoneTorchOff;
import net.o2gaming.carbon.blocks.BlockBarrier;
import net.o2gaming.carbon.blocks.BlockIronTrapdoor;
import net.o2gaming.carbon.blocks.BlockPrismarine;
import net.o2gaming.carbon.blocks.BlockRedSandstone;
import net.o2gaming.carbon.blocks.BlockRedSandstoneStairs;
import net.o2gaming.carbon.blocks.BlockSeaLantern;
import net.o2gaming.carbon.blocks.BlockSlime;
import net.o2gaming.carbon.blocks.BlockSponge;
import net.o2gaming.carbon.blocks.BlockStep;
import net.o2gaming.carbon.blocks.BlockStone;
import net.o2gaming.carbon.blocks.BlockWoodenDoor;
import net.o2gaming.carbon.blocks.BlockWoodenFence;
import net.o2gaming.carbon.blocks.BlockWoodenFenceGate;
import net.o2gaming.carbon.blocks.BlockNewTorch;
import net.o2gaming.carbon.items.ItemCookedMutton;
import net.o2gaming.carbon.items.ItemCookedRabbit;
import net.o2gaming.carbon.items.ItemMutton;
import net.o2gaming.carbon.items.ItemPrismarineCrystal;
import net.o2gaming.carbon.items.ItemPrismarineShard;
import net.o2gaming.carbon.items.ItemRabbit;
import net.o2gaming.carbon.items.ItemRabbitFoot;
import net.o2gaming.carbon.items.ItemRabbitHide;
import net.o2gaming.carbon.items.ItemRabbitStew;
import net.o2gaming.carbon.items.ItemWoodenDoor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;




public class Injector {
  //Blocks
  public Block stoneBlock = new BlockStone();
  public Block redstoneTorchBlockOn = new BlockNewRedstoneTorchOn();
  public Block redstoneTorchBlockOff = new BlockNewRedstoneTorchOff();
  public Block slimeBlock = new BlockSlime();
  public Block torchBlock = new BlockNewTorch();
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

  //Bukkit materials
  public Material slimeMat = Utilities.addMaterial("slime", 165);
  public Material barrierMat = Utilities.addMaterial("barrier", 166);
  public Material ironTrapdoorMat = Utilities.addMaterial("iron_trapdoor", 167);
  public Material prismarineBlockMat = Utilities.addMaterial("prismarine", 168);
  public Material seaLaternMat = Utilities.addMaterial("sea_lantern", 169);
  public Material redSandstoneMat = Utilities.addMaterial("red_sandstone", 179);
  public Material redSandstoneStairsMat = Utilities.addMaterial("red_sandstone_stairs", 180);
  public Material redSandstoneDoubleSlabMat = Utilities.addMaterial("red_sandstone_doubleslab", 181);
  public Material redSandstoneSlabMat = Utilities.addMaterial("red_sandstone_slab", 182);
  public Material spruceFenceGateMat = Utilities.addMaterial("spruce_fence_gate", 183);
  public Material birchFenceGateMat = Utilities.addMaterial("birch_fence_gate", 184);
  public Material jungleFenceGateMat = Utilities.addMaterial("jungle_fence_gate", 185);
  public Material darkOakFenceGateMat = Utilities.addMaterial("dark_oak_fence_gate", 186);
  public Material acaciaFenceGateMat = Utilities.addMaterial("acacia_fence_gate", 187);
  public Material spruceFenceMat = Utilities.addMaterial("spruce_fence", 188);
  public Material birchFenceMat = Utilities.addMaterial("birch_fence", 189);
  public Material jungleFenceMat = Utilities.addMaterial("jungle_fence", 190);
  public Material darkOakFenceMat = Utilities.addMaterial("dark_oak_fence", 191);
  public Material acaciaFenceMat = Utilities.addMaterial("acacia_fence", 192);
  public Material spruceDoorBlockMat = Utilities.addMaterial("spruce_door", 193);
  public Material birchDoorBlockMat = Utilities.addMaterial("birch_door", 194);
  public Material jungleDoorBlockMat = Utilities.addMaterial("jungle_door", 195);
  public Material acaciaDoorBlockMat = Utilities.addMaterial("acacia_door", 196);
  public Material darkOakDoorBlockMat = Utilities.addMaterial("dark_oak_door", 197);
  public Material prismarineShardMat = Utilities.addMaterial("prismarine_shard", 409);
  public Material prismarineCrystalsMat = Utilities.addMaterial("prismarine_crystals", 410);
  public Material rabbitItemMat = Utilities.addMaterial("rabbit", 411);
  public Material cookedRabbitItemMat = Utilities.addMaterial("cooked_rabbit", 412);
  public Material rabbitStewItemMat = Utilities.addMaterial("rabbit_stew", 413);
  public Material rabbitFootItemMat = Utilities.addMaterial("rabbit_foot", 414);
  public Material rabbitHideItemMat = Utilities.addMaterial("rabbit_hide", 415);
  public Material armorStandEntityMat = Utilities.addMaterial("armor_stand", 416);
  public Material muttonItemMat = Utilities.addMaterial("mutton", 423);
  public Material cookedMuttonItemMat = Utilities.addMaterial("cooked_mutton", 424);
  public Material spruceDoorMat = Utilities.addMaterial("spruce_door", 427);
  public Material birchDoorMat = Utilities.addMaterial("birch_door", 428);
  public Material jungleDoorMat = Utilities.addMaterial("jungle_door", 429);
  public Material acaciaDoorMat = Utilities.addMaterial("acacia_door", 430);
  public Material darkOakDoorMat = Utilities.addMaterial("dark_oak_door", 431);
  

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
  

  public Item rabbitItem = new ItemRabbit();
  public Item cookedRabbitItem = new ItemCookedRabbit();
  public Item rabbitStewItem = new ItemRabbitStew();
  public Item rabbitFootItem = new ItemRabbitFoot();
  public Item rabbitHideItem = new ItemRabbitHide();
  public Item muttonItem = new ItemMutton();
  public Item cookedMuttonItem = new ItemCookedMutton();
  public Item prismarineShardItem = new ItemPrismarineShard();
  public Item prismarineCrystalItem = new ItemPrismarineCrystal();
  
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
  


  public void registerAll() {
    //Register blocks
    registerBlock(Material.STONE, 1, "stone", this.stoneBlock, this.stoneItem);
    registerBlock(Material.TORCH, 50, "torch", this.torchBlock, this.torchItem);
    registerBlock(Material.REDSTONE_TORCH_ON, 76, "redstone_torch", this.redstoneTorchBlockOn, this.redstoneTorchItemOn);
    registerBlock(Material.REDSTONE_TORCH_OFF, 75, "redstone_torch", this.redstoneTorchBlockOff, this.redstoneTorchItemOff);
    registerBlock(Material.SPONGE, 19, "sponge", this.spongeBlock, this.spongeItem);
    registerBlock(this.slimeMat, 165, "slime", this.slimeBlock, this.slimeItem);
    registerBlock(this.barrierMat, 166, "barrier", this.barrierBlock, this.barrierItem);
    registerBlock(this.ironTrapdoorMat, 167, "iron_trapdoor", this.ironTrapDoorBlock, this.ironTrapDoorItem);
    registerBlock(this.prismarineBlockMat, 168, "prismarine", this.prismarineBlock, this.prismarineItem);
    registerBlock(this.seaLaternMat, 169, "sea_lantern", this.seaLanternBlock, this.seaLanternItem);
    registerBlock(this.redSandstoneMat, 179, "red_sandstone", this.redSandstoneBlock, this.redSandstoneItem);
    registerBlock(this.redSandstoneStairsMat, 180, "red_sandstone_stairs", this.redSandstoneStairsBlock, this.redSandstoneStairsItem);
    registerBlock(this.redSandstoneDoubleSlabMat, 181, "double_stone_slab2", this.redSandstoneDoubleSlabBlock, this.redSandstoneDoubleSlabItem);
    registerBlock(this.redSandstoneSlabMat, 182, "stone_slab2", this.redSandstoneSlabBlock, this.redSandstoneSlabItem);
    registerBlock(this.spruceFenceGateMat, 183, "spruce_fence_gate", this.spruceFenceBlockGate, this.spruceFenceGateItem);
    registerBlock(this.birchFenceGateMat, 184, "birch_fence_gate", this.birchFenceBlockGate, this.birchFenceGateItem);
    registerBlock(this.jungleFenceGateMat, 185, "jungle_fence_gate", this.jungleFenceBlockGate, this.jungleFenceGateItem);
    registerBlock(this.darkOakFenceGateMat, 186, "dark_oak_fence_gate", this.darkOakFenceBlockGate, this.darkOakFenceGateItem);
    registerBlock(this.acaciaFenceGateMat, 187, "acacia_fence_gate", this.acaciaFenceBlockGate, this.acaciaFenceGateItem);
    registerBlock(this.spruceFenceMat, 188, "spruce_fence", this.spruceFenceBlock, this.spruceFenceItem);
    registerBlock(this.birchFenceMat, 189, "birch_fence", this.birchFenceBlock, this.birchFenceItem);
    registerBlock(this.jungleFenceMat, 190, "jungle_fence", this.jungleFenceBlock, this.jungleFenceItem);
    registerBlock(this.darkOakFenceMat, 191, "dark_oak_fence", this.darkOakFenceBlock, this.darkOakFenceItem);
    registerBlock(this.acaciaFenceMat, 192, "acacia_fence", this.acaciaFenceBlock, this.acaciaFenceItem);
    registerBlock(this.spruceDoorMat, 193, "spruce_door", this.spruceDoorBlock);
    registerItem(this.spruceDoorMat, 427, "spruce_door", this.spruceDoorItem);
    registerBlock(this.birchDoorMat, 194, "birch_door", this.birchDoorBlock);
    registerItem(this.birchDoorMat, 428, "birch_door", this.birchDoorItem);
    registerBlock(this.jungleDoorMat, 195, "jungle_door", this.jungleDoorBlock);
    registerItem(this.jungleDoorMat, 429, "jungle_door", this.jungleDoorItem);
    registerBlock(this.acaciaDoorMat, 196, "acacia_door", this.acaciaDoorBlock);
    registerItem(this.acaciaDoorMat, 430, "acacia_door", this.acaciaDoorItem);
    registerBlock(this.darkOakDoorMat, 197, "dark_oak_door", this.darkOakDoorBlock);
    registerItem(this.darkOakDoorMat, 431, "dark_oak_door", this.darkOakDoorItem);
    

    //Register items
    registerItem(this.prismarineShardMat, 409, "prismarine_shard", this.prismarineShardItem);
    registerItem(this.prismarineCrystalsMat, 410, "prismarine_crystals", this.prismarineCrystalItem);
    registerItem(this.rabbitItemMat, 411, "rabbit", this.rabbitItem);
    registerItem(this.cookedRabbitItemMat, 412, "cooked_rabbit", this.cookedRabbitItem);
    registerItem(this.rabbitStewItemMat, 413, "rabbit_stew", this.rabbitStewItem);
    registerItem(this.rabbitFootItemMat, 414, "rabbit_foot", this.rabbitFootItem);
    registerItem(this.rabbitHideItemMat, 415, "rabbit_hide", this.rabbitHideItem);
    registerItem(this.muttonItemMat, 423, "mutton", this.muttonItem);
    registerItem(this.cookedMuttonItemMat, 424, "cooked_mutton", this.cookedMuttonItem);

    //inject our new stone, sponge, torch and redstone torches
    try {
        Class<Blocks> blocksClass = Blocks.class;
        setFinalField(blocksClass, "STONE", Blocks.STONE, Carbon.injector().stoneBlock);
        setFinalField(blocksClass, "SPONGE", Blocks.SPONGE, Carbon.injector().spongeBlock);
        setFinalField(blocksClass, "TORCH", Blocks.TORCH, Carbon.injector().torchBlock);
        setFinalField(blocksClass, "REDSTONE_TORCH_ON", Blocks.REDSTONE_TORCH_ON, Carbon.injector().redstoneTorchBlockOn);
        setFinalField(blocksClass, "REDSTONE_TORCH_OFF", Blocks.REDSTONE_TORCH_OFF, Carbon.injector().redstoneTorchBlockOff);
     } catch (Throwable t) {
    	 t.printStackTrace();
    	 Bukkit.shutdown();
     }

  }

  private void setFinalField(Class<?> clazz, String fieldname, Object object, Object newValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
	  Field field = clazz.getDeclaredField(fieldname);
	  field.setAccessible(true);
	  Field fieldModifiers = Field.class.getDeclaredField("modifiers");
	  fieldModifiers.setAccessible(true);
	  fieldModifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
	  field.set(object, newValue);
  }
  








  public void registerRecipes() {
    //Remove all recipes that have the new items so we can add the recipes later
    Iterator<Recipe> ri = Bukkit.getServer().recipeIterator();
    while (ri.hasNext()) {
      Recipe recipe = (Recipe)ri.next();
      if (recipe.getResult().getType() == org.bukkit.Material.WOOD_DOOR) {
        ri.remove();
      }
      if (recipe.getResult().getType() == org.bukkit.Material.IRON_DOOR) {
        ri.remove();
      }
      if (recipe.getResult().getType() == org.bukkit.Material.FENCE_GATE) {
        ri.remove();
      }
      if (recipe.getResult().getType() == org.bukkit.Material.FENCE) {
        ri.remove();
      }
      if (recipe.getResult().getType() == org.bukkit.Material.STONE) {
        ri.remove();
      }
    }
    

    ShapedRecipe coarseDirt = new ShapedRecipe(new ItemStack(org.bukkit.Material.DIRT, 4, (short)1)).shape(new String[] { "dg", "gd" }).setIngredient('d', org.bukkit.Material.DIRT).setIngredient('g', org.bukkit.Material.GRAVEL);
    addRecipe(coarseDirt);
    

    ShapedRecipe coarseDirt2 = new ShapedRecipe(new ItemStack(org.bukkit.Material.DIRT, 4, (short)1)).shape(new String[] { "gd", "dg" }).setIngredient('d', org.bukkit.Material.DIRT).setIngredient('g', org.bukkit.Material.GRAVEL);
    addRecipe(coarseDirt2);
    

    ShapedRecipe diorite = new ShapedRecipe(new ItemStack(org.bukkit.Material.STONE, 2, (short)3)).shape(new String[] { "cq", "qc" }).setIngredient('c', org.bukkit.Material.COBBLESTONE).setIngredient('q', org.bukkit.Material.QUARTZ);
    addRecipe(diorite);
    

    ShapedRecipe diorite2 = new ShapedRecipe(new ItemStack(org.bukkit.Material.STONE, 2, (short)3)).shape(new String[] { "cq", "qc" }).setIngredient('c', org.bukkit.Material.COBBLESTONE).setIngredient('q', org.bukkit.Material.QUARTZ);
    addRecipe(diorite2);
    

    ShapelessRecipe andesite = new ShapelessRecipe(new ItemStack(org.bukkit.Material.STONE, 2, (short)5)).addIngredient(1, org.bukkit.Material.STONE, 3).addIngredient(1, org.bukkit.Material.COBBLESTONE);
    addRecipe(andesite);
    

    ShapelessRecipe granite = new ShapelessRecipe(new ItemStack(org.bukkit.Material.STONE, 1, (short)1)).addIngredient(1, org.bukkit.Material.STONE, 3).addIngredient(1, org.bukkit.Material.QUARTZ);
    addRecipe(granite);
    

    ShapedRecipe polishedDiorite = new ShapedRecipe(new ItemStack(org.bukkit.Material.STONE, 4, (short)4)).shape(new String[] { "ss", "ss" }).setIngredient('s', org.bukkit.Material.STONE, 3);
    addRecipe(polishedDiorite);
    

    ShapedRecipe polishedAndesite = new ShapedRecipe(new ItemStack(org.bukkit.Material.STONE, 4, (short)6)).shape(new String[] { "ss", "ss" }).setIngredient('s', org.bukkit.Material.STONE, 5);
    addRecipe(polishedAndesite);
    

    ShapedRecipe polishedGranite = new ShapedRecipe(new ItemStack(org.bukkit.Material.STONE, 4, (short)2)).shape(new String[] { "ss", "ss" }).setIngredient('s', org.bukkit.Material.STONE, 1);
    addRecipe(polishedGranite);
    

    ShapedRecipe slimeRec = new ShapedRecipe(new ItemStack(this.slimeMat)).shape(new String[] { "sss", "sss", "sss" }).setIngredient('s', org.bukkit.Material.SLIME_BALL);
    addRecipe(slimeRec);
    

    ShapelessRecipe slimeBalls = new ShapelessRecipe(new ItemStack(org.bukkit.Material.SLIME_BALL, 9)).addIngredient(this.slimeMat);
    addRecipe(slimeBalls);
    

    ShapedRecipe redSandstone = new ShapedRecipe(new ItemStack(this.redSandstoneMat, 4, (short)0)).shape(new String[] { "ss", "ss" }).setIngredient('s', org.bukkit.Material.SAND, 1);
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
    

    ShapedRecipe prismarineDarkRecipe = new ShapedRecipe(new ItemStack(this.prismarineBlockMat, 1, (short)2)).shape(new String[] { "***", "*x*", "***" }).setIngredient('*', this.prismarineShardMat).setIngredient('x', org.bukkit.Material.INK_SACK, 0);
    addRecipe(prismarineDarkRecipe);
    

    ShapedRecipe oakFence = new ShapedRecipe(new ItemStack(org.bukkit.Material.FENCE, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', org.bukkit.Material.WOOD, 0).setIngredient('x', org.bukkit.Material.STICK);
    addRecipe(oakFence);
    

    ShapedRecipe spruceFence = new ShapedRecipe(new ItemStack(this.spruceFenceMat, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', org.bukkit.Material.WOOD, 1).setIngredient('x', org.bukkit.Material.STICK);
    addRecipe(spruceFence);
    

    ShapedRecipe birchFence = new ShapedRecipe(new ItemStack(this.birchFenceMat, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', org.bukkit.Material.WOOD, 2).setIngredient('x', org.bukkit.Material.STICK);
    addRecipe(birchFence);
    

    ShapedRecipe jungleFence = new ShapedRecipe(new ItemStack(this.jungleFenceMat, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', org.bukkit.Material.WOOD, 3).setIngredient('x', org.bukkit.Material.STICK);
    addRecipe(jungleFence);
    

    ShapedRecipe darkOakFence = new ShapedRecipe(new ItemStack(this.darkOakFenceMat, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', org.bukkit.Material.WOOD, 5).setIngredient('x', org.bukkit.Material.STICK);
    addRecipe(darkOakFence);
    

    ShapedRecipe acaciaFence = new ShapedRecipe(new ItemStack(this.acaciaFenceMat, 3)).shape(new String[] { "*x*", "*x*" }).setIngredient('*', org.bukkit.Material.WOOD, 4).setIngredient('x', org.bukkit.Material.STICK);
    addRecipe(acaciaFence);
    

    ShapedRecipe oakGate = new ShapedRecipe(new ItemStack(org.bukkit.Material.FENCE_GATE, 1)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', org.bukkit.Material.WOOD, 0).setIngredient('*', org.bukkit.Material.STICK);
    addRecipe(oakGate);
    

    ShapedRecipe spruceGate = new ShapedRecipe(new ItemStack(this.spruceFenceGateMat, 1)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', org.bukkit.Material.WOOD, 1).setIngredient('*', org.bukkit.Material.STICK);
    addRecipe(spruceGate);
    

    ShapedRecipe birchGate = new ShapedRecipe(new ItemStack(this.birchFenceGateMat, 1)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', org.bukkit.Material.WOOD, 2).setIngredient('*', org.bukkit.Material.STICK);
    addRecipe(birchGate);
    

    ShapedRecipe jungleGate = new ShapedRecipe(new ItemStack(this.jungleFenceGateMat, 1)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', org.bukkit.Material.WOOD, 3).setIngredient('*', org.bukkit.Material.STICK);
    addRecipe(jungleGate);
    

    ShapedRecipe darkOakGate = new ShapedRecipe(new ItemStack(this.darkOakFenceGateMat, 1)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', org.bukkit.Material.WOOD, 5).setIngredient('*', org.bukkit.Material.STICK);
    addRecipe(darkOakGate);
    

    ShapedRecipe acaciaGate = new ShapedRecipe(new ItemStack(this.acaciaFenceGateMat, 1)).shape(new String[] { "*x*", "*x*" }).setIngredient('x', org.bukkit.Material.WOOD, 4).setIngredient('*', org.bukkit.Material.STICK);
    addRecipe(acaciaGate);
    

    ShapedRecipe oakDoor = new ShapedRecipe(new ItemStack(org.bukkit.Material.WOOD_DOOR, 3)).shape(new String[] { "xx ", "xx ", "xx " }).setIngredient('x', org.bukkit.Material.WOOD, 0);
    addRecipe(oakDoor);
    

    ShapedRecipe ironDoor = new ShapedRecipe(new ItemStack(org.bukkit.Material.IRON_DOOR, 3)).shape(new String[] { "xx ", "xx ", "xx " }).setIngredient('x', org.bukkit.Material.IRON_INGOT);
    addRecipe(ironDoor);
    

    ShapedRecipe spruceDoor = new ShapedRecipe(new ItemStack(this.spruceDoorMat, 3)).shape(new String[] { "xx ", "xx ", "xx " }).setIngredient('x', org.bukkit.Material.WOOD, 1);
    addRecipe(spruceDoor);
    

    ShapedRecipe birchDoor = new ShapedRecipe(new ItemStack(this.birchDoorMat, 3)).shape(new String[] { "xx ", "xx ", "xx " }).setIngredient('x', org.bukkit.Material.WOOD, 2);
    addRecipe(birchDoor);
    

    ShapedRecipe jungleDoor = new ShapedRecipe(new ItemStack(this.jungleDoorMat, 3)).shape(new String[] { "xx ", "xx ", "xx " }).setIngredient('x', org.bukkit.Material.WOOD, 3);
    addRecipe(jungleDoor);
    

    ShapedRecipe darkOakDoor = new ShapedRecipe(new ItemStack(this.darkOakDoorMat, 3)).shape(new String[] { "xx ", "xx ", "xx " }).setIngredient('x', org.bukkit.Material.WOOD, 5);
    addRecipe(darkOakDoor);
    

    ShapedRecipe acaciaDoor = new ShapedRecipe(new ItemStack(this.acaciaDoorMat, 3)).shape(new String[] { "xx ", "xx ", "xx " }).setIngredient('x', org.bukkit.Material.WOOD, 4);
    addRecipe(acaciaDoor);
    

    ShapedRecipe ironTrapDoor = new ShapedRecipe(new ItemStack(this.ironTrapdoorMat, 1)).shape(new String[] { "xx", "xx" }).setIngredient('x', org.bukkit.Material.IRON_INGOT);
    addRecipe(ironTrapDoor);
    

    FurnaceRecipe wetSpongeFurnace = new FurnaceRecipe(new ItemStack(Material.SPONGE, 1, (short)0), Material.SPONGE).setInput(Material.SPONGE, 1);
    addRecipe(wetSpongeFurnace);
    
    FurnaceRecipe cobbleFurnace = new FurnaceRecipe(new ItemStack(org.bukkit.Material.STONE, 1, (short)0), org.bukkit.Material.STONE).setInput(org.bukkit.Material.COBBLESTONE, 1);
    addRecipe(cobbleFurnace);
    

    ShapelessRecipe button = new ShapelessRecipe(new ItemStack(org.bukkit.Material.STONE_BUTTON)).addIngredient(1, org.bukkit.Material.STONE);
    addRecipe(button);
    

    ShapedRecipe stonePlate = new ShapedRecipe(new ItemStack(org.bukkit.Material.STONE_PLATE, 1)).shape(new String[] { "xx" }).setIngredient('x', org.bukkit.Material.STONE);
    addRecipe(stonePlate);
    


    ShapedRecipe comparator = new ShapedRecipe(new ItemStack(org.bukkit.Material.REDSTONE_COMPARATOR, 1)).shape(new String[] { " r ", "rqr", "sss" }).setIngredient('r', org.bukkit.Material.REDSTONE_TORCH_ON).setIngredient('q', org.bukkit.Material.QUARTZ).setIngredient('s', org.bukkit.Material.STONE, 0);
    addRecipe(comparator);
    


    ShapedRecipe repeater = new ShapedRecipe(new ItemStack(org.bukkit.Material.DIODE, 1)).shape(new String[] { "trt", "sss" }).setIngredient('t', org.bukkit.Material.REDSTONE_TORCH_ON).setIngredient('r', org.bukkit.Material.REDSTONE).setIngredient('s', org.bukkit.Material.STONE, 0);
    addRecipe(repeater);
    

    ShapedRecipe stoneSlab = new ShapedRecipe(new ItemStack(org.bukkit.Material.STEP, 6)).shape(new String[] { "sss" }).setIngredient('s', org.bukkit.Material.STONE);
    addRecipe(stoneSlab);
    

    ShapedRecipe stoneBricks = new ShapedRecipe(new ItemStack(org.bukkit.Material.SMOOTH_BRICK, 4)).shape(new String[] { "xx", "xx" }).setIngredient('x', org.bukkit.Material.STONE);
    addRecipe(stoneBricks);

    FurnaceRecipe rawRabbit = new FurnaceRecipe(new ItemStack(this.cookedRabbitItemMat, 1, (short)0), this.cookedRabbitItemMat).setInput(this.rabbitItemMat);
    addRecipe(rawRabbit);
    

    FurnaceRecipe rawMutton = new FurnaceRecipe(new ItemStack(this.cookedMuttonItemMat, 1, (short)0), this.cookedMuttonItemMat).setInput(this.muttonItemMat);
    addRecipe(rawMutton);
    
    
    ShapedRecipe rabbitStew = new ShapedRecipe(new ItemStack(this.rabbitStewItemMat, 1)).shape(new String[] { " r ", "cpm", " b " }).setIngredient('r', this.cookedRabbitItemMat).setIngredient('c', org.bukkit.Material.CARROT_ITEM).setIngredient('p', org.bukkit.Material.BAKED_POTATO).setIngredient('m', org.bukkit.Material.RED_MUSHROOM).setIngredient('b', org.bukkit.Material.BOWL);
    addRecipe(rabbitStew);
    
    
    ShapedRecipe rabbitStew2 = new ShapedRecipe(new ItemStack(this.rabbitStewItemMat, 1)).shape(new String[] { " r ", "cpm", " b " }).setIngredient('r', this.cookedRabbitItemMat).setIngredient('c', org.bukkit.Material.CARROT_ITEM).setIngredient('p', org.bukkit.Material.BAKED_POTATO).setIngredient('m', org.bukkit.Material.BROWN_MUSHROOM).setIngredient('b', org.bukkit.Material.BOWL);
    addRecipe(rabbitStew2);
    
    
    ShapedRecipe mossStone = new ShapedRecipe(new ItemStack(org.bukkit.Material.MOSSY_COBBLESTONE, 1)).shape(new String[] {"s", "v"}).setIngredient('s', org.bukkit.Material.COBBLESTONE)
            .setIngredient('v', org.bukkit.Material.VINE);
    addRecipe(mossStone);
    
    
    ShapedRecipe cobbleWall = new ShapedRecipe(new ItemStack(org.bukkit.Material.COBBLE_WALL, 6)).shape(new String[] {"sss", "sss"}).setIngredient('s', org.bukkit.Material.COBBLESTONE);
    addRecipe(cobbleWall);
    
    
    ShapedRecipe mossyCobbleWall = new ShapedRecipe(new ItemStack(org.bukkit.Material.COBBLE_WALL, 6, (short)1)).shape(new String[] {"sss", "sss"}).setIngredient('s', org.bukkit.Material.MOSSY_COBBLESTONE);
    addRecipe(mossyCobbleWall);
  }

  public void addRecipe(Recipe recipe) {
    Bukkit.getServer().addRecipe(recipe);
  }
}
