package org.felixWegener.bastichunkchallange;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.border.WorldBorder;

public class PlayerMovementListener {

    public static void dynamicEvent() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                world.getPlayers().forEach(player -> {
                    double playerX = player.getX();
                    double playerZ = player.getZ();

                    WorldBorder border = world.getWorldBorder();
                    border.setCenter(playerX, playerZ);
                    border.setSize(32.0);
                });
            }
        });
    }

    public static void setWorldBoarderOnPlayer(ServerWorld world, ServerPlayerEntity player) {
        WorldBorder border = world.getWorldBorder();

        border.setCenter(player.getBlockX(), player.getBlockZ());
        border.setSize(32.0);
    }

    public static void removeWorldBoarderPlayerView(ServerWorld world) {
        WorldBorder border = world.getWorldBorder();
        border.setSize(200000.0);
    }

}
