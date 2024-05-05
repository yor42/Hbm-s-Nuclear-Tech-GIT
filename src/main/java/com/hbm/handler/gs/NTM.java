package com.hbm.handler.gs;

import com.cleanroommc.groovyscript.api.GroovyPlugin;
import com.cleanroommc.groovyscript.compat.mods.GroovyContainer;
import com.cleanroommc.groovyscript.compat.mods.ModPropertyContainer;
import com.cleanroommc.groovyscript.compat.mods.ModSupport;
import com.hbm.handler.gs.script.*;
import com.hbm.lib.RefStrings;

public class NTM implements GroovyPlugin {
    public static final AnvilSmithing ANVILSMITHING = new AnvilSmithing();
    public static final AnvilConstruction ANVILCONSTRUCTION = new AnvilConstruction();
    public static final Assembler ASSEMBLER = new Assembler();
    public static final Press PRESS = new Press();
    public static final BlastFurnaceFuel BLASTFURNACEFUEL = new BlastFurnaceFuel();
    public static final BlastFurnace BLASTFURNACE = new BlastFurnace();
    public static final Shredder SHREDDER = new Shredder();
    public static final Bobmazon BOBMAZON = new Bobmazon();
    public static final BreedingReactor BREEDINGREACTOR = new BreedingReactor();
    public static final Centrifuge CENTRIFUGE = new Centrifuge();
    public static final DFC DFC = new DFC();
    public static final FluidCombustion FLUIDCOMBUSTION = new FluidCombustion();
    public static final SILEX SILEX = new SILEX();
    public static final IrradiationChannel IRRADIATIONCHANNEL = new IrradiationChannel();
    public static final FluidHeating FLUIDHEATING = new FluidHeating();
    public static final WasteDrum WASTEDRUM = new WasteDrum();

    public static void register(){}

    @Override
    public String getModId() {
        return RefStrings.MODID;
    }

    @Override
    public String getContainerName() {
        return RefStrings.NAME;
    }
    @Override
    public void onCompatLoaded(GroovyContainer<?> groovyContainer) {
        groovyContainer.getRegistrar().addFieldsOf(this);
    }
}
