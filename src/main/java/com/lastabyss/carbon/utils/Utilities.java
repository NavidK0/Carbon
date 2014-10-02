package com.lastabyss.carbon.utils;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.DynamicEnumType;
import com.lastabyss.carbon.worldborder.CommandWorldBorder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.Packet;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 *
 * @author Navid
 */
public class Utilities {
    
    private static Carbon plugin = null;

    private Utilities() {
        throw new UnsupportedOperationException("No, bad!");
    }
    
    /**
     * Can only be instantiated once.
     * @param instance
     */
    public static void instantiate(Carbon instance) {
        if (plugin == null) {
            plugin = instance;
        }
    }
    
    /**
     * Registers a bukkit command without the need for a plugin.yml entry.
     * 
     * Yes, I'm terrible.
     * @param fallbackPrefix
     * @param cmd
     */
    public static void registerBukkitCommand(String fallbackPrefix, Command cmd) {
        try {
        if (Bukkit.getServer() instanceof CraftServer) {
            Field f = CraftServer.class.getDeclaredField("commandMap");
            f.setAccessible(true);
            CommandMap cmap = (CommandMap) f.get(Bukkit.getServer());
            cmap.register(fallbackPrefix, cmd);
        }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Returns the BUKKIT ENTITYTYPE.
     * @param name
     * @param id
     * @param entityClass
     * @return 
     */
    @SuppressWarnings("unchecked")
    public static EntityType addEntity(String name, int id, Class<? extends Entity> entityClass) {
        EntityType entityType = DynamicEnumType.addEnum(EntityType.class, name, new Class[] {String.class, entityClass.getClass(), Integer.TYPE}, new Object[] {name, entityClass.getClass(), id});
        try {
                Field field = EntityType.class.getDeclaredField("NAME_MAP");
                field.setAccessible(true);
                Object object = field.get(null);
                Map<String, EntityType> NAME_MAP = (Map<String, EntityType>) object;
                NAME_MAP.put(name, entityType);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
        }
        try {
                Field field = EntityType.class.getDeclaredField("ID_MAP");
                field.setAccessible(true);
                Object object = field.get(null);
                Map<Short, EntityType> ID_MAP = (Map<Short, EntityType>) object;
                ID_MAP.put((short)id, entityType);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
        }
        return entityType;
    }

    @SuppressWarnings("unchecked")
    public static Material addMaterial(String name, int id) {
        Material material = (Material) DynamicEnumType.addEnum(Material.class, name, new Class[] { Integer.TYPE }, new Object[] { id });
        try {
                Field field = Material.class.getDeclaredField("BY_NAME");
                field.setAccessible(true);
                Object object = field.get(null);
                Map<String, Material> BY_NAME = (Map<String, Material>) object;
                BY_NAME.put(name, material);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
        {
                e.printStackTrace();
        }
        try {
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
    public static Material addMaterial(String name, int id, short data) {
        Material material = (Material) DynamicEnumType.addEnum(Material.class, name, new Class[] { Integer.TYPE }, new Object[] { id });
        try {
                Field field = Material.class.getDeclaredField("BY_NAME");
                field.setAccessible(true);
                Object object = field.get(null);
                Map<String, Material> BY_NAME = (Map<String, Material>) object;
                BY_NAME.put(name, material);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
        {
                e.printStackTrace();
        }
        try {
                Field field = Material.class.getDeclaredField("byId");
                field.setAccessible(true);
                Object object = field.get((int)0);
                Material[] byId = (Material[]) object;
                byId[id] = material;
                field.set(object, byId);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
        }
        try {
                Field field = Material.class.getDeclaredField("durability");
                field.setAccessible(true);
                Object object = field.get((short)0);
                Material[] durability = (Material[]) object;
                durability[data] = material;
                field.set(object, durability);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
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
    public static float getBlockStrength(net.minecraft.server.v1_7_R4.Block b) {
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
    public static float getBlockDurability(net.minecraft.server.v1_7_R4.Block b) {
        try {
            Field field = b.getClass().getField("durability");
            field.setAccessible(true);
            return field.getFloat(b);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    
    /**
     * Returns all adjacent blocks to a specified block.
     * Returns an empty list if none were found.
     * Air is not included.
     * @param source
     * @return 
     */
    public static List<Block> getAllAdjacentBlocks(Block source) {
        List<Block> list = new ArrayList<>();
        for (BlockFace f : BlockFace.values()) {
            Block rel = source.getRelative(f);
            if (rel.getType() != Material.AIR) {
            list.add(rel);
            }
        }
        return list;
    }

    /**
     * Sends packet to a player
     * @param player
     * @param packet
     */
    public static void sendPacket(Player player, Packet packet) {
    	EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
    	nmsPlayer.playerConnection.sendPacket(packet);
    }

}
