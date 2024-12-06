package org.felixWegener.bastichunkchallange;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Challenge {

    public static void initChallenge() {
        System.out.println("init challenge");
        DelayedTask.register();

        List<EntityType<?>> monsters = MonsterListGenerator.getAllMonsters();
        AtomicBoolean delayActive = new AtomicBoolean(false);
        AtomicReference<UUID> mobId = new AtomicReference<>();

        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            if (!world.getPlayers().isEmpty()) {
                ServerPlayerEntity chosenPlayer = world.getPlayers().getFirst();

                if (entity instanceof LivingEntity livingEntity) {
                    if (livingEntity.isDead()) {
                        if (Objects.equals(entity.getUuid().toString(), mobId.toString())) {
                            mobId.set(null);
                            PlayerMovementListener.removeWorldBoarderPlayerView(world);
                        }
                    }
                }
                if (mobId.get() == null && !delayActive.get()) {
                    delayActive.set(true);

                    DelayedTask.scheduleDelayedTask(200, () -> {
                        BlockPos mobSpawnPosition = new BlockPos(chosenPlayer.getBlockX(), chosenPlayer.getBlockY(), chosenPlayer.getBlockZ());

                        PlayerMovementListener.setWorldBoarderOnPlayer(world, chosenPlayer);
                        Integer randomIndex = MonsterListGenerator.generateRandomNumber(monsters.size() - 1);

                        mobId.set(MobSpawner.spawnTargetMobNew(monsters.get(randomIndex), world, mobSpawnPosition));
                        delayActive.set(false);
                    });

                }
            }
        });

    }

}
