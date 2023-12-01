package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.handler.gs.NTM;
import com.hbm.handler.gs.util.IngredientUtils;
import com.hbm.inventory.RecipesCommon;
import com.hbm.util.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.hbm.inventory.AssemblerRecipes.*;

public class Assembler extends VirtualizedRegistry<Tuple.Triplet<RecipesCommon.ComparableStack, RecipesCommon.AStack[], Integer>>  {
    @Override
    public void onReload() {
        removeScripted().forEach(recipe->{
            this.removeRecipebyOutput(recipe.getX());
        });
        restoreFromBackup().forEach(this::addRecipe);
        Collections.sort(recipeList);
    }

    public void addRecipe(Tuple.Triplet<RecipesCommon.ComparableStack, RecipesCommon.AStack[], Integer> recipe){
        recipes.put(recipe.getX(), recipe.getY());
        time.put(recipe.getX(), recipe.getZ());
        recipeList.add(recipe.getX());
        this.addScripted(recipe);
    }

    public void removeRecipebyOutput(RecipesCommon.ComparableStack output){
        this.addBackup(new Tuple.Triplet<>(output,recipes.get(output),time.get(output)));
        recipes.remove(output);
        time.remove(output);
        recipeList.remove(output);
    }

    public void removeAll(){
        for(RecipesCommon.ComparableStack stack:recipeList){
            this.addBackup(new Tuple.Triplet<>(stack,recipes.get(stack),time.get(stack)));
            recipes.remove(stack);
            time.remove(stack);
            recipeList.remove(stack);
        }
    }

    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    public static class RecipeBuilder extends AbstractRecipeBuilder<Tuple.Triplet<RecipesCommon.ComparableStack, RecipesCommon.AStack[], Integer>>{

        int time = 200;

        public RecipeBuilder time(int time){
            this.time = time;
            return this;
        }

        @Override
        public String getErrorMsg() {
            return "Error adding NTM Assembler Recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            this.validateItems(msg, 1, Integer.MAX_VALUE, 1, 1);
        }

        @Override
        public Tuple.Triplet<RecipesCommon.ComparableStack, RecipesCommon.AStack[], Integer> register() {
            if (!this.validate()) {
                return null;
            }
            List<RecipesCommon.AStack> list = new ArrayList<>();
            for(IIngredient ingredient:this.input){
                list.add(IngredientUtils.convertIngredient2Astack(ingredient));
            }
            Tuple.Triplet<RecipesCommon.ComparableStack, RecipesCommon.AStack[], Integer> recipe = new Tuple.Triplet<>(new RecipesCommon.ComparableStack(this.output.get(0)),  list.toArray(new RecipesCommon.AStack[0]), this.time);
            NTM.ASSEMBLER.addRecipe(recipe);
            return recipe;
        }
    }

}
