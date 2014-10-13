package com.lastabyss.carbon.instrumentation;

import com.lastabyss.carbon.Carbon;
import java.io.File;

/**
 * This is exactly what you think it is.
 * @author Navid, Icynene
 */
public class Instrumentator {
    
    private Carbon plugin;
    private File libFolder;

    public Instrumentator(Carbon plugin, File libFolder) {
        this.plugin = plugin;
        this.libFolder = libFolder;
        System.out.println("[Carbon] Lib folder has been set to " + libFolder.getAbsolutePath());
    }

    public void instrumentate() {
        try {
            Tools.loadAgentLibrary(new File(libFolder, "/libraries/natives"));
            AgentLoader.attachAgentToJVM(Tools.getCurrentPID(), ClassMaterialAgent.class, AgentLoader.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
