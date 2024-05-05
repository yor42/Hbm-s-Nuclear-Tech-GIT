package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.handler.gs.NTM;
import com.hbm.inventory.HeatRecipes;
import com.hbm.util.Tuple;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import static com.hbm.inventory.HeatRecipes.*;

public class FluidHeating extends VirtualizedRegistry<FluidHeating.HeatRecipe> {

    @Override
    public void onReload() {
        this.removeScripted().forEach(this::removeRecipe);
        this.restoreFromBackup().forEach(this::addRecipe);
    }

    public void removeRecipe(HeatRecipe recipe){
        switch (recipe.getMode()){
            case BOIL:
                removeBoilRecipe(recipe.getCool().getFluid());
                break;
            case COOL:
                removeCoolRecipe(recipe.getHot().getFluid());
                break;
            default:
                removeBoilRecipe(recipe.getCool().getFluid());
                removeCoolRecipe(recipe.getHot().getFluid());
                break;
        }
        this.addBackup(recipe);
    }

    public void removeCoolRecipe(FluidStack fluid){
        removeCoolRecipe(fluid.getFluid());
    }

    public void removeBoilRecipes(FluidStack fluid){
        removeBoilRecipe(fluid.getFluid());
    }

    public void removeCoolRecipe(Fluid fluid){
        HeatRecipes.removeCoolRecipe(fluid);
    }

    public void removeBoilRecipes(Fluid fluid){
        HeatRecipes.removeBoilRecipe(fluid);
    }

    public void removeAllBoil(){
        for(Fluid cold : hotFluids.keySet()){
            Fluid hot = hotFluids.get(cold);
            int TU = requiredTU.get(cold);
            int inputamount = inputAmountHot.get(cold);
            int outputamount = outputAmountHot.get(cold);
            removeRecipe(new HeatRecipe(new FluidStack(cold, inputamount), new FluidStack(hot, outputamount), TU, RECIPEMODE.BOIL));
        }
    }

    public void removeAllCool(){
        for(Fluid hot : coolFluids.keySet()){
            Fluid cool = coolFluids.get(hot);
            int TU = resultingTU.get(hot);
            int inputamount = inputAmountCold.get(hot);
            int outputamount = outputAmountCold.get(hot);
            removeRecipe(new HeatRecipe(new FluidStack(cool, outputamount), new FluidStack(hot, inputamount), TU, RECIPEMODE.COOL));
        }
    }

    public void removeAll(){
        removeAllBoil();
        removeAllCool();
    }

    public void addRecipe(HeatRecipe recipe){
        switch (recipe.getMode()){
            case BOIL:
                addBoilRecipe(recipe.getCool(), recipe.getHot(), recipe.getHeat());
                break;
            case COOL:
                addCoolRecipe(recipe.getHot(), recipe.getCool(), recipe.getHeat());
                break;
            default:
                addBoilAndCoolRecipe(recipe.getCool(), recipe.getHot(), recipe.getHeat());
                break;
        }
        this.addScripted(recipe);
    }

    public static class RecipeBuilder extends AbstractRecipeBuilder<FluidHeating.HeatRecipe> {

        private int heat = 0;
        private RECIPEMODE mode = RECIPEMODE.BOTH;

        public RecipeBuilder setHeat(int value) {
            this.heat = value;
            return this;
        }

        public RecipeBuilder setBoil(){
            this.mode = RECIPEMODE.BOIL;
            return this;
        }

        public RecipeBuilder setCool(){
            this.mode = RECIPEMODE.COOL;
            return this;
        }

        public RecipeBuilder setBoilandCool(){
            this.mode = RECIPEMODE.BOTH;
            return this;
        }

        @Override
        public String getErrorMsg() {
            return "Error adding FluidHeating Recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            this.validateFluids(msg,1,1,1,1);
            msg.add(this.heat<=0, "Temperature must be higher than 0 and yet it was {}.", this.heat);
        }

        @Override
        public FluidHeating.HeatRecipe register() {
            if (!this.validate()) {
                return null;
            }
            HeatRecipe recipe = new HeatRecipe(this.fluidInput.get(0), this.fluidOutput.get(0), this.heat, this.mode);
            NTM.FLUIDHEATING.addRecipe(recipe);
            return recipe;
        }
    }

    public static class HeatRecipe{

        private final FluidStack cool;
        private final FluidStack hot;
        private final RECIPEMODE mode;
        private final int heat;

        public HeatRecipe(FluidStack cool, FluidStack hot, int heat, RECIPEMODE mode) {
            this.cool = cool;
            this.hot = hot;
            this.mode = mode;
            this.heat = heat;
        }

        public FluidStack getCool() {
            return cool;
        }

        public FluidStack getHot() {
            return hot;
        }

        public RECIPEMODE getMode() {
            return mode;
        }

        public int getHeat() {
            return heat;
        }
    }

    public enum RECIPEMODE {
        COOL, BOIL, BOTH
    }

}
