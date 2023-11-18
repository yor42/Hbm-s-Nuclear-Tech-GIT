package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.handler.gs.util.IngredientUtils;
import com.hbm.inventory.DiFurnaceRecipes;
import com.hbm.inventory.RecipesCommon;
import com.hbm.util.Tuple;
import net.minecraft.item.ItemStack;

import static com.hbm.inventory.DiFurnaceRecipes.diFuels;


public class BlastFurnaceFuel extends VirtualizedRegistry<Tuple.Pair<RecipesCommon.AStack, Integer>> {
    @Override
    public void onReload() {
        removeScripted().forEach(recipe->removeFuel(recipe.getKey()));
        restoreFromBackup().forEach(recipe->addFuel(recipe.getKey(), recipe.getValue()));
    }

    public void removeFuel(RecipesCommon.AStack stack){
        int value = diFuels.get(stack);
        this.addBackup(new Tuple.Pair<>(stack, value));
        DiFurnaceRecipes.removeFuel(stack);
    }

    public void removeFuel(IIngredient stack){
        removeFuel(IngredientUtils.convertIngredient2Astack(stack));
    }

    public void removeAll(){
        for(RecipesCommon.AStack fuel:diFuels.keySet()){
            int value = diFuels.get(fuel);
            this.addBackup(new Tuple.Pair<>(fuel, value));
            diFuels.remove(fuel);
        }
    }

    public void addFuel(RecipesCommon.AStack stack, int power){
        DiFurnaceRecipes.addFuel(stack, power);
        this.addScripted(new Tuple.Pair<>(stack, power));
    }

    public void addFuel(IIngredient stack, int power){
        this.addFuel(IngredientUtils.convertIngredient2Astack(stack), power);
    }
}
