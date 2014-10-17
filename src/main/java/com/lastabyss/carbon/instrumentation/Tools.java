package com.lastabyss.carbon.instrumentation;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;

/**
 * Various IO tools.
 *
 * @author Tudor
 */
public class Tools {

    /**
     * Gets the current JVM PID.
     *
     * @return Returns the PID.
     */
    public static String getCurrentPID() {
        String jvm = ManagementFactory.getRuntimeMXBean().getName();
        return jvm.substring(0, jvm.indexOf('@'));
    }

    public static byte[] getBytesFromStream(InputStream stream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[65536];
        while ((nRead = stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();

    }

    /**
     * Gets bytes from class
     *
     * @param clazz The class.
     * @return Returns a byte[] representation of given class.
     * @throws IOException
     */
    public static byte[] getBytesFromClass(Class<?> clazz) throws IOException {
        return getBytesFromStream(clazz.getClassLoader().getResourceAsStream(clazz.getName().replace('.', '/') + ".class"));
    }

    /**
     * Gets bytes from resource
     *
     * @param resource The resource string.
     * @return Returns a byte[] representation of given resource.
     * @throws IOException
     */
    public static byte[] getBytesFromResource(ClassLoader clazzLoader, String resource) throws IOException {
        return getBytesFromStream(clazzLoader.getResourceAsStream(resource));
    }

    /**
     * Adds a a path to the current java.library.path.
     *
     * @param path The path.
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static void addToLibPath(String path) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        if (System.getProperty("java.library.path") != null) {
            // If java.library.path is not empty, we will prepend our path
            // Note that path.separator is ; on Windows and : on *nix,
            // so we can't hard code it.
            System.setProperty("java.library.path", path + System.getProperty("path.separator") + System.getProperty("java.library.path"));
        } else {
            System.setProperty("java.library.path", path);
        }

        // Important: java.library.path is cached
        // We will be using reflection to clear the cache
        Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
        fieldSysPath.setAccessible(true);
        fieldSysPath.set(null, null);

    }

    public enum Platform {

        LINUX, WINDOWS, MAC, SOLARIS;

        public static Platform getPlatform() {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win") && !os.contains("darwin")) {
                return WINDOWS;
            }
            if ((os.contains("nix")) || (os.contains("nux")) || (os.indexOf("aix") > 0)) {
                return LINUX;
            }
            if (os.contains("mac")) {
                return MAC;
            }
            if (os.contains("sunos")) {
                return SOLARIS;
            }
            return null;
        }

        public static boolean is64Bit() {
            String osArch = System.getProperty("os.arch");
            return "amd64".equals(osArch) || "x86_64".equals(osArch);
        }
    }

}
