package com.mcmoddev.mineralogy.init;

import com.mcmoddev.mineralogy.Constants;
import com.mcmoddev.mineralogy.MineralogyConfig;
import com.mcmoddev.mineralogy.blocks.Rock;
import com.mcmoddev.mineralogy.blocks.RockSlab;
import com.mcmoddev.mineralogy.blocks.RockStairs;
import com.mcmoddev.mineralogy.data.MaterialType;
import com.mcmoddev.mineralogy.data.MaterialTypes;
import com.mcmoddev.mineralogy.ioc.MinIoC;
import com.mcmoddev.mineralogy.util.BlockItemPair;
import com.mcmoddev.mineralogy.util.RecipeHelper;
import com.mcmoddev.mineralogy.util.RegistrationHelper;

import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Blocks {

	private static boolean initDone = false;
	private static CreativeTabs mineralogyTab;
	
	protected Blocks() {
		throw new IllegalAccessError("Not a instantiable class");
	}

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}
		
		mineralogyTab = MinIoC.getInstance().resolve(CreativeTabs.class);
		MaterialTypes.toArray().forEach(material -> addStoneType(material));
		
//		com.mcmoddev.basemetals.util.Config.init();
//		com.mcmoddev.lib.init.Blocks.init();
//		Materials.init();
//		ItemGroups.init();
//
//		registerVanilla();

