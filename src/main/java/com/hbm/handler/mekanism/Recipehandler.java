package com.hbm.handler.mekanism;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager;
import com.hbm.items.ModItems;
import mekanism.common.recipe.RecipeHandler;
import mekanism.common.util.StackUtils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

import static com.hbm.inventory.OreDictManager.DICTLIST;

public class Recipehandler {

    public static void registerRecipes(){
        RecipeHandler.addEnrichmentChamberRecipe(new ItemStack(ModBlocks.cluster_copper), new ItemStack(ModItems.crystal_copper,2));
        RecipeHandler.addEnrichmentChamberRecipe(new ItemStack(ModBlocks.cluster_iron), new ItemStack(ModItems.crystal_iron,2));
        RecipeHandler.addEnrichmentChamberRecipe(new ItemStack(ModBlocks.cluster_depth_iron), new ItemStack(ModItems.crystal_iron,2));
        RecipeHandler.addEnrichmentChamberRecipe(new ItemStack(ModBlocks.cluster_aluminium), new ItemStack(ModItems.crystal_aluminium,2));
        RecipeHandler.addEnrichmentChamberRecipe(new ItemStack(ModBlocks.cluster_titanium), new ItemStack(ModItems.crystal_titanium,2));
        RecipeHandler.addEnrichmentChamberRecipe(new ItemStack(ModBlocks.cluster_depth_titanium), new ItemStack(ModItems.crystal_titanium,2));
        RecipeHandler.addEnrichmentChamberRecipe(new ItemStack(ModBlocks.cluster_depth_tungsten), new ItemStack(ModItems.crystal_tungsten,2));
        List<ItemStack> ores = OreDictionary.getOres("oreAsbestos", false);
        if(!ores.isEmpty()) {
            for (ItemStack stack : ores) {
                RecipeHandler.addEnrichmentChamberRecipe(stack, new ItemStack(ModItems.powder_asbestos, 2));
            }
        }
        for(ItemStack stack:OreDictionary.getOres("oreLignite", false)){
            RecipeHandler.addEnrichmentChamberRecipe(stack, new ItemStack(ModItems.lignite,2));
        }
        for(ItemStack stack:OreDictionary.getOres("oreCinnebar", false)){
            RecipeHandler.addEnrichmentChamberRecipe(stack, new ItemStack(ModItems.cinnebar,2));
        }
        for(ItemStack stack:OreDictionary.getOres("oreBorax", false)){
            RecipeHandler.addEnrichmentChamberRecipe(stack, new ItemStack(ModItems.powder_borax,2));
        }
        for(ItemStack stack:OreDictionary.getOres("oreBorax", false)){
            RecipeHandler.addEnrichmentChamberRecipe(stack, new ItemStack(ModItems.powder_borax,2));
        }
        for(ItemStack stack:OreDictionary.getOres("oreNiter", false)){
            RecipeHandler.addEnrichmentChamberRecipe(stack, new ItemStack(ModItems.niter,2));
        }
        for(ItemStack stack:OreDictionary.getOres("oreFluorite", false)){
            RecipeHandler.addEnrichmentChamberRecipe(stack, new ItemStack(ModItems.fluorite,2));
        }
        for(ItemStack stack:OreDictionary.getOres("oreSulfur", false)){
            RecipeHandler.addEnrichmentChamberRecipe(stack, new ItemStack(ModItems.sulfur,2));
        }
        for(ItemStack stack:OreDictionary.getOres("oreNitanium", false)){
            RecipeHandler.addEnrichmentChamberRecipe(stack, new ItemStack(ModItems.powder_nitan_mix,2));
        }
        for(ItemStack stack:OreDictionary.getOres("oreRareEarth", false)){
            RecipeHandler.addEnrichmentChamberRecipe(stack, new ItemStack(ModItems.powder_desh_mix,2));
        }
        for(ItemStack stack:OreDictionary.getOres("oreRedPhosphorus", false)){
            RecipeHandler.addEnrichmentChamberRecipe(stack, new ItemStack(ModItems.powder_fire,2));
        }
    }

}
