package com.hbm.handler.jei;

import mezz.jei.api.IGuiHelper;

public class SILEXDigammaRecipeHandler extends SILEXRecipeHandler {

	public SILEXDigammaRecipeHandler(IGuiHelper help){
		super(help);
	}
	
	@Override
	public String getUid(){
		return JEIConfig.SILEX_DIGAMMA;
	}

	@Override
	public String getTitle(){
		return "SILEX Digamma Recipes";
	}
}
