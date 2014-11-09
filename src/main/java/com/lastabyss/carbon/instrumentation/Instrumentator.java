package com.lastabyss.carbon.instrumentation;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.instrumentation.Tools.Platform;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.spi.AttachProvider;

import java.io.File;
import java.io.IOException;

import sun.tools.attach.BsdAttachProvider;
import sun.tools.attach.LinuxAttachProvider;
import sun.tools.attach.SolarisAttachProvider;
import sun.tools.attach.WindowsAttachProvider;

/**
 * This is exactly what you think it is.
 * @author Navid, Icynene
 */
public class Instrumentator {

	//All pretransformed classes should be located in the pretransformedclasses folder inside the jar

    private String attachLibFolder;

    public Instrumentator(Carbon plugin, String attachLibFolder) {
        this.attachLibFolder = attachLibFolder;
        System.out.println("[Carbon] Lib folder has been set to " + attachLibFolder);
    }

    public void instrumentate() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
    	Tools.addToLibPath(getLibraryPath(attachLibFolder));
    	AttachProvider.setAttachProvider(getAttachProvider());
        AgentLoader.attachAgentToJVM(
        	Tools.getCurrentPID(), CarbonTransformAgent.class,
        	new String[] {
        		"org/bukkit/craftbukkit/v1_7_R4/inventory/BannerMeta.class",
        		"org/bukkit/craftbukkit/v1_7_R4/inventory/BannerMeta$BannerPattern.class",
        		"net/minecraft/server/v1_7_R4/EnumEntitySpawnZone.class",
        		"net/minecraft/server/v1_7_R4/EntitySpawnZone.class",
        	},
        	"org/bukkit/craftbukkit/v1_7_R4/inventory/CraftItemStack.class",
        	"org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem.class",
        	"org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem$SerializableMeta.class",
        	"org/bukkit/craftbukkit/v1_7_R4/inventory/CraftItemFactory.class",
        	"net/minecraft/server/v1_7_R4/EntityTracker.class",
        	"net/minecraft/server/v1_7_R4/EntityTrackerEntry.class",
        	"net/minecraft/server/v1_7_R4/DataWatcher.class",
        	"net/minecraft/server/v1_7_R4/SpawnerCreature.class",
        	"net/minecraft/server/v1_7_R4/SpawnerCreature$1.class",
        	"org/bukkit/Material.class"
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
