package com.hbm.entity.particle;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

public class ParticleContrailDark extends ParticleContrail {

	public ParticleContrailDark(TextureManager manage, World worldIn, double posXIn, double posYIn, double posZIn) {
		super(manage, worldIn, posXIn, posYIn, posZIn, 0.4F, 0.4F, 0.4F, 1F);
		flameRed = 1F;
		flameGreen = 0.75F;
		flameBlue = 0F;
		this.doFlames = true;
	}
}
