package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.handler.BobmazonOfferFactory;
import com.hbm.handler.gs.NTM;
import com.hbm.inventory.gui.GUIScreenBobmazon;
import net.minecraft.item.ItemStack;

import java.util.Iterator;

import static com.hbm.handler.BobmazonOfferFactory.*;

public class Bobmazon extends VirtualizedRegistry<GUIScreenBobmazon.Offer> {
    @Override
    public void onReload() {
        reset();
        removeScripted().forEach(this::remove);
        restoreFromBackup().forEach(this::addRecipe);
        init();
    }

    private void remove(GUIScreenBobmazon.Offer offer) {
        offer.getCategorie().getList().remove(offer);
        this.addBackup(offer);
    }

    public void addRecipe(GUIScreenBobmazon.Offer offer){
        custom.add(offer);
        this.addScripted(offer);
    }

    public RecipeBuilder recipeBuilder(){
        return new RecipeBuilder();
    }

    public static class RecipeBuilder extends AbstractRecipeBuilder<GUIScreenBobmazon.Offer> {
        public GUIScreenBobmazon.Requirement requirement = GUIScreenBobmazon.Requirement.STEEL;
        public int cost = 1;
        public int rating = 0;
        public String comment = "No Ratings";
        public String author = "";
        public BobmazonOfferFactory.OfferCategorie category = BobmazonOfferFactory.OfferCategorie.MATERIALS;

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
            this.category = BobmazonOfferFactory.OfferCategorie.MATERIALS;
            return this;
        }

        public RecipeBuilder setCategoryMachiness(){
            this.category = BobmazonOfferFactory.OfferCategorie.MACHINES;
            return this;
        }

        public RecipeBuilder setCategoryWeapons(){
            this.category = BobmazonOfferFactory.OfferCategorie.WEAPONS;
            return this;
        }

        public RecipeBuilder setCategoryTools(){
            this.category = BobmazonOfferFactory.OfferCategorie.TOOLS;
            return this;
        }

        public RecipeBuilder setCategorySpecial(){
            this.category = BobmazonOfferFactory.OfferCategorie.SPECIAL;
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
        public GUIScreenBobmazon.Offer register() {
            if (!this.validate()) {
                return null;
            }
            GUIScreenBobmazon.Offer recipe = new GUIScreenBobmazon.Offer(this.output.get(0), this.requirement, this.cost, this.rating, this.comment, this.author, this.category);
            NTM.BOBMAZON.addRecipe(recipe);
            return recipe;
        }
    }

}
