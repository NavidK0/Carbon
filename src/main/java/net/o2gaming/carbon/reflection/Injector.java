package net.o2gaming.carbon.reflection;

import java.util.Iterator;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemBlock;
import net.minecraft.server.v1_7_R4.ItemMultiTexture;
import net.minecraft.server.v1_7_R4.RegistryID;
import net.o2gaming.carbon.Carbon;
import net.o2gaming.carbon.Utilities;
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
import net.o2gaming.carbon.items.ItemWoodenDoor.DoorType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

/**
 *
 * @author Navid
 */
public class Injector {
    //Blocks
    public Block stoneBlock = new BlockStone();
    public Block slimeBlock = new BlockSlime();
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
    public Block spruceDoorBlock = new BlockWoodenDoor(DoorType.SPRUCE);
    public Block birchDoorBlock = new BlockWoodenDoor(DoorType.BIRCH);
    public Block jungleDoorBlock = new BlockWoodenDoor(DoorType.JUNGLE);
    public Block darkOakDoorBlock = new BlockWoodenDoor(DoorType.DARK_OAK);
    public Block acaciaDoorBlock = new BlockWoodenDoor(DoorType.ACACIA);
    public Block ironTrapDoorBlock = new BlockIronTrapdoor();
    public Block redSandstoneStairsBlock = new BlockRedSandstoneStairs(redSandstoneBlock, 0);
    public Block redSandstoneSlabBlock = new BlockStep(false, true);
    public Block redSandstoneDoubleSlabBlock = new BlockStep(true, true);
    public Block prismarineBlock = new BlockPrismarine();
    public Block seaLanternBlock = new BlockSeaLantern();
    public Block spongeBlock = new BlockSponge();
    
    //Bukkit/Spigot Materials
    //public Material stoneMat = Utilities.addMaterial("STONE", 1);
    //public Material spongeMat = Utilities.addMaterial("SPONGE", 19);
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
    public Material spruceDoorBlockMat = Utilities.addMaterial("SPRUCE_DOOR", 193);
    public Material birchDoorBlockMat = Utilities.addMaterial("BIRCH_DOOR", 194);
    public Material jungleDoorBlockMat = Utilities.addMaterial("JUNGLE_DOOR", 195);
    public Material acaciaDoorBlockMat = Utilities.addMaterial("ACACIA_DOOR", 196);
    public Material darkOakDoorBlockMat = Utilities.addMaterial("DARK_OAK_DOOR", 197);
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
    
    //Block Items
    public Item stoneItem = new ItemMultiTexture(stoneBlock, stoneBlock, BlockStone.names).b("stone");
    public Item slimeItem = new ItemBlock(slimeBlock);
    public Item barrierItem = new ItemBlock(barrierBlock);
    public Item redSandstoneItem = new ItemMultiTexture(redSandstoneBlock, redSandstoneBlock, BlockRedSandstone.names).b("red_sandstone");
    public Item spruceFenceGateItem = new ItemBlock(spruceFenceBlockGate);
    public Item birchFenceGateItem = new ItemBlock(birchFenceBlockGate);
    public Item jungleFenceGateItem = new ItemBlock(jungleFenceBlockGate);
    public Item darkOakFenceGateItem = new ItemBlock(darkOakFenceBlockGate);
    public Item acaciaFenceGateItem = new ItemBlock(acaciaFenceBlockGate);
    public Item spruceFenceItem = new ItemBlock(spruceFenceBlock);
    public Item birchFenceItem = new ItemBlock(birchFenceBlock);
    public Item jungleFenceItem = new ItemBlock(jungleFenceBlock);
    public Item darkOakFenceItem = new ItemBlock(darkOakFenceBlock);
    public Item acaciaFenceItem = new ItemBlock(acaciaFenceBlock);
    public Item spruceDoorItem = new ItemWoodenDoor(DoorType.SPRUCE);
    public Item birchDoorItem = new ItemWoodenDoor(DoorType.BIRCH);
    public Item jungleDoorItem = new ItemWoodenDoor(DoorType.JUNGLE);
    public Item darkOakDoorItem = new ItemWoodenDoor(DoorType.DARK_OAK);
    public Item acaciaDoorItem = new ItemWoodenDoor(DoorType.ACACIA);
    public Item ironTrapDoorItem = new ItemBlock(ironTrapDoorBlock);
    public Item redSandstoneStairsItem = new ItemBlock(redSandstoneStairsBlock);
    public Item redSandstoneSlabItem = new ItemBlock(redSandstoneSlabBlock);
    public Item redSandstoneDoubleSlabItem = new ItemBlock(redSandstoneDoubleSlabBlock);
    public Item prismarineItem = new ItemMultiTexture(prismarineBlock, prismarineBlock, BlockPrismarine.names);
    public Item seaLanternItem = new ItemBlock(seaLanternBlock);
    public Item spongeItem = new ItemMultiTexture(spongeBlock, spongeBlock, BlockSponge.names);
    