//		String[] simpleFullBlocks = new String[] { MaterialTypes.ADAMANTINE, MaterialTypes.ANTIMONY, MaterialTypes.BISMUTH,
//				MaterialTypes.COLDIRON, MaterialTypes.COPPER, MaterialTypes.LEAD, MaterialTypes.NICKEL, MaterialTypes.PLATINUM,
//				MaterialTypes.SILVER, MaterialTypes.TIN, MaterialTypes.ZINC };
//		
//		Arrays.asList(simpleFullBlocks).stream()
//		.filter( name -> Options.isMaterialEnabled(name))
//		.forEach( name -> createBlocksFull(name, myTabs) );
//		
//		Arrays.asList(alloyFullBlocks).stream()
//		.filter( name -> Options.isMaterialEnabled(name))
//		.forEach( name -> createBlocksFullOreless(name, myTabs) );
//
//		createStarSteel();
//		createMercury();
//		
//
//		humanDetector = addBlock(new BlockHumanDetector(), "human_detector", myTabs.blocksTab);
//		initDone = true;
	}

	protected static void addStoneType(MaterialType materialType) {
		String name = materialType.materialName.toLowerCase();

		final BlockItemPair rockPair;
		final BlockItemPair rockStairPair;
		final BlockItemPair rockSlabPair;
		final BlockItemPair brickPair;
		final BlockItemPair brickStairPair;
		final BlockItemPair brickSlabPair;
		final BlockItemPair smoothPair;
		final BlockItemPair smoothStairPair;
		final BlockItemPair smoothSlabPair;
		final BlockItemPair smoothBrickPair;
		final BlockItemPair smoothBrickStairPair;
		final BlockItemPair smoothBrickSlabPair;

		rockPair = RegistrationHelper.registerBlock(new Rock(true, (float) materialType.hardness, (float) materialType.blastResistance, materialType.toolHardnessLevel, SoundType.STONE, mineralogyTab), name, name);

		// TODO: see why this is necessary, the ore dictionary should make this unnecessary?
		 
		MineralogyRegistry.MineralogyRecipeRegistry.put(name + "_STONE_AXE", RecipeHelper.addShapedOreRecipe(name + "_STONE_AXE", new ItemStack(Items.STONE_AXE), "xx", "xy", " y", 'x', rockPair.PairedItem, 'y', Items.STICK));
		MineralogyRegistry.MineralogyRecipeRegistry.put(name + "_STONE_HOE", RecipeHelper.addShapedOreRecipe(name + "_STONE_HOE", new ItemStack(Items.STONE_HOE), "xx", " y", " y", 'x', rockPair.PairedItem, 'y', Items.STICK));
		MineralogyRegistry.MineralogyRecipeRegistry.put(name + "_STONE_PICKAXE", RecipeHelper.addShapedOreRecipe(name + "_STONE_PICKAXE", new ItemStack(Items.STONE_PICKAXE), "xxx", " y ", " y ", 'x', rockPair.PairedItem, 'y', Items.STICK));
		MineralogyRegistry.MineralogyRecipeRegistry.put(name + "_STONE_SHOVEL", RecipeHelper.addShapedOreRecipe(name + "_STONE_SHOVEL", new ItemStack(Items.STONE_SHOVEL), "x", "y", "y", 'x', rockPair.PairedItem, 'y', Items.STICK));
		MineralogyRegistry.MineralogyRecipeRegistry.put(name + "_STONE_SWORD", RecipeHelper.addShapedOreRecipe(name + "_STONE_SWORD", new ItemStack(Items.STONE_SWORD), "x", "x", "y", 'x', rockPair.PairedItem, 'y', Items.STICK));
		MineralogyRegistry.MineralogyRecipeRegistry.put(name + "_STONE_FURNACE", RecipeHelper.addShapedOreRecipe(name + "_FURNACE", new ItemStack(net.minecraft.init.Blocks.FURNACE), "xxx", "x x", "xxx", 'x', rockPair.PairedItem));
		MineralogyRegistry.MineralogyRecipeRegistry.put(name + "_" + Constants.COBBLESTONE.toUpperCase(), RecipeHelper.addShapelessOreRecipe(name + "_" + Constants.COBBLESTONE.toUpperCase(), new ItemStack(net.minecraft.init.Blocks.COBBLESTONE, 4),
				Ingredient.fromStacks(new ItemStack(rockPair.PairedItem)),
				Ingredient.fromStacks(new ItemStack(rockPair.PairedItem)),
				Ingredient.fromStacks(new ItemStack(net.minecraft.init.Blocks.GRAVEL)),
				Ingredient.fromStacks(new ItemStack(net.minecraft.init.Blocks.GRAVEL))));

		MineralogyRegistry.BlocksToRegister.put(Constants.COBBLESTONE, rockPair.PairedBlock); // register so it can be used in cobblestone recipes

		switch (materialType.rockType) {
			case IGNEOUS:
				MineralogyRegistry.igneousStones.add(rockPair.PairedBlock);
				break;
			case METAMORPHIC:
				MineralogyRegistry.metamorphicStones.add(rockPair.PairedBlock);
				break;
			case SEDIMENTARY:
				MineralogyRegistry.sedimentaryStones.add(rockPair.PairedBlock);
				break;
			case ANY:
				MineralogyRegistry.sedimentaryStones.add(rockPair.PairedBlock);
				MineralogyRegistry.metamorphicStones.add(rockPair.PairedBlock);
				MineralogyRegistry.igneousStones.add(rockPair.PairedBlock);
				break;
		}

		// TODO: See why this doesn't work (recipes still wont work with 'stone')
		// OreDictionary.registerOre("stone", rock);
		GameRegistry.addSmelting(rockPair.PairedBlock, new ItemStack(net.minecraft.init.Blocks.STONE), 0.1F);

		if (MineralogyConfig.generateRockStairs()) {
			rockStairPair = RegistrationHelper.registerBlock(new RockStairs(rockPair.PairedBlock, (float) materialType.hardness,
					(float) materialType.blastResistance, materialType.toolHardnessLevel, SoundType.STONE, mineralogyTab), name + "_" + Constants.STAIRS,
					Constants.STAIRS + materialType.materialName);
			RecipeHelper.addShapedOreRecipe(name + "_" + Constants.STAIRS, new ItemStack(rockStairPair.PairedItem, 4), "x  ", "xx ", "xxx",
					'x', rockPair.PairedItem);
		}

		if (MineralogyConfig.generateRockSlab()) {
			rockSlabPair = RegistrationHelper.registerBlock(
					new RockSlab((float) materialType.hardness, (float) materialType.blastResistance, materialType.toolHardnessLevel, SoundType.STONE, mineralogyTab),
					name + "_" + Constants.SLAB, Constants.SLAB + materialType.materialName);
			RecipeHelper.addShapedOreRecipe(name + "_" + Constants.SLAB, new ItemStack(rockSlabPair.PairedItem, 6), "xxx", 'x',
					rockPair.PairedItem);
		}

		if (MineralogyConfig.generateBrick()) {
			brickPair = RegistrationHelper.registerBlock(
					new Rock(false, (float) materialType.hardness, (float) materialType.blastResistance, materialType.toolHardnessLevel, SoundType.STONE, mineralogyTab),
					name + "_" + Constants.BRICK, Constants.BRICK + materialType.materialName);
			RecipeHelper.addShapedOreRecipe(name + "_" + Constants.BRICK, new ItemStack(brickPair.PairedItem, 4), "xx", "xx", 'x',
					rockPair.PairedItem);

			if (MineralogyConfig.generateBrickStairs()) {
				brickStairPair = RegistrationHelper.registerBlock(
						new RockStairs(rockPair.PairedBlock, (float) materialType.hardness, (float) materialType.blastResistance,
								materialType.toolHardnessLevel, SoundType.STONE, mineralogyTab),
						name + "_" + Constants.BRICK + "_" + Constants.STAIRS, Constants.STAIRS + materialType.materialName + "Brick");
				RecipeHelper.addShapedOreRecipe(name + "_" + Constants.BRICK + "_" + Constants.STAIRS, new ItemStack(brickStairPair.PairedItem, 4),
						"x  ", "xx ", "xxx", 'x', brickPair.PairedItem);
			}

			if (MineralogyConfig.generateBrickSlab()) {
				brickSlabPair = RegistrationHelper.registerBlock(
						new RockSlab((float) materialType.hardness, (float) materialType.blastResistance, materialType.toolHardnessLevel, SoundType.STONE, mineralogyTab),
						name + "_" + Constants.BRICK + "_" + Constants.SLAB, Constants.SLAB + materialType.materialName + "Brick");
				RecipeHelper.addShapedOreRecipe(name + "_" + Constants.BRICK + "_" + Constants.SLAB, new ItemStack(brickSlabPair.PairedItem, 6), "xxx",
						'x', brickPair.PairedItem);
			}
		}

		if (MineralogyConfig.generateSmooth()) {
			smoothPair = RegistrationHelper.registerBlock(
					new Rock(false, (float) materialType.hardness, (float) materialType.blastResistance, materialType.toolHardnessLevel, SoundType.STONE, mineralogyTab),
					name + "_" + Constants.SMOOTH, Constants.SMOOTH + materialType.materialName);
			RecipeHelper.addShapelessOreRecipe(name + "_" + Constants.SMOOTH, new ItemStack(smoothPair.PairedItem, 1),
					Ingredient.fromStacks(new ItemStack(rockPair.PairedItem, 1)),
					Ingredient.fromStacks(new ItemStack(net.minecraft.init.Blocks.SAND, 1)));

			if (MineralogyConfig.generateSmoothStairs()) {
				smoothStairPair = RegistrationHelper.registerBlock(
						new RockStairs(rockPair.PairedBlock, (float) materialType.hardness, (float) materialType.blastResistance,
								materialType.toolHardnessLevel, SoundType.STONE, mineralogyTab),
						name + "_" + Constants.SMOOTH + "_" + Constants.STAIRS, Constants.STAIRS + materialType.materialName + "Smooth");
				RecipeHelper.addShapedOreRecipe(name + "_" + Constants.SMOOTH + "_" + Constants.STAIRS, new ItemStack(smoothStairPair.PairedItem, 4),
						"x  ", "xx ", "xxx", 'x', smoothPair.PairedItem);
			}

			if (MineralogyConfig.generateSmoothSlab()) {
				smoothSlabPair = RegistrationHelper.registerBlock(
						new RockSlab((float) materialType.hardness, (float) materialType.blastResistance, materialType.toolHardnessLevel, SoundType.STONE, mineralogyTab),
						name + "_" + Constants.SMOOTH + "_" + Constants.SLAB, Constants.SLAB + materialType.materialName + "Smooth");
				RecipeHelper.addShapedOreRecipe(name + "_" + Constants.SMOOTH + "_" + Constants.SLAB, new ItemStack(smoothSlabPair.PairedItem, 6), "xxx",
						'x', smoothPair.PairedItem);
			}

			if (MineralogyConfig.generateSmoothBrick()) {
				smoothBrickPair = RegistrationHelper.registerBlock(
						new Rock(false, (float) materialType.hardness, (float) materialType.blastResistance, materialType.toolHardnessLevel, SoundType.STONE, mineralogyTab),
						name + "_" + Constants.SMOOTH + "_" + Constants.BRICK, Constants.BRICK + materialType.materialName + "Smooth");
				RecipeHelper.addShapedOreRecipe(name + "_" + Constants.SMOOTH + "_" + Constants.BRICK, new ItemStack(smoothBrickPair.PairedItem, 4),
						"xx", "xx", 'x', smoothPair.PairedItem);

				if (MineralogyConfig.generateSmoothBrickStairs()) {
					smoothBrickStairPair = RegistrationHelper.registerBlock(
							new RockStairs(rockPair.PairedBlock, (float) materialType.hardness, (float) materialType.blastResistance,
									materialType.toolHardnessLevel, SoundType.STONE, mineralogyTab),
							name + "_" + Constants.SMOOTH + "_" + Constants.BRICK + "_" + Constants.STAIRS, Constants.STAIRS + materialType.materialName + "SmoothBrick");
					RecipeHelper.addShapedOreRecipe(name + "_" + Constants.SMOOTH + "_" + Constants.BRICK + "_" + Constants.STAIRS,
							new ItemStack(smoothBrickStairPair.PairedItem, 4), "x  ", "xx ", "xxx", 'x',
							smoothBrickPair.PairedItem);
				}

				if (MineralogyConfig.generateSmoothBrickSlab()) {
					smoothBrickSlabPair = RegistrationHelper.registerBlock(
							new RockSlab((float) materialType.hardness, (float) materialType.blastResistance, materialType.toolHardnessLevel, SoundType.STONE, mineralogyTab),
							name + "_" + Constants.SMOOTH + "_" + Constants.BRICK + "_" + Constants.SLAB, Constants.SLAB + materialType.materialName + "SmoothBrick");
					RecipeHelper.addShapedOreRecipe(name + "_" + Constants.SMOOTH + "_" + Constants.BRICK + "_" + Constants.SLAB,
							new ItemStack(smoothBrickSlabPair.PairedItem, 6), "xxx", 'x', smoothBrickPair.PairedItem);
				}
			}
		}
	}
}
