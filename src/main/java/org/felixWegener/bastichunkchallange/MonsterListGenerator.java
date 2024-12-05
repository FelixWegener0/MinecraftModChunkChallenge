package org.felixWegener.bastichunkchallange;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.Monster;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MonsterListGenerator {

    public static List<EntityType<?>> getAllMonsters() {
        List<EntityType<?>> monsters = new ArrayList<>();

        Registries.ENTITY_TYPE.stream().forEach(entityType -> {
            if (entityType.isSummonable() && entityType.getLootTableKey().isPresent() && !Objects.equals(entityType.toString(), "entity.minecraft.armor_stand") && !Objects.equals(entityType.toString(), "entity.minecraft.creaking")) {
                monsters.add(entityType);
            }
        });

        return monsters;
    }

    public static Integer generateRandomNumber(Integer max) {
        return (int) ((Math.random() * (max)) + 0);
    }

}
