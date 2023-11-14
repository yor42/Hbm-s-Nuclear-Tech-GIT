package com.hbm.handler.gs.script.util;

import com.hbm.inventory.gui.GUIScreenBobmazon;
import net.minecraft.item.ItemStack;

import java.util.List;

import static com.hbm.handler.BobmazonOfferFactory.*;

public class GSOffer extends GUIScreenBobmazon.Offer {

    private final Category category;

    public GSOffer(Category category, ItemStack offer, GUIScreenBobmazon.Requirement requirement, int cost, int rating, String comment, String author) {
        super(offer, requirement, cost, rating, comment, author);
        this.category = category;
    }

    public GSOffer(Category category, ItemStack offer, GUIScreenBobmazon.Requirement requirement, int cost) {
        super(offer, requirement, cost);
        this.category = category;
    }

    public Category getCategory() {
        return this.category;
    }

    public enum Category{
        MATERIALS(materials), MACHINES(machines), WEAPONS(weapons), TOOLS(tools), SPECIAL(special);

        final List<GUIScreenBobmazon.Offer> list;

        Category(List<GUIScreenBobmazon.Offer> list){
            this.list = list;
        }

        public List<GUIScreenBobmazon.Offer> getList() {
            return this.list;
        }
    }
}
