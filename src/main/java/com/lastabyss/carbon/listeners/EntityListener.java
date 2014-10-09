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

    /**
     * Change rabbit's color and age depending on chance.
     * @param evt 
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onRabbitSpawned(CreatureSpawnEvent evt) {
        if (evt.getEntityType() == Carbon.injector().rabbitEntity)
            if (evt.getSpawnReason() == CreatureSpawnEvent.SpawnReason.DISPENSE_EGG ||
                evt.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG ||
                evt.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL ||
                evt.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING || 
                evt.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
                Rabbit rabbit = (Rabbit)evt.getEntity();
                if (evt.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG && rabbit.hasParent()) {
                    rabbit.setRabbitType(rabbit.getParent().getRabbitType());
                    return;
                }
                int type = Utilities.random.nextInt(6);
                boolean isKiller = Utilities.random.nextInt(1000) == 999;
                if (evt.getSpawnReason() != CreatureSpawnEvent.SpawnReason.DISPENSE_EGG &&
                    evt.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG &&
                    evt.getSpawnReason() != CreatureSpawnEvent.SpawnReason.BREEDING &&
                    evt.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER) {
                    boolean isBaby = Utilities.random.nextInt(4) == 3;
                    if (isBaby) {
                        rabbit.setBaby();
                    }
                }
                if (isKiller) {
                    rabbit.setRabbitType(EntityRabbit.TYPE_KILLER);
                } else {
                    rabbit.setRabbitType(type);
                }
            }
    }
}
