package com.lastabyss.carbon.listeners;

import com.lastabyss.carbon.Carbon;
import com.lastabyss.carbon.entity.EntityRabbit;
import com.lastabyss.carbon.entity.bukkit.Rabbit;
import com.lastabyss.carbon.utils.Utilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * @author Navid
 */
public class EntityListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onRabbitSpawned(CreatureSpawnEvent evt) {
        if (evt.getEntityType() == Carbon.injector().rabbitEntity)
            if (evt.getSpawnReason() == CreatureSpawnEvent.SpawnReason.DISPENSE_EGG ||
                evt.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG ||
                evt.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL ||
                evt.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING || 
                evt.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
                Rabbit rabbit = (Rabbit)evt.getEntity();
                int type = Utilities.random.nextInt(6);
                int isKiller = Utilities.random.nextInt(1000);
                
                if (isKiller == 999) {
                    rabbit.setType(EntityRabbit.TYPE_KILLER);
                } else {
                    rabbit.setType(type);
                }
            }
    }
}
