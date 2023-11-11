package com.hbm.entity.particle;

import java.util.Random;

import com.hbm.main.ModEventHandlerClient;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleContrail extends Particle {

	private final TextureManager theRenderEngine;
	public boolean doFlames = false;
	protected static float flameRed;
	protected static float flameGreen;
	protected static float flameBlue;
	protected static float lowRed;
	protected static float lowGreen;
	protected static float lowBlue;
	private int age = 0;
	private final int maxAge;

	public ParticleContrail(TextureManager manage, World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
		theRenderEngine = manage;
		maxAge = 300 + rand.nextInt(50);

		this.particleRed = this.particleGreen = this.particleBlue = 0;
		this.particleScale = 1F;
	}

	public ParticleContrail(TextureManager manage, World worldIn, double posXIn, double posYIn, double posZIn, float red, float green, float blue, float scale) {
		super(worldIn, posXIn, posYIn, posZIn);
		theRenderEngine = manage;
		maxAge = 300 + rand.nextInt(50);

		lowRed = red;
		lowGreen = green;
		lowBlue = blue;

		this.particleScale = scale;
	}

	public ParticleContrail(TextureManager manage, World worldIn, double posXIn, double posYIn, double posZIn, float flameRed, float flameGreen, float flameBlue, float red, float green, float blue, float scale) {
		this(manage, worldIn, posXIn, posYIn, posZIn, red, green, blue, scale);
		ParticleContrail.flameRed = flameRed;
		ParticleContrail.flameGreen = flameGreen;
		ParticleContrail.flameBlue = flameBlue;
		this.doFlames = true;
	}

	public void setMotion(double x, double y, double z){
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.setAlphaF(1F - (float)Math.pow((float) this.age / (float) this.maxAge, 2));

		this.age++;

		if (this.age == this.maxAge) {
			this.setExpired();
		}
		this.motionX *= 0.8;
		this.motionY *= 0.8;
		this.motionZ *= 0.8;
		
        this.move(this.motionX, this.motionY, this.motionZ);
	}

	private float clampGood(float b, float a, float c){
		if(c < a){
			return MathHelper.clamp(b, c, a);
		}
		else{
			return MathHelper.clamp(b, a, c);
		}
	}

	private float getColor(int index){
		float pColor = 0;
		if(index == 0){
			if(doFlames){
				pColor = clampGood(flameRed - (flameRed- lowRed)*(1-particleAlpha)*50F, flameRed, lowRed);
			} else {
				pColor = lowRed;
			}
		} else if(index == 1){
			if(doFlames){
				pColor = clampGood(flameGreen - (flameGreen- lowGreen)*(1-particleAlpha)*50F, flameGreen, lowGreen);
			} else {
				pColor = lowGreen;
			}
		} else if(index == 2){
			if(doFlames){
				pColor = clampGood(flameBlue - (flameBlue- lowBlue)*(1-particleAlpha)*50F, flameBlue, lowBlue);
			} else {
				pColor = lowBlue;
			}
		}
		return MathHelper.clamp(pColor, 0, 1);
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		
		this.theRenderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		this.particleTexture = ModEventHandlerClient.contrail;
		float f = (float) this.particleTextureIndexX / 16.0F;
		float f1 = f + 0.0624375F;
		float f2 = (float) this.particleTextureIndexY / 16.0F;
		float f3 = f2 + 0.0624375F;
		float f4 = (1-particleAlpha)*3F + 0.5F * this.particleScale;

		if (this.particleTexture != null) {
			f = this.particleTexture.getMinU();
			f1 = this.particleTexture.getMaxU();
			f2 = this.particleTexture.getMinV();
			f3 = this.particleTexture.getMaxV();
		}
		this.setRBGColorF(getColor(0), getColor(1), getColor(2));
		Random urandom = new Random(this.hashCode());
		for (int ii = 0; ii < 6; ii++) {
			
			float f5 = (float) ((this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX) + urandom.nextGaussian() * (0.2 + (1-particleAlpha)));
			float f6 = (float) ((this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY) + urandom.nextGaussian() * (0.2 + (1-particleAlpha)));
			float f7 = (float) ((this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ) + urandom.nextGaussian() * (0.2 + (1-particleAlpha)));
			
			int i = this.getBrightnessForRender(partialTicks);
			int j = i >> 16 & 65535;
			int k = i & 65535;
			
			Vec3d[] avec3d = new Vec3d[] { new Vec3d(-rotationX * f4 - rotationXY * f4, -rotationZ * f4, -rotationYZ * f4 - rotationXZ * f4), new Vec3d(-rotationX * f4 + rotationXY * f4, rotationZ * f4, -rotationYZ * f4 + rotationXZ * f4), new Vec3d(rotationX * f4 + rotationXY * f4, rotationZ * f4, rotationYZ * f4 + rotationXZ * f4), new Vec3d(rotationX * f4 - rotationXY * f4, -rotationZ * f4, rotationYZ * f4 - rotationXZ * f4) };

			if (this.particleAngle != 0.0F) {
				float f8 = this.particleAngle + (this.particleAngle - this.prevParticleAngle) * partialTicks;
				float f9 = MathHelper.cos(f8 * 0.5F);
				float f10 = MathHelper.sin(f8 * 0.5F) * (float) cameraViewDir.x;
				float f11 = MathHelper.sin(f8 * 0.5F) * (float) cameraViewDir.y;
				float f12 = MathHelper.sin(f8 * 0.5F) * (float) cameraViewDir.z;
				Vec3d vec3d = new Vec3d(f10, f11, f12);

				for (int l = 0; l < 4; ++l) {
					avec3d[l] = vec3d.scale(2.0D * avec3d[l].dotProduct(vec3d)).add(avec3d[l].scale((double) (f9 * f9) - vec3d.dotProduct(vec3d))).add(vec3d.crossProduct(avec3d[l]).scale(2.0F * f9));
				}
			}
			
			buffer.pos((double) f5 + avec3d[0].x, (double) f6 + avec3d[0].y, (double) f7 + avec3d[0].z).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
			buffer.pos((double) f5 + avec3d[1].x, (double) f6 + avec3d[1].y, (double) f7 + avec3d[1].z).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
			buffer.pos((double) f5 + avec3d[2].x, (double) f6 + avec3d[2].y, (double) f7 + avec3d[2].z).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
			buffer.pos((double) f5 + avec3d[3].x, (double) f6 + avec3d[3].y, (double) f7 + avec3d[3].z).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		}
	}

	@Override
	public int getBrightnessForRender(float p_189214_1_) {
		return 240;
	}

	public int getFXLayer() {
        return 1;
    }
}
