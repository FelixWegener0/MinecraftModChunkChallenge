package org.felixWegener.bastichunkchallange;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.LivingEntity;

import java.util.UUID;

public class MonsterDeathListener {

    private static boolean mobKilld = false;

    public static void register(UUID mobId) {
        System.out.println("init MonsterDeath Listener with mob uuid: " + mobId);


        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            if (entity instanceof LivingEntity livingEntity) {
                if (livingEntity.isDead()) {
                    System.out.println("entity died with id: " + entity.getUuid());
                    System.out.println("looking for mob  id: " + mobId);
                }
            }
        });
    }

    // Liefert den Kill-Status zurück
    public static boolean getMobKilled() {
        return mobKilld;
    }

    // Setzt den Kill-Status zurück
    public static void resetMobKilled() {
        mobKilld = false;
    }
}
