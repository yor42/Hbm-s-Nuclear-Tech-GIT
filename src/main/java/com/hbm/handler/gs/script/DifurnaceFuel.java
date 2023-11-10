package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.handler.gs.util.IngredientUtils;
import com.hbm.inventory.DiFurnaceRecipes;
import com.hbm.inventory.RecipesCommon;
import com.hbm.util.Tuple;
import net.minecraft.item.ItemStack;

import static com.hbm.inventory.DiFurnaceRecipes.diFuels;


public class DifurnaceFuel extends VirtualizedRegistry<Tuple.Pair<RecipesCommon.AStack, Integer>> {
    @Override
    public void onReload() {
        removeScripted().forEach(recipe->DiFurnaceRecipes.removeFuel(recipe.getKey()));
        restoreFromBackup().forEach(recipe->DiFurnaceRecipes.addFuel(recipe.getKey(), recipe.getValue()));
    }

    public void removeFuel(ItemStack stack){
        DiFurnaceRecipes.removeFuel(new RecipesCommon.ComparableStack(stack));
    }

    public void removeFuel(IIngredient stack){
        DiFurnaceRecipes.removeFuel(IngredientUtils.convertIngredient2Astack(stack));
    }

    public void removeAll(){
        diFuels.clear();
    }

    public void addFuel(IIngredient stack, int power){
        DiFurnaceRecipes.addFuel(IngredientUtils.convertIngredient2Astack(stack), power);
    }
}
