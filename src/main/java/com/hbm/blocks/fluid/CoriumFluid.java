package com.hbm.blocks.fluid;

import java.awt.Color;

import com.hbm.Tags;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class CoriumFluid extends Fluid {
	
	public CoriumFluid(){
		super("corium_fluid", new ResourceLocation(Tags.MOD_ID, "blocks/forgefluid/corium_still"), new ResourceLocation(Tags.MOD_ID, "blocks/forgefluid/corium_flowing"), Color.white);
	}

}
