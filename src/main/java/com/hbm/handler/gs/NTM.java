package com.hbm.handler.gs;

import com.cleanroommc.groovyscript.compat.mods.ModPropertyContainer;
import com.cleanroommc.groovyscript.compat.mods.ModSupport;
import com.hbm.handler.gs.script.*;
import com.hbm.lib.RefStrings;

public class NTM extends ModPropertyContainer {

    public static final ModSupport.Container<NTM> NTM = new ModSupport.Container<>(RefStrings.MODID, RefStrings.NAME , NTM::new, "ntm");
    public final AnvilSmithing ANVILSMITHING = new AnvilSmithing();
    public final AnvilConstruction ANVILCONSTRUCTION = new AnvilConstruction();
    public final Assembler ASSEMBLER = new Assembler();
    public final Press PRESS = new Press();
    public final BlastFurnaceFuel BLASTFURNACEFUEL = new BlastFurnaceFuel();
    public final BlastFurnace BLASTFURNACE = new BlastFurnace();
    public final Shredder SHREDDER = new Shredder();
    public final Bobmazon BOBMAZON = new Bobmazon();
    public final BreedingReactor BREEDINGREACTOR = new BreedingReactor();
    public final Centrifuge CENTRIFUGE = new Centrifuge();
    public final DFC DFC = new DFC();
    public final FluidCombustion FLUIDCOMBUSTION = new FluidCombustion();
    public final SILEX SILEX = new SILEX();
    public final IrradiationChannel IRRADIATIONCHANNEL = new IrradiationChannel();
    public final FluidHeating FLUIDHEATING = new FluidHeating();
    public final WasteDrum WASTEDRUM = new WasteDrum();

    public NTM(){
        addRegistry(ANVILSMITHING);
        addRegistry(ANVILCONSTRUCTION);
        addRegistry(ASSEMBLER);
        addRegistry(PRESS);
        addRegistry(BLASTFURNACEFUEL);
        addRegistry(BLASTFURNACE);
        addRegistry(SHREDDER);
        addRegistry(BOBMAZON);
        addRegistry(BREEDINGREACTOR);
        addRegistry(CENTRIFUGE);
        addRegistry(DFC);
        addRegistry(FLUIDCOMBUSTION);
        addRegistry(WASTEDRUM);
    }

    public static void register(){}
}
