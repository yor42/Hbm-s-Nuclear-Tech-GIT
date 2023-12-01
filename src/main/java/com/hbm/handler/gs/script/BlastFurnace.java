package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.handler.gs.NTM;
import com.hbm.handler.gs.util.IngredientUtils;
import com.hbm.inventory.DiFurnaceRecipes;
import com.hbm.inventory.RecipesCommon;
import com.hbm.util.Tuple;
import net.minecraft.item.ItemStack;

import static com.hbm.inventory.DiFurnaceRecipes.diRecipes;

public class BlastFurnace extends VirtualizedRegistry<Tuple.Pair<Tuple.Pair<RecipesCommon.AStack, RecipesCommon.AStack>, ItemStack>> {
    @Override
    public void onReload() {
        removeScripted().forEach(recipe->removeRecipe(recipe.getKey()));
        restoreFromBackup().forEach(this::addRecipe);
    }

    public void addRecipe(Tuple.Pair<Tuple.Pair<RecipesCommon.AStack, RecipesCommon.AStack>, ItemStack> recipe){
        DiFurnaceRecipes.addRecipe(recipe.getKey().getKey(), recipe.getKey().getValue(), recipe.getValue());
        addScripted(recipe);
    }

    public void removeRecipe(Tuple.Pair<RecipesCommon.AStack, RecipesCommon.AStack> inputs){
        ItemStack stack = diRecipes.get(inputs);
        addBackup(new Tuple.Pair<>(inputs, stack));
        DiFurnaceRecipes.removeRecipe(inputs.getKey(), inputs.getValue());
    }

    public void removeAll(){
        for(Tuple.Pair<RecipesCommon.AStack, RecipesCommon.AStack> inputs:diRecipes.keySet()){
            ItemStack out = diRecipes.get(inputs);
            addBackup(new Tuple.Pair<>(inputs, out));
            diRecipes.remove(inputs);
        }
    }

    public static class RecipeBuilder extends AbstractRecipeBuilder<Tuple.Pair<Tuple.Pair<RecipesCommon.AStack, RecipesCommon.AStack>, ItemStack>>{

        @Override
        public String getErrorMsg() {
            return "Error adding NTM BlastFurnace Recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            this.validateItems(msg, 2, 2, 1, 1);
        }

        @Override
        public Tuple.Pair<Tuple.Pair<RecipesCommon.AStack, RecipesCommon.AStack>, ItemStack> register() {
            if (!this.validate()) {
                return null;
            }
            Tuple.Pair<Tuple.Pair<RecipesCommon.AStack, RecipesCommon.AStack>, ItemStack> recipe = new Tuple.Pair<>(new Tuple.Pair<>(IngredientUtils.convertIngredient2Astack(this.input.get(0)), IngredientUtils.convertIngredient2Astack(this.input.get(1))), this.output.get(0));
            NTM.BLASTFURNACE.addRecipe(recipe);
            return recipe;
        }
    }
}
