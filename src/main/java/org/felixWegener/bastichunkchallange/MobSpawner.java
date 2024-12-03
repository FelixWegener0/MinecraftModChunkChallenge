package org.felixWegener.bastichunkchallange;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class MobSpawner {

    public static UUID spawnTargetMob(ServerWorld world, BlockPos position) {
        ZombieEntity zombie = new ZombieEntity(EntityType.ZOMBIE, world);
        zombie.refreshPositionAndAngles(position.getX(), position.getY(), position.getZ(), 0, 0);
        world.spawnEntity(zombie);
        return zombie.getUuid();
    }

    public static <T extends LivingEntity> UUID spawnTargetMobNew(EntityType<?> entityType, ServerWorld world, BlockPos position) {
        T entity = (T) entityType.spawn(world, position, null);

        if (entity == null) {
            throw new IllegalArgumentException("Ungültiger EntityType: " + entityType);
        }

        entity.refreshPositionAndAngles(position.getX(), position.getY(), position.getZ(), 0, 0);
        world.spawnEntity(entity);

        return entity.getUuid();
    }

}
