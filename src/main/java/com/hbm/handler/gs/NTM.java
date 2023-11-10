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
    public final DifurnaceFuel DIFURNACEFUEL = new DifurnaceFuel();
    public final DiFurnace DIFURNACE = new DiFurnace();
    public final Shredder SHREDDER = new Shredder();
    public NTM(){
        addRegistry(ANVILSMITHING);
        addRegistry(ANVILCONSTRUCTION);
        addRegistry(ASSEMBLER);
        addRegistry(PRESS);
        addRegistry(DIFURNACEFUEL);
        addRegistry(DIFURNACE);
        addRegistry(SHREDDER);
    }

    public static void register(){};
}
