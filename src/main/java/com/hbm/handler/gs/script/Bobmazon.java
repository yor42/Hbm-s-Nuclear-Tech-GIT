package com.hbm.handler.gs.script;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.documentation.annotations.Comp;
import com.cleanroommc.groovyscript.api.documentation.annotations.Property;
import com.cleanroommc.groovyscript.api.documentation.annotations.RecipeBuilderMethodDescription;
import com.cleanroommc.groovyscript.api.documentation.annotations.RecipeBuilderRegistrationMethod;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.StandardListRegistry;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.hbm.handler.BobmazonOfferFactory;
import com.hbm.handler.gs.NTM;
import com.hbm.inventory.gui.GUIScreenBobmazon;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import static com.hbm.handler.BobmazonOfferFactory.*;

public class Bobmazon extends StandardListRegistry<GUIScreenBobmazon.Offer> {

    @Override
    public Collection<GUIScreenBobmazon.Offer> getRecipes() {
        return custom;
    }

    public RecipeBuilder recipeBuilder(){
        return new RecipeBuilder();
    }

    public static class RecipeBuilder extends AbstractRecipeBuilder<GUIScreenBobmazon.Offer> {
        @Property(defaultValue = "STEEL", comp = @Comp(not = "null"))
        public GUIScreenBobmazon.Requirement requirement = GUIScreenBobmazon.Requirement.STEEL;
        @Property(defaultValue = "1", comp = @Comp(gt = 0))
        public int cost = 1;
        @Property(defaultValue = "0", comp = @Comp(gte = 0))
        public int rating = 0;
        @Property(defaultValue = "No Ratings", comp = @Comp(not = "null"))
        public String comment = "No Ratings";
        @Property(comp = @Comp(not = "null"))
        public String author = "";
        @Property(defaultValue = "MATERIALS", comp = @Comp(not = "null"))
        public BobmazonOfferFactory.OfferCategorie category = BobmazonOfferFactory.OfferCategorie.MATERIALS;

        @RecipeBuilderMethodDescription(field = "requirement")
        public RecipeBuilder setRequirementSteel(){
            this.requirement = GUIScreenBobmazon.Requirement.STEEL;
            return this;
        }
        @RecipeBuilderMethodDescription(field = "requirement")
        public RecipeBuilder setRequirementAssembly(){
            this.requirement = GUIScreenBobmazon.Requirement.ASSEMBLY;
            return this;
        }
        @RecipeBuilderMethodDescription(field = "requirement")
        public RecipeBuilder setRequirementChemics(){
            this.requirement = GUIScreenBobmazon.Requirement.CHEMICS;
            return this;
        }
        @RecipeBuilderMethodDescription(field = "requirement")
        public RecipeBuilder setRequirementOil(){
            this.requirement = GUIScreenBobmazon.Requirement.OIL;
            return this;
        }
        @RecipeBuilderMethodDescription(field = "requirement")
        public RecipeBuilder setRequirementNuclear(){
            this.requirement = GUIScreenBobmazon.Requirement.NUCLEAR;
            return this;
        }
        @RecipeBuilderMethodDescription(field = "requirement")
        public RecipeBuilder setRequirementHidden(){
            this.requirement = GUIScreenBobmazon.Requirement.HIDDEN;
            return this;
        }
        @RecipeBuilderMethodDescription(field = "cost")
        public RecipeBuilder setCost(int cost){
            this.cost = cost*inflation;
            return this;
        }
        @RecipeBuilderMethodDescription(field = "author")
        public RecipeBuilder setAuthor(String author){
            this.author = author;
            return this;
        }
        @RecipeBuilderMethodDescription(field = "comment")
        public RecipeBuilder setComment(String comment){
            this.comment = comment;
            return this;
        }
        @RecipeBuilderMethodDescription(field = "category")
        public RecipeBuilder setCategoryMaterials(){
            this.category = BobmazonOfferFactory.OfferCategorie.MATERIALS;
            return this;
        }
        @RecipeBuilderMethodDescription(field = "category")
        public RecipeBuilder setCategoryMachiness(){
            this.category = BobmazonOfferFactory.OfferCategorie.MACHINES;
            return this;
        }
        @RecipeBuilderMethodDescription(field = "category")
        public RecipeBuilder setCategoryWeapons(){
            this.category = BobmazonOfferFactory.OfferCategorie.WEAPONS;
            return this;
        }
        @RecipeBuilderMethodDescription(field = "category")
        public RecipeBuilder setCategoryTools(){
            this.category = BobmazonOfferFactory.OfferCategorie.TOOLS;
            return this;
        }
        @RecipeBuilderMethodDescription(field = "category")
        public RecipeBuilder setCategorySpecial(){
            this.category = BobmazonOfferFactory.OfferCategorie.SPECIAL;
            return this;
        }
        @RecipeBuilderMethodDescription(field = "category")
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
        @RecipeBuilderRegistrationMethod
        public GUIScreenBobmazon.Offer register() {
            if (!this.validate()) {
                return null;
            }
            GUIScreenBobmazon.Offer recipe = new GUIScreenBobmazon.Offer(this.output.get(0), this.requirement, this.cost, this.rating, this.comment, this.author, this.category);
            NTM.BOBMAZON.add(recipe);
            return recipe;
        }
    }

}
