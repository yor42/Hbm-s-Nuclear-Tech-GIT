package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.helper.SimpleObjectStream;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.handler.gs.NTM;
import com.hbm.handler.gs.script.recipes.GroovyAnvilConstructionRecipe;
import com.hbm.inventory.AnvilRecipes;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AnvilConstruction extends VirtualizedRegistry<AnvilRecipes.AnvilConstructionRecipe> {
    @Override
    public void onReload() {
        Collection<AnvilRecipes.AnvilConstructionRecipe> collection = this.removeScripted();
        List<AnvilRecipes.AnvilConstructionRecipe> recipelist = AnvilRecipes.getConstruction();
        collection.forEach(recipelist::remove);
        collection = this.restoreFromBackup();
        recipelist = AnvilRecipes.getConstruction();
        recipelist.addAll(collection);
    }

    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    public void add(AnvilRecipes.AnvilConstructionRecipe recipe) {
        this.addScripted(recipe);
        AnvilRecipes.getConstruction().add(recipe);
    }

    public boolean remove(AnvilRecipes.AnvilConstructionRecipe recipe) {
        if (AnvilRecipes.getConstruction().contains(recipe)) {
            this.addBackup(recipe);
            AnvilRecipes.getConstruction().remove(recipe);
            return true;
        }
        return false;
    }

    public void removeAll(){
        AnvilRecipes.getConstruction().forEach(this::addBackup);
        AnvilRecipes.getConstruction().clear();
    }

    public SimpleObjectStream<AnvilRecipes.AnvilConstructionRecipe> streamRecipes() {
        return new SimpleObjectStream<>(AnvilRecipes.getConstruction()).setRemover(this::remove);
    }

    public static class RecipeBuilder extends AbstractRecipeBuilder<AnvilRecipes.AnvilConstructionRecipe> {

        private int minTier = 0;
        private int maxTier = -1;
        private AnvilRecipes.OverlayType overlayType = AnvilRecipes.OverlayType.NONE;
        public List<AnvilRecipes.AnvilOutput> output = new ArrayList<>();

        public RecipeBuilder OverlayConstruction(){
            this.overlayType = AnvilRecipes.OverlayType.CONSTRUCTION;
            return this;
        }

        public RecipeBuilder OverlayNone(){
            this.overlayType = AnvilRecipes.OverlayType.NONE;
            return this;
        }

        public RecipeBuilder OverlayRecycling(){
            this.overlayType = AnvilRecipes.OverlayType.RECYCLING;
            return this;
        }

        public RecipeBuilder OverlaySmithing(){
            this.overlayType = AnvilRecipes.OverlayType.SMITHING;
            return this;
        }

        public RecipeBuilder minTier(int tier){
            this.minTier = tier;
            return this;
        }

        public RecipeBuilder maxTier(int tier){
            this.maxTier = tier;
            return this;
        }

        public RecipeBuilder output(ItemStack stack, float chance){
            this.output.add(new AnvilRecipes.AnvilOutput(stack, chance));
            return this;
        }

        public RecipeBuilder output(ItemStack stack){
            return output(stack, 1.0f);
        }

        @Override
        public AbstractRecipeBuilder<AnvilRecipes.AnvilConstructionRecipe> output(ItemStack... outputs) {
            for(ItemStack stack:outputs){
                this.output.add(new AnvilRecipes.AnvilOutput(stack));
            }
            return this;
        }

        @Override
        public AbstractRecipeBuilder<AnvilRecipes.AnvilConstructionRecipe> output(Collection<ItemStack> outputs) {
            for(ItemStack stack:outputs){
                this.output.add(new AnvilRecipes.AnvilOutput(stack));
            }
            return this;
        }

        @Override
        public String getErrorMsg() {
            return "Error adding NTM anvil construction recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            msg.add(this.output.isEmpty(), "Output can not be empty!");
            msg.add(this.input.isEmpty(), "Input can not be empty!");
        }

        @Override
        public AnvilRecipes.AnvilConstructionRecipe register() {
            if (!this.validate()) {
                return null;
            }
            GroovyAnvilConstructionRecipe recipe = new GroovyAnvilConstructionRecipe(this.input, this.output.toArray(new AnvilRecipes.AnvilOutput[0]), this.overlayType, this.minTier, this.maxTier);
            NTM.ANVILCONSTRUCTION.add(recipe);
            return recipe;
        }
    }

}