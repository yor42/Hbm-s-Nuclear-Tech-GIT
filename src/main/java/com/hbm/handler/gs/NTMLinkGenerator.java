package com.hbm.handler.gs;

import com.cleanroommc.groovyscript.documentation.linkgenerator.BasicLinkGenerator;
import com.hbm.Tags;

public class NTMLinkGenerator extends BasicLinkGenerator {
    @Override
    public String id() {
        return "academy";
    }

    @Override
    protected String version() {
        return "v"+ Tags.MOD_ID;
    }

    @Override
    protected String domain() {
        return "https://github.com/yor42/Hbm-s-Nuclear-Tech-GIT/";
    }
}
