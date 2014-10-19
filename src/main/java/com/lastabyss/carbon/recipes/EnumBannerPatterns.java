package com.lastabyss.carbon.recipes;

import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;

public enum EnumBannerPatterns {

	BASE("base", "b"), 
	SQUARE_BOTTOM_LEFT("square_bottom_left", "bl", "   ", "   ", "#  "), 
	SQUARE_BOTTOM_RIGHT("square_bottom_right", "br", "   ", "   ", "  #"), 
	SQUARE_TOP_LEFT("square_top_left", "tl", "#  ", "   ", "   "), 
	SQUARE_TOP_RIGHT("square_top_right", "tr", "  #", "   ", "   "), 
	STRIPE_BOTTOM("stripe_bottom", "bs", "   ", "   ", "###"), 
	STRIPE_TOP("stripe_top", "ts", "###", "   ", "   "), 
	STRIPE_LEFT("stripe_left", "ls", "#  ", "#  ", "#  "), 
	STRIPE_RIGHT("stripe_right", "rs", "  #", "  #", "  #"), 
	STRIPE_CENTER("stripe_center", "cs", " # ", " # ", " # "), 
	STRIPE_MIDDLE("stripe_middle", "ms", "   ", "###", "   "), 
	STRIPE_DOWNRIGHT("stripe_downright", "drs", "#  ", " # ", "  #"), 
	STRIPE_DOWNLEFT("stripe_downleft", "dls", "  #", " # ", "#  "), 
	STRIPE_SMALL("small_stripes", "ss", "# #", "# #", "   "), 
	CROSS("cross", "cr", "# #", " # ", "# #"), 
	STRAIGHT_CROSS("straight_cross", "sc", " # ", "###", " # "), 
	TRIANGLE_BOTTOM("triangle_bottom", "bt", "   ", " # ", "# #"), 
	TRIANGLE_TOP("triangle_top", "tt", "# #", " # ", "   "), 
	TRIANGLES_BOTTOM("triangles_bottom", "bts", "   ", "# #", " # "), 
	TRIANGLES_TOP("triangles_top", "tts", " # ", "# #", "   "), 
	DIAGONAL_LEFT("diagonal_left", "ld", "## ", "#  ", "   "), 
	DIAGONAL_RIGHT("diagonal_up_right", "rd", "   ", "  #", " ##"),
	DIAGONAL_LEFT_MIRROR("diagonal_up_left", "lud", "   ", "#  ", "## "), 
	DIAGONAL_RIGHT_MIRROR("diagonal_right", "rud", " ##", "  #", "   "), 
	CIRCLE_MIDDLE("circle", "mc", "   ", " # ", "   "), 
	RHOMBUS_MIDDLE("rhombus", "mr", " # ", "# #", " # "), 
	HALF_VERTICAL("half_vertical", "vh", "## ", "## ", "## "), 
	HALF_HORIZONTAL("half_horizontal", "hh", "###", "###", "   "), 
	HALF_VERTICAL_MIRROR("half_vertical_right", "vhr", " ##", " ##", " ##"), 
	HALF_HORIZONTAL_MIRROR("half_horizontal_bottom", "hhb", "   ", "###", "###"), 
	BORDER("border", "bo", "###", "# #", "###"), 
	CURLY_BORDER("curly_border", "cbo", new ItemStack(Blocks.VINE)), 
	CREEPER("creeper", "cre", new ItemStack(Items.SKULL, 1, 4)), 
	GRADIENT("gradient", "gra", "# #", " # ", " # "), 
	GRADIENT_UP("gradient_up", "gru", " # ", " # ", "# #"), 
	BRICKS("bricks", "bri", new ItemStack(Blocks.BRICK)), 
	SKULL("skull", "sku", new ItemStack(Items.SKULL, 1, 1)), 
	FLOWER("flower", "flo", new ItemStack(Blocks.RED_ROSE, 1, 8)), 
	MOJANG("mojang", "moj", new ItemStack(Items.GOLDEN_APPLE, 1, 1));

	private String patternName;
	private String[] craftingGrid;
	private ItemStack item;

	private EnumBannerPatterns(String name, String patternName) {
		this.craftingGrid = new String[3];
		this.patternName = patternName;
	}

	private EnumBannerPatterns(String name, String patternName, ItemStack var5) {
		this(name, patternName);
		this.item = var5;
	}

	private EnumBannerPatterns(String name, String patternName, String row1, String row2, String row3) {
		this(name, patternName);
		this.craftingGrid[0] = row1;
		this.craftingGrid[1] = row2;
		this.craftingGrid[2] = row3;
	}

	public String getPatternName() {
		return this.patternName;
	}

	public String[] getCraftingGrid() {
		return this.craftingGrid;
	}

	public boolean canBeCrafted() {
		return this.item != null || this.craftingGrid[0] != null;
	}

	public boolean needsItem() {
		return this.item != null;
	}

	public ItemStack getItem() {
		return this.item;
	}

	public static int getBaseColor(ItemStack itemStack) {
		int color = itemStack.getData() & 0xF;
		if (itemStack.hasTag() && itemStack.getTag().hasKey("BlockEntityTag")) {
			NBTTagCompound compound = itemStack.getTag().getCompound("BlockEntityTag");
			if (compound.hasKeyOfType("Base", 99)) {
				color = compound.getInt("Base");
			}
		}
		return color;
	}

	public static int getPatternsCount(ItemStack itemStack) {
		if (itemStack.getTag() == null) {
			return 0;
		}
		NBTTagCompound tag = itemStack.getTag().getCompound("BlockEntityTag");
		return tag != null && tag.hasKey("Patterns") ? tag.getList("Patterns", 10).size() : 0;
	}

	private static String DISPLAY_TAG_NAME = "display";
	private static String LORE_TAG_NAME = "Lore";
	private static String PATTERN_TAG_NAME = "Pattern";
	private static String COLOR_TAG_NAME = "Color";

	public static NBTTagList fromLoreToNBT(ItemStack itemstack) {
		if (!itemstack.hasTag()) {
			return null;
		}
		if (itemstack.getTag().hasKey(DISPLAY_TAG_NAME)) {
			NBTTagCompound display = itemstack.getTag().getCompound(DISPLAY_TAG_NAME);
			if (display.hasKeyOfType(LORE_TAG_NAME, 9)) {
				NBTTagList patterns = new NBTTagList();
				NBTTagList lore = display.getList(LORE_TAG_NAME, 8);
				for (int i = 0; i < lore.size(); i++) {
					String string = lore.getString(i);
					String[] split = string.split("[|]");
					if (split.length == 3 && split[0].equals("Carbon")) {
						NBTTagCompound pattern = new NBTTagCompound();
						pattern.setString(PATTERN_TAG_NAME, split[1]);
						pattern.setInt(COLOR_TAG_NAME, Integer.parseInt(split[2]));
						patterns.add(pattern);
					}
				}
				return patterns;
			}
		}
		return null;
	}

}
