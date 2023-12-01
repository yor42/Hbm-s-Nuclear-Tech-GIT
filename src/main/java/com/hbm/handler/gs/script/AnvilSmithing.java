package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.helper.SimpleObjectStream;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.handler.gs.NTM;
import com.hbm.handler.gs.script.recipes.GroovyAnvilSmithingRecipes;
import com.hbm.inventory.AnvilRecipes;
import com.hbm.inventory.AnvilSmithingRecipe;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class AnvilSmithing extends VirtualizedRegistry<AnvilSmithingRecipe> {

    @Override
    public void onReload() {
        Collection<AnvilSmithingRecipe> collection = this.removeScripted();
        List<AnvilSmithingRecipe> recipelist = AnvilRecipes.getSmithing();
        collection.forEach(recipelist::remove);
        collection = this.restoreFromBackup();
        recipelist = AnvilRecipes.getSmithing();
        recipelist.addAll(collection);
    }

    public RecipeBuilder recipeBuilder() {
        return new RecipeBuilder();
    }

    public void add(AnvilSmithingRecipe recipe) {
        this.addScripted(recipe);
        AnvilRecipes.getSmithing().add(recipe);
    }

    public boolean remove(AnvilSmithingRecipe recipe){
        if(AnvilRecipes.getSmithing().contains(recipe)){
            this.addBackup(recipe);
            AnvilRecipes.getSmithing().remove(recipe);
            return true;
        }
        return false;
    }

    public void removeAll(){
        AnvilRecipes.getSmithing().forEach(this::addBackup);
        AnvilRecipes.getSmithing().clear();
    }

    public SimpleObjectStream<AnvilSmithingRecipe> streamRecipes() {
        return new SimpleObjectStream<>(AnvilRecipes.getSmithing()).setRemover(this::remove);
    }

    public void removeByOutput(ItemStack item) {

        for (AnvilSmithingRecipe recipe : AnvilRecipes.getSmithing().stream().filter((x) -> x.getSimpleOutput().isItemEqual(item)).collect(Collectors.toList())) {
            this.remove(recipe);
        }

    }

    public static class RecipeBuilder extends AbstractRecipeBuilder<AnvilSmithingRecipe> {

        int tier = 1;

        public RecipeBuilder tier(int tier){
            this.tier = tier;
            return this;
        }

        public RecipeBuilder tierIron(){
            this.tier = 1;
            return this;
        }

        public RecipeBuilder tierSteel(){
            this.tier = 1;
            return this;
        }

        public RecipeBuilder tierMeteorite(){
            this.tier = 2;
            return this;
        }

        public RecipeBuilder tierStarmetal(){
            this.tier = 3;
            return this;
        }

        public RecipeBuilder tierFerrouranium(){
            this.tier = 4;
            return this;
        }

        public RecipeBuilder tierBismuth(){
            this.tier = 5;
            return this;
        }

        public RecipeBuilder tierSchrabidate(){
            this.tier = 6;
            return this;
        }

        public RecipeBuilder tierDNT(){
            this.tier = 7;
            return this;
        }

        public RecipeBuilder tierOsmiridium(){
            this.tier = 8;
            return this;
        }

        public RecipeBuilder tierMurky(){
            this.tier = 1916169;
            return this;
        }

        @Override
        public String getErrorMsg() {
            return "Error adding NTM anvil recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            this.validateItems(msg, 1, 2, 1, 1);
            msg.add(this.tier < 1 || this.tier > 1916169, "tier must be between 0 (iron) and 1916169 (Murky), yet it was {}. (Highest tier that player can obtain in vanilla setup is 8.)", this.tier);
        }

        @Override
        public AnvilSmithingRecipe register() {
            if (!this.validate()) {
                return null;
            }
            GroovyAnvilSmithingRecipes recipe = new GroovyAnvilSmithingRecipes(this.tier, this.output.get(0), this.input);
            NTM.ANVILSMITHING.add(recipe);
            return recipe;
        }
    }

}
