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

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerWorld world : server.getWorlds()) {
                if (!world.getPlayers().isEmpty()) {
                    ServerPlayerEntity chosenPlayer = world.getPlayers().getFirst();

                    if (mobId.get() == null && !delayActive.get()) {
                        delayActive.set(true);

                        DelayedTask.scheduleDelayedTask(200, () -> {
                            ServerWorld ServerWorld = server.getOverworld();
                            BlockPos mobSpawnPosition = new BlockPos(chosenPlayer.getBlockX(), chosenPlayer.getBlockY(), chosenPlayer.getBlockZ());

                            PlayerMovementListener.setWorldBoarderOnPlayer(ServerWorld);
                            Integer randomIndex = MonsterListGenerator.generateRandomNumber(monsters.size() - 1);

                            mobId.set(MobSpawner.spawnTargetMobNew(monsters.get(randomIndex), server.getOverworld(), mobSpawnPosition));
                            delayActive.set(false);
                        });

                    }

                }

            }

        });

        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {

            if (entity instanceof LivingEntity livingEntity) {
                if (livingEntity.isDead()) {
                    if (Objects.equals(entity.getUuid().toString(), mobId.toString())) {
                        mobId.set(null);
                        PlayerMovementListener.removeWorldBoarderPlayerView(world);
                    }
                }
            }

        });

    }

}
