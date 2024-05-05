package com.hbm.handler.rf;

import api.hbm.energy.IEnergyConnector;
import com.hbm.config.GeneralConfig;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TEHeRfCompatLayer implements ICapabilityProvider, IEnergyStorage {

    private final IEnergyConnector connector;

    public TEHeRfCompatLayer(IEnergyConnector connector){
        this.connector = connector;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {

        if(capability == CapabilityEnergy.ENERGY){
            return (T) this;
        }

        return null;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        int energyReceived = (int) Math.min(this.connector.getMaxPower() - this.getEnergyStored(), Math.min(2048, maxReceive) / GeneralConfig.conversionRateHeToRF);
        if(!simulate){
            this.connector.transferPower(energyReceived);
        }

        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {

        if (!canExtract())
            return 0;

        int energyExtracted = (int) Math.min(this.getEnergyStored(), Math.min(2048, maxExtract) * GeneralConfig.conversionRateHeToRF);
        if(!simulate){
            this.connector.transferPower(energyExtracted*-1);
        }

        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return (int) (this.connector.getPower() * GeneralConfig.conversionRateHeToRF);
    }

    @Override
    public int getMaxEnergyStored() {
        return (int) (this.connector.getMaxPower() * GeneralConfig.conversionRateHeToRF);
    }

    @Override
    public boolean canExtract() {
        return this.connector.getPower()>0;
    }

    @Override
    public boolean canReceive() {
        return this.connector.getPower()<this.connector.getMaxPower();
    }
}
