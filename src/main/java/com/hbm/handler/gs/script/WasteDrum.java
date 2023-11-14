package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.inventory.FluidCombustionRecipes;
import com.hbm.inventory.WasteDrumRecipes;
import com.hbm.util.Tuple;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static com.hbm.inventory.StorageDrumRecipes.outputs;
import static com.hbm.inventory.WasteDrumRecipes.recipes;

public class WasteDrum extends VirtualizedRegistry<Tuple.Pair<Item, ItemStack>> {

    @Override
    public void onReload() {
        this.removeScripted().forEach(WasteDrumRecipes::removeRecipe);
        this.restoreFromBackup().forEach(recipe-> WasteDrumRecipes.addRecipe(recipe.getKey(), recipe.getValue()));
    }

    public void removeAll(){
        recipes.clear();
        outputs.clear();
    }

    public void removeRecipe(Item input, ItemStack output){
        WasteDrumRecipes.removeRecipe(input, output);
    }

    public void addRecipe(IIngredient ingredient, ItemStack output){
        for(ItemStack stack:ingredient.getMatchingStacks()) {
            WasteDrumRecipes.addRecipe(stack, output);
        }
    }

}
