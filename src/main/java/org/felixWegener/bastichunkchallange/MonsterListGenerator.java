package org.felixWegener.bastichunkchallange;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.List;

public class MonsterListGenerator {

    public static List<EntityType<?>> getAllMonsters() {
        List<EntityType<?>> monsters = new ArrayList<>();
        List<String> notWantedEntity = new ArrayList<>();

        notWantedEntity.add("entity.minecraft.armor_stand");
        notWantedEntity.add("entity.minecraft.creaking");

        Registries.ENTITY_TYPE.stream().forEach(entityType -> {
            if (entityType.isSummonable() && entityType.getLootTableKey().isPresent() && !notWantedEntity.contains(entityType.toString())) {
                monsters.add(entityType);
            }
        });

        return monsters;
    }

    public static Integer generateRandomNumber(Integer max) {
        return (int) ((Math.random() * (max)) + 0);
    }

}
