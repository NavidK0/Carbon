package com.lastabyss.carbon.instrumentation;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.instrumentation.Tools.Platform;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.spi.AttachProvider;

import java.io.File;
import java.io.IOException;

import sun.tools.attach.LinuxAttachProvider;
import sun.tools.attach.WindowsAttachProvider;

/**
 * This is exactly what you think it is.
 * @author Navid, Icynene
 */
public class Instrumentator {

    private String attachLibFolder;

    public Instrumentator(Carbon plugin, String attachLibFolder) {
        this.attachLibFolder = attachLibFolder;
        System.out.println("[Carbon] Lib folder has been set to " + attachLibFolder);
    }

    public void instrumentate() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
    	Tools.addToLibPath(getLibraryPath(attachLibFolder));
    	AttachProvider.setAttachProvider(getAttachProvider());
        AgentLoader.attachAgentToJVM(Tools.getCurrentPID(), CarbonTransformAgent.class, 
        	"pretransformedclasses/org/bukkit/craftbukkit/v1_7_R4/inventory/CraftItemStack.class",
        	"pretransformedclasses/org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem.class",
        	"pretransformedclasses/org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem$1.class",
        	"pretransformedclasses/org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem$SerializableMeta.class",
        	"pretransformedclasses/org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem$ItemMetaKey.class",
        	"pretransformedclasses/org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem$ItemMetaKey$Specific.class",
        	"pretransformedclasses/org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem$ItemMetaKey$Specific$To.class",
        	"pretransformedclasses/org/bukkit/craftbukkit/v1_7_R4/inventory/CraftItemFactory.class",
        	"org/bukkit/craftbukkit/v1_7_R4/inventory/BannerMeta.class"
        );
    }

    private static String getLibraryPath(String parentDir) {
    	String path = Platform.is64Bit() ? "64/" : "32/";
        switch (Platform.getPlatform()) {
            case LINUX:
            	path += "linux/";
                break;
            case WINDOWS:
            	path += "windows/";
                break;
            case MAC:
            	path += "mac/";
                break;
            case SOLARIS:
            	path += "solaris/";
                break;
            default:
                throw new UnsupportedOperationException("unsupported platform");
        }
        return new File(parentDir, path).getAbsolutePath();
    }

    private static AttachProvider getAttachProvider() {
        switch (Platform.getPlatform()) {
            case LINUX:
            	return new LinuxAttachProvider();
            case WINDOWS:
            	return new WindowsAttachProvider();
			default:
                throw new UnsupportedOperationException("unsupported platform");
        }
    }

}
