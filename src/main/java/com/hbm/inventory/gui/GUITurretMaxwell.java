package com.hbm.inventory.gui;

import com.hbm.Tags;
import com.hbm.tileentity.turret.TileEntityTurretBaseNT;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUITurretMaxwell extends GUITurretBase {
	
	private static ResourceLocation texture = new ResourceLocation(Tags.MOD_ID + ":textures/gui/weapon/gui_turret_maxwell.png");

	public GUITurretMaxwell(InventoryPlayer invPlayer, TileEntityTurretBaseNT tedf) {
		super(invPlayer, tedf);
	}
	
	protected ResourceLocation getTexture() {
		return texture;
	}

	@Override
	public void drawAmmo(int mouseX, int mouseY){
	}

	@Override
	public int getTurretFontColor(){
		return 0x0C0C0C;
	}
}