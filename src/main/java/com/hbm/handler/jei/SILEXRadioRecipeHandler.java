package com.hbm.handler.jei;

import mezz.jei.api.IGuiHelper;

public class SILEXRadioRecipeHandler extends SILEXRecipeHandler {

	public SILEXRadioRecipeHandler(IGuiHelper help){
		super(help);
	}

	@Override
	public String getUid(){
		return JEIConfig.SILEX_RADIO;
	}

	@Override
	public String getTitle(){
		return "SILEX Radio Recipes";
	}
}
