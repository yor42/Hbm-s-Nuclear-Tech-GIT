package com.hbm.blocks.fluid;

import java.awt.Color;

import com.hbm.Tags;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class ToxicFluid extends Fluid {

	public ToxicFluid(String name){
		super(name, new ResourceLocation(Tags.MOD_ID, "blocks/forgefluid/toxic_still"), new ResourceLocation(Tags.MOD_ID, "blocks/forgefluid/toxic_flowing"), Color.white);
	}
	
}
