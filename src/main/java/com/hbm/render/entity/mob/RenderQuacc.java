package com.hbm.render.entity.mob;

import com.hbm.Tags;
import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.EntityDuck;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

/**
 *  BOW
 */
public class RenderQuacc extends RenderChicken {

	/**
	 *  BOW
	 */
	public static final IRenderFactory<EntityDuck> FACTORY = man -> new RenderQuacc(man);
	
	/**
	 *  BOW
	 */
	public static final ResourceLocation ducc = new ResourceLocation(Tags.MOD_ID, "textures/entity/duck.png");
	
	 /**
     *  BOW
     */
	public RenderQuacc(RenderManager p_i47211_1_) {
		super(p_i47211_1_);
	}
	
	 /**
     *  BOW
     */
	@Override
	protected ResourceLocation getEntityTexture(EntityChicken DUCC) {
		return ducc;
	}
	
	 /**
     *  BOW
     */
	@Override
	protected void preRenderCallback(EntityChicken DUCC, float partialTickTime) {
		GL11.glScaled(25, 25, 25);
	}
}
