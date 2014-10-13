package com.lastabyss.carbon.instrumentation;

import com.lastabyss.carbon.Carbon;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Navid
 */
public class ClassMaterialAgent implements ClassFileTransformer {
    
    private static Instrumentation instrumentation = null;
    private static ClassMaterialAgent transformer;
    
    //Public static void main() but for this agent
    public static void agentmain(String string, Instrumentation instrument) {
        Carbon.log.info("[Carbon] Loaded Material.class transformer agent!");
        
        transformer = new ClassMaterialAgent();
        instrumentation.addTransformer(transformer);
        try {
            instrumentation.redefineClasses(new ClassDefinition(org.bukkit.Material.class, Tools.getBytesFromClass(com.lastabyss.carbon.instrumentation.classes.Material.class)));
            } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to redefine class!");
        }
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            Carbon.log.log(Level.INFO, "[Carbon] Instrumenting class {0}", className);
            
            System.out.println("Instrumenting class: " + className);
            return Tools.getBytesFromClass(com.lastabyss.carbon.instrumentation.classes.Material.class);
        } catch (IOException ex) {
            Logger.getLogger(ClassMaterialAgent.class.getName()).log(Level.SEVERE, null, ex);
            return new byte[0];
        }
    }
   
    //Kills agent
    public static void killAgent() {
        instrumentation.removeTransformer(transformer);
    }

}
