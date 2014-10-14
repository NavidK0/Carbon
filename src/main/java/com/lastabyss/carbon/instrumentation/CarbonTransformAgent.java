package com.lastabyss.carbon.instrumentation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;


/**
 *
 * @author Navid
 */
public class CarbonTransformAgent implements ClassFileTransformer {

	//All pretransformed class files should be bundled in "pretransformedclasses" dir inside the carbon jar
    
    private static Instrumentation instrumentation = null;
    private static CarbonTransformAgent transformer;
    
    //Public static void main() but for this agent
    public static void agentmain(String string, Instrumentation instrument) {
        instrumentation = instrument;

        LogManager.getLogger().info("[Carbon] Loaded transformer agent!");

        transformer = new CarbonTransformAgent();
        instrumentation.addTransformer(transformer);
        try {
			instrumentation.redefineClasses(new ClassDefinition(org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack.class, getPreTransformedClass("org/bukkit/craftbukkit/v1_7_R4/inventory/CraftItemStack")));
		} catch (UnmodifiableClassException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
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
