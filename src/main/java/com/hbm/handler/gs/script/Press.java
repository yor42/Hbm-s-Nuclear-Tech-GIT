package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.handler.gs.NTM;
import com.hbm.handler.gs.util.IngredientUtils;
import com.hbm.inventory.PressRecipes;
import com.hbm.inventory.RecipesCommon;
import com.hbm.util.Tuple;
import net.minecraft.item.ItemStack;

import java.util.Collections;

import static com.hbm.inventory.AssemblerRecipes.recipeList;
import static com.hbm.inventory.PressRecipes.pressRecipes;

public class Press extends VirtualizedRegistry<Tuple.Pair<Tuple.Pair<PressRecipes.PressType, RecipesCommon.AStack>, ItemStack>> {
    @Override
    public void onReload() {
        removeScripted().forEach(this::removeRecipe);
        restoreFromBackup().forEach(this::addRecipe);
        Collections.sort(recipeList);
    }

    private void addRecipe(Tuple.Pair<Tuple.Pair<PressRecipes.PressType, RecipesCommon.AStack>, ItemStack> pairItemStackPair) {
        pressRecipes.put(pairItemStackPair.getKey(), pairItemStackPair.getValue());
        this.addScripted(pairItemStackPair);
    }

    private void removeRecipe(Tuple.Pair<Tuple.Pair<PressRecipes.PressType, RecipesCommon.AStack>, ItemStack> pair){
        pressRecipes.remove(pair.getKey());
        this.addBackup(pair);
    }

    private void removeRecipebyOutput(ItemStack out){
        for(Tuple.Pair<PressRecipes.PressType, RecipesCommon.AStack> key: pressRecipes.keySet()){
            ItemStack recipeout = pressRecipes.get(key);
            if(recipeout == out){
                pressRecipes.remove(key);
                this.addBackup(new Tuple.Pair<>(key, recipeout));
            }
        }
    }

    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    public static class RecipeBuilder extends AbstractRecipeBuilder<Tuple.Pair<Tuple.Pair<PressRecipes.PressType, RecipesCommon.AStack>, ItemStack>> {

        private PressRecipes.PressType type = PressRecipes.PressType.NONE;


        public RecipeBuilder None(){
            this.type = PressRecipes.PressType.NONE;
            return this;
        }

        public RecipeBuilder Flat(){
            this.type = PressRecipes.PressType.FLAT;
            return this;
        }

        public RecipeBuilder Plate(){
            this.type = PressRecipes.PressType.PLATE;
            return this;
        }

        public RecipeBuilder Wire(){
            this.type = PressRecipes.PressType.WIRE;
            return this;
        }

        public RecipeBuilder Circuit(){
            this.type = PressRecipes.PressType.CIRCUIT;
            return this;
        }

        public RecipeBuilder ThreeFiveSeven(){
            this.type = PressRecipes.PressType.THREEFIFESEVEN;
            return this;
        }

        public RecipeBuilder Fourfour(){
            this.type = PressRecipes.PressType.FOURFOUR;
            return this;
        }

        public RecipeBuilder Nine(){
            this.type = PressRecipes.PressType.NINE;
            return this;
        }

        public RecipeBuilder Fivezero(){
            this.type = PressRecipes.PressType.FIVEZERO;
            return this;
        }

        @Override
        public String getErrorMsg() {
            return "Error adding recipes for NTM Press";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            this.validateItems(msg, 1, 1, 1, 1);
        }

        @Override
        public  Tuple.Pair<Tuple.Pair<PressRecipes.PressType, RecipesCommon.AStack>, ItemStack> register() {
            if (!this.validate()) {
                return null;
            }
            Tuple.Pair<Tuple.Pair<PressRecipes.PressType, RecipesCommon.AStack>, ItemStack> recipe = new Tuple.Pair<>(new Tuple.Pair<>(this.type, IngredientUtils.convertIngredient2Astack(this.input.get(0))), this.output.get(0));
            NTM.PRESS.addRecipe(recipe);
            return recipe;
        }
    }
}
