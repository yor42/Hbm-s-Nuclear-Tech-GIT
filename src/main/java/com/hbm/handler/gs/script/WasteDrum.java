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

public class WasteDrum extends VirtualizedRegistry<Tuple.Pair<ItemStack, ItemStack>> {

    @Override
    public void onReload() {
        this.removeScripted().forEach(this::removeRecipe);
        this.restoreFromBackup().forEach(this::addRecipe);
    }

    public void removeAll(){
        recipes.clear();
        outputs.clear();
    }

    public void removeRecipe(ItemStack input, ItemStack output){
        WasteDrumRecipes.removeRecipe(input, output);
    }

    public void removeRecipe(Tuple.Pair<ItemStack, ItemStack> pair){
        WasteDrumRecipes.removeRecipe(pair.getKey(), pair.getValue());
        this.addBackup(pair);
    }

    public void addRecipe(Tuple.Pair<ItemStack, ItemStack> pair){
        WasteDrumRecipes.addRecipe(pair.getKey(), pair.getValue());
        this.addScripted(pair);
    }

    public void addRecipe(IIngredient ingredient, ItemStack output){
        for(ItemStack stack:ingredient.getMatchingStacks()) {
            WasteDrumRecipes.addRecipe(stack, output);
            this.addScripted(new Tuple.Pair<>(stack,output));
        }
    }

}
