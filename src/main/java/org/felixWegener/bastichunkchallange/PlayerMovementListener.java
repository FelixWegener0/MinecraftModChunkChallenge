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
                    ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                    double playerX = serverPlayer.getX();
                    double playerZ = serverPlayer.getZ();

                    // World Border basierend auf der Spielerposition aktualisieren
                    WorldBorder border = world.getWorldBorder();
                    border.setCenter(playerX, playerZ);
                    border.setSize(32.0);
                });
            }
        });
    }

    public static void setWorldBoarderOnPlayer(ServerWorld world) {
        WorldBorder border = world.getWorldBorder();

        world.getPlayers().forEach(player -> {
            border.setCenter(player.getBlockX(), player.getBlockZ());
        });
        border.setSize(32.0);

    }

    public static void removeWorldBoarderPlayerView(ServerWorld world) {
        WorldBorder border = world.getWorldBorder();

        border.setSize(200000.0);
    }

}
