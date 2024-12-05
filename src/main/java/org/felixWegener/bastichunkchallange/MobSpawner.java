package org.felixWegener.bastichunkchallange;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.attribute.EntityAttributes;


import java.util.Objects;
import java.util.UUID;

public class MobSpawner {

    public static UUID spawnTargetMob(ServerWorld world, BlockPos position) {
        ZombieEntity zombie = new ZombieEntity(EntityType.ZOMBIE, world);
        zombie.refreshPositionAndAngles(position.getX(), position.getY(), position.getZ(), 0, 0);
        zombie.setPersistent();
        world.spawnEntity(zombie);
        return zombie.getUuid();
    }

    public static <T extends LivingEntity> UUID spawnTargetMobNew(EntityType<?> entityType, ServerWorld world, BlockPos position) {
        T entity = (T) entityType.spawn(world, position, null);

        if (entity == null) {
            throw new IllegalArgumentException("Ungültiger EntityType: " + entityType);
        }

        entity.refreshPositionAndAngles(position.getX(), position.getY(), position.getZ(), 0, 0);
        float newHealth = entity.getMaxHealth() * world.getPlayers().size();

        Objects.requireNonNull(entity.getAttributeInstance(EntityAttributes.MAX_HEALTH)).setBaseValue(newHealth);
        entity.setHealth(newHealth);
        entity.setCustomName(entity.getName());
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING,60 * 60 * 20, 0, false, true));
        world.spawnEntity(entity);

        System.out.println("Spawning new mob with health: " + entity.getHealth());
        return entity.getUuid();
    }

}
