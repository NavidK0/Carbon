package net.o2gaming.carbon;

import java.util.logging.Logger;
import net.o2gaming.carbon.listeners.BlockListener;
import net.o2gaming.carbon.listeners.ItemListener;
import net.o2gaming.carbon.reflection.Injector;
import org.bukkit.plugin.java.JavaPlugin;

public class Carbon extends JavaPlugin {
  private BlockListener blockListener = new BlockListener(this);
  private ItemListener itemListener = new ItemListener(this);
  public static final Logger log = Logger.getLogger("minecraft");
  public static Injector injector;
  
  @Override
  public void onEnable() {
    try {
      DynamicEnumType.loadReflection();
    }
    catch (NoSuchMethodException|SecurityException|ClassNotFoundException|IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException e) {
      e.printStackTrace();
    }
    getServer().getPluginManager().registerEvents(this.blockListener, this);
    getServer().getPluginManager().registerEvents(this.itemListener, this);
    injector = new Injector();
    injector.registerAll();
    injector.registerRecipes();
    
    log.info("Carbon has finished injecting all 1.8 functionalities.");
  }
  
  public static Injector injector() {
    return injector;
  }
  
  public BlockListener getBlockListener() {
    return this.blockListener;
  }
  
  public ItemListener getItemListener() {
    return this.itemListener;
  }
}
