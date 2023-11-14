package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.inventory.CentrifugeRecipes;
import com.hbm.inventory.RecipesCommon;
import com.hbm.util.Tuple;
import net.minecraft.item.ItemStack;

import static com.hbm.inventory.CentrifugeRecipes.getRecipes;

public class Centrifuge extends VirtualizedRegistry<Tuple.Pair<RecipesCommon.ComparableStack, ItemStack[]>> {
    @Override
    public void onReload() {
        this.removeScripted().forEach((recipe)->{
            CentrifugeRecipes.removeRecipe(recipe.getKey());
        });
        this.restoreFromBackup().forEach((recipe)->{
            CentrifugeRecipes.addRecipe(recipe.getKey(), recipe.getValue());
        });
    }

    public void removeAll(){
        getRecipes().clear();
    }

    public void remove(ItemStack stack){
        CentrifugeRecipes.removeRecipe(stack);
    }

    public void remove(IIngredient ingredient){
        for(ItemStack stack:ingredient.getMatchingStacks()){
            CentrifugeRecipes.removeRecipe(stack);
        }
    }

    public void addRecipe(IIngredient input, ItemStack... stacks){
        for(ItemStack stack: input.getMatchingStacks()){
            CentrifugeRecipes.addRecipe(stack, stacks);
        }
    }

}
