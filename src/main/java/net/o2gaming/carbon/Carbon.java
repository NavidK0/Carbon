package net.o2gaming.carbon;

import java.util.logging.Logger;
import net.o2gaming.carbon.chunkgenerator.CarbonWorldGenerator;
import net.o2gaming.carbon.listeners.BlockListener;
import net.o2gaming.carbon.listeners.ItemListener;
import net.o2gaming.carbon.reflection.Injector;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

public class Carbon extends JavaPlugin {
  private BlockListener blockListener = new BlockListener(this);
  private ItemListener itemListener = new ItemListener(this);
  private CarbonWorldGenerator worldGenerator = new CarbonWorldGenerator(this);
  public static final Logger log = Logger.getLogger("minecraft");
  public static Injector injector;

    @Override
    public void onLoad() {
        try {
          DynamicEnumType.loadReflection();
        }
        catch (NoSuchMethodException|SecurityException|ClassNotFoundException|IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException e) {
          e.printStackTrace();
        }
        getServer().getPluginManager().registerEvents(this.blockListener, this);
        getServer().getPluginManager().registerEvents(this.itemListener, this);
        getServer().getPluginManager().registerEvents(this.worldGenerator, this);
        injector = new Injector();
        injector.registerAll();
        injector.registerRecipes();
        worldGenerator.populate();
        log.info("Carbon has finished injecting all 1.8 functionalities.");
    }
  
  
  @Override
  public void onEnable() {
    log.info("[Carbon] Enabled.");
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

    public CarbonWorldGenerator getWorldGenerator() {
        return worldGenerator;
    }
  
}
