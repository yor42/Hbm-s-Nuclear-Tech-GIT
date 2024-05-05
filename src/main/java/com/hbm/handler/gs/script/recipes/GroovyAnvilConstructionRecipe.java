package com.hbm.handler.gs.script.recipes;

import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.helper.ingredient.IngredientList;
import com.hbm.handler.gs.util.IngredientUtils;
import com.hbm.inventory.AnvilRecipes;

public class GroovyAnvilConstructionRecipe extends AnvilRecipes.AnvilConstructionRecipe {
    public GroovyAnvilConstructionRecipe(IngredientList<IIngredient> input, AnvilRecipes.AnvilOutput[] output, AnvilRecipes.OverlayType overlayType, int minTier, int maxTier) {
        super(IngredientUtils.convertIngredient2Astack(input.get(0)), output);
        for(IIngredient ingredient : input){
            this.input.add(IngredientUtils.convertIngredient2Astack(ingredient));
        }
        this.setOverlay(overlayType);
        this.tierLower = minTier;
        this.tierUpper = maxTier;
    }
}
