package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.inventory.DFCRecipes;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.SILEXRecipes;
import com.hbm.util.Tuple;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import static com.hbm.inventory.DFCRecipes.dfcRecipes;

public class DFC extends VirtualizedRegistry<Tuple.Triplet<RecipesCommon.ComparableStack, ItemStack, Long>> {
    @Override
    public void onReload() {
        this.removeScripted().forEach(recipe -> {
            DFCRecipes.removeRecipe(recipe.getX());
        });
        this.restoreFromBackup().forEach(recipe -> DFCRecipes.setRecipe(recipe.getZ(), recipe.getX(), recipe.getY()));
    }

    public void removeRecipe(ItemStack stack) {
        DFCRecipes.removeRecipe(stack);
    }

    public void removeRecipe(IIngredient ingredient) {
        for (ItemStack stack : ingredient.getMatchingStacks()) {
            DFCRecipes.removeRecipe(stack);
        }
    }

    public void removeAll() {
        dfcRecipes.clear();
    }

    public void addRecipe(IIngredient ingredient, ItemStack output, long flux) {
        for (ItemStack stack : ingredient.getMatchingStacks()) {
            DFCRecipes.setRecipe(flux, stack, output);
        }
    }

    public void addRecipe(ItemStack stack, ItemStack output, long flux) {
        DFCRecipes.setRecipe(flux, stack, output);
    }

}
