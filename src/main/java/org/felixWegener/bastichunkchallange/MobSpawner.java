package org.felixWegener.bastichunkchallange;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class MobSpawner {

    public static UUID spawnTargetMob(ServerWorld world, BlockPos position) {
        ZombieEntity zombie = new ZombieEntity(EntityType.ZOMBIE, world);
        zombie.refreshPositionAndAngles(position.getX(), position.getY(), position.getZ(), 0, 0);
        world.spawnEntity(zombie);
        return zombie.getUuid();
    }

}
