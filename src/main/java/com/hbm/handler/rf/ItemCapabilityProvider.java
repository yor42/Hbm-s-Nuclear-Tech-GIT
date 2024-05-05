package com.hbm.handler.rf;

import api.hbm.energy.IBatteryItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemCapabilityProvider implements ICapabilityProvider {

    private final ItemStack stack;
    private final IEnergyStorage energy;

    public ItemCapabilityProvider(ItemStack stack){
        this.stack = stack;
        this.energy = new ItemsFEWrapper(this.stack);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {

        if(capability == CapabilityEnergy.ENERGY){
            return stack.getItem() instanceof IBatteryItem;
        }

        return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {

        if(capability == CapabilityEnergy.ENERGY){
            return (T) energy;
        }

        return null;
    }
}
