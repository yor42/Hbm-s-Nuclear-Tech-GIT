package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.handler.gs.NTM;
import com.hbm.inventory.RBMKOutgasserRecipes;
import com.hbm.inventory.RecipesCommon;
import com.hbm.util.Tuple;
import net.minecraft.item.ItemStack;

import java.util.Iterator;

import static com.hbm.inventory.RBMKOutgasserRecipes.rbmkOutgasserRecipes;

public class IrradiationChannel extends VirtualizedRegistry<Tuple.Pair<RecipesCommon.ComparableStack, Object[]>> {
    @Override
    public void onReload() {
        this.removeScripted().forEach(this::removeRecipe);
        this.restoreFromBackup().forEach(this::addRecipe);
    }

    public void removeRecipe(Tuple.Pair<RecipesCommon.ComparableStack, Object[]> pair){
        rbmkOutgasserRecipes.remove(pair.getKey(), pair.getValue());
        this.addBackup(pair);
    }

    public void removeAll(){
        for (Iterator<RecipesCommon.ComparableStack> it = rbmkOutgasserRecipes.keySet().iterator(); it.hasNext(); ) {
            RecipesCommon.ComparableStack stack = it.next();
            Object[] param = rbmkOutgasserRecipes.get(stack);
            this.addBackup(new Tuple.Pair<>(stack, param));
            it.remove();
        }
    }

    public void removeRecipe(IIngredient ingredient){
        for(ItemStack stack:ingredient.getMatchingStacks()){
            removeRecipe(stack);
        }
    }

    public void removeRecipe(ItemStack stack){
        RecipesCommon.ComparableStack comparableStack = new RecipesCommon.ComparableStack(stack);
        Object[] param = rbmkOutgasserRecipes.get(comparableStack);
        rbmkOutgasserRecipes.remove(comparableStack, param);
        this.addBackup(new Tuple.Pair<>(comparableStack, param));
    }

    public void addRecipe(Tuple.Triplet<Integer, IIngredient, ItemStack> triplet){
        addRecipe(triplet.getX(), triplet.getY(), triplet.getZ());
    }

    public void addRecipe(int flux, IIngredient input, ItemStack out){
        for(ItemStack stack:input.getMatchingStacks()){
            addRecipe(flux, stack, out);
        }
    }

    public void addRecipe(int flux, ItemStack input, ItemStack out){
        RecipesCommon.ComparableStack stack = new RecipesCommon.ComparableStack(input);
        Object[] objects = new Object[] {flux, out};
        addRecipe(new Tuple.Pair<>(stack, objects));
    }

    public void addRecipe(Tuple.Pair<RecipesCommon.ComparableStack, Object[]> pair){
        rbmkOutgasserRecipes.put(pair.getKey(), pair.getValue());
        this.addScripted(pair);
    }

    public static class RecipeBuilder extends AbstractRecipeBuilder<Tuple.Triplet<Integer, IIngredient, ItemStack>> {

        private int flux = 0;

        public RecipeBuilder setFlux(int value) {
            this.flux = value;
            return this;
        }

        @Override
        public String getErrorMsg() {
            return "Error adding Irradiation Channel Recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            this.validateItems(msg, 1, 1, 1, 1);
            msg.add(this.flux <= 0, "Flux value must be larger than 0 and got {}.", this.flux);
        }

        @Override
        public Tuple.Triplet<Integer, IIngredient, ItemStack> register() {
            if (!this.validate()) {
                return null;
            }
            Tuple.Triplet<Integer, IIngredient, ItemStack> recipe = new Tuple.Triplet<>(this.flux, this.input.get(0), this.output.get(0));
            NTM.IRRADIATIONCHANNEL.addRecipe(recipe);
            return recipe;
        }
    }
}
