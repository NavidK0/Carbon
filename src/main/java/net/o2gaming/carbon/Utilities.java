package net.o2gaming.carbon;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.v1_7_R4.Block;
import org.bukkit.Material;

/**
 *
 * @author Navid
 */
public class Utilities {

    @SuppressWarnings("unchecked")
    public static Material addMaterial(String name, int id)
    {
        Material material = (Material) DynamicEnumType.addEnum(Material.class, name, new Class[] { Integer.TYPE }, new Object[] { id });
        try
        {
                Field field = Material.class.getDeclaredField("BY_NAME");
                field.setAccessible(true);
                Object object = field.get(null);
                Map<String, Material> BY_NAME = (Map<String, Material>) object;
                BY_NAME.put(name, material);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
        {
                e.printStackTrace();
        }
        try
        {
                Field field = Material.class.getDeclaredField("byId");
                field.setAccessible(true);
                Object object = field.get((int)0);
                Material[] byId = (Material[]) object;
                byId[id] = material;
                field.set(object, byId);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
        {
                e.printStackTrace();
        }
        return material;
    }
    
    @SuppressWarnings("unchecked")
    public static Material addMaterial(String name, int id, short data)
    {
        Material material = (Material) DynamicEnumType.addEnum(Material.class, name, new Class[] { Integer.TYPE }, new Object[] { id });
        try
        {
                Field field = Material.class.getDeclaredField("BY_NAME");
                field.setAccessible(true);
                Object object = field.get(null);
                Map<String, Material> BY_NAME = (Map<String, Material>) object;
                BY_NAME.put(name, material);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
        {
                e.printStackTrace();
        }
        try
        {
                Field field = Material.class.getDeclaredField("byId");
                field.setAccessible(true);
                Object object = field.get((int)0);
                Material[] byId = (Material[]) object;
                byId[id] = material;
                field.set(object, byId);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
        {
                e.printStackTrace();
        }
        try
        {
                Field field = Material.class.getDeclaredField("durability");
                field.setAccessible(true);
                Object object = field.get((short)0);
                Material[] durability = (Material[]) object;
                durability[data] = material;
                field.set(object, durability);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
        {
                e.printStackTrace();
        }
        return material;
    }
    
    
    /**
     * Returns 0 if there's an error accessing the "strength" field in the minecraft server
     * Blocks class, otherwise, returns the block's given strength.
     * @param b
     * @return 
     */
    public static float getBlockStrength(Block b) {
        try {
            Field field = b.getClass().getField("strength");
            field.setAccessible(true);
            return field.getFloat(b);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    /**
     * Returns 0 if there's an error accessing the "durability" field in the minecraft server
     * Blocks class, otherwise, returns the block's given strength.
     * @param b
     * @return 
     */
    public static float getBlockDurability(Block b) {
        try {
            Field field = b.getClass().getField("durability");
            field.setAccessible(true);
            return field.getFloat(b);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
