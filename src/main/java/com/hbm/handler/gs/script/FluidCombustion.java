package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.inventory.FluidCombustionRecipes;
import com.hbm.util.Tuple;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import static com.hbm.inventory.FluidCombustionRecipes.resultingTU;

public class FluidCombustion extends VirtualizedRegistry<Tuple.Pair<Fluid, Integer>> {
    @Override
    public void onReload() {
        this.removeScripted().forEach(this::remove);
        this.restoreFromBackup().forEach(this::add);
    }

    public void removeAll(){
        for(Fluid stack: resultingTU.keySet()){
            int value = resultingTU.get(stack);
            remove(new Tuple.Pair<>(stack, value));
        }
    }

    public void remove(Tuple.Pair<Fluid, Integer> pair){
        this.addBackup(pair);
        resultingTU.remove(pair.getKey());
    }

    public void remove(Fluid fluid){
        int value = FluidCombustionRecipes.getFlameEnergy(fluid);
        remove(new Tuple.Pair<>(fluid, value));
    }

    public void remove(FluidStack fluidStack){
        int value = FluidCombustionRecipes.getFlameEnergy(fluidStack.getFluid());
        remove(new Tuple.Pair<>(fluidStack.getFluid(), value));
    }

    public void add(Tuple.Pair<Fluid, Integer> pair){
        FluidCombustionRecipes.addBurnableFluid(pair.getKey(), pair.getValue());
        this.addScripted(pair);
    }

    public void add(FluidStack fluid, int mbperHeat){
        this.add(new Tuple.Pair<>(fluid.getFluid(), mbperHeat));
    }

}
