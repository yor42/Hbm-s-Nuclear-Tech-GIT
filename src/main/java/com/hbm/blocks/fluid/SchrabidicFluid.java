package com.hbm.blocks.fluid;

import java.awt.Color;

import com.hbm.Tags;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class SchrabidicFluid extends Fluid {

	public SchrabidicFluid(String name){
		super(name, new ResourceLocation(Tags.MOD_ID, "blocks/forgefluid/schrabidic_acid_still"), new ResourceLocation(Tags.MOD_ID, "blocks/forgefluid/schrabidic_acid_flowing"), Color.white);
	}
	
}
