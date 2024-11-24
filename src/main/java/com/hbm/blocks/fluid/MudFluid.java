package com.hbm.blocks.fluid;

import java.awt.Color;

import com.hbm.Tags;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class MudFluid extends Fluid {

	public MudFluid(){
		super("mud_fluid", new ResourceLocation(Tags.MOD_ID, "blocks/forgefluid/mud_still"), new ResourceLocation(Tags.MOD_ID, "blocks/forgefluid/mud_flowing"), Color.white);
	}

}
