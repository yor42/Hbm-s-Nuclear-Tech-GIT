package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.handler.gs.NTM;
import com.hbm.handler.gs.util.IngredientUtils;
import com.hbm.inventory.BreederRecipes;
import com.hbm.inventory.RecipesCommon;
import com.hbm.util.Tuple;
import net.minecraft.item.ItemStack;

public class BreedingReactor extends VirtualizedRegistry<Tuple.Pair<RecipesCommon.ComparableStack, BreederRecipes.BreederRecipe>> {
    @Override
    public void onReload() {
        this.removeScripted().forEach((recipe)->{
            removeRecipe(recipe.getKey());
        });
        this.restoreFromBackup().forEach((recipe)->{
            BreederRecipes.addRecipe(recipe.getKey(), recipe.getValue().output, recipe.getValue().heat);
        });
    }

    public void removeRecipe(RecipesCommon.ComparableStack stack){
        BreederRecipes.removeRecipe(stack);
    }

    public void removeRecipe(IIngredient ingredient){
        for(ItemStack stack:ingredient.getMatchingStacks()){
            removeRecipe(new RecipesCommon.ComparableStack(stack));
        }
    }

    public void addRecipe(RecipesCommon.ComparableStack input , BreederRecipes.BreederRecipe recipe){
        BreederRecipes.addRecipe(input, recipe.output, recipe.heat);
    }

    public void addRecipe(ItemStack output, RecipesCommon.ComparableStack ingredient, int heatlevel){
        BreederRecipes.addRecipe(ingredient, output, heatlevel);
    }

    public void addRecipe(ItemStack output, ItemStack ingredient, int heatlevel){
        addRecipe(output, new RecipesCommon.ComparableStack(ingredient), heatlevel);
    }

    public void addRecipe(ItemStack output, IIngredient ingredient, int heatlevel){
        for(ItemStack stack:ingredient.getMatchingStacks()){
            addRecipe(output, stack, heatlevel);
        }
    }

    public RecipeBuilder recipeBuilder(){
        return new RecipeBuilder();
    }

    public static class RecipeBuilder extends AbstractRecipeBuilder<Tuple.Pair<RecipesCommon.ComparableStack, BreederRecipes.BreederRecipe>>{

        private int heatLvl = 1;

        public void heatLevel(int heatLvl) {
            this.heatLvl = heatLvl;
        }

        @Override
        public String getErrorMsg() {
            return "Error adding Breeder Reactor Recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            this.validateItems(msg, 1, 1, 1, 1);
            msg.add(this.heatLvl < 1 || this.heatLvl > 4, "Breeding heat needs to be between 1-4, but it was {}.", this.heatLvl);
        }

        @Override
        public Tuple.Pair<RecipesCommon.ComparableStack, BreederRecipes.BreederRecipe> register() {
            Tuple.Pair<RecipesCommon.ComparableStack, BreederRecipes.BreederRecipe> recipe = new Tuple.Pair<>(new RecipesCommon.ComparableStack(this.input.get(0).getMatchingStacks()[0]), new BreederRecipes.BreederRecipe(this.output.get(0),this.heatLvl));
            NTM.NTM.get().BREEDINGREACTOR.addRecipe(recipe.getKey(), recipe.getValue());
            return recipe;
        }
    }

}
