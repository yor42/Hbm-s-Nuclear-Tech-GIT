package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.handler.gs.NTM;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.SILEXRecipes;
import com.hbm.items.machine.ItemFELCrystal;
import com.hbm.util.Tuple;
import com.hbm.util.WeightedRandomObject;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SILEX extends VirtualizedRegistry<Tuple.Pair<IIngredient, SILEXRecipes.SILEXRecipe>> {
    @Override
    public void onReload() {
        removeScripted().forEach(recipe->removeRecipe(recipe.getKey()));
        restoreFromBackup().forEach(recipe->addRecipe(recipe.getKey(), recipe.getValue()));
    }

    public void removeAll(){
        SILEXRecipes.clearRecipes();
    }

    public void removeRecipe(IIngredient ingredient){
        for(ItemStack stack:ingredient.getMatchingStacks()){
            SILEXRecipes.removeRecipe(stack);
        }
    }

    public void addRecipe(Tuple.Pair<IIngredient, SILEXRecipes.SILEXRecipe> recipe){
        addRecipe(recipe.getKey(), recipe.getValue());
    }

    public void addRecipe(IIngredient ingredient, SILEXRecipes.SILEXRecipe recipe){
        for(ItemStack stack:ingredient.getMatchingStacks()){
            SILEXRecipes.addRecipe(new RecipesCommon.ComparableStack(stack), recipe);
        }
    }

    public static class RecipeBuilder extends AbstractRecipeBuilder<Tuple.Pair<IIngredient, SILEXRecipes.SILEXRecipe>>{

        public int fluidProduced = 0;
        public int fluidConsumed = 0;
        public ItemFELCrystal.EnumWavelengths laserStrength = ItemFELCrystal.EnumWavelengths.NULL;
        public List<WeightedRandomObject> outputs = new ArrayList<>();

        public RecipeBuilder fluidProduced(int value){
            this.fluidProduced = value;
            return this;
        }

        public RecipeBuilder fluidConsumed(int value){
            this.fluidConsumed = value;
            return this;
        }

        public RecipeBuilder WaveLengthRadio(){
            this.laserStrength = ItemFELCrystal.EnumWavelengths.RADIO;
            return this;
        }

        public RecipeBuilder WaveLengthMicro(){
            this.laserStrength = ItemFELCrystal.EnumWavelengths.MICRO;
            return this;
        }

        public RecipeBuilder WaveLengthIR(){
            this.laserStrength = ItemFELCrystal.EnumWavelengths.IR;
            return this;
        }

        public RecipeBuilder WaveLengthVisible(){
            this.laserStrength = ItemFELCrystal.EnumWavelengths.VISIBLE;
            return this;
        }

        public RecipeBuilder WaveLengthUV(){
            this.laserStrength = ItemFELCrystal.EnumWavelengths.UV;
            return this;
        }

        public RecipeBuilder WaveLengthXray(){
            this.laserStrength = ItemFELCrystal.EnumWavelengths.XRAY;
            return this;
        }

        public RecipeBuilder WaveLengthGamma(){
            this.laserStrength = ItemFELCrystal.EnumWavelengths.GAMMA;
            return this;
        }

        public RecipeBuilder WaveLengthDRX(){
            this.laserStrength = ItemFELCrystal.EnumWavelengths.DRX;
            return this;
        }

        public RecipeBuilder addOutput(ItemStack stack, int weight){
            this.outputs.add(new WeightedRandomObject(stack, weight));
            return this;
        }

        @Override
        public String getErrorMsg() {
            return "Error adding SILEX Recipes";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            this.validateItems(msg, 1, 1, 0, 0);
            msg.add(this.outputs.isEmpty(), "outputs can't be empty!");
            msg.add(this.fluidProduced < 0 || this.fluidProduced > 16000, "SILEX Recipe's output solution must be 0-16k but got {}mb.", this.fluidProduced);
            msg.add(this.fluidConsumed < 0 || this.fluidConsumed > 16000, "SILEX Recipe's input solution must be 0-16k but got {}mb.", this.fluidConsumed);
        }

        @Override
        public @Nullable Tuple.Pair<IIngredient, SILEXRecipes.SILEXRecipe> register() {
            if (!this.validate()) {
                return null;
            }

            Tuple.Pair<IIngredient, SILEXRecipes.SILEXRecipe> recipe = new Tuple.Pair<>(this.input.get(0), new SILEXRecipes.SILEXRecipe(this.fluidProduced, this.fluidConsumed, this.laserStrength).addOut(this.outputs));
            NTM.NTM.get().SILEX.addRecipe(recipe);
            return recipe;
        }
    }

}
