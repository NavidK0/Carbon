package com.lastabyss.carbon.instrumentation;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.instrumentation.Tools.Platform;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.spi.AttachProvider;
import sun.tools.attach.BsdAttachProvider;
import sun.tools.attach.LinuxAttachProvider;
import sun.tools.attach.SolarisAttachProvider;
import sun.tools.attach.WindowsAttachProvider;

import java.io.File;
import java.io.IOException;

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
        	new String[] {
        		"pretransformedclasses/org/bukkit/craftbukkit/v1_7_R4/inventory/BannerMeta.class",
        		"pretransformedclasses/org/bukkit/craftbukkit/v1_7_R4/inventory/BannerMeta$BannerPattern.class"
        	},
        	"pretransformedclasses/org/bukkit/craftbukkit/v1_7_R4/inventory/CraftItemStack.class",
        	"pretransformedclasses/org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem.class",
        	"pretransformedclasses/org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem$SerializableMeta.class",
        	"pretransformedclasses/org/bukkit/craftbukkit/v1_7_R4/inventory/CraftItemFactory.class",
        	"pretransformedclasses/net/minecraft/server/v1_7_R4/EntityTracker.class",
        	"pretransformedclasses/net/minecraft/server/v1_7_R4/EntityTrackerEntry.class",
        	"pretransformedclasses/net/minecraft/server/v1_7_R4/DataWatcher.class"
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
            case MAC:
            	return new BsdAttachProvider();
            case SOLARIS:
            	return new SolarisAttachProvider();
			default:
                throw new UnsupportedOperationException("unsupported platform");
        }
    }

}
