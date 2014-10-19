package com.lastabyss.test;

import com.lastabyss.carbon.utils.Utilities;

/**
 *
 * @author Navid
 */
public class TestGrabMinecraftVersion {
    public static final String MCVER1710 = "git-Spigot-1.7.9-R0.2-207-g03373bb (MC: 1.7.10)";
    
    
    public static void main(String[] args) {
        System.out.println("Original string: " + MCVER1710);
        System.out.println("MC Version: " + Utilities.getMinecraftVersion(MCVER1710));
    }
}
