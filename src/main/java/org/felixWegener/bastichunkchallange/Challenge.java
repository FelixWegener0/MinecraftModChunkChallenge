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
        for (EntityType<?> monster : monsters) {
            System.out.println(monster);
        }

        AtomicBoolean delayActive = new AtomicBoolean(false);
        AtomicReference<UUID> mobId = new AtomicReference<>();

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                world.getPlayers().forEach(player -> {

                    // Hardcore border reset to be around admin player
                    if (Objects.equals(player.getName().toString(), "literal{Ich_Bin_AFK}")) {

                        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

                        if (mobId.get() == null && !delayActive.get()) {
                            // this is called when the last mob was killed or on init server join
                            delayActive.set(true);
                            DelayedTask.scheduleDelayedTask(200, () -> {

                                ServerWorld ServerWorld = server.getOverworld();
                                BlockPos mobSpawnPosition = new BlockPos(
                                        serverPlayer.getBlockX() + 10,
                                        serverPlayer.getBlockY(),
                                        serverPlayer.getBlockZ()
                                );

                                PlayerMovementListener.setWorldBoarderOnPlayer(ServerWorld);
                                mobId.set(MobSpawner.spawnTargetMob(server.getOverworld(), mobSpawnPosition));
                                delayActive.set(false);
                            });
                        }

                        ServerEntityEvents.ENTITY_UNLOAD.register((entity, MCworld) -> {
                            if (entity instanceof LivingEntity livingEntity) {
                                if (livingEntity.isDead()) {

                                    if (Objects.equals(entity.getUuid().toString(), mobId.toString())) {
                                        // this method is called when the mob that was last spawned dies
                                        mobId.set(null);
                                        PlayerMovementListener.removeWorldBoarderPlayerView(server.getOverworld());
                                    }

                                }
                            }
                        });
                    }

                });
            }
        });
    }

}