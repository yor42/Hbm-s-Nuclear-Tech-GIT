package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.handler.gs.NTM;
import com.hbm.handler.gs.script.util.GSOffer;
import com.hbm.inventory.gui.GUIScreenBobmazon;
import net.minecraft.item.ItemStack;

import static com.hbm.config.ToolConfig.inflation;

public class Bobmazon extends VirtualizedRegistry<GSOffer> {
    @Override
    public void onReload() {
        removeScripted().forEach(recipe-> recipe.getCategory().getList().remove(recipe));
        restoreFromBackup().forEach((recipe)-> recipe.getCategory().getList().add(recipe));
    }

    public void remove(GSOffer offer){
        offer.getCategory().getList().remove(offer);
    }

    public void removeAll(){
        for(GSOffer.Category category: GSOffer.Category.values()){
            removeCategory(category);
        }
    }

    public void removeCategory(GSOffer.Category category){
        category.getList().clear();
    }

    public void removeByOutput(ItemStack stack){
        for(GSOffer.Category category: GSOffer.Category.values()){
            category.getList().removeIf(offer -> stack.getItem() == offer.offer.getItem() && offer.offer.getCount() == stack.getCount());
        }
    }

    public void addRecipe(GSOffer offer){
        offer.getCategory().getList().add(offer);
    }

    public RecipeBuilder recipeBuilder(){
        return new RecipeBuilder();
    }

    public static class RecipeBuilder extends AbstractRecipeBuilder<GSOffer> {
       public GUIScreenBobmazon.Requirement requirement = GUIScreenBobmazon.Requirement.STEEL;
        public int cost = 1;
        public int rating = 0;
        public String comment = "No Ratings";
        public String author = "";
        public GSOffer.Category category = GSOffer.Category.MATERIALS;

        public RecipeBuilder setRequirementSteel(){
            this.requirement = GUIScreenBobmazon.Requirement.STEEL;
            return this;
        }

        public RecipeBuilder setRequirementAssembly(){
            this.requirement = GUIScreenBobmazon.Requirement.ASSEMBLY;
            return this;
        }
        public RecipeBuilder setRequirementChemics(){
            this.requirement = GUIScreenBobmazon.Requirement.CHEMICS;
            return this;
        }
        public RecipeBuilder setRequirementOil(){
            this.requirement = GUIScreenBobmazon.Requirement.OIL;
            return this;
        }
        public RecipeBuilder setRequirementNuclear(){
            this.requirement = GUIScreenBobmazon.Requirement.NUCLEAR;
            return this;
        }
        public RecipeBuilder setRequirementHidden(){
            this.requirement = GUIScreenBobmazon.Requirement.HIDDEN;
            return this;
        }
        public RecipeBuilder setCost(int cost){
            this.cost = cost*inflation;
            return this;
        }
        public RecipeBuilder setAuthor(String author){
            this.author = author;
            return this;
        }

        public RecipeBuilder setComment(String comment){
            this.comment = comment;
            return this;
        }

        public RecipeBuilder setCategoryMaterials(){
            this.category = GSOffer.Category.MATERIALS;
            return this;
        }

        public RecipeBuilder setCategoryMachiness(){
            this.category = GSOffer.Category.MACHINES;
            return this;
        }

        public RecipeBuilder setCategoryWeapons(){
            this.category = GSOffer.Category.WEAPONS;
            return this;
        }

        public RecipeBuilder setCategoryTools(){
            this.category = GSOffer.Category.TOOLS;
            return this;
        }

        public RecipeBuilder setCategorySpecial(){
            this.category = GSOffer.Category.SPECIAL;
            return this;
        }

        public RecipeBuilder setRating(int rating){
            this.rating = rating;
            return this;
        }

        @Override
        public String getErrorMsg() {
            return "Error adding Bobmazon Entry.";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            msg.add(this.cost < 1, "cost must be higher than 0, and yet it was {}.", this.cost);
            this.validateItems(msg, 0, 0, 1, 1);
        }

        @Override
        public GSOffer register() {
            if (!this.validate()) {
                return null;
            }
            GSOffer recipe = new GSOffer(this.category, this.output.get(0), this.requirement, this.cost, this.rating, this.comment, this.author);
            NTM.NTM.get().BOBMAZON.addRecipe(recipe);
            return recipe;
        }
    }

}
