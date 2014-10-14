package com.lastabyss.carbon.instrumentation;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import org.apache.logging.log4j.LogManager;


/**
 *
 * @author Navid
 */
public class CarbonTransformAgent implements ClassFileTransformer {
    
    private static Instrumentation instrumentation = null;
    private static CarbonTransformAgent transformer;
    
    //Public static void main() but for this agent
    public static void agentmain(String string, Instrumentation instrument) {
        instrumentation = instrument;

        LogManager.getLogger().info("[Carbon] Loaded transformer agent!");

        transformer = new CarbonTransformAgent();
        instrumentation.addTransformer(transformer);
        try {
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to redefine classses!");
        }
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		return null;
    }

    private String getClassName(Class<?> clazz) {
    	return clazz.getName().replace(".", "/");
    }

    public static void killAgent() {
        instrumentation.removeTransformer(transformer);
    }

}
