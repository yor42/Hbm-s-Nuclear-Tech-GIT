package com.hbm.handler.gs.script.recipes;

import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.helper.ingredient.IngredientList;
import com.hbm.handler.gs.util.IngredientUtils;
import com.hbm.inventory.AnvilSmithingRecipe;
import net.minecraft.item.ItemStack;

public class GroovyAnvilSmithingRecipes extends AnvilSmithingRecipe {

    public GroovyAnvilSmithingRecipes(int tier, ItemStack out, IngredientList<IIngredient> input) {
        super(tier, out, IngredientUtils.convertIngredient2Astack(input.get(0)), IngredientUtils.convertIngredient2Astack(input.get(1)));
    }
}
