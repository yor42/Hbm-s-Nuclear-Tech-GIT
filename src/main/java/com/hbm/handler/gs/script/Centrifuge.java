package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.inventory.CentrifugeRecipes;
import com.hbm.inventory.RecipesCommon;
import com.hbm.util.Tuple;
import net.minecraft.item.ItemStack;

public class Centrifuge extends VirtualizedRegistry<Tuple.Pair<ItemStack, ItemStack[]>> {
    @Override
    public void onReload() {
        this.removeScripted().forEach(this::remove);
        this.restoreFromBackup().forEach(this::addRecipe);
    }

    public void removeAll(){
        for(Object key:CentrifugeRecipes.recipes.keySet()){
            ItemStack[] stackarray = CentrifugeRecipes.recipes.get(key);
            if(key instanceof ItemStack){
                Tuple.Pair<ItemStack, ItemStack[]> pair = new Tuple.Pair<>((ItemStack) key, stackarray);
                addBackup(pair);
                remove(pair);
            }
        }
    }

    public void remove(Tuple.Pair<ItemStack, ItemStack[]> pair){
        CentrifugeRecipes.removeRecipe(pair.getKey());
        this.addBackup(pair);
    }

    public void remove(ItemStack ingredient){
        ItemStack[] out = CentrifugeRecipes.getOutput(ingredient);
        this.remove(new Tuple.Pair<>(ingredient, out));
    }

    public void addRecipe(Tuple.Pair<ItemStack, ItemStack[]> pair){
        CentrifugeRecipes.addRecipe(pair.getKey(), pair.getValue());
        this.addScripted(pair);
    }

    public void addRecipe(IIngredient input, ItemStack... stacks){
        for(ItemStack stack: input.getMatchingStacks()){
            Tuple.Pair<ItemStack, ItemStack[]> pair = new Tuple.Pair<>(stack, stacks);
            addScripted(pair);
        }
    }

}
