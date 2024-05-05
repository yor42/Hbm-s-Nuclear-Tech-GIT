package com.hbm.handler.rf;

import api.hbm.energy.IBatteryItem;
import com.hbm.config.GeneralConfig;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.IEnergyStorage;

public class ItemsFEWrapper implements IEnergyStorage {

    private final ItemStack stack;

    public ItemsFEWrapper(ItemStack stack){
        this.stack = stack;
    }

    public IBatteryItem getItem() {
        return (IBatteryItem)this.stack.getItem();
    }
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        int energyReceived = (int) Math.min(this.getMaxEnergyStored() - this.getEnergyStored(), maxReceive / GeneralConfig.conversionRateHeToRF);
        if(!simulate){
            this.getItem().chargeBattery(this.stack, energyReceived);
        }

        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {

        if (!canExtract())
            return 0;

        int energyExtracted = (int) Math.min(this.getEnergyStored(), maxExtract * GeneralConfig.conversionRateHeToRF);
        if(!simulate){
            this.getItem().dischargeBattery(this.stack, energyExtracted);
        }

        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return (int) (this.getItem().getCharge(this.stack) * GeneralConfig.conversionRateHeToRF);
    }

    @Override
    public int getMaxEnergyStored() {
        return (int) (this.getItem().getMaxCharge() * GeneralConfig.conversionRateHeToRF);
    }

    @Override
    public boolean canExtract() {
        return this.getEnergyStored()>0;
    }

    @Override
    public boolean canReceive() {
        return this.getEnergyStored() < this.getMaxEnergyStored();
    }
}