    //Other Items
    public Item rabbitItem = new ItemRabbit();
    public Item cookedRabbitItem = new ItemCookedRabbit();
    public Item rabbitStewItem = new ItemRabbitStew();
    public Item rabbitFootItem = new ItemRabbitFoot();
    public Item rabbitHideItem = new ItemRabbitHide();
    public Item muttonItem = new ItemMutton();
    public Item cookedMuttonItem = new ItemCookedMutton();
    public Item prismarineShardItem = new ItemPrismarineShard();
    public Item prismarineCrystalItem = new ItemPrismarineCrystal();
        
    
    public static void registerBlock(Material mat, int id, String name, Block block) {
        Block.REGISTRY.a(id, name, block);
    }
    
    public static void registerBlock(Material mat, int id, String name, Block block, Item item) {
        Block.REGISTRY.a(id, name, block);
        Item.REGISTRY.a(id, name, item);
    }
    
    public static void registerItem(Material mat, int id, String name, Item item) {
        Item.REGISTRY.a(id, name, item);
    }

    /**
     * Convenience method.
     */
    public void registerAll() {
        
        registerBlock(Material.STONE, 1, "stone", stoneBlock, stoneItem);
        registerBlock(Material.SPONGE, 19, "sponge", spongeBlock, spongeItem);
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
        
        //Register Items seperate from Blocks
        registerItem(prismarineShardMat, 409, "prismarine_shard", prismarineShardItem);
        registerItem(prismarineCrystalsMat, 410, "prismarine_crystals", prismarineCrystalItem);
        registerItem(rabbitItemMat, 411, "rabbit", rabbitItem);
        registerItem(cookedRabbitItemMat, 412, "cooked_rabbit", cookedRabbitItem);
        registerItem(rabbitStewItemMat, 413, "rabbit_stew", rabbitStewItem);
        registerItem(rabbitFootItemMat, 414, "rabbit_foot", rabbitFootItem);
        registerItem(rabbitHideItemMat, 415, "rabbit_hide", rabbitHideItem);
        registerItem(muttonItemMat, 423, "mutton", muttonItem);
        registerItem(cookedMuttonItemMat, 424, "cooked_mutton", cookedMuttonItem);
        
    }
    
    
    public void registerRecipes() {
       /*
        Yes, before you say anything, this plugin does erase the old recipes.
        This is to allow the new recipes to come in...
        But there's no real way to tell if the recipe is the old recipe or not, we so just
        check the type here.
        Stone recipes are also reset, so we have to re-register them here.
        */
        Iterator<Recipe> ri = Bukkit.getServer().recipeIterator();
        while(ri.hasNext()) {
            Recipe recipe = ri.next();
            if (recipe.getResult().getType() == (Material.WOOD_DOOR)) {
                ri.remove();
            }
            if (recipe.getResult().getType() == (Material.IRON_DOOR)) {
                ri.remove();
            }
            if (recipe.getResult().getType() == (Material.FENCE_GATE)) {
                ri.remove();
            }
            if (recipe.getResult().getType() == (Material.FENCE)) {
                ri.remove();
            }
            if (recipe.getResult().getType() == (Material.STONE)) {
                ri.remove();
            }
        }
        
        //Coarse dirt recipe
        ShapedRecipe coarseDirt = new ShapedRecipe(new ItemStack(Material.DIRT, 4, (short) 1)).shape("dg", "gd").setIngredient('d', Material.DIRT).setIngredient('g', Material.GRAVEL);
        addRecipe(coarseDirt);
        
        //Coarse dirt flipped recipe
        ShapedRecipe coarseDirt2 = new ShapedRecipe(new ItemStack(Material.DIRT, 4, (short) 1)).shape("gd", "dg").setIngredient('d', Material.DIRT).setIngredient('g', Material.GRAVEL);
        addRecipe(coarseDirt2);
        
        //Diorite recipe
        ShapedRecipe diorite = new ShapedRecipe(new ItemStack(Material.STONE, 2, (short) 3)).shape(new String[] {"cq", "qc"}).setIngredient('c', Material.COBBLESTONE).setIngredient('q', Material.QUARTZ);
        addRecipe(diorite);
        
        //Diorite flipped recipe
        ShapedRecipe diorite2 = new ShapedRecipe(new ItemStack(Material.STONE, 2, (short) 3)).shape(new String[] {"cq", "qc"}).setIngredient('c', Material.COBBLESTONE).setIngredient('q', Material.QUARTZ);
        addRecipe(diorite2);
        
        //Andesite recipe
        ShapelessRecipe andesite = new ShapelessRecipe(new ItemStack(Material.STONE, 2, (short) 5)).addIngredient(1, Material.STONE, 3).addIngredient(1, Material.COBBLESTONE);
        addRecipe(andesite);
        
        //Granite recipe
        ShapelessRecipe granite = new ShapelessRecipe(new ItemStack(Material.STONE, 1, (short) 1)).addIngredient(1, Material.STONE, 3).addIngredient(1, Material.QUARTZ);
        addRecipe(granite);
        
        //Polished Diorite recipe
        ShapedRecipe polishedDiorite = new ShapedRecipe(new ItemStack(Material.STONE, 4, (short) 4)).shape(new String[] {"ss", "ss"}).setIngredient('s', Material.STONE, 3);
        addRecipe(polishedDiorite);
        
        //Polished Andesite recipe
        ShapedRecipe polishedAndesite = new ShapedRecipe(new ItemStack(Material.STONE, 4, (short) 6)).shape(new String[] {"ss", "ss"}).setIngredient('s', Material.STONE, 5);
        addRecipe(polishedAndesite);
        
        //Polished Granite recipe
        ShapedRecipe polishedGranite = new ShapedRecipe(new ItemStack(Material.STONE, 4, (short) 2)).shape(new String[] {"ss", "ss"}).setIngredient('s', Material.STONE, 1);
        addRecipe(polishedGranite);
        
        //Slimeblock recipe
        ShapedRecipe slimeRec = new ShapedRecipe(new ItemStack(slimeMat)).shape("sss","sss","sss").setIngredient('s', Material.SLIME_BALL);
        addRecipe(slimeRec);
        
        //Slimeblock into slimeballs recipe
        ShapelessRecipe slimeBalls = new ShapelessRecipe(new ItemStack(Material.SLIME_BALL, 9)).addIngredient(slimeMat);
        addRecipe(slimeBalls);

        //Red Sandstone recipe
        ShapedRecipe redSandstone = new ShapedRecipe(new ItemStack(redSandstoneMat, 4, (short) 0)).shape(new String[] {"ss", "ss"}).setIngredient('s', Material.SAND, 1);
        addRecipe(redSandstone);
        
        //Red Sandstone Smooth recipe
        ShapedRecipe smoothRedSandstone = new ShapedRecipe(new ItemStack(redSandstoneMat, 4, (short) 2)).shape(new String[] {"ss", "ss"}).setIngredient('s', redSandstoneMat);
        addRecipe(smoothRedSandstone);
        
        //Red Sandstone Chiseled recipe
        ShapedRecipe chiseledRedSandstone = new ShapedRecipe(new ItemStack(redSandstoneMat, 4, (short) 1)).shape(new String[] {"s", "s"}).setIngredient('s', redSandstoneSlabMat);
        addRecipe(chiseledRedSandstone);
        
        //Red Sandstone Slabs
        ShapedRecipe redSandstoneSlabs = new ShapedRecipe(new ItemStack(redSandstoneSlabMat, 6, (short)0)).shape(new String[] {"sss"}).setIngredient('s', redSandstoneMat);
        addRecipe(redSandstoneSlabs);
        
        //Red Stairs 1 recipe
        ShapedRecipe redSandstoneStairs = new ShapedRecipe(new ItemStack(redSandstoneStairsMat, 4)).shape(new String[] { "  s", " ss", "sss" }).setIngredient('s', redSandstoneMat);
        addRecipe(redSandstoneStairs);
        
        //Red Stairs 2 recipe
        ShapedRecipe redSandstoneStairs2 = new ShapedRecipe(new ItemStack(redSandstoneStairsMat, 4)).shape(new String[] { "s  ", "ss ", "sss" }).setIngredient('s', redSandstoneMat);
        addRecipe(redSandstoneStairs2);
        
        //Sea Lantern recipe
        ShapedRecipe seaLanternRecipe = new ShapedRecipe(new ItemStack(seaLaternMat)).shape("*x*", "xxx", "*x*").setIngredient('*', prismarineShardMat)
                .setIngredient('x', prismarineCrystalsMat);
        addRecipe(seaLanternRecipe);
        
        //Prismarine recipe
        ShapedRecipe prismarineRecipe = new ShapedRecipe(new ItemStack(prismarineBlockMat, 1, (short) 0)).shape("**", "**").setIngredient('*', prismarineShardMat);
        addRecipe(prismarineRecipe);
        
        //Prismarine Bricks recipe
        ShapelessRecipe prismarineBricksRecipe = new ShapelessRecipe(new ItemStack(prismarineBlockMat, 1, (short) 1)).addIngredient(9, prismarineShardMat);
        addRecipe(prismarineBricksRecipe);
        
        //Prismarine Dark recipe
        ShapedRecipe prismarineDarkRecipe = new ShapedRecipe(new ItemStack(prismarineBlockMat, 1, (short) 2)).shape("***", "*x*", "***").setIngredient('*', prismarineShardMat).setIngredient('x', Material.INK_SACK, 0);
        addRecipe(prismarineDarkRecipe);
        
        //Oak fence recipe
        ShapedRecipe oakFence = new ShapedRecipe(new ItemStack(Material.FENCE, 3)).shape("*x*", "*x*").setIngredient('*', Material.WOOD, 0).setIngredient('x', Material.STICK);
        addRecipe(oakFence); 
        
        //Spruce fence recipe
        ShapedRecipe spruceFence = new ShapedRecipe(new ItemStack(spruceFenceMat, 3)).shape("*x*", "*x*").setIngredient('*', Material.WOOD, 1).setIngredient('x', Material.STICK);
        addRecipe(spruceFence);      
        
        //Birch fence recipe
        ShapedRecipe birchFence = new ShapedRecipe(new ItemStack(birchFenceMat, 3)).shape("*x*", "*x*").setIngredient('*', Material.WOOD, 2).setIngredient('x', Material.STICK);
        addRecipe(birchFence);
        
        //Jungle fence recipe
        ShapedRecipe jungleFence = new ShapedRecipe(new ItemStack(jungleFenceMat, 3)).shape("*x*", "*x*").setIngredient('*', Material.WOOD, 3).setIngredient('x', Material.STICK);
        addRecipe(jungleFence);
        
        //DarkOak fence recipe
        ShapedRecipe darkOakFence = new ShapedRecipe(new ItemStack(darkOakFenceMat, 3)).shape("*x*", "*x*").setIngredient('*', Material.WOOD, 5).setIngredient('x', Material.STICK);
        addRecipe(darkOakFence);
        
        //Acacia fence recipe
        ShapedRecipe acaciaFence = new ShapedRecipe(new ItemStack(acaciaFenceMat, 3)).shape("*x*", "*x*").setIngredient('*', Material.WOOD, 4).setIngredient('x', Material.STICK);
        addRecipe(acaciaFence);
        
        //Oak gate fence recipe
        ShapedRecipe oakGate = new ShapedRecipe(new ItemStack(Material.FENCE_GATE, 1)).shape("*x*", "*x*").setIngredient('x', Material.WOOD, 0).setIngredient('*', Material.STICK);
        addRecipe(oakGate); 
        
        //Spruce gate fence recipe
        ShapedRecipe spruceGate = new ShapedRecipe(new ItemStack(spruceFenceGateMat, 1)).shape("*x*", "*x*").setIngredient('x', Material.WOOD, 1).setIngredient('*', Material.STICK);
        addRecipe(spruceGate);      
        
        //Birch gate fence recipe
        ShapedRecipe birchGate = new ShapedRecipe(new ItemStack(birchFenceGateMat, 1)).shape("*x*", "*x*").setIngredient('x', Material.WOOD, 2).setIngredient('*', Material.STICK);
        addRecipe(birchGate);
        
        //Jungle gate fence recipe
        ShapedRecipe jungleGate = new ShapedRecipe(new ItemStack(jungleFenceGateMat, 1)).shape("*x*", "*x*").setIngredient('x', Material.WOOD, 3).setIngredient('*', Material.STICK);
        addRecipe(jungleGate);
        
        //DarkOak gate fence recipe
        ShapedRecipe darkOakGate = new ShapedRecipe(new ItemStack(darkOakFenceGateMat, 1)).shape("*x*", "*x*").setIngredient('x', Material.WOOD, 5).setIngredient('*', Material.STICK);
        addRecipe(darkOakGate);
        
        //Acacia gate fence recipe
        ShapedRecipe acaciaGate = new ShapedRecipe(new ItemStack(acaciaFenceGateMat, 1)).shape("*x*", "*x*").setIngredient('x', Material.WOOD, 4).setIngredient('*', Material.STICK);
        addRecipe(acaciaGate);
        
        //Oak door recipe
        ShapedRecipe oakDoor = new ShapedRecipe(new ItemStack(Material.WOOD_DOOR, 3)).shape("xx ", "xx ", "xx ").setIngredient('x', Material.WOOD, 0);
        addRecipe(oakDoor);
        
        //Iron door recipe
        ShapedRecipe ironDoor = new ShapedRecipe(new ItemStack(Material.IRON_DOOR, 3)).shape("xx ", "xx ", "xx ").setIngredient('x', Material.IRON_INGOT);
        addRecipe(ironDoor); 
        
        //Spruce door recipe
        ShapedRecipe spruceDoor = new ShapedRecipe(new ItemStack(spruceDoorMat, 3)).shape("xx ", "xx ", "xx ").setIngredient('x', Material.WOOD, 1);
        addRecipe(spruceDoor);      
        
        //Birch door recipe
        ShapedRecipe birchDoor = new ShapedRecipe(new ItemStack(birchDoorMat, 3)).shape("xx ", "xx ", "xx ").setIngredient('x', Material.WOOD, 2);
        addRecipe(birchDoor);
        
        //Jungle door recipe
        ShapedRecipe jungleDoor = new ShapedRecipe(new ItemStack(jungleDoorMat, 3)).shape("xx ", "xx ", "xx ").setIngredient('x', Material.WOOD, 3);
        addRecipe(jungleDoor);
        
        //DarkOak door recipe
        ShapedRecipe darkOakDoor = new ShapedRecipe(new ItemStack(darkOakDoorMat, 3)).shape("xx ", "xx ", "xx ").setIngredient('x', Material.WOOD, 5);
        addRecipe(darkOakDoor);
        
        //Acacia door recipe
        ShapedRecipe acaciaDoor = new ShapedRecipe(new ItemStack(acaciaDoorMat, 3)).shape("xx ", "xx ", "xx ").setIngredient('x', Material.WOOD, 4);
        addRecipe(acaciaDoor);
        
        //Iron trapdoor recipe
        ShapedRecipe ironTrapDoor = new ShapedRecipe(new ItemStack(ironTrapdoorMat, 1)).shape("xx", "xx").setIngredient('x', Material.IRON_INGOT);
        addRecipe(ironTrapDoor);
        
        //Sponge in a furnace
        FurnaceRecipe wetSpongeFurnace = new FurnaceRecipe(new ItemStack(Material.SPONGE, 1, (short)0), Material.SPONGE).setInput(Material.SPONGE, 1);
        addRecipe(wetSpongeFurnace);
        
        //Cobble in a furnace
        FurnaceRecipe cobbleFurnace = new FurnaceRecipe(new ItemStack(Material.STONE, 1, (short)0), Material.STONE).setInput(Material.COBBLESTONE, 1);
        addRecipe(cobbleFurnace);
        
        //Stone button recipe
        ShapelessRecipe button = new ShapelessRecipe(new ItemStack(Material.STONE_BUTTON)).addIngredient(1, Material.STONE);
        addRecipe(button);
        
        //Stone plate recipe
        ShapedRecipe stonePlate = new ShapedRecipe(new ItemStack(Material.STONE_PLATE, 1)).shape("xx").setIngredient('x', Material.STONE);
        addRecipe(stonePlate);
        
        //Comparator recipe
        ShapedRecipe comparator = new ShapedRecipe(new ItemStack(Material.REDSTONE_COMPARATOR, 1)).shape(" r ", "rqr", "sss").setIngredient('r', Material.REDSTONE_TORCH_ON)
                .setIngredient('q', Material.QUARTZ).setIngredient('s', Material.STONE, 0);
        addRecipe(comparator);
        
        //Repeater recipe
        ShapedRecipe repeater = new ShapedRecipe(new ItemStack(Material.DIODE, 1)).shape("trt", "sss").setIngredient('t', Material.REDSTONE_TORCH_ON)
                .setIngredient('r', Material.REDSTONE).setIngredient('s', Material.STONE, 0);
        addRecipe(repeater);
        
        //Stone slab recipe
        ShapedRecipe stoneSlab = new ShapedRecipe(new ItemStack(Material.STEP, 6)).shape("sss").setIngredient('s', Material.STONE);
        addRecipe(stoneSlab);
        
        //Stone bricks recipe
        ShapedRecipe stoneBricks = new ShapedRecipe(new ItemStack(Material.SMOOTH_BRICK, 4)).shape("xx", "xx").setIngredient('x', Material.STONE);
        addRecipe(stoneBricks);
               
        //Raw rabbit in a furnace
        FurnaceRecipe rawRabbit = new FurnaceRecipe(new ItemStack(cookedRabbitItemMat, 1, (short)0), cookedRabbitItemMat).setInput(rabbitItemMat);
        addRecipe(rawRabbit);

        //Raw mutton in a furnace
        FurnaceRecipe rawMutton = new FurnaceRecipe(new ItemStack(cookedMuttonItemMat, 1, (short)0), cookedMuttonItemMat).setInput(muttonItemMat);
        addRecipe(rawMutton);

        //Rabbit stew recipe
        ShapedRecipe rabbitStew = new ShapedRecipe(new ItemStack(rabbitStewItemMat, 1)).shape(" r ", "cpm", " b ").setIngredient('r',  cookedRabbitItemMat)
                .setIngredient('c', Material.CARROT_ITEM).setIngredient('p', Material.BAKED_POTATO).setIngredient('m', Material.RED_MUSHROOM).setIngredient('b', Material.BOWL);
        addRecipe(rabbitStew);

        //Rabbit stew 2 recipe
        ShapedRecipe rabbitStew2 = new ShapedRecipe(new ItemStack(rabbitStewItemMat, 1)).shape(" r ", "cpm", " b ").setIngredient('r',  cookedRabbitItemMat)
                .setIngredient('c', Material.CARROT_ITEM).setIngredient('p', Material.BAKED_POTATO).setIngredient('m', Material.BROWN_MUSHROOM).setIngredient('b', Material.BOWL);
        addRecipe(rabbitStew2);
    }
    
    /**
     * Convenience method.
     * @param recipe 
     */
    public void addRecipe(Recipe recipe) {
        Bukkit.getServer().addRecipe(recipe);
    }
    
}
