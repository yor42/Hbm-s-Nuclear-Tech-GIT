package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.brackets.BracketHandlerManager;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.inventory.FluidCombustionRecipes;
import com.hbm.util.Tuple;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import static com.hbm.inventory.FluidCombustionRecipes.resultingTU;

public class FluidCombustion extends VirtualizedRegistry<Tuple.Pair<Fluid, Integer>> {
    @Override
    public void onReload() {
        this.removeScripted().forEach(recipe-> FluidCombustionRecipes.removeBurnableFluid(recipe.getKey()));
        this.restoreFromBackup().forEach(recipe-> FluidCombustionRecipes.addBurnableFluid(recipe.getKey(), recipe.getValue()));
    }

    public void removeAll(){
        resultingTU.clear();
    }

    public void remove(@NotNull FluidStack fluidStack){
        FluidCombustionRecipes.removeBurnableFluid(fluidStack.getFluid());
    }

    public void remove(Fluid fluid){
        FluidCombustionRecipes.removeBurnableFluid(fluid);
    }

    public void add(FluidStack fluid, int mbperHeat){
        FluidCombustionRecipes.addBurnableFluid(fluid.getFluid(), mbperHeat);
    }

}
