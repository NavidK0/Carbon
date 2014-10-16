package com.lastabyss.carbon.instrumentation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.Field;
import java.security.ProtectionDomain;
import java.util.HashMap;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.inventory.BannerMeta;

/**
 *
 * @author Navid
 */
public class CarbonTransformAgent implements ClassFileTransformer {

	//All pretransformed classes are located in the pretransformedclasses folder inside the jar

    private static Instrumentation instrumentation = null;
    private static CarbonTransformAgent transformer;
    
    //Public static void main() but for this agent
    public static void agentmain(String string, Instrumentation instrument) {
        instrumentation = instrument;

        LogManager.getLogger().info("[Carbon] Loaded transformer agent!");

        transformer = new CarbonTransformAgent();
        instrumentation.addTransformer(transformer);
        try {
        	//redefine classes
        	instrumentation.redefineClasses(
        		new ClassDefinition(
        			Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack"), 
        			getPreTransformedClass("org/bukkit/craftbukkit/v1_7_R4/inventory/CraftItemStack")
        		)
        	);
			instrumentation.redefineClasses(
				new ClassDefinition(
					Class.forName("org.bukkit.craftbukkit.v1_7_R4.inventory.CraftMetaItem"), 
					getPreTransformedClass("org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem")
				)
			);

	        //inject banner meta into the server
	        ClassLoader cl = ClassLoader.getSystemClassLoader();
	        Field packagesField = cl.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("packages");
	        packagesField.setAccessible(true);
	        @SuppressWarnings("unchecked")
			HashMap<String, Package> packages = (HashMap<String, Package>) packagesField.get(cl);
	        synchronized (packages) {
	        	for (Package pkg : packages.values()) {
	        		if (pkg.getName().equals("org.bukkit.craftbukkit.v1_7_R4.inventory")) {
	        			//unseal
	        			Object sealBase = null;
		        		Field sealBaseField = pkg.getClass().getDeclaredField("sealBase");
		        		sealBaseField.setAccessible(true);
		        		sealBase = sealBaseField.get(pkg);
		        		sealBaseField.set(pkg, null);
		        		//load class
		        		BannerMeta.init();
		        		//seal back
		        		sealBaseField.set(pkg, sealBase);
		        		break;
	        		}
	        	}
	        }
		} catch (UnmodifiableClassException | ClassNotFoundException | IOException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			Bukkit.shutdown();
		}
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
    	if (className.equals("org/bukkit/craftbukkit/v1_7_R4/inventory/CraftItemStack")) {
    		LogManager.getLogger().log(Level.INFO, "[Carbon] Transforming CraftBukkit CraftItemStack");
    		try {
    			return getPreTransformedClass(className);
    		} catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to transform class!");
    		}
    	} else if (className.startsWith("org/bukkit/craftbukkit/v1_7_R4/inventory/CraftMetaItem")) {
    		LogManager.getLogger().log(Level.INFO, "[Carbon] Transforming CraftBukkit CraftMetaItem");
    		try {
    			return getPreTransformedClass(className);
    		} catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to transform class!");
    		}
    	}
		return null;
    }

    private static byte[] getPreTransformedClass(String className) throws IOException {
		InputStream stream = CarbonTransformAgent.class.getClassLoader().getResourceAsStream("pretransformedclasses/"+className+".class");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[4096];
        while ((nRead = stream.read(data)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    public static void killAgent() {
        instrumentation.removeTransformer(transformer);
    }

}
