package com.hbm.handler.crt;

import com.hbm.main.MainRegistry;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;

import java.util.ArrayList;
import java.util.List;

public class NTMCraftTweaker {
	public static final List<IAction> postInitActions = new ArrayList<>();

	public static void applyPostInitActions(){
		try{
			postInitActions.forEach( CraftTweakerAPI::apply );
		} catch( final Throwable t ){
			MainRegistry.logger.info("CraftTweaker integration decativated");
		}
	}
}
//NTMCraftTweaker.postInitActions.add(IAction);