package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.handler.gs.NTM;
import com.hbm.handler.gs.util.IngredientUtils;
import com.hbm.inventory.RecipesCommon;
import com.hbm.util.Tuple;
import net.minecraft.item.ItemStack;

import static com.hbm.inventory.ShredderRecipes.jeiShredderRecipes;
import static com.hbm.inventory.ShredderRecipes.shredderRecipes;

public class Shredder extends VirtualizedRegistry<Tuple.Pair<RecipesCommon.ComparableStack, ItemStack>> {
    @Override
    public void onReload() {
        jeiShredderRecipes = null;
        removeScripted().forEach(this::addRecipe);
        restoreFromBackup().forEach(recipe->removeRecipe(recipe.getKey()));
    }

    private void removeRecipe(RecipesCommon.ComparableStack key) {
        shredderRecipes.remove(key);
    }

    private void removeAll() {
        shredderRecipes.clear();
    }

    private void addRecipe(Tuple.Pair<RecipesCommon.ComparableStack, ItemStack> pair) {
        shredderRecipes.put(pair.getKey(), pair.getValue());
    }

    public RecipeBuilder recipeBuilder(){
        return new RecipeBuilder();
    }

    public static class RecipeBuilder extends AbstractRecipeBuilder<Tuple.Pair<RecipesCommon.ComparableStack, ItemStack>> {

        @Override
        public String getErrorMsg() {
            return "Error adding NTM Shredder Recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            this.validateItems(msg, 1, 1, 1, 1);
        }

        @Override
        public Tuple.Pair<RecipesCommon.ComparableStack, ItemStack> register() {
            if (!this.validate()) {
                return null;
            }
            Tuple.Pair<RecipesCommon.ComparableStack, ItemStack> recipe = new Tuple.Pair<>(IngredientUtils.convertIngredient2ComparableStack(this.input.get(0)), this.output.get(0));
            NTM.NTM.get().SHREDDER.addRecipe(recipe);
            return recipe;
        }
    }

}
