package com.hbm.blocks.fluid;

import java.awt.Color;

import com.hbm.Tags;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class VolcanicFluid extends Fluid {

	public VolcanicFluid() {
		super("volcanic_lava_fluid", new ResourceLocation(Tags.MOD_ID, "blocks/forgefluid/volcanic_lava_still"), new ResourceLocation(Tags.MOD_ID, "blocks/forgefluid/volcanic_lava_flowing"), Color.white);
	}
}
